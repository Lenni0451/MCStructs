package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextComponentSerializerTest {

    private static final String BASIC_COMPONENT = "{\"text\":\"Hello World\"}";
    private static final String LENIENT_COMPONENT = "{\"text\":\n//Test comment\n\"Hello World\"}";
    private static final StringComponent DESERIALIZED_COMPONENT = new StringComponent("Hello World");

    @Test
    void serialize() {
        assertEquals("\"Hello World\"", TextComponentSerializer.V1_8.serialize(DESERIALIZED_COMPONENT));
        assertEquals(BASIC_COMPONENT, TextComponentSerializer.V1_16.serialize(DESERIALIZED_COMPONENT));
    }

    @Test
    void serializeJson() {
        JsonElement element1_8 = TextComponentSerializer.V1_8.serializeJson(DESERIALIZED_COMPONENT);
        JsonElement element1_16 = TextComponentSerializer.V1_16.serializeJson(DESERIALIZED_COMPONENT);

        assertInstanceOf(JsonPrimitive.class, element1_8);
        assertTrue(element1_8.isJsonPrimitive());
        assertEquals("Hello World", element1_8.getAsString());

        assertInstanceOf(JsonObject.class, element1_16);
        assertTrue(element1_16.isJsonObject());
        assertEquals("Hello World", element1_16.getAsJsonObject().get("text").getAsString());
    }

    @Test
    void deserialize() {
        assertEquals(DESERIALIZED_COMPONENT, TextComponentSerializer.V1_8.deserialize(BASIC_COMPONENT));
        assertEquals(DESERIALIZED_COMPONENT, TextComponentSerializer.V1_8.deserialize(LENIENT_COMPONENT));
    }

    @Test
    void deserializeReader() {
        assertEquals(DESERIALIZED_COMPONENT, TextComponentSerializer.V1_8.deserializeReader(BASIC_COMPONENT));
        assertThrows(JsonSyntaxException.class, () -> TextComponentSerializer.V1_8.deserializeReader(LENIENT_COMPONENT));
    }

    @Test
    void deserializeLenientReader() {
        assertEquals(DESERIALIZED_COMPONENT, TextComponentSerializer.V1_8.deserializeLenientReader(BASIC_COMPONENT));
        assertEquals(DESERIALIZED_COMPONENT, TextComponentSerializer.V1_8.deserializeLenientReader(LENIENT_COMPONENT));
    }

}
