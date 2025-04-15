package net.lenni0451.mcstructs.itemcomponents.registry;

import net.lenni0451.mcstructs.core.Identifier;

import javax.annotation.Nullable;

public class NoOpRegistry extends Registry {

    public NoOpRegistry(final Identifier name) {
        super(name);
    }

    @Nullable
    @Override
    public RegistryEntry getEntry(Identifier id) {
        return new RegistryEntry(this, id);
    }

    @Nullable
    @Override
    public RegistryEntry getEntry(int networkId) {
        return new RegistryEntry(this, networkId);
    }

    @Nullable
    @Override
    public RegistryTag getTag(Identifier tag) {
        return new RegistryTag(this, tag);
    }

}
