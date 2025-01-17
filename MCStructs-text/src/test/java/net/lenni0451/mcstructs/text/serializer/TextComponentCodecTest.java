package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.JsonElement;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.ListTag;
import net.lenni0451.mcstructs.snbt.SNbt;
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
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TextComponentCodecTest {

    private static final TextComponentCodec[] codecs = new TextComponentCodec[]{
            TextComponentCodec.V1_20_3,
            TextComponentCodec.V1_20_5,
//            TextComponentCodec.V1_21_2, //TODO: Add back when codec
//            TextComponentCodec.V1_21_4,
            TextComponentCodec.V1_21_5,
            TextComponentCodec.LATEST
    };
    private static final TextComponentCodec[] legacyCodecs = new TextComponentCodec[]{
            TextComponentCodec.V1_20_3,
            TextComponentCodec.V1_20_5,
            TextComponentCodec.V1_21_2,
            TextComponentCodec.V1_21_4,
    };
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
            .append(new StringComponent("style").setStyle(new Style().setFormatting(TextFormatting.ALL.values().toArray(new TextFormatting[0])).setClickEvent(ClickEvent.openUrl(URI.create("https://example.com"))).setFont(Identifier.of("font")).setInsertion("insertion")));

    @ParameterizedTest
    @FieldSource("codecs")
    void serializeDeserializeJson(final TextComponentCodec codec) {
        JsonElement json = codec.serializeJsonTree(this.text);
        TextComponent deserialized = codec.deserializeJsonTree(json);
        assertEquals(this.text, deserialized);
    }

    @ParameterizedTest
    @FieldSource("codecs")
    void serializeDeserializeNbt(final TextComponentCodec codec) {
        NbtTag nbt = codec.serializeNbt(this.text);
        TextComponent deserialized = codec.deserializeNbtTree(nbt);
        assertEquals(this.text, deserialized);
    }

    @ParameterizedTest
    @FieldSource("legacyCodecs")
    void legacyItemDeserialization(final TextComponentCodec codec) throws SNbtSerializeException {
        CompoundTag legacyNbt = new CompoundTag()
                .add("id", "stone")
                .addByte("Count", (byte) 5);
        TextComponent legacyComponent = new StringComponent("test")
                .setStyle(new Style()
                        .setHoverEvent(new ItemHoverEvent(SNbt.LATEST.serialize(legacyNbt)))
                );

        JsonElement legacyJson = TextComponentSerializer.V1_12.serializeJson(legacyComponent);
        TextComponent modernDeserialized = codec.deserialize(legacyJson);
        HoverEvent hoverEvent = modernDeserialized.getStyle().getHoverEvent();
        ItemHoverEvent itemHoverEvent = assertInstanceOf(ItemHoverEvent.class, hoverEvent);
        assertEquals(Identifier.of("stone"), itemHoverEvent.asModern().getId());
//        assertEquals(5, itemHoverEvent.getCount()); //The 1.20.5 version broke the legacy deserialize. The count is now lowercase
    }

    @ParameterizedTest
    @FieldSource("legacyCodecs")
    void legacyEntityDeserialization(final TextComponentCodec codec) throws SNbtSerializeException {
        UUID randomUUID = UUID.randomUUID();
        CompoundTag legacyNbt = new CompoundTag()
                .add("name", "{\"text\":\"test\"}")
                .add("type", "cow")
                .add("id", randomUUID.toString());
        TextComponent legacyComponent = new StringComponent("test")
                .setStyle(new Style()
                        .setHoverEvent(new EntityHoverEvent(new StringComponent(SNbt.LATEST.serialize(legacyNbt))))
                );

        JsonElement legacyJson = TextComponentSerializer.V1_12.serializeJson(legacyComponent);
        TextComponent modernDeserialized = codec.deserialize(legacyJson);
        HoverEvent hoverEvent = modernDeserialized.getStyle().getHoverEvent();
        EntityHoverEvent entityHoverEvent = assertInstanceOf(EntityHoverEvent.class, hoverEvent);
        assertEquals(new StringComponent("test"), entityHoverEvent.asModern().getName());
        assertEquals(Identifier.of("cow"), entityHoverEvent.asModern().getType());
        assertEquals(randomUUID, entityHoverEvent.asModern().getUuid());
    }

    @ParameterizedTest
    @FieldSource("codecs")
    void arrayWithTag(final TextComponentCodec codec) {
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

        TextComponent nbtComponent = codec.deserializeNbtTree(tags);
        assertEquals(component, nbtComponent);
    }

}
