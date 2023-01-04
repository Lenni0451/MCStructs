package net.lenni0451.mcstructs.text.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JsonUtils {

    /**
     * Get a boolean value from a json element.
     *
     * @param element The element to get the value from
     * @param key     The key of the value
     * @return The value
     * @throws JsonSyntaxException If the value is not a json primitive boolean
     */
    public static boolean getBoolean(final JsonElement element, final String key) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()) return element.getAsBoolean();
        else throw new JsonSyntaxException("Expected " + key + " to be a boolean, was " + element);
    }

    /**
     * Get a boolean value from a json object.
     *
     * @param object The object to get the value from
     * @param key    The key of the value
     * @return The value
     * @throws JsonSyntaxException If the json object does not contain the key or the value is not a json primitive boolean
     */
    public static boolean getBoolean(final JsonObject object, final String key) {
        if (object.has(key)) return getBoolean(object.get(key), key);
        else throw new JsonSyntaxException("Missing " + key + ", expected to find a boolean");
    }

    /**
     * Get a boolean value from a json object with a default value.
     *
     * @param object   The object to get the value from
     * @param key      The key of the value
     * @param fallback The default value
     * @return The value
     * @throws JsonSyntaxException If the value is not a json primitive boolean
     */
    public static boolean getBoolean(final JsonObject object, final String key, final boolean fallback) {
        if (object.has(key)) return getBoolean(object, key);
        else return fallback;
    }


    /**
     * Get a int value from a json element.
     *
     * @param element The element to get the value from
     * @param key     The key of the value
     * @return The value
     * @throws JsonSyntaxException If the value is not a json primitive int
     */
    public static int getInt(final JsonElement element, final String key) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) return element.getAsInt();
        else throw new JsonSyntaxException("Expected " + key + " to be a boolean, was " + element);
    }

    /**
     * Get a int value from a json object.
     *
     * @param object The object to get the value from
     * @param key    The key of the value
     * @return The value
     * @throws JsonSyntaxException If the json object does not contain the key or the value is not a json primitive int
     */
    public static int getInt(final JsonObject object, final String key) {
        if (object.has(key)) return getInt(object.get(key), key);
        else throw new JsonSyntaxException("Missing " + key + ", expected to find a boolean");
    }

    /**
     * Get a int value from a json object with a default value.
     *
     * @param object   The object to get the value from
     * @param key      The key of the value
     * @param fallback The default value
     * @return The value
     * @throws JsonSyntaxException If the value is not a json primitive int
     */
    public static int getInt(final JsonObject object, final String key, final int fallback) {
        if (object.has(key)) return getInt(object, key);
        else return fallback;
    }


    /**
     * Get a string value from a json element.
     *
     * @param element The element to get the value from
     * @param key     The key of the value
     * @return The value
     * @throws JsonSyntaxException If the value is not a json primitive string
     */
    public static String getString(final JsonElement element, final String key) {
        if (element.isJsonPrimitive()) return element.getAsString();
        else throw new JsonSyntaxException("Expected " + key + " to be a string, was " + element);
    }

    /**
     * Get a string value from a json object.
     *
     * @param object The object to get the value from
     * @param key    The key of the value
     * @return The value
     * @throws JsonSyntaxException If the json object does not contain the key or the value is not a json primitive string
     */
    public static String getString(final JsonObject object, final String key) {
        if (object.has(key)) return getString(object.get(key), key);
        else throw new JsonSyntaxException("Missing " + key + ", expected to find a string");
    }

    /**
     * Get a string value from a json object with a default value.
     *
     * @param object   The object to get the value from
     * @param key      The key of the value
     * @param fallback The default value
     * @return The value
     * @throws JsonSyntaxException If the value is not a json primitive string
     */
    public static String getString(final JsonObject object, final String key, final String fallback) {
        if (object.has(key)) return getString(object, key);
        else return fallback;
    }


    /**
     * Get a json object value from a json element.
     *
     * @param element The element to get the value from
     * @param key     The key of the value
     * @return The value
     * @throws JsonSyntaxException If the value is not a json object
     */
    public static JsonObject getJsonObject(final JsonElement element, final String key) {
        if (element.isJsonObject()) return element.getAsJsonObject();
        else throw new JsonSyntaxException("Expected " + key + " to be a JsonObject, was " + element);
    }

    /**
     * Get a json object value from a json object.
     *
     * @param object The object to get the value from
     * @param key    The key of the value
     * @return The value
     * @throws JsonSyntaxException If the json object does not contain the key or the value is not a json object
     */
    public static JsonObject getJsonObject(final JsonObject object, final String key) {
        if (object.has(key)) return getJsonObject(object.get(key), key);
        else throw new JsonSyntaxException("Missing " + key + ", expected to find a JsonObject");
    }


    /**
     * Convert a json element to a sorted string.<br>
     * If the {@code comparator} is null, {@link Comparator#naturalOrder()} will be used.
     *
     * @param element    The element to convert
     * @param comparator The comparator to use
     * @return The sorted string
     */
    public static String toSortedString(@Nullable final JsonElement element, @Nullable final Comparator<String> comparator) {
        if (element == null) return null;
        else if (comparator != null) return sort(element, comparator).toString();
        else return sort(element, Comparator.naturalOrder()).toString();
    }


    /**
     * Sort a json element.
     *
     * @param element    The element to sort
     * @param comparator The comparator to use
     * @return The sorted element
     */
    public static JsonElement sort(@Nullable final JsonElement element, @Nonnull final Comparator<String> comparator) {
        if (element == null) {
            return null;
        } else if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            for (int i = 0; i < array.size(); i++) array.set(i, sort(array.get(i), comparator));
            return array;
        } else if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            JsonObject sorted = new JsonObject();
            List<String> keys = new ArrayList<>(object.keySet());
            keys.sort(comparator);
            for (String key : keys) sorted.add(key, sort(object.get(key), comparator));
            return sorted;
        } else {
            return element;
        }
    }

}
