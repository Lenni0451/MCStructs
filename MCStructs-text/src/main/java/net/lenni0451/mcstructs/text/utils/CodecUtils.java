package net.lenni0451.mcstructs.text.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Basic util methods for text component codecs.
 */
@ParametersAreNonnullByDefault
public class CodecUtils {

    /**
     * Check if the given element is a string.
     *
     * @param element The element to check
     * @return If the element is a string
     */
    public static boolean isString(@Nullable final JsonElement element) {
        return element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isString();
    }

    /**
     * Check if the given element is a number.
     *
     * @param element The element to check
     * @return If the element is a number
     */
    public static boolean isNumber(@Nullable final JsonElement element) {
        return element != null && element.isJsonPrimitive() && (element.getAsJsonPrimitive().isNumber() || element.getAsJsonPrimitive().isBoolean());
    }

    /**
     * Check if the given element is an array.
     *
     * @param element The element to check
     * @return If the element is an array
     */
    public static boolean isObject(@Nullable final JsonElement element) {
        return element != null && element.isJsonObject();
    }


    /**
     * Check if the given json object contains a boolean.
     *
     * @param obj  The object to check
     * @param name The name of the boolean
     * @return If the object contains the boolean
     */
    public static boolean containsString(final JsonObject obj, final String name) {
        return obj.has(name) && isString(obj.get(name));
    }

    /**
     * Check if the given json object contains a json array.
     *
     * @param obj  The object to check
     * @param name The name of the array
     * @return If the object contains the array
     */
    public static boolean containsArray(final JsonObject obj, final String name) {
        return obj.has(name) && obj.get(name).isJsonArray();
    }

    /**
     * Check if the given json object contains a json object.
     *
     * @param obj  The object to check
     * @param name The name of the object
     * @return If the object contains the object
     */
    public static boolean containsObject(final JsonObject obj, final String name) {
        return obj.has(name) && isObject(obj.get(name));
    }


    /**
     * Get an optional boolean or null if not present.
     *
     * @param tag  The tag to get the boolean from
     * @param name The name of the boolean
     * @return The boolean or null if not present
     */
    @Nullable
    public static Boolean optionalBoolean(final CompoundTag tag, final String name) {
        if (!tag.contains(name)) return null;
        return requiredBoolean(tag, name);
    }

    /**
     * Get an optional boolean or null if not present.
     *
     * @param obj  The object to get the boolean from
     * @param name The name of the boolean
     * @return The boolean or null if not present
     */
    @Nullable
    public static Boolean optionalBoolean(final JsonObject obj, final String name) {
        if (!obj.has(name)) return null;
        return requiredBoolean(obj, name);
    }

    /**
     * Get an optional int or null if not present.
     *
     * @param tag  The tag to get the int from
     * @param name The name of the int
     * @return The int or null if not present
     */
    @Nullable
    public static Integer optionalInt(final CompoundTag tag, final String name) {
        if (!tag.contains(name)) return null;
        return requiredInt(tag, name);
    }

    /**
     * Get an optional int or null if not present.
     *
     * @param obj  The object to get the int from
     * @param name The name of the int
     * @return The int or null if not present
     */
    @Nullable
    public static Integer optionalInt(final JsonObject obj, final String name) {
        if (!obj.has(name)) return null;
        return requiredInt(obj, name);
    }

    /**
     * Get an optional string or null if not present.
     *
     * @param tag  The tag to get the string from
     * @param name The name of the string
     * @return The string or null if not present
     */
    @Nullable
    public static String optionalString(final CompoundTag tag, final String name) {
        if (!tag.contains(name)) return null;
        return requiredString(tag, name);
    }

    /**
     * Get an optional string or null if not present.
     *
     * @param obj  The object to get the string from
     * @param name The name of the string
     * @return The string or null if not present
     */
    @Nullable
    public static String optionalString(final JsonObject obj, final String name) {
        if (!obj.has(name)) return null;
        return requiredString(obj, name);
    }


