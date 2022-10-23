package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.text.ATextComponent;

public class SelectorComponent extends ATextComponent {

    private final String selector;

    public SelectorComponent(final String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return this.selector;
    }

    @Override
    public ATextComponent copy() {
        return new SelectorComponent(this.selector);
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
