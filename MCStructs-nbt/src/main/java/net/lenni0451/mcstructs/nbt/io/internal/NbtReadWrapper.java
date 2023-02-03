package net.lenni0451.mcstructs.nbt.io.internal;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.io.NbtHeader;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.io.types.INbtReader;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;

public interface NbtReadWrapper {

    INbtReader getReader();

    /**
     * Read an uncompressed Nbt tag from a file.
     *
     * @param f           The file to read from
     * @param readTracker The read tracker to use
     * @return The read tag
     * @throws IOException If an I/O error occurs
     */
    default INbtTag readFile(final File f, final NbtReadTracker readTracker) throws IOException {
        return this.readFile(f, false, readTracker);
    }

    /**
     * Read a compressed Nbt tag from a file.
     *
     * @param f           The file to read from
     * @param readTracker The read tracker to use
     * @return The read tag
     * @throws IOException If an I/O error occurs
     */
    default INbtTag readCompressedFile(final File f, final NbtReadTracker readTracker) throws IOException {
        return this.readFile(f, true, readTracker);
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
    default INbtTag readFile(final File f, final boolean compressed, final NbtReadTracker readTracker) throws IOException {
        try (InputStream fis = Files.newInputStream(f.toPath())) {
            return this.read(fis, compressed, readTracker);
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
    default INbtTag read(final InputStream is, final boolean compressed, final NbtReadTracker readTracker) throws IOException {
        if (compressed) return this.read(new DataInputStream(new GZIPInputStream(is)), readTracker);
        else return this.read(new DataInputStream(is), readTracker);
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
    default INbtTag read(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        NbtHeader header = this.getReader().readHeader(in, readTracker);
        if (header.isEnd()) return null;
        readTracker.pushDepth();
        INbtTag tag = this.getReader().read(header.getType(), in, readTracker);
        readTracker.popDepth();
        return tag;
    }

}
