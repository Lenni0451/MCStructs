package net.lenni0451.mcstructs.dialog.template;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Value
public class ParsedTemplate {

    private String raw;
    private final StringTemplate parsed;

    public String apply(final Map<String, String> variables) {
        List<String> list = new ArrayList<>();
        for (String variable : this.parsed.getVariables()) {
            String value = variables.get(variable);
            if (value == null) value = "";
            list.add(value);
        }
        return this.parsed.substitute(list);
    }

}
