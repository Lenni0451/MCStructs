package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;
import net.lenni0451.mcstructs.text.serializer.v1_12.TextDeserializer_v1_12;
import net.lenni0451.mcstructs.text.serializer.v1_12.TextSerializer_v1_12;
import net.lenni0451.mcstructs.text.serializer.v1_14.TextDeserializer_v1_14;
import net.lenni0451.mcstructs.text.serializer.v1_14.TextSerializer_v1_14;
import net.lenni0451.mcstructs.text.serializer.v1_15.TextDeserializer_v1_15;
import net.lenni0451.mcstructs.text.serializer.v1_15.TextSerializer_v1_15;
import net.lenni0451.mcstructs.text.serializer.v1_16.*;
import net.lenni0451.mcstructs.text.serializer.v1_17.TextDeserializer_v1_17;
import net.lenni0451.mcstructs.text.serializer.v1_17.TextSerializer_v1_17;
import net.lenni0451.mcstructs.text.serializer.v1_18.HoverEventDeserializer_v1_18;
import net.lenni0451.mcstructs.text.serializer.v1_19_4.TextDeserializer_v1_19_4;
import net.lenni0451.mcstructs.text.serializer.v1_19_4.TextSerializer_v1_19_4;
import net.lenni0451.mcstructs.text.serializer.v1_6.TextDeserializer_v1_6;
import net.lenni0451.mcstructs.text.serializer.v1_6.TextSerializer_v1_6;
import net.lenni0451.mcstructs.text.serializer.v1_7.StyleDeserializer_v1_7;
import net.lenni0451.mcstructs.text.serializer.v1_7.StyleSerializer_v1_7;
import net.lenni0451.mcstructs.text.serializer.v1_7.TextDeserializer_v1_7;
import net.lenni0451.mcstructs.text.serializer.v1_7.TextSerializer_v1_7;
import net.lenni0451.mcstructs.text.serializer.v1_8.StyleDeserializer_v1_8;
import net.lenni0451.mcstructs.text.serializer.v1_8.StyleSerializer_v1_8;
import net.lenni0451.mcstructs.text.serializer.v1_8.TextDeserializer_v1_8;
import net.lenni0451.mcstructs.text.serializer.v1_8.TextSerializer_v1_8;
import net.lenni0451.mcstructs.text.serializer.v1_9.TextSerializer_v1_9;
import net.lenni0451.mcstructs.text.utils.LegacyGson;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.StringReader;
import java.util.function.Supplier;

/**
 * The text component serializer and deserializer wrapper class.<br>
 * Use the static default fields for a specific minecraft version or create your own serializer/deserializer.
 */
public class TextComponentSerializer {

