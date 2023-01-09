package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.core.TextFormatting;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextComponentBuilderTest {

    @Test
    void build() {
        ATextComponent component = TextComponentBuilder.build("Hello", TextFormatting.RED, " World ", 1, TextFormatting.GREEN);
        assertEquals("§cHello§a World §a1", component.asLegacyFormatString());
    }

}