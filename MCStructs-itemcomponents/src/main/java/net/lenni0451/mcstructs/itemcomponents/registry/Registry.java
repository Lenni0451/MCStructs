package net.lenni0451.mcstructs.itemcomponents.registry;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.core.Identifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class Registry {

    private final Identifier name;
    private final Codec<RegistryEntry> entryCodec;

    protected Registry(@Nonnull final Identifier name) {
        this.name = name;
        this.entryCodec = Codec.STRING_IDENTIFIER.flatMap(registryEntry -> Result.success(registryEntry.getId()), identifier -> {
            RegistryEntry entry = this.getEntry(identifier);
            if (entry == null) return Result.error("Entry " + identifier + " not found in registry " + name);
            return Result.success(entry);
        });
    }

    public Identifier getName() {
        return this.name;
    }

    public Codec<RegistryEntry> entryCodec() {
        return this.entryCodec;
    }

    @Nullable
    public abstract RegistryEntry getEntry(final Identifier id);

    @Nullable
    public abstract RegistryEntry getEntry(final int networkId);

    @Nullable
    public <T> EitherEntry<T> getLeftEntry(final Identifier id) {
        RegistryEntry entry = this.getEntry(id);
        if (entry == null) return null;
        return new EitherEntry<>(entry);
    }

    @Nullable
    public abstract RegistryTag getTag(final Identifier tag);

}
