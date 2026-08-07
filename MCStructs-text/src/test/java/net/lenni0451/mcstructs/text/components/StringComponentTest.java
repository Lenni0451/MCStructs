package net.lenni0451.mcstructs.text.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class StringComponentTest {

    private static final StringComponent COMPONENT = new StringComponent("text");

    @Test
    void getText() {
        assertEquals("text", COMPONENT.getText());
    }

    @Test
    void asSingleString() {
        assertEquals("text", COMPONENT.asSingleString());
    }

    @Test
    void copy() {
        StringComponent copy = (StringComponent) COMPONENT.copy();
        assertEquals(COMPONENT, copy);
        assertNotSame(COMPONENT, copy);
    }

    @Test
    void shallowCopy() {
        StringComponent copy = (StringComponent) COMPONENT.copy();
        copy.append("Test");
        assertEquals(1, copy.getSiblings().size());
        assertEquals(0, copy.shallowCopy().getSiblings().size());
    }

}
