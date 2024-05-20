package net.lenni0451.mcstructs.itemcomponents;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.Result;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.DataDeserializer;
import net.lenni0451.mcstructs.converter.codec.DataSerializer;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.ItemComponents_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.ItemComponents_v1_21;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * The registry containing all item components.<br>
 * Components are registered internally and can't be registered by the user.
 */
public abstract class ItemComponentRegistry {

    /**
     * Item component registry for 1.20.5.
     */
    public static final ItemComponents_v1_20_5 V1_20_5 = new ItemComponents_v1_20_5();
    /**
     * Item component registry for 1.21.
     */
    public static final ItemComponents_v1_21 V1_21 = new ItemComponents_v1_21();
    /**
     * The latest item component registry.
     */
    public static final ItemComponentRegistry LATEST = V1_21;


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

    /**
     * Get a component by its name.
     *
     * @param name The name of the component
     * @param <T>  The type of the component
     * @return The component or null if not found
     */
    @Nullable
    public <T> ItemComponent<T> getComponent(final Identifier name) {
        for (ItemComponent<?> component : this.components) {
            if (component.getName().equals(name)) return (ItemComponent<T>) component;
        }
        return null;
    }

    /**
     * @return The verifier for registry entries
     */
    public RegistryVerifier getRegistryVerifier() {
        return this.registryVerifier;
    }

    /**
     * @return The codec for the item component map
     */
    public Codec<ItemComponentMap> getMapCodec() {
        return this.mapCodec;
    }

    /**
     * @return The default values for item components
     */
    public abstract ItemComponentMap getItemDefaults();

    /**
     * Serialize an item component map to a data object.
     *
     * @param converter The data converter
     * @param map       The item component map
     * @param <D>       The type of the data object
     * @return The serialized data object
     */
    public abstract <D> D mapTo(final DataConverter<D> converter, final ItemComponentMap map);

    /**
     * Deserialize an item component map from a data object.
     *
     * @param converter The data converter
     * @param data      The data object
     * @param <D>       The type of the data object
     * @return The deserialized item component map
     */
    public abstract <D> ItemComponentMap mapFrom(final DataConverter<D> converter, final D data);


    protected <T> ItemComponent<T> copy(final String name, final ItemComponent<T> component) {
        return this.register(name, component.codec);
    }

    protected <T> ItemComponent<T> registerNonSerializable(final String name) {
        return this.register(name, Codec.failing("The component " + name + " is not serializable!"));
    }

    protected <T> ItemComponent<T> register(final String name, final Codec<T> codec) {
        ItemComponent<T> itemComponent = new ItemComponent<>(name, codec);
        this.components.add(itemComponent);
        return itemComponent;
    }

    protected <T> ItemComponent<T> unregister(final String name) {
        ItemComponent<?> itemComponent = this.getComponent(Identifier.of(name));
        if (itemComponent != null) this.components.remove(itemComponent);
        return null;
    }

}
