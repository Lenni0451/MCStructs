package net.lenni0451.mcstructs.nbt.io;

import net.lenni0451.mcstructs.nbt.io.impl.NbtReader;
import net.lenni0451.mcstructs.nbt.io.impl.NbtWriter;
import net.lenni0451.mcstructs.nbt.io.impl.v1_0_0.NbtReader_v1_0_0;
import net.lenni0451.mcstructs.nbt.io.impl.v1_0_0.NbtWriter_v1_0_0;
import net.lenni0451.mcstructs.nbt.io.impl.v1_12.NbtReader_v1_12;
import net.lenni0451.mcstructs.nbt.io.impl.v1_12.NbtWriter_v1_12;
import net.lenni0451.mcstructs.nbt.io.impl.v1_2_1.NbtReader_v1_2_1;
import net.lenni0451.mcstructs.nbt.io.impl.v1_2_1.NbtWriter_v1_2_1;
import net.lenni0451.mcstructs.nbt.io.internal.NbtReadWrapper;
import net.lenni0451.mcstructs.nbt.io.internal.NbtWriteWrapper;

public class NbtIO implements NbtReadWrapper, NbtWriteWrapper {

    /**
     * NbtIO for version 1.0.0 and higher.<br>
     * Does not support int arrays and long arrays.
     */
    public static final NbtIO V1_0_0 = new NbtIO(new NbtReader_v1_0_0(), new NbtWriter_v1_0_0());
    /**
     * NbtIO for version 1.2.1 and higher.<br>
     * Does not support long arrays.
     */
    public static final NbtIO V1_2_1 = new NbtIO(new NbtReader_v1_2_1(), new NbtWriter_v1_2_1());
    /**
     * NbtIO for version 1.12 and higher.
     */
    public static final NbtIO V1_12 = new NbtIO(new NbtReader_v1_12(), new NbtWriter_v1_12());
    /**
     * The latest NbtIO.
     */
    public static final NbtIO LATEST = V1_12;


    private final NbtReader reader;
    private final NbtWriter writer;

    public NbtIO(final NbtReader reader, final NbtWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    /**
     * @return The Nbt reader
     */
    @Override
    public NbtReader getReader() {
        return this.reader;
    }

    /**
     * @return The Nbt writer
     */
    @Override
    public NbtWriter getWriter() {
        return this.writer;
    }

}
