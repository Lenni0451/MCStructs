package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.ATextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

public class KeybindComponent extends ATextComponent {

    private static final Function<String, String> DEFAULT_TRANSLATOR = s -> s;

    private String keybind;
    private Function<String, String> translator = DEFAULT_TRANSLATOR;

    public KeybindComponent(final String keybind) {
        this.keybind = keybind;
    }

    public KeybindComponent(final String keybind, @Nonnull final Function<String, String> translator) {
        this.keybind = keybind;
        this.translator = translator;
    }

    /**
     * @return The keybind of this component
     */
    public String getKeybind() {
        return this.keybind;
    }

    /**
     * Set the keybind of this component.
     *
     * @param keybind The keybind
     * @return This component
     */
    public KeybindComponent setKeybind(final String keybind) {
        this.keybind = keybind;
        return this;
    }

    /**
     * Set the translator function used to translate the key.
     *
     * @param translator The translator function
     * @return This component
     */
    public KeybindComponent setTranslator(@Nullable final Function<String, String> translator) {
        if (translator == null) this.translator = DEFAULT_TRANSLATOR;
        else this.translator = translator;
        return this;
    }

    @Override
    public String asSingleString() {
        return this.translator.apply(this.keybind);
    }

    @Override
    public ATextComponent copy() {
        KeybindComponent copy = new KeybindComponent(this.keybind);
        copy.translator = this.translator;
        return this.putMetaCopy(copy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeybindComponent that = (KeybindComponent) o;
        return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.keybind, that.keybind) && Objects.equals(this.translator, that.translator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSiblings(), this.getStyle(), this.keybind, this.translator);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty())
                .add("style", this.getStyle(), style -> !style.isEmpty())
                .add("keybind", this.keybind)
                .add("translator", this.translator, translator -> translator != DEFAULT_TRANSLATOR)
                .toString();
    }

}
