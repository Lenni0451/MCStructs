package net.lenni0451.mcstructs.text.serializer.versions;

import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.components.TranslationComponent;
import org.junit.jupiter.api.Test;

import static net.lenni0451.mcstructs.text.serializer.TextComponentSerializer.V1_9;

class TextSerializer_v1_9Test extends TextSerializerTest {

    @Override
    protected TextComponent deserialize(String json) {
        return V1_9.deserializeReader(json);
    }

    @Override
    protected String serialize(TextComponent component) {
        return V1_9.serialize(component);
    }

    @Test
    @Override
    protected void runTests() {
        this.executeDeserializeTests(
                new StringComponent("test"),
                DESERIALIZE_FAIL,
                null,
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                null,
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                new StringComponent("test1"),
                new StringComponent("test1"),
                new TranslationComponent("%s %s", "abc", "123"),
                new StringComponent("test"),
                new StringComponent("test"),
                DESERIALIZE_FAIL
        );
        this.executeSerializeTests(
                "{\"text\":\"test\"}"
        );
    }

}
