package net.lenni0451.mcstructs.networkcodec;

import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface NetWriter<T> {
    void write(final ByteBuf buf, final T value);

}
