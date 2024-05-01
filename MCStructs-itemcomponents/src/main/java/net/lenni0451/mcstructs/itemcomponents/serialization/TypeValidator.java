package net.lenni0451.mcstructs.itemcomponents.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.itemcomponents.exceptions.InvalidTypeException;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.tags.*;
import net.lenni0451.mcstructs.nbt.utils.NbtCodecUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TypeValidator {

    public static boolean requireBoolean(final JsonElement element) {
        if (element == null) throw InvalidTypeException.of(null, JsonPrimitive.class);
        if (!element.isJsonPrimitive()) throw InvalidTypeException.of(element, JsonPrimitive.class);
        if (!element.getAsJsonPrimitive().isBoolean()) throw InvalidTypeException.of(element, "boolean");
        return element.getAsJsonPrimitive().getAsBoolean();
    }

    public static boolean requireBoolean(final INbtTag tag) {
        if (tag == null) throw InvalidTypeException.of(null, ByteTag.class);
        if (!tag.isByteTag()) throw InvalidTypeException.of(tag, ByteTag.class);
        return tag.asByteTag().booleanValue();
    }

    public static int requireInt(final JsonElement element) {
        if (element == null) throw InvalidTypeException.of(null, JsonPrimitive.class);
        if (!element.isJsonPrimitive()) throw InvalidTypeException.of(element, JsonPrimitive.class);
        if (!element.getAsJsonPrimitive().isNumber()) throw InvalidTypeException.of(element, "int");
        return element.getAsJsonPrimitive().getAsInt();
    }

    public static int requireInt(final INbtTag tag) {
        if (tag == null) throw InvalidTypeException.of(null, IntTag.class);
        if (!tag.isNumberTag()) throw InvalidTypeException.of(tag, IntTag.class);
        return tag.asNumberTag().intValue();
    }

    public static long requireLong(final JsonElement element) {
        if (element == null) throw InvalidTypeException.of(null, JsonPrimitive.class);
        if (!element.isJsonPrimitive()) throw InvalidTypeException.of(element, JsonPrimitive.class);
        if (!element.getAsJsonPrimitive().isNumber()) throw InvalidTypeException.of(element, "long");
        return element.getAsJsonPrimitive().getAsLong();
    }

    public static long requireLong(final INbtTag tag) {
        if (tag == null) throw InvalidTypeException.of(null, LongTag.class);
        if (!tag.isNumberTag()) throw InvalidTypeException.of(tag, LongTag.class);
        return tag.asNumberTag().longValue();
    }

    public static float requireFloat(final JsonElement element) {
        if (element == null) throw InvalidTypeException.of(null, JsonPrimitive.class);
        if (!element.isJsonPrimitive()) throw InvalidTypeException.of(element, JsonPrimitive.class);
        if (!element.getAsJsonPrimitive().isNumber()) throw InvalidTypeException.of(element, "float");
        return element.getAsJsonPrimitive().getAsFloat();
    }

    public static float requireFloat(final INbtTag tag) {
        if (tag == null) throw InvalidTypeException.of(null, FloatTag.class);
        if (!tag.isNumberTag()) throw InvalidTypeException.of(tag, FloatTag.class);
        return tag.asNumberTag().floatValue();
    }

    public static double requireDouble(final JsonElement element) {
        if (element == null) throw InvalidTypeException.of(null, JsonPrimitive.class);
        if (!element.isJsonPrimitive()) throw InvalidTypeException.of(element, JsonPrimitive.class);
        if (!element.getAsJsonPrimitive().isNumber()) throw InvalidTypeException.of(element, "double");
        return element.getAsJsonPrimitive().getAsDouble();
    }

    public static double requireDouble(final INbtTag tag) {
        if (tag == null) throw InvalidTypeException.of(null, DoubleTag.class);
        if (!tag.isNumberTag()) throw InvalidTypeException.of(tag, DoubleTag.class);
        return tag.asNumberTag().doubleValue();
    }

    public static String requireString(final JsonElement element) {
        if (element == null) throw InvalidTypeException.of(null, JsonPrimitive.class);
        if (!element.isJsonPrimitive()) throw InvalidTypeException.of(element, JsonPrimitive.class);
        if (!element.getAsJsonPrimitive().isString()) throw InvalidTypeException.of(element, "String");
        return element.getAsJsonPrimitive().getAsString();
    }

    public static String requireString(final INbtTag tag) {
        if (tag == null) throw InvalidTypeException.of(null, StringTag.class);
        if (!tag.isStringTag()) throw InvalidTypeException.of(tag, StringTag.class);
        return tag.asStringTag().getValue();
    }

    public static JsonArray requireJsonArray(final JsonElement element) {
        if (element == null) throw InvalidTypeException.of(null, JsonArray.class);
        if (!element.isJsonArray()) throw InvalidTypeException.of(element, JsonArray.class);
        return element.getAsJsonArray();
    }

    public static <T> List<T> requireList(final JsonElement element, final Function<JsonElement, T> mapper) {
        JsonArray array = requireJsonArray(element);
        List<T> list = new ArrayList<>();
        for (JsonElement e : array) list.add(mapper.apply(e));
        return list;
    }

    public static List<INbtTag> requireListTag(final INbtTag tag) {
        if (tag == null) throw InvalidTypeException.of(null, ListTag.class);
        if (!tag.isListTag()) throw InvalidTypeException.of(tag, ListTag.class);
        return NbtCodecUtils.unwrapMarkers(tag.asListTag());
    }

    public static <T> List<T> requireList(final INbtTag tag, final Function<INbtTag, T> mapper) {
        if (tag == null) throw InvalidTypeException.of(null, ListTag.class, ByteArrayTag.class, IntArrayTag.class, LongArrayTag.class);
        List<INbtTag> list;
        if (tag.isListTag()) list = requireListTag(tag);
        else if (tag.isArrayTag()) list = requireListTag(tag.asArrayTag().toListTag());
        else throw InvalidTypeException.of(tag, ListTag.class, ByteArrayTag.class, IntArrayTag.class, LongArrayTag.class);

        List<T> result = new ArrayList<>();
        for (INbtTag t : list) result.add(mapper.apply(t));
        return result;
    }

    public static JsonObject requireJsonObject(final JsonElement element) {
        if (element == null) throw InvalidTypeException.of(null, JsonObject.class);
        if (!element.isJsonObject()) throw InvalidTypeException.of(element, JsonObject.class);
        return element.getAsJsonObject();
    }

    public static CompoundTag requireCompoundTag(final INbtTag tag) {
        if (tag == null) throw InvalidTypeException.of(null, CompoundTag.class);
        if (!tag.isCompoundTag()) throw InvalidTypeException.of(tag, CompoundTag.class);
        return tag.asCompoundTag();
    }

}
