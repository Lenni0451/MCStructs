package net.lenni0451.mcstructs.nbt.io.impl.v1_12;

import net.lenni0451.mcstructs.nbt.io.impl.v1_2_1.NbtWriter_v1_2_1;
import net.lenni0451.mcstructs.nbt.tags.LongArrayTag;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The Nbt writer implementation for the Java Edition Nbt format.
 */
@ParametersAreNonnullByDefault
public class NbtWriter_v1_12 extends NbtWriter_v1_2_1 {

    @Override
    public void writeLongArray(DataOutput out, LongArrayTag value) throws IOException {
        out.writeInt(value.getLength());
        for (long l : value.getValue()) out.writeLong(l);
    }

}
