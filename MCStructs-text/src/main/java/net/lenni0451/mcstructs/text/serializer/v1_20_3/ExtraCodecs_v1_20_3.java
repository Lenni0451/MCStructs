package net.lenni0451.mcstructs.text.serializer.v1_20_3;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbt;

import java.util.UUID;

public class ExtraCodecs_v1_20_3 {

    public static final Codec<CompoundTag> STRING_COMPOUND = Codec.STRING.mapThrowing(SNbt.V1_14::serialize, SNbt.V1_14::deserialize);
    public static final Codec<UUID> LENIENT_UUID = Codec.oneOf(Codec.INT_ARRAY_UUID, Codec.STRICT_STRING_UUID);

}
