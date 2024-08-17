package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class BlockNbtComponentTest {

    private static final BlockNbtComponent component = new BlockNbtComponent("test", true, new StringComponent("separator"), "pos");

    @Test
    void copy() {
        ATextComponent copy = component.copy();
        assertEquals(component, copy);
        assertNotSame(component, copy);
    }

    @Test
    void shallowCopy() {
        BlockNbtComponent copy = (BlockNbtComponent) component.copy();
        copy.append("Test");
        assertEquals(1, copy.getSiblings().size());
        assertEquals(0, copy.shallowCopy().getSiblings().size());
    }

}
