package net.lenni0451.mcstructs.text.serializer.v1_21_2;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.util.UUID;

public class ExtraCodecs_v1_21_2 {

    public static final Codec<UUID> LENIENT_UUID = Codec.oneOf(Codec.INT_ARRAY_UUID, Codec.STRICT_STRING_UUID);
    public static final Codec<CompoundTag> INLINED_COMPOUND_TAG = NbtConverter_v1_20_3.INSTANCE.toCodec().verified(tag -> {
        if (!tag.isCompoundTag()) return Result.error("Expected a compound tag");
        return null;
    }).map(NbtTag::asCompoundTag, NbtTag::asCompoundTag);

}
