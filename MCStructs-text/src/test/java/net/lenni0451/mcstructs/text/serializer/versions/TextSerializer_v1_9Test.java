package net.lenni0451.mcstructs.text.serializer.versions;

import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static net.lenni0451.mcstructs.text.serializer.TextComponentSerializer.V1_9;

class TextSerializer_v1_9Test extends TextSerializerTest {

    @Override
    protected ATextComponent deserialize(String json) {
        return V1_9.deserializeReader(json);
    }

    @Override
    protected String serialize(ATextComponent component) {
        return V1_9.serialize(component);
    }

    @Test
    @Override
    protected void runTests() {
        this.executeDeserializeTests(
                new StringComponent("test"),
                DESERIALIZE_FAIL,
                null,
                null,
                new StringComponent("123"),
                null,
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                new StringComponent("test1"),
                new StringComponent("test1")
        );
        this.executeSerializeTests(
                "{\"text\":\"test\"}"
        );
    }

}
