package net.lenni0451.mcstructs.all.text;

import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

/**
 * Create a string text component with a style and serialize it to a string.
 */
public class TextToString {

    public static void main(String[] args) {
        TextComponent text = new StringComponent("Test");
        text.setStyle(new Style().setFormatting(TextFormatting.RED));
        String serialized = TextComponentSerializer.V1_18.serialize(text);
        System.out.println(serialized);
    }

}
