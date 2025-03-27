package net.lenni0451.mcstructs.text.serializer.legacy;

import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;

public abstract class EventSerializersBase {

    protected final SerializerMap<ClickEvent, ClickEventAction, String> clickEventSerializer;
    protected final SerializerMap<HoverEvent, HoverEventAction, TextComponent> hoverEventSerializer;

    public EventSerializersBase(final SNbt<?> sNbt) {
        this.clickEventSerializer = this.createClickEventSerializer(SerializerMap.create(sNbt));
        this.hoverEventSerializer = this.createHoverEventSerializer(SerializerMap.create(sNbt));
    }

    protected abstract SerializerMap<ClickEvent, ClickEventAction, String> createClickEventSerializer(final SerializerMap.Builder<ClickEvent, ClickEventAction, String> builder);

    protected abstract SerializerMap<HoverEvent, HoverEventAction, TextComponent> createHoverEventSerializer(final SerializerMap.Builder<HoverEvent, HoverEventAction, TextComponent> builder);

}
