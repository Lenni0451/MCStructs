package net.lenni0451.mcstructs.converter.impl.v1_20_3;

import net.lenni0451.mcstructs.converter.ConverterResult;
import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.nbt.INbtArray;
import net.lenni0451.mcstructs.nbt.INbtNumber;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.*;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;

import javax.annotation.Nullable;
import java.util.*;

public class NbtConverter_v1_20_3 implements DataConverter<INbtTag> {

    public static final NbtConverter_v1_20_3 INSTANCE = new NbtConverter_v1_20_3();
    private static final String MARKER_KEY = "";

    @Override
    public <N> N convertTo(DataConverter<N> to, @Nullable INbtTag element) {
        if (to == this) return (N) element;
        if (element == null) return null;
        switch (element.getNbtType()) {
            case END:
                return null;
            case BYTE:
                return to.createByte(element.asByteTag().getValue());
            case SHORT:
                return to.createShort(element.asShortTag().getValue());
            case INT:
                return to.createInt(element.asIntTag().getValue());
            case LONG:
                return to.createLong(element.asLongTag().getValue());
            case FLOAT:
                return to.createFloat(element.asFloatTag().getValue());
            case DOUBLE:
                return to.createDouble(element.asDoubleTag().getValue());
            case BYTE_ARRAY:
                return to.createByteArray(element.asByteArrayTag().getValue());
            case STRING:
                return to.createString(element.asStringTag().getValue());
            case LIST:
                return this.convertList(to, element);
            case COMPOUND:
                return this.convertMap(to, element);
            case INT_ARRAY:
                return to.createIntArray(element.asIntArrayTag().getValue());
            case LONG_ARRAY:
                return to.createLongArray(element.asLongArrayTag().getValue());
            default:
                throw new IllegalArgumentException("Unknown Nbt type: " + element.getNbtType());
        }
    }

    @Override
    public INbtTag createBoolean(boolean value) {
        return new ByteTag(value);
    }

    @Override
    public ConverterResult<Boolean> asBoolean(INbtTag element) {
        return this.asNumber(element).map(number -> number.byteValue() != 0);
    }

    @Override
    public INbtTag createNumber(Number number) {
        return new DoubleTag(number.doubleValue());
    }

    @Override
    public ConverterResult<Number> asNumber(INbtTag element) {
        if (!element.isNumberTag()) return ConverterResult.unexpected(element, INbtNumber.class);
        return ConverterResult.success(element.asNumberTag().numberValue());
    }

    @Override
    public INbtTag createByte(byte value) {
        return new ByteTag(value);
    }

    @Override
    public INbtTag createShort(short value) {
        return new ShortTag(value);
    }

    @Override
    public INbtTag createInt(int value) {
        return new IntTag(value);
    }

    @Override
    public INbtTag createLong(long value) {
        return new LongTag(value);
    }

    @Override
    public INbtTag createFloat(float value) {
        return new FloatTag(value);
    }

    @Override
    public INbtTag createDouble(double value) {
        return new DoubleTag(value);
    }

    @Override
    public INbtTag createString(String value) {
        return new StringTag(value);
    }

    @Override
    public ConverterResult<String> asString(INbtTag element) {
        if (!element.isStringTag()) return ConverterResult.unexpected(element, StringTag.class);
        return ConverterResult.success(element.asStringTag().getValue());
    }

