package net.lenni0451.mcstructs.text.serializer.versions;

import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static net.lenni0451.mcstructs.text.serializer.TextComponentSerializer.V1_7;

class TextSerializer_v1_7Test extends TextSerializerTest {

    @Override
    protected ATextComponent deserialize(String json) {
        return V1_7.deserialize(json);
    }

    @Override
    protected String serialize(ATextComponent component) {
        return V1_7.serialize(component);
    }

    @Test
    @Override
    protected void runTests() {
        this.executeDeserializeTests(
                new StringComponent("test"),
                new StringComponent("test"),
                null,
                null,
                new StringComponent("123"),
                null,
                new StringComponent("\0")
        );
        this.executeSerializeTests(
                "\"test\""
        );
    }

}
