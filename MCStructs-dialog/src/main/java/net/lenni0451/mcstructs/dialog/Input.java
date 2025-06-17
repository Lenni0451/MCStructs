package net.lenni0451.mcstructs.dialog;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.dialog.input.DialogInput;

@Data
@AllArgsConstructor
public class Input {

    private String key;
    private DialogInput control;

}
