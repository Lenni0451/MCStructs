package net.lenni0451.mcstructs.text.serializer.v1_21_2.json;

import com.google.gson.JsonElement;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.SelectorComponent;
import net.lenni0451.mcstructs.text.serializer.subtypes.IStyleSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.json.JsonTextSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.json.JsonTextSerializer_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_21_2.TextComponentCodec_v1_21_2;

import java.util.function.Function;

public class JsonTextSerializer_v1_21_2 extends JsonTextSerializer_v1_20_5 {

    private final TextComponentCodec_v1_21_2 codec;

    public JsonTextSerializer_v1_21_2(final TextComponentCodec_v1_21_2 codec, final Function<JsonTextSerializer_v1_20_3, IStyleSerializer<JsonElement>> styleSerializer) {
        super(styleSerializer);
        this.codec = codec;
    }

    @Override
    public ATextComponent deserialize(JsonElement object) {
        ATextComponent textComponent = super.deserialize(object);
        if (textComponent instanceof SelectorComponent) {
            SelectorComponent selectorComponent = (SelectorComponent) textComponent;
            this.codec.verifyEntitySelector(selectorComponent.getSelector());
        }
        return textComponent;
    }

}
