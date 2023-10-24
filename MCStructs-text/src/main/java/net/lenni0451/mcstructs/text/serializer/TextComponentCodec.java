package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt.NbtTextSerializer_v1_20_3;

import java.io.StringReader;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class TextComponentCodec {

    /**
     * The text codec for 1.20.2.
     */
    public static TextComponentCodec V1_20_2 = new TextComponentCodec(
            () -> SNbtSerializer.V1_14,
            () -> null, //TODO
            NbtTextSerializer_v1_20_3::new
    );
    /**
     * The latest text codec.
     */
    public static TextComponentCodec LATEST = V1_20_2;

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private final Supplier<SNbtSerializer<CompoundTag>> sNbtSerializerSupplier;
    private final Supplier<ITypedSerializer<JsonElement, ATextComponent>> jsonSerializerSupplier;
    private final BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITypedSerializer<INbtTag, ATextComponent>> nbtSerializerSupplier;
    private SNbtSerializer<CompoundTag> sNbtSerializer;
    private ITypedSerializer<JsonElement, ATextComponent> jsonSerializer;
    private ITypedSerializer<INbtTag, ATextComponent> nbtSerializer;

    public TextComponentCodec(final Supplier<SNbtSerializer<CompoundTag>> sNbtSerializerSupplier, final Supplier<ITypedSerializer<JsonElement, ATextComponent>> jsonSerializerSupplier, final BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITypedSerializer<INbtTag, ATextComponent>> nbtSerializerSupplier) {
        this.sNbtSerializerSupplier = sNbtSerializerSupplier;
        this.jsonSerializerSupplier = jsonSerializerSupplier;
        this.nbtSerializerSupplier = nbtSerializerSupplier;
    }

    private SNbtSerializer<CompoundTag> getSNbtSerializer() {
        if (this.sNbtSerializer == null) this.sNbtSerializer = this.sNbtSerializerSupplier.get();
        return this.sNbtSerializerSupplier.get();
    }

    public ITypedSerializer<JsonElement, ATextComponent> getJsonSerializer() {
        if (this.jsonSerializer == null) this.jsonSerializer = this.jsonSerializerSupplier.get();
        return this.jsonSerializer;
    }

    public ITypedSerializer<INbtTag, ATextComponent> getNbtSerializer() {
        if (this.nbtSerializer == null) this.nbtSerializer = this.nbtSerializerSupplier.apply(this, this.getSNbtSerializer());
        return this.nbtSerializer;
    }

    public ATextComponent deserializeJson(final String json) {
        return this.deserializeJsonTree(JsonParser.parseString(json));
    }

    public ATextComponent deserializeNbt(final String nbt) throws SNbtDeserializeException {
        //TODO: this.getSNbtSerializer().readValue()
        return null;
//        return this.deserializeNbt(this.getSNbtSerializer().deserialize(nbt));
    }

    public ATextComponent deserializeLenientJson(final String json) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return this.deserializeJsonTree(JsonParser.parseReader(reader));
    }

    public ATextComponent deserializeJsonTree(final JsonElement element) {
        if (element == null) return null;
        return this.deserialize(element);
    }

    public ATextComponent deserialize(final JsonElement json) {
        return this.getJsonSerializer().deserialize(json);
    }

    public ATextComponent deserialize(final INbtTag nbt) {
        return this.getNbtSerializer().deserialize(nbt);
    }

    public JsonElement serializeJsonTree(final ATextComponent component) {
        return this.getJsonSerializer().serialize(component);
    }

    public INbtTag serializeNbt(final ATextComponent component) {
        return this.getNbtSerializer().serialize(component);
    }

    public String serializeJsonString(final ATextComponent component) {
        return GSON.toJson(this.serializeJsonTree(component));
    }

    public String serializeNbtString(final ATextComponent component) {
        try {
            return this.getSNbtSerializer().serialize(this.serializeNbt(component));
        } catch (SNbtSerializeException e) {
            throw new RuntimeException("Failed to serialize SNbt", e);
        }
    }

    public TextComponentSerializer asSerializer() {
        return new TextComponentSerializer(() -> new GsonBuilder()
                .registerTypeHierarchyAdapter(ATextComponent.class, (JsonSerializer<ATextComponent>) (src, typeOfSrc, context) -> this.serializeJsonTree(src))
                .registerTypeHierarchyAdapter(ATextComponent.class, (JsonDeserializer<ATextComponent>) (src, typeOfSrc, context) -> this.deserializeJsonTree(src))
                .disableHtmlEscaping()
                .create());
    }

}
