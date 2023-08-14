package net.lenni0451.mcstructs.text.serializer;

import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LegacyStringDeserializerTest {

    @Test
    void parseWithUnknownWhite() {
        ATextComponent component = LegacyStringDeserializer.parse("§4A§xB", true);
        assertEquals(
                new StringComponent("")
                        .append(new StringComponent("A").setStyle(new Style().setFormatting(TextFormatting.DARK_RED)))
                        .append(new StringComponent("B").setStyle(new Style().setFormatting(TextFormatting.WHITE))),
                component);
    }

    @Test
    void parseWithoutUnknownWhite() {
        ATextComponent component = LegacyStringDeserializer.parse("§4A§xB", false);
        assertEquals(new StringComponent("AB").setStyle(new Style().setFormatting(TextFormatting.DARK_RED)), component);
    }

}
