package net.lenni0451.mcstructs.text.components;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KeybindComponentTest {

    private static final KeybindComponent COMPONENT = new KeybindComponent("key.forward");

    @Test
    @Order(0)
    void setTranslator() {
        COMPONENT.setTranslator(s -> "test");
    }

    @Test
    @Order(1)
    void asSingleString() {
        assertEquals("test", COMPONENT.asSingleString());
    }

    @Test
    @Order(1)
    void copy() {
        KeybindComponent copy = (KeybindComponent) COMPONENT.copy();
        assertEquals(COMPONENT, copy);
        assertNotSame(COMPONENT, copy);
    }

    @Test
    @Order(1)
    void shallowCopy() {
        KeybindComponent copy = (KeybindComponent) COMPONENT.copy();
        copy.append("Test");
        assertEquals(1, copy.getSiblings().size());
        assertEquals(0, copy.shallowCopy().getSiblings().size());
    }

}
