package net.lenni0451.mcstructs.text.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class SelectorComponentTest {

    private static final SelectorComponent component = new SelectorComponent("@a", new StringComponent("separator"));

    @Test
    void getSelector() {
        assertEquals("@a", component.getSelector());
    }

    @Test
    void getSeparator() {
        assertEquals(new StringComponent("separator"), component.getSeparator());
    }

    @Test
    void asSingleString() {
        assertEquals("@a", component.asSingleString());
    }

    @Test
    void copy() {
        SelectorComponent copy = (SelectorComponent) component.copy();
        assertEquals(component, copy);
        assertNotSame(component, copy);
    }

}
