package net.lenni0451.mcstructs.dialog.serializer;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LatestVersionFieldTest {

    @Test
    void testDialogSerializer() throws IllegalAccessException {
        List<Field> fields = Arrays.stream(DialogSerializer.class.getDeclaredFields()).filter(f -> DialogSerializer.class.isAssignableFrom(f.getType())).collect(Collectors.toList());
        assertFalse(fields.isEmpty());
        assertEquals("LATEST", fields.get(fields.size() - 1).getName());
        assertSame(DialogSerializer.LATEST, fields.get(fields.size() - 2).get(null));
    }

}
