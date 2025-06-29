package net.lenni0451.mcstructs.text.stringformat;

import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.components.TranslationComponent;
import net.lenni0451.mcstructs.text.stringformat.handling.ColorHandling;
import net.lenni0451.mcstructs.text.stringformat.handling.DeserializerUnknownHandling;
import net.lenni0451.mcstructs.text.stringformat.handling.SerializerUnknownHandling;
import net.lenni0451.mcstructs.text.stringformat.matcher.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * A class capable of reading and writing formatted strings in various formats.
 */
public abstract class StringFormat {

    /**
     * Create a new {@link VanillaStringFormat}.<br>
     * The color character is set to {@code '§'} and rgb down sampling is disabled.
     *
     * @return The new string format
     * @see VanillaStringFormat
     */
    public static StringFormat vanilla() {
        return vanilla('§', false);
    }

    /**
     * Create a new {@link VanillaStringFormat} with the given color character.<br>
     * The {@code downsampleRgbColors} parameter is set to {@code false}.
     *
     * @param colorChar The color character
     * @return The new string format
     * @see VanillaStringFormat
     */
    public static StringFormat vanilla(final char colorChar) {
        return vanilla(colorChar, false);
    }

    /**
     * Create a new {@link VanillaStringFormat} with the given down sampling parameter.<br>
     * The color character is set to {@code '§'}.
     *
     * @param downsampleRgbColors Downsample rgb colors to the closest vanilla color
     * @return The new string format
     * @see VanillaStringFormat
     */
    public static StringFormat vanilla(final boolean downsampleRgbColors) {
        return vanilla('§', downsampleRgbColors);
    }

    /**
     * Create a new {@link VanillaStringFormat} with the given color character and down sampling parameter.
     *
     * @param colorChar           The color character
     * @param downsampleRgbColors Downsample rgb colors to the closest vanilla color
     * @return The new string format
     * @see VanillaStringFormat
     */
    public static StringFormat vanilla(final char colorChar, final boolean downsampleRgbColors) {
        return new PrioritizingStringFormat(new VanillaStringFormat(colorChar, downsampleRgbColors), new VoidVanillaStringFormat(colorChar));
    }

    /**
     * Create a new {@link BungeeRgbStringFormat}.<br>
     * The color character is set to {@code '&'}.
     *
     * @return The new string format
     * @see BungeeRgbStringFormat
     */
    public static StringFormat bungee() {
        return bungee('&');
    }

    /**
     * Create a new {@link BungeeRgbStringFormat} with the given color character.
     *
     * @param colorChar The color character
     * @return The new string format
     * @see BungeeRgbStringFormat
     */
    public static StringFormat bungee(final char colorChar) {
        return new PrioritizingStringFormat(new BungeeRgbStringFormat(colorChar), new VanillaStringFormat(colorChar, false), new VoidVanillaStringFormat(colorChar));
    }

    /**
     * Create a new {@link AdventureRgbStringFormat}.<br>
     * The color character is set to {@code '&'}.
     *
     * @return The new string format
     * @see AdventureRgbStringFormat
     */
    public static StringFormat adventure() {
        return adventure('&');
    }

    /**
     * Create a new {@link AdventureRgbStringFormat} with the given color character.
     *
     * @param colorChar The color character
     * @return The new string format
     * @see AdventureRgbStringFormat
     */
    public static StringFormat adventure(final char colorChar) {
        return new PrioritizingStringFormat(new AdventureRgbStringFormat(colorChar), new VanillaStringFormat(colorChar, false), new VoidVanillaStringFormat(colorChar));
    }

    /**
     * Create a new {@link AnsiStringFormat} with true color enabled.
     *
     * @return The new string format
     */
    public static StringFormat ansi() {
        return ansi(true);
    }

    /**
     * Create a new {@link AnsiStringFormat} with the given true color parameter.
     *
     * @param trueColor If true color should be enabled
     * @return The new string format
     */
    public static StringFormat ansi(final boolean trueColor) {
        return new AnsiStringFormat(trueColor);
    }


    protected final char colorChar;

    public StringFormat(final char colorChar) {
        this.colorChar = colorChar;
    }

