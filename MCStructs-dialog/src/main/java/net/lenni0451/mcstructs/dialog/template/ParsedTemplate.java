package net.lenni0451.mcstructs.dialog.template;

import lombok.Value;

@Value
public class ParsedTemplate {

    private String raw;
    private final StringTemplate parsed;

}
