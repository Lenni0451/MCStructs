package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.text.ATextComponent;

public class SelectorComponent extends ATextComponent {

    private final String selector;
    private final ATextComponent separator;

    public SelectorComponent(final String selector, final ATextComponent separator) {
        this.selector = selector;
        this.separator = separator;
    }

    public String getSelector() {
        return this.selector;
    }

    public ATextComponent getSeparator() {
        return this.separator;
    }

    @Override
    public String asString() {
        return this.selector;
    }

    @Override
    public ATextComponent copy() {
        if (this.separator == null) return new SelectorComponent(this.selector, null);
        else return new SelectorComponent(this.selector, this.separator.copy());
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }

}