    /**
     * Convert a string to a {@link TextComponent}.<br>
     * The color handling is used to determine if color codes should be treated as reset codes.<br>
     * The unknown handling is used to determine what should happen if an unsupported formatting is encountered.<br>
     * The string will be split into multiple components if multiple formatting codes are present.<br>
     * Empty sections will be ignored.
     *
     * @param s               The string to convert
     * @param colorHandling   The color handling
     * @param unknownHandling The unknown handling
     * @return The converted text component
     */
    public TextComponent fromString(final String s, final ColorHandling colorHandling, final DeserializerUnknownHandling unknownHandling) {
        return this.fromString(s, colorHandling, unknownHandling, true);
    }

    /**
     * Convert a string to a {@link TextComponent}.<br>
     * The color handling is used to determine if color codes should be treated as reset codes.<br>
     * The unknown handling is used to determine what should happen if an unsupported formatting is encountered.<br>
     * The string will be split into multiple components if multiple formatting codes are present.<br>
     * Empty sections are ignored if {@code ignoreEmptySections} is {@code true}, otherwise they are included as empty components.
     *
     * @param s               The string to convert
     * @param colorHandling   The color handling
     * @param unknownHandling The unknown handling
     * @return The converted text component
     */
    public TextComponent fromString(final String s, final ColorHandling colorHandling, final DeserializerUnknownHandling unknownHandling, final boolean ignoreEmptySections) {
        TextStringReader reader = new TextStringReader(s);
        Style currentStyle = new Style();
        List<TextComponent> components = new ArrayList<>();
        StringBuilder currentText = new StringBuilder();
        while (reader.canRead()) {
            ResolvedFormatting resolved = this.resolve(reader);
            if (resolved == null) {
                currentText.append(reader.read());
            } else {
                TextFormatting formatting = resolved.get();
                if (formatting == null) {
                    formatting = unknownHandling.handle(resolved, currentText);
                    if (formatting == null) continue;
                }
                if (currentText.length() > 0 || !ignoreEmptySections) {
                    components.add(TextComponent.of(currentText.toString()).setStyle(currentStyle.copy()));
                    currentText.setLength(0);
                }
                if (formatting.equals(TextFormatting.RESET) || (colorHandling.equals(ColorHandling.RESET) && formatting.isColor())) {
                    currentStyle = new Style();
                }
                currentStyle.setFormatting(formatting);
            }
        }
        if (currentText.length() > 0 || !ignoreEmptySections) {
            components.add(TextComponent.of(currentText.toString()).setStyle(currentStyle));
        }
        if (components.size() == 1) return components.get(0);
        return TextComponent.of(components);
    }

    /**
     * Convert a {@link TextComponent} to a string.<br>
     * The unknown handling is used to determine what should happen if an unsupported formatting is encountered.<br>
     * A reset code will be added at the start of every new section if the previous section had any formattings.<br>
     * Some string formats may require a reset code at the end of the string. It is only inserted if {@link #shouldResetAtEnd()} return {@code true} and the last section had any formattings.<br>
     * The style of empty components will not be applied.
     *
     * @param component       The component to convert
     * @param colorHandling   The color handling
     * @param unknownHandling The unknown handling
     * @return The converted string
     */
    public String toString(final TextComponent component, final ColorHandling colorHandling, final SerializerUnknownHandling unknownHandling) {
        return this.toString(component, colorHandling, unknownHandling, true);
    }

