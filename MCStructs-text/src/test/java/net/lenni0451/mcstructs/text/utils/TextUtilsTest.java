package net.lenni0451.mcstructs.text.utils;

import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.components.TranslationComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextUtilsTest {

    @Test
    void makeURLsClickable() {
        String url = "https://lenni0451.net";

        TextComponent component = new StringComponent("Check out my website: " + url + " it's awesome!");
        component = TextUtils.makeURLsClickable(component);
        assertEquals(new StringComponent("")
                        .append("Check out my website: ")
                        .append(new StringComponent(url).setStyle(new Style().setClickEvent(ClickEvent.openURL(URI.create(url)))))
                        .append(" it's awesome!"),
                component);
    }

    @Test
    void replaceRGBColors() {
        TextComponent component = new StringComponent("A").setStyle(new Style().setColor(0xFF_00_00)).append(new StringComponent("B").setStyle(new Style().setColor(0x00_FF_00))).append(new StringComponent("C").setStyle(new Style().setColor(0x00_00_FF)));
        TextComponent replaced = TextUtils.replaceRGBColors(component);

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
        TextComponent a = new StringComponent("A");
        TextComponent b = new StringComponent("B");
        TextComponent c = new StringComponent("C");

        TextComponent joined = TextUtils.join(new StringComponent(" "), a, b, c);
        assertEquals(new StringComponent("").append(a).append(" ").append(b).append(" ").append(c), joined);
    }

    @Test
    void iterateAll() {
        TextComponent component = new TranslationComponent("%s", new StringComponent("A").append("B")).append("C").styled(style -> style.setHoverEvent(new TextHoverEvent(HoverEventAction.SHOW_TEXT, new StringComponent("D"))));
        StringBuilder out = new StringBuilder();
        TextUtils.iterateAll(component, comp -> out.append(comp.asSingleString()));
        //'AB' from the translation component filled with the args
        //'A' from the translation argument
        //'B' from the sibling of the translation argument
        //'D' from the hover event
        //'C' from the sibling of the translation component
        assertEquals("ABABDC", out.toString());
    }

    @Test
    void split() {
        TextComponent component = new StringComponent("A B C");
        TextComponent[] split = TextUtils.split(component, " ", false);
        assertEquals(3, split.length);
        assertEquals(new StringComponent("A"), split[0]);
        assertEquals(new StringComponent("B"), split[1]);
        assertEquals(new StringComponent("C"), split[2]);
    }

    @Test
    void splitEmpty() {
        TextComponent component = new StringComponent("");
        TextComponent[] split = TextUtils.split(component, " ", false);
        assertEquals(1, split.length);
        assertEquals(new StringComponent(""), split[0]);
    }

    @Test
    void splitOnlySplitter() {
        TextComponent component = new StringComponent("   ");
        TextComponent[] split = TextUtils.split(component, " ", false);
        assertEquals(4, split.length);
        for (TextComponent textComponent : split) assertEquals(new StringComponent(""), textComponent);
    }

    @Test
    void splitSiblings() {
        TextComponent component = new StringComponent("A").append(" ").append("B").append(" ").append("C");
        TextComponent[] split = TextUtils.split(component, " ", true);
        assertEquals(3, split.length);
        assertEquals(new StringComponent("A"), split[0]);
        assertEquals(new StringComponent("B"), split[1]);
        assertEquals(new StringComponent("C"), split[2]);
    }

    @Test
    void splitEmptySplitter() {
        TextComponent component = new StringComponent("A B C");
        TextComponent[] split = TextUtils.split(component, "", false);
        assertEquals(6, split.length);
        assertEquals(new StringComponent("A"), split[0]);
        assertEquals(new StringComponent(" "), split[1]);
        assertEquals(new StringComponent("B"), split[2]);
        assertEquals(new StringComponent(" "), split[3]);
        assertEquals(new StringComponent("C"), split[4]);
        assertEquals(new StringComponent(""), split[5]);
    }

}
