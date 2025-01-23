package net.lenni0451.mcstructs.snbt;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.snbt.impl.SNbtDeserializer;
import net.lenni0451.mcstructs.snbt.impl.SNbtSerializer;
import net.lenni0451.mcstructs.snbt.impl.v1_12.SNbtDeserializer_v1_12;
import net.lenni0451.mcstructs.snbt.impl.v1_12.SNbtSerializer_v1_12;
import net.lenni0451.mcstructs.snbt.impl.v1_13.SNbtDeserializer_v1_13;
import net.lenni0451.mcstructs.snbt.impl.v1_14.SNbtDeserializer_v1_14;
import net.lenni0451.mcstructs.snbt.impl.v1_14.SNbtSerializer_v1_14;
import net.lenni0451.mcstructs.snbt.impl.v1_21_5.SNbtDeserializer_v1_21_5;
import net.lenni0451.mcstructs.snbt.impl.v1_7.SNbtDeserializer_v1_7;
import net.lenni0451.mcstructs.snbt.impl.v1_7.SNbtSerializer_v1_7;
import net.lenni0451.mcstructs.snbt.impl.v1_8.SNbtDeserializer_v1_8;
import net.lenni0451.mcstructs.snbt.impl.v1_8.SNbtSerializer_v1_8;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * The SNbt serializer and deserializer wrapper class.<br>
 * Use the static default fields for a specific minecraft version or create your own serializer/deserializer.
 *
 * @param <T> The output type of the deserializer
 */
public class SNbt<T extends NbtTag> {

    /**
     * The SNbt serializer for minecraft 1.7.
     */
    public static final SNbt<NbtTag> V1_7 = new SNbt<>(SNbtSerializer_v1_7::new, SNbtDeserializer_v1_7::new);
    /**
     * The SNbt serializer for minecraft 1.8 - 1.11.
     */
    public static final SNbt<CompoundTag> V1_8 = new SNbt<>(SNbtSerializer_v1_8::new, SNbtDeserializer_v1_8::new);
    /**
     * The SNbt serializer for minecraft 1.12.
     */
    public static final SNbt<CompoundTag> V1_12 = new SNbt<>(SNbtSerializer_v1_12::new, SNbtDeserializer_v1_12::new);
    /**
     * The SNbt serializer for minecraft 1.13.
     */
    public static final SNbt<CompoundTag> V1_13 = new SNbt<>(SNbtSerializer_v1_12::new, SNbtDeserializer_v1_13::new);
    /**
     * The SNbt serializer for minecraft 1.14 - 1.21.4.
     */
    public static final SNbt<CompoundTag> V1_14 = new SNbt<>(SNbtSerializer_v1_14::new, SNbtDeserializer_v1_14::new);
    /**
     * The SNbt serializer for minecraft 1.21.5+.
     */
    public static final SNbt<CompoundTag> V1_21_5 = new SNbt<>(SNbtSerializer_v1_14::new, SNbtDeserializer_v1_21_5::new);
    /**
     * The latest SNbt serializer.
     */
    public static final SNbt<CompoundTag> LATEST = V1_21_5;


    private final Supplier<SNbtSerializer> serializerSupplier;
    private final Supplier<SNbtDeserializer<T>> deserializerSupplier;
    private SNbtSerializer serializer;
    private SNbtDeserializer<T> deserializer;

    public SNbt(final Supplier<SNbtSerializer> serializerSupplier, final Supplier<SNbtDeserializer<T>> deserializerSupplier) {
        this.serializerSupplier = serializerSupplier;
        this.deserializerSupplier = deserializerSupplier;
    }

    /**
     * @return The serializer instance
     */
    public SNbtSerializer getSerializer() {
        if (this.serializer == null) this.serializer = this.serializerSupplier.get();
        return this.serializer;
    }

    /**
     * @return The deserializer instance
     */
    public SNbtDeserializer<T> getDeserializer() {
        if (this.deserializer == null) this.deserializer = this.deserializerSupplier.get();
        return this.deserializer;
    }

    /**
     * Serialize a tag to a string.
     *
     * @param tag The tag to serialize
     * @return The serialized tag
     * @throws SNbtSerializeException If the tag could not be serialized
     */
    public String serialize(final NbtTag tag) throws SNbtSerializeException {
        return this.getSerializer().serialize(tag);
    }

    /**
     * Try to serialize a tag to a string.
     *
     * @param tag The tag to serialize
     * @return The serialized tag or null if the tag could not be serialized
     */
    @Nullable
    public String trySerialize(final NbtTag tag) {
        try {
            return this.serialize(tag);
        } catch (SNbtSerializeException t) {
            return null;
        }
    }

    /**
     * Deserialize a string to a tag.
     *
     * @param s The string to deserialize
     * @return The deserialized tag
     * @throws SNbtDeserializeException If the string could not be deserialized
     */
    public T deserialize(final String s) throws SNbtDeserializeException {
        return this.getDeserializer().deserialize(s);
    }

    /**
     * Try to deserialize a string to a tag.
     *
     * @param s The string to deserialize
     * @return The deserialized tag or null if the string could not be deserialized
     */
    @Nullable
    public T tryDeserialize(final String s) {
        try {
            return this.deserialize(s);
        } catch (SNbtDeserializeException t) {
            return null;
        }
    }

}
