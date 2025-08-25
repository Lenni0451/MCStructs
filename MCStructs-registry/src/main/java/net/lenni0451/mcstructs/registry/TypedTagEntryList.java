package net.lenni0451.mcstructs.registry;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.core.utils.ToString;

import java.util.List;
import java.util.Objects;

/**
 * A class that represents a {@link RegistryTag} or a list of {@link EitherEntry}.
 */
@EqualsAndHashCode
public class TypedTagEntryList<T> {

    /**
     * Create a codec for this class bound to the given registry.
     *
     * @param registry    The registry owning the tag
     * @param requireList Require single entries to be in a list
     * @param codec       The codec of the entries
     * @param <T>         The type of the entries
     * @return The codec for this class
     */
    public static <T> Codec<TypedTagEntryList<T>> codec(final Registry registry, final Codec<T> codec, final boolean requireList) {
        return Codec.either(RegistryTag.codec(registry), HomogenousListCodec.codec(EitherEntry.codec(registry, codec), requireList))
                .map(tagEntryList -> tagEntryList.isTag() ? Either.left(tagEntryList.getTag()) : Either.right(tagEntryList.getEntries()),
                        either -> either.xmap(TypedTagEntryList::new, TypedTagEntryList::new));
    }


    private final RegistryTag tag;
    private final List<EitherEntry<T>> entries;

    public TypedTagEntryList(final RegistryTag tag) {
        this.tag = tag;
        this.entries = null;
    }

    public TypedTagEntryList(final List<EitherEntry<T>> entries) {
        this.tag = null;
        this.entries = entries;
    }

    /**
     * @return If this list has a {@link RegistryTag}
     */
    public boolean isTag() {
        return this.tag != null;
    }

    /**
     * @return If this list has {@link RegistryEntry}s
     */
    public boolean isEntries() {
        return this.entries != null;
    }

    /**
     * @return The {@link RegistryTag} of this list
     */
    public RegistryTag getTag() {
        return this.tag;
    }

    /**
     * @return The {@link RegistryEntry}s of this list
     */
    public List<EitherEntry<T>> getEntries() {
        return this.entries;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("tag", this.tag, Objects::nonNull)
                .add("entries", this.entries, Objects::nonNull)
                .toString();
    }

}
