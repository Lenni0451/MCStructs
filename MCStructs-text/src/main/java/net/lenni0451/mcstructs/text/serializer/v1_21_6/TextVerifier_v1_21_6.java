package net.lenni0451.mcstructs.text.serializer.v1_21_6;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.serializer.verify.TextVerifier;

public interface TextVerifier_v1_21_6 extends TextVerifier {

    default boolean verifyRegistryItem(final Identifier id) {
        return true;
    }

    default boolean verifyRegistryEntity(final Identifier id) {
        return true;
    }

    default boolean verifyDataComponents(final CompoundTag tag) {
        return true;
    }

    default boolean verifySelector(final String selector) {
        return true;
    }

}
