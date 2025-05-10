package net.lenni0451.mcstructs.itemcomponents.registry;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.utils.ToString;

/**
 * A class that represents a tag in a {@link Registry}.<br>
 * Tags are used to group multiple {@link RegistryEntry}s together.
 */
@EqualsAndHashCode
public class RegistryTag {

    /**
     * Create a codec for this class bound to the given registry.
     *
     * @param registry The registry owning the tag
     * @return The codec for this class
     */
    public static Codec<RegistryTag> codec(final Registry registry) {
        return Codec.STRING
                .verified(s -> s.startsWith("#") ? null : Result.error("Tag needs to start with #"))
                .flatMap(identifier -> Result.success("#" + identifier.get()), s -> {
                    Identifier id = Identifier.tryOf(s.substring(1));
                    if (id == null) return Result.error("Tag is not a valid identifier");
                    else return Result.success(id);
                })
                .flatMap(registryTag -> Result.success(registryTag.getTag()), identifier -> {
                    RegistryTag tag = registry.getTag(identifier);
                    if (tag == null) return Result.error("Registry " + registry.getName() + " doesn't contain tag " + identifier);
                    return Result.success(tag);
                });
    }


    private final Registry registry;
    private final Identifier tag;

    public RegistryTag(final Registry registry, final Identifier tag) {
        this.registry = registry;
        this.tag = tag;
    }

    /**
     * @return The registry this tag belongs to
     */
    public Registry getRegistry() {
        return this.registry;
    }

    /**
     * @return The tag id
     */
    public Identifier getTag() {
        return this.tag;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("registry", this.registry.getName())
                .add("tag", this.tag)
                .toString();
    }

}
