package net.lenni0451.mcstructs.converter.model;

import lombok.SneakyThrows;
import net.lenni0451.mcstructs.core.utils.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Result<T> {

    public static <T> Result<T> success(final T result) {
        return new Success<>(result);
    }

    public static <T> Result<T> error(final String error) {
        return new Error<>(new CodecException(error));
    }

    public static <T> Result<T> error(final Throwable cause) {
        return new Error<>(new CodecException(cause));
    }

    public static <T> Result<T> mergeErrors(final String error, final Collection<Result<?>> errors) {
        String errorMessages = errors.stream().filter(Result::isError).map(result -> result.error().getMessage()).map(s -> "[" + s + "]").collect(Collectors.joining(","));
        CodecException exception = new CodecException(error + ": " + errorMessages);
        errors.stream().filter(Result::isError).map(Result::error).forEach(exception::addSuppressed);
        return new Error<>(exception);
    }

    public static <T> Result<T> unexpected(final Object actual, final Class<?>... expected) {
        return unexpected(actual, Arrays.stream(expected).map(Class::getSimpleName).toArray(String[]::new));
    }

    public static <T> Result<T> unexpected(final Object actual, final String... expected) {
        return error("Expected " + String.join("/", expected) + " but got " + (actual == null ? "null" : actual.getClass().getSimpleName()));
    }

    protected abstract T result();

    protected abstract CodecException error();

    public T get() {
        if (this.isError()) throw this.error();
        return this.result();
    }

    @SneakyThrows
    public T getOrThrow(final Function<Throwable, ? extends Throwable> exceptionSupplier) {
        if (this.isError()) throw exceptionSupplier.apply(this.error());
        return this.result();
    }

    public T orElse(final T other) {
        return this.isError() ? other : this.result();
    }

    @SneakyThrows
    public T orElseThrow(final Function<Throwable, ? extends Throwable> exceptionSupplier) {
        if (this.isError()) throw exceptionSupplier.apply(this.error());
        return this.result();
    }

    public <N> Result<N> map(final Function<T, N> mapper) {
        if (this.isError()) return this.mapError();
        return Result.success(mapper.apply(this.result()));
    }

    public <N> Result<N> mapResult(final Function<T, Result<N>> mapper) {
        if (this.isError()) return this.mapError();
        return mapper.apply(this.result());
    }

    public <N> Result<N> mapError() {
        if (!this.isError()) return Result.error("No error");
        return (Result<N>) this;
    }

    public boolean isSuccessful() {
        return !this.isError();
    }

    public boolean isError() {
        return this.error() != null;
    }

    public void validate() {
        if (this.isError()) throw new IllegalStateException("Tried to get result from error", this.error());
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("result", this.result(), r -> this.error() == null)
                .add("error", this.error(), Objects::nonNull)
                .toString();
    }

    private static final class Success<T> extends Result<T> {
        private final T result;

        private Success(final T result) {
            this.result = result;
        }

        @Override
        public T result() {
            return this.result;
        }

        @Override
        protected CodecException error() {
            return null;
        }
    }

    private static final class Error<T> extends Result<T> {
        private final CodecException error;

        private Error(final CodecException error) {
            this.error = error;
        }

        @Override
        protected CodecException error() {
            return this.error;
        }

        @Override
        protected T result() {
            return null;
        }
    }

}
