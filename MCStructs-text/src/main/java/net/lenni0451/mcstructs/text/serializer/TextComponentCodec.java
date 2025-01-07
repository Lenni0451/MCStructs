package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.subtypes.ITextComponentSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.json.JsonHoverEventSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.json.JsonStyleSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.json.JsonTextSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt.NbtHoverEventSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt.NbtStyleSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt.NbtTextSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.TextComponentCodec_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_21_2.TextComponentCodec_v1_21_2;
import net.lenni0451.mcstructs.text.serializer.v1_21_4.TextComponentCodec_v1_21_4;

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
@SuppressWarnings("StaticInitializerReferencesSubClass")
public class TextComponentCodec {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    /**
     * The text codec for 1.20.3.
     */
    public static final TextComponentCodec V1_20_3 = new TextComponentCodec(
            () -> SNbt.V1_14,
            (codec, sNbtSerializer) -> new JsonTextSerializer_v1_20_3(textSerializer -> new JsonStyleSerializer_v1_20_3(styleSerializer -> new JsonHoverEventSerializer_v1_20_3(codec, textSerializer, sNbtSerializer))),
            (codec, sNbtSerializer) -> new NbtTextSerializer_v1_20_3(textSerializer -> new NbtStyleSerializer_v1_20_3(styleSerializer -> new NbtHoverEventSerializer_v1_20_3(codec, textSerializer, sNbtSerializer)))
    );
    /**
     * The text codec for 1.20.5.<br>
     * <b>If you have access to minecraft data, it is recommended to implement the {@link TextComponentCodec_v1_20_5} class yourself instead of using this codec.</b>
     */
    public static final TextComponentCodec_v1_20_5 V1_20_5 = new TextComponentCodec_v1_20_5();
    /**
     * The text codec for 1.21.2.<br>
     * <b>If you have access to minecraft data, it is recommended to implement the {@link TextComponentCodec_v1_21_2} class yourself instead of using this codec.</b>
     */
    public static final TextComponentCodec_v1_21_2 V1_21_2 = new TextComponentCodec_v1_21_2();
    /**
     * The text codec for 1.21.4.<br>
     * <b>If you have access to minecraft data, it is recommended to implement the {@link TextComponentCodec_v1_21_4} class yourself instead of using this codec.</b>
     */
    public static final TextComponentCodec_v1_21_4 V1_21_4 = new TextComponentCodec_v1_21_4();
    /**
     * The latest text codec.
     */
    public static final TextComponentCodec LATEST = V1_21_4;


    private final Supplier<SNbt<CompoundTag>> sNbtSerializerSupplier;
    private final BiFunction<TextComponentCodec, SNbt<CompoundTag>, ITextComponentSerializer<JsonElement>> jsonSerializerSupplier;
    private final BiFunction<TextComponentCodec, SNbt<CompoundTag>, ITextComponentSerializer<NbtTag>> nbtSerializerSupplier;
    private SNbt<CompoundTag> sNbt;
    private ITextComponentSerializer<JsonElement> jsonSerializer;
    private ITextComponentSerializer<NbtTag> nbtSerializer;

    public TextComponentCodec(final Supplier<SNbt<CompoundTag>> sNbtSerializerSupplier, final BiFunction<TextComponentCodec, SNbt<CompoundTag>, ITextComponentSerializer<JsonElement>> jsonSerializerSupplier, final BiFunction<TextComponentCodec, SNbt<CompoundTag>, ITextComponentSerializer<NbtTag>> nbtSerializerSupplier) {
        this.sNbtSerializerSupplier = sNbtSerializerSupplier;
        this.jsonSerializerSupplier = jsonSerializerSupplier;
        this.nbtSerializerSupplier = nbtSerializerSupplier;
    }

    /**
     * @return The used snbt serializer
     */
    private SNbt<CompoundTag> getSNbtSerializer() {
        if (this.sNbt == null) this.sNbt = this.sNbtSerializerSupplier.get();
        return this.sNbtSerializerSupplier.get();
    }

    /**
     * @return The used json serializer/deserializer
     */
    public ITextComponentSerializer<JsonElement> getJsonSerializer() {
        if (this.jsonSerializer == null) this.jsonSerializer = this.jsonSerializerSupplier.apply(this, this.getSNbtSerializer());
        return this.jsonSerializer;
    }

    /**
     * @return The used nbt serializer/deserializer
     */
    public ITextComponentSerializer<NbtTag> getNbtSerializer() {
        if (this.nbtSerializer == null) this.nbtSerializer = this.nbtSerializerSupplier.apply(this, this.getSNbtSerializer());
        return this.nbtSerializer;
    }

    /**
     * Deserialize a text component from a json string.
     *
     * @param json The json string
     * @return The deserialized text component
     */
    public TextComponent deserializeJson(final String json) {
        return this.deserializeJsonTree(JsonParser.parseString(json));
    }

    /**
     * Deserialize a text component from a json string.<br>
     * A {@link JsonReader} will be used to parse the json string.
     *
     * @param json The json string
     * @return The deserialized text component
     */
    public TextComponent deserializeJsonReader(final String json) {
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
    public TextComponent deserializeLenientJson(final String json) {
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
    public TextComponent deserializeNbt(final String nbt) {
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
    public TextComponent deserializeJsonTree(@Nullable final JsonElement element) {
        if (element == null) return null;
        return this.deserialize(element);
    }

    /**
     * Deserialize a text component from a {@link NbtTag}.
     *
     * @param nbt The nbt tag
     * @return The deserialized text component
     */
    public TextComponent deserializeNbtTree(@Nullable final NbtTag nbt) {
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
    public TextComponent deserialize(final JsonElement json) {
        return this.getJsonSerializer().deserialize(json);
    }

    /**
     * Deserialize a text component from a {@link NbtTag}.<br>
     * This method does not check for null values.
     *
     * @param nbt The nbt tag
     * @return The deserialized text component
     */
    public TextComponent deserialize(final NbtTag nbt) {
        return this.getNbtSerializer().deserialize(nbt);
    }

    /**
     * Serialize a text component to a {@link JsonElement}.
     *
     * @param component The text component
     * @return The serialized json element
     */
    public JsonElement serializeJsonTree(final TextComponent component) {
        return this.getJsonSerializer().serialize(component);
    }

    /**
     * Serialize a text component to a {@link NbtTag}.
     *
     * @param component The text component
     * @return The serialized nbt tag
     */
    public NbtTag serializeNbt(final TextComponent component) {
        return this.getNbtSerializer().serialize(component);
    }

    /**
     * Serialize a text component to a json string.
     *
     * @param component The text component
     * @return The serialized json string
     */
    public String serializeJsonString(final TextComponent component) {
        return GSON.toJson(this.serializeJsonTree(component));
    }

    /**
     * Serialize a text component to a nbt string.
     *
     * @param component The text component
     * @return The serialized nbt string
     */
    public String serializeNbtString(final TextComponent component) {
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
                .registerTypeHierarchyAdapter(TextComponent.class, (JsonSerializer<TextComponent>) (src, typeOfSrc, context) -> this.serializeJsonTree(src))
                .registerTypeHierarchyAdapter(TextComponent.class, (JsonDeserializer<TextComponent>) (src, typeOfSrc, context) -> this.deserializeJsonTree(src))
                .disableHtmlEscaping()
                .create());
    }

}
