package net.lenni0451.mcstructs.nbt;

import net.lenni0451.mcstructs.nbt.exceptions.UnknownTagTypeException;
import net.lenni0451.mcstructs.nbt.tags.*;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class NbtRegistry {

    private static final Unsafe UNSAFE;
    public static final byte END_NBT = 0;
    public static final byte BYTE_NBT = 1;
    public static final byte SHORT_NBT = 2;
    public static final byte INT_NBT = 3;
    public static final byte LONG_NBT = 4;
    public static final byte FLOAT_NBT = 5;
    public static final byte DOUBLE_NBT = 6;
    public static final byte BYTE_ARRAY_NBT = 7;
    public static final byte STRING_NBT = 8;
    public static final byte LIST_NBT = 9;
    public static final byte COMPOUND_NBT = 10;
    public static final byte INT_ARRAY_NBT = 11;
    public static final byte LONG_ARRAY_NBT = 12;

    static {
        try {
            Unsafe unsafe = null;
            for (Field field : Unsafe.class.getDeclaredFields()) {
                if (Unsafe.class.equals(field.getType())) {
                    field.setAccessible(true);
                    unsafe = (Unsafe) field.get(null);
                    break;
                }
            }
            if (unsafe == null) throw new IllegalStateException("Failed to find unsafe field");
            UNSAFE = unsafe;
        } catch (Throwable t) {
            throw new IllegalStateException("Failed to get unsafe instance", t);
        }
    }

    public static int getTagId(final Class<? extends INbtTag> type) {
        if (ByteNbt.class.equals(type)) return BYTE_NBT;
        else if (ShortNbt.class.equals(type)) return SHORT_NBT;
        else if (IntNbt.class.equals(type)) return INT_NBT;
        else if (LongNbt.class.equals(type)) return LONG_NBT;
        else if (FloatNbt.class.equals(type)) return FLOAT_NBT;
        else if (DoubleNbt.class.equals(type)) return DOUBLE_NBT;
        else if (ByteArrayNbt.class.equals(type)) return BYTE_ARRAY_NBT;
        else if (StringNbt.class.equals(type)) return STRING_NBT;
        else if (ListNbt.class.equals(type)) return LIST_NBT;
        else if (CompoundNbt.class.equals(type)) return COMPOUND_NBT;
        else if (IntArrayNbt.class.equals(type)) return INT_ARRAY_NBT;
        else if (LongArrayNbt.class.equals(type)) return LONG_ARRAY_NBT;
        throw new UnknownTagTypeException(type);
    }

    public static Class<? extends INbtTag> getTagClass(final int id) {
        if (id == BYTE_NBT) return ByteNbt.class;
        else if (id == SHORT_NBT) return ShortNbt.class;
        else if (id == INT_NBT) return IntNbt.class;
        else if (id == LONG_NBT) return LongNbt.class;
        else if (id == FLOAT_NBT) return FloatNbt.class;
        else if (id == DOUBLE_NBT) return DoubleNbt.class;
        else if (id == BYTE_ARRAY_NBT) return ByteArrayNbt.class;
        else if (id == STRING_NBT) return StringNbt.class;
        else if (id == LIST_NBT) return ListNbt.class;
        else if (id == COMPOUND_NBT) return CompoundNbt.class;
        else if (id == INT_ARRAY_NBT) return IntArrayNbt.class;
        else if (id == LONG_ARRAY_NBT) return LongArrayNbt.class;
        throw new UnknownTagTypeException(id);
    }

    public static boolean isNumber(final int id) {
        return BYTE_NBT == id || SHORT_NBT == id || INT_NBT == id || LONG_NBT == id || FLOAT_NBT == id || DOUBLE_NBT == id;
    }

    public static <T extends INbtTag> T newInstance(final Class<?> type) {
        try {
            return (T) UNSAFE.allocateInstance(type);
        } catch (InstantiationException e) {
            throw new IllegalStateException("Failed to allocate nbt instance", e);
        }
    }

    public static INbtTag wrap(final Object o) {
        if (o == null) return null;
        if (o instanceof INbtTag) return (INbtTag) o;
        if (o instanceof Byte) return new ByteNbt((Byte) o);
        else if (o instanceof Short) return new ShortNbt((Short) o);
        else if (o instanceof Integer) return new IntNbt((Integer) o);
        else if (o instanceof Long) return new LongNbt((Long) o);
        else if (o instanceof Float) return new FloatNbt((Float) o);
        else if (o instanceof Double) return new DoubleNbt((Double) o);
        else if (o instanceof byte[]) return new ByteArrayNbt((byte[]) o);
        else if (o instanceof String) return new StringNbt((String) o);
        else if (o instanceof int[]) return new IntArrayNbt((int[]) o);
        else if (o instanceof long[]) return new LongArrayNbt((long[]) o);
        throw new UnknownTagTypeException(o.getClass());
    }

}
