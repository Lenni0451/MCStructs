package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

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
public abstract class ATextComponent {

    private final List<ATextComponent> siblings = new ArrayList<>();
    private Style style = new Style();

    /**
     * Append a string to this component.
     *
     * @param s The string to append
     */
    public void append(final String s) {
        this.append(new StringComponent(s));
    }

    /**
     * Append a component to this component.
     *
     * @param component The component to append
     */
    public void append(final ATextComponent component) {
        component.getStyle().setParent(this.style);
        this.siblings.add(component);
    }

    /**
     * @return The siblings of this component
     */
    public List<ATextComponent> getSiblings() {
        return this.siblings;
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
     */
    public void setStyle(@Nonnull final Style style) {
        this.style = style;
        for (ATextComponent sibling : this.siblings) sibling.getStyle().setParent(this.style);
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
        for (ATextComponent sibling : this.siblings) out.append(sibling.asLegacyFormatString());
        return out.toString();
    }

    /**
     * @return An unformatted string representation of this component
     */
    public abstract String asSingleString();

    /**
     * @return A copy of this component
     */
    public abstract ATextComponent copy();

    public abstract boolean equals(final Object o);

    public abstract int hashCode();

    public abstract String toString();

}
