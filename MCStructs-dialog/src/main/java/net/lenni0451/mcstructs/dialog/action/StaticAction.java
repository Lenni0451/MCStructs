package net.lenni0451.mcstructs.dialog.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;

import javax.annotation.Nullable;
import java.util.Map;

@Data
@AllArgsConstructor
public class StaticAction implements DialogAction {

    private ClickEvent value;

    @Nullable
    @Override
    public ClickEvent toAction(Map<String, ValueGetter> valueGetters) {
        return this.value;
    }

}
