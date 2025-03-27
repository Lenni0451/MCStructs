package net.lenni0451.mcstructs.text.components.nbt;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;

@EqualsAndHashCode
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
    public String toString() {
        return ToString.of(this)
                .add("selector", this.selector)
                .toString();
    }

}
