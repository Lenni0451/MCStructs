package net.lenni0451.mcstructs.dialog.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.dialog.template.ParsedTemplate;

@Data
@AllArgsConstructor
public class CommandTemplateAction implements DialogAction {

    private ParsedTemplate template;

}
