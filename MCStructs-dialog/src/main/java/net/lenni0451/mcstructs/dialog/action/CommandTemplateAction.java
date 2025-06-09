package net.lenni0451.mcstructs.dialog.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.dialog.template.ParsedTemplate;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class CommandTemplateAction implements DialogAction {

    private ParsedTemplate template;

    @Nullable
    @Override
    public ClickEvent toAction(Map<String, ValueGetter> valueGetters) {
        Map<String, String> variables = new HashMap<>();
        for (Map.Entry<String, ValueGetter> entry : valueGetters.entrySet()) {
            variables.put(entry.getKey(), entry.getValue().asTemplateSubstitution());
        }
        String command = this.template.apply(variables);
        return ClickEvent.runCommand(command);
    }

}
