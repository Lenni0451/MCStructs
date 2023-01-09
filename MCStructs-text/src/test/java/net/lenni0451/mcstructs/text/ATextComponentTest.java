package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;
import net.lenni0451.mcstructs.text.utils.JsonUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    void forEach() {
        ATextComponent component = new StringComponent("A").append(new StringComponent("B").append(new StringComponent("C")));
        int[] i = {0};
        component.forEach(comp -> {
            switch (i[0]) {
                case 0:
                    assertEquals("A", comp.asSingleString());
                    break;
                case 1:
                    assertEquals("B", comp.asSingleString());
                    break;
                case 2:
                    assertEquals("C", comp.asSingleString());
                    break;
            }
            i[0]++;
        });
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

    @Test
    void parentStyle() {
        String rawComponent = "{\"bold\":true,\"color\":\"red\",\"extra\":[{\"extra\":[{\"text\":\"test\"}],\"text\":\"test\"}],\"text\":\"Test\"}";
        ATextComponent component = TextComponentSerializer.V1_18.deserialize(rawComponent);
        String serialized = JsonUtils.toSortedString(TextComponentSerializer.V1_18.serializeJson(component), null);
        assertEquals(rawComponent, serialized);
    }

    @Test
    void copiedParentStyle() {
        String rawComponent = "{\"bold\":true,\"color\":\"red\",\"extra\":[{\"extra\":[{\"text\":\"test\"}],\"text\":\"test\"}],\"text\":\"Test\"}";
        ATextComponent component = TextComponentSerializer.V1_18.deserialize(rawComponent);
        component.copyParentStyle();
        String serialized = JsonUtils.toSortedString(TextComponentSerializer.V1_18.serializeJson(component), null);
        assertEquals("{\"bold\":true,\"color\":\"red\",\"extra\":[{\"bold\":true,\"color\":\"red\",\"extra\":[{\"bold\":true,\"color\":\"red\",\"text\":\"test\"}],\"text\":\"test\"}],\"text\":\"Test\"}", serialized);
        assertNotEquals(rawComponent, serialized);
    }

}
