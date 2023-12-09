package net.lenni0451.mcstructs.text.serializer.versions;

import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.components.TranslationComponent;
import org.junit.jupiter.api.Test;

import static net.lenni0451.mcstructs.text.serializer.TextComponentSerializer.V1_6;

class TextSerializer_v1_6Test extends TextSerializerTest {

    @Override
    protected ATextComponent deserialize(String json) {
        return V1_6.deserialize(json);
    }

    @Override
    protected String serialize(ATextComponent component) {
        return V1_6.serialize(component);
    }

    @Test
    @Override
    protected void runTests() {
        this.executeDeserializeTests(
                new StringComponent("test"),
                new StringComponent("test"),
                DESERIALIZE_FAIL,
                null,
                DESERIALIZE_FAIL,
                null,
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                null,
                new StringComponent("test1"),
                new StringComponent("test1"),
                new TranslationComponent("%s %s"),
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL
        );
        this.executeSerializeTests(
                "{\"text\":\"test\"}"
        );
    }

}
