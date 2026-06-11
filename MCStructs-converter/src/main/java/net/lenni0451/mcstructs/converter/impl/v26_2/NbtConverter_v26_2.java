package net.lenni0451.mcstructs.converter.impl.v26_2;

import net.lenni0451.mcstructs.converter.impl.v1_21_5.NbtConverter_v1_21_5;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbt;

public class NbtConverter_v26_2 extends NbtConverter_v1_21_5 {

    public static final NbtConverter_v26_2 INSTANCE = new NbtConverter_v26_2();

    public NbtConverter_v26_2() {
        super(SNbt.V1_21_5);
    }

    protected NbtConverter_v26_2(final SNbt<CompoundTag> sNbt) {
        super(sNbt);
    }

    @Override
    public Result<Boolean> asBoolean(NbtTag element) {
        return this.asNumber(element).map(number -> number.doubleValue() != 0.0);
    }

}
