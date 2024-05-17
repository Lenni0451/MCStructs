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
        return (Codec<T>) this.codecCache.computeIfAbsent(key, k -> codecSupplier.get());
    }

}
