package net.lenni0451.mcstructs.itemcomponents.registry;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;

import javax.annotation.Nonnull;
import java.util.Objects;

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

    public Registry getRegistry() {
        return this.registry;
    }

    public Identifier getId() {
        if (this.id == null) {
            this.id = this.registry.getId(this.networkId);
            if (this.id == null) throw new IllegalStateException("Network ID " + this.networkId + " not found in registry " + this.registry.getName());
        }
        return this.id;
    }

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
