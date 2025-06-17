package net.lenni0451.mcstructs.registry;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * An entry in a {@link Registry}.
 */
public class RegistryEntry {

    private final Registry registry;
    private Identifier id;
    private Integer networkId;
    private final int cachedHashCode;

    public RegistryEntry(@Nonnull final Registry registry, final Identifier id) {
        this.registry = registry;
        this.id = id;
        this.cachedHashCode = id.hashCode();
    }

    public RegistryEntry(final Registry registry, final int networkId) {
        this.registry = registry;
        this.networkId = networkId;
        this.cachedHashCode = this.networkId.hashCode();
    }

    /**
     * @return The registry this entry belongs to
     */
    public Registry getRegistry() {
        return this.registry;
    }

    /**
     * Get the id of this entry.<br>
     * If the id has not been resolved yet, it will be resolved using the registry.
     * An exception will be thrown if the id is not found in the registry.
     *
     * @return The id of this entry
     */
    public Identifier getId() {
        if (this.id == null) {
            this.id = this.registry.getId(this.networkId);
            if (this.id == null) throw new IllegalStateException("Network ID " + this.networkId + " not found in registry " + this.registry.getName());
        }
        return this.id;
    }

    /**
     * Get the network id of this entry.<br>
     * If the network id has not been resolved yet, it will be resolved using the registry.
     * An exception will be thrown if the network id is not found in the registry.
     *
     * @return The network id of this entry
     */
    public int getNetworkId() {
        if (this.networkId == null) {
            this.networkId = this.registry.getNetworkId(this.id);
            if (this.networkId == null) throw new IllegalStateException("ID " + this.id + " not found in registry " + this.registry.getName());
        }
        return this.networkId;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("registry", this.registry.getName())
                .add("id", this.id, Objects::nonNull)
                .add("networkId", this.networkId, Objects::nonNull)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RegistryEntry that = (RegistryEntry) o;
        return Objects.equals(this.registry, that.registry) && (Objects.equals(this.id, that.id) || Objects.equals(this.networkId, that.networkId));
    }

    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }

}
