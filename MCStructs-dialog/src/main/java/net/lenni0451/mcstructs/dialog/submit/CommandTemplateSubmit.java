package net.lenni0451.mcstructs.dialog.submit;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.dialog.template.ParsedTemplate;

@Data
@AllArgsConstructor
public class CommandTemplateSubmit implements DialogSubmit {

    private final SubmitType type = SubmitType.COMMAND_TEMPLATE;
    private ParsedTemplate template;

}
