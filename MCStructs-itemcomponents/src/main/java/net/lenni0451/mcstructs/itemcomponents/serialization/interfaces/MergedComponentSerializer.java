package net.lenni0451.mcstructs.itemcomponents.serialization.interfaces;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.serialization.BaseTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public interface MergedComponentSerializer<I> extends ComponentSerializer<I>, ComponentDeserializer<I> {

    default <N> MergedComponentSerializer<N> map(final Function<N, I> serializer, final Function<I, N> deserializer) {
        return new MergedComponentSerializer<N>() {
            @Override
            public <T> T serialize(DataConverter<T> converter, N component) {
                return MergedComponentSerializer.this.serialize(converter, serializer.apply(component));
            }

            @Override
            public <T> N deserialize(DataConverter<T> converter, T data) {
                return deserializer.apply(MergedComponentSerializer.this.deserialize(converter, data));
            }
        };
    }

    default <T, K> I fromMap(final DataConverter<T> converter, final Map<K, T> map, final K key) {
        if (!map.containsKey(key)) throw new IllegalStateException("Key not found in map: " + key);
        return this.deserialize(converter, map.get(key));
    }

    default <T, K> I fromMap(final DataConverter<T> converter, final Map<K, T> map, final K key, final I def) {
        if (!map.containsKey(key)) return def;
        return this.deserialize(converter, map.get(key));
    }

    default MergedComponentSerializer<I> withVerifier(final RegistryVerifier.Checker<I> verifier) {
        return this.withVerifier(verifier::verify);
    }

    default MergedComponentSerializer<I> withVerifier(final Consumer<I> verifier) {
        return new MergedComponentSerializer<I>() {
            @Override
            public <T> T serialize(DataConverter<T> converter, I component) {
                verifier.accept(component);
                return MergedComponentSerializer.this.serialize(converter, component);
            }

            @Override
            public <T> I deserialize(DataConverter<T> converter, T data) {
                I component = MergedComponentSerializer.this.deserialize(converter, data);
                verifier.accept(component);
                return component;
            }
        };
    }

    default MergedComponentSerializer<List<I>> listOf() {
        return this.listOf(Integer.MAX_VALUE);
    }

    default MergedComponentSerializer<List<I>> listOf(final int maxSize) {
        return new MergedComponentSerializer<List<I>>() {
            @Override
            public <T> T serialize(DataConverter<T> converter, List<I> component) {
                if (component.size() > maxSize) throw new IllegalStateException("List size is bigger than maximum: " + component.size() + " > " + maxSize);
                List<T> converted = new ArrayList<>();
                for (I element : component) converted.add(MergedComponentSerializer.this.serialize(converter, element));
                return converter.createList(converted);
            }

            @Override
            public <T> List<I> deserialize(DataConverter<T> converter, T data) {
                List<T> list = BaseTypes.asList(converter, data);
                if (list.size() > maxSize) throw new IllegalStateException("List size is bigger than maximum: " + list.size() + " > " + maxSize);
                List<I> converted = new ArrayList<>();
                for (T element : list) converted.add(MergedComponentSerializer.this.deserialize(converter, element));
                return converted;
            }
        };
    }

    default MergedComponentSerializer<Map<String, I>> stringMapOf() {
        return new MergedComponentSerializer<Map<String, I>>() {
            @Override
            public <T> T serialize(DataConverter<T> converter, Map<String, I> component) {
                T map = converter.emptyMap();
                for (Map.Entry<String, I> entry : component.entrySet()) {
                    converter.put(map, entry.getKey(), MergedComponentSerializer.this.serialize(converter, entry.getValue()));
                }
                return map;
            }

            @Override
            public <T> Map<String, I> deserialize(DataConverter<T> converter, T data) {
                Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
                Map<String, I> converted = new java.util.HashMap<>();
                for (Map.Entry<String, T> entry : map.entrySet()) {
                    converted.put(entry.getKey(), MergedComponentSerializer.this.deserialize(converter, entry.getValue()));
                }
                return converted;
            }
        };
    }

}
