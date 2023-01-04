package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextComponentJsonUtilsTest {

    @Test
    void getBoolean() {
        JsonObject ob = new JsonObject();
        ob.addProperty("test", true);
        assertTrue(TextComponentJsonUtils.getBoolean(ob, "test"));
    }

    @Test
    void getInt() {
        JsonObject ob = new JsonObject();
        ob.addProperty("test", 1);
        assertEquals(1, TextComponentJsonUtils.getInt(ob, "test"));
    }

    @Test
    void getString() {
        JsonObject ob = new JsonObject();
        ob.addProperty("test", "test");
        assertEquals("test", TextComponentJsonUtils.getString(ob, "test"));
    }

    @Test
    void getJsonObject() {
        JsonObject ob = new JsonObject();
        ob.add("test", new JsonObject());
        assertEquals(new JsonObject(), TextComponentJsonUtils.getJsonObject(ob, "test"));
    }

}
