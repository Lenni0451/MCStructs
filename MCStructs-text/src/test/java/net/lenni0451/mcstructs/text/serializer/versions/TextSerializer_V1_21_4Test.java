package net.lenni0451.mcstructs.text.serializer.versions;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.components.TranslationComponent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import org.junit.jupiter.api.Test;

import static net.lenni0451.mcstructs.text.serializer.TextComponentSerializer.V1_21_4;

public class TextSerializer_V1_21_4Test extends TextSerializerTest {

    @Override
    protected TextComponent deserialize(String json) {
        return V1_21_4.deserializeParser(json);
    }

    @Override
    protected String serialize(TextComponent component) {
        return V1_21_4.serialize(component);
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
                new TranslationComponent("%s %s", "abc", (byte) 123),
                new StringComponent("test").setStyle(new Style().setColor(0xFFF001)),
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                new StringComponent().styled(style -> style.setHoverEvent(new ItemHoverEvent(Identifier.of("minecraft:stone"), 1, null)))
        );
        this.executeSerializeTests(
                "\"test\""
        );
    }

}