    @Override
    public ConverterResult<INbtTag> mergeList(@Nullable INbtTag list, List<INbtTag> values) {
        if (list == null) list = new ListTag<>();
        if (list.isByteArrayTag() && values.stream().allMatch(INbtTag::isByteArrayTag)) {
            ByteArrayTag tag = new ByteArrayTag();
            byte[] bytes = Arrays.copyOf(tag.getValue(), tag.getLength() + values.size());
            for (int i = 0; i < values.size(); i++) bytes[tag.getLength() + i] = values.get(i).asNumberTag().byteValue();
            return ConverterResult.success(new ByteArrayTag(bytes));
        } else if (list.isIntArrayTag() && values.stream().allMatch(INbtTag::isIntArrayTag)) {
            IntArrayTag tag = new IntArrayTag();
            int[] ints = Arrays.copyOf(tag.getValue(), tag.getLength() + values.size());
            for (int i = 0; i < values.size(); i++) ints[tag.getLength() + i] = values.get(i).asNumberTag().intValue();
            return ConverterResult.success(new IntArrayTag(ints));
        } else if (list.isLongArrayTag() && values.stream().allMatch(INbtTag::isLongArrayTag)) {
            LongArrayTag tag = new LongArrayTag();
            long[] longs = Arrays.copyOf(tag.getValue(), tag.getLength() + values.size());
            for (int i = 0; i < values.size(); i++) longs[tag.getLength() + i] = values.get(i).asNumberTag().longValue();
            return ConverterResult.success(new LongArrayTag(longs));
        }

        ConverterResult<List<INbtTag>> listResult = this.asList(list);
        if (listResult.isError()) return listResult.mapError();
        List<INbtTag> elements = new ArrayList<>(listResult.get());
        elements.addAll(values);

        NbtType listType = null;
        for (INbtTag value : elements) {
            NbtType valueType = value.getNbtType();
            if (listType == null) listType = valueType;
            else if (!valueType.equals(listType)) listType = NbtType.END; //Placeholder for mixed lists
        }

        if (listType == null) {
            return ConverterResult.success(new ListTag<>());
        } else if (NbtType.BYTE.equals(listType)) {
            byte[] bytes = new byte[elements.size()];
            for (int i = 0; i < elements.size(); i++) bytes[i] = elements.get(i).asByteTag().byteValue();
            return ConverterResult.success(new ByteArrayTag(bytes));
        } else if (NbtType.INT.equals(listType)) {
            int[] ints = new int[elements.size()];
            for (int i = 0; i < elements.size(); i++) ints[i] = elements.get(i).asIntTag().intValue();
            return ConverterResult.success(new IntArrayTag(ints));
        } else if (NbtType.LONG.equals(listType)) {
            long[] longs = new long[elements.size()];
            for (int i = 0; i < elements.size(); i++) longs[i] = elements.get(i).asLongTag().longValue();
            return ConverterResult.success(new LongArrayTag(longs));
        } else if (NbtType.END.equals(listType)) {
            ListTag<CompoundTag> listTag = new ListTag<>();
            for (INbtTag tag : elements) {
                if (tag.isCompoundTag()) listTag.add(tag.asCompoundTag());
                else listTag.add(new CompoundTag().add(MARKER_KEY, tag));
            }
            return ConverterResult.success(listTag);
        } else {
            return ConverterResult.success(new ListTag<>(elements));
        }
    }

    @Override
    public ConverterResult<List<INbtTag>> asList(INbtTag element) {
        if (element.isListTag()) {
            ListTag<?> listTag = element.asListTag();
            List<INbtTag> list = new ArrayList<>();
            if (NbtType.COMPOUND.equals(listTag.getType())) {
                for (INbtTag tag : listTag) {
                    CompoundTag compound = tag.asCompoundTag();
                    INbtTag wrapped = compound.size() == 1 ? compound.get(MARKER_KEY) : null;
                    if (wrapped != null) list.add(wrapped);
                    else list.add(tag);
                }
            } else {
                list.addAll(listTag.getValue());
            }
            return ConverterResult.success(list);
        } else if (element.isArrayTag()) {
            return ConverterResult.success((List<INbtTag>) element.asArrayTag().toListTag().getValue());
        } else {
            return ConverterResult.unexpected(element, ListTag.class, INbtArray.class);
        }
    }

    @Override
    public INbtTag createMap(Map<INbtTag, INbtTag> values) {
        CompoundTag compound = new CompoundTag();
        for (Map.Entry<INbtTag, INbtTag> entry : values.entrySet()) {
            compound.add(SNbtSerializer.V1_14.trySerialize(entry.getKey()), entry.getValue());
        }
        return compound;
    }

    @Override
    public ConverterResult<INbtTag> mergeMap(@Nullable INbtTag map, Map<INbtTag, INbtTag> values) {
        if (map == null) map = new CompoundTag();
        if (!map.isCompoundTag()) return ConverterResult.unexpected(map, CompoundTag.class);
        CompoundTag compound = map.asCompoundTag();
        for (Map.Entry<INbtTag, INbtTag> entry : values.entrySet()) {
            if (entry.getKey().isStringTag()) {
                compound.add(entry.getKey().asStringTag().getValue(), entry.getValue());
            } else {
                return ConverterResult.error("Map key is not a string tag");
            }
        }
        return ConverterResult.success(compound);
    }

    @Override
    public ConverterResult<Map<INbtTag, INbtTag>> asMap(INbtTag element) {
        if (!element.isCompoundTag()) return ConverterResult.unexpected(element, CompoundTag.class);
        CompoundTag compound = element.asCompoundTag();
        Map<INbtTag, INbtTag> map = new HashMap<>();
        for (Map.Entry<String, INbtTag> entry : compound) map.put(this.createString(entry.getKey()), entry.getValue());
        return ConverterResult.success(map);
    }

    @Override
    public ConverterResult<Map<String, INbtTag>> asStringTypeMap(INbtTag element) {
        if (!element.isCompoundTag()) return ConverterResult.unexpected(element, CompoundTag.class);
        CompoundTag compound = element.asCompoundTag();
        Map<String, INbtTag> map = new HashMap<>();
        for (Map.Entry<String, INbtTag> entry : compound) map.put(entry.getKey(), entry.getValue());
        return ConverterResult.success(map);
    }

