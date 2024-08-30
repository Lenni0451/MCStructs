package net.lenni0451.mcstructs.text.serializer.v1_21_2;

import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt.NbtStyleSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.TextComponentCodec_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.json.JsonHoverEventSerializer_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.json.JsonStyleSerializer_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.nbt.NbtHoverEventSerializer_v1_20_5;
import net.lenni0451.mcstructs.text.serializer.v1_21_2.json.JsonTextSerializer_v1_21_2;
import net.lenni0451.mcstructs.text.serializer.v1_21_2.nbt.NbtTextSerializer_v1_21_2;

public class TextComponentCodec_v1_21_2 extends TextComponentCodec_v1_20_5 {

    public TextComponentCodec_v1_21_2() {
        super(
                () -> SNbtSerializer.V1_14,
                (codec, sNbtSerializer) -> new JsonTextSerializer_v1_21_2((TextComponentCodec_v1_21_2) codec, textSerializer -> new JsonStyleSerializer_v1_20_5(styleSerializer -> new JsonHoverEventSerializer_v1_20_5((TextComponentCodec_v1_20_5) codec, textSerializer, sNbtSerializer))),
                (codec, sNbtSerializer) -> new NbtTextSerializer_v1_21_2((TextComponentCodec_v1_21_2) codec, textSerializer -> new NbtStyleSerializer_v1_20_3(styleSerializer -> new NbtHoverEventSerializer_v1_20_5((TextComponentCodec_v1_20_5) codec, textSerializer, sNbtSerializer)))
        );
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
