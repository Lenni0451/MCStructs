package net.lenni0451.mcstructs.networkcodec;

import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface NetReader<T> {
    T read(final ByteBuf buf);
}
