package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.core.utils.ToString;

import java.util.Objects;

public class EntityNbtSource implements NbtDataSource {

    private String selector;

    public EntityNbtSource(final String selector) {
        this.selector = selector;
    }

    /**
     * @return The selector of this component
     */
    public String getSelector() {
        return this.selector;
    }

    /**
     * Set the selector of this component.
     *
     * @param selector The selector
     * @return This component
     */
    public EntityNbtSource setSelector(final String selector) {
        this.selector = selector;
        return this;
    }

    @Override
    public EntityNbtSource copy() {
        return new EntityNbtSource(this.selector);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EntityNbtSource that = (EntityNbtSource) o;
        return Objects.equals(this.selector, that.selector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.selector);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("selector", this.selector)
                .toString();
    }

}
