package net.lenni0451.mcstructs.itemcomponents.registry;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Either;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TagEntryList {

    private static Codec<List<RegistryEntry>> homogenousList(final Codec<RegistryEntry> entryCodec, final boolean requireList) {
        Codec<List<RegistryEntry>> listCodec = entryCodec.listOf();
        if (requireList) {
            return listCodec;
        } else {
            return Codec.either(listCodec, entryCodec)
                    .map(registryEntries -> {
                        if (registryEntries.size() == 1) {
                            return Either.right(registryEntries.get(0));
                        } else {
                            return Either.left(registryEntries);
                        }
                    }, either -> either.xmap(Function.identity(), Arrays::asList));
        }
    }

    public static Codec<TagEntryList> codec(final Registry registry, final boolean requireList) {
        return Codec.either(RegistryTag.codec(registry), homogenousList(registry.entryCodec(), requireList))
                .map(tagEntryList -> tagEntryList.isTag() ? Either.left(tagEntryList.getTag()) : Either.right(tagEntryList.getEntries()),
                        either -> either.xmap(TagEntryList::new, TagEntryList::new));
    }


    private final RegistryTag tag;
    private final List<RegistryEntry> entries;

    public TagEntryList(final RegistryTag tag) {
        this.tag = tag;
        this.entries = null;
    }

    public TagEntryList(final List<RegistryEntry> entries) {
        this.tag = null;
        this.entries = entries;
    }

    public boolean isTag() {
        return this.tag != null;
    }

    public boolean isEntries() {
        return this.entries != null;
    }

    public RegistryTag getTag() {
        return this.tag;
    }

    public List<RegistryEntry> getEntries() {
        return this.entries;
    }

}
