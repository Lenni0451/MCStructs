package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.components.TranslationComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;
import net.lenni0451.mcstructs.text.utils.JsonUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TextComponentTest {

    private static TextComponent component;

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
        TextComponent component = new StringComponent("A").append(new StringComponent("B").append(new StringComponent("C")));
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
        assertEquals("§c§lHello§r §9§oWorld", component.asLegacyFormatString());
        assertEquals(
                "§4Hello §rWorld",
                new StringComponent()
                        .append(new StringComponent("Hello ").styled(style -> style.setFormatting(TextFormatting.DARK_RED)))
                        .append(new StringComponent("World"))
                        .asLegacyFormatString()
        );
        assertEquals(
                "§cHello§r§f §r§9World",
                new TranslationComponent("%s", new StringComponent("")
                        .styled(style -> style.setFormatting(TextFormatting.WHITE))
                        .append(new StringComponent("Hello").styled(style -> style.setFormatting(TextFormatting.RED)))
                        .append(" ")
                        .append(new StringComponent("World").styled(style -> style.setFormatting(TextFormatting.BLUE)))
                )
                        .styled(style -> style.setFormatting(TextFormatting.WHITE))
                        .asLegacyFormatString()
        );
        assertEquals(
                "§cHello§r§e World",
                new TranslationComponent("%s World", new StringComponent("Hello").styled(style -> style.setFormatting(TextFormatting.RED))).styled(style -> style.setFormatting(TextFormatting.YELLOW)).asLegacyFormatString()
        );
    }

    @Test
    void asSingleString() {
        assertEquals("", component.asSingleString());
    }

    @Test
    void parentStyle() {
        String rawComponent = "{\"bold\":true,\"color\":\"red\",\"extra\":[{\"extra\":[{\"text\":\"test\"}],\"text\":\"test\"}],\"text\":\"Test\"}";
        TextComponent component = TextComponentSerializer.V1_18.deserialize(rawComponent);
        String serialized = JsonUtils.toSortedString(TextComponentSerializer.V1_18.serializeJson(component), null);
        assertEquals(rawComponent, serialized);
    }

    @Test
    void copiedParentStyle() {
        String rawComponent = "{\"bold\":true,\"color\":\"red\",\"extra\":[{\"extra\":[{\"text\":\"test\"}],\"text\":\"test\"}],\"text\":\"Test\"}";
        TextComponent component = TextComponentSerializer.V1_18.deserialize(rawComponent);
        component.setSiblingParentStyle();
        String serialized = JsonUtils.toSortedString(TextComponentSerializer.V1_18.serializeJson(component), null);
        assertEquals("{\"bold\":true,\"color\":\"red\",\"extra\":[{\"bold\":true,\"color\":\"red\",\"extra\":[{\"bold\":true,\"color\":\"red\",\"text\":\"test\"}],\"text\":\"test\"}],\"text\":\"Test\"}", serialized);
        assertNotEquals(rawComponent, serialized);
    }

}
