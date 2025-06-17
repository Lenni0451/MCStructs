package net.lenni0451.mcstructs.dialog.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.mcstructs.converter.types.IdentifiedType;
import net.lenni0451.mcstructs.core.Identifier;

@Getter
@RequiredArgsConstructor
public enum InputType implements IdentifiedType {

    BOOLEAN(Identifier.of("boolean")),
    NUMBER_RANGE(Identifier.of("number_range")),
    SINGLE_OPTION(Identifier.of("single_option")),
    TEXT(Identifier.of("text"));

    private final Identifier identifier;

}