    /**
     * Convert a {@link TextComponent} to a string.<br>
     * The unknown handling is used to determine what should happen if an unsupported formatting is encountered.<br>
     * A reset code will be added at the start of every new section if the previous section had any formattings.<br>
     * Some string formats may require a reset code at the end of the string. It is only inserted if {@link #shouldResetAtEnd()} return {@code true} and the last section had any formattings.<br>
     * Empty components are ignored if {@code ignoreEmptyComponents} is {@code true}, otherwise their style will be applied (and immediately overwritten by the next component's style).
     *
     * @param component       The component to convert
     * @param colorHandling   The color handling
     * @param unknownHandling The unknown handling
     * @return The converted string
     */
    public String toString(final TextComponent component, final ColorHandling colorHandling, final SerializerUnknownHandling unknownHandling, final boolean ignoreEmptyComponents) {
        StringBuilder output = new StringBuilder();
        Style lastStyle = null;
        List<TextComponent> texts = new ArrayList<>();
        texts.add(component.copy().mergeSiblingParentStyle());
        while (!texts.isEmpty()) {
            TextComponent current = texts.remove(0);
            if (current instanceof TranslationComponent) {
                TranslationComponent translation = (TranslationComponent) current;
                texts.add(0, translation.resolveIntoComponents().mergeSiblingParentStyle());
                for (int i = 0; i < translation.getSiblings().size(); i++) {
                    texts.add(i + 1, translation.getSiblings().get(i));
                }
            } else {
                String text = current.asSingleString();
                if (!text.isEmpty() || !ignoreEmptyComponents) {
                    Style currentStyle = current.getStyle();
                    if (!currentStyle.equals(lastStyle)) {
                        TextFormatting[] formattings = currentStyle.getFormattings();
                        if (lastStyle != null && !lastStyle.isEmpty()) {
                            if (colorHandling.equals(ColorHandling.FORMATTING) || formattings.length <= 0 || !formattings[0].isColor()) {
                                this.write(output, TextFormatting.RESET);
                            }
                        }
                        for (TextFormatting formatting : formattings) {
                            if (this.canWrite(formatting)) {
                                this.write(output, formatting);
                            } else {
                                TextFormatting resolved = unknownHandling.handle(formatting, output);
                                if (resolved != null && this.canWrite(resolved)) {
                                    this.write(output, resolved);
                                }
                            }
                        }
                        lastStyle = currentStyle;
                    }
                    output.append(current.asSingleString());
                }

                for (int i = 0; i < current.getSiblings().size(); i++) {
                    texts.add(i, current.getSiblings().get(i));
                }
            }
        }
        if (lastStyle != null && !lastStyle.isEmpty() && this.shouldResetAtEnd()) this.write(output, TextFormatting.RESET);
        return output.toString();
    }

    /**
     * Get the style of a string at the given index.<br>
     * If the index is smaller or equal to 0 the style will be empty.<br>
     * If the index is greater than the length of the string the last style will be returned.<br>
     * If the index is in the middle of a formatting code the index will be moved to the end of the formatting code.<br>
     * The color handling is used to determine if color codes should be treated as reset codes.<br>
     * The unknown handling is used to determine what should happen if an unsupported formatting is encountered.
     *
     * @param s               The string to get the style from
     * @param index           The index to get the style at
     * @param colorHandling   The color handling
     * @param unknownHandling The unknown handling
     * @return The style at the given index
     */
    public Style styleAt(final String s, final int index, final ColorHandling colorHandling, final DeserializerUnknownHandling unknownHandling) {
        TextStringReader reader = new TextStringReader(s);
        Style style = new Style();
        while (reader.canRead() && reader.getIndex() < index) {
            ResolvedFormatting resolved = this.resolve(reader);
            if (resolved == null) {
                reader.read();
            } else {
                TextFormatting formatting = resolved.get();
                if (formatting == null) {
                    formatting = unknownHandling.handle(resolved, new StringBuilder());
                    if (formatting == null) continue;
                }
                if (formatting.equals(TextFormatting.RESET) || (colorHandling.equals(ColorHandling.RESET) && formatting.isColor())) {
                    style = new Style();
                }
                style.setFormatting(formatting);
            }
        }
        return style;
    }

    /**
     * Convert a string from one format to another.<br>
     * Some string formats may require a reset code at the end of the string. It is only inserted if {@link #shouldResetAtEnd()} return {@code true} and the last section had any formattings.<br>
     * If you convert from a format which requires a reset code at the end to one that doesn't, the previous reset code will <b>not</b> be removed automatically.
     *
     * @param s      The string in the current format
     * @param target The target format
     * @return The string in the target format
     */
    public String convertTo(final String s, final StringFormat target) {
        TextStringReader reader = new TextStringReader(s);
        StringBuilder out = new StringBuilder();
        TextFormatting formatting = null;
        while (reader.canRead()) {
            ResolvedFormatting resolved = this.resolve(reader);
            if (resolved == null) {
                out.append(reader.read());
            } else {
                formatting = resolved.get();
                target.write(out, formatting);
            }
        }
        if (target.shouldResetAtEnd() && !TextFormatting.RESET.equals(formatting)) target.write(out, TextFormatting.RESET);
        return out.toString();
    }

    /**
     * Prepend a style to the given string.<br>
     * This only works with formatting codes (colors, bold, italic, etc.).<br>
     * If the style contains an unknown formatting code the {@link SerializerUnknownHandling} will be used to resolve it.
     *
     * @param s               The string to append the style to
     * @param style           The style to append
     * @param unknownHandling The unknown handling
     * @return The string with the appended style
     */
    public String prependStyle(final String s, final Style style, final SerializerUnknownHandling unknownHandling) {
        StringBuilder output = new StringBuilder();
        for (TextFormatting formatting : style.getFormattings()) {
            if (this.canWrite(formatting)) {
                this.write(output, formatting);
            } else {
                TextFormatting resolved = unknownHandling.handle(formatting, output);
                if (resolved != null && this.canWrite(resolved)) {
                    this.write(output, resolved);
                }
            }
        }
        return output.append(s).toString();
    }

