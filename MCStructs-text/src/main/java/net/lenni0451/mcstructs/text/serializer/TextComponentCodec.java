package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.JsonConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.impl.v1_20_5.JsonConverter_v1_20_5;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.subtypes.ITextComponentSerializer;
import net.lenni0451.mcstructs.text.serializer.subtypes.adapter.CodecTextComponentSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.StyleCodecs_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.TextCodecs_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.StyleCodecs_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.TextCodecs_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.TextComponentCodec_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_21_2.StyleCodecs_v1_21_2;
import net.lenni0451.mcstructs.text.serializer.v1_21_2.TextCodecs_v1_21_2;
import net.lenni0451.mcstructs.text.serializer.v1_21_2.TextComponentCodec_v1_21_2;
import net.lenni0451.mcstructs.text.serializer.v1_21_4.TextComponentCodec_v1_21_4;
import net.lenni0451.mcstructs.text.serializer.v1_21_5.StyleCodecs_v1_21_5;
import net.lenni0451.mcstructs.text.serializer.v1_21_5.TextCodecs_v1_21_5;
import net.lenni0451.mcstructs.text.serializer.verify.TextVerifier;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.StringReader;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * The text component serializer and deserializer wrapper class for multiple types of input and output.<br>
 * Use the static default fields for a specific minecraft version or create your own serializer/deserializer.<br>
 * <br>
 * This class will now be used for implementations of new minecraft versions (1.20.3+) instead of the {@link net.lenni0451.mcstructs.text.serializer.TextComponentSerializer} class because components can now be serialized to nbt.<br>
 * Backwards compatibility is supported through the {@link #asSerializer()} method. The fields in {@link net.lenni0451.mcstructs.text.serializer.TextComponentSerializer} will still be updated using this wrapper method.
 */
@ParametersAreNonnullByDefault
@SuppressWarnings("StaticInitializerReferencesSubClass")
public class TextComponentCodec {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    /**
     * The text codec for 1.20.3.
     */
    public static final TextComponentCodec V1_20_3 = new TextComponentCodec(() -> SNbt.V1_14, () -> TextCodecs_v1_20_3.TEXT, () -> StyleCodecs_v1_20_3.CODEC, JsonConverter_v1_20_3.INSTANCE, NbtConverter_v1_20_3.INSTANCE);
    /**
     * The text codec for 1.20.5.<br>
     * <b>If you have access to minecraft data, it is recommended to implement the {@link TextComponentCodec_v1_20_5} class yourself instead of using this codec.</b>
     */
    public static final TextComponentCodec V1_20_5 = new TextComponentCodec(() -> SNbt.V1_14, () -> TextCodecs_v1_20_5.TEXT, () -> StyleCodecs_v1_20_5.CODEC, JsonConverter_v1_20_5.INSTANCE, NbtConverter_v1_20_3.INSTANCE);
    /**
     * The text codec for 1.21.2.<br>
     * <b>If you have access to minecraft data, it is recommended to implement the {@link TextComponentCodec_v1_21_2} class yourself instead of using this codec.</b>
     */
    public static final TextComponentCodec V1_21_2 = new TextComponentCodec(() -> SNbt.V1_14, () -> TextCodecs_v1_21_2.TEXT, () -> StyleCodecs_v1_21_2.CODEC, JsonConverter_v1_20_5.INSTANCE, NbtConverter_v1_20_3.INSTANCE);
    /**
     * The text codec for 1.21.4.<br>
     * <b>If you have access to minecraft data, it is recommended to implement the {@link TextComponentCodec_v1_21_4} class yourself instead of using this codec.</b>
     */
    public static final TextComponentCodec_v1_21_4 V1_21_4 = new TextComponentCodec_v1_21_4();
    /**
     * The text codec for 1.21.5.<br>
     * TODO: Access to minecraft data
     */
    public static final TextComponentCodec V1_21_5 = new TextComponentCodec(() -> SNbt.V1_14, () -> TextCodecs_v1_21_5.TEXT, () -> StyleCodecs_v1_21_5.CODEC, JsonConverter_v1_20_5.INSTANCE, NbtConverter_v1_20_3.INSTANCE);
    /**
     * The latest text codec.
     */
    public static final TextComponentCodec LATEST = V1_21_5;


    private final Supplier<SNbt<CompoundTag>> sNbtSupplier;
    private final BiFunction<TextComponentCodec, SNbt<CompoundTag>, ITextComponentSerializer<JsonElement>> jsonSerializerSupplier;
    private final BiFunction<TextComponentCodec, SNbt<CompoundTag>, ITextComponentSerializer<NbtTag>> nbtSerializerSupplier;
    private SNbt<CompoundTag> sNbt;
    private ITextComponentSerializer<JsonElement> jsonSerializer;
    private ITextComponentSerializer<NbtTag> nbtSerializer;

    public TextComponentCodec(final Supplier<SNbt<CompoundTag>> sNbtSupplier, final Supplier<Codec<TextComponent>> textCodec, final Supplier<Codec<Style>> styleCodec, final DataConverter<JsonElement> jsonConverter, final DataConverter<NbtTag> nbtConverter) {
        this.sNbtSupplier = sNbtSupplier;
        this.jsonSerializerSupplier = (thiz, sNbt) -> new CodecTextComponentSerializer<>(textCodec.get(), jsonConverter, styleCodec.get());
        this.nbtSerializerSupplier = (thiz, sNbt) -> new CodecTextComponentSerializer<>(textCodec.get(), nbtConverter, styleCodec.get());
    }

    public TextComponentCodec(final Supplier<SNbt<CompoundTag>> sNbtSupplier, final BiFunction<TextComponentCodec, SNbt<CompoundTag>, ITextComponentSerializer<JsonElement>> jsonSerializerSupplier, final BiFunction<TextComponentCodec, SNbt<CompoundTag>, ITextComponentSerializer<NbtTag>> nbtSerializerSupplier) {
        this.sNbtSupplier = sNbtSupplier;
        this.jsonSerializerSupplier = jsonSerializerSupplier;
        this.nbtSerializerSupplier = nbtSerializerSupplier;
    }

    public TextComponentCodec withVerifier(final TextVerifier verifier) {
        return null; //new TextComponentCodec(this, verifier); TODO
    }

    /**
     * @return The used snbt serializer
     */
    private SNbt<CompoundTag> getSNbtSerializer() {
        if (this.sNbt == null) this.sNbt = this.sNbtSupplier.get();
        return this.sNbtSupplier.get();
    }

    /**
     * @return The used json serializer/deserializer
     */
    @Deprecated
    public ITextComponentSerializer<JsonElement> getJsonSerializer() {
        if (this.jsonSerializer == null) this.jsonSerializer = this.jsonSerializerSupplier.apply(this, this.getSNbtSerializer());
        return this.jsonSerializer;
    }

    /**
     * @return The used nbt serializer/deserializer
     */
    @Deprecated
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
     * @return A wrapper for this codec to use it as a {@link net.lenni0451.mcstructs.text.serializer.TextComponentSerializer}.
     */
    public net.lenni0451.mcstructs.text.serializer.TextComponentSerializer asSerializer() {
        return new net.lenni0451.mcstructs.text.serializer.TextComponentSerializer(this, () -> new GsonBuilder()
                .registerTypeHierarchyAdapter(TextComponent.class, (JsonSerializer<TextComponent>) (src, typeOfSrc, context) -> this.serializeJsonTree(src))
                .registerTypeHierarchyAdapter(TextComponent.class, (JsonDeserializer<TextComponent>) (src, typeOfSrc, context) -> this.deserializeJsonTree(src))
                .disableHtmlEscaping()
                .create());
    }

}
