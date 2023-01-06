package net.lenni0451.mcstructs.text.utils;

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
                        .append(" it's awesome!")
                , component);
    }

}