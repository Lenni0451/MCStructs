package net.lenni0451.mcstructs.dialog.action;

import net.lenni0451.mcstructs.text.events.click.ClickEvent;

import javax.annotation.Nullable;
import java.util.Map;

public interface DialogAction {

    @Nullable
    ClickEvent toAction(final Map<String, ValueGetter> valueGetters);

}
