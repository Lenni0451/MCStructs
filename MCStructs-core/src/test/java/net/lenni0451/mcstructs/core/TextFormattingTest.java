package net.lenni0451.mcstructs.core;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TextFormattingTest {

    private static final Random RND = new Random();

    @Test
    void getByOrdinal() {
        for (TextFormatting formatting : TextFormatting.ALL.values()) assertEquals(formatting, TextFormatting.getByOrdinal(formatting.getOrdinal()));
    }

    @Test
    void getByName() {
        for (TextFormatting textFormatting : TextFormatting.ALL.values()) assertEquals(textFormatting, TextFormatting.getByName(randomizeCase(textFormatting)));
    }

    @Test
    void parse() {
        assertEquals(TextFormatting.RED, TextFormatting.parse("red"));
        assertTrue(TextFormatting.parse("#FF0000").isColor());
        assertTrue(TextFormatting.parse("#FF0000").isRGBColor());
    }

    @Test
    void getClosestFormattingColor() {
        for (TextFormatting color : TextFormatting.COLORS.values()) assertEquals(color, TextFormatting.getClosestFormattingColor(color.getRgbValue()));
    }

    @Test
    void isColor() {
        for (TextFormatting color : TextFormatting.COLORS.values()) {
            assertTrue(color.isColor());
            assertFalse(color.isRGBColor());
            assertFalse(color.isFormatting());
        }
    }

    @Test
    void isRGBColor() {
        for (TextFormatting color : TextFormatting.ALL.values()) assertFalse(color.isRGBColor());
        assertTrue(TextFormatting.parse("#FF0000").isRGBColor());
    }

    @Test
    void isFormatting() {
        for (TextFormatting formatting : TextFormatting.FORMATTINGS.values()) {
            assertFalse(formatting.isColor());
            assertFalse(formatting.isRGBColor());
            assertTrue(formatting.isFormatting());
        }
    }

    @Test
    void serialize() {
        assertEquals("red", TextFormatting.RED.serialize());
        assertEquals("#FF0000", TextFormatting.parse("#ff0000").serialize());
        assertEquals(7, TextFormatting.parse("#1").serialize().length());
    }


    private static String randomizeCase(final TextFormatting textFormatting) {
        String name = textFormatting.getName();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            if (RND.nextBoolean()) builder.append(Character.toUpperCase(name.charAt(i)));
            else builder.append(Character.toLowerCase(name.charAt(i)));
        }
        return builder.toString();
    }

}
