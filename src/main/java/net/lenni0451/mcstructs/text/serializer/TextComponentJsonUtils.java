package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class TextComponentJsonUtils {

    public static String getString(final JsonElement element, final String key) {
        if (element.isJsonPrimitive()) return element.getAsString();
        else throw new JsonSyntaxException("Expected " + key + " to be a string, was " + element);
    }

    public static String getString(final JsonObject object, final String key) {
        if (!object.has(key)) throw new JsonSyntaxException("Missing " + key + ", expected to find a string");
        else return getString(object.get(key), key);
    }

    public static boolean getBoolean(final JsonElement element, final String key) {
        if (element.isJsonPrimitive()) return element.getAsBoolean();
        else throw new JsonSyntaxException("Expected " + key + " to be a boolean, was " + element);
    }

    public static boolean getBoolean(final JsonObject object, final String key) {
        if (!object.has(key)) throw new JsonSyntaxException("Missing " + key + ", expected to find boolean");
        else return getBoolean(object, key);
    }

    public static boolean getBoolean(final JsonObject object, final String key, final boolean fallback) {
        if (!object.has(key)) return fallback;
        else return getBoolean(object, key);
    }

}
