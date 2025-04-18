package net.lenni0451.mcstructs.text.components;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.translation.Translator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@EqualsAndHashCode(callSuper = true)
public class KeybindComponent extends TextComponent {

    private String keybind;
    private Translator translator;

    public KeybindComponent(final String keybind) {
        this(keybind, Translator.GLOBAL);
    }

    public KeybindComponent(final String keybind, @Nonnull Translator translator) {
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
    public KeybindComponent setTranslator(@Nullable final Translator translator) {
        if (translator == null) this.translator = Translator.GLOBAL;
        else this.translator = translator;
        return this;
    }

    @Override
    public String asSingleString() {
        return this.translator.translateOrKey(this.keybind);
    }

    @Override
    public TextComponent copy() {
        return this.copyMetaTo(this.shallowCopy());
    }

    @Override
    public TextComponent shallowCopy() {
        KeybindComponent copy = new KeybindComponent(this.keybind);
        copy.translator = this.translator;
        return copy.setStyle(this.getStyle().copy());
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty())
                .add("style", this.getStyle(), style -> !style.isEmpty())
                .add("keybind", this.keybind)
                .add("translator", this.translator, translator -> translator != Translator.GLOBAL)
                .toString();
    }

}
