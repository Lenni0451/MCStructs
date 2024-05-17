package net.lenni0451.mcstructs.itemcomponents.impl;

import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;

/**
 * An abstract class for version specific type serializers.<br>
 * This only exists to allow type serializers to be constructed outside the class constructor.
 */
public class TypeSerializers {

    protected final ItemComponentRegistry registry;

    public TypeSerializers(final ItemComponentRegistry registry) {
        this.registry = registry;
    }

}
