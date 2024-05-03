package net.lenni0451.mcstructs.itemcomponents;

import net.lenni0451.mcstructs.converter.DataConverter;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ItemComponentMap {

    private final ItemComponentRegistry registry;
    private final Map<ItemComponent<?>, Optional<?>> components = new HashMap<>();

    public ItemComponentMap(final ItemComponentRegistry registry) {
        this.registry = registry;
    }

    public ItemComponentRegistry getRegistry() {
        return this.registry;
    }

    public Map<ItemComponent<?>, Optional<?>> getRaw() {
        return this.components;
    }

    public <T> ItemComponentMap set(final ItemComponent<T> component, final T value) {
        this.components.put(component, Optional.of(value));
        return this;
    }

    @Nullable
    public <T> T get(final ItemComponent<T> component) {
        return (T) this.components.getOrDefault(component, Optional.empty()).orElse(null);
    }

    public ItemComponentMap remove(final ItemComponent<?> component) {
        this.components.remove(component);
        return this;
    }

    public ItemComponentMap markForRemoval(final ItemComponent<?> component) {
        this.components.put(component, Optional.empty());
        return this;
    }

    public boolean isMarkedForRemoval(final ItemComponent<?> component) {
        Optional<?> optional = this.components.get(component);
        return optional != null && !optional.isPresent();
    }

    public boolean isEmpty() {
        return this.components.isEmpty();
    }

    public <T> T to(final DataConverter<T> converter) {
        return this.registry.mapTo(converter, this);
    }

}
