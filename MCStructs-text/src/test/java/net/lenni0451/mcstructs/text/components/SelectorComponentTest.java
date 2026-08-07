package net.lenni0451.mcstructs.text.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class SelectorComponentTest {

    private static final SelectorComponent COMPONENT = new SelectorComponent("@a", new StringComponent("separator"));

    @Test
    void getSelector() {
        assertEquals("@a", COMPONENT.getSelector());
    }

    @Test
    void getSeparator() {
        assertEquals(new StringComponent("separator"), COMPONENT.getSeparator());
    }

    @Test
    void asSingleString() {
        assertEquals("@a", COMPONENT.asSingleString());
    }

    @Test
    void copy() {
        SelectorComponent copy = (SelectorComponent) COMPONENT.copy();
        assertEquals(COMPONENT, copy);
        assertNotSame(COMPONENT, copy);
    }

    @Test
    void shallowCopy() {
        SelectorComponent copy = (SelectorComponent) COMPONENT.copy();
        copy.append("Test");
        assertEquals(1, copy.getSiblings().size());
        assertEquals(0, copy.shallowCopy().getSiblings().size());
    }

}
