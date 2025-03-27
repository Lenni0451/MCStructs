package net.lenni0451.mcstructs.text.serializer.v1_9;

import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.serializer.legacy.ClickEventSerializer;
import net.lenni0451.mcstructs.text.serializer.legacy.HoverEventSerializer;
import net.lenni0451.mcstructs.text.serializer.legacy.SerializerMap;
import net.lenni0451.mcstructs.text.serializer.v1_8.StyleDeserializer_v1_8;

public class StyleDeserializer_v1_9 extends StyleDeserializer_v1_8 {

    public StyleDeserializer_v1_9(final SNbt<?> sNbt) {
        super(sNbt);
    }

    @Override
    protected SerializerMap<ClickEvent, ClickEventAction, String> createClickEventSerializer(SerializerMap.Builder<ClickEvent, ClickEventAction, String> builder) {
        return builder
                .add(ClickEventSerializer.OPEN_URL)
                .add(ClickEventSerializer.OPEN_FILE)
                .add(ClickEventSerializer.RUN_COMMAND)
                .add(ClickEventSerializer.SUGGEST_COMMAND)
                .add(ClickEventSerializer.CHANGE_PAGE)
                .finalize(ClickEvent::getAction);
    }

    @Override
    protected SerializerMap<HoverEvent, HoverEventAction, TextComponent> createHoverEventSerializer(SerializerMap.Builder<HoverEvent, HoverEventAction, TextComponent> builder) {
        return builder
                .add(HoverEventSerializer.TEXT)
                .add(HoverEventSerializer.ACHIEVEMENT)
                .add(HoverEventSerializer.LEGACY_ITEM)
                .add(HoverEventSerializer.LEGACY_ENTITY)
                .finalize(HoverEvent::getAction);
    }

}
