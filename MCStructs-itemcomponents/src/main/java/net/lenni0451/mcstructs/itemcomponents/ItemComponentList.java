package net.lenni0451.mcstructs.itemcomponents;

import net.lenni0451.mcstructs.core.Identifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemComponentList implements Iterable<ItemComponent<?>> {

    private final List<ItemComponent<?>> components;

    public ItemComponentList() {
        this.components = new ArrayList<>();
    }

    /**
     * @return The amount of registered components
     */
    public int size() {
        return this.components.size();
    }

    /**
     * Get a component by its name.<br>
     * The name is prepended with the namespace if required.
     *
     * @param name The name of the component
     * @param <T>  The type of the component
     * @return The component if found
     */
    public <T> Optional<ItemComponent<T>> getByName(final String name) {
        return this.getByName(Identifier.of(name));
    }

    /**
     * Get a component by its name.
     *
     * @param identifier The name of the component
     * @param <T>        The type of the component
     * @return The component if found
     */
    public <T> Optional<ItemComponent<T>> getByName(final Identifier identifier) {
        for (ItemComponent<?> component : this.components) {
            if (component.getName().equals(identifier)) return Optional.of((ItemComponent<T>) component);
        }
        return Optional.empty();
    }

    /**
     * Get a component by its id.
     *
     * @param id  The id of the component
     * @param <T> The type of the component
     * @return The component if found
     */
    public <T> Optional<ItemComponent<T>> getById(final int id) {
        if (id < 0 || id >= this.components.size()) return Optional.empty();
        return Optional.of((ItemComponent<T>) this.components.get(id));
    }

    /**
     * Get the id of a component.
     *
     * @param component The component to get the id from
     * @return The id of the component or -1 if not found
     */
    public int getId(final ItemComponent<?> component) {
        return this.components.indexOf(component);
    }

    void register(final ItemComponent<?> component) {
        this.components.add(component);
    }

    void unregister(final ItemComponent<?> component) {
        this.components.remove(component);
    }

    void sort(final String[] names) {
        List<ItemComponent<?>> sorted = new ArrayList<>();
        List<ItemComponent<?>> missed = new ArrayList<>(this.components);
        NAMES:
        for (String name : names) {
            for (ItemComponent<?> component : missed) {
                if (component.getName().equals(Identifier.of(name))) {
                    sorted.add(component);
                    missed.remove(component);
                    continue NAMES;
                }
            }
            throw new IllegalArgumentException("Component '" + name + "' was not found in the registry or was already sorted");
        }
        if (!missed.isEmpty()) {
            throw new IllegalArgumentException("Missed components after sorting " + missed.stream().map(c -> c.getName().get()).collect(Collectors.joining("/")));
        }
        this.components.clear();
        this.components.addAll(sorted);
    }

    @Override
    public Iterator<ItemComponent<?>> iterator() {
        return new Iterator<ItemComponent<?>>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < ItemComponentList.this.components.size();
            }

            @Override
            public ItemComponent<?> next() {
                return ItemComponentList.this.components.get(this.index++);
            }
        };
    }

}
