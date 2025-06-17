package net.lenni0451.mcstructs.dialog.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;

import javax.annotation.Nullable;
import java.util.Map;

@Data
@AllArgsConstructor
public class CustomAllAction implements DialogAction {

    private Identifier id;
    @Nullable
    private CompoundTag additions;

    @Nullable
    @Override
    public ClickEvent toAction(Map<String, ValueGetter> valueGetters) {
        CompoundTag tag = this.additions == null ? new CompoundTag() : this.additions.copy();
        for (Map.Entry<String, ValueGetter> entry : valueGetters.entrySet()) {
            tag.add(entry.getKey(), entry.getValue().asNbtTag());
        }
        return ClickEvent.custom(this.id, tag);
    }

}
