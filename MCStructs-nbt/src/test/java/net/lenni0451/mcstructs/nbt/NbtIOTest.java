package net.lenni0451.mcstructs.nbt;

import net.lenni0451.mcstructs.nbt.io.NamedTag;
import net.lenni0451.mcstructs.nbt.io.NbtIO;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.StringTag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.zip.GZIPInputStream;

import static org.junit.jupiter.api.Assertions.*;

class NbtIOTest {

    private static final NbtIO NBT_IO = NbtIO.LATEST;
    private static final CompoundTag COMPOUND_TAG = new CompoundTag();
    private static byte[] uncompressed;
    private static byte[] compressed;
    private static byte[] named;

    @BeforeAll
    static void prepare() throws IOException {
        COMPOUND_TAG.addByteArray("ByteArray", (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5);
        COMPOUND_TAG.addByte("Byte", (byte) 0);
        COMPOUND_TAG.addCompound("Compound", new CompoundTag());
        COMPOUND_TAG.addDouble("Double", 0D);
        COMPOUND_TAG.addFloat("Float", 0F);
        COMPOUND_TAG.addIntArray("IntArray", 0, 1, 2, 3, 4, 5);
        COMPOUND_TAG.addInt("Int", 0);
        COMPOUND_TAG.addList("List");
        COMPOUND_TAG.addLongArray("LongArray", 0L, 1L, 2L, 3L, 4L, 5L);
        COMPOUND_TAG.addLong("Long", 0L);
        COMPOUND_TAG.addShort("Short", (short) 0);
        COMPOUND_TAG.addString("String", "Hello World");

        uncompressed = readResource("uncompressed.nbt");
        compressed = readResource("compressed.nbt");
        named = readResource("named.nbt");
    }

    static byte[] readResource(final String name) throws IOException {
        InputStream is = NbtIOTest.class.getClassLoader().getResourceAsStream(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) != -1) baos.write(buf, 0, len);
        return baos.toByteArray();
    }

    static byte[] decompress(final byte[] bytes) throws IOException {
        GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) != -1) baos.write(buf, 0, len);
        return baos.toByteArray();
    }


    @Test
    void write() {
        ByteArrayOutputStream uncompressed = new ByteArrayOutputStream();
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        assertDoesNotThrow(() -> NBT_IO.write(uncompressed, "", COMPOUND_TAG, false));
        assertDoesNotThrow(() -> NBT_IO.write(compressed, "", COMPOUND_TAG, true));

        assertArrayEquals(NbtIOTest.uncompressed, uncompressed.toByteArray());
        assertArrayEquals(NbtIOTest.uncompressed, assertDoesNotThrow(() -> decompress(compressed.toByteArray())));
    }

    @Test
    void read() {
        NbtTag uncompressed = assertDoesNotThrow(() -> NBT_IO.read(new ByteArrayInputStream(NbtIOTest.uncompressed), false, NbtReadTracker.unlimitedDepth()));
        NbtTag compressed = assertDoesNotThrow(() -> NBT_IO.read(new ByteArrayInputStream(NbtIOTest.compressed), true, NbtReadTracker.unlimitedDepth()));

        assertEquals(COMPOUND_TAG, uncompressed);
        assertEquals(COMPOUND_TAG, compressed);
    }

    @Test
    void readNamed() {
        NamedTag namedTag = assertDoesNotThrow(() -> NBT_IO.readNamed(new DataInputStream(new ByteArrayInputStream(NbtIOTest.named)), NbtReadTracker.unlimitedDepth()));

        assertEquals("named", namedTag.getName());
        assertInstanceOf(StringTag.class, namedTag.getTag());
        assertEquals("text", namedTag.getTag().asStringTag().getValue());
    }

}
