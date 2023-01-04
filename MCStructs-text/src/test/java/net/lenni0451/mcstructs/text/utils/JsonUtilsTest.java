package net.lenni0451.mcstructs.text.utils;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonUtilsTest {

    @Test
    void getBoolean() {
        JsonObject ob = new JsonObject();
        ob.addProperty("test", true);
        assertTrue(JsonUtils.getBoolean(ob, "test"));
    }

    @Test
    void getInt() {
        JsonObject ob = new JsonObject();
        ob.addProperty("test", 1);
        assertEquals(1, JsonUtils.getInt(ob, "test"));
    }

    @Test
    void getString() {
        JsonObject ob = new JsonObject();
        ob.addProperty("test", "test");
        assertEquals("test", JsonUtils.getString(ob, "test"));
    }

    @Test
    void getJsonObject() {
        JsonObject ob = new JsonObject();
        ob.add("test", new JsonObject());
        assertEquals(new JsonObject(), JsonUtils.getJsonObject(ob, "test"));
    }

    @Test
    void toSortedString() {
        JsonObject unsorted = new JsonObject();
        unsorted.addProperty("b", "b");
        unsorted.addProperty("a", "a");

        String unsortedString = unsorted.toString();
        String sortedString = JsonUtils.toSortedString(unsorted, null);

        assertEquals("{\"b\":\"b\",\"a\":\"a\"}", unsortedString);
        assertEquals("{\"a\":\"a\",\"b\":\"b\"}", sortedString);
    }

}
