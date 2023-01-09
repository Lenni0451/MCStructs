package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;

import java.awt.*;

/**
 * Easily build and style text components.
 */
public class TextComponentBuilder {

    /**
     * Build a new text component from the given parts.<br>
     * Special types:<br>
     * - {@code null}: End the current component and start a new one<br>
     * - {@link TextFormatting}: Add a new formatting to the current style<br>
     * - {@link Color}: Add a new rgb color to the current style<br>
     * - {@link ClickEvent}: Add a new click event to the current style<br>
     * - {@link AHoverEvent}: Add a new hover event to the current style<br>
     * - {@link Style}: Set the current style<br>
     * <br>
     * All other objects will be converted to a string and added to the current component.<br>
     * The order of the parts is important:<br>
     * First you have to add the text and then the formattings and events.<br>
     * <br>
     * Example:<br>
     * <code>build("Hello", Formatting.RED, " World", Formatting.GREEN)</code><br>
     * This results in a component with "Hello" in red and " World" in green.
     *
     * @param parts The parts of the text component
     * @return The built text component
     */
    public static ATextComponent build(final Object... parts) {
        StringComponent out = new StringComponent("");

        StringComponent current = null;
        Style style = new Style();
        for (Object part : parts) {
            if (part == null) {
                checkAppend(out, current, style);
                current = null;
                style = new Style();
            } else if (part instanceof TextFormatting) {
                style.setFormatting((TextFormatting) part);
            } else if (part instanceof Color) {
                style.setFormatting(new TextFormatting(((Color) part).getRGB()));
            } else if (part instanceof ClickEvent) {
                style.setClickEvent((ClickEvent) part);
            } else if (part instanceof AHoverEvent) {
                style.setHoverEvent((AHoverEvent) part);
            } else if (part instanceof Style) {
                style = (Style) part;
            } else if (part instanceof ATextComponent) {
                if (checkAppend(out, current, style)) {
                    current = null;
                    style = new Style();
                }
                if (current == null) current = (StringComponent) part;
                else current.append((ATextComponent) part);
            } else {
                if (checkAppend(out, current, style)) {
                    current = null;
                    style = new Style();
                }
                if (current == null) current = new StringComponent(part.toString());
                else current.append(part.toString());
            }
        }
        if (current != null) {
            if (!style.isEmpty()) current.setStyle(style);
            out.append(current);
        }
        if (out.getSiblings().size() == 1) return out.getSiblings().get(0);
        return out;
    }

    private static boolean checkAppend(final ATextComponent out, final ATextComponent current, final Style style) {
        if (current == null) return !style.isEmpty();
        if (style.isEmpty()) return false;
        out.append(current.setStyle(style));
        return true;
    }

}
