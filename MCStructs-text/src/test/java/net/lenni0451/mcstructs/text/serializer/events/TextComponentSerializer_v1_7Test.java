package net.lenni0451.mcstructs.text.serializer.events;

import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextComponentSerializer_v1_7Test extends TextComponentLegacyEventsTest {

    @Override
    protected TextComponentSerializer getSerializer() {
        return TextComponentSerializer.V1_7;
    }

    @Test
    void testSuccess() {
        this.testSuccess(CLICK_OPEN_URL);
        this.testSuccess(CLICK_OPEN_FILE);
        this.testSuccess(CLICK_RUN_COMMAND);
        this.testSuccess(CLICK_SUGGEST_COMMAND);
        this.testSuccess(CLICK_CHANGE_PAGE);

        this.testSuccess(HOVER_TEXT);
        this.testSuccess(HOVER_ACHIEVEMENT);
        this.testSuccess(HOVER_ITEM_LEGACY);
    }

    @Test
    void testFailure() {
        this.testFailure(CLICK_TWITCH_USER_INFO);
        this.testFailure(CLICK_COPY_TO_CLIPBOARD);

        this.testFailure(HOVER_ITEM); //Modern item hover events are just skipped
        this.testFailure(HOVER_ENTITY); //Modern entity hover events are just skipped
        this.testFailure(HOVER_ENTITY_LEGACY);
    }

    @Test
    void testSkippedEvents() {
        //Modern item hover
        assertEquals(
                new StringComponent(""),
                this.getSerializer().deserialize("{\"hoverEvent\":{\"action\":\"show_item\",\"contents\":{\"id\":\"minecraft:test\",\"tag\":\"{test:\\\"test\\\"}\"}},\"text\":\"\"}")
        );
        //Modern entity hover
        assertEquals(
                new StringComponent(""),
                this.getSerializer().deserialize("{\"hoverEvent\":{\"action\":\"show_entity\",\"contents\":{\"id\":\"1dcbdfea-2afe-464d-ba06-50f99f2db01a\",\"name\":{\"text\":\"test\"},\"type\":\"minecraft:test\"}},\"text\":\"\"}")
        );
    }

}