    @Override
    public boolean put(INbtTag map, String key, INbtTag value) {
        if (!map.isCompoundTag()) return false;
        map.asCompoundTag().add(key, value);
        return true;
    }

    @Override
    public INbtTag createByteArray(byte[] value) {
        return new ByteArrayTag(value);
    }

    @Override
    public ConverterResult<byte[]> asByteArray(INbtTag element) {
        if (element.isByteArrayTag()) {
            return ConverterResult.success(element.asByteArrayTag().getValue());
        } else if (element.isIntArrayTag()) {
            IntArrayTag intArrayTag = element.asIntArrayTag();
            byte[] bytes = new byte[intArrayTag.getLength()];
            for (int i = 0; i < intArrayTag.getLength(); i++) bytes[i] = (byte) intArrayTag.get(i);
            return ConverterResult.success(bytes);
        } else if (element.isLongArrayTag()) {
            LongArrayTag longArrayTag = element.asLongArrayTag();
            byte[] bytes = new byte[longArrayTag.getLength()];
            for (int i = 0; i < longArrayTag.getLength(); i++) bytes[i] = (byte) longArrayTag.get(i);
            return ConverterResult.success(bytes);
        } else if (element.isListTag()) {
            List<INbtTag> list = this.asList(element).get();
            if (list.stream().allMatch(INbtTag::isNumberTag)) {
                byte[] bytes = new byte[list.size()];
                for (int i = 0; i < list.size(); i++) bytes[i] = list.get(i).asNumberTag().byteValue();
                return ConverterResult.success(bytes);
            } else {
                return ConverterResult.error("List tag does not contain only number tags");
            }
        } else {
            return ConverterResult.unexpected(element, ByteArrayTag.class, IntArrayTag.class, LongArrayTag.class, ListTag.class);
        }
    }

    @Override
    public INbtTag createIntArray(int[] value) {
        return new IntArrayTag(value);
    }

    @Override
    public ConverterResult<int[]> asIntArray(INbtTag element) {
        if (element.isByteArrayTag()) {
            ByteArrayTag byteArrayTag = element.asByteArrayTag();
            int[] ints = new int[byteArrayTag.getLength()];
            for (int i = 0; i < byteArrayTag.getLength(); i++) ints[i] = byteArrayTag.get(i);
            return ConverterResult.success(ints);
        } else if (element.isIntArrayTag()) {
            return ConverterResult.success(element.asIntArrayTag().getValue());
        } else if (element.isLongArrayTag()) {
            LongArrayTag longArrayTag = element.asLongArrayTag();
            int[] ints = new int[longArrayTag.getLength()];
            for (int i = 0; i < longArrayTag.getLength(); i++) ints[i] = (int) longArrayTag.get(i);
            return ConverterResult.success(ints);
        } else if (element.isListTag()) {
            List<INbtTag> list = this.asList(element).get();
            if (list.stream().allMatch(INbtTag::isNumberTag)) {
                int[] ints = new int[list.size()];
                for (int i = 0; i < list.size(); i++) ints[i] = list.get(i).asNumberTag().intValue();
                return ConverterResult.success(ints);
            } else {
                return ConverterResult.error("List tag does not contain only number tags");
            }
        } else {
            return ConverterResult.unexpected(element, ByteArrayTag.class, IntArrayTag.class, LongArrayTag.class, ListTag.class);
        }
    }

    @Override
    public INbtTag createLongArray(long[] value) {
        return new LongArrayTag(value);
    }

    @Override
    public ConverterResult<long[]> asLongArray(INbtTag element) {
        if (element.isByteArrayTag()) {
            ByteArrayTag byteArrayTag = element.asByteArrayTag();
            long[] longs = new long[byteArrayTag.getLength()];
            for (int i = 0; i < byteArrayTag.getLength(); i++) longs[i] = byteArrayTag.get(i);
            return ConverterResult.success(longs);
        } else if (element.isIntArrayTag()) {
            IntArrayTag intArrayTag = element.asIntArrayTag();
            long[] longs = new long[intArrayTag.getLength()];
            for (int i = 0; i < intArrayTag.getLength(); i++) longs[i] = intArrayTag.get(i);
            return ConverterResult.success(longs);
        } else if (element.isLongArrayTag()) {
            return ConverterResult.success(element.asLongArrayTag().getValue());
        } else if (element.isListTag()) {
            List<INbtTag> list = this.asList(element).get();
            if (list.stream().allMatch(INbtTag::isNumberTag)) {
                long[] longs = new long[list.size()];
                for (int i = 0; i < list.size(); i++) longs[i] = list.get(i).asNumberTag().longValue();
                return ConverterResult.success(longs);
            } else {
                return ConverterResult.error("List tag does not contain only number tags");
            }
        } else {
            return ConverterResult.unexpected(element, ByteArrayTag.class, IntArrayTag.class, LongArrayTag.class, ListTag.class);
        }
    }

}
