package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.components.StringComponent;

import java.util.ArrayList;
import java.util.List;

import static net.lenni0451.mcstructs.core.TextFormatting.COLOR_CHAR;

public abstract class ATextComponent {

    private final List<ATextComponent> siblings = new ArrayList<>();
    private Style style = new Style();

    public void append(final String s) {
        this.append(new StringComponent(s));
    }

    public void append(final ATextComponent component) {
        component.getStyle().setParent(this.style);
        this.siblings.add(component);
    }

    public List<ATextComponent> getSiblings() {
        return this.siblings;
    }

    public Style getStyle() {
        return this.style;
    }

    public void setStyle(final Style style) {
        this.style = style;
        for (ATextComponent sibling : this.siblings) sibling.getStyle().setParent(this.style);
    }

    public <C extends ATextComponent> C putMetaCopy(final C component) {
        component.setStyle(this.style.copy());
        for (ATextComponent sibling : this.siblings) component.append(sibling.copy());
        return component;
    }

    public String asUnformattedString() {
        StringBuilder out = new StringBuilder(this.asSingleString());
        for (ATextComponent sibling : this.siblings) out.append(sibling.asUnformattedString());
        return out.toString();
    }

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

    public abstract String asSingleString();

    public abstract ATextComponent copy();

    public abstract boolean equals(final Object o);

    public abstract int hashCode();

    public abstract String toString();

}
