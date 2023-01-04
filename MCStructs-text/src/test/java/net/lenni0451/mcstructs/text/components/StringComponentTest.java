package net.lenni0451.mcstructs.text.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class StringComponentTest {

    private static final StringComponent component = new StringComponent("text");

    @Test
    void getText() {
        assertEquals("text", component.getText());
    }

    @Test
    void asSingleString() {
        assertEquals("text", component.asSingleString());
    }

    @Test
    void copy() {
        StringComponent copy = (StringComponent) component.copy();
        assertEquals(component, copy);
        assertNotSame(component, copy);
    }

}
