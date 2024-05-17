package net.lenni0451.mcstructs.converter;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

public class Result<T> {

    public static <T> Result<T> success(final T result) {
        return new Result<>(result, null);
    }

    public static <T> Result<T> error(final String error) {
        return new Result<>(null, new IllegalStateException(error));
    }

    public static <T> Result<T> error(final Throwable cause) {
        return new Result<>(null, new IllegalStateException(cause));
    }

    public static <T> Result<T> mergeErrors(final String error, final Collection<Result<?>> errors) {
        IllegalStateException exception = new IllegalStateException(error);
        errors.stream().filter(Result::isError).map(r -> r.error).forEach(exception::addSuppressed);
        return new Result<>(null, exception);
    }

    public static <T> Result<T> unexpected(final Object actual, final Class<?>... expected) {
        return unexpected(actual, Arrays.stream(expected).map(Class::getSimpleName).toArray(String[]::new));
    }

    public static <T> Result<T> unexpected(final Object actual, final String... expected) {
        return error("Expected " + String.join("/", expected) + " but got " + (actual == null ? "null" : actual.getClass().getSimpleName()));
    }


    private final T result;
    private final IllegalStateException error;

    private Result(final T result, final IllegalStateException error) {
        this.result = result;
        this.error = error;
    }

    public T get() {
        this.validate();
        return this.result;
    }

    public T getOrThrow() {
        if (this.isError()) throw this.error;
        return this.result;
    }

    @SneakyThrows
    public T getOrThrow(final Function<Throwable, ? extends Throwable> exceptionSupplier) {
        if (this.isError()) throw exceptionSupplier.apply(this.error);
        return this.result;
    }

    public T orElse(final T other) {
        return this.isError() ? other : this.result;
    }

    @SneakyThrows
    public T orElseThrow(final Function<Throwable, ? extends Throwable> exceptionSupplier) {
        if (this.isError()) throw exceptionSupplier.apply(this.error);
        return this.result;
    }

    public <N> Result<N> map(final Function<T, N> mapper) {
        if (this.isError()) return new Result<>(null, this.error);
        return Result.success(mapper.apply(this.result));
    }

    public <N> Result<N> mapError() {
        if (!this.isError()) return Result.error("No error");
        return (Result<N>) this;
    }

    public String error() {
        return this.error.getMessage();
    }

    public boolean isSuccessful() {
        return !this.isError();
    }

    public boolean isError() {
        return this.error != null;
    }

    public void validate() {
        if (this.isError()) throw new IllegalStateException("Tried to get result from error", this.error);
    }

    @Override
    public String toString() {
        return "Result{" + (this.error == null ? "result=" + this.result : "error=" + this.error) + "}";
    }

}
