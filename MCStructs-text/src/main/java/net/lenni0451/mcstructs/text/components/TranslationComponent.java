package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.ATextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranslationComponent extends ATextComponent {

    private static final Function<String, String> NULL_TRANSLATOR = s -> null;
    private static final Pattern ARG_PATTERN = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

    private String key;
    private Object[] args;
    @Nullable
    private String fallback;
    private Function<String, String> translator = NULL_TRANSLATOR;

    public TranslationComponent(final String key, final List<?> args) {
        this.key = key;
        this.args = args.toArray();
    }

    public TranslationComponent(final String key, final Object... args) {
        this.key = key;
        this.args = args;
    }

    /**
     * @return The key of this component
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Set the key of this component.
     *
     * @param key The key
     * @return This component
     */
    public TranslationComponent setKey(final String key) {
        this.key = key;
        return this;
    }

    /**
     * @return The arguments of this component
     */
    public Object[] getArgs() {
        return this.args;
    }

    /**
     * Set the arguments of this component.
     *
     * @param args The arguments
     * @return This component
     */
    public TranslationComponent setArgs(final Object[] args) {
        this.args = args;
        return this;
    }

    /**
     * @return The fallback of this component
     */
    @Nullable
    public String getFallback() {
        return this.fallback;
    }

    /**
     * Set the fallback of this component.<br>
     * The fallback is used when the translation of the key was not found.
     *
     * @param fallback The fallback
     * @return This component
     */
    public TranslationComponent setFallback(@Nullable String fallback) {
        this.fallback = fallback;
        return this;
    }

    /**
     * Set the translator function used to translate the key.<br>
     * If a key is not found return <code>null</code>. This is required for the fallback to work.
     *
     * @param translator The translator function
     * @return This component
     */
    public TranslationComponent setTranslator(@Nullable final Function<String, String> translator) {
        if (translator == null) this.translator = NULL_TRANSLATOR;
        else this.translator = translator;
        return this;
    }

    public ATextComponent resolveIntoComponents() {
        List<ATextComponent> components = new ArrayList<>();
        String translated = this.translator.apply(this.key);
        if (translated == null) translated = this.fallback;
        if (translated == null) translated = this.key;
        Matcher matcher = ARG_PATTERN.matcher(translated);
        int argIndex = 0;
        int start = 0;
        while (matcher.find(start)) {
            int matchStart = matcher.start();
            int matchEnd = matcher.end();
            if (matchStart > start) components.add(new StringComponent(String.format(translated.substring(start, matchStart))));
            start = matchEnd;

            String argType = matcher.group(2);
            String match = translated.substring(matchStart, matchEnd);
            if (argType.equals("%") && match.equals("%%")) {
                components.add(new StringComponent("%"));
            } else {
                if (!argType.equals("s")) throw new IllegalStateException("Unsupported format: '" + match + "'");
                String rawIndex = matcher.group(1);
                int index;
                if (rawIndex == null) index = argIndex++;
                else index = Integer.parseInt(rawIndex) - 1;
                if (index < this.args.length) {
                    Object arg = this.args[index];
                    if (arg instanceof ATextComponent) components.add((ATextComponent) arg);
                    else if (arg == null) components.add(new StringComponent("null"));
                    else components.add(new StringComponent(arg.toString()));
                }
            }
        }
        if (start < translated.length()) components.add(new StringComponent(String.format(translated.substring(start))));
        if (components.size() == 1) return components.get(0);
        return new StringComponent().append(components.toArray(new ATextComponent[0]));
    }

    @Override
    public String asSingleString() {
        return this.resolveIntoComponents().asUnformattedString();
    }

    @Override
    public ATextComponent copy() {
        return this.putMetaCopy(this.shallowCopy());
    }

    @Override
    public ATextComponent shallowCopy() {
        Object[] copyArgs = new Object[this.args.length];
        for (int i = 0; i < this.args.length; i++) {
            Object arg = this.args[i];
            if (arg instanceof ATextComponent) copyArgs[i] = ((ATextComponent) arg).copy();
            else copyArgs[i] = arg;
        }
        TranslationComponent copy = new TranslationComponent(this.key, copyArgs);
        copy.translator = this.translator;
        return copy.setStyle(this.getStyle().copy());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslationComponent that = (TranslationComponent) o;
        return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.key, that.key) && Arrays.equals(this.args, that.args) && Objects.equals(this.translator, that.translator);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.getSiblings(), this.getStyle(), this.key, this.translator);
        result = 31 * result + Arrays.hashCode(this.args);
        return result;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty())
                .add("style", this.getStyle(), style -> !style.isEmpty())
                .add("key", this.key)
                .add("args", this.args, args -> args.length > 0, Arrays::toString)
                .add("translator", this.translator, translator -> translator != NULL_TRANSLATOR)
                .toString();
    }

}