    /**
     * The text component serializer for 1.6.<br>
     * Use the {@link #deserialize(String)} and {@link #deserialize(JsonElement)} methods for vanilla like deserialization.
     */
    public static final TextComponentSerializer V1_6 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_6())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_6())
            .create(), true);
    /**
     * The text component serializer for 1.7.<br>
     * Use the {@link #deserialize(String)} and {@link #deserialize(JsonElement)} methods for vanilla like deserialization.
     */
    public static final TextComponentSerializer V1_7 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_7())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_7())
            .registerTypeAdapter(Style.class, new StyleDeserializer_v1_7())
            .registerTypeAdapter(Style.class, new StyleSerializer_v1_7(TextComponentSerializer.V1_7, SNbtSerializer.V1_7))
            .create(), true);
    /**
     * The text component serializer for 1.8.<br>
     * Use the {@link #deserialize(String)} and {@link #deserialize(JsonElement)} methods for vanilla like deserialization.
     */
    public static final TextComponentSerializer V1_8 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_8())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_8())
            .registerTypeAdapter(Style.class, new StyleDeserializer_v1_8())
            .registerTypeAdapter(Style.class, new StyleSerializer_v1_8(TextComponentSerializer.V1_8, SNbtSerializer.V1_8))
            .create(), true);
    /**
     * The text component serializer for 1.9 - 1.11.<br>
     * Use the {@link #deserializeReader(String)} method for vanilla like deserialization.
     */
    public static final TextComponentSerializer V1_9 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_9())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_8())
            .registerTypeAdapter(Style.class, new StyleDeserializer_v1_8())
            .registerTypeAdapter(Style.class, new StyleSerializer_v1_8(TextComponentSerializer.V1_9, SNbtSerializer.V1_8))
            .create(), true);
    /**
     * The text component serializer for 1.12 - 1.13.<br>
     * Use the {@link #deserializeReader(String)} method for vanilla like deserialization.
     */
    public static final TextComponentSerializer V1_12 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_12())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_12())
            .registerTypeAdapter(Style.class, new StyleDeserializer_v1_8())
            .registerTypeAdapter(Style.class, new StyleSerializer_v1_8(TextComponentSerializer.V1_12, SNbtSerializer.V1_12))
            .create());
    /**
     * The text component serializer for 1.14.<br>
     * Use the {@link #deserializeReader(String)} method for vanilla like deserialization.
     */
    public static final TextComponentSerializer V1_14 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_14())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_14())
            .registerTypeAdapter(Style.class, new StyleDeserializer_v1_8())
            .registerTypeAdapter(Style.class, new StyleSerializer_v1_8(TextComponentSerializer.V1_14, SNbtSerializer.V1_14))
            .disableHtmlEscaping()
            .create());
    /**
     * The text component serializer for 1.15.<br>
     * Use the {@link #deserializeReader(String)} method for vanilla like deserialization.
     */
    public static final TextComponentSerializer V1_15 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_15())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_15())
            .registerTypeAdapter(Style.class, new StyleDeserializer_v1_8())
            .registerTypeAdapter(Style.class, new StyleSerializer_v1_8(TextComponentSerializer.V1_15, SNbtSerializer.V1_14))
            .disableHtmlEscaping()
            .create());
    /**
     * The text component serializer for 1.16.<br>
     * Use the {@link #deserializeReader(String)} method for vanilla like deserialization.
     */
    public static final TextComponentSerializer V1_16 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_16())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_16())
            .registerTypeAdapter(Style.class, new StyleDeserializer_v1_16())
            .registerTypeAdapter(Style.class, new StyleSerializer_v1_16())
            .registerTypeHierarchyAdapter(AHoverEvent.class, new HoverEventDeserializer_v1_16(TextComponentSerializer.V1_16, SNbtSerializer.V1_14))
            .registerTypeHierarchyAdapter(AHoverEvent.class, new HoverEventSerializer_v1_16(TextComponentSerializer.V1_16, SNbtSerializer.V1_14))
            .disableHtmlEscaping()
            .create());
    /**
     * The text component serializer for 1.17.<br>
     * Use the {@link #deserializeReader(String)} method for vanilla like deserialization.
     */
    public static final TextComponentSerializer V1_17 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_17())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_17())
            .registerTypeAdapter(Style.class, new StyleDeserializer_v1_16())
            .registerTypeAdapter(Style.class, new StyleSerializer_v1_16())
            .registerTypeHierarchyAdapter(AHoverEvent.class, new HoverEventDeserializer_v1_16(TextComponentSerializer.V1_17, SNbtSerializer.V1_14))
            .registerTypeHierarchyAdapter(AHoverEvent.class, new HoverEventSerializer_v1_16(TextComponentSerializer.V1_17, SNbtSerializer.V1_14))
            .disableHtmlEscaping()
            .create());
    /**
     * The text component serializer for 1.18 - 1.19.<br>
     * Use the {@link #deserializeReader(String)} method for vanilla like deserialization.
     */
    public static final TextComponentSerializer V1_18 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_17())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_17())
            .registerTypeAdapter(Style.class, new StyleDeserializer_v1_16())
            .registerTypeAdapter(Style.class, new StyleSerializer_v1_16())
            .registerTypeHierarchyAdapter(AHoverEvent.class, new HoverEventDeserializer_v1_18(TextComponentSerializer.V1_18, SNbtSerializer.V1_14))
            .registerTypeHierarchyAdapter(AHoverEvent.class, new HoverEventSerializer_v1_16(TextComponentSerializer.V1_18, SNbtSerializer.V1_14))
            .disableHtmlEscaping()
            .create());
    /**
     * The text component serializer for 1.19.4.<br>
     * Use the {@link #deserializeReader(String)} method for vanilla like deserialization.
     */
    public static final TextComponentSerializer V1_19_4 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_19_4())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_19_4())
            .registerTypeAdapter(Style.class, new StyleDeserializer_v1_16())
            .registerTypeAdapter(Style.class, new StyleSerializer_v1_16())
            .registerTypeHierarchyAdapter(AHoverEvent.class, new HoverEventDeserializer_v1_18(TextComponentSerializer.V1_19_4, SNbtSerializer.V1_14))
            .registerTypeHierarchyAdapter(AHoverEvent.class, new HoverEventSerializer_v1_16(TextComponentSerializer.V1_19_4, SNbtSerializer.V1_14))
            .disableHtmlEscaping()
            .create());
    /**
     * The text component serializer for 1.20.3.
     */
    public static final TextComponentSerializer V1_20_3 = TextComponentCodec.V1_20_3.asSerializer();
    /**
     * The text component serializer for 1.20.5.
     */
    public static final TextComponentSerializer V1_20_5 = TextComponentCodec.V1_20_5.asSerializer();
    /**
     * The latest text component serializer.
     */
    public static final TextComponentSerializer LATEST = V1_20_5;


    private final TextComponentCodec parentCodec;
    private final Supplier<Gson> gsonSupplier;
    private final boolean legacyGson;
    private Gson gson;

    public TextComponentSerializer(final Supplier<Gson> gsonSupplier) {
        this(gsonSupplier, false);
    }

    public TextComponentSerializer(final Supplier<Gson> gsonSupplier, final boolean legacyGson) {
        this.parentCodec = null;
        this.gsonSupplier = gsonSupplier;
        this.legacyGson = legacyGson;
    }

    public TextComponentSerializer(final TextComponentCodec parentCodec, final Supplier<Gson> gsonSupplier) {
        this.parentCodec = parentCodec;
        this.gsonSupplier = gsonSupplier;
        this.legacyGson = false;
    }

    /**
     * @return The parent codec of this serializer if it was created using {@link TextComponentCodec#asSerializer()}
     */
    @Nullable
    public TextComponentCodec getParentCodec() {
        return this.parentCodec;
    }

    /**
     * @return If this serializer has a parent codec
     */
    public boolean isCodec() {
        return this.parentCodec != null;
    }

    /**
     * @return The used gson instance
     */
    public Gson getGson() {
        if (this.gson == null) this.gson = this.gsonSupplier.get();
        return this.gson;
    }

    /**
     * Serialize a text component to a json string.
     *
     * @param component The component to serialize
     * @return The json string
     */
    public String serialize(final ATextComponent component) {
        return this.getGson().toJson(component);
    }

    /**
     * Serialize a text component to a {@link JsonElement}.
     *
     * @param component The component to serialize
     * @return The json element
     */
    public JsonElement serializeJson(final ATextComponent component) {
        return this.getGson().toJsonTree(component);
    }

    /**
     * Deserialize a text component from a json string.<br>
     * This method is used by minecraft versions 1.6 - 1.8.<br>
     * It can deserialize components with comments which newer versions did not support.<br>
     * <br>
     * <b>Example:</b><br>
     * <code>{"text":/*comment*{@literal /}"Hello World"}</code>
     *
     * @param json The json string
     * @return The deserialized text component
     */
    public ATextComponent deserialize(String json) {
        if (this.legacyGson) {
            LegacyGson.checkStartingType(json, true);
            json = LegacyGson.fixInvalidEscapes(json);
        }
        return this.getGson().fromJson(json, ATextComponent.class);
    }

    /**
     * Deserialize a text component from a {@link JsonElement}.<br>
     * This method is used by minecraft versions 1.6 - 1.8.
     *
     * @param element The json element
     * @return The deserialized text component
     */
    public ATextComponent deserialize(final JsonElement element) {
        return this.getGson().fromJson(element, ATextComponent.class);
    }

    /**
     * Deserialize a text component from a json string.<br>
     * This method is used by minecraft versions 1.9 - 1.20.2.<br>
     * It can not deserialize components with comments which older versions did support.
     *
     * @param json The json string
     * @return The deserialized text component
     */
    public ATextComponent deserializeReader(final String json) {
        return this.deserializeReader(json, false);
    }

    /**
     * Deserialize a text component from a json string.<br>
     * This method is used by minecraft versions 1.20.3+.
     *
     * @param json The json string
     * @return The deserialized text component
     */
    public ATextComponent deserializeParser(String json) {
        if (this.legacyGson) {
            LegacyGson.checkStartingType(json, true);
            json = LegacyGson.fixInvalidEscapes(json);
        }
        if (this.parentCodec != null) return this.parentCodec.deserializeJson(json);
        else return this.getGson().fromJson(JsonParser.parseString(json), ATextComponent.class);
    }

    /**
     * Deserialize a text component from a json string.<br>
     * This method is available in minecraft versions 1.9+ but not used for text components.<br>
     * It can deserialize components with comments and trailing data.<br>
     * <br>
     * <b>Example:</b><br>
     * <code>{"text":/*comment*{@literal /}"Hello World"}</code><br>
     * <code>{"text":"Hello World"}trailing data</code>
     *
     * @param json The json string
     * @return The deserialized text component
     */
    public ATextComponent deserializeLenientReader(final String json) {
        if (this.parentCodec != null) return this.parentCodec.deserializeLenientJson(json);
        else return this.deserializeReader(json, true);
    }

    /**
     * Deserialize a text component from a json string.<br>
     * This method is available in minecraft versions 1.9+ but is only used with {@code lenient} being false.
     *
     * @param json    The json string
     * @param lenient Whether to use a lenient json reader
     * @return The deserialized text component
     */
    public ATextComponent deserializeReader(String json, final boolean lenient) {
        if (this.legacyGson) {
            LegacyGson.checkStartingType(json, lenient);
            json = LegacyGson.fixInvalidEscapes(json);
        }
        if (this.parentCodec != null) {
            if (lenient) return this.parentCodec.deserializeLenientJson(json);
            else return this.parentCodec.deserializeJsonReader(json);
        }
        try {
            JsonReader reader = new JsonReader(new StringReader(json));
            reader.setLenient(lenient);
            return this.getGson().getAdapter(ATextComponent.class).read(reader);
        } catch (IOException e) {
            throw new JsonParseException("Failed to parse json", e);
        }
    }

}
