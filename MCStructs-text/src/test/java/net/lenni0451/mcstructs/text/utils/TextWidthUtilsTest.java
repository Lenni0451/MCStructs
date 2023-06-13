package net.lenni0451.mcstructs.text.utils;

import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TextWidthUtilsTest {

    @Test
    void loadDefaultWidths() {
        assertDoesNotThrow(() -> TextWidthUtils.getComponentWidth(new StringComponent("")));
    }

    @Test
    void getComponentWidth() {
        assertEquals(6, TextWidthUtils.getComponentWidth(new StringComponent("\0\1\2\3"), new float[]{1, 2, 3}));
    }

}
