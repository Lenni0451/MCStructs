package net.lenni0451.mcstructs.text.serializer.events;

import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.LegacyHoverEvent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextComponentSerializer_v1_7Test extends TextComponentLegacyEventsTest {

    @Override
    protected TextComponentSerializer getSerializer() {
        return TextComponentSerializer.V1_7;
    }

    @Test
    void testSuccess() {
        this.testSuccess(CLICK_OPEN_URL);
        this.testSuccess(CLICK_OPEN_URL_LEGACY);
        this.testSuccess(CLICK_OPEN_FILE);
        this.testSuccess(CLICK_RUN_COMMAND);
        this.testSuccess(CLICK_SUGGEST_COMMAND);
        this.testSuccess(CLICK_CHANGE_PAGE);
        this.testSuccess(CLICK_CHANGE_PAGE_LEGACY);

        this.testSuccess(HOVER_TEXT);
        this.testSuccess(HOVER_ACHIEVEMENT);
        this.testSuccess(HOVER_INT_ITEM_LEGACY
                .withSerialized("{\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:1s,Count:1b,tag:{test:\\\"test\\\",},Damage:1s,}\"},\"text\":\"\"}"));
        this.testSuccess(HOVER_INVALID_ITEM_LEGACY);
    }

    @Test
    void testFailure() {
        this.testFailure(CLICK_TWITCH_USER_INFO);
        this.testFailure(CLICK_COPY_TO_CLIPBOARD);

        this.testFailure(HOVER_ITEM); //Modern item hover events are just skipped
        this.testFailure(HOVER_ENTITY); //Modern entity hover events are just skipped
        this.testFailure(HOVER_ENTITY_LEGACY
                .withSerialized("{\"hoverEvent\":{\"action\":\"show_entity\",\"value\":\"\"},\"text\":\"test\"}"));
        this.testFailure(HOVER_INVALID_ENTITY_LEGACY);
    }

    @Test
    void testSkippedEvents() {
        assertEquals( //Modern item hover
                new StringComponent(""),
                this.getSerializer().deserialize("{\"hoverEvent\":{\"action\":\"show_item\",\"contents\":{\"id\":\"minecraft:test\",\"tag\":\"{test:\\\"test\\\"}\"}},\"text\":\"\"}")
        );
        assertEquals( //Modern entity hover
                new StringComponent(""),
                this.getSerializer().deserialize("{\"hoverEvent\":{\"action\":\"show_entity\",\"contents\":{\"id\":\"1dcbdfea-2afe-464d-ba06-50f99f2db01a\",\"name\":{\"text\":\"test\"},\"type\":\"minecraft:test\"}},\"text\":\"\"}")
        );
    }

    @Test
    void testStringItem() {
        //1.7 did not support string items which makes deserializing them special
        //The 1.7 deserializer takes the valid component and calls CompoundTag#getShort("id") on it
        //Since the compound contains a string value, the default value of 0 is returned and the deserialization succeeds
        assertThrows(Throwable.class, () -> this.getSerializer().serialize(HOVER_STRING_ITEM_LEGACY.component));
        assertEquals(
                new StringComponent("").styled(style -> style.setHoverEvent(new LegacyHoverEvent(HoverEventAction.SHOW_ITEM, new LegacyHoverEvent.LegacyIntItemData((short) 0, (byte) 1, (short) 1, new CompoundTag().addString("test", "test"))))),
                this.getSerializer().deserialize("{\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"{id:stone,Count:1b,tag:{test:\\\"test\\\",},Damage:1s,}\"},\"text\":\"\"}")
        );
    }

}
