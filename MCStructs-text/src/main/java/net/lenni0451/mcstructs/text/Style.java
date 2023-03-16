package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.core.ICopyable;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;

import java.util.Objects;

public class Style implements ICopyable<Style> {

    private Style parent;
    private TextFormatting color;
    private Boolean obfuscated;
    private Boolean bold;
    private Boolean strikethrough;
    private Boolean underlined;
    private Boolean italic;
    private ClickEvent clickEvent;
    private AHoverEvent hoverEvent;
    private String insertion;
    private Identifier font;

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
    public Style setHoverEvent(final AHoverEvent hoverEvent) {
        this.hoverEvent = hoverEvent;
        return this;
    }

    /**
     * @return The hover event of this style
     */
    public AHoverEvent getHoverEvent() {
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
        return this.getColor() == null
                && this.getBold() == null
                && this.getItalic() == null
                && this.getUnderlined() == null
                && this.getStrikethrough() == null
                && this.getObfuscated() == null
                && this.getClickEvent() == null
                && this.getHoverEvent() == null
                && this.getInsertion() == null
                && this.getFont() == null;
    }

    @Override
    public Style copy() {
        Style style = new Style();
        style.parent = this.parent;
        style.color = this.color;
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
        return Objects.equals(parent, style.parent) && Objects.equals(color, style.color) && Objects.equals(obfuscated, style.obfuscated) && Objects.equals(bold, style.bold) && Objects.equals(strikethrough, style.strikethrough) && Objects.equals(underlined, style.underlined) && Objects.equals(italic, style.italic) && Objects.equals(clickEvent, style.clickEvent) && Objects.equals(hoverEvent, style.hoverEvent) && Objects.equals(insertion, style.insertion) && Objects.equals(font, style.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, color, obfuscated, bold, strikethrough, underlined, italic, clickEvent, hoverEvent, insertion, font);
    }

    @Override
    public String toString() {
        return "Style{" +
                "parent=" + parent +
                ", color=" + color +
                ", obfuscated=" + obfuscated +
                ", bold=" + bold +
                ", strikethrough=" + strikethrough +
                ", underlined=" + underlined +
                ", italic=" + italic +
                ", clickEvent=" + clickEvent +
                ", hoverEvent=" + hoverEvent +
                ", insertion='" + insertion + '\'' +
                ", font=" + font +
                "}";
    }

}
