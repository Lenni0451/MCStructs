package net.lenni0451.mcstructs.text.utils;

import com.google.gson.*;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.*;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A {@literal json <-> nbt} converter which is based on Minecraft's Codecs from 1.20.5.
 */
public class JsonNbtConverter {

    /**
     * Convert a {@link NbtTag} to a {@link JsonElement}.
     *
     * @param tag The tag to convert
     * @return The converted json element
     */
    @Nullable
    public static JsonElement toJson(@Nullable final NbtTag tag) {
        if (tag == null) return null;
        switch (tag.getNbtType()) {
            case END:
                return null;
            case BYTE:
            case SHORT:
            case INT:
            case LONG:
            case FLOAT:
            case DOUBLE:
                return new JsonPrimitive(tag.asNumberTag().numberValue());
            case BYTE_ARRAY:
                JsonArray byteArray = new JsonArray();
                for (byte b : tag.asByteArrayTag().getValue()) byteArray.add(b);
                return byteArray;
            case STRING:
                return new JsonPrimitive(tag.asStringTag().getValue());
            case LIST:
                JsonArray list = new JsonArray();
                ListTag<NbtTag> listTag = tag.asListTag();
                for (NbtTag tagInList : listTag.getValue()) {
                    if (NbtType.COMPOUND.equals(listTag.getType())) {
                        CompoundTag compound = tagInList.asCompoundTag();
                        if (compound.size() == 1) {
                            NbtTag wrappedTag = compound.get("");
                            if (wrappedTag != null) tagInList = wrappedTag;
                        }
                    }
                    list.add(toJson(tagInList));
                }
                return list;
            case COMPOUND:
                JsonObject compound = new JsonObject();
                for (Map.Entry<String, NbtTag> entry : tag.asCompoundTag().getValue().entrySet()) compound.add(entry.getKey(), toJson(entry.getValue()));
                return compound;
            case INT_ARRAY:
                JsonArray intArray = new JsonArray();
                for (int i : tag.asIntArrayTag().getValue()) intArray.add(i);
                return intArray;
            case LONG_ARRAY:
                JsonArray longArray = new JsonArray();
                for (long l : tag.asLongArrayTag().getValue()) longArray.add(l);
                return longArray;
            default:
                throw new IllegalArgumentException("Unknown Nbt type: " + tag.getNbtType());
        }
    }

    /**
     * Convert a {@link JsonElement} to a {@link NbtTag}.
     *
     * @param element The element to convert
     * @return The converted nbt tag
     */
    @Nullable
    public static NbtTag toNbt(@Nullable final JsonElement element) {
        if (element == null) return null;
        if (element instanceof JsonObject) {
            JsonObject object = element.getAsJsonObject();
            CompoundTag compound = new CompoundTag();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) compound.add(entry.getKey(), toNbt(entry.getValue()));
            return compound;
        } else if (element instanceof JsonArray) {
            JsonArray array = element.getAsJsonArray();
            List<NbtTag> nbtTags = new ArrayList<>();
            NbtType listType = null;
            for (JsonElement arrayElement : array) {
                NbtTag tag = toNbt(arrayElement);
                nbtTags.add(tag);
                listType = getListType(listType, tag);
            }
            if (listType == null) {
                return new ListTag<>();
            } else if (listType == NbtType.END) { //Mixed list
                ListTag<CompoundTag> list = new ListTag<>();
                for (NbtTag tag : nbtTags) {
                    if (tag instanceof CompoundTag) list.add(tag.asCompoundTag());
                    else list.add(new CompoundTag().add("", tag));
                }
                return list;
            } else if (listType == NbtType.BYTE) {
                byte[] bytes = new byte[nbtTags.size()];
                for (int i = 0; i < nbtTags.size(); i++) bytes[i] = nbtTags.get(i).asByteTag().byteValue();
                return new ByteArrayTag(bytes);
            } else if (listType == NbtType.INT) {
                int[] ints = new int[nbtTags.size()];
                for (int i = 0; i < nbtTags.size(); i++) ints[i] = nbtTags.get(i).asIntTag().intValue();
                return new IntArrayTag(ints);
            } else if (listType == NbtType.LONG) {
                long[] longs = new long[nbtTags.size()];
                for (int i = 0; i < nbtTags.size(); i++) longs[i] = nbtTags.get(i).asIntTag().intValue();
                return new LongArrayTag(longs);
            } else {
                return new ListTag<>(nbtTags);
            }
        } else if (element instanceof JsonNull) {
            return null;
        } else if (element instanceof JsonPrimitive) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                return new StringTag(primitive.getAsString());
            } else if (primitive.isBoolean()) {
                return new ByteTag(primitive.getAsBoolean());
            } else {
                BigDecimal number = primitive.getAsBigDecimal();
                try {
                    long l = number.longValueExact();
                    if ((byte) l == l) return new ByteTag((byte) l);
                    else if ((short) l == l) return new ShortTag((short) l);
                    else if ((int) l == l) return new IntTag((int) l);
                    else return new LongTag(l);
                } catch (ArithmeticException e) {
                    double d = number.doubleValue();
                    if ((float) d == d) return new FloatTag((float) d);
                    else return new DoubleTag(d);
                }
            }
        } else {
            throw new IllegalArgumentException("Unknown JsonElement type: " + element.getClass().getName());
        }
    }

    private static NbtType getListType(final NbtType current, final NbtTag tag) {
        if (current == null) return tag.getNbtType();
        if (current != tag.getNbtType()) return NbtType.END; //Placeholder for mixed lists
        return current;
    }

}
