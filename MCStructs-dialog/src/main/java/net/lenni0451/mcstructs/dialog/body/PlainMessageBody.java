package net.lenni0451.mcstructs.dialog.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.text.TextComponent;

@Data
@AllArgsConstructor
public class PlainMessageBody implements DialogBody {

    private final BodyType type = BodyType.PLAIN_MESSAGE;
    private TextComponent contents;
    private int width = 200;

    public PlainMessageBody(final TextComponent contents) {
        this.contents = contents;
    }

}
