package net.lenni0451.mcstructs.itemcomponents.registry;

import net.lenni0451.mcstructs.core.Identifier;

import javax.annotation.Nonnull;

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
    public int hashCode() {
        return this.hashCode;
    }

}
