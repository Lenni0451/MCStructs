package net.lenni0451.mcstructs.text.serializer.v1_20_3.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtComponent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;

import java.util.Map;
import java.util.function.Function;

import static net.lenni0451.mcstructs.text.utils.CodecUtils.*;

public class JsonTextSerializer_v1_20_3 implements ITypedSerializer<JsonElement, ATextComponent> {

    private final ITypedSerializer<JsonElement, Style> styleSerializer;

    public JsonTextSerializer_v1_20_3(final Function<JsonTextSerializer_v1_20_3, ITypedSerializer<JsonElement, Style>> styleSerializer) {
        this.styleSerializer = styleSerializer.apply(this);
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

    protected JsonElement convert(final Object object) {
        if (object instanceof Boolean) return new JsonPrimitive((Boolean) object);
        else if (object instanceof Number) return new JsonPrimitive((Number) object);
        else if (object instanceof String) return new JsonPrimitive((String) object);
        else if (object instanceof ATextComponent) return this.serialize((ATextComponent) object);
        else throw new IllegalArgumentException("Unknown object type: " + object.getClass().getName());
    }

    @Override
    public ATextComponent deserialize(JsonElement object) {
        if (isString(object)) {
            return new StringComponent(object.getAsString());
        } else if (object.isJsonArray()) {
            if (object.getAsJsonArray().isEmpty()) throw new IllegalArgumentException("Empty json array");
            JsonArray array = object.getAsJsonArray();
            ATextComponent[] components = new ATextComponent[array.size()];
            for (int i = 0; i < array.size(); i++) components[i] = this.deserialize(array.get(i));
            if (components.length == 1) {
                return components[0];
            } else {
                ATextComponent parent = components[0];
                for (int i = 1; i < components.length; i++) parent.append(components[i]);
                return parent;
            }
        } else if (!object.isJsonObject()) {
            throw new IllegalArgumentException("Unknown component type: " + object.getClass().getSimpleName());
        }

        ATextComponent component = null;
        JsonObject obj = object.getAsJsonObject();
        String type = optionalString(obj, "type");
        if (containsString(obj, "text") && (type == null || type.equals("text"))) {
            component = new StringComponent(obj.get("text").getAsString());
        } else if (containsString(obj, "translate") && (type == null || type.equals("translatable"))) {
            String key = obj.get("translate").getAsString();
            String fallback = optionalString(obj, "fallback");
            if (obj.has("with")) {
                if (!containsArray(obj, "with")) throw new IllegalArgumentException("Expected json array for 'with' tag");
                JsonArray with = obj.getAsJsonArray("with");
                Object[] args = new Object[with.size()];
                for (int i = 0; i < with.size(); i++) {
                    JsonElement arg = with.get(i);
                    if (isNumber(arg)) {
                        if (arg.getAsJsonPrimitive().isNumber()) args[i] = arg.getAsInt();
                        else args[i] = arg.getAsBoolean() ? 1 : 0;
                    } else if (isString(arg)) {
                        args[i] = arg.getAsString();
                    } else {
                        args[i] = this.deserialize(arg);
                    }
                }
                component = new TranslationComponent(key, args).setFallback(fallback);
            } else {
                component = new TranslationComponent(key).setFallback(fallback);
            }
        } else if (containsString(obj, "keybind") && (type == null || type.equals("keybind"))) {
            component = new KeybindComponent(obj.get("keybind").getAsString());
        } else if (containsObject(obj, "score") && containsString(obj.getAsJsonObject("score"), "name") && containsString(obj.getAsJsonObject("score"), "objective") && (type == null || type.equals("score"))) {
            JsonObject score = obj.getAsJsonObject("score");
            String name = score.get("name").getAsString();
            String objective = score.get("objective").getAsString();
            component = new ScoreComponent(name, objective);
        } else if (containsString(obj, "selector") && (type == null || type.equals("selector"))) {
            String selector = obj.get("selector").getAsString();
            ATextComponent separator = null;
            if (obj.has("separator")) separator = this.deserialize(obj.get("separator"));
            component = new SelectorComponent(selector, separator);
        } else if (containsString(obj, "nbt") && (type == null || type.equals("nbt"))) {
            String nbt = obj.get("nbt").getAsString();
            boolean interpret = Boolean.TRUE.equals(optionalBoolean(obj, "interpret"));
            ATextComponent separator = null;
            if (obj.has("separator")) {
                try {
                    separator = this.deserialize(obj.get("separator"));
                } catch (Throwable ignored) {
                    //If the separator fails to parse we just ignore it
                }
            }
            String source = optionalString(obj, "source");

            boolean typeFound = false;
            if (containsString(obj, "entity") && (source == null || source.equals("entity"))) {
                component = new EntityNbtComponent(nbt, interpret, separator, obj.get("entity").getAsString());
                typeFound = true;
            } else if (containsString(obj, "block") && (source == null || source.equals("block"))) {
                component = new BlockNbtComponent(nbt, interpret, separator, obj.get("block").getAsString());
                typeFound = true;
            } else if (containsString(obj, "storage") && (source == null || source.equals("storage"))) {
                try {
                    component = new StorageNbtComponent(nbt, interpret, separator, Identifier.of(obj.get("storage").getAsString()));
                    typeFound = true;
                } catch (Throwable ignored) {
                    //If the storage identifier fails to parse we just ignore it
                }
            }
            if (!typeFound) throw new IllegalArgumentException("Unknown Nbt component type: " + obj.getClass().getSimpleName());
        } else {
            throw new IllegalArgumentException("Unknown component type: " + obj.getClass().getSimpleName());
        }

        Style style = this.styleSerializer.deserialize(obj);
        if (!style.isEmpty()) component.setStyle(style);

        if (obj.has("extra")) {
            if (!obj.has("extra") || !obj.get("extra").isJsonArray()) throw new IllegalArgumentException("Expected json array for 'extra' tag");
            JsonArray extraList = obj.getAsJsonArray("extra");
            if (extraList.isEmpty()) throw new IllegalArgumentException("Empty extra json array");

            ATextComponent[] extra;
            extra = new ATextComponent[extraList.size()];
            for (int i = 0; i < extraList.size(); i++) extra[i] = this.deserialize(extraList.get(i));
            component.append(extra);
        }

        return component;
    }

}
