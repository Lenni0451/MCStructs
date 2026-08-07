package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.click.types.ChangePageClickEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.font.ResourceFont;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StyleTest {

    private static final Style STYLE = new Style();

    @Test
    void setFormatting() {
    }

    @Test
    @Order(0)
    void setColor() {
        STYLE.setColor(0xFF0000);
    }

    @Test
    @Order(1)
    void getColor() {
        assertTrue(STYLE.getColor().isRGBColor());
        assertEquals(0xFF0000, STYLE.getColor().getRgbValue());
    }

    @Test
    @Order(0)
    void setShadowColor() {
        STYLE.setShadowColor(0x00FF00);
    }

    @Test
    @Order(1)
    void getShadowColor() {
        assertEquals(0x00FF00, STYLE.getShadowColor());
    }

    @Test
    @Order(0)
    void setBold() {
        STYLE.setBold(true);
    }

    @Test
    @Order(1)
    void getBold() {
        assertNotNull(STYLE.getBold());
        assertTrue(STYLE.getBold());
    }

    @Test
    @Order(1)
    void isBold() {
        assertTrue(STYLE.isBold());
    }

    @Test
    @Order(0)
    void setItalic() {
        STYLE.setItalic(true);
    }

    @Test
    @Order(1)
    void getItalic() {
        assertNotNull(STYLE.getItalic());
        assertTrue(STYLE.getItalic());
    }

    @Test
    @Order(1)
    void isItalic() {
        assertTrue(STYLE.isItalic());
    }

    @Test
    @Order(0)
    void setUnderlined() {
        STYLE.setUnderlined(true);
    }

    @Test
    @Order(1)
    void getUnderlined() {
        assertNotNull(STYLE.getUnderlined());
        assertTrue(STYLE.getUnderlined());
    }

    @Test
    @Order(1)
    void isUnderlined() {
        assertTrue(STYLE.isUnderlined());
    }

    @Test
    @Order(0)
    void setStrikethrough() {
        STYLE.setStrikethrough(true);
    }

    @Test
    @Order(1)
    void getStrikethrough() {
        assertNotNull(STYLE.getStrikethrough());
        assertTrue(STYLE.getStrikethrough());
    }

    @Test
    @Order(1)
    void isStrikethrough() {
        assertTrue(STYLE.isStrikethrough());
    }

    @Test
    @Order(0)
    void setObfuscated() {
        STYLE.setObfuscated(true);
    }

    @Test
    @Order(1)
    void getObfuscated() {
        assertNotNull(STYLE.getObfuscated());
        assertTrue(STYLE.getObfuscated());
    }

    @Test
    @Order(1)
    void isObfuscated() {
        assertTrue(STYLE.isObfuscated());
    }

    @Test
    @Order(0)
    void setClickEvent() {
        STYLE.setClickEvent(ClickEvent.changePage(1));
    }

    @Test
    @Order(1)
    void getClickEvent() {
        assertNotNull(STYLE.getClickEvent());
        assertEquals(ClickEventAction.CHANGE_PAGE, STYLE.getClickEvent().getAction());
        assertInstanceOf(ChangePageClickEvent.class, STYLE.getClickEvent());
        assertEquals(1, ((ChangePageClickEvent) STYLE.getClickEvent()).asInt());
    }

    @Test
    @Order(0)
    void setHoverEvent() {
        STYLE.setHoverEvent(new ItemHoverEvent(Identifier.of("stone"), 1, new CompoundTag()));
    }

    @Test
    @Order(1)
    void getHoverEvent() {
        ItemHoverEvent itemHoverEvent = (ItemHoverEvent) STYLE.getHoverEvent();
        assertNotNull(STYLE.getHoverEvent());
        assertEquals(HoverEventAction.SHOW_ITEM, STYLE.getHoverEvent().getAction());
        assertEquals(Identifier.of("stone"), itemHoverEvent.asModern().getId());
        assertEquals(1, itemHoverEvent.asModern().getCount());
        assertNotNull(itemHoverEvent.asModern().getTag());
    }

    @Test
    @Order(0)
    void setInsertion() {
        STYLE.setInsertion("test");
    }

    @Test
    @Order(1)
    void getInsertion() {
        assertEquals("test", STYLE.getInsertion());
    }

    @Test
    @Order(0)
    void setFont() {
        STYLE.setFont(Identifier.of("minecraft:default"));
    }

    @Test
    @Order(1)
    void getFont() {
        assertEquals(new ResourceFont(Identifier.of("minecraft:default")), STYLE.getFont());
    }

    @Test
    @Order(-1)
    void isEmpty() {
        assertTrue(STYLE.isEmpty());
    }

    @Test
    @Order(2)
    void isNotEmpty() {
        assertFalse(STYLE.isEmpty());
    }

    @Test
    @Order(2)
    void copy() {
        Style copy = STYLE.copy();
        assertEquals(STYLE, copy);
        assertNotSame(STYLE, copy);
    }

}
