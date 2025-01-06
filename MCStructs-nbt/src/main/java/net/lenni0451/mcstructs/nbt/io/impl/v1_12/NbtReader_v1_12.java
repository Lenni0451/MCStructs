package net.lenni0451.mcstructs.nbt.io.impl.v1_12;

import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.io.impl.v1_2_1.NbtReader_v1_2_1;
import net.lenni0451.mcstructs.nbt.tags.LongArrayTag;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.DataInput;
import java.io.IOException;

/**
 * The Nbt reader implementation for the Java Edition Nbt format.
 */
@ParametersAreNonnullByDefault
public class NbtReader_v1_12 extends NbtReader_v1_2_1 {

    @Nonnull
    @Override
    public LongArrayTag readLongArray(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(24);
        int length = in.readInt();
        readTracker.read(8 * length);
        long[] value = new long[length];
        for (int i = 0; i < value.length; i++) value[i] = in.readLong();
        return new LongArrayTag(value);
    }

}
