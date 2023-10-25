package net.lenni0451.mcstructs.text.serializer.v1_20_3.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

public class JsonStyleSerializer_v1_20_3 implements ITypedSerializer<JsonElement, Style> {

    private final ITypedSerializer<JsonElement, AHoverEvent> hoverEventSerializer;

    public JsonStyleSerializer_v1_20_3(final TextComponentCodec codec, final ITypedSerializer<JsonElement, ATextComponent> textSerializer, final SNbtSerializer<CompoundTag> sNbtSerializer) {
        this.hoverEventSerializer = new JsonHoverEventSerializer_v1_20_3(codec, textSerializer, sNbtSerializer);
    }

    @Override
    public JsonElement serialize(Style object) {
        JsonObject out = new JsonObject();
        if (object.getColor() != null) out.addProperty("color", object.getColor().serialize());
        if (object.getBold() != null) out.addProperty("bold", object.isBold());
        if (object.getItalic() != null) out.addProperty("italic", object.isItalic());
        if (object.getUnderlined() != null) out.addProperty("underlined", object.isUnderlined());
        if (object.getStrikethrough() != null) out.addProperty("strikethrough", object.isStrikethrough());
        if (object.getObfuscated() != null) out.addProperty("obfuscated", object.isObfuscated());
        if (object.getClickEvent() != null) {
            JsonObject clickEvent = new JsonObject();
            clickEvent.addProperty("action", object.getClickEvent().getAction().getName());
            clickEvent.addProperty("value", object.getClickEvent().getValue());
            out.add("clickEvent", clickEvent);
        }
        if (object.getHoverEvent() != null) out.add("hoverEvent", this.hoverEventSerializer.serialize(object.getHoverEvent()));
        if (object.getInsertion() != null) out.addProperty("insertion", object.getInsertion());
        if (object.getFont() != null) out.addProperty("font", object.getFont().get());
        return out;
    }

    @Override
    public Style deserialize(JsonElement object) {
        return null; //TODO
    }

}