    /**
     * Split a string by a given separator and keep the formatting of the previous part.<br>
     * The color handling is used to determine if color codes should be treated as reset codes.<br>
     * The unknown handling is used to determine what should happen if an unsupported formatting is encountered.
     *
     * @param s                           The string to split
     * @param split                       The split string
     * @param colorHandling               The color handling (for {@link #styleAt(String, int, ColorHandling, DeserializerUnknownHandling)})
     * @param serializerUnknownHandling   The serializer unknown handling (for {@link #prependStyle(String, Style, SerializerUnknownHandling)})
     * @param deserializerUnknownHandling The deserializer unknown handling (for {@link #styleAt(String, int, ColorHandling, DeserializerUnknownHandling)})
     * @return The split string
     */
    public String[] split(final String s, final String split, final ColorHandling colorHandling, final SerializerUnknownHandling serializerUnknownHandling, final DeserializerUnknownHandling deserializerUnknownHandling) {
        String[] parts = s.split(Pattern.quote(split));
        for (int i = 1; i < parts.length; i++) {
            String previous = parts[i - 1];
            Style style = this.styleAt(previous, previous.length(), colorHandling, deserializerUnknownHandling);
            parts[i] = this.prependStyle(parts[i], style, serializerUnknownHandling);
        }
        return parts;
    }

    /**
     * Resolve the next formatting code in the string.<br>
     * {@code null} will be returned if no formatting code is at the current position.<br>
     * If the formatting code is invalid the {@link ResolvedFormatting#get()} method will return {@code null}.<br>
     * This is a convenience method which combines {@link #canRead(TextStringReader)}, {@link #matches(TextStringReader)} and {@link #read(TextStringReader)}.
     *
     * @param reader The reader to read from
     * @return The resolved formatting code or {@code null} if no formatting code was found
     */
    @Nullable
    public ResolvedFormatting resolve(final TextStringReader reader) {
        if (!this.canRead(reader)) return null;
        reader.mark();
        if (!this.matches(reader)) {
            reader.reset();
            return null;
        }
        reader.reset();
        TextFormatting formatting = this.read(reader);
        String raw = reader.getMark();
        return new ResolvedFormatting(raw, formatting);
    }

    /**
     * Check if the reader can read the color character of this format.<br>
     * It is recommended to check this before calling {@link #matches(TextStringReader)}.
     *
     * @param reader The reader to check
     * @return If the reader can read the color character
     */
    public boolean canRead(final TextStringReader reader) {
        return reader.canRead() && reader.peek() == this.colorChar;
    }

    /**
     * Check if the reader matches the format of this string format.<br>
     * This method is allowed to modify the current position of the reader. It is recommended to use {@link TextStringReader#mark()} and {@link TextStringReader#reset()}.
     *
     * @param reader The reader to check
     * @return If the reader matches the format
     */
    public abstract boolean matches(final TextStringReader reader);

    /**
     * Read the next formatting code from the reader.<br>
     * The reader should be at the start of the formatting code when this method is called. Make sure to call {@link #matches(TextStringReader)} before calling this method.<br>
     * If the formatting code is invalid {@code null} should be returned.
     *
     * @param reader The reader to read from
     * @return The read formatting code or {@code null} if the formatting code is invalid
     */
    @Nullable
    public abstract TextFormatting read(final TextStringReader reader);

    /**
     * Check if this format can write the given text formatting.
     *
     * @param formatting The formatting to check
     * @return If this format can write the formatting
     */
    public abstract boolean canWrite(final TextFormatting formatting);

    /**
     * Write the given text formatting to the builder.<br>
     * This method should only be called if {@link #canWrite(TextFormatting)} returns {@code true}.
     *
     * @param builder    The builder to write to
     * @param formatting The formatting to write
     */
    public abstract void write(final StringBuilder builder, final TextFormatting formatting);

    /**
     * @return If a reset code should be added at the end of the string
     */
    public boolean shouldResetAtEnd() {
        return false;
    }


    protected static <T> T init(final T t, final Consumer<T> initializer) {
        initializer.accept(t);
        return t;
    }

}
