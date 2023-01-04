package net.lenni0451.mcstructs.text.components;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KeybindComponentTest {

    private static final KeybindComponent component = new KeybindComponent("key.forward");

    @Test
    @Order(0)
    void setTranslator() {
        component.setTranslator(s -> "test");
    }

    @Test
    @Order(1)
    void asSingleString() {
        assertEquals("test", component.asSingleString());
    }

    @Test
    @Order(1)
    void copy() {
        KeybindComponent copy = (KeybindComponent) component.copy();
        assertEquals(component, copy);
        assertNotSame(component, copy);
    }

}
