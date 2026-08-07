package net.lenni0451.mcstructs.text.components;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TranslationComponentTest {

    private static final String TRANSLATION_KEY = "test";
    private static final String TRANSLATION = "%%test %2$s %1$s";
    private static final TranslationComponent COMPONENT = new TranslationComponent(TRANSLATION_KEY, "test1", new StringComponent("test2"));

    @Test
    void getKey() {
        assertEquals(TRANSLATION_KEY, COMPONENT.getKey());
    }

    @Test
    void getArgs() {
        assertEquals("test1", COMPONENT.getArgs()[0]);
        assertEquals(new StringComponent("test2"), COMPONENT.getArgs()[1]);
    }

    @Test
    @Order(0)
    void setTranslator() {
        COMPONENT.setTranslator(s -> TRANSLATION);
    }

    @Test
    @Order(1)
    void asSingleString() {
        assertEquals("%test test2 test1", COMPONENT.asSingleString());
    }

    @Test
    void copy() {
        TranslationComponent copy = (TranslationComponent) COMPONENT.copy();
        assertEquals(COMPONENT, copy);
        assertNotSame(COMPONENT, copy);
    }

    @Test
    void shallowCopy() {
        TranslationComponent copy = (TranslationComponent) COMPONENT.copy();
        copy.append("Test");
        assertEquals(1, copy.getSiblings().size());
        assertEquals(0, copy.shallowCopy().getSiblings().size());
    }

}
