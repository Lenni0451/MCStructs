package net.lenni0451.mcstructs.itemcomponents;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.Result;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.DataDeserializer;
import net.lenni0451.mcstructs.converter.codec.DataSerializer;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.ItemComponents_v1_20_5;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    private final Codec<ItemComponentMap> mapCodec = Codec.ofThrowing(new DataSerializer<ItemComponentMap>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, ItemComponentMap element) {
            return Result.success(ItemComponentRegistry.this.mapTo(converter, element));
        }
    }, new DataDeserializer<ItemComponentMap>() {
        @Override
        public <S> Result<ItemComponentMap> deserialize(DataConverter<S> converter, S data) {
            return Result.success(ItemComponentRegistry.this.mapFrom(converter, data));
        }
    });

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

    public Codec<ItemComponentMap> getMapCodec() {
        return this.mapCodec;
    }

    public abstract <D> D mapTo(final DataConverter<D> converter, final ItemComponentMap map);

    public abstract <D> ItemComponentMap mapFrom(final DataConverter<D> converter, final D data);


    protected <T> ItemComponent<T> copy(final String name, final ItemComponent<T> component) {
        return this.register(name, component.codec, component.verifier);
    }

    protected <T> ItemComponent<T> registerNonSerializable(final String name) {
        String message = "The component " + name + " is not serializable!";
        return this.register(name, new Codec<T>() {
            @Override
            public <S> Result<T> deserialize(DataConverter<S> converter, S data) {
                return Result.error(message);
            }

            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, T element) {
                return Result.error(message);
            }
        });
    }

    protected <T> ItemComponent<T> register(final String name, final Codec<T> codec) {
        return this.register(name, codec, null);
    }

    protected <T> ItemComponent<T> register(final String name, final Codec<T> codec, final Consumer<T> verifier) {
        return new ItemComponent<>(name, codec, verifier);
    }

}
