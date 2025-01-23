package net.lenni0451.mcstructs.converter.impl.v1_20_3;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.nbt.NbtArray;
import net.lenni0451.mcstructs.nbt.NbtNumber;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.*;
import net.lenni0451.mcstructs.snbt.SNbt;

import javax.annotation.Nullable;
import java.util.*;

public class NbtConverter_v1_20_3 implements DataConverter<NbtTag> {

    public static final NbtConverter_v1_20_3 INSTANCE = new NbtConverter_v1_20_3();
    public static final String MARKER_KEY = "";

    private final SNbt<CompoundTag> sNbt;

    public NbtConverter_v1_20_3() {
        this(SNbt.V1_14);
    }

    protected NbtConverter_v1_20_3(final SNbt<CompoundTag> sNbt) {
        this.sNbt = sNbt;
    }

    @Override
    public <N> N convertTo(DataConverter<N> to, @Nullable NbtTag element) {
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
    public NbtTag createBoolean(boolean value) {
        return new ByteTag(value);
    }

    @Override
    public Result<Boolean> asBoolean(NbtTag element) {
        return this.asNumber(element).map(number -> number.byteValue() != 0);
    }

    @Override
    public NbtTag createNumber(Number number) {
        return new DoubleTag(number.doubleValue());
    }

    @Override
    public Result<Number> asNumber(NbtTag element) {
        if (!element.isNumberTag()) return Result.unexpected(element, NbtNumber.class);
        return Result.success(element.asNumberTag().numberValue());
    }

    @Override
    public NbtTag createByte(byte value) {
        return new ByteTag(value);
    }

    @Override
    public NbtTag createShort(short value) {
        return new ShortTag(value);
    }

    @Override
    public NbtTag createInt(int value) {
        return new IntTag(value);
    }

    @Override
    public NbtTag createLong(long value) {
        return new LongTag(value);
    }

    @Override
    public NbtTag createFloat(float value) {
        return new FloatTag(value);
    }

    @Override
    public NbtTag createDouble(double value) {
        return new DoubleTag(value);
    }

    @Override
    public NbtTag createString(String value) {
        return new StringTag(value);
    }

    @Override
    public Result<String> asString(NbtTag element) {
        if (!element.isStringTag()) return Result.unexpected(element, StringTag.class);
        return Result.success(element.asStringTag().getValue());
    }

    @Override
    public Result<NbtTag> mergeList(@Nullable NbtTag list, List<NbtTag> values) {
        if (list == null) list = new ListTag<>();
        if (list.isByteArrayTag() && values.stream().allMatch(NbtTag::isByteTag)) {
            ByteArrayTag tag = list.asByteArrayTag();
            byte[] bytes = Arrays.copyOf(tag.getValue(), tag.getLength() + values.size());
            for (int i = 0; i < values.size(); i++) bytes[tag.getLength() + i] = values.get(i).asNumberTag().byteValue();
            return Result.success(new ByteArrayTag(bytes));
        } else if (list.isIntArrayTag() && values.stream().allMatch(NbtTag::isIntTag)) {
            IntArrayTag tag = list.asIntArrayTag();
            int[] ints = Arrays.copyOf(tag.getValue(), tag.getLength() + values.size());
            for (int i = 0; i < values.size(); i++) ints[tag.getLength() + i] = values.get(i).asNumberTag().intValue();
            return Result.success(new IntArrayTag(ints));
        } else if (list.isLongArrayTag() && values.stream().allMatch(NbtTag::isLongTag)) {
            LongArrayTag tag = list.asLongArrayTag();
            long[] longs = Arrays.copyOf(tag.getValue(), tag.getLength() + values.size());
            for (int i = 0; i < values.size(); i++) longs[tag.getLength() + i] = values.get(i).asNumberTag().longValue();
            return Result.success(new LongArrayTag(longs));
        }

        Result<List<NbtTag>> listResult = this.asList(list);
        if (listResult.isError()) return listResult.mapError();
        List<NbtTag> elements = new ArrayList<>(listResult.get());
        elements.addAll(values);

        NbtType listType = null;
        for (NbtTag value : elements) {
            NbtType valueType = value.getNbtType();
            if (listType == null) listType = valueType;
            else if (!valueType.equals(listType)) listType = NbtType.END; //Placeholder for mixed lists
        }

        if (listType == null) {
            return Result.success(new ListTag<>());
        } else if (NbtType.BYTE.equals(listType)) {
            byte[] bytes = new byte[elements.size()];
            for (int i = 0; i < elements.size(); i++) bytes[i] = elements.get(i).asByteTag().byteValue();
            return Result.success(new ByteArrayTag(bytes));
        } else if (NbtType.INT.equals(listType)) {
            int[] ints = new int[elements.size()];
            for (int i = 0; i < elements.size(); i++) ints[i] = elements.get(i).asIntTag().intValue();
            return Result.success(new IntArrayTag(ints));
        } else if (NbtType.LONG.equals(listType)) {
            long[] longs = new long[elements.size()];
            for (int i = 0; i < elements.size(); i++) longs[i] = elements.get(i).asLongTag().longValue();
            return Result.success(new LongArrayTag(longs));
        } else if (NbtType.END.equals(listType)) {
            ListTag<CompoundTag> listTag = new ListTag<>();
            for (NbtTag tag : elements) {
                boolean isMarker = tag.isCompoundTag() && tag.asCompoundTag().size() == 1 && tag.asCompoundTag().contains(MARKER_KEY);
                if (tag.isCompoundTag() && !isMarker) listTag.add(tag.asCompoundTag());
                else listTag.add(new CompoundTag().add(MARKER_KEY, tag));
            }
            return Result.success(listTag);
        } else {
            return Result.success(new ListTag<>(elements));
        }
    }

    @Override
    public Result<List<NbtTag>> asList(NbtTag element) {
        if (element.isListTag()) {
            ListTag<?> listTag = element.asListTag();
            List<NbtTag> list = new ArrayList<>();
            if (NbtType.COMPOUND.equals(listTag.getType())) {
                for (NbtTag tag : listTag) {
                    CompoundTag compound = tag.asCompoundTag();
                    NbtTag wrapped = compound.size() == 1 ? compound.get(MARKER_KEY) : null;
                    if (wrapped != null) list.add(wrapped);
                    else list.add(tag);
                }
            } else {
                list.addAll(listTag.getValue());
            }
            return Result.success(list);
        } else if (element.isArrayTag()) {
            return Result.success((List<NbtTag>) element.asArrayTag().toListTag().getValue());
        } else {
            return Result.unexpected(element, ListTag.class, NbtArray.class);
        }
    }

    @Override
    public NbtTag createUnsafeMap(Map<NbtTag, NbtTag> values) {
        CompoundTag compound = new CompoundTag();
        for (Map.Entry<NbtTag, NbtTag> entry : values.entrySet()) {
            String key;
            if (entry.getKey().isStringTag()) key = entry.getKey().asStringTag().getValue();
            else key = this.sNbt.trySerialize(entry.getKey());
            compound.add(key, entry.getValue());
        }
        return compound;
    }

    @Override
    public Result<NbtTag> mergeMap(@Nullable NbtTag map, Map<NbtTag, NbtTag> values) {
        if (map == null) map = new CompoundTag();
        if (!map.isCompoundTag()) return Result.unexpected(map, CompoundTag.class);
        CompoundTag compound = map.asCompoundTag();
        for (Map.Entry<NbtTag, NbtTag> entry : values.entrySet()) {
            if (entry.getKey().isStringTag()) {
                compound.add(entry.getKey().asStringTag().getValue(), entry.getValue());
            } else {
                return Result.error("Map key is not a string tag");
            }
        }
        return Result.success(compound);
    }

    @Override
    public Result<Map<NbtTag, NbtTag>> asMap(NbtTag element) {
        if (!element.isCompoundTag()) return Result.unexpected(element, CompoundTag.class);
        CompoundTag compound = element.asCompoundTag();
        Map<NbtTag, NbtTag> map = new HashMap<>();
        for (Map.Entry<String, NbtTag> entry : compound) map.put(this.createString(entry.getKey()), entry.getValue());
        return Result.success(map);
    }

    @Override
    public Result<Map<String, NbtTag>> asStringTypeMap(NbtTag element) {
        if (!element.isCompoundTag()) return Result.unexpected(element, CompoundTag.class);
        CompoundTag compound = element.asCompoundTag();
        Map<String, NbtTag> map = new HashMap<>();
        for (Map.Entry<String, NbtTag> entry : compound) map.put(entry.getKey(), entry.getValue());
        return Result.success(map);
    }

    @Override
    public boolean put(NbtTag map, String key, NbtTag value) {
        if (!map.isCompoundTag()) return false;
        map.asCompoundTag().add(key, value);
        return true;
    }

    @Override
    public NbtTag createByteArray(byte[] value) {
        return new ByteArrayTag(value);
    }

    @Override
    public Result<byte[]> asByteArray(NbtTag element) {
        if (element.isByteArrayTag()) {
            return Result.success(element.asByteArrayTag().getValue());
        } else if (element.isIntArrayTag()) {
            IntArrayTag intArrayTag = element.asIntArrayTag();
            byte[] bytes = new byte[intArrayTag.getLength()];
            for (int i = 0; i < intArrayTag.getLength(); i++) bytes[i] = (byte) intArrayTag.get(i);
            return Result.success(bytes);
        } else if (element.isLongArrayTag()) {
            LongArrayTag longArrayTag = element.asLongArrayTag();
            byte[] bytes = new byte[longArrayTag.getLength()];
            for (int i = 0; i < longArrayTag.getLength(); i++) bytes[i] = (byte) longArrayTag.get(i);
            return Result.success(bytes);
        } else if (element.isListTag()) {
            List<NbtTag> list = this.asList(element).get();
            if (list.stream().allMatch(NbtTag::isNumberTag)) {
                byte[] bytes = new byte[list.size()];
                for (int i = 0; i < list.size(); i++) bytes[i] = list.get(i).asNumberTag().byteValue();
                return Result.success(bytes);
            } else {
                return Result.error("List tag does not contain only number tags");
            }
        } else {
            return Result.unexpected(element, ByteArrayTag.class, IntArrayTag.class, LongArrayTag.class, ListTag.class);
        }
    }

    @Override
    public NbtTag createIntArray(int[] value) {
        return new IntArrayTag(value);
    }

    @Override
    public Result<int[]> asIntArray(NbtTag element) {
        if (element.isByteArrayTag()) {
            ByteArrayTag byteArrayTag = element.asByteArrayTag();
            int[] ints = new int[byteArrayTag.getLength()];
            for (int i = 0; i < byteArrayTag.getLength(); i++) ints[i] = byteArrayTag.get(i);
            return Result.success(ints);
        } else if (element.isIntArrayTag()) {
            return Result.success(element.asIntArrayTag().getValue());
        } else if (element.isLongArrayTag()) {
            LongArrayTag longArrayTag = element.asLongArrayTag();
            int[] ints = new int[longArrayTag.getLength()];
            for (int i = 0; i < longArrayTag.getLength(); i++) ints[i] = (int) longArrayTag.get(i);
            return Result.success(ints);
        } else if (element.isListTag()) {
            List<NbtTag> list = this.asList(element).get();
            if (list.stream().allMatch(NbtTag::isNumberTag)) {
                int[] ints = new int[list.size()];
                for (int i = 0; i < list.size(); i++) ints[i] = list.get(i).asNumberTag().intValue();
                return Result.success(ints);
            } else {
                return Result.error("List tag does not contain only number tags");
            }
        } else {
            return Result.unexpected(element, ByteArrayTag.class, IntArrayTag.class, LongArrayTag.class, ListTag.class);
        }
    }

    @Override
    public NbtTag createLongArray(long[] value) {
        return new LongArrayTag(value);
    }

    @Override
    public Result<long[]> asLongArray(NbtTag element) {
        if (element.isByteArrayTag()) {
            ByteArrayTag byteArrayTag = element.asByteArrayTag();
            long[] longs = new long[byteArrayTag.getLength()];
            for (int i = 0; i < byteArrayTag.getLength(); i++) longs[i] = byteArrayTag.get(i);
            return Result.success(longs);
        } else if (element.isIntArrayTag()) {
            IntArrayTag intArrayTag = element.asIntArrayTag();
            long[] longs = new long[intArrayTag.getLength()];
            for (int i = 0; i < intArrayTag.getLength(); i++) longs[i] = intArrayTag.get(i);
            return Result.success(longs);
        } else if (element.isLongArrayTag()) {
            return Result.success(element.asLongArrayTag().getValue());
        } else if (element.isListTag()) {
            List<NbtTag> list = this.asList(element).get();
            if (list.stream().allMatch(NbtTag::isNumberTag)) {
                long[] longs = new long[list.size()];
                for (int i = 0; i < list.size(); i++) longs[i] = list.get(i).asNumberTag().longValue();
                return Result.success(longs);
            } else {
                return Result.error("List tag does not contain only number tags");
            }
        } else {
            return Result.unexpected(element, ByteArrayTag.class, IntArrayTag.class, LongArrayTag.class, ListTag.class);
        }
    }

}
