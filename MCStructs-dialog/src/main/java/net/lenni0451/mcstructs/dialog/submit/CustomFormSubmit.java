package net.lenni0451.mcstructs.dialog.submit;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.core.Identifier;

@Data
@AllArgsConstructor
public class CustomFormSubmit implements DialogSubmit {

    private final SubmitType type = SubmitType.CUSTOM_FORM;
    private Identifier id;

}
