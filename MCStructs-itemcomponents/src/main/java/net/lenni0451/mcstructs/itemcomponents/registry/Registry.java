package net.lenni0451.mcstructs.itemcomponents.registry;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * A class that represents a registry of {@link RegistryEntry}s and {@link RegistryTag}s.
 */
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

    /**
     * @return The name of the registry
     */
    public Identifier getName() {
        return this.name;
    }

    /**
     * @return The codec for {@link RegistryEntry}s in this registry
     */
    public Codec<RegistryEntry> entryCodec() {
        return this.entryCodec;
    }

    /**
     * Get the {@link RegistryEntry} for the given id.
     *
     * @param id The id of the entry
     * @return The entry or null if it does not exist
     */
    @Nullable
    public abstract RegistryEntry getEntry(final Identifier id);

    /**
     * Get the {@link RegistryEntry} for the given network id.
     *
     * @param networkId The network id of the entry
     * @return The entry or null if it does not exist
     */
    @Nullable
    public abstract RegistryEntry getEntry(final int networkId);

    /**
     * Get the network id for the given id.
     *
     * @param id The id of the entry
     * @return The network id or null if it does not exist
     */
    @Nullable
    public abstract Integer getNetworkId(final Identifier id);

    /**
     * Get the id for the given network id.
     *
     * @param networkId The network id of the entry
     * @return The id or null if it does not exist
     */
    @Nullable
    public abstract Identifier getId(final int networkId);

    /**
     * Get an {@link EitherEntry} for the given id.
     *
     * @param id  The id of the entry
     * @param <T> The type of the entry
     * @return The entry or null if it does not exist
     */
    @Nullable
    public <T> EitherEntry<T> getLeftEntry(final Identifier id) {
        RegistryEntry entry = this.getEntry(id);
        if (entry == null) return null;
        return new EitherEntry<>(entry);
    }

    /**
     * Get an {@link EitherEntry} for the given network id.
     *
     * @param networkId The network id of the entry
     * @param <T>       The type of the entry
     * @return The entry or null if it does not exist
     */
    @Nullable
    public <T> EitherEntry<T> getLeftEntry(final int networkId) {
        RegistryEntry entry = this.getEntry(networkId);
        if (entry == null) return null;
        return new EitherEntry<>(entry);
    }

    /**
     * Get the {@link RegistryTag} for the given tag id.
     *
     * @param tag The id of the tag
     * @return The tag or null if it does not exist
     */
    @Nullable
    public abstract RegistryTag getTag(final Identifier tag);

    @Override
    public String toString() {
        return ToString.of(this)
                .add("name", this.name)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Registry registry = (Registry) o;
        return Objects.equals(this.name, registry.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

}
