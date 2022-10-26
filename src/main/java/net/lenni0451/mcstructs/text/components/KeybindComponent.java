package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.text.ATextComponent;

import java.util.function.Function;

public class KeybindComponent extends ATextComponent {

    private final String keybind;
    private Function<String, String> translator = s -> s;

    public KeybindComponent(final String keybind) {
        this.keybind = keybind;
    }

    public String getKeybind() {
        return this.keybind;
    }

    public void setTranslator(Function<String, String> translator) {
        this.translator = translator;
    }

    @Override
    public String asString() {
        return this.translator.apply(this.keybind);
    }

    @Override
    public ATextComponent copy() {
        return new KeybindComponent(this.keybind);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }

}
