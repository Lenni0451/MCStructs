package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ATextComponentTest {

    private static ATextComponent component;

    @BeforeAll
    static void init() {
        StringComponent hello = new StringComponent("Hello");
        hello.getStyle().setFormatting(TextFormatting.RED);
        hello.getStyle().setBold(true);

        StringComponent space = new StringComponent(" ");

        StringComponent world = new StringComponent("World");
        world.getStyle().setFormatting(TextFormatting.BLUE);
        world.getStyle().setItalic(true);

        component = new StringComponent("");
        component.append(hello);
        component.append(space);
        component.append(world);
    }

    @Test
    void asUnformattedString() {
        assertEquals("Hello World", component.asUnformattedString());
    }

    @Test
    void asLegacyFormatString() {
        assertEquals("§c§lHello §9§oWorld", component.asLegacyFormatString());
    }

    @Test
    void asSingleString() {
        assertEquals("", component.asSingleString());
    }

}
