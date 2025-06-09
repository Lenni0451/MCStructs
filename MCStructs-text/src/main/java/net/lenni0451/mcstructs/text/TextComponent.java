package net.lenni0451.mcstructs.text;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.Copyable;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtSource;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtSource;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtSource;
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
 * - {@link BlockNbtSource}<br>
 * - {@link EntityNbtSource}<br>
 * - {@link StorageNbtSource}
 */
@EqualsAndHashCode
public abstract class TextComponent implements Copyable<TextComponent> {

    private static final StringFormat LEGACY_FORMAT = StringFormat.vanilla('§', false);

    private List<TextComponent> siblings;
    private Style style;

    /**
     * Append multiple strings to this component.
     *
     * @param strings The strings to append
     * @return This component
     */
    public TextComponent append(final String... strings) {
        for (String s : strings) this.getSiblings().add(new StringComponent(s));
        return this;
    }

    /**
     * Append a component to this component.
     *
     * @param component The component to append
     * @return This component
     */
    public TextComponent append(final TextComponent component) {
        this.getSiblings().add(component);
        return this;
    }

    /**
     * Append multiple components to this component.
     *
     * @param components The components to append
     * @return This component
     */
    public TextComponent append(final TextComponent... components) {
        Collections.addAll(this.getSiblings(), components);
        return this;
    }

    public TextComponent append(final Iterable<TextComponent> components) {
        components.forEach(this.getSiblings()::add);
        return this;
    }

    /**
     * @return The siblings of this component
     */
    public List<TextComponent> getSiblings() {
        if (this.siblings == null) this.siblings = new ArrayList<>();
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
        if (this.siblings != null) {
            for (TextComponent sibling : this.siblings) sibling.forEach(consumer);
        }
        return this;
    }

    /**
     * @return The style of this component
     */
    @Nonnull
    public Style getStyle() {
        if (this.style == null) this.style = new Style();
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
        styleConsumer.accept(this.getStyle());
        return this;
    }

    /**
     * Set a formatting of the style of this component.
     *
     * @param formatting The formatting to set
     * @return This component
     */
    public TextComponent formatted(final TextFormatting formatting) {
        this.getStyle().setFormatting(formatting);
        return this;
    }

    /**
     * Set the parent style of this component.
     *
     * @param style The new parent style
     * @return This component
     */
    public TextComponent setParentStyle(@Nonnull final Style style) {
        this.getStyle().setParent(style);
        return this;
    }

    /**
     * Set the parent style of all siblings to this style.
     *
     * @return This component
     */
    public TextComponent setSiblingParentStyle() {
        if (this.siblings == null) return this;
        for (TextComponent sibling : this.siblings) {
            sibling.getStyle().setParent(this.getStyle());
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
        if (this.siblings == null) return this;
        for (TextComponent sibling : this.siblings) {
            sibling.getStyle().setParent(this.getStyle());
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
        component.setStyle(this.getStyle().copy());
        if (this.siblings != null) {
            for (TextComponent sibling : this.siblings) component.append(sibling.copy());
        }
        return component;
    }

    /**
     * @return An unformatted string representation of this component
     */
    public String asUnformattedString() {
        StringBuilder out = new StringBuilder(this.asSingleString());
        if (this.siblings != null) {
            for (TextComponent sibling : this.siblings) out.append(sibling.asUnformattedString());
        }
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

    public static NbtComponent blockNbt(final String rawComponent, final boolean resolve, final String pos) {
        return new NbtComponent(rawComponent, resolve, new BlockNbtSource(pos));
    }

    public static NbtComponent blockNbt(final String rawComponent, final boolean resolve, @Nullable final TextComponent separator, final String pos) {
        return new NbtComponent(rawComponent, resolve, separator, new BlockNbtSource(pos));
    }

    public static NbtComponent entityNbt(final String component, final boolean resolve, final String selector) {
        return new NbtComponent(component, resolve, new EntityNbtSource(selector));
    }

    public static NbtComponent entityNbt(final String component, final boolean resolve, @Nullable final TextComponent separator, final String selector) {
        return new NbtComponent(component, resolve, separator, new EntityNbtSource(selector));
    }

    public static NbtComponent storageNbt(final String component, final boolean resolve, final Identifier id) {
        return new NbtComponent(component, resolve, new StorageNbtSource(id));
    }

    public static NbtComponent storageNbt(final String component, final boolean resolve, @Nullable final TextComponent separator, final Identifier id) {
        return new NbtComponent(component, resolve, separator, new StorageNbtSource(id));
    }

}
