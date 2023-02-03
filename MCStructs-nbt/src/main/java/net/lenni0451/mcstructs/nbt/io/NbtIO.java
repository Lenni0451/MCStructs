package net.lenni0451.mcstructs.nbt.io;

import net.lenni0451.mcstructs.nbt.io.impl.JavaNbtReader;
import net.lenni0451.mcstructs.nbt.io.impl.JavaNbtWriter;
import net.lenni0451.mcstructs.nbt.io.internal.NbtReadWrapper;
import net.lenni0451.mcstructs.nbt.io.internal.NbtWriteWrapper;
import net.lenni0451.mcstructs.nbt.io.types.INbtReader;
import net.lenni0451.mcstructs.nbt.io.types.INbtWriter;

public class NbtIO implements NbtReadWrapper, NbtWriteWrapper {

    public static final NbtIO JAVA = new NbtIO(new JavaNbtReader(), new JavaNbtWriter());


    private final INbtReader reader;
    private final INbtWriter writer;

    public NbtIO(final INbtReader reader, final INbtWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public INbtReader getReader() {
        return this.reader;
    }

    public INbtWriter getWriter() {
        return this.writer;
    }

}
