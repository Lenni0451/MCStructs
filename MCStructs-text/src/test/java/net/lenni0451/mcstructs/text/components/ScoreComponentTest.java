package net.lenni0451.mcstructs.text.components;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ScoreComponentTest {

    private static final ScoreComponent COMPONENT = new ScoreComponent("name", "objective");

    @Test
    void getName() {
        assertEquals("name", COMPONENT.getName());
    }

    @Test
    void getObjective() {
        assertEquals("objective", COMPONENT.getObjective());
    }

    @Test
    @Order(0)
    void getValue() {
        assertNull(COMPONENT.getValue());
    }

    @Test
    @Order(1)
    void setValue() {
        COMPONENT.setValue("value");
        assertEquals("value", COMPONENT.getValue());
    }

    @Test
    @Order(2)
    void asSingleString() {
        assertEquals("value", COMPONENT.asSingleString());
    }

    @Test
    @Order(2)
    void copy() {
        ScoreComponent copy = (ScoreComponent) COMPONENT.copy();
        assertEquals(COMPONENT, copy);
        assertNotSame(COMPONENT, copy);
    }

    @Test
    @Order(2)
    void shallowCopy() {
        ScoreComponent copy = (ScoreComponent) COMPONENT.copy();
        copy.append("Test");
        assertEquals(1, copy.getSiblings().size());
        assertEquals(0, copy.shallowCopy().getSiblings().size());
    }

}
