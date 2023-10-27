package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.text.ATextComponent;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Function;

public class KeybindComponent extends ATextComponent {

    private final String keybind;
    private Function<String, String> translator = s -> s;

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
     * Set the translator function used to translate the key.
     *
     * @param translator The translator function
     * @return This component
     */
    public KeybindComponent setTranslator(@Nonnull final Function<String, String> translator) {
        this.translator = translator;
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
        return "KeybindComponent{" +
                "siblings=" + this.getSiblings() +
                ", style=" + this.getStyle() +
                ", keybind='" + this.keybind + '\'' +
                ", translator=" + this.translator +
                '}';
    }

}
