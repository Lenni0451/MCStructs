package net.lenni0451.mcstructs.all.text;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.TranslationComponent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

/**
 * Add a show item hover event to a text component.
 */
public class TextHoverEvent {

    public static void main(String[] args) {
        TextComponent text = new TranslationComponent("narrator.controls.reset", "123");
        text.getStyle().setHoverEvent(new ItemHoverEvent(Identifier.of("stone"), 1, new CompoundTag()));
        System.out.println(TextComponentSerializer.V1_18.serialize(text));
    }

}
