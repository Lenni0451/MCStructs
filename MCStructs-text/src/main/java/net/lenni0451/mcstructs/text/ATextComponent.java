package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.core.ICopyable;
import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtComponent;
import net.lenni0451.mcstructs.text.utils.TextUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static net.lenni0451.mcstructs.core.TextFormatting.COLOR_CHAR;

/**
 * The base text component class.<br>
 * Implementations of this class are:<br>
 * - {@link StringComponent}<br>
 * - {@link TranslationComponent}<br>
 * - {@link ScoreComponent}<br>
 * - {@link SelectorComponent}<br>
 * - {@link KeybindComponent}<br>
 * - {@link BlockNbtComponent}<br>
 * - {@link EntityNbtComponent}<br>
 * - {@link StorageNbtComponent}
 */
public abstract class ATextComponent implements ICopyable<ATextComponent> {

    private final List<ATextComponent> siblings = new ArrayList<>();
    private Style style = new Style();

    /**
     * Append a string to this component.
     *
     * @param s The string to append
     * @return This component
     */
    public ATextComponent append(final String s) {
        this.append(new StringComponent(s));
        return this;
    }

    /**
     * Append a component to this component.
     *
     * @param component The component to append
     * @return This component
     */
    public ATextComponent append(final ATextComponent component) {
        this.siblings.add(component);
        return this;
    }

    /**
     * Append multiple components to this component.
     *
     * @param components The components to append
     * @return This component
     */
    public ATextComponent append(final ATextComponent... components) {
        this.siblings.addAll(Arrays.asList(components));
        return this;
    }

    /**
     * @return The siblings of this component
     */
    public List<ATextComponent> getSiblings() {
        return this.siblings;
    }

    /**
     * Iterate over all components and their siblings.
     *
     * @param consumer The consumer that will be called for every component
     * @return This component
     * @see TextUtils#iterateAll(ATextComponent, Consumer)
     */
    public ATextComponent forEach(final Consumer<ATextComponent> consumer) {
        consumer.accept(this);
        for (ATextComponent sibling : this.siblings) sibling.forEach(consumer);
        return this;
    }

    /**
     * @return The style of this component
     */
    @Nonnull
    public Style getStyle() {
        return this.style;
    }

    /**
     * Set the style of this component.
     *
     * @param style The new style
     * @return This component
     */
    public ATextComponent setStyle(@Nonnull final Style style) {
        this.style = style;
        return this;
    }

    /**
     * Modify the style of this component.
     *
     * @param styleConsumer The consumer that will be called with the current style
     * @return This component
     */
    public ATextComponent modifyStyle(final Consumer<Style> styleConsumer) {
        styleConsumer.accept(this.style);
        return this;
    }

    /**
     * Set the parent style of this component.
     *
     * @param style The new parent style
     * @return This component
     */
    public ATextComponent setParentStyle(@Nonnull final Style style) {
        this.style.setParent(style);
        return this;
    }

    /**
     * Set the style of this component as the parent style for all siblings.
     *
     * @return This component
     */
    public ATextComponent copyParentStyle() {
        for (ATextComponent sibling : this.siblings) {
            sibling.getStyle().setParent(this.style);
            sibling.copyParentStyle();
        }
        return this;
    }

    /**
     * Copy the style and siblings of this component to the given component.
     *
     * @param component The component to copy to
     * @param <C>       The type of the component
     * @return The given component
     */
    public <C extends ATextComponent> C putMetaCopy(final C component) {
        component.setStyle(this.style.copy());
        for (ATextComponent sibling : this.siblings) component.append(sibling.copy());
        return component;
    }

    /**
     * @return An unformatted string representation of this component
     */
    public String asUnformattedString() {
        StringBuilder out = new StringBuilder(this.asSingleString());
        for (ATextComponent sibling : this.siblings) out.append(sibling.asUnformattedString());
        return out.toString();
    }

    /**
     * @return A legacy formatted string representation of this component
     */
    public String asLegacyFormatString() {
        StringBuilder out = new StringBuilder();
        if (this.style.getColor() != null && this.style.getColor().isFormattingColor()) out.append(COLOR_CHAR).append(this.style.getColor().getCode());
        if (this.style.isObfuscated()) out.append(COLOR_CHAR).append(TextFormatting.OBFUSCATED.getCode());
        if (this.style.isBold()) out.append(COLOR_CHAR).append(TextFormatting.BOLD.getCode());
        if (this.style.isStrikethrough()) out.append(COLOR_CHAR).append(TextFormatting.STRIKETHROUGH.getCode());
        if (this.style.isUnderlined()) out.append(COLOR_CHAR).append(TextFormatting.UNDERLINE.getCode());
        if (this.style.isItalic()) out.append(COLOR_CHAR).append(TextFormatting.ITALIC.getCode());
        out.append(this.asSingleString());
        for (ATextComponent sibling : this.siblings) {
            ATextComponent copy = sibling.copy();
            copy.getStyle().setParent(this.style);
            out.append(copy.asLegacyFormatString());
        }
        return out.toString();
    }

    /**
     * @return An unformatted string representation of this component
     */
    public abstract String asSingleString();

    @Override
    public abstract ATextComponent copy();

    /**
     * Create a shallow copy of this component.<br>
     * This will only copy the component itself and not its siblings.
     *
     * @return The shallow copy
     */
    public ATextComponent shallowCopy() {
        ATextComponent copy = this.copy();
        copy.getSiblings().clear();
        return copy;
    }

    public abstract boolean equals(final Object o);

    public abstract int hashCode();

    public abstract String toString();

}
