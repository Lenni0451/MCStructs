package net.lenni0451.mcstructs.text.serializer.versions;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.components.TranslationComponent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import org.junit.jupiter.api.Test;

import static net.lenni0451.mcstructs.text.serializer.TextComponentSerializer.V1_16;

class TextSerializer_v1_16Test extends TextSerializerTest {

    @Override
    protected TextComponent deserialize(String json) {
        return V1_16.deserializeReader(json);
    }

    @Override
    protected String serialize(TextComponent component) {
        return V1_16.serialize(component);
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
                new StringComponent("test1"),
                new TranslationComponent("%s %s", "abc", "123"),
                new StringComponent("test").setStyle(new Style().setColor(0xFFF001)),
                new StringComponent("test"),
                DESERIALIZE_FAIL,
                new StringComponent().styled(style -> style.setHoverEvent(new ItemHoverEvent(Identifier.of("minecraft:stone"), 1, null)))
        );
        this.executeSerializeTests(
                "{\"text\":\"test\"}"
        );
    }

}
