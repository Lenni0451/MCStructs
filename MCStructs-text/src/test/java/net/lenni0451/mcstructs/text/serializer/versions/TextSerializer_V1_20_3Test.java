package net.lenni0451.mcstructs.text.serializer.versions;

import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.components.TranslationComponent;
import org.junit.jupiter.api.Test;

import static net.lenni0451.mcstructs.text.serializer.TextComponentSerializer.V1_20_3;

public class TextSerializer_V1_20_3Test extends TextSerializerTest {

    @Override
    protected ATextComponent deserialize(String json) {
        return V1_20_3.deserializeParser(json);
    }

    @Override
    protected String serialize(ATextComponent component) {
        return V1_20_3.serialize(component);
    }

    @Test
    @Override
    protected void runTests() {
        this.executeDeserializeTests(
                new StringComponent("test"),
                new StringComponent("test"),
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                new StringComponent("\0"),
                DESERIALIZE_FAIL,
                new TranslationComponent("test"),
                new StringComponent("test1"),
                new TranslationComponent("test2"),
                new TranslationComponent("%s %s", "abc", 123),
                new StringComponent("test").setStyle(new Style().setColor(0xFFF001)),
                DESERIALIZE_FAIL
        );
        this.executeSerializeTests(
                "\"test\""
        );
    }

}
