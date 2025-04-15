package net.lenni0451.mcstructs.itemcomponents.impl;

import net.lenni0451.mcstructs.itemcomponents.registry.RegistryEntry;

/**
 * Verifier methods for not registry based things.
 */
public interface Verifiers {

    default boolean verifyBlockState(final RegistryEntry block, final String state) {
        return true;
    }

}
