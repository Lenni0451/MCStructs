package net.lenni0451.mcstructs.dialog.body;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.mcstructs.converter.types.IdentifiedType;
import net.lenni0451.mcstructs.core.Identifier;

@Getter
@RequiredArgsConstructor
public enum BodyType implements IdentifiedType {

    ITEM(Identifier.of("item")),
    PLAIN_MESSAGE(Identifier.of("plain_message"));

    private final Identifier identifier;

}
