package net.lenni0451.mcstructs.text.serializer.v1_12;

import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.serializer.legacy.HoverEventSerializer;
import net.lenni0451.mcstructs.text.serializer.legacy.SerializerMap;
import net.lenni0451.mcstructs.text.serializer.v1_9.StyleDeserializer_v1_9;

public class StyleDeserializer_v1_12 extends StyleDeserializer_v1_9 {

    public StyleDeserializer_v1_12(final SNbt<?> sNbt) {
        super(sNbt);
    }

    @Override
    protected SerializerMap<HoverEvent, HoverEventAction, TextComponent> createHoverEventSerializer(SerializerMap.Builder<HoverEvent, HoverEventAction, TextComponent> builder) {
        return builder
                .add(HoverEventSerializer.TEXT)
                .add(HoverEventSerializer.LEGACY_STRING_ITEM)
                .add(HoverEventSerializer.LEGACY_ENTITY)
                .finalize(
                        HoverEvent::getAction,
                        HoverEventSerializer.LEGACY_FALLBACK_SERIALIZER,
                        HoverEventSerializer.LEGACY_FALLBACK_DESERIALIZER
                );
    }

}
