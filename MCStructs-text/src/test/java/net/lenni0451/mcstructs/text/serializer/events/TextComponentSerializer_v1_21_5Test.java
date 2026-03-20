package net.lenni0451.mcstructs.text.serializer.events;

import com.google.gson.JsonElement;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.click.types.OpenUrlClickEvent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;
import net.lenni0451.mcstructs.text.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextComponentSerializer_v1_21_5Test {

    @Test
    void testSerialize() {
        TextComponent component = TextComponent.of("test").styled(style -> style.setClickEvent(new OpenUrlClickEvent("example.com")));
        JsonElement json = TextComponentCodec.V1_21_5.serializeJsonTree(component);
        json = JsonUtils.sort(json, Comparator.naturalOrder());
        assertEquals(json.toString(), "{\"click_event\":{\"action\":\"open_url\",\"url\":\"example.com\"},\"text\":\"test\"}");
    }

    @Test
    void testDeserialize() {
        // Starting with 1.21.5, Minecraft tries to parse URL click events as a URI
        // It requires the URI to have a scheme trusted by the client (http, https)
        // If the URI is not valid, the text component will not deserialize correctly while older versions were fine
        String json = "{\"click_event\":{\"action\":\"open_url\",\"url\":\"example.com\"},\"text\":\"test\"}";
        assertThrows(Throwable.class, () -> TextComponentCodec.V1_21_5.deserializeJson(json));
    }

}
