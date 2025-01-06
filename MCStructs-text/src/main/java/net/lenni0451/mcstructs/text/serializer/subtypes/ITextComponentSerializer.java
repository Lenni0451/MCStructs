package net.lenni0451.mcstructs.text.serializer.subtypes;

import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;

public interface ITextComponentSerializer<T> extends ITypedSerializer<T, TextComponent> {

    IStyleSerializer<T> getStyleSerializer();

}
