package net.lenni0451.mcstructs.text.serializer.v1_21_2;

import com.google.gson.JsonElement;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;
import net.lenni0451.mcstructs.text.serializer.subtypes.ITextComponentSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt.NbtStyleSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.TextComponentCodec_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.json.JsonHoverEventSerializer_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.json.JsonStyleSerializer_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.nbt.NbtHoverEventSerializer_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_21_2.json.JsonTextSerializer_v1_21_2;
import net.lenni0451.mcstructs.text.serializer.v1_21_2.nbt.NbtTextSerializer_v1_21_2;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class TextComponentCodec_v1_21_2 extends TextComponentCodec_v1_20_5 {

    public TextComponentCodec_v1_21_2() {
        super(
                () -> SNbtSerializer.V1_14,
                (codec, sNbtSerializer) -> new JsonTextSerializer_v1_21_2((TextComponentCodec_v1_21_2) codec, textSerializer -> new JsonStyleSerializer_v1_20_5(styleSerializer -> new JsonHoverEventSerializer_v1_20_5((TextComponentCodec_v1_20_5) codec, textSerializer, sNbtSerializer))),
                (codec, sNbtSerializer) -> new NbtTextSerializer_v1_21_2((TextComponentCodec_v1_21_2) codec, textSerializer -> new NbtStyleSerializer_v1_20_3(styleSerializer -> new NbtHoverEventSerializer_v1_20_5((TextComponentCodec_v1_20_5) codec, textSerializer, sNbtSerializer)))
        );
    }

    protected TextComponentCodec_v1_21_2(final Supplier<SNbtSerializer<CompoundTag>> sNbtSerializerSupplier, final BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITextComponentSerializer<JsonElement>> jsonSerializerSupplier, final BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITextComponentSerializer<INbtTag>> nbtSerializerSupplier) {
        super(sNbtSerializerSupplier, jsonSerializerSupplier, nbtSerializerSupplier);
    }

    /**
     * Verify if the given entity selector is valid.<br>
     * You can override this method to make the component deserialization more legit.
     *
     * @param selector The entity selector to verify
     */
    public void verifyEntitySelector(final String selector) {
    }

}
