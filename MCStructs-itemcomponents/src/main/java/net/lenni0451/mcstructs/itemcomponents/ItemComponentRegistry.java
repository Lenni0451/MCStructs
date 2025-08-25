package net.lenni0451.mcstructs.itemcomponents;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.DataDeserializer;
import net.lenni0451.mcstructs.converter.codec.DataSerializer;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.Registries;
import net.lenni0451.mcstructs.itemcomponents.impl.Verifiers;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.ItemComponents_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.ItemComponents_v1_21;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.ItemComponents_v1_21_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.ItemComponents_v1_21_4;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.ItemComponents_v1_21_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_6.ItemComponents_v1_21_6;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9.ItemComponents_v1_21_9;

import javax.annotation.Nullable;

/**
 * The registry containing all item components.<br>
 * Components are registered internally and can't be registered by the user.<br>
 * By default, the registries and verifiers are noop and accept everything.
 * You need to create a new instance of the required registry and pass implementations of {@link Registries} and {@link Verifiers} to it.
 */
public abstract class ItemComponentRegistry {

    /**
     * Item component registry for 1.20.5.<br>
     * The registries and verifiers are noop and accept everything.<br>
     * It is recommended to create a new instance with your own registries and verifiers.
     */
    public static final ItemComponents_v1_20_5 V1_20_5 = new ItemComponents_v1_20_5();
    /**
     * Item component registry for 1.21.<br>
     * The registries and verifiers are noop and accept everything.<br>
     * It is recommended to create a new instance with your own registries and verifiers.
     */
    public static final ItemComponents_v1_21 V1_21 = new ItemComponents_v1_21();
    /**
     * Item component registry for 1.21.2.<br>
     * The registries and verifiers are noop and accept everything.<br>
     * It is recommended to create a new instance with your own registries and verifiers.
     */
    public static final ItemComponents_v1_21_2 V1_21_2 = new ItemComponents_v1_21_2();
    /**
     * Item component registry for 1.21.4.<br>
     * The registries and verifiers are noop and accept everything.<br>
     * It is recommended to create a new instance with your own registries and verifiers.
     */
    public static final ItemComponents_v1_21_4 V1_21_4 = new ItemComponents_v1_21_4();
    /**
     * Item component registry for 1.21.5.<br>
     * The registries and verifiers are noop and accept everything.<br>
     * It is recommended to create a new instance with your own registries and verifiers.
     */
    public static final ItemComponents_v1_21_5 V1_21_5 = new ItemComponents_v1_21_5();
    /**
     * Item component registry for 1.21.6.<br>
     * The registries and verifiers are noop and accept everything.<br>
     * It is recommended to create a new instance with your own registries and verifiers.
     */
    public static final ItemComponents_v1_21_6 V1_21_6 = new ItemComponents_v1_21_6();
    /**
     * Item component registry for 1.21.9.<br>
     * The registries and verifiers are noop and accept everything.<br>
     * It is recommended to create a new instance with your own registries and verifiers.
     */
    public static final ItemComponents_v1_21_9 V1_21_9 = new ItemComponents_v1_21_9();
    /**
     * The latest item component registry.
     */
    public static final ItemComponentRegistry LATEST = V1_21_9;


    private final ItemComponentList components;
    protected final Registries registries;
    protected final Verifiers verifiers;
    private final Codec<ItemComponent<?>> componentCodec = Codec.STRING_IDENTIFIER.flatMap(itemComponent -> Result.success(itemComponent.getName()), identifier -> {
        ItemComponent<?> component = ItemComponentRegistry.this.getComponent(identifier);
        if (component == null) return Result.error("Unknown item component: " + identifier);
        return Result.success(component);
    });
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
        this.components = new ItemComponentList();
        this.registries = new Registries();
        this.verifiers = new Verifiers() {
        };
    }

    public ItemComponentRegistry(final Registries registries, final Verifiers verifiers) {
        this.components = new ItemComponentList();
        this.registries = registries;
        this.verifiers = verifiers;
    }

    /**
     * @return The list of all registered components
     */
    public ItemComponentList getComponentList() {
        return this.components;
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
        return this.components.<T>getByName(name).orElse(null);
    }

    /**
     * @return The registries
     */
    public Registries getRegistries() {
        return this.registries;
    }

    /**
     * @return The verifiers
     */
    public Verifiers getVerifiers() {
        return this.verifiers;
    }

    /**
     * @return The codec for item components
     */
    public Codec<ItemComponent<?>> getComponentCodec() {
        return this.componentCodec;
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
        this.unregister(name);
        ItemComponent<T> itemComponent = new ItemComponent<>(name, codec);
        this.components.register(itemComponent);
        return itemComponent;
    }

    protected void unregister(final String name) {
        this.components.getByName(name).ifPresent(this.components::unregister);
    }

    /**
     * Sort the components by their names.<br>
     * The array should contain all names of the components in the version specific order.
     *
     * @param sortedNames The sorted names
     */
    protected void sort(final String... sortedNames) {
        this.components.sort(sortedNames);
    }

}
