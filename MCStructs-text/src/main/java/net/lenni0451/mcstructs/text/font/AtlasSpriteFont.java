package net.lenni0451.mcstructs.text.font;

import lombok.Value;
import net.lenni0451.mcstructs.core.Identifier;

@Value
public class AtlasSpriteFont implements FontDescription {

    private final Identifier atlasId;
    private final Identifier spriteId;

}
