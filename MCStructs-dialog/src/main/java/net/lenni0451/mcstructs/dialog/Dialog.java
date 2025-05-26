package net.lenni0451.mcstructs.dialog;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.dialog.body.DialogBody;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;
import java.util.List;

@Data
@AllArgsConstructor
public abstract class Dialog {

    private final DialogType type;
    private TextComponent title;
    @Nullable
    private TextComponent externalTitle = null;
    private boolean canCloseWithEscape;
    private List<DialogBody> body;

}
