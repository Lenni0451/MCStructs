package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.core.Copyable;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtComponent;
import net.lenni0451.mcstructs.text.stringformat.StringFormat;
import net.lenni0451.mcstructs.text.stringformat.handling.ColorHandling;
import net.lenni0451.mcstructs.text.stringformat.handling.SerializerUnknownHandling;
import net.lenni0451.mcstructs.text.utils.TextUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

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
public abstract class TextComponent implements Copyable<TextComponent> {

    private static final StringFormat LEGACY_FORMAT = StringFormat.vanilla('§', false);

    private final List<TextComponent> siblings = new ArrayList<>();
    private Style style = new Style();

    /**
     * Append multiple strings to this component.
     *
     * @param strings The strings to append
     * @return This component
     */
    public TextComponent append(final String... strings) {
        for (String s : strings) this.siblings.add(new StringComponent(s));
        return this;
    }

    /**
     * Append a component to this component.
     *
     * @param component The component to append
     * @return This component
     */
    public TextComponent append(final TextComponent component) {
        this.siblings.add(component);
        return this;
    }

    /**
     * Append multiple components to this component.
     *
     * @param components The components to append
     * @return This component
     */
    public TextComponent append(final TextComponent... components) {
        Collections.addAll(this.siblings, components);
        return this;
    }

    public TextComponent append(final Iterable<TextComponent> components) {
        components.forEach(this.siblings::add);
        return this;
    }

    /**
     * @return The siblings of this component
     */
    public List<TextComponent> getSiblings() {
        return this.siblings;
    }

    /**
     * Iterate over all components and their siblings.
     *
     * @param consumer The consumer that will be called for every component
     * @return This component
     * @see TextUtils#iterateAll(TextComponent, Consumer)
     */
    public TextComponent forEach(final Consumer<TextComponent> consumer) {
        consumer.accept(this);
        for (TextComponent sibling : this.siblings) sibling.forEach(consumer);
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
    public TextComponent setStyle(@Nonnull final Style style) {
        this.style = style;
        return this;
    }

    /**
     * Modify the style of this component.
     *
     * @param styleConsumer The consumer that will be called with the current style
     * @return This component
     */
    public TextComponent styled(final Consumer<Style> styleConsumer) {
        styleConsumer.accept(this.style);
        return this;
    }

    /**
     * Set a formatting of the style of this component.
     *
     * @param formatting The formatting to set
     * @return This component
     */
    public TextComponent formatted(final TextFormatting formatting) {
        this.style.setFormatting(formatting);
        return this;
    }

    /**
     * Set the parent style of this component.
     *
     * @param style The new parent style
     * @return This component
     */
    public TextComponent setParentStyle(@Nonnull final Style style) {
        this.style.setParent(style);
        return this;
    }

    /**
     * Set the parent style of all siblings to this style.
     *
     * @return This component
     */
    public TextComponent setSiblingParentStyle() {
        for (TextComponent sibling : this.siblings) {
            sibling.getStyle().setParent(this.style);
            sibling.setSiblingParentStyle();
        }
        return this;
    }

    /**
     * Set the parent style of all siblings to this style and merge them together.<br>
     * This removes all parent references from the siblings while maintaining the style.
     *
     * @return This component
     * @see Style#mergeParent()
     */
    public TextComponent mergeSiblingParentStyle() {
        for (TextComponent sibling : this.siblings) {
            sibling.getStyle().setParent(this.style);
            sibling.getStyle().mergeParent();
            sibling.mergeSiblingParentStyle();
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
    public <C extends TextComponent> C copyMetaTo(final C component) {
        component.setStyle(this.style.copy());
        for (TextComponent sibling : this.siblings) component.append(sibling.copy());
        return component;
    }

    /**
     * @return An unformatted string representation of this component
     */
    public String asUnformattedString() {
        StringBuilder out = new StringBuilder(this.asSingleString());
        for (TextComponent sibling : this.siblings) out.append(sibling.asUnformattedString());
        return out.toString();
    }

    /**
     * @return A legacy formatted string representation of this component
     * @see StringFormat#vanilla(char, boolean)
     * @see StringFormat#toString(TextComponent, ColorHandling, SerializerUnknownHandling)
     */
    public String asLegacyFormatString() {
        return LEGACY_FORMAT.toString(this, ColorHandling.RESET, SerializerUnknownHandling.IGNORE);
    }

    /**
     * @return An unformatted string representation of this component
     */
    public abstract String asSingleString();

    @Override
    public abstract TextComponent copy();

    /**
     * Create a shallow copy of this component.<br>
     * This will only copy the component itself and not its siblings.
     *
     * @return The shallow copy
     */
    public TextComponent shallowCopy() {
        TextComponent copy = this.copy();
        copy.getSiblings().clear();
        return copy;
    }

    public abstract boolean equals(final Object o);

    public abstract int hashCode();

    public abstract String toString();


    public static StringComponent empty() {
        return new StringComponent();
    }

    public static StringComponent of(final String text) {
        return new StringComponent(text);
    }

    public static TextComponent of(final String... text) {
        if (text.length == 0) {
            return new StringComponent();
        } else if (text.length == 1) {
            return new StringComponent(text[0]);
        } else {
            StringComponent component = new StringComponent();
            component.append(text);
            return component;
        }
    }

    public static TextComponent of(final TextComponent... components) {
        if (components.length == 0) {
            return new StringComponent();
        } else if (components.length == 1) {
            return components[0];
        } else {
            StringComponent component = new StringComponent();
            component.append(components);
            return component;
        }
    }

    public static TextComponent of(final Iterable<TextComponent> components) {
        StringComponent component = new StringComponent();
        components.forEach(component::append);
        return component;
    }

    public static TranslationComponent translation(final String key, final List<Object> args) {
        return new TranslationComponent(key, args);
    }

    public static TranslationComponent translation(final String key, final Object... args) {
        return new TranslationComponent(key, args);
    }

    public static ScoreComponent score(final String name, final String objective) {
        return new ScoreComponent(name, objective);
    }

    public static ScoreComponent score(final String name, final String objective, @Nullable final String value) {
        return new ScoreComponent(name, objective, value);
    }

    public static SelectorComponent selector(final String selector) {
        return new SelectorComponent(selector);
    }

    public static SelectorComponent selector(final String selector, @Nullable final TextComponent separator) {
        return new SelectorComponent(selector, separator);
    }

    public static KeybindComponent keybind(final String keybind) {
        return new KeybindComponent(keybind);
    }

    public static BlockNbtComponent blockNbt(final String rawComponent, final boolean resolve, final String pos) {
        return new BlockNbtComponent(rawComponent, resolve, pos);
    }

    public static BlockNbtComponent blockNbt(final String rawComponent, final boolean resolve, @Nullable final TextComponent separator, final String pos) {
        return new BlockNbtComponent(rawComponent, resolve, separator, pos);
    }

    public static EntityNbtComponent entityNbt(final String component, final boolean resolve, final String selector) {
        return new EntityNbtComponent(component, resolve, selector);
    }

    public static EntityNbtComponent entityNbt(final String component, final boolean resolve, @Nullable final TextComponent separator, final String selector) {
        return new EntityNbtComponent(component, resolve, separator, selector);
    }

    public static StorageNbtComponent storageNbt(final String component, final boolean resolve, final Identifier id) {
        return new StorageNbtComponent(component, resolve, id);
    }

    public static StorageNbtComponent storageNbt(final String component, final boolean resolve, @Nullable final TextComponent separator, final Identifier id) {
        return new StorageNbtComponent(component, resolve, separator, id);
    }

}
