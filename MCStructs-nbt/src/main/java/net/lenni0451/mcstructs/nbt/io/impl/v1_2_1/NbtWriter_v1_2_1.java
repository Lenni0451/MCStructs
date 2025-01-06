package net.lenni0451.mcstructs.nbt.io.impl.v1_2_1;

import net.lenni0451.mcstructs.nbt.io.impl.v1_0_0.NbtWriter_v1_0_0;
import net.lenni0451.mcstructs.nbt.tags.IntArrayTag;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The Nbt writer implementation for the Java Edition Nbt format.
 */
@ParametersAreNonnullByDefault
public class NbtWriter_v1_2_1 extends NbtWriter_v1_0_0 {

    @Override
    public void writeIntArray(DataOutput out, IntArrayTag value) throws IOException {
        out.writeInt(value.getLength());
        for (int i : value.getValue()) out.writeInt(i);
    }

}
