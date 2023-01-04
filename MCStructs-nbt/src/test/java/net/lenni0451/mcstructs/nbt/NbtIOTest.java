package net.lenni0451.mcstructs.nbt;

import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import static org.junit.jupiter.api.Assertions.*;

class NbtIOTest {

    private static final CompoundTag compoundTag = new CompoundTag();
    private static byte[] uncompressed;
    private static byte[] compressed;

    @BeforeAll
    static void prepare() throws IOException {
        compoundTag.addByteArray("ByteArray", (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5);
        compoundTag.addByte("Byte", (byte) 0);
        compoundTag.addCompound("Compound", new CompoundTag());
        compoundTag.addDouble("Double", 0D);
        compoundTag.addFloat("Float", 0F);
        compoundTag.addIntArray("IntArray", 0, 1, 2, 3, 4, 5);
        compoundTag.addInt("Int", 0);
        compoundTag.addList("List");
        compoundTag.addLongArray("LongArray", 0L, 1L, 2L, 3L, 4L, 5L);
        compoundTag.addLong("Long", 0L);
        compoundTag.addShort("Short", (short) 0);
        compoundTag.addString("String", "Hello World");

        uncompressed = readResource("uncompressed.nbt");
        compressed = readResource("compressed.nbt");
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
        assertDoesNotThrow(() -> NbtIO.write(uncompressed, "", compoundTag, false));
        assertDoesNotThrow(() -> NbtIO.write(compressed, "", compoundTag, true));

        assertArrayEquals(NbtIOTest.uncompressed, uncompressed.toByteArray());
        assertArrayEquals(NbtIOTest.uncompressed, assertDoesNotThrow(() -> decompress(compressed.toByteArray())));
    }

    @Test
    void read() {
        INbtTag uncompressed = assertDoesNotThrow(() -> NbtIO.read(new ByteArrayInputStream(NbtIOTest.uncompressed), false, NbtReadTracker.unlimited()));
        INbtTag compressed = assertDoesNotThrow(() -> NbtIO.read(new ByteArrayInputStream(NbtIOTest.compressed), true, NbtReadTracker.unlimited()));

        assertEquals(compoundTag, uncompressed);
        assertEquals(compoundTag, compressed);
    }

}