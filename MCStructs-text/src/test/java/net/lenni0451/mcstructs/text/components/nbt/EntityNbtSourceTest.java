package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.NbtComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class EntityNbtSourceTest {

    private static final NbtComponent component = new NbtComponent("test", true, new StringComponent("separator"), new EntityNbtSource("selector"));

    @Test
    void copy() {
        TextComponent copy = component.copy();
        assertEquals(component, copy);
        assertNotSame(component, copy);
    }

    @Test
    void shallowCopy() {
        NbtComponent copy = (NbtComponent) component.copy();
        copy.append("Test");
        assertEquals(1, copy.getSiblings().size());
        assertEquals(0, copy.shallowCopy().getSiblings().size());
    }

}
