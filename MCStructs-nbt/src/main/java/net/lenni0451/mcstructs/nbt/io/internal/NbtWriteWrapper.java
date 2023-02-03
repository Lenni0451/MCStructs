package net.lenni0451.mcstructs.nbt.io.internal;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.io.NbtHeader;
import net.lenni0451.mcstructs.nbt.io.types.INbtWriter;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.file.Files;
import java.util.zip.GZIPOutputStream;

/**
 * A wrapper class containing methods to write Nbt tags to different sources.
 */
public interface NbtWriteWrapper {

    INbtWriter getWriter();

    /**
     * Write and uncompressed Nbt tag to a file.
     *
     * @param f    The file to write to
     * @param name The name of the tag (should be "" if not needed)
     * @param tag  The tag to write
     * @throws IOException If an I/O error occurs
     */
    default void writeFile(final File f, @Nonnull final String name, final INbtTag tag) throws IOException {
        this.writeFile(f, name, tag, false);
    }

    /**
     * Write a compressed Nbt tag to a file.
     *
     * @param f    The file to write to
     * @param name The name of the tag (should be "" if not needed)
     * @param tag  The tag to write
     * @throws IOException If an I/O error occurs
     */
    default void writeCompressedFile(final File f, final String name, final INbtTag tag) throws IOException {
        this.writeFile(f, name, tag, true);
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
    default void writeFile(final File f, final String name, final INbtTag tag, final boolean compressed) throws IOException {
        try (OutputStream fos = Files.newOutputStream(f.toPath())) {
            this.write(fos, name, tag, compressed);
        }
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
    default void write(final OutputStream os, final String name, final INbtTag tag, final boolean compressed) throws IOException {
        if (compressed) {
            try (GZIPOutputStream gos = new GZIPOutputStream(os)) {
                this.write(new DataOutputStream(gos), name, tag);
            }
        } else {
            this.write(new DataOutputStream(os), name, tag);
        }
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
    default void write(final DataOutput out, final String name, final INbtTag tag) throws IOException {
        this.getWriter().writeHeader(out, new NbtHeader(NbtType.byClass(tag.getClass()), name));
        this.getWriter().write(out, tag);
    }

}
