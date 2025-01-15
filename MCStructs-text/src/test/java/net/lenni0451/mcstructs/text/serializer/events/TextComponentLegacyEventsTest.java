package net.lenni0451.mcstructs.text.serializer.events;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.With;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.click.types.*;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.AchievementHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;
import net.lenni0451.mcstructs.text.utils.JsonUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TextComponentLegacyEventsTest {

    protected static final TestComponent CLICK_OPEN_URL = new TestComponent(
            withClickEvent(new OpenUrlClickEvent("https://example.com")),
            "{\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://example.com\"},\"text\":\"\"}",
            ClickEventAction.OPEN_URL.isUserDefinable()
    );
    protected static final TestComponent CLICK_OPEN_FILE = new TestComponent(
            withClickEvent(new OpenFileClickEvent("example.txt")),
            "{\"clickEvent\":{\"action\":\"open_file\",\"value\":\"example.txt\"},\"text\":\"\"}",
            ClickEventAction.OPEN_FILE.isUserDefinable()
    );
    protected static final TestComponent CLICK_RUN_COMMAND = new TestComponent(
            withClickEvent(new RunCommandClickEvent("/say test")),
            "{\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/say test\"},\"text\":\"\"}",
            ClickEventAction.RUN_COMMAND.isUserDefinable()
    );
    protected static final TestComponent CLICK_TWITCH_USER_INFO = new TestComponent(
            withClickEvent(new TwitchUserInfoClickEvent("ExampleDude")),
            "{\"clickEvent\":{\"action\":\"twitch_user_info\",\"value\":\"ExampleDude\"},\"text\":\"\"}",
            ClickEventAction.TWITCH_USER_INFO.isUserDefinable()
    );
    protected static final TestComponent CLICK_SUGGEST_COMMAND = new TestComponent(
            withClickEvent(new SuggestCommandClickEvent("/say test")),
            "{\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/say test\"},\"text\":\"\"}",
            ClickEventAction.SUGGEST_COMMAND.isUserDefinable()
    );
    protected static final TestComponent CLICK_CHANGE_PAGE = new TestComponent(
            withClickEvent(new ChangePageClickEvent("2")),
            "{\"clickEvent\":{\"action\":\"change_page\",\"value\":\"2\"},\"text\":\"\"}",
            ClickEventAction.CHANGE_PAGE.isUserDefinable()
    );
    protected static final TestComponent CLICK_COPY_TO_CLIPBOARD = new TestComponent(
            withClickEvent(new CopyToClipboardClickEvent("test")),
            "{\"clickEvent\":{\"action\":\"copy_to_clipboard\",\"value\":\"test\"},\"text\":\"\"}",
            ClickEventAction.COPY_TO_CLIPBOARD.isUserDefinable()
    );

    protected static final TestComponent HOVER_TEXT = new TestComponent(
            withHoverEvent(new TextHoverEvent(TextComponent.of("test"))),
            "{\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"test\"},\"text\":\"\"}",
            HoverEventAction.SHOW_TEXT.isUserDefinable()
    );
    protected static final TestComponent HOVER_ACHIEVEMENT = new TestComponent(
            withHoverEvent(new AchievementHoverEvent("test")),
            "{\"hoverEvent\":{\"action\":\"show_achievement\",\"value\":\"test\"},\"text\":\"\"}",
            HoverEventAction.SHOW_ACHIEVEMENT.isUserDefinable()
    );
    protected static final TestComponent HOVER_ITEM = new TestComponent(
            withHoverEvent(new ItemHoverEvent(Identifier.of("test"), 1, new CompoundTag().addString("test", "test"))),
            "{\"hoverEvent\":{\"action\":\"show_item\",\"contents\":{\"id\":\"minecraft:test\",\"tag\":\"{test:\\\"test\\\"}\"}},\"text\":\"\"}",
            HoverEventAction.SHOW_ITEM.isUserDefinable()
    );
    protected static final TestComponent HOVER_INT_ITEM_LEGACY = new TestComponent(
            withHoverEvent(new ItemHoverEvent((short) 1, (byte) 1, (short) 1, new CompoundTag().addString("test", "test"))),
            "{", //Placeholder: this has to be filled in the test directly because of snbt serialization changes
            HoverEventAction.SHOW_ITEM.isUserDefinable()
    );
    protected static final TestComponent HOVER_STRING_ITEM_LEGACY = new TestComponent(
            withHoverEvent(new ItemHoverEvent("test", (byte) 1, (short) 1, new CompoundTag().addString("test", "test"))),
            "{", //Placeholder: this has to be filled in the test directly because of snbt serialization changes
            HoverEventAction.SHOW_ITEM.isUserDefinable()
    );
    protected static final TestComponent HOVER_ENTITY = new TestComponent(
            withHoverEvent(new EntityHoverEvent(Identifier.of("test"), UUID.randomUUID(), new StringComponent("test"))),
            "{\"hoverEvent\":{\"action\":\"show_entity\",\"contents\":{\"id\":\"3ea69abb-e9a0-4fe4-82c7-459a20a12da2\",\"name\":{\"text\":\"test\"},\"type\":\"minecraft:test\"}},\"text\":\"\"}",
            HoverEventAction.SHOW_ENTITY.isUserDefinable()
    );
    protected static final TestComponent HOVER_ENTITY_LEGACY = new TestComponent(
            withHoverEvent(new EntityHoverEvent("test", "test2", "test3")),
            "{", //Placeholder: this has to be filled in the test directly because of snbt serialization changes
            HoverEventAction.SHOW_ENTITY.isUserDefinable()
    );
    protected static final TestComponent HOVER_INVALID_ITEM_LEGACY = new TestComponent(
            withHoverEvent(new ItemHoverEvent("test")),
            "{\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"test\"},\"text\":\"\"}",
            HoverEventAction.SHOW_ENTITY.isUserDefinable()
    );
    protected static final TestComponent HOVER_INVALID_ENTITY_LEGACY = new TestComponent(
            withHoverEvent(new EntityHoverEvent(new StringComponent("test"))),
            "{\"hoverEvent\":{\"action\":\"show_entity\",\"value\":\"test\"},\"text\":\"\"}",
            HoverEventAction.SHOW_ENTITY.isUserDefinable()
    );

    private static TextComponent withClickEvent(final ClickEvent clickEvent) {
        return TextComponent.of("").styled(style -> style.setClickEvent(clickEvent));
    }

    private static TextComponent withHoverEvent(final HoverEvent hoverEvent) {
        return TextComponent.of("").styled(style -> style.setHoverEvent(hoverEvent));
    }


    protected abstract TextComponentSerializer getSerializer();

    protected final void testSuccess(final TestComponent component) {
        JsonElement serialized = this.getSerializer().serializeJson(component.component);
        assertEquals(component.serialized, JsonUtils.sort(serialized, String::compareToIgnoreCase).toString());
        if (component.canDeserialize) {
            TextComponent deserialized = this.getSerializer().deserialize(serialized);
            assertEquals(component.component, deserialized);
            assertEquals(component.serialized, JsonUtils.sort(this.getSerializer().serializeJson(deserialized), String::compareToIgnoreCase).toString());
        } else {
            //Actions which are not user definable can be serialized, but not deserialized
            assertNotEquals(component.component, this.getSerializer().deserialize(serialized));
        }
    }

    protected final void testFailure(final TestComponent component) {
        assertThrows(Throwable.class, () -> this.getSerializer().serialize(component.component));
        //Unsupported actions are ignored by the deserializer and should not throw an exception
        assertNotEquals(component.component, this.getSerializer().deserialize(component.serialized));
    }


    @With
    @AllArgsConstructor
    public static class TestComponent {
        public final TextComponent component;
        public final String serialized;
        public final boolean canDeserialize;
    }

}
