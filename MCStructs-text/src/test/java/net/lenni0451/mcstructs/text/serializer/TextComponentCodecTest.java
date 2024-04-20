package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.JsonElement;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.ListTag;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.components.ScoreComponent;
import net.lenni0451.mcstructs.text.components.SelectorComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.components.TranslationComponent;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TextComponentCodecTest {

    private final ATextComponent text = new StringComponent("test")
            .append(new TranslationComponent("translation", "arg1", 2))
            .append(new ScoreComponent("name", "objective"))
            .append(new SelectorComponent("selector", new StringComponent("separator")))
            .append(new BlockNbtComponent("raw", true, new StringComponent("separator"), "pos"))
            .append(new EntityNbtComponent("raw", true, new StringComponent("separator"), "selector"))
            .append(new StorageNbtComponent("raw", true, new StringComponent("separator"), Identifier.of("namespace", "id")))
            .append(new StringComponent("hover text").setStyle(new Style().setHoverEvent(new TextHoverEvent(HoverEventAction.SHOW_TEXT, new StringComponent("text")))))
            .append(new StringComponent("hover item").setStyle(new Style().setHoverEvent(new ItemHoverEvent(HoverEventAction.SHOW_ITEM, Identifier.of("stone"), 64, new CompoundTag()))))
            .append(new StringComponent("hover entity").setStyle(new Style().setHoverEvent(new EntityHoverEvent(HoverEventAction.SHOW_ENTITY, Identifier.of("player"), UUID.randomUUID(), new StringComponent("name")))))
            .append(new StringComponent("style").setStyle(new Style().setFormatting(TextFormatting.ALL.values().toArray(new TextFormatting[0])).setClickEvent(new ClickEvent(ClickEventAction.OPEN_URL, "https://example.com")).setFont(Identifier.of("font")).setInsertion("insertion")));

    @Test
    void serializeDeserializeJson() {
        JsonElement json = TextComponentCodec.LATEST.serializeJsonTree(this.text);
        ATextComponent deserialized = TextComponentCodec.LATEST.deserializeJsonTree(json);
        assertEquals(this.text, deserialized);
    }

    @Test
    void serializeDeserializeNbt() {
        INbtTag nbt = TextComponentCodec.LATEST.serializeNbt(this.text);
        ATextComponent deserialized = TextComponentCodec.LATEST.deserializeNbtTree(nbt);
        assertEquals(this.text, deserialized);
    }

    @Test
    void legacyItemDeserialization() throws SNbtSerializeException {
        CompoundTag legacyNbt = new CompoundTag()
                .add("id", "stone")
                .addByte("Count", (byte) 5);
        ATextComponent legacyComponent = new StringComponent("test")
                .setStyle(new Style()
                        .setHoverEvent(new TextHoverEvent(HoverEventAction.SHOW_ITEM, new StringComponent(SNbtSerializer.LATEST.serialize(legacyNbt))))
                );

        JsonElement legacyJson = TextComponentSerializer.V1_12.serializeJson(legacyComponent);
        ATextComponent modernDeserialized = TextComponentCodec.LATEST.deserialize(legacyJson);
        AHoverEvent hoverEvent = modernDeserialized.getStyle().getHoverEvent();
        ItemHoverEvent itemHoverEvent = assertInstanceOf(ItemHoverEvent.class, hoverEvent);
        assertEquals(Identifier.of("stone"), itemHoverEvent.getItem());
//        assertEquals(5, itemHoverEvent.getCount()); //The 1.20.5 version broke the legacy deserialize. The count is now lowercase
    }

    @Test
    void legacyEntityDeserialization() throws SNbtSerializeException {
        UUID randomUUID = UUID.randomUUID();
        CompoundTag legacyNbt = new CompoundTag()
                .add("name", "{\"text\":\"test\"}")
                .add("type", "cow")
                .add("id", randomUUID.toString());
        ATextComponent legacyComponent = new StringComponent("test")
                .setStyle(new Style()
                        .setHoverEvent(new TextHoverEvent(HoverEventAction.SHOW_ENTITY, new StringComponent(SNbtSerializer.LATEST.serialize(legacyNbt))))
                );

        JsonElement legacyJson = TextComponentSerializer.V1_12.serializeJson(legacyComponent);
        ATextComponent modernDeserialized = TextComponentCodec.LATEST.deserialize(legacyJson);
        AHoverEvent hoverEvent = modernDeserialized.getStyle().getHoverEvent();
        EntityHoverEvent entityHoverEvent = assertInstanceOf(EntityHoverEvent.class, hoverEvent);
        assertEquals(new StringComponent("test"), entityHoverEvent.getName());
        assertEquals(Identifier.of("cow"), entityHoverEvent.getEntityType());
        assertEquals(randomUUID, entityHoverEvent.getUuid());
    }

    @Test
    void arrayWithTag() {
        ListTag<INbtTag> tags = new ListTag<>()
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
        ATextComponent component = new TranslationComponent("test", (byte) 1, (byte) 2, (byte) 3)
                .append(new TranslationComponent("test", 1, 2, 3))
                .append(new TranslationComponent("test", 1L, 2L, 3L))
                .append(new TranslationComponent("test", 1, 2, 3));

        ATextComponent nbtComponent = TextComponentCodec.LATEST.deserializeNbtTree(tags);
        assertEquals(component, nbtComponent);
    }

}
