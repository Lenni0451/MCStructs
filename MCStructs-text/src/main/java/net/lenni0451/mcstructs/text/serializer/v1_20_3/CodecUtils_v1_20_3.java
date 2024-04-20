package net.lenni0451.mcstructs.text.serializer.v1_20_3;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.ListTag;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic util methods for text component codecs based on Minecraft 1.20.3.
 */
@ParametersAreNonnullByDefault
public interface CodecUtils_v1_20_3 {

    /**
     * Unwrap all marker tags from the given list.<br>
     * A marker tag is a compound tag with only one entry with an empty key (e.g. {@code {"":123}}).
     *
     * @param list The list to unwrap
     * @return The unwrapped list
     */
    default List<INbtTag> unwrapMarkers(final ListTag<?> list) {
        List<INbtTag> tags = new ArrayList<>(list.getValue());
        if (NbtType.COMPOUND.equals(list.getType())) {
            for (int i = 0; i < tags.size(); i++) {
                CompoundTag compound = (CompoundTag) tags.get(i);
                if (compound.size() == 1) {
                    INbtTag wrapped = compound.get("");
                    if (wrapped != null) tags.set(i, wrapped);
                }
            }
        }
        return tags;
    }

    /**
     * Check if the given element is a string.
     *
     * @param element The element to check
     * @return If the element is a string
     */
    default boolean isString(@Nullable final JsonElement element) {
        return element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isString();
    }

    /**
     * Check if the given element is a number.
     *
     * @param element The element to check
     * @return If the element is a number
     */
    default boolean isNumber(@Nullable final JsonElement element) {
        return element != null && element.isJsonPrimitive() && (element.getAsJsonPrimitive().isNumber() || element.getAsJsonPrimitive().isBoolean());
    }

    /**
     * Check if the given element is an array.
     *
     * @param element The element to check
     * @return If the element is an array
     */
    default boolean isObject(@Nullable final JsonElement element) {
        return element != null && element.isJsonObject();
    }


    /**
     * Check if the given json object contains a boolean.
     *
     * @param obj  The object to check
     * @param name The name of the boolean
     * @return If the object contains the boolean
     */
    default boolean containsString(final JsonObject obj, final String name) {
        return obj.has(name) && this.isString(obj.get(name));
    }

    /**
     * Check if the given json object contains a json array.
     *
     * @param obj  The object to check
     * @param name The name of the array
     * @return If the object contains the array
     */
    default boolean containsArray(final JsonObject obj, final String name) {
        return obj.has(name) && obj.get(name).isJsonArray();
    }

    /**
     * Check if the given json object contains a json object.
     *
     * @param obj  The object to check
     * @param name The name of the object
     * @return If the object contains the object
     */
    default boolean containsObject(final JsonObject obj, final String name) {
        return obj.has(name) && this.isObject(obj.get(name));
    }


    /**
     * Get an optional boolean or null if not present.
     *
     * @param tag  The tag to get the boolean from
     * @param name The name of the boolean
     * @return The boolean or null if not present
     */
    @Nullable
    default Boolean optionalBoolean(final CompoundTag tag, final String name) {
        if (!tag.contains(name)) return null;
        return this.requiredBoolean(tag, name);
    }

    /**
     * Get an optional boolean or null if not present.
     *
     * @param obj  The object to get the boolean from
     * @param name The name of the boolean
     * @return The boolean or null if not present
     */
    @Nullable
    default Boolean optionalBoolean(final JsonObject obj, final String name) {
        if (!obj.has(name)) return null;
        return this.requiredBoolean(obj, name);
    }

    /**
     * Get an optional int or null if not present.
     *
     * @param tag  The tag to get the int from
     * @param name The name of the int
     * @return The int or null if not present
     */
    @Nullable
    default Integer optionalInt(final CompoundTag tag, final String name) {
        if (!tag.contains(name)) return null;
        return this.requiredInt(tag, name);
    }

    /**
     * Get an optional int or null if not present.
     *
     * @param obj  The object to get the int from
     * @param name The name of the int
     * @return The int or null if not present
     */
    @Nullable
    default Integer optionalInt(final JsonObject obj, final String name) {
        if (!obj.has(name)) return null;
        return this.requiredInt(obj, name);
    }

    /**
     * Get an optional string or null if not present.
     *
     * @param tag  The tag to get the string from
     * @param name The name of the string
     * @return The string or null if not present
     */
    @Nullable
    default String optionalString(final CompoundTag tag, final String name) {
        if (!tag.contains(name)) return null;
        return this.requiredString(tag, name);
    }

    /**
     * Get an optional string or null if not present.
     *
     * @param obj  The object to get the string from
     * @param name The name of the string
     * @return The string or null if not present
     */
    @Nullable
    default String optionalString(final JsonObject obj, final String name) {
        if (!obj.has(name)) return null;
        return this.requiredString(obj, name);
    }

    /**
     * Get an optional compound tag or null if not present.
     *
     * @param tag  The tag to get the compound from
     * @param name The name of the compound
     * @return The compound or null if not present
     */
    @Nullable
    default CompoundTag optionalCompound(final CompoundTag tag, final String name) {
        if (!tag.contains(name)) return null;
        return this.requiredCompound(tag, name);
    }

    /**
     * Get an optional json object or null if not present.
     *
     * @param obj  The object to get the object from
     * @param name The name of the object
     * @return The object or null if not present
     */
    @Nullable
    default JsonObject optionalObject(final JsonObject obj, final String name) {
        if (!obj.has(name)) return null;
        return this.requiredObject(obj, name);
    }


    /**
     * Get a required boolean or throw an exception if not present.
     *
     * @param tag  The tag to get the boolean from
     * @param name The name of the boolean
     * @return The boolean
     * @throws IllegalArgumentException If the boolean is not present
     */
    default boolean requiredBoolean(final CompoundTag tag, final String name) {
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
    default boolean requiredBoolean(final JsonObject obj, final String name) {
        if (!obj.has(name)) throw new IllegalArgumentException("Missing boolean for '" + name + "' tag");
        JsonElement element = obj.get(name);
        if (!element.isJsonPrimitive()) throw new IllegalArgumentException("Expected boolean for '" + name + "' tag");

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
    default int requiredInt(final CompoundTag tag, final String name) {
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
    default int requiredInt(final JsonObject obj, final String name) {
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
    default String requiredString(final CompoundTag tag, final String name) {
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
    default String requiredString(final JsonObject obj, final String name) {
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
    default CompoundTag requiredCompound(final CompoundTag tag, final String name) {
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
    default JsonObject requiredObject(final JsonObject obj, final String name) {
        if (!obj.has(name)) throw new IllegalArgumentException("Missing object for '" + name + "' tag");
        JsonElement element = obj.get(name);
        if (!element.isJsonObject()) throw new IllegalArgumentException("Expected object for '" + name + "' tag");
        return element.getAsJsonObject();
    }

}
