package net.lenni0451.mcstructs.itemcomponents;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.core.utils.ToString;

import javax.annotation.Nullable;
import java.util.*;

/**
 * A map containing item components and their values.<br>
 * A map is linked to its registry and can only contain components from this registry.
 */
public class ItemComponentMap {

    private final ItemComponentRegistry registry;
    private final Map<ItemComponent<?>, Object> components = new HashMap<>();
    private final List<ItemComponent<?>> markedForRemoval = new ArrayList<>();

    public ItemComponentMap(final ItemComponentRegistry registry) {
        this.registry = registry;
    }

    /**
     * @return The registry of this map
     */
    public ItemComponentRegistry getRegistry() {
        return this.registry;
    }

    /**
     * @return The components with values in this map
     */
    public Map<ItemComponent<?>, ?> getValues() {
        return Collections.unmodifiableMap(this.components);
    }

    /**
     * @return The amount of components with values in this map
     */
    public int getValuesSize() {
        return this.components.size();
    }

    /**
     * @return The components marked for removal in this map
     */
    public List<ItemComponent<?>> getMarkedForRemoval() {
        return Collections.unmodifiableList(this.markedForRemoval);
    }

    /**
     * @return The amount of components marked for removal in this map
     */
    public int getMarkedForRemovalSize() {
        return this.markedForRemoval.size();
    }

    /**
     * @return The total amount of components in this map
     */
    public int size() {
        return this.components.size() + this.markedForRemoval.size();
    }

    /**
     * @return If this map is empty
     */
    public boolean isEmpty() {
        return this.components.isEmpty() && this.markedForRemoval.isEmpty();
    }

    /**
     * Check if a component is present in this map.
     *
     * @param component The component to check
     * @return If the component is present
     */
    public boolean contains(final ItemComponent<?> component) {
        return this.components.containsKey(component) || this.markedForRemoval.contains(component);
    }

    /**
     * Set a component with a value in this map.
     *
     * @param component The component to set
     * @param value     The value of the component
     * @param <T>       The type of the component
     * @return This map
     */
    public <T> ItemComponentMap set(final ItemComponent<T> component, final T value) {
        this.components.put(component, value);
        this.markedForRemoval.remove(component);
        return this;
    }

    /**
     * Get the value of a component from this map.
     *
     * @param component The component to get
     * @param <T>       The type of the component
     * @return The value of the component or null if the component is not present
     */
    @Nullable
    public <T> T get(final ItemComponent<T> component) {
        return (T) this.components.get(component);
    }

    /**
     * Remove a component from this map.
     *
     * @param component The component to remove
     * @return This map
     */
    public ItemComponentMap remove(final ItemComponent<?> component) {
        this.components.remove(component);
        this.markedForRemoval.remove(component);
        return this;
    }

    /**
     * Mark a component for removal from this map.<br>
     * This is used for removing default components from items.
     *
     * @param component The component to mark for removal
     * @return This map
     */
    public ItemComponentMap markForRemoval(final ItemComponent<?> component) {
        this.components.remove(component);
        this.markedForRemoval.add(component);
        return this;
    }

    /**
     * Check if a component is marked for removal.
     *
     * @param component The component to check
     * @return If the component is marked for removal
     */
    public boolean isMarkedForRemoval(final ItemComponent<?> component) {
        return this.markedForRemoval.contains(component);
    }

    /**
     * Serialize this map to a data object.
     *
     * @param converter The data converter
     * @param <T>       The type of the data object
     * @return The serialized data object
     */
    public <T> T to(final DataConverter<T> converter) {
        return this.registry.mapTo(converter, this);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("registry", this.registry)
                .add("components", this.components, map -> !map.isEmpty())
                .add("markedForRemoval", this.markedForRemoval, list -> !list.isEmpty())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemComponentMap that = (ItemComponentMap) o;
        return Objects.equals(this.registry, that.registry) && Objects.equals(this.components, that.components) && Objects.equals(this.markedForRemoval, that.markedForRemoval);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.registry, this.components, this.markedForRemoval);
    }

}
