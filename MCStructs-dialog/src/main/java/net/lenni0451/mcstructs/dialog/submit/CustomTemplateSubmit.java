package net.lenni0451.mcstructs.dialog.submit;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.dialog.template.ParsedTemplate;

@Data
@AllArgsConstructor
public class CustomTemplateSubmit implements DialogSubmit {

    private final SubmitType type = SubmitType.CUSTOM_TEMPLATE;
    private Identifier id;
    private ParsedTemplate template;

}
