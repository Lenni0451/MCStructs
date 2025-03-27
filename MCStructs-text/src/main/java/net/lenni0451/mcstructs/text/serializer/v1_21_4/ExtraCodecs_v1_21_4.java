package net.lenni0451.mcstructs.text.serializer.v1_21_4;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.util.Arrays;
import java.util.UUID;

public class ExtraCodecs_v1_21_4 {

    public static final Codec<Integer> ARGB_COLOR = Codec.oneOf(
            Codec.INTEGER,
            Codec.FLOAT.listOf(4, 4).map(i -> {
                float a = (i >> 24 & 255) / 255F;
                float r = (i >> 16 & 255) / 255F;
                float g = (i >> 8 & 255) / 255F;
                float b = (i & 255) / 255F;
                return Arrays.asList(r, g, b, a);
            }, floats -> {
                int r = (int) (floats.get(0) * 255);
                int g = (int) (floats.get(1) * 255);
                int b = (int) (floats.get(2) * 255);
                int a = (int) (floats.get(3) * 255);
                return a << 24 | r << 16 | g << 8 | b;
            })
    );
    public static final Codec<UUID> LENIENT_UUID = Codec.oneOf(Codec.INT_ARRAY_UUID, Codec.STRICT_STRING_UUID);
    public static final Codec<CompoundTag> INLINED_COMPOUND_TAG = NbtConverter_v1_20_3.INSTANCE.toCodec().verified(tag -> {
        if (!tag.isCompoundTag()) return Result.error("Expected a compound tag");
        return null;
    }).map(NbtTag::asCompoundTag, NbtTag::asCompoundTag);

}
