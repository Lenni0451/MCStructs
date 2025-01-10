package net.lenni0451.mcstructs.text.serializer.v1_14;

import com.google.gson.*;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtSource;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtSource;

import java.lang.reflect.Type;

import static net.lenni0451.mcstructs.text.utils.JsonUtils.getBoolean;
import static net.lenni0451.mcstructs.text.utils.JsonUtils.getString;

public class TextDeserializer_v1_14 implements JsonDeserializer<TextComponent> {

    @Override
    public TextComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return new StringComponent(json.getAsString());
        } else if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            TextComponent component = null;

            for (JsonElement element : array) {
                TextComponent serializedElement = this.deserialize(element, element.getClass(), context);
                if (component == null) component = serializedElement;
                else component.append(serializedElement);
            }

            return component;
        } else if (json.isJsonObject()) {
            JsonObject rawComponent = json.getAsJsonObject();
            TextComponent component;

            if (rawComponent.has("text")) {
                component = new StringComponent(getString(rawComponent, "text"));
            } else if (rawComponent.has("translate")) {
                String translate = getString(rawComponent, "translate");
                if (rawComponent.has("with")) {
                    JsonArray with = rawComponent.getAsJsonArray("with");
                    Object[] args = new Object[with.size()];
                    for (int i = 0; i < with.size(); i++) {
                        TextComponent element = this.deserialize(with.get(i), typeOfT, context);
                        args[i] = element;
                        if (element instanceof StringComponent) {
                            StringComponent stringComponent = (StringComponent) element;
                            if (stringComponent.getStyle().isEmpty() && stringComponent.getSiblings().isEmpty()) args[i] = stringComponent.getText();
                        }
                    }
                    component = new TranslationComponent(translate, args);
                } else {
                    component = new TranslationComponent(translate);
                }
            } else if (rawComponent.has("score")) {
                JsonObject score = rawComponent.getAsJsonObject("score");
                if (!score.has("name") || !score.has("objective")) throw new JsonParseException("A score component needs at least a name and an objective");

                component = new ScoreComponent(getString(score, "name"), getString(score, "objective"));
                if (score.has("value")) ((ScoreComponent) component).setValue(getString(score, "value"));
            } else if (rawComponent.has("selector")) {
                component = new SelectorComponent(getString(rawComponent, "selector"), null);
            } else if (rawComponent.has("keybind")) {
                component = new KeybindComponent(getString(rawComponent, "keybind"));
            } else if (rawComponent.has("nbt")) {
                String nbt = getString(rawComponent, "nbt");
                boolean interpret = getBoolean(rawComponent, "interpret", false);
                if (rawComponent.has("block")) component = new NbtComponent(nbt, interpret, null, new BlockNbtSource(getString(rawComponent, "block")));
                else if (rawComponent.has("entity")) component = new NbtComponent(nbt, interpret, null, new EntityNbtSource(getString(rawComponent, "entity")));
                else throw new JsonParseException("Don't know how to turn " + json + " into a Component");
            } else {
                throw new JsonParseException("Don't know how to turn " + json + " into a Component");
            }

            if (rawComponent.has("extra")) {
                JsonArray extra = rawComponent.getAsJsonArray("extra");
                if (extra.isEmpty()) throw new JsonParseException("Unexpected empty array of components");
                for (JsonElement element : extra) component.append(this.deserialize(element, typeOfT, context));
            }

            Style newStyle = context.deserialize(rawComponent, Style.class);
            if (newStyle != null) component.setStyle(newStyle);
            return component;
        } else {
            throw new JsonParseException("Don't know how to turn " + json + " into a Component");
        }
    }

}
