package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.core.Copyable;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Style implements Copyable<Style> {

    private Style parent;
    private TextFormatting color;
    private Integer shadowColor;
    private Boolean obfuscated;
    private Boolean bold;
    private Boolean strikethrough;
    private Boolean underlined;
    private Boolean italic;
    private ClickEvent clickEvent;
    private HoverEvent hoverEvent;
    private String insertion;
    private Identifier font;

    public Style() {
    }

    public Style(final TextFormatting color, final Boolean obfuscated, final Boolean bold, final Boolean strikethrough, final Boolean underlined, final Boolean italic, final ClickEvent clickEvent, final HoverEvent hoverEvent, final String insertion, final Identifier font) {
        this(color, null, obfuscated, bold, strikethrough, underlined, italic, clickEvent, hoverEvent, insertion, font);
    }

    public Style(final TextFormatting color, final Integer shadowColor, final Boolean obfuscated, final Boolean bold, final Boolean strikethrough, final Boolean underlined, final Boolean italic, final ClickEvent clickEvent, final HoverEvent hoverEvent, final String insertion, final Identifier font) {
        if (color != null && !color.isColor()) throw new IllegalArgumentException("The color must be a color");
        this.color = color;
        this.shadowColor = shadowColor;
        this.obfuscated = obfuscated;
        this.bold = bold;
        this.strikethrough = strikethrough;
        this.underlined = underlined;
        this.italic = italic;
        this.clickEvent = clickEvent;
        this.hoverEvent = hoverEvent;
        this.insertion = insertion;
        this.font = font;
    }

    /**
     * Set the parent style.
     *
     * @param style The parent style
     * @return The current style
     */
    public Style setParent(final Style style) {
        this.parent = style;
        return this;
    }

    /**
     * @return The parent style
     */
    public Style getParent() {
        return this.parent;
    }

    /**
     * Set a formatting to this style.
     *
     * @param formatting The formatting
     * @return The current style
     */
    public Style setFormatting(final TextFormatting formatting) {
        if (formatting == null) return this;
        if (formatting.isColor()) {
            this.color = formatting;
        } else {
            if (TextFormatting.OBFUSCATED.equals(formatting)) {
                this.obfuscated = true;
            } else if (TextFormatting.BOLD.equals(formatting)) {
                this.bold = true;
            } else if (TextFormatting.STRIKETHROUGH.equals(formatting)) {
                this.strikethrough = true;
            } else if (TextFormatting.UNDERLINE.equals(formatting)) {
                this.underlined = true;
            } else if (TextFormatting.ITALIC.equals(formatting)) {
                this.italic = true;
            } else if (TextFormatting.RESET.equals(formatting)) {
                this.color = null;
                this.obfuscated = null;
                this.bold = null;
                this.strikethrough = null;
                this.underlined = null;
                this.italic = null;
                this.clickEvent = null;
                this.hoverEvent = null;
                this.insertion = null;
                this.font = null;
            } else {
                throw new IllegalArgumentException("Invalid TextFormatting " + formatting);
            }
        }
        return this;
    }

    /**
     * Set multiple formattings to this style.
     *
     * @param formattings The formattings
     * @return The current style
     */
    public Style setFormatting(final TextFormatting... formattings) {
        for (TextFormatting formatting : formattings) this.setFormatting(formatting);
        return this;
    }

    /**
     * Get the formattings of this style.<br>
     * The color will always be the first element in the array.
     *
     * @return The formattings
     */
    public TextFormatting[] getFormattings() {
        List<TextFormatting> formattings = new ArrayList<>();
        if (this.color != null) formattings.add(this.color);
        if (this.isObfuscated()) formattings.add(TextFormatting.OBFUSCATED);
        if (this.isBold()) formattings.add(TextFormatting.BOLD);
        if (this.isStrikethrough()) formattings.add(TextFormatting.STRIKETHROUGH);
        if (this.isUnderlined()) formattings.add(TextFormatting.UNDERLINE);
        if (this.isItalic()) formattings.add(TextFormatting.ITALIC);
        return formattings.toArray(new TextFormatting[0]);
    }

    /**
     * Set the rgb color of this style.
     *
     * @param rgb The rgb color
     * @return The current style
     */
    public Style setColor(final int rgb) {
        this.color = new TextFormatting(rgb);
        return this;
    }

    /**
     * @return The color of this style
     */
    public TextFormatting getColor() {
        if (this.color == null && this.parent != null) return this.parent.getColor();
        return this.color;
    }

    /**
     * Set the shadow color of this style.
     *
     * @param shadowColor The shadow color
     * @return The current style
     */
    public Style setShadowColor(final Integer shadowColor) {
        this.shadowColor = shadowColor;
        return this;
    }

    /**
     * @return The shadow color of this style
     */
    public Integer getShadowColor() {
        if (this.shadowColor == null && this.parent != null) return this.parent.getShadowColor();
        return this.shadowColor;
    }

    /**
     * Set this style to be bold.
     *
     * @param bold The bold state (use <code>null</code> to reset)
     * @return The current style
     */
    public Style setBold(final Boolean bold) {
        this.bold = bold;
        return this;
    }

    /**
     * @return The bold state of this style
     */
    public Boolean getBold() {
        if (this.bold == null && this.parent != null) return this.parent.getBold();
        return this.bold;
    }

    /**
     * @return If this style is bold
     */
    public boolean isBold() {
        Boolean bold = this.getBold();
        return bold != null && bold;
    }

    /**
     * Set this style to be italic.
     *
     * @param italic The italic state (use <code>null</code> to reset)
     * @return The current style
     */
    public Style setItalic(final Boolean italic) {
        this.italic = italic;
        return this;
    }

    /**
     * @return The italic state of this style
     */
    public Boolean getItalic() {
        if (this.italic == null && this.parent != null) return this.parent.getItalic();
        return this.italic;
    }

    /**
     * @return If this style is italic
     */
    public boolean isItalic() {
        Boolean italic = this.getItalic();
        return italic != null && italic;
    }

    /**
     * Set this style to be underlined.
     *
     * @param underlined The underlined state (use <code>null</code> to reset)
     * @return The current style
     */
    public Style setUnderlined(final Boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    /**
     * @return The underlined state of this style
     */
    public Boolean getUnderlined() {
        if (this.underlined == null && this.parent != null) return this.parent.getUnderlined();
        return this.underlined;
    }

    /**
     * @return If this style is underlined
     */
    public boolean isUnderlined() {
        Boolean underlined = this.getUnderlined();
        return underlined != null && underlined;
    }

    /**
     * Set this style to be strikethrough.
     *
     * @param strikethrough The strikethrough state (use <code>null</code> to reset)
     * @return The current style
     */
    public Style setStrikethrough(final Boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    /**
     * @return The strikethrough state of this style
     */
    public Boolean getStrikethrough() {
        if (this.strikethrough == null && this.parent != null) return this.parent.getStrikethrough();
        return this.strikethrough;
    }

    /**
     * @return If this style is strikethrough
     */
    public boolean isStrikethrough() {
        Boolean strikethrough = this.getStrikethrough();
        return strikethrough != null && strikethrough;
    }

    /**
     * Set this style to be obfuscated.
     *
     * @param obfuscated The obfuscated state (use <code>null</code> to reset)
     * @return The current style
     */
    public Style setObfuscated(final Boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }

    /**
     * @return The obfuscated state of this style
     */
    public Boolean getObfuscated() {
        if (this.obfuscated == null && this.parent != null) return this.parent.getObfuscated();
        return this.obfuscated;
    }

    /**
     * @return If this style is obfuscated
     */
    public boolean isObfuscated() {
        Boolean obfuscated = this.getObfuscated();
        return obfuscated != null && obfuscated;
    }

    /**
     * Set the click event of this style.
     *
     * @param clickEvent The click event
     * @return The current style
     */
    public Style setClickEvent(final ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
        return this;
    }

    /**
     * @return The click event of this style
     */
    public ClickEvent getClickEvent() {
        if (this.clickEvent == null && this.parent != null) return this.parent.getClickEvent();
        return this.clickEvent;
    }

    /**
     * Set the hover event of this style.
     *
     * @param hoverEvent The hover event
     * @return The current style
     */
    public Style setHoverEvent(final HoverEvent hoverEvent) {
        this.hoverEvent = hoverEvent;
        return this;
    }

    /**
     * @return The hover event of this style
     */
    public HoverEvent getHoverEvent() {
        if (this.hoverEvent == null && this.parent != null) return this.parent.getHoverEvent();
        return this.hoverEvent;
    }

    /**
     * Set the insertion of this style.
     *
     * @param insertion The insertion
     * @return The current style
     */
    public Style setInsertion(final String insertion) {
        this.insertion = insertion;
        return this;
    }

    /**
     * @return The insertion of this style
     */
    public String getInsertion() {
        if (this.insertion == null && this.parent != null) return this.parent.getInsertion();
        return this.insertion;
    }

    /**
     * Set the font of this style.
     *
     * @param font The font
     * @return The current style
     */
    public Style setFont(final Identifier font) {
        this.font = font;
        return this;
    }

    /**
     * @return The font of this style
     */
    public Identifier getFont() {
        if (this.font == null && this.parent != null) return this.parent.getFont();
        return this.font;
    }

    /**
     * @return If the style is empty
     */
    public boolean isEmpty() {
        return this.getColor() == null &&
                this.getShadowColor() == null &&
                this.getBold() == null &&
                this.getItalic() == null &&
                this.getUnderlined() == null &&
                this.getStrikethrough() == null &&
                this.getObfuscated() == null &&
                this.getClickEvent() == null &&
                this.getHoverEvent() == null &&
                this.getInsertion() == null &&
                this.getFont() == null;
    }

    /**
     * Merge the parent style with this style.<br>
     * This removes the reference to the parent style while maintaining the formatting.
     */
    public void mergeParent() {
        this.color = this.getColor();
        this.shadowColor = this.getShadowColor();
        this.bold = this.getBold();
        this.italic = this.getItalic();
        this.underlined = this.getUnderlined();
        this.strikethrough = this.getStrikethrough();
        this.obfuscated = this.getObfuscated();
        this.clickEvent = this.getClickEvent();
        this.hoverEvent = this.getHoverEvent();
        this.insertion = this.getInsertion();
        this.font = this.getFont();
        this.parent = null;
    }

    @Override
    public Style copy() {
        Style style = new Style();
        style.parent = this.parent;
        style.color = this.color;
        style.shadowColor = this.shadowColor;
        style.bold = this.bold;
        style.italic = this.italic;
        style.underlined = this.underlined;
        style.strikethrough = this.strikethrough;
        style.obfuscated = this.obfuscated;
        style.clickEvent = this.clickEvent;
        style.hoverEvent = this.hoverEvent;
        style.insertion = this.insertion;
        style.font = this.font;
        return style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Style style = (Style) o;
        return Objects.equals(this.parent, style.parent) && Objects.equals(this.color, style.color) && Objects.equals(this.shadowColor, style.shadowColor) && Objects.equals(this.obfuscated, style.obfuscated) && Objects.equals(this.bold, style.bold) && Objects.equals(this.strikethrough, style.strikethrough) && Objects.equals(this.underlined, style.underlined) && Objects.equals(this.italic, style.italic) && Objects.equals(this.clickEvent, style.clickEvent) && Objects.equals(this.hoverEvent, style.hoverEvent) && Objects.equals(this.insertion, style.insertion) && Objects.equals(this.font, style.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.parent, this.color, this.shadowColor, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("parent", this.parent, Objects::nonNull)
                .add("shadowColor", this.shadowColor, Objects::nonNull)
                .add("color", this.color, Objects::nonNull)
                .add("obfuscated", this.obfuscated, Objects::nonNull)
                .add("bold", this.bold, Objects::nonNull)
                .add("strikethrough", this.strikethrough, Objects::nonNull)
                .add("underlined", this.underlined, Objects::nonNull)
                .add("italic", this.italic, Objects::nonNull)
                .add("clickEvent", this.clickEvent, Objects::nonNull)
                .add("hoverEvent", this.hoverEvent, Objects::nonNull)
                .add("insertion", this.insertion, Objects::nonNull)
                .add("font", this.font, Objects::nonNull)
                .toString();
    }

}
