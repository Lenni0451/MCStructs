package net.lenni0451.mcstructs.registry;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;

/**
 * A class that represents an entry in a {@link Registry}.
 */
@EqualsAndHashCode
public class ResourceKey {

    public static Codec<ResourceKey> codec(final Registry registry) {
        return Codec.STRING_IDENTIFIER.map(key -> key.name, name -> new ResourceKey(registry, name));
    }


    private final Registry registry;
    private final Identifier name;

    public ResourceKey(final Registry registry, final Identifier name) {
        this.registry = registry;
        this.name = name;
    }

    /**
     * @return The registry this entry belongs to
     */
    public Registry getRegistry() {
        return this.registry;
    }

    /**
     * @return The entry name
     */
    public Identifier getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("registry", this.registry.getName())
                .add("name", this.name)
                .toString();
    }

}
