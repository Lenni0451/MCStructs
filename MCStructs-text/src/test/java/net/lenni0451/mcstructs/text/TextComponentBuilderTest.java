package net.lenni0451.mcstructs.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextComponentBuilderTest {

    @Test
    void build() {
        TextComponent component = TextComponentBuilder.build("Hello", TextFormatting.RED, " World ", 1, TextFormatting.GREEN);
        assertEquals("§cHello§a World 1", component.asLegacyFormatString());
    }

}
