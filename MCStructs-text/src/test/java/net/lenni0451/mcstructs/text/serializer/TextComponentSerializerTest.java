package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextComponentSerializerTest {

    private static final String basicComponent = "{\"text\":\"Hello World\"}";
    private static final String lenientComponent = "{\"text\":\n//Test comment\n\"Hello World\"}";
    private static final StringComponent deserializedComponent = new StringComponent("Hello World");

    @Test
    void serialize() {
        assertEquals("\"Hello World\"", TextComponentSerializer.V1_8.serialize(deserializedComponent));
        assertEquals(basicComponent, TextComponentSerializer.V1_16.serialize(deserializedComponent));
    }

    @Test
    void serializeJson() {
        JsonElement element1_8 = TextComponentSerializer.V1_8.serializeJson(deserializedComponent);
        JsonElement element1_16 = TextComponentSerializer.V1_16.serializeJson(deserializedComponent);

        assertInstanceOf(JsonPrimitive.class, element1_8);
        assertTrue(element1_8.isJsonPrimitive());
        assertEquals("Hello World", element1_8.getAsString());

        assertInstanceOf(JsonObject.class, element1_16);
        assertTrue(element1_16.isJsonObject());
        assertEquals("Hello World", element1_16.getAsJsonObject().get("text").getAsString());
    }

    @Test
    void deserialize() {
        assertEquals(deserializedComponent, TextComponentSerializer.V1_8.deserialize(basicComponent));
        assertEquals(deserializedComponent, TextComponentSerializer.V1_8.deserialize(lenientComponent));
    }

    @Test
    void deserializeReader() {
        assertEquals(deserializedComponent, TextComponentSerializer.V1_8.deserializeReader(basicComponent));
        assertThrows(JsonSyntaxException.class, () -> TextComponentSerializer.V1_8.deserializeReader(lenientComponent));
    }

    @Test
    void deserializeLenientReader() {
        assertEquals(deserializedComponent, TextComponentSerializer.V1_8.deserializeLenientReader(basicComponent));
        assertEquals(deserializedComponent, TextComponentSerializer.V1_8.deserializeLenientReader(lenientComponent));
    }

}
