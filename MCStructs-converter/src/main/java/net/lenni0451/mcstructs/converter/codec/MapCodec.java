package net.lenni0451.mcstructs.converter.codec;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.Result;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MapCodec<K, V> {

    public static <K, V> MapCodec<K, V> of(final K key, final Codec<K> keyCodec, final Codec<V> valueCodec) {
        return new MapCodec<>(key, keyCodec, valueCodec);
    }


    private final K key;
    private final Codec<K> keyCodec;
    private final Codec<V> valueCodec;
    private boolean lenient;
    private Supplier<V> defaultValue;
    private Predicate<V> skipDefault;

    private MapCodec(final K key, final Codec<K> keyCodec, final Codec<V> valueCodec) {
        this.key = key;
        this.keyCodec = keyCodec;
        this.valueCodec = valueCodec;
    }

    /**
     * Set the field to be lenient.<br>
     * If the field is present in the map but fails to deserialize it will be ignored instead of failing the whole deserialization process.
     *
     * @return The map codec
     */
    public MapCodec<K, V> lenient() {
        this.lenient = true;
        return this;
    }

    /**
     * Set the default value for the value codec if the value is not present in the map.<br>
     * If the value is equal to the default value it will not be serialized.
     *
     * @param defaultValue The default value
     * @return The map codec
     */
    public MapCodec<K, V> optionalDefault(final Supplier<V> defaultValue) {
        this.defaultValue = defaultValue;
        this.skipDefault = v -> Objects.equals(v, this.defaultValue.get());
        return this;
    }

    /**
     * Set the default value for the value codec if the value is not present in the map.<br>
     * The <b>required</b> only has an effect on the serialization process.
     * If the value is equal to the default value it will still be serialized and put into the map unlike {@link #optionalDefault(Supplier)} which skips the serialization.<br>
     * The deserialization will still return the default value if the key is missing.
     *
     * @param defaultValue The default value
     * @return The map codec
     */
    public MapCodec<K, V> requiredDefault(final Supplier<V> defaultValue) {
        this.defaultValue = defaultValue;
        this.skipDefault = v -> false;
        return this;
    }

    /**
     * Set the default value for the value codec if the value is not present in the map.<br>
     * This behaves like {@link #optionalDefault(Supplier)} if the predicate returns true and like {@link #requiredDefault(Supplier)} if the predicate returns false.
     *
     * @param defaultValue The default value
     * @param skipDefault  If the value should not be serialized
     * @return The map codec
     */
    public MapCodec<K, V> defaulted(final Supplier<V> defaultValue, final Predicate<V> skipDefault) {
        this.defaultValue = defaultValue;
        this.skipDefault = skipDefault;
        return this;
    }

    public <T> Result<T> serialize(final DataConverter<T> converter, final Map<T, T> map, final V value) {
        Result<T> serializedKey = this.keyCodec.serialize(converter, this.key);
        if (serializedKey.isError()) return serializedKey.mapError();
        Result<T> serializedValue = this.valueCodec.serialize(converter, value);
        if (serializedValue.isError()) {
            if (this.lenient) return Result.success(null);
            else return serializedValue;
        }

        if (this.defaultValue != null && this.skipDefault.test(value)) return serializedValue;
        map.put(serializedKey.get(), serializedValue.get());
        return serializedValue;
    }

    public <T> Result<V> deserialize(final DataConverter<T> converter, final Map<T, T> map) {
        Result<T> key = this.keyCodec.serialize(converter, this.key);
        if (key.isError()) return key.mapError();

        T value = map.get(key.get());
        if (value == null) {
            if (this.defaultValue == null) return Result.error("Key not found in map: " + key.get());
            else return Result.success(this.defaultValue.get());
        } else {
            Result<V> deserializedValue = this.valueCodec.deserialize(converter, value);
            if (deserializedValue.isError() && this.lenient) return Result.success(this.defaultValue.get());
            return deserializedValue;
        }
    }

}
