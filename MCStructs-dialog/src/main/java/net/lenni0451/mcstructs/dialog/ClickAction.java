package net.lenni0451.mcstructs.dialog;

import lombok.Value;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;

import javax.annotation.Nullable;

@Value
public class ClickAction {

    private TextComponent label;
    @Nullable
    private TextComponent tooltip;
    private int width;
    @Nullable
    private ClickEvent onClick;

}
