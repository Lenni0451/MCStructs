package net.lenni0451.mcstructs.text.utils;

import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    private static final String URL_PATTERN = "(?:https?://)?[\\w._-]+\\.\\w{2,}(?:/\\S*)?";

    /**
     * Make URLs in the given text component clickable.
     *
     * @param component The component to make clickable
     * @return The component with clickable URLs
     */
    public static ATextComponent makeURLsClickable(final ATextComponent component) {
        return replace(component, URL_PATTERN, comp -> {
            comp.getStyle().setClickEvent(new ClickEvent(ClickEventAction.OPEN_URL, comp.asSingleString()));
            return comp;
        });
    }

    /**
     * Replace all matches of the given pattern in this component and all siblings.<br>
     * This only works for {@link StringComponent} components.
     *
     * @param component       The component to replace in
     * @param searchRegex     The regex to search for
     * @param replaceFunction The function that will be called for every match
     * @return A new component with the replaced text
     */
    public static ATextComponent replace(final ATextComponent component, final String searchRegex, final Function<ATextComponent, ATextComponent> replaceFunction) {
        ATextComponent out;
        Pattern pattern = Pattern.compile(searchRegex);
        if (component instanceof StringComponent) {
            String text = component.asSingleString();
            Matcher matcher = pattern.matcher(text);
            List<ATextComponent> parts = new ArrayList<>();
            int last = 0;
            while (matcher.find()) {
                int start = matcher.start();
                String match = matcher.group();

                if (start > last) parts.add(new StringComponent(text.substring(last, start)).setStyle(component.getStyle().copy()));
                ATextComponent replace = replaceFunction.apply(new StringComponent(match).setStyle(component.getStyle().copy()));
                if (replace != null) parts.add(replace);
                last = matcher.end();
            }
            if (last < text.length()) parts.add(new StringComponent(text.substring(last)).setStyle(component.getStyle().copy()));
            if (parts.size() > 1) {
                out = new StringComponent("");
                for (ATextComponent part : parts) out.append(part);
            } else {
                if (parts.size() == 1) out = parts.get(0).copy();
                else out = component.copy();
                out.getSiblings().clear();
            }
        } else {
            out = component.copy();
            out.getSiblings().clear();
        }
        for (ATextComponent sibling : component.getSiblings()) {
            ATextComponent replace = replace(sibling, searchRegex, replaceFunction);
            out.append(replace);
        }

        return out;
    }

    /**
     * Replace all rgb color codes with the nearest formatting color.<br>
     * This <b>can not</b> perfectly convert the colors since there is only a limited amount of formatting colors.<br>
     * Minecraft 1.16 has added support for rgb colors which older versions can't display.
     *
     * @param component The component to replace in
     * @return A new component with the replaced text
     */
    public static ATextComponent replaceRGBColors(final ATextComponent component) {
        ATextComponent out = component.copy();
        out.forEach(comp -> {
            if (comp.getStyle().getColor() != null && comp.getStyle().getColor().isRGBColor()) {
                comp.getStyle().setFormatting(TextFormatting.getClosestFormattingColor(comp.getStyle().getColor().getRgbValue()));
            }
        });
        return out;
    }

    /**
     * Join the given components with the given separator.<br>
     * All components are copied before they are joined.<br>
     * If there are no components an empty {@link StringComponent} will be returned.<br>
     * If there is only one component it will be copied and returned.
     *
     * @param separator  The separator
     * @param components The components
     * @return The joined component
     */
    public static ATextComponent join(final ATextComponent separator, final ATextComponent... components) {
        if (components.length == 0) return new StringComponent("");
        if (components.length == 1) return components[0].copy();

        ATextComponent out = null;
        for (ATextComponent component : components) {
            if (out == null) out = new StringComponent("").append(component.copy());
            else out.append(separator.copy()).append(component.copy());
        }
        return out;
    }

    /**
     * Calculate the width of a component using the given widths array.<br>
     * Minecraft takes the ceil of the width.
     *
     * @param component The component to calculate the width of
     * @param widths    The char widths array
     * @return The width of the component
     */
    public static float getComponentWidth(final ATextComponent component, final float[] widths) {
        return getComponentWidth(component, widths, 1);
    }

    /**
     * Calculate the width of a component using the given widths array.<br>
     * The bold offset is 1 for the vanilla font and 0.5 for unihex fonts.<br>
     * Minecraft takes the ceil of the width.
     *
     * @param component  The component to calculate the width of
     * @param widths     The char widths array
     * @param boldOffset The bold offset
     * @return The width of the component
     */
    public static float getComponentWidth(final ATextComponent component, final float[] widths, final float boldOffset) {
        float[] width = new float[]{0};
        component.forEach(comp -> {
            char[] chars = comp.asSingleString().toCharArray();
            for (char c : chars) width[0] += c >= widths.length ? 0 : widths[c];
            if (comp.getStyle().isBold()) width[0] += boldOffset * chars.length;
        });
        return width[0];
    }

}
