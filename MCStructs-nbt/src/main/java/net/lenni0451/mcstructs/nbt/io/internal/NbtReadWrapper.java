package net.lenni0451.mcstructs.nbt.io.internal;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.io.NamedTag;
import net.lenni0451.mcstructs.nbt.io.NbtHeader;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.io.impl.INbtReader;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.WillNotClose;
import java.io.*;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;

/**
 * A wrapper class containing methods to read Nbt tags from different sources.
 */
@ParametersAreNonnullByDefault
public interface NbtReadWrapper {

    INbtReader getReader();

    /**
     * Read an uncompressed Nbt tag from a file.
     *
     * @param f           The file to read from
     * @param readTracker The read tracker to use
     * @return The read tag (null if an end tag was read)
     * @throws IOException If an I/O error occurs
     */
    @Nullable
    default NbtTag readFile(final File f, final NbtReadTracker readTracker) throws IOException {
        return this.readFile(f, false, readTracker);
    }

    /**
     * Read a compressed Nbt tag from a file.
     *
     * @param f           The file to read from
     * @param readTracker The read tracker to use
     * @return The read tag (null if an end tag was read)
     * @throws IOException If an I/O error occurs
     */
    @Nullable
    default NbtTag readCompressedFile(final File f, final NbtReadTracker readTracker) throws IOException {
        return this.readFile(f, true, readTracker);
    }

    /**
     * Read a Nbt tag from a file.
     *
     * @param f           The file to read from
     * @param compressed  Whether the file is compressed or not
     * @param readTracker The read tracker to use
     * @return The read tag (null if an end tag was read)
     * @throws IOException If an I/O error occurs
     */
    @Nullable
    default NbtTag readFile(final File f, final boolean compressed, final NbtReadTracker readTracker) throws IOException {
        try (InputStream fis = Files.newInputStream(f.toPath())) {
            return this.read(fis, compressed, readTracker);
        }
    }

    /**
     * Read a Nbt tag from an input stream.
     *
     * @param is          The input stream to read from
     * @param compressed  Whether the input stream is compressed or not
     * @param readTracker The read tracker to use
     * @return The read tag (null if an end tag was read)
     * @throws IOException If an I/O error occurs
     */
    @Nullable
    @WillNotClose
    default NbtTag read(final InputStream is, final boolean compressed, final NbtReadTracker readTracker) throws IOException {
        if (compressed) return this.read(new DataInputStream(new GZIPInputStream(is)), readTracker);
        else return this.read(new DataInputStream(is), readTracker);
    }

    /**
     * Read a Nbt tag from a {@link DataInput}.
     *
     * @param in          The data input to read from
     * @param readTracker The read tracker to use
     * @return The read tag (null if an end tag was read)
     * @throws IOException If an I/O error occurs
     */
    @Nullable
    @WillNotClose
    default NbtTag read(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        NamedTag named = this.readNamed(in, readTracker);
        if (named == null) return null;
        return named.getTag();
    }

    /**
     * Read a named Nbt tag from a {@link DataInput}.
     *
     * @param in          The data input to read from
     * @param readTracker The read tracker to use
     * @return The read tag (null if an end tag was read)
     * @throws IOException If an I/O error occurs
     */
    @Nullable
    default NamedTag readNamed(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        NbtHeader header = this.getReader().readHeader(in, readTracker);
        if (header.isEnd()) return null;
        readTracker.pushDepth();
        NbtTag tag = this.getReader().read(header.getType(), in, readTracker);
        readTracker.popDepth();
        return new NamedTag(header.getName(), header.getType(), tag);
    }

    /**
     * Read an unnamed Nbt tag from a {@link DataInput}.<br>
     * <b>Unnamed tags are a network only feature of Minecraft 1.20.2+!<br>
     * You probably need to use {@link #read(DataInput, NbtReadTracker)} instead!</b>
     *
     * @param in          The data input to read from
     * @param readTracker The read tracker to use
     * @return The read tag (null if an end tag was read)
     * @throws IOException If an I/O error occurs
     */
    @Nullable
    default NbtTag readUnnamed(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        NbtType type = this.getReader().readType(in, readTracker);
        if (NbtType.END.equals(type)) return null;
        readTracker.pushDepth();
        NbtTag tag = this.getReader().read(type, in, readTracker);
        readTracker.popDepth();
        return tag;
    }

}
