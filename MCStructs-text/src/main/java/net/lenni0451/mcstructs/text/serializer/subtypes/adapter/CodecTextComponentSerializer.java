package net.lenni0451.mcstructs.text.serializer.subtypes.adapter;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.subtypes.IStyleSerializer;
import net.lenni0451.mcstructs.text.serializer.subtypes.ITextComponentSerializer;

public class CodecTextComponentSerializer<T> implements ITextComponentSerializer<T> {

    private final Codec<TextComponent> codec;
    private final DataConverter<T> dataConverter;
    private final IStyleSerializer<T> styleSerializer;

    public CodecTextComponentSerializer(final Codec<TextComponent> codec, final DataConverter<T> dataConverter, final Codec<Style> styleCodec) {
        this.codec = codec;
        this.dataConverter = dataConverter;
        this.styleSerializer = new CodecStyleSerializer<>(styleCodec, dataConverter);
    }

    @Override
    public IStyleSerializer<T> getStyleSerializer() {
        return this.styleSerializer;
    }

    @Override
    public T serialize(TextComponent object) {
        return this.codec.serialize(this.dataConverter, object).getOrThrow();
    }

    @Override
    public TextComponent deserialize(T object) {
        return this.codec.deserialize(this.dataConverter, object).getOrThrow();
    }

}
