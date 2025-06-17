package net.lenni0451.mcstructs.dialog.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.converter.SerializedData;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
public class ItemBody implements DialogBody {

    private final BodyType type = BodyType.ITEM;
    private SerializedData<?> item;
    @Nullable
    private PlainMessageBody description = null;
    private boolean showDecorations = true;
    private boolean showTooltip = true;
    private int width = 16;
    private int height = 16;

    public ItemBody(final SerializedData<?> item) {
        this.item = item;
    }

}
