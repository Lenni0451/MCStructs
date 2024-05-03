package net.lenni0451.mcstructs.itemcomponents;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.serialization.interfaces.ComponentDeserializer;
import net.lenni0451.mcstructs.itemcomponents.serialization.interfaces.ComponentSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ItemComponent<T> {

    private final Identifier name;
    final ComponentSerializer<T> serializer;
    final ComponentDeserializer<T> deserializer;
    @Nullable
    final Consumer<T> verifier;

    public ItemComponent(final String name, final ComponentSerializer<T> serializer, final ComponentDeserializer<T> deserializer, @Nullable final Consumer<T> verifier) {
        this.name = Identifier.of(name);
        this.serializer = serializer;
        this.deserializer = deserializer;
        this.verifier = verifier;
    }

    public Identifier getName() {
        return this.name;
    }

    public <D> D serialize(final DataConverter<D> converter, final T value) {
        return this.serializer.serialize(converter, value);
    }

    public <D> T deserialize(final DataConverter<D> converter, final D data) {
        try {
            return this.deserializer.deserialize(converter, data);
        } catch (Throwable t) {
            throw new IllegalStateException("Failed to deserialize component " + this.name, t);
        }
    }

}
