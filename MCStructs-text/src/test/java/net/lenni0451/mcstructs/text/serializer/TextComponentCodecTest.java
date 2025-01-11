package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.JsonElement;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.ListTag;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtSource;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtSource;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtSource;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.LegacyHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TextComponentCodecTest {

    private final TextComponent text = new StringComponent("test")
            .append(new TranslationComponent("translation", "arg1", (byte) 2))
            .append(new ScoreComponent("name", "objective"))
            .append(new SelectorComponent("selector", new StringComponent("separator")))
            .append(new NbtComponent("raw", true, new StringComponent("separator"), new BlockNbtSource("pos")))
            .append(new NbtComponent("raw", true, new StringComponent("separator"), new EntityNbtSource("selector")))
            .append(new NbtComponent("raw", true, new StringComponent("separator"), new StorageNbtSource(Identifier.of("namespace", "id"))))
            .append(new StringComponent("hover text").setStyle(new Style().setHoverEvent(new TextHoverEvent(new StringComponent("text")))))
            .append(new StringComponent("hover item").setStyle(new Style().setHoverEvent(new ItemHoverEvent(Identifier.of("stone"), 64, new CompoundTag().add("display", new CompoundTag().addString("Name", "name"))))))
            .append(new StringComponent("hover entity").setStyle(new Style().setHoverEvent(new EntityHoverEvent(Identifier.of("player"), UUID.randomUUID(), new StringComponent("name")))))
            .append(new StringComponent("style").setStyle(new Style().setFormatting(TextFormatting.ALL.values().toArray(new TextFormatting[0])).setClickEvent(ClickEvent.openURL(URI.create("https://example.com"))).setFont(Identifier.of("font")).setInsertion("insertion")));

    @Test
    void serializeDeserializeJson() {
        JsonElement json = TextComponentCodec.LATEST.serializeJsonTree(this.text);
        TextComponent deserialized = TextComponentCodec.LATEST.deserializeJsonTree(json);
        assertEquals(this.text, deserialized);
    }

    @Test
    void serializeDeserializeNbt() {
        NbtTag nbt = TextComponentCodec.LATEST.serializeNbt(this.text);
        TextComponent deserialized = TextComponentCodec.LATEST.deserializeNbtTree(nbt);
        assertEquals(this.text, deserialized);
    }

    @Test
    void legacyItemDeserialization() throws SNbtSerializeException {
        TextComponent legacyComponent = new StringComponent("test")
                .setStyle(new Style()
                        .setHoverEvent(new LegacyHoverEvent(HoverEventAction.SHOW_ITEM, new LegacyHoverEvent.LegacyStringItemData("stone", (byte) 5, (short) 0, null)))
                );

        JsonElement legacyJson = TextComponentSerializer.V1_12.serializeJson(legacyComponent);
        TextComponent modernDeserialized = TextComponentCodec.V1_21_4.deserialize(legacyJson);
        HoverEvent hoverEvent = modernDeserialized.getStyle().getHoverEvent();
        ItemHoverEvent itemHoverEvent = assertInstanceOf(ItemHoverEvent.class, hoverEvent);
        assertEquals(Identifier.of("stone"), itemHoverEvent.getItem());
//        assertEquals(5, itemHoverEvent.getCount()); //The 1.20.5 version broke the legacy deserialize. The count is now lowercase
    }

    @Test
    void legacyEntityDeserialization() throws SNbtSerializeException {
        UUID randomUUID = UUID.randomUUID();
        TextComponent legacyComponent = new StringComponent("test")
                .setStyle(new Style()
                        .setHoverEvent(new LegacyHoverEvent(HoverEventAction.SHOW_ENTITY, new LegacyHoverEvent.LegacyEntityData("{\"text\":\"test\"}", "cow", randomUUID.toString())))
                );

        JsonElement legacyJson = TextComponentSerializer.V1_12.serializeJson(legacyComponent);
        TextComponent modernDeserialized = TextComponentCodec.V1_21_4.deserialize(legacyJson);
        HoverEvent hoverEvent = modernDeserialized.getStyle().getHoverEvent();
        EntityHoverEvent entityHoverEvent = assertInstanceOf(EntityHoverEvent.class, hoverEvent);
        assertEquals(new StringComponent("test"), entityHoverEvent.getName());
        assertEquals(Identifier.of("cow"), entityHoverEvent.getEntityType());
        assertEquals(randomUUID, entityHoverEvent.getUuid());
    }

    @Test
    void arrayWithTag() {
        ListTag<NbtTag> tags = new ListTag<>()
                .add(new CompoundTag()
                        .addString("translate", "test")
                        .addByteArray("with", (byte) 1, (byte) 2, (byte) 3))
                .add(new CompoundTag()
                        .addString("translate", "test")
                        .addIntArray("with", 1, 2, 3))
                .add(new CompoundTag()
                        .addString("translate", "test")
                        .addLongArray("with", 1, 2, 3))
                .add(new CompoundTag()
                        .addString("translate", "test")
                        .addList("with", 1, 2, 3));
        TextComponent component = new TranslationComponent("test", (byte) 1, (byte) 2, (byte) 3)
                .append(new TranslationComponent("test", 1, 2, 3))
                .append(new TranslationComponent("test", 1L, 2L, 3L))
                .append(new TranslationComponent("test", 1, 2, 3));

        TextComponent nbtComponent = TextComponentCodec.LATEST.deserializeNbtTree(tags);
        assertEquals(component, nbtComponent);
    }

}
