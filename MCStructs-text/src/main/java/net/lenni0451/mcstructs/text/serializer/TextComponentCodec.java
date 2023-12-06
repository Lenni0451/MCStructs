package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.json.JsonTextSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt.NbtTextSerializer_v1_20_3;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.StringReader;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * The text component serializer and deserializer wrapper class for multiple types of input and output.<br>
 * Use the static default fields for a specific minecraft version or create your own serializer/deserializer.<br>
 * <br>
 * This class will now be used for implementations of new minecraft versions (1.20.3+) instead of the {@link TextComponentSerializer} class because components can now be serialized to nbt.<br>
 * Backwards compatibility is supported through the {@link #asSerializer()} method. The fields in {@link TextComponentSerializer} will still be updated using this wrapper method.
 */
@ParametersAreNonnullByDefault
public class TextComponentCodec {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    /**
     * The text codec for 1.20.3.
     */
    public static final TextComponentCodec V1_20_3 = new TextComponentCodec(
            () -> SNbtSerializer.V1_14,
            JsonTextSerializer_v1_20_3::new,
            NbtTextSerializer_v1_20_3::new
    );
    /**
     * The latest text codec.
     */
    public static final TextComponentCodec LATEST = V1_20_3;


    private final Supplier<SNbtSerializer<CompoundTag>> sNbtSerializerSupplier;
    private final BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITypedSerializer<JsonElement, ATextComponent>> jsonSerializerSupplier;
    private final BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITypedSerializer<INbtTag, ATextComponent>> nbtSerializerSupplier;
    private SNbtSerializer<CompoundTag> sNbtSerializer;
    private ITypedSerializer<JsonElement, ATextComponent> jsonSerializer;
    private ITypedSerializer<INbtTag, ATextComponent> nbtSerializer;

    public TextComponentCodec(final Supplier<SNbtSerializer<CompoundTag>> sNbtSerializerSupplier, final BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITypedSerializer<JsonElement, ATextComponent>> jsonSerializerSupplier, final BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITypedSerializer<INbtTag, ATextComponent>> nbtSerializerSupplier) {
        this.sNbtSerializerSupplier = sNbtSerializerSupplier;
        this.jsonSerializerSupplier = jsonSerializerSupplier;
        this.nbtSerializerSupplier = nbtSerializerSupplier;
    }

    /**
     * @return The used snbt serializer
     */
    private SNbtSerializer<CompoundTag> getSNbtSerializer() {
        if (this.sNbtSerializer == null) this.sNbtSerializer = this.sNbtSerializerSupplier.get();
        return this.sNbtSerializerSupplier.get();
    }

    /**
     * @return The used json serializer/deserializer
     */
    public ITypedSerializer<JsonElement, ATextComponent> getJsonSerializer() {
        if (this.jsonSerializer == null) this.jsonSerializer = this.jsonSerializerSupplier.apply(this, this.getSNbtSerializer());
        return this.jsonSerializer;
    }

    /**
     * @return The used nbt serializer/deserializer
     */
    public ITypedSerializer<INbtTag, ATextComponent> getNbtSerializer() {
        if (this.nbtSerializer == null) this.nbtSerializer = this.nbtSerializerSupplier.apply(this, this.getSNbtSerializer());
        return this.nbtSerializer;
    }

    /**
     * Deserialize a text component from a json string.
     *
     * @param json The json string
     * @return The deserialized text component
     */
    public ATextComponent deserializeJson(final String json) {
        return this.deserializeJsonTree(JsonParser.parseString(json));
    }

    /**
     * Deserialize a text component from a json string.<br>
     * A {@link JsonReader} will be used to parse the json string.
     *
     * @param json The json string
     * @return The deserialized text component
     */
    public ATextComponent deserializeJsonReader(final String json) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(false);
        try {
            return this.deserialize(Streams.parse(reader));
        } catch (StackOverflowError e) {
            throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
        }
    }

    /**
     * Deserialize a text component from a json string.<br>
     * A {@link JsonReader} will be used to parse the json string.
     *
     * @param json The json string
     * @return The deserialized text component
     */
    public ATextComponent deserializeLenientJson(final String json) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return this.deserializeJsonTree(JsonParser.parseReader(reader));
    }

    /**
     * Deserialize a text component from a nbt string.
     *
     * @param nbt The nbt string
     * @return The deserialized text component
     */
    public ATextComponent deserializeNbt(final String nbt) {
        try {
            return this.deserialize(this.getSNbtSerializer().getDeserializer().deserializeValue(nbt));
        } catch (SNbtDeserializeException e) {
            throw new RuntimeException("Failed to deserialize SNbt", e);
        }
    }

    /**
     * Deserialize a text component from a {@link JsonElement}.
     *
     * @param element The json element
     * @return The deserialized text component
     */
    public ATextComponent deserializeJsonTree(@Nullable final JsonElement element) {
        if (element == null) return null;
        return this.deserialize(element);
    }

    /**
     * Deserialize a text component from a {@link INbtTag}.
     *
     * @param nbt The nbt tag
     * @return The deserialized text component
     */
    public ATextComponent deserializeNbtTree(@Nullable final INbtTag nbt) {
        if (nbt == null) return null;
        return this.deserialize(nbt);
    }

    /**
     * Deserialize a text component from a {@link JsonElement}.<br>
     * This method does not check for null values.
     *
     * @param json The json element
     * @return The deserialized text component
     */
    public ATextComponent deserialize(final JsonElement json) {
        return this.getJsonSerializer().deserialize(json);
    }

    /**
     * Deserialize a text component from a {@link INbtTag}.<br>
     * This method does not check for null values.
     *
     * @param nbt The nbt tag
     * @return The deserialized text component
     */
    public ATextComponent deserialize(final INbtTag nbt) {
        return this.getNbtSerializer().deserialize(nbt);
    }

    /**
     * Serialize a text component to a {@link JsonElement}.
     *
     * @param component The text component
     * @return The serialized json element
     */
    public JsonElement serializeJsonTree(final ATextComponent component) {
        return this.getJsonSerializer().serialize(component);
    }

    /**
     * Serialize a text component to a {@link INbtTag}.
     *
     * @param component The text component
     * @return The serialized nbt tag
     */
    public INbtTag serializeNbt(final ATextComponent component) {
        return this.getNbtSerializer().serialize(component);
    }

    /**
     * Serialize a text component to a json string.
     *
     * @param component The text component
     * @return The serialized json string
     */
    public String serializeJsonString(final ATextComponent component) {
        return GSON.toJson(this.serializeJsonTree(component));
    }

    /**
     * Serialize a text component to a nbt string.
     *
     * @param component The text component
     * @return The serialized nbt string
     */
    public String serializeNbtString(final ATextComponent component) {
        try {
            return this.getSNbtSerializer().serialize(this.serializeNbt(component));
        } catch (SNbtSerializeException e) {
            throw new RuntimeException("Failed to serialize SNbt", e);
        }
    }

    /**
     * @return A wrapper for this codec to use it as a {@link TextComponentSerializer}.
     */
    public TextComponentSerializer asSerializer() {
        return new TextComponentSerializer(this, () -> new GsonBuilder()
                .registerTypeHierarchyAdapter(ATextComponent.class, (JsonSerializer<ATextComponent>) (src, typeOfSrc, context) -> this.serializeJsonTree(src))
                .registerTypeHierarchyAdapter(ATextComponent.class, (JsonDeserializer<ATextComponent>) (src, typeOfSrc, context) -> this.deserializeJsonTree(src))
                .disableHtmlEscaping()
                .create());
    }

}
