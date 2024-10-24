package net.lenni0451.mcstructs.text;

import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LatestVersionFieldTest {

    @Test
    void testTextComponentSerializer() throws IllegalAccessException {
        List<Field> fields = Arrays.stream(TextComponentSerializer.class.getDeclaredFields()).filter(f -> TextComponentSerializer.class.isAssignableFrom(f.getType())).collect(Collectors.toList());
        assertFalse(fields.isEmpty());
        assertEquals("LATEST", fields.get(fields.size() - 1).getName());
        assertSame(TextComponentSerializer.LATEST, fields.get(fields.size() - 2).get(null));
    }

    @Test
    void testTextComponentCodec() throws IllegalAccessException {
        List<Field> fields = Arrays.stream(TextComponentCodec.class.getDeclaredFields()).filter(f -> TextComponentCodec.class.isAssignableFrom(f.getType())).collect(Collectors.toList());
        assertFalse(fields.isEmpty());
        assertEquals("LATEST", fields.get(fields.size() - 1).getName());
        assertSame(TextComponentCodec.LATEST, fields.get(fields.size() - 2).get(null));
    }

}
