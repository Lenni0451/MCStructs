package net.lenni0451.mcstructs.converter;

import java.util.Arrays;
import java.util.function.Function;

public class ConverterResult<T> {

    public static <T> ConverterResult<T> success(final T result) {
        return new ConverterResult<>(result, null);
    }

    public static <T> ConverterResult<T> error(final String error) {
        return new ConverterResult<>(null, new IllegalStateException(error));
    }

    public static <T> ConverterResult<T> unexpected(final Object actual, final Class<?>... expected) {
        return unexpected(actual, Arrays.stream(expected).map(Class::getSimpleName).toArray(String[]::new));
    }

    public static <T> ConverterResult<T> unexpected(final Object actual, final String... expected) {
        return error("Expected " + String.join("/", expected) + " but got " + (actual == null ? "null" : actual.getClass().getSimpleName()));
    }


    private final T result;
    private final IllegalStateException error;

    private ConverterResult(final T result, final IllegalStateException error) {
        this.result = result;
        this.error = error;
    }

    public T get() {
        this.validate();
        return this.result;
    }

    public T orElse(final T other) {
        return this.isError() ? other : this.result;
    }

    public <N> ConverterResult<N> map(final Function<T, N> mapper) {
        if (this.isError()) return new ConverterResult<>(null, this.error);
        return ConverterResult.success(mapper.apply(this.result));
    }

    public <N> ConverterResult<N> mapError() {
        if (!this.isError()) return ConverterResult.error("No error");
        return ConverterResult.error(this.error.getMessage());
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
        if (this.isError()) throw new IllegalStateException(this.error);
    }

}
