package net.lenni0451.mcstructs.text.utils;

import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.components.KeybindComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.components.TranslationComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import net.lenni0451.mcstructs.text.translation.Translator;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
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
    public static TextComponent makeURLsClickable(final TextComponent component) {
        return replace(component, URL_PATTERN, comp -> {
            comp.getStyle().setClickEvent(ClickEvent.openUrl(URI.create(comp.asSingleString())));
            return comp;
        });
    }

    /**
     * Replace components in the given component and all siblings with the given function.<br>
     * This includes all arguments of translation components.
     *
     * @param component       The component to replace in
     * @param replaceFunction The function that will be called for every component
     * @return A new component with the replaced components
     */
    public static TextComponent replace(final TextComponent component, final Function<TextComponent, TextComponent> replaceFunction) {
        TextComponent out = component.shallowCopy();
        out = replaceFunction.apply(out);
        if (out instanceof TranslationComponent) {
            Object[] args = ((TranslationComponent) out).getArgs();
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof TextComponent) {
                    args[i] = replace((TextComponent) args[i], replaceFunction);
                }
            }
        }
        for (TextComponent sibling : component.getSiblings()) out.append(replace(sibling, replaceFunction));
        return out;
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
    public static TextComponent replace(final TextComponent component, final String searchRegex, final Function<TextComponent, TextComponent> replaceFunction) {
        TextComponent out;
        Pattern pattern = Pattern.compile(searchRegex);
        if (component instanceof StringComponent) {
            String text = component.asSingleString();
            Matcher matcher = pattern.matcher(text);
            List<TextComponent> parts = new ArrayList<>();
            int last = 0;
            while (matcher.find()) {
                int start = matcher.start();
                String match = matcher.group();

                if (start > last) parts.add(new StringComponent(text.substring(last, start)).setStyle(component.getStyle().copy()));
                TextComponent replace = replaceFunction.apply(new StringComponent(match).setStyle(component.getStyle().copy()));
                if (replace != null) parts.add(replace);
                last = matcher.end();
            }
            if (last < text.length()) parts.add(new StringComponent(text.substring(last)).setStyle(component.getStyle().copy()));
            if (parts.size() > 1) {
                out = new StringComponent("");
                for (TextComponent part : parts) out.append(part);
            } else {
                if (parts.size() == 1) out = parts.get(0).shallowCopy();
                else out = component.shallowCopy();
            }
        } else {
            out = component.shallowCopy();
        }
        for (TextComponent sibling : component.getSiblings()) {
            TextComponent replace = replace(sibling, searchRegex, replaceFunction);
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
    public static TextComponent replaceRGBColors(final TextComponent component) {
        TextComponent out = component.copy();
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
    public static TextComponent join(final TextComponent separator, final TextComponent... components) {
        if (components.length == 0) return new StringComponent("");
        if (components.length == 1) return components[0].copy();

        TextComponent out = null;
        for (TextComponent component : components) {
            if (out == null) out = new StringComponent("").append(component.copy());
            else out.append(separator.copy()).append(component.copy());
        }
        return out;
    }

    /**
     * Iterate over all siblings of the given component.<br>
     * This includes all arguments of translation components.
     *
     * @param component The component to iterate over
     * @param consumer  The consumer that will be called for every component
     * @see TextComponent#forEach(Consumer)
     */
    public static void iterateAll(final TextComponent component, final Consumer<TextComponent> consumer) {
        consumer.accept(component);
        if (component instanceof TranslationComponent) {
            TranslationComponent translationComponent = (TranslationComponent) component;
            for (Object arg : translationComponent.getArgs()) {
                if (arg instanceof TextComponent) iterateAll((TextComponent) arg, consumer);
            }
        }
        if (component.getStyle().getHoverEvent() != null) {
            HoverEvent hoverEvent = component.getStyle().getHoverEvent();
            if (hoverEvent instanceof TextHoverEvent) {
                iterateAll(((TextHoverEvent) hoverEvent).getText(), consumer);
            } else if (hoverEvent instanceof EntityHoverEvent) {
                EntityHoverEvent entityHoverEvent = (EntityHoverEvent) hoverEvent;
                if (entityHoverEvent.isModern()) {
                    TextComponent name = entityHoverEvent.asModern().getName();
                    if (name != null) iterateAll(name, consumer);
                }
            }
        }
        for (TextComponent sibling : component.getSiblings()) {
            iterateAll(sibling, consumer);
        }
    }

    /**
     * Recursively set the translator for all translation and keybind components in the given component.<br>
     * This includes all arguments of translation components.
     *
     * @param component  The component to set the translator for
     * @param translator The translator function
     */
    public static void setTranslator(final TextComponent component, @Nullable final Translator translator) {
        setTranslator(component, translator, translator);
    }

    /**
     * Recursively set the translators for all translation and keybind components in the given component.<br>
     * This includes all arguments of translation components.
     *
     * @param component      The component to set the translators for
     * @param textTranslator The translator function for text components
     * @param keyTranslator  The translator function for keybind components
     */
    public static void setTranslator(final TextComponent component, @Nullable final Translator textTranslator, @Nullable final Translator keyTranslator) {
        iterateAll(component, comp -> {
            if (comp instanceof TranslationComponent) {
                TranslationComponent translationComponent = (TranslationComponent) comp;
                translationComponent.setTranslator(textTranslator);
            } else if (comp instanceof KeybindComponent) {
                KeybindComponent keybindComponent = (KeybindComponent) comp;
                keybindComponent.setTranslator(keyTranslator);
            }
        });
    }

    /**
     * Split the given component by the given split string.<br>
     * An array of components will be returned where each component is a part.<br>
     * This method behaves like {@code string.split(split,  -1)} without regex.<br>
     * The original component will not be modified.
     *
     * @param component           The component to split
     * @param split               The split string
     * @param resolveTranslations If translations should be resolved before splitting
     * @return An array of components
     */
    public static TextComponent[] split(final TextComponent component, final String split, final boolean resolveTranslations) {
        TextComponent rootCopy = component.copy();
        rootCopy.mergeSiblingParentStyle(); //Make sure all siblings have the correct style
        //This allows us to handle every component independently without having to worry about the hierarchy
        //Using this approach is kind of cheating, but it's by far the easiest way

        List<TextComponent> components = new ArrayList<>();
        List<TextComponent> current = new ArrayList<>();
        Runnable addCurrent = () -> {
            boolean wasEmpty = current.isEmpty();
            current.removeIf(comp -> comp instanceof StringComponent && comp.asSingleString().isEmpty()); //Remove empty components to reduce the amount of components
            if (current.size() == 1) {
                components.add(current.get(0));
            } else if (!wasEmpty) {
                TextComponent part = new StringComponent("");
                for (TextComponent textComponent : current) part.append(textComponent);
                components.add(part);
            }
            current.clear();
        };
        rootCopy.forEach(comp -> {
            if (comp instanceof StringComponent || (comp instanceof TranslationComponent && resolveTranslations)) {
                String text = comp.asSingleString();
                if (text.contains(split)) {
                    String[] parts = text.split(split, -1 /*Keep empty parts*/);
                    for (int i = 0; i < parts.length; i++) {
                        String part = parts[i];
                        TextComponent partComp = new StringComponent(part).setStyle(comp.getStyle().copy());
                        current.add(partComp);

                        if (i != parts.length - 1) addCurrent.run(); //Don't add the last part since more text will follow
                    }
                } else {
                    current.add(comp.shallowCopy()); //No split found, just add the component
                }
            } else {
                current.add(comp.shallowCopy()); //Can't split this component, just add it
            }
        });
        addCurrent.run();
        return components.toArray(new TextComponent[0]);
    }

}
