package net.lenni0451.mcstructs.converter.model;

import lombok.experimental.StandardException;

@StandardException
public class CodecException extends RuntimeException {

    @Override
    public void printStackTrace() {
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
