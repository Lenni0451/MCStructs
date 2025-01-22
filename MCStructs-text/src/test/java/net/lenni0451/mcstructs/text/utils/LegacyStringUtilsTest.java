package net.lenni0451.mcstructs.text.utils;

import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.stringformat.StringFormat;
import net.lenni0451.mcstructs.text.stringformat.handling.ColorHandling;
import net.lenni0451.mcstructs.text.stringformat.handling.DeserializerUnknownHandling;
import net.lenni0451.mcstructs.text.stringformat.handling.SerializerUnknownHandling;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LegacyStringUtilsTest {

    private static final StringFormat FORMAT = StringFormat.vanilla();

    @Test
    void getStyleAt() {
        Style style0 = FORMAT.styleAt("§4§kHello", 0, ColorHandling.RESET, DeserializerUnknownHandling.WHITE);
        assertNull(style0.getColor());
        assertEquals(0, style0.getFormattings(false).length);

        Style style2 = FORMAT.styleAt("§4§kHello", 2, ColorHandling.RESET, DeserializerUnknownHandling.WHITE);
        assertEquals(TextFormatting.DARK_RED, style2.getColor());
        assertEquals(0, style2.getFormattings(false).length);

        Style style3 = FORMAT.styleAt("§4§kHello", 3, ColorHandling.RESET, DeserializerUnknownHandling.WHITE);
        assertEquals(TextFormatting.DARK_RED, style3.getColor());
        assertEquals(1, style3.getFormattings(false).length);
        assertEquals(TextFormatting.OBFUSCATED, style3.getFormattings(false)[0]);

        Style style40 = FORMAT.styleAt("§4§kHello", 40, ColorHandling.RESET, DeserializerUnknownHandling.WHITE);
        assertEquals(style3, style40);

        Style resetInvalidStyle4 = FORMAT.styleAt("§4H§zi", 4, ColorHandling.RESET, DeserializerUnknownHandling.WHITE);
        assertEquals(TextFormatting.WHITE, resetInvalidStyle4.getColor());
        assertEquals(0, resetInvalidStyle4.getFormattings(false).length);

        Style ignoreInvalidStyle6 = FORMAT.styleAt("§4H§zi", 6, ColorHandling.RESET, DeserializerUnknownHandling.IGNORE);
        assertEquals(TextFormatting.DARK_RED, ignoreInvalidStyle6.getColor());
        assertEquals(0, ignoreInvalidStyle6.getFormattings(false).length);
    }

    @Test
    void split() {
        String[] parts = FORMAT.split("§4§kHello\nWorld\nTest", "\n", ColorHandling.RESET, SerializerUnknownHandling.THROW, DeserializerUnknownHandling.THROW);
        assertEquals(3, parts.length);
        assertEquals("§4§kHello", parts[0]);
        assertEquals("§4§kWorld", parts[1]);
        assertEquals("§4§kTest", parts[2]);

        parts = FORMAT.split("§4§k§bHi\nWorld\nTest", "\n", ColorHandling.RESET, SerializerUnknownHandling.THROW, DeserializerUnknownHandling.THROW);
        assertEquals(3, parts.length);
        assertEquals("§4§k§bHi", parts[0]);
        assertEquals("§bWorld", parts[1]);
        assertEquals("§bTest", parts[2]);

        parts = FORMAT.split("§4Hello\n§eWorld\n§lTest\n3", "\n", ColorHandling.RESET, SerializerUnknownHandling.THROW, DeserializerUnknownHandling.THROW);
        assertEquals(4, parts.length);
        assertEquals("§4Hello", parts[0]);
        assertEquals("§4§eWorld", parts[1]);
        assertEquals("§e§lTest", parts[2]);
        assertEquals("§e§l3", parts[3]);
    }

}
