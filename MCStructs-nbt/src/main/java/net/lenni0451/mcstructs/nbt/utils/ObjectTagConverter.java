package net.lenni0451.mcstructs.nbt.utils;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.exceptions.UnknownTagTypeException;
import net.lenni0451.mcstructs.nbt.tags.*;

import java.util.List;
import java.util.Map;

public class ObjectTagConverter {

    public static ListTag<?> convertList(final List<?> list) {
        ListTag<NbtTag> tag = new ListTag<>();
        for (Object o : list) {
            tag.add(convert(o));
        }
        return tag;
    }

    public static CompoundTag convertMap(final Map<?, ?> map) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            tag.add(entry.getKey().toString(), convert(entry.getValue()));
        }
        return tag;
    }

    public static NbtTag convert(final Object o) {
        if (o == null) return null;
        if (o instanceof NbtTag) return (NbtTag) o;
        if (o instanceof Byte) return new ByteTag((Byte) o);
        else if (o instanceof Short) return new ShortTag((Short) o);
        else if (o instanceof Integer) return new IntTag((Integer) o);
        else if (o instanceof Long) return new LongTag((Long) o);
        else if (o instanceof Float) return new FloatTag((Float) o);
        else if (o instanceof Double) return new DoubleTag((Double) o);
        else if (o instanceof byte[]) return new ByteArrayTag((byte[]) o);
        else if (o instanceof String) return new StringTag((String) o);
        else if (o instanceof List) return convertList((List<?>) o);
        else if (o instanceof Map) return convertMap((Map<?, ?>) o);
        else if (o instanceof int[]) return new IntArrayTag((int[]) o);
        else if (o instanceof long[]) return new LongArrayTag((long[]) o);
        throw new UnknownTagTypeException(o.getClass());
    }

}
