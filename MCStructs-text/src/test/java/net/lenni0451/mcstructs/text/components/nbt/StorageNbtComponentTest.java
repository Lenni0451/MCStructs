package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class StorageNbtComponentTest {

    @Test
    void copy() {
        StorageNbtComponent component = new StorageNbtComponent("test", true, new StringComponent("separator"), Identifier.of("id"));
        ATextComponent copy = component.copy();

        assertEquals(component, copy);
        assertNotSame(component, copy);
    }

}
