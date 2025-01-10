package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.click.types.ChangePageClickEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StyleTest {

    private static final Style style = new Style();

    @Test
    void setFormatting() {
    }

    @Test
    @Order(0)
    void setColor() {
        style.setColor(0xFF0000);
    }

    @Test
    @Order(1)
    void getColor() {
        assertTrue(style.getColor().isRGBColor());
        assertEquals(0xFF0000, style.getColor().getRgbValue());
    }

    @Test
    @Order(0)
    void setShadowColor() {
        style.setShadowColor(0x00FF00);
    }

    @Test
    @Order(1)
    void getShadowColor() {
        assertEquals(0x00FF00, style.getShadowColor());
    }

    @Test
    @Order(0)
    void setBold() {
        style.setBold(true);
    }

    @Test
    @Order(1)
    void getBold() {
        assertNotNull(style.getBold());
        assertTrue(style.getBold());
    }

    @Test
    @Order(1)
    void isBold() {
        assertTrue(style.isBold());
    }

    @Test
    @Order(0)
    void setItalic() {
        style.setItalic(true);
    }

    @Test
    @Order(1)
    void getItalic() {
        assertNotNull(style.getItalic());
        assertTrue(style.getItalic());
    }

    @Test
    @Order(1)
    void isItalic() {
        assertTrue(style.isItalic());
    }

    @Test
    @Order(0)
    void setUnderlined() {
        style.setUnderlined(true);
    }

    @Test
    @Order(1)
    void getUnderlined() {
        assertNotNull(style.getUnderlined());
        assertTrue(style.getUnderlined());
    }

    @Test
    @Order(1)
    void isUnderlined() {
        assertTrue(style.isUnderlined());
    }

    @Test
    @Order(0)
    void setStrikethrough() {
        style.setStrikethrough(true);
    }

    @Test
    @Order(1)
    void getStrikethrough() {
        assertNotNull(style.getStrikethrough());
        assertTrue(style.getStrikethrough());
    }

    @Test
    @Order(1)
    void isStrikethrough() {
        assertTrue(style.isStrikethrough());
    }

    @Test
    @Order(0)
    void setObfuscated() {
        style.setObfuscated(true);
    }

    @Test
    @Order(1)
    void getObfuscated() {
        assertNotNull(style.getObfuscated());
        assertTrue(style.getObfuscated());
    }

    @Test
    @Order(1)
    void isObfuscated() {
        assertTrue(style.isObfuscated());
    }

    @Test
    @Order(0)
    void setClickEvent() {
        style.setClickEvent(ClickEvent.changePage(1));
    }

    @Test
    @Order(1)
    void getClickEvent() {
        assertNotNull(style.getClickEvent());
        assertEquals(ClickEventAction.CHANGE_PAGE, style.getClickEvent().getAction());
        assertInstanceOf(ChangePageClickEvent.class, style.getClickEvent());
        assertEquals(1, ((ChangePageClickEvent) style.getClickEvent()).getPage());
    }

    @Test
    @Order(0)
    void setHoverEvent() {
        style.setHoverEvent(new ItemHoverEvent(HoverEventAction.SHOW_ITEM, Identifier.of("stone"), 1, new CompoundTag()));
    }

    @Test
    @Order(1)
    void getHoverEvent() {
        ItemHoverEvent itemHoverEvent = (ItemHoverEvent) style.getHoverEvent();
        assertNotNull(style.getHoverEvent());
        assertEquals(HoverEventAction.SHOW_ITEM, style.getHoverEvent().getAction());
        assertEquals(Identifier.of("stone"), itemHoverEvent.getItem());
        assertEquals(1, itemHoverEvent.getCount());
        assertNotNull(itemHoverEvent.getNbt());
    }

    @Test
    @Order(0)
    void setInsertion() {
        style.setInsertion("test");
    }

    @Test
    @Order(1)
    void getInsertion() {
        assertEquals("test", style.getInsertion());
    }

    @Test
    @Order(0)
    void setFont() {
        style.setFont(Identifier.of("minecraft:default"));
    }

    @Test
    @Order(1)
    void getFont() {
        assertEquals(Identifier.of("minecraft:default"), style.getFont());
    }

    @Test
    @Order(-1)
    void isEmpty() {
        assertTrue(style.isEmpty());
    }

    @Test
    @Order(2)
    void isNotEmpty() {
        assertFalse(style.isEmpty());
    }

    @Test
    @Order(2)
    void copy() {
        Style copy = style.copy();
        assertEquals(style, copy);
        assertNotSame(style, copy);
    }

}
