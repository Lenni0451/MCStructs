package net.lenni0451.mcstructs.text.components;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
public class SelectorComponent extends TextComponent {

    private String selector;
    @Nullable
    private TextComponent separator;

    public SelectorComponent(final String selector) {
        this(selector, null);
    }

    public SelectorComponent(final String selector, @Nullable final TextComponent separator) {
        this.selector = selector;
        this.separator = separator;
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
    public SelectorComponent setSelector(final String selector) {
        this.selector = selector;
        return this;
    }

    /**
     * @return The separator of this component
     */
    @Nullable
    public TextComponent getSeparator() {
        return this.separator;
    }

    /**
     * Set the separator of this component.
     *
     * @param separator The separator
     * @return This component
     */
    public SelectorComponent setSeparator(@Nullable final TextComponent separator) {
        this.separator = separator;
        return this;
    }

    @Override
    public String asSingleString() {
        return this.selector;
    }

    @Override
    public TextComponent copy() {
        return this.copyMetaTo(this.shallowCopy());
    }

    @Override
    public TextComponent shallowCopy() {
        if (this.separator == null) return new SelectorComponent(this.selector, null).setStyle(this.getStyle().copy());
        else return new SelectorComponent(this.selector, this.separator.copy()).setStyle(this.getStyle().copy());
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty())
                .add("style", this.getStyle(), style -> !style.isEmpty())
                .add("selector", this.selector)
                .add("separator", this.separator, Objects::nonNull)
                .toString();
    }

}
