package net.lenni0451.mcstructs.text.components;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TranslationComponentTest {

    private static final String translationKey = "test";
    private static final String translation = "%%test %2$s %1$s";
    private static final TranslationComponent component = new TranslationComponent(translationKey, "test1", new StringComponent("test2"));

    @Test
    void getKey() {
        assertEquals(translationKey, component.getKey());
    }

    @Test
    void getArgs() {
        assertEquals("test1", component.getArgs()[0]);
        assertEquals(new StringComponent("test2"), component.getArgs()[1]);
    }

    @Test
    @Order(0)
    void setTranslator() {
        component.setTranslator(s -> translation);
    }

    @Test
    @Order(1)
    void asSingleString() {
        assertEquals("%test test2 test1", component.asSingleString());
    }

    @Test
    void copy() {
        TranslationComponent copy = (TranslationComponent) component.copy();
        assertEquals(component, copy);
        assertNotSame(component, copy);
    }

}
