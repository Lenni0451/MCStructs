package net.lenni0451.mcstructs.itemcomponents;

import com.google.gson.JsonElement;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.ItemComponents_v1_20_5;
import net.lenni0451.mcstructs.nbt.INbtTag;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ItemComponentRegistry {

    /**
     * Item component registry for 1.20.5.
     */
    public static final ItemComponentRegistry V1_20_5 = new ItemComponents_v1_20_5();
    /**
     * The latest item component registry.
     */
    public static final ItemComponentRegistry LATEST = V1_20_5;


    private final List<ItemComponent<?>> components;

    public ItemComponentRegistry() {
        this.components = new ArrayList<>();
    }

    @Nullable
    public <T> ItemComponent<T> getComponent(final Identifier name) {
        for (ItemComponent<?> component : this.components) {
            if (component.getName().equals(name)) return (ItemComponent<T>) component;
        }
        return null;
    }

    public abstract JsonElement mapToJson(final ItemComponentMap map);

    public abstract ItemComponentMap mapFromJson(final JsonElement element);

    public abstract INbtTag mapToNbt(final ItemComponentMap map);

    public abstract ItemComponentMap mapFromNbt(final INbtTag tag);

    protected <T> ItemComponent<T> copy(final String name, final ItemComponent<T> component) {
        return this.register(name, component.toJson, component.fromJson, component.toNbt, component.fromNbt, component.verifier);
    }

    protected <T> ItemComponent<T> registerNonSerializable(final String name) {
        Supplier<UnsupportedOperationException> exceptionSupplier = () -> new UnsupportedOperationException("This component is not serializable");
        return this.register(name, t -> {
            throw exceptionSupplier.get();
        }, element -> {
            throw exceptionSupplier.get();
        }, t -> {
            throw exceptionSupplier.get();
        }, tag -> {
            throw exceptionSupplier.get();
        });
    }

    protected <T> ItemComponent<T> register(final String name, final Function<T, JsonElement> toJson, final Function<JsonElement, T> fromJson, final Function<T, INbtTag> toNbt, final Function<INbtTag, T> fromNbt) {
        return this.register(name, toJson, fromJson, toNbt, fromNbt, null);
    }

    protected <T> ItemComponent<T> register(final String name, final Function<T, JsonElement> toJson, final Function<JsonElement, T> fromJson, final Function<T, INbtTag> toNbt, final Function<INbtTag, T> fromNbt, final Consumer<T> verifier) {
        ItemComponent<T> itemComponent = new ItemComponent<>(name, toJson, fromJson, toNbt, fromNbt, verifier);
        this.components.add(itemComponent);
        return itemComponent;
    }

}
