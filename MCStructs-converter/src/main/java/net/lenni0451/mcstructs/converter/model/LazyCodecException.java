package net.lenni0451.mcstructs.converter.model;

import java.util.function.Supplier;

/**
 * A codec exception that only builds its message when requested.
 * Used for errors that are usually discarded, e.g. when trying multiple codecs.
 */
public class LazyCodecException extends CodecException {

    private final Supplier<String> messageSupplier;

    public LazyCodecException(final Supplier<String> messageSupplier) {
        this.messageSupplier = messageSupplier;
    }

    @Override
    public String getMessage() {
        return this.messageSupplier.get();
    }

}
