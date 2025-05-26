package net.lenni0451.mcstructs.dialog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.mcstructs.converter.types.IdentifiedType;
import net.lenni0451.mcstructs.core.Identifier;

@Getter
@RequiredArgsConstructor
public enum DialogType implements IdentifiedType {

    NOTICE(Identifier.of("notice")),
    SERVER_LINKS(Identifier.of("server_links")),
    DIALOG_LIST(Identifier.of("dialog_list")),
    MULTI_ACTION(Identifier.of("multi_action")),
    MULTI_ACTION_INPUT_FORM(Identifier.of("multi_action_input_form")),
    SIMPLE_INPUT_FORM(Identifier.of("simple_input_form")),
    CONFIRMATION(Identifier.of("confirmation"));

    private final Identifier identifier;

}
