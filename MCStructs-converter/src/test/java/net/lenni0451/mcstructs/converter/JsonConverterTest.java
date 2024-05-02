package net.lenni0451.mcstructs.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.converter.impl.v1_20_5.JsonConverter_v1_20_5;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonConverterTest {

    private static final JsonConverter_v1_20_5 CONVERTER = new JsonConverter_v1_20_5();

    @Test
    void testNumberArray() {
        JsonArray array = new JsonArray();
        for (int i = 0; i < 5; i++) array.add(new JsonPrimitive(i));

        assertArrayEquals(new byte[]{0, 1, 2, 3, 4}, CONVERTER.asByteArray(array).get());
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, CONVERTER.asIntArray(array).get());
        assertArrayEquals(new long[]{0, 1, 2, 3, 4}, CONVERTER.asLongArray(array).get());
    }

    @Test
    void arrayMerge() {
        JsonElement array = CONVERTER.emptyList();
        array = CONVERTER.mergeList(array, new JsonPrimitive(12)).get();
        array = CONVERTER.mergeList(array, new JsonPrimitive(true)).get();
        array = CONVERTER.mergeList(array, new JsonPrimitive("test")).get();

        JsonArray out = new JsonArray();
        out.add(new JsonPrimitive(12));
        out.add(new JsonPrimitive(true));
        out.add(new JsonPrimitive("test"));
        assertEquals(out, array);
    }

}
