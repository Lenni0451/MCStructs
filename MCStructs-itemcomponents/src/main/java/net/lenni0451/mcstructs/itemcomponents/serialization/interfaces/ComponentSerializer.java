package net.lenni0451.mcstructs.itemcomponents.serialization.interfaces;

import net.lenni0451.mcstructs.converter.DataConverter;

@FunctionalInterface
public interface ComponentSerializer<I> {

    <T> T serialize(final DataConverter<T> converter, final I component);

}
