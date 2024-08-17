package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class StorageNbtComponentTest {

    private static final StorageNbtComponent component = new StorageNbtComponent("test", true, new StringComponent("separator"), Identifier.of("id"));

    @Test
    void copy() {
        ATextComponent copy = component.copy();
        assertEquals(component, copy);
        assertNotSame(component, copy);
    }

    @Test
    void shallowCopy() {
        StorageNbtComponent copy = (StorageNbtComponent) component.copy();
        copy.append("Test");
        assertEquals(1, copy.getSiblings().size());
        assertEquals(0, copy.shallowCopy().getSiblings().size());
    }

}
