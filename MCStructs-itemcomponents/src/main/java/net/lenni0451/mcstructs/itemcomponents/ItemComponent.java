package net.lenni0451.mcstructs.itemcomponents;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.core.Identifier;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ItemComponent<T> {

    private final Identifier name;
    final Codec<T> codec;
    @Nullable
    final Consumer<T> verifier;

    public ItemComponent(final String name, final Codec<T> codec, @Nullable final Consumer<T> verifier) {
        this.name = Identifier.of(name);
        this.codec = codec;
        this.verifier = verifier;
    }

    public Identifier getName() {
        return this.name;
    }

    public Codec<T> getCodec() {
        return this.codec;
    }

    public <D> D serialize(final DataConverter<D> converter, final T value) {
        return this.codec.serialize(converter, value).getOrThrow(cause -> new IllegalStateException("Failed to serialize component " + this.name.get(), cause));
    }

    public <D> T deserialize(final DataConverter<D> converter, final D data) {
        return this.codec.deserialize(converter, data).getOrThrow(cause -> new IllegalStateException("Failed to deserialize component " + this.name.get(), cause));
    }

}
