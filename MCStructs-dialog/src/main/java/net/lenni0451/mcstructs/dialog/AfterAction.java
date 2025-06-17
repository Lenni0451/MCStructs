package net.lenni0451.mcstructs.dialog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.mcstructs.converter.types.NamedType;

@Getter
@RequiredArgsConstructor
public enum AfterAction implements NamedType {

    CLOSE("close"),
    NONE("none"),
    WAIT_FOR_RESPONSE("wait_for_response");

    private final String name;

}
