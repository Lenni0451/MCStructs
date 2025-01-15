package net.lenni0451.mcstructs.text.serializer.v1_12;

import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.serializer.legacy.HoverEventSerializer;
import net.lenni0451.mcstructs.text.serializer.legacy.SerializerMap;
import net.lenni0451.mcstructs.text.serializer.v1_9.StyleSerializer_v1_9;

public class StyleSerializer_v1_12 extends StyleSerializer_v1_9 {

    public StyleSerializer_v1_12(final SNbt<?> sNbt) {
        super(sNbt);
    }

    @Override
    protected SerializerMap<HoverEvent, HoverEventAction, TextComponent> createHoverEventSerializer(SerializerMap.Builder<HoverEvent, HoverEventAction, TextComponent> builder) {
        return builder
                .add(HoverEventSerializer.TEXT)
                .add(HoverEventSerializer.LEGACY_STRING_ITEM)
                .add(HoverEventSerializer.LEGACY_RAW_ITEM)
                .add(HoverEventSerializer.LEGACY_ENTITY)
                .add(HoverEventSerializer.LEGACY_RAW_ENTITY)
                .finalize(HoverEvent::getAction);
    }

}
