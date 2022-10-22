package net.lenni0451.mcstructs.nbt;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NbtIO {


    public static INbtTag readFile(final File f, final NbtReadTracker readTracker) throws IOException {
        return readFile(f, false, readTracker);
    }

    public static void writeFile(final File f, final String name, final INbtTag tag) throws IOException {
        writeFile(f, name, tag, false);
    }


    public static INbtTag readCompressedFile(final File f, final NbtReadTracker readTracker) throws IOException {
        return readFile(f, true, readTracker);
    }

    public static void writeCompressedFile(final File f, final String name, final INbtTag tag) throws IOException {
        writeFile(f, name, tag, true);
    }


    public static INbtTag readFile(final File f, final boolean compressed, final NbtReadTracker readTracker) throws IOException {
        return read(Files.newInputStream(f.toPath()), compressed, readTracker);
    }

    public static void writeFile(final File f, final String name, final INbtTag tag, final boolean compressed) throws IOException {
        write(Files.newOutputStream(f.toPath()), name, tag, compressed);
    }


    public static INbtTag read(final InputStream is, final boolean compressed, final NbtReadTracker readTracker) throws IOException {
        if (compressed) return read(new DataInputStream(new GZIPInputStream(is)), readTracker);
        else return read(new DataInputStream(is), readTracker);
    }

    public static void write(final OutputStream os, final String name, final INbtTag tag, final boolean compressed) throws IOException {
        if (compressed) write(new DataOutputStream(new GZIPOutputStream(os)), name, tag);
        else write(new DataOutputStream(os), name, tag);
    }


    public static INbtTag read(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        NbtHeader<?> header = NbtIO.readNbtHeader(in, readTracker);
        if (header.isEnd()) return null;
        INbtTag tag = NbtRegistry.newInstance(header.getType());
        readTracker.pushDepth();
        tag.read(in, readTracker);
        readTracker.popDepth();
        return tag;
    }

    public static void write(final DataOutput out, final String name, final INbtTag tag) throws IOException {
        writeNbtHeader(out, new NbtHeader<>(tag.getClass(), name));
        tag.write(out);
    }


    public static NbtHeader<?> readNbtHeader(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        byte type = in.readByte();
        if (type == NbtRegistry.END_NBT) return NbtHeader.END;
        return new NbtHeader<>(NbtRegistry.getTagClass(type), in.readUTF());
    }

    public static void writeNbtHeader(final DataOutput out, final NbtHeader<?> header) throws IOException {
        if (header.isEnd()) {
            out.writeByte(NbtRegistry.END_NBT);
        } else {
            out.writeByte(NbtRegistry.getTagId(header.getType()));
            out.writeUTF(header.getName());
        }
    }

}
