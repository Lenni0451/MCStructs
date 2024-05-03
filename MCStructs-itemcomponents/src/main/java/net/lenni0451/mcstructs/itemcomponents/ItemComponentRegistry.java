package net.lenni0451.mcstructs.itemcomponents;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.ItemComponents_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.serialization.interfaces.ComponentDeserializer;
import net.lenni0451.mcstructs.itemcomponents.serialization.interfaces.ComponentSerializer;
import net.lenni0451.mcstructs.itemcomponents.serialization.interfaces.MergedComponentSerializer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class ItemComponentRegistry {

    /**
     * Item component registry for 1.20.5.
     */
    public static final ItemComponents_v1_20_5 V1_20_5 = new ItemComponents_v1_20_5();
    /**
     * The latest item component registry.
     */
    public static final ItemComponentRegistry LATEST = V1_20_5;


    private final List<ItemComponent<?>> components;
    protected final RegistryVerifier registryVerifier;

    public ItemComponentRegistry() {
        this.components = new ArrayList<>();
        this.registryVerifier = new RegistryVerifier();
    }

    protected ItemComponentRegistry(final RegistryVerifier registryVerifier) {
        this.components = new ArrayList<>();
        this.registryVerifier = registryVerifier;
    }

    @Nullable
    public <T> ItemComponent<T> getComponent(final Identifier name) {
        for (ItemComponent<?> component : this.components) {
            if (component.getName().equals(name)) return (ItemComponent<T>) component;
        }
        return null;
    }

    public RegistryVerifier getRegistryVerifier() {
        return this.registryVerifier;
    }

    public abstract <D> D mapTo(final DataConverter<D> converter, final ItemComponentMap map);

    public abstract <D> ItemComponentMap mapFrom(final DataConverter<D> converter, final D data);


    protected <T> ItemComponent<T> copy(final String name, final ItemComponent<T> component) {
        return this.register(name, component.serializer, component.deserializer, component.verifier);
    }

    protected <T> ItemComponent<T> registerNonSerializable(final String name) {
        Supplier<UnsupportedOperationException> exceptionSupplier = () -> new UnsupportedOperationException("This component is not serializable");
        return this.register(name, new ComponentSerializer<T>() {
            @Override
            public <D> D serialize(DataConverter<D> converter, T component) {
                throw exceptionSupplier.get();
            }
        }, new ComponentDeserializer<T>() {
            @Override
            public <D> T deserialize(DataConverter<D> converter, D data) {
                throw exceptionSupplier.get();
            }
        });
    }

    protected <T> ItemComponent<T> register(final String name, final MergedComponentSerializer<T> serializer) {
        return this.register(name, serializer, serializer);
    }

    protected <T> ItemComponent<T> register(final String name, final MergedComponentSerializer<T> serializer, final Consumer<T> verifier) {
        return this.register(name, serializer, serializer, verifier);
    }

    protected <T> ItemComponent<T> register(final String name, final ComponentSerializer<T> serializer, final ComponentDeserializer<T> deserializer) {
        return this.register(name, serializer, deserializer, null);
    }

    protected <T> ItemComponent<T> register(final String name, final ComponentSerializer<T> serializer, final ComponentDeserializer<T> deserializer, final Consumer<T> verifier) {
        ItemComponent<T> itemComponent = new ItemComponent<>(name, serializer, deserializer, verifier);
        this.components.add(itemComponent);
        return itemComponent;
    }

}
