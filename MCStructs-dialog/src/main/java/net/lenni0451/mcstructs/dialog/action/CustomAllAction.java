package net.lenni0451.mcstructs.dialog.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
public class CustomAllAction implements DialogAction {

    private Identifier id;
    @Nullable
    private CompoundTag additions;

}
