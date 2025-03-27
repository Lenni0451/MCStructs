package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.TypeSerializers_v1_21_2;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.Arrays;

public class TypeSerializers_v1_21_4 extends TypeSerializers_v1_21_2 {

    protected static final String RGB_COLOR = "rgb_color";

    public TypeSerializers_v1_21_4(final ItemComponentRegistry registry, final TextComponentCodec textComponentCodec) {
        super(registry, textComponentCodec);
    }

    public Codec<Integer> rgbColor() {
        return this.init(RGB_COLOR, () -> Codec.oneOf(
                Codec.INTEGER,
                Codec.FLOAT.listOf(3, 3).map(i -> {
                    float r = ((i >> 16) & 0xFF) / 255F;
                    float g = ((i >> 8) & 0xFF) / 255F;
                    float b = (i & 0xFF) / 255F;
                    return Arrays.asList(r, g, b);
                }, floats -> {
                    int r = (int) (floats.get(0) * 255);
                    int g = (int) (floats.get(1) * 255);
                    int b = (int) (floats.get(2) * 255);
                    return (r << 16) | (g << 8) | b;
                })
        ));
    }

}
