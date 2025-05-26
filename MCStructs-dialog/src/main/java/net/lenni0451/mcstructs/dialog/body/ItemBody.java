package net.lenni0451.mcstructs.dialog.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import net.lenni0451.mcstructs.core.Identifier;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
public class ItemBody implements DialogBody {

    private final BodyType type = BodyType.ITEM;
    private ItemStack item;
    @Nullable
    private PlainMessageBody description = null;
    private boolean showDecorations = true;
    private boolean showTooltip = true;
    private int width = 16;
    private int height = 16;

    public ItemBody(final ItemStack item) {
        this.item = item;
    }


    @Value
    public static class ItemStack {
        private final Identifier id;
        private final int count;
        //TODO: item components
    }

}
