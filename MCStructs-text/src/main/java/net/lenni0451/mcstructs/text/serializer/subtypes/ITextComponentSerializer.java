package net.lenni0451.mcstructs.text.serializer.subtypes;

import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;

public interface ITextComponentSerializer<T> extends ITypedSerializer<T, ATextComponent> {

    IStyleSerializer<T> getStyleSerializer();

}
