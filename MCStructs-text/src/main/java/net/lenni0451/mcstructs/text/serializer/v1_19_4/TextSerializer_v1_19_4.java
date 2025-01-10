package net.lenni0451.mcstructs.text.serializer.v1_19_4;

import com.google.gson.*;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtSource;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtSource;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtSource;

import java.lang.reflect.Type;
import java.util.Map;

public class TextSerializer_v1_19_4 implements JsonSerializer<TextComponent> {

    @Override
    public JsonElement serialize(TextComponent src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject serializedComponent = new JsonObject();

        if (!src.getStyle().isEmpty()) {
            JsonElement serializedStyle = context.serialize(src.getStyle());
            if (serializedStyle.isJsonObject()) {
                JsonObject serializedStyleObject = serializedStyle.getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : serializedStyleObject.entrySet()) serializedComponent.add(entry.getKey(), entry.getValue());
            }
        }
        if (!src.getSiblings().isEmpty()) {
            JsonArray siblings = new JsonArray();
            for (TextComponent sibling : src.getSiblings()) siblings.add(this.serialize(sibling, sibling.getClass(), context));
            serializedComponent.add("extra", siblings);
        }

        if (src instanceof StringComponent) {
            serializedComponent.addProperty("text", ((StringComponent) src).getText());
        } else if (src instanceof TranslationComponent) {
            TranslationComponent translationComponent = (TranslationComponent) src;
            serializedComponent.addProperty("translate", translationComponent.getKey());
            if (translationComponent.getFallback() != null) serializedComponent.addProperty("fallback", translationComponent.getFallback());
            if (translationComponent.getArgs().length > 0) {
                JsonArray with = new JsonArray();
                Object[] args = translationComponent.getArgs();
                for (Object arg : args) {
                    if (arg instanceof TextComponent) with.add(this.serialize((TextComponent) arg, arg.getClass(), context));
                    else with.add(new JsonPrimitive(String.valueOf(arg)));
                }
                serializedComponent.add("with", with);
            }
        } else if (src instanceof ScoreComponent) {
            ScoreComponent scoreComponent = (ScoreComponent) src;
            JsonObject serializedScore = new JsonObject();
            serializedScore.addProperty("name", scoreComponent.getName());
            serializedScore.addProperty("objective", scoreComponent.getObjective());
            serializedComponent.add("score", serializedScore);
        } else if (src instanceof SelectorComponent) {
            SelectorComponent selectorComponent = (SelectorComponent) src;
            serializedComponent.addProperty("selector", selectorComponent.getSelector());
            if (selectorComponent.getSeparator() != null) serializedComponent.add("separator", this.serialize(selectorComponent.getSeparator(), typeOfSrc, context));
        } else if (src instanceof KeybindComponent) {
            serializedComponent.addProperty("keybind", ((KeybindComponent) src).getKeybind());
        } else if (src instanceof NbtComponent) {
            NbtComponent nbtComponent = (NbtComponent) src;
            serializedComponent.addProperty("nbt", nbtComponent.getComponent());
            serializedComponent.addProperty("interpret", nbtComponent.isResolve());
            if (nbtComponent.getSeparator() != null) serializedComponent.add("separator", this.serialize(nbtComponent.getSeparator(), typeOfSrc, context));
            if (nbtComponent.getDataSource() instanceof BlockNbtSource) {
                serializedComponent.addProperty("block", ((BlockNbtSource) nbtComponent.getDataSource()).getPos());
            } else if (nbtComponent.getDataSource() instanceof EntityNbtSource) {
                serializedComponent.addProperty("entity", ((EntityNbtSource) nbtComponent.getDataSource()).getSelector());
            } else if (nbtComponent.getDataSource() instanceof StorageNbtSource) {
                serializedComponent.addProperty("storage", ((StorageNbtSource) nbtComponent.getDataSource()).getId().get());
            } else {
                throw new JsonParseException("Don't know how to serialize " + src + " as a Component");
            }
        } else {
            throw new JsonParseException("Don't know how to serialize " + src + " as a Component");
        }

        return serializedComponent;
    }

}
