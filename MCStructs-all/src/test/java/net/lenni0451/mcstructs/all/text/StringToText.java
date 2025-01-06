package net.lenni0451.mcstructs.all.text;

import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

/**
 * Convert a string to a text component.
 */
public class StringToText {

    public static void main(String[] args) {
        TextComponent text = TextComponentSerializer.V1_18.deserializeLenientReader("{\"text\":\"Hi\",\"color\":\"gold\"}");
        System.out.println(text);
    }

}
