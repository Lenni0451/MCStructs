package net.lenni0451.mcstructs.text.serializer;

import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.stringformat.StringFormat;
import net.lenni0451.mcstructs.text.stringformat.handling.ColorHandling;
import net.lenni0451.mcstructs.text.stringformat.handling.DeserializerUnknownHandling;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LegacyStringDeserializerTest {

    private static final StringFormat FORMAT = StringFormat.vanilla();

    @Test
    void parseWithUnknownWhite() {
        TextComponent component = FORMAT.fromString("§4A§xB", ColorHandling.RESET, DeserializerUnknownHandling.WHITE);
        assertEquals(
                new StringComponent("")
                        .append(new StringComponent("A").setStyle(new Style().setFormatting(TextFormatting.DARK_RED)))
                        .append(new StringComponent("B").setStyle(new Style().setFormatting(TextFormatting.WHITE))),
                component);
    }

    @Test
    void parseWithoutUnknownWhite() {
        TextComponent component = FORMAT.fromString("§4A§xB", ColorHandling.RESET, DeserializerUnknownHandling.IGNORE);
        assertEquals(new StringComponent("AB").setStyle(new Style().setFormatting(TextFormatting.DARK_RED)), component);
    }

}
