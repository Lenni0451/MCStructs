package net.lenni0451.mcstructs.converter.model;

import java.util.Collection;

public class MergedCodecException extends CodecException {

    private final String error;
    private final Collection<Result<?>> errors;

    public MergedCodecException(final String error, final Collection<Result<?>> errors) {
        this.error = error;
        this.errors = errors;

        for (Result<?> result : errors) {
            if (result.isError()) {
                this.addSuppressed(result.getError());
            }
        }
    }

    @Override
    public String getMessage() {
        StringBuilder errorMessagesBuilder = new StringBuilder(128);
        for (Result<?> result : this.errors) {
            if (result.isError()) {
                if (errorMessagesBuilder.length() > 0) {
                    errorMessagesBuilder.append(',');
                }
                errorMessagesBuilder.append('[')
                        .append(result.getError().getMessage())
                        .append(']');
            }
        }
        return this.error + ": " + errorMessagesBuilder;
    }

}
