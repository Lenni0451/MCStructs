package net.lenni0451.mcstructs.converter.model;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.function.Function;

public interface Result<T> {

    static <T> Result<T> success(final T result) {
        return new Success<>(result);
    }

    static <T> Result<T> error(final String error) {
        return new Error<>(new CodecException(error));
    }

    static <T> Result<T> error(final Throwable cause) {
        return new Error<>(new CodecException(cause));
    }

    static <T> Result<T> mergeErrors(final String error, final Collection<Result<?>> errors) {
        return new Error<>(new MergedCodecException(error, errors));
    }

    static <T> Result<T> unexpected(final Object actual, final Class<?>... expected) {
        String[] names = new String[expected.length];
        for (int i = 0; i < expected.length; i++) {
            names[i] = expected[i].getSimpleName();
        }
        return unexpected(actual, names);
    }

    static <T> Result<T> unexpected(final Object actual, final String... expected) {
        return error("Expected " + String.join("/", expected) + " but got " + (actual == null ? "null" : actual.getClass().getSimpleName()));
    }


    T get();

    CodecException getError();

    T getOrThrow(final Function<Throwable, ? extends Throwable> exceptionSupplier);

    T orElse(final T other);

    T orElseThrow(final Function<Throwable, ? extends Throwable> exceptionSupplier);

    <N> Result<N> map(final Function<T, N> mapper);

    <N> Result<N> mapResult(final Function<T, Result<N>> mapper);

    <N> Result<N> mapError();

    boolean isSuccessful();

    boolean isError();

    @Override
    String toString();

    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();


    @EqualsAndHashCode(callSuper = false)
    class Success<T> implements Result<T> {
        private final T result;

        private Success(final T result) {
            this.result = result;
        }

        @Override
        public T get() {
            return this.result;
        }

        @Override
        public CodecException getError() {
            return null;
        }

        @Override
        public T getOrThrow(Function<Throwable, ? extends Throwable> exceptionSupplier) {
            return this.result;
        }

        @Override
        public T orElse(T other) {
            return this.result;
        }

        @Override
        public T orElseThrow(Function<Throwable, ? extends Throwable> exceptionSupplier) {
            return this.result;
        }

        @Override
        public <N> Result<N> map(Function<T, N> mapper) {
            return Result.success(mapper.apply(this.result));
        }

        @Override
        public <N> Result<N> mapResult(Function<T, Result<N>> mapper) {
            return mapper.apply(this.result);
        }

        @Override
        public <N> Result<N> mapError() {
            return Result.error("No error");
        }

        @Override
        public boolean isSuccessful() {
            return true;
        }

        @Override
        public boolean isError() {
            return false;
        }

        @Override
        public String toString() {
            return "Success{" + this.result + "}";
        }
    }

    @EqualsAndHashCode(callSuper = false)
    class Error<T> implements Result<T> {
        private final CodecException error;

        private Error(final CodecException error) {
            this.error = error;
        }

        @Override
        public T get() {
            throw this.error;
        }

        @Override
        public CodecException getError() {
            return this.error;
        }

        @Override
        @SneakyThrows
        public T getOrThrow(Function<Throwable, ? extends Throwable> exceptionSupplier) {
            throw exceptionSupplier.apply(this.error);
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        @SneakyThrows
        public T orElseThrow(Function<Throwable, ? extends Throwable> exceptionSupplier) {
            throw exceptionSupplier.apply(this.error);
        }

        @Override
        public <N> Result<N> map(Function<T, N> mapper) {
            return this.mapError();
        }

        @Override
        public <N> Result<N> mapResult(Function<T, Result<N>> mapper) {
            return this.mapError();
        }

        @Override
        public <N> Result<N> mapError() {
            return (Result<N>) this;
        }

        @Override
        public boolean isSuccessful() {
            return false;
        }

        @Override
        public boolean isError() {
            return true;
        }

        @Override
        public String toString() {
            return "Error{" + this.error.getMessage() + "}";
        }
    }

}
