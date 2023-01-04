package net.lenni0451.mcstructs.nbt;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NbtIO {

    /**
     * Read an uncompressed Nbt tag from a file.
     *
     * @param f           The file to read from
     * @param readTracker The read tracker to use
     * @return The read tag
     * @throws IOException If an I/O error occurs
     */
    public static INbtTag readFile(final File f, final NbtReadTracker readTracker) throws IOException {
        return readFile(f, false, readTracker);
    }

    /**
     * Write and uncompressed Nbt tag to a file.
     *
     * @param f    The file to write to
     * @param name The name of the tag (should be "" if not needed)
     * @param tag  The tag to write
     * @throws IOException If an I/O error occurs
     */
    public static void writeFile(final File f, @Nonnull final String name, final INbtTag tag) throws IOException {
        writeFile(f, name, tag, false);
    }


    /**
     * Read a compressed Nbt tag from a file.
     *
     * @param f           The file to read from
     * @param readTracker The read tracker to use
     * @return The read tag
     * @throws IOException If an I/O error occurs
     */
    public static INbtTag readCompressedFile(final File f, final NbtReadTracker readTracker) throws IOException {
        return readFile(f, true, readTracker);
    }

    /**
     * Write a compressed Nbt tag to a file.
     *
     * @param f    The file to write to
     * @param name The name of the tag (should be "" if not needed)
     * @param tag  The tag to write
     * @throws IOException If an I/O error occurs
     */
    public static void writeCompressedFile(final File f, final String name, final INbtTag tag) throws IOException {
        writeFile(f, name, tag, true);
    }


    /**
     * Read a Nbt tag from a file.
     *
     * @param f           The file to read from
     * @param compressed  Whether the file is compressed or not
     * @param readTracker The read tracker to use
     * @return The read tag
     * @throws IOException If an I/O error occurs
     */
    public static INbtTag readFile(final File f, final boolean compressed, final NbtReadTracker readTracker) throws IOException {
        try (InputStream fis = Files.newInputStream(f.toPath())) {
            return read(fis, compressed, readTracker);
        }
    }

    /**
     * Write a Nbt tag to a file.
     *
     * @param f          The file to write to
     * @param name       The name of the tag (should be "" if not needed)
     * @param tag        The tag to write
     * @param compressed Whether the file should be compressed or not
     * @throws IOException If an I/O error occurs
     */
    public static void writeFile(final File f, final String name, final INbtTag tag, final boolean compressed) throws IOException {
        try (OutputStream fos = Files.newOutputStream(f.toPath())) {
            write(fos, name, tag, compressed);
        }
    }


    /**
     * Read a Nbt tag from an input stream.<br>
     * The stream will <b>not</b> be closed.
     *
     * @param is          The input stream to read from
     * @param compressed  Whether the input stream is compressed or not
     * @param readTracker The read tracker to use
     * @return The read tag
     * @throws IOException If an I/O error occurs
     */
    public static INbtTag read(final InputStream is, final boolean compressed, final NbtReadTracker readTracker) throws IOException {
        if (compressed) return read(new DataInputStream(new GZIPInputStream(is)), readTracker);
        else return read(new DataInputStream(is), readTracker);
    }

    /**
     * Write a Nbt tag to an output stream.<br>
     *
     * @param os         The output stream to write to
     * @param name       The name of the tag (should be "" if not needed)
     * @param tag        The tag to write
     * @param compressed Whether the output stream should be compressed or not
     * @throws IOException If an I/O error occurs
     */
    public static void write(final OutputStream os, final String name, final INbtTag tag, final boolean compressed) throws IOException {
        if (compressed) {
            try (GZIPOutputStream gos = new GZIPOutputStream(os)) {
                write(new DataOutputStream(gos), name, tag);
            }
        } else {
            write(new DataOutputStream(os), name, tag);
        }
    }


    /**
     * Read a Nbt tag from a {@link DataInput}.<br>
     * The data input will <b>not</b> be closed.
     *
     * @param in          The data input to read from
     * @param readTracker The read tracker to use
     * @return The read tag
     * @throws IOException If an I/O error occurs
     */
    public static INbtTag read(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        NbtHeader header = readNbtHeader(in, readTracker);
        if (header.isEnd()) return null;
        INbtTag tag = header.getType().newInstance();
        readTracker.pushDepth();
        tag.read(in, readTracker);
        readTracker.popDepth();
        return tag;
    }

    /**
     * Write a Nbt tag to a {@link DataOutput}.<br>
     * The data output will <b>not</b> be closed.
     *
     * @param out  The data output to write to
     * @param name The name of the tag (should be "" if not needed)
     * @param tag  The tag to write
     * @throws IOException If an I/O error occurs
     */
    public static void write(final DataOutput out, final String name, final INbtTag tag) throws IOException {
        writeNbtHeader(out, new NbtHeader(NbtType.byClass(tag.getClass()), name));
        tag.write(out);
    }


    /**
     * Read a Nbt header from a {@link DataInput}.
     *
     * @param in          The data input to read from
     * @param readTracker The read tracker to use
     * @return The read header
     * @throws IOException If an I/O error occurs
     */
    public static NbtHeader readNbtHeader(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        byte type = in.readByte();
        if (NbtType.END.getId() == type) return NbtHeader.END;
        return new NbtHeader(NbtType.byId(type), in.readUTF());
    }

    /**
     * Write a Nbt header to a {@link DataOutput}.
     *
     * @param out    The data output to write to
     * @param header The header to write
     * @throws IOException If an I/O error occurs
     */
    public static void writeNbtHeader(final DataOutput out, final NbtHeader header) throws IOException {
        if (header.isEnd()) {
            out.writeByte(NbtType.END.getId());
        } else {
            out.writeByte(header.getType().getId());
            out.writeUTF(header.getName());
        }
    }

}
