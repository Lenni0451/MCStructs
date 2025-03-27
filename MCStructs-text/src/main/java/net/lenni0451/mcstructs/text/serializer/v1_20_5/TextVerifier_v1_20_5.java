package net.lenni0451.mcstructs.text.serializer.v1_20_5;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.serializer.verify.TextVerifier;

public interface TextVerifier_v1_20_5 extends TextVerifier {

    default boolean verifyRegistryItem(final Identifier id) {
        return true;
    }

    default boolean verifyRegistryEntity(final Identifier id) {
        return true;
    }

    default boolean verifyDataComponents(final CompoundTag tag) {
        return true;
    }

}
