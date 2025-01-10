package net.lenni0451.mcstructs.text.serializer.subtypes.adapter;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.serializer.subtypes.IStyleSerializer;

public class CodecStyleSerializer<T> implements IStyleSerializer<T> {

    private final Codec<Style> codec;
    private final DataConverter<T> dataConverter;

    public CodecStyleSerializer(final Codec<Style> codec, final DataConverter<T> dataConverter) {
        this.codec = codec;
        this.dataConverter = dataConverter;
    }

    @Override
    public T serialize(Style object) {
        return this.codec.serialize(this.dataConverter, object).getOrThrow();
    }

    @Override
    public Style deserialize(T object) {
        return this.codec.deserialize(this.dataConverter, object).getOrThrow();
    }

}
