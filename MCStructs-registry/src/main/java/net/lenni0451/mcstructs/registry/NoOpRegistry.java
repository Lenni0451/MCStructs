package net.lenni0451.mcstructs.registry;

import net.lenni0451.mcstructs.core.Identifier;

import javax.annotation.Nullable;

/**
 * A no-op registry that does nothing.<br>
 * Every value is valid, but the registry will not be able to convert them.
 */
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
    public Integer getNetworkId(Identifier id) {
        return null;
    }

    @Nullable
    @Override
    public Identifier getId(int networkId) {
        return null;
    }

    @Nullable
    @Override
    public TagKey getTag(Identifier tag) {
        return new TagKey(this, tag);
    }

}
