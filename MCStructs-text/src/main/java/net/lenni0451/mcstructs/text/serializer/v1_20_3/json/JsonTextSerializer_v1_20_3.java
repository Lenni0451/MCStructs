package net.lenni0451.mcstructs.text.serializer.v1_20_3.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtComponent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.Map;

public class JsonTextSerializer_v1_20_3 implements ITypedSerializer<JsonElement, ATextComponent> {

    private final ITypedSerializer<JsonElement, Style> styleSerializer;

    public JsonTextSerializer_v1_20_3(final TextComponentCodec codec, final SNbtSerializer<CompoundTag> sNbtSerializer) {
        this.styleSerializer = new JsonStyleSerializer_v1_20_3(codec, this, sNbtSerializer);
    }

    @Override
    public JsonElement serialize(ATextComponent object) {
        JsonObject out = new JsonObject();
        if (object instanceof StringComponent) {
            StringComponent component = (StringComponent) object;
            if (component.getSiblings().isEmpty() && component.getStyle().isEmpty()) return new JsonPrimitive(component.getText());
            else out.addProperty("text", component.getText());
        } else if (object instanceof TranslationComponent) {
            TranslationComponent component = (TranslationComponent) object;
            out.addProperty("translate", component.getKey());
            if (component.getFallback() != null) out.addProperty("fallback", component.getFallback());
            if (component.getArgs().length > 0) {
                JsonArray args = new JsonArray();
                for (Object arg : component.getArgs()) args.add(this.convert(arg));
                out.add("with", args);
            }
        } else if (object instanceof KeybindComponent) {
            KeybindComponent component = (KeybindComponent) object;
            out.addProperty("keybind", component.getKeybind());
        } else if (object instanceof ScoreComponent) {
            ScoreComponent component = (ScoreComponent) object;
            JsonObject score = new JsonObject();
            score.addProperty("name", component.getName());
            score.addProperty("objective", component.getObjective());
            out.add("score", score);
        } else if (object instanceof SelectorComponent) {
            SelectorComponent component = (SelectorComponent) object;
            out.addProperty("selector", component.getSelector());
            if (component.getSeparator() != null) out.add("separator", this.serialize(component.getSeparator()));
        } else if (object instanceof NbtComponent) {
            NbtComponent component = (NbtComponent) object;
            out.addProperty("nbt", component.getComponent());
            if (component.isResolve()) out.addProperty("interpret", 1);
            if (component instanceof EntityNbtComponent) {
                EntityNbtComponent entityComponent = (EntityNbtComponent) component;
                out.addProperty("entity", entityComponent.getSelector());
            } else if (component instanceof BlockNbtComponent) {
                BlockNbtComponent blockNbtComponent = (BlockNbtComponent) component;
                out.addProperty("block", blockNbtComponent.getPos());
            } else if (component instanceof StorageNbtComponent) {
                StorageNbtComponent storageNbtComponent = (StorageNbtComponent) component;
                out.addProperty("storage", storageNbtComponent.getId().get());
            } else {
                throw new IllegalArgumentException("Unknown Nbt component type: " + component.getClass().getName());
            }
        } else {
            throw new IllegalArgumentException("Unknown component type: " + object.getClass().getName());
        }

        JsonObject style = this.styleSerializer.serialize(object.getStyle()).getAsJsonObject();
        if (style.size() > 0) {
            for (Map.Entry<String, JsonElement> entry : style.entrySet()) out.add(entry.getKey(), entry.getValue());
        }

        if (!object.getSiblings().isEmpty()) {
            JsonArray siblings = new JsonArray();
            for (ATextComponent sibling : object.getSiblings()) siblings.add(this.serialize(sibling));
            out.add("extra", siblings);
        }

        return out;
    }

    private JsonElement convert(final Object object) {
        if (object instanceof Boolean) return new JsonPrimitive((Boolean) object);
        else if (object instanceof Number) return new JsonPrimitive((Number) object);
        else if (object instanceof String) return new JsonPrimitive((String) object);
        else if (object instanceof ATextComponent) return this.serialize((ATextComponent) object);
        else throw new IllegalArgumentException("Unknown object type: " + object.getClass().getName());
    }

    @Override
    public ATextComponent deserialize(JsonElement object) {
        return null; //TODO
    }

}
