package net.lenni0451.mcstructs.text.utils;

import net.lenni0451.mcstructs.text.TextFormatting;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LegacyStringUtilsTest {

    @Test
    void getStyleAt() {
        LegacyStringUtils.LegacyStyle style0 = LegacyStringUtils.getStyleAt("§4§kHello", 0, true);
        assertNull(style0.getColor());
        assertTrue(style0.getStyles().isEmpty());

        LegacyStringUtils.LegacyStyle style2 = LegacyStringUtils.getStyleAt("§4§kHello", 2, true);
        assertEquals(TextFormatting.DARK_RED, style2.getColor());
        assertTrue(style2.getStyles().isEmpty());

        LegacyStringUtils.LegacyStyle style3 = LegacyStringUtils.getStyleAt("§4§kHello", 3, true);
        assertEquals(TextFormatting.DARK_RED, style3.getColor());
        assertEquals(1, style3.getStyles().size());
        assertTrue(style3.getStyles().contains(TextFormatting.OBFUSCATED));

        LegacyStringUtils.LegacyStyle style40 = LegacyStringUtils.getStyleAt("§4§kHello", 40, true);
        assertEquals(style3, style40);

        LegacyStringUtils.LegacyStyle resetInvalidStyle4 = LegacyStringUtils.getStyleAt("§4H§zi", 4, true);
        assertEquals(TextFormatting.WHITE, resetInvalidStyle4.getColor());
        assertTrue(resetInvalidStyle4.getStyles().isEmpty());

        LegacyStringUtils.LegacyStyle ignoreInvalidStyle6 = LegacyStringUtils.getStyleAt("§4H§zi", 6, false);
        assertEquals(TextFormatting.DARK_RED, ignoreInvalidStyle6.getColor());
        assertTrue(ignoreInvalidStyle6.getStyles().isEmpty());
    }

    @Test
    void split() {
        String[] parts = LegacyStringUtils.split("§4§kHello\nWorld\nTest", "\n", true);
        assertEquals(3, parts.length);
        assertEquals("§4§kHello", parts[0]);
        assertEquals("§4§kWorld", parts[1]);
        assertEquals("§4§kTest", parts[2]);

        parts = LegacyStringUtils.split("§4§k§bHi\nWorld\nTest", "\n", true);
        assertEquals(3, parts.length);
        assertEquals("§4§k§bHi", parts[0]);
        assertEquals("§bWorld", parts[1]);
        assertEquals("§bTest", parts[2]);

        parts = LegacyStringUtils.split("§4Hello\n§eWorld\n§lTest\n3", "\n", true);
        assertEquals(4, parts.length);
        assertEquals("§4Hello", parts[0]);
        assertEquals("§4§eWorld", parts[1]);
        assertEquals("§e§lTest", parts[2]);
        assertEquals("§e§l3", parts[3]);
    }

}
