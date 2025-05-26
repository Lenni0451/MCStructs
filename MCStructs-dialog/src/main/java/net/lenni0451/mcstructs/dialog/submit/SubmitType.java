package net.lenni0451.mcstructs.dialog.submit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubmitType {

    COMMAND_TEMPLATE("command_template"),
    CUSTOM_TEMPLATE("custom_template"),
    CUSTOM_FORM("custom_form"),
    ;

    private final String name;

}
