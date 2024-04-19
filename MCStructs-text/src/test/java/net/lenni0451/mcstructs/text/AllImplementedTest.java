package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class AllImplementedTest {

    @Test
    void verifyNothingIsNull() throws Throwable {
        for (Field field : TextComponentSerializer.class.getDeclaredFields()) {
            if (!field.getType().equals(TextComponentSerializer.class)) continue;
            TextComponentSerializer serializer = (TextComponentSerializer) field.get(null);
            if (serializer == null) throw new NullPointerException("Serializer " + field.getName() + " is null");
        }
        for (Field field : TextComponentCodec.class.getDeclaredFields()) {
            if (!field.getType().equals(TextComponentCodec.class)) continue;
            TextComponentCodec codec = (TextComponentCodec) field.get(null);
            if (codec == null) throw new NullPointerException("Codec " + field.getName() + " is null");
            if (codec.getJsonSerializer() == null) throw new NullPointerException("JsonSerializer " + field.getName() + " is null");
            if (codec.getNbtSerializer() == null) throw new NullPointerException("NbtSerializer " + field.getName() + " is null");
        }
    }

}
