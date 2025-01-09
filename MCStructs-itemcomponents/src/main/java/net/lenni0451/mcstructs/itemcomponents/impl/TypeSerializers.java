package net.lenni0451.mcstructs.itemcomponents.impl;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * An abstract class for version specific type serializers.
 */
public class TypeSerializers {

    protected final ItemComponentRegistry registry;
    private final Map<String, Codec<?>> codecCache;

    public TypeSerializers(final ItemComponentRegistry registry) {
        this.registry = registry;
        this.codecCache = new HashMap<>();
    }

    protected <T> Codec<T> init(final String key, final Supplier<Codec<T>> codecSupplier) {
        if (this.codecCache.containsKey(key)) {
            Codec<T> codec = (Codec<T>) this.codecCache.get(key);
            if (codec == null) {
                //The codec was requested before it is initialized (probably a recursive codec)
                //Return an unfinished codec to prevent a stack overflow and set the codec later
                UnfinishedCodec<T> unfinishedCodec = new UnfinishedCodec<>();
                this.codecCache.put(key, unfinishedCodec);
                return unfinishedCodec;
            } else {
                return codec;
            }
        } else {
            this.codecCache.put(key, null);
            Codec<T> codec = codecSupplier.get();
            if (this.codecCache.get(key) != null) {
                //If the codec is in the cache before it is initialized it is an unfinished codec
                //Set the codec of the unfinished codec and return it
                UnfinishedCodec<T> unfinishedCodec = (UnfinishedCodec<T>) this.codecCache.get(key);
                unfinishedCodec.codec = codec;
                return unfinishedCodec;
            } else {
                this.codecCache.put(key, codec);
                return codec;
            }
        }
    }


    private static class UnfinishedCodec<T> implements Codec<T> {
        private Codec<T> codec = Codec.failing("Codec not initialized yet");

        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, T element) {
            return this.codec.serialize(converter, element);
        }

        @Override
        public <S> Result<T> deserialize(DataConverter<S> converter, S data) {
            return this.codec.deserialize(converter, data);
        }
    }

}
