package net.lenni0451.mcstructs.nbt;

import net.lenni0451.mcstructs.nbt.tags.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToStringTest {

    @Test
    void byteTag() {
        assertEquals("byte(1)", new ByteTag((byte) 1).toString());
    }

    @Test
    void shortTag() {
        assertEquals("short(1)", new ShortTag((short) 1).toString());
    }

    @Test
    void intTag() {
        assertEquals("int(1)", new IntTag(1).toString());
    }

    @Test
    void longTag() {
        assertEquals("long(1)", new LongTag(1).toString());
    }

    @Test
    void floatTag() {
        assertEquals("float(1.0)", new FloatTag(1.0F).toString());
    }

    @Test
    void doubleTag() {
        assertEquals("double(1.0)", new DoubleTag(1.0).toString());
    }

    @Test
    void byteArrayTag() {
        assertEquals("byte[3](1, 2, 3)", new ByteArrayTag(new byte[]{1, 2, 3}).toString());
    }

    @Test
    void stringTag() {
        assertEquals("String(test)", new StringTag("test").toString());
    }

    @Test
    void listTag() {
        assertEquals("List[2](int(1), int(2))", new ListTag<>(NbtType.INT, Arrays.asList(new IntTag(1), new IntTag(2))).toString());
    }

    @Test
    void compoundTag() {
        assertEquals("Compound[2]{test1=String(1), test2=int(2)}", new CompoundTag(new LinkedHashMap<>()).addString("test1", "1").addInt("test2", 2).toString());
    }

    @Test
    void intArrayTag() {
        assertEquals("int[3](1, 2, 3)", new IntArrayTag(new int[]{1, 2, 3}).toString());
    }

    @Test
    void longArrayTag() {
        assertEquals("long[3](1, 2, 3)", new LongArrayTag(new long[]{1, 2, 3}).toString());
    }

}
