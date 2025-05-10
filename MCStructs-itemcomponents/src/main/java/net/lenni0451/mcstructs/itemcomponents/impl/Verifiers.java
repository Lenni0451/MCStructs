package net.lenni0451.mcstructs.itemcomponents.impl;

import net.lenni0451.mcstructs.itemcomponents.registry.RegistryEntry;

/**
 * Verifier methods for not registry based things.
 */
public interface Verifiers {

    /**
     * Verify the given block state.
     *
     * @param block The block owning the state
     * @param state The state to verify
     * @return If the state is valid
     */
    default boolean verifyBlockState(final RegistryEntry block, final String state) {
        return true;
    }

}
