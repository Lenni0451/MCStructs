package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class EntityNbtComponentTest {

    @Test
    void copy() {
        EntityNbtComponent component = new EntityNbtComponent("test", true, new StringComponent("separator"), "selector");
        ATextComponent copy = component.copy();

        assertEquals(component, copy);
        assertNotSame(component, copy);
    }

}
