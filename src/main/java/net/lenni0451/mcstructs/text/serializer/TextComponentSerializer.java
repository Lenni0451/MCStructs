package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.serializer.v1_6.TextDeserializer_v1_6;
import net.lenni0451.mcstructs.text.serializer.v1_6.TextSerializer_v1_6;
import net.lenni0451.mcstructs.text.serializer.v1_7.StyleDeserializer_v1_7;
import net.lenni0451.mcstructs.text.serializer.v1_7.StyleSerializer_v1_7;
import net.lenni0451.mcstructs.text.serializer.v1_7.TextDeserializer_v1_7;
import net.lenni0451.mcstructs.text.serializer.v1_7.TextSerializer_v1_7;

import java.io.IOException;
import java.io.StringReader;
import java.util.function.Supplier;

public class TextComponentSerializer {

    public static final TextComponentSerializer V1_6 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_6())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_6())
            .create());
    public static final TextComponentSerializer V1_7 = new TextComponentSerializer(() -> new GsonBuilder()
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextSerializer_v1_7())
            .registerTypeHierarchyAdapter(ATextComponent.class, new TextDeserializer_v1_7())
            .registerTypeAdapter(Style.class, new StyleDeserializer_v1_7())
            .registerTypeAdapter(Style.class, new StyleSerializer_v1_7())
            .create());


    private final Supplier<Gson> gsonSupplier;
    private Gson gson;

    public TextComponentSerializer(final Supplier<Gson> gsonSupplier) {
        this.gsonSupplier = gsonSupplier;
    }

    public Gson getGson() {
        if (this.gson == null) this.gson = this.gsonSupplier.get();
        return this.gson;
    }

    public ATextComponent deserializeReader(final String json) {
        return deserializeReader(json, false);
    }

    public ATextComponent deserializeLenientReader(final String json) {
        return deserializeReader(json, true);
    }

    public ATextComponent deserializeReader(final String json, final boolean lenient) {
        try {
            JsonReader reader = new JsonReader(new StringReader(json));
            reader.setLenient(lenient);
            return this.getGson().getAdapter(ATextComponent.class).read(reader);
        } catch (IOException e) {
            throw new JsonParseException("Failed to parse json", e);
        }
    }

    public ATextComponent deserialize(final String json) {
        return this.getGson().fromJson(json, ATextComponent.class);
    }


    public String serialize(final ATextComponent component) {
        return this.getGson().toJson(component);
    }

    public JsonElement serializeJson(final ATextComponent component) {
        return this.getGson().toJsonTree(component);
    }

}
