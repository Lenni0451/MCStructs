package net.lenni0451.mcstructs.text.utils;

import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextUtilsTest {

    @Test
    void makeURLsClickable() {
        String url = "https://lenni0451.net";

        ATextComponent component = new StringComponent("Check out my website: " + url + " it's awesome!");
        component = TextUtils.makeURLsClickable(component);
        assertEquals(new StringComponent("")
                        .append("Check out my website: ")
                        .append(new StringComponent(url).setStyle(new Style().setClickEvent(new ClickEvent(ClickEventAction.OPEN_URL, url))))
                        .append(" it's awesome!"),
                component);
    }

    @Test
    void replaceRGBColors() {
        ATextComponent component = new StringComponent("A").setStyle(new Style().setColor(0xFF_00_00)).append(new StringComponent("B").setStyle(new Style().setColor(0x00_FF_00))).append(new StringComponent("C").setStyle(new Style().setColor(0x00_00_FF)));
        ATextComponent replaced = TextUtils.replaceRGBColors(component);

        int[] i = {0};
        replaced.forEach(comp -> {
            switch (i[0]) {
                case 0:
                    assertEquals(TextFormatting.DARK_RED, comp.getStyle().getColor());
                    break;
                case 1:
                    assertEquals(TextFormatting.DARK_GREEN, comp.getStyle().getColor());
                    break;
                case 2:
                    assertEquals(TextFormatting.DARK_BLUE, comp.getStyle().getColor());
                    break;
            }
            i[0]++;
        });
    }

    @Test
    void join() {
        ATextComponent a = new StringComponent("A");
        ATextComponent b = new StringComponent("B");
        ATextComponent c = new StringComponent("C");

        ATextComponent joined = TextUtils.join(new StringComponent(" "), a, b, c);
        assertEquals(new StringComponent("").append(a).append(" ").append(b).append(" ").append(c), joined);
    }

}
