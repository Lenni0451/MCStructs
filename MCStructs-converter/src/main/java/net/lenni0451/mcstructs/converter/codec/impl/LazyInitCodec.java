package net.lenni0451.mcstructs.converter.codec.impl;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.function.Supplier;

public class LazyInitCodec<T> implements Codec<T> {

    private final Supplier<Codec<T>> codecSupplier;
    private Codec<T> codec;
    private volatile boolean initialized = false;

    public LazyInitCodec(final Supplier<Codec<T>> codecSupplier) {
        this.codecSupplier = codecSupplier;
    }

    private Codec<T> getCodec() {
        if (!this.initialized) {
            synchronized (this.codecSupplier) {
                if (!this.initialized) {
                    this.codec = this.codecSupplier.get();
                    this.initialized = true;
                }
            }
        }
        return this.codec;
    }

    @Override
    public <S> Result<S> serialize(DataConverter<S> converter, T element) {
        return this.getCodec().serialize(converter, element);
    }

    @Override
    public <S> Result<T> deserialize(DataConverter<S> converter, S data) {
        return this.getCodec().deserialize(converter, data);
    }

}
