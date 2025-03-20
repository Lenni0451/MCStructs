package net.lenni0451.mcstructs.nbt.io.impl.v1_2_1;

import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.io.impl.v1_0_0.NbtReader_v1_0_0;
import net.lenni0451.mcstructs.nbt.tags.IntArrayTag;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.DataInput;
import java.io.IOException;

/**
 * The Nbt reader implementation for the Java Edition Nbt format.
 */
@ParametersAreNonnullByDefault
public class NbtReader_v1_2_1 extends NbtReader_v1_0_0 {

    @Nonnull
    @Override
    public IntArrayTag readIntArray(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(24);
        int length = in.readInt();
        readTracker.read(length, 4);
        int[] value = new int[length];
        for (int i = 0; i < value.length; i++) value[i] = in.readInt();
        return new IntArrayTag(value);
    }

}
