package net.lenni0451.mcstructs.itemcomponents.registry;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;

import javax.annotation.Nonnull;
import java.util.Objects;

public class RegistryEntry {

    private final Registry registry;
    private Identifier id;
    private Integer networkId;
    private final int hashCode;

    public RegistryEntry(@Nonnull final Registry registry, final Identifier id) {
        this.registry = registry;
        this.id = id;
        this.hashCode = id.hashCode();
    }

    public RegistryEntry(final Registry registry, final int networkId) {
        this.registry = registry;
        this.networkId = networkId;
        this.hashCode = this.networkId.hashCode();
    }

    public Registry getRegistry() {
        return this.registry;
    }

    public Identifier getId() {
        return this.id;
    }

    public int getNetworkId() {
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
        return this.hashCode;
    }

}
