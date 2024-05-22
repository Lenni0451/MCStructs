package net.lenni0451.mcstructs.itemcomponents.impl;

import net.lenni0451.mcstructs.converter.codec.Codec;
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
        Codec<T> codec = (Codec<T>) this.codecCache.get(key);
        if (codec != null) return codec;
        codec = codecSupplier.get();
        this.codecCache.put(key, codec);
        return codec;
    }

}