    /**
     * Get a required boolean or throw an exception if not present.
     *
     * @param tag  The tag to get the boolean from
     * @param name The name of the boolean
     * @return The boolean
     * @throws IllegalArgumentException If the boolean is not present
     */
    public static boolean requiredBoolean(final CompoundTag tag, final String name) {
        if (!tag.contains(name, NbtType.BYTE)) throw new IllegalArgumentException("Expected byte tag for '" + name + "' tag");
        return tag.getBoolean(name);
    }

    /**
     * Get a required boolean or throw an exception if not present.
     *
     * @param obj  The object to get the boolean from
     * @param name The name of the boolean
     * @return The boolean
     */
    public static boolean requiredBoolean(final JsonObject obj, final String name) {
        if (!obj.has(name)) throw new IllegalArgumentException("Missing boolean for '" + name + "' tag");
        JsonElement element = obj.get(name);
        if (element.isJsonPrimitive()) throw new IllegalArgumentException("Expected boolean for '" + name + "' tag");

        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (primitive.isBoolean()) return primitive.getAsBoolean();
        else if (primitive.isNumber()) return primitive.getAsInt() != 0;
        else throw new IllegalArgumentException("Expected boolean for '" + name + "' tag");
    }

    /**
     * Get a required int or throw an exception if not present.
     *
     * @param tag  The tag to get the int from
     * @param name The name of the int
     * @return The int
     */
    public static int requiredInt(final CompoundTag tag, final String name) {
        if (!tag.contains(name, NbtType.INT)) throw new IllegalArgumentException("Expected int tag for '" + name + "' tag");
        return tag.getInt(name);
    }

    /**
     * Get a required int or throw an exception if not present.
     *
     * @param obj  The object to get the int from
     * @param name The name of the int
     * @return The int
     */
    public static int requiredInt(final JsonObject obj, final String name) {
        if (!obj.has(name)) throw new IllegalArgumentException("Missing int for '" + name + "' tag");
        JsonElement element = obj.get(name);
        if (!element.isJsonPrimitive()) throw new IllegalArgumentException("Expected int for '" + name + "' tag");

        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (primitive.isNumber()) return primitive.getAsInt();
        else if (primitive.isBoolean()) return primitive.getAsBoolean() ? 1 : 0;
        else throw new IllegalArgumentException("Expected int for '" + name + "' tag");
    }

    /**
     * Get a required string or throw an exception if not present.
     *
     * @param tag  The tag to get the string from
     * @param name The name of the string
     * @return The string
     */
    public static String requiredString(final CompoundTag tag, final String name) {
        if (!tag.contains(name, NbtType.STRING)) throw new IllegalArgumentException("Expected string tag for '" + name + "' tag");
        return tag.getString(name);
    }

    /**
     * Get a required string or throw an exception if not present.
     *
     * @param obj  The object to get the string from
     * @param name The name of the string
     * @return The string
     */
    public static String requiredString(final JsonObject obj, final String name) {
        if (!obj.has(name)) throw new IllegalArgumentException("Missing string for '" + name + "' tag");
        JsonElement element = obj.get(name);
        if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) throw new IllegalArgumentException("Expected string for '" + name + "' tag");
        return element.getAsString();
    }

    /**
     * Get a required compound tag or throw an exception if not present.
     *
     * @param tag  The tag to get the compound from
     * @param name The name of the compound
     * @return The compound
     */
    public static CompoundTag requiredCompound(final CompoundTag tag, final String name) {
        if (!tag.contains(name, NbtType.COMPOUND)) throw new IllegalArgumentException("Expected compound tag for '" + name + "' tag");
        return tag.getCompound(name);
    }

    /**
     * Get a required json object or throw an exception if not present.
     *
     * @param obj  The object to get the object from
     * @param name The name of the object
     * @return The object
     */
    public static JsonObject requiredObject(final JsonObject obj, final String name) {
        if (!obj.has(name)) throw new IllegalArgumentException("Missing object for '" + name + "' tag");
        JsonElement element = obj.get(name);
        if (!element.isJsonObject()) throw new IllegalArgumentException("Expected object for '" + name + "' tag");
        return element.getAsJsonObject();
    }

}
