package net.lenni0451.mcstructs.text.components;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ScoreComponentTest {

    private static final ScoreComponent component = new ScoreComponent("name", "objective");

    @Test
    void getName() {
        assertEquals("name", component.getName());
    }

    @Test
    void getObjective() {
        assertEquals("objective", component.getObjective());
    }

    @Test
    @Order(0)
    void getValue() {
        assertNull(component.getValue());
    }

    @Test
    @Order(1)
    void setValue() {
        component.setValue("value");
        assertEquals("value", component.getValue());
    }

    @Test
    @Order(2)
    void asSingleString() {
        assertEquals("value", component.asSingleString());
    }

    @Test
    @Order(2)
    void copy() {
        ScoreComponent copy = (ScoreComponent) component.copy();
        assertEquals(component, copy);
        assertNotSame(component, copy);
    }

    @Test
    @Order(2)
    void shallowCopy() {
        ScoreComponent copy = (ScoreComponent) component.copy();
        copy.append("Test");
        assertEquals(1, copy.getSiblings().size());
        assertEquals(0, copy.shallowCopy().getSiblings().size());
    }

}
