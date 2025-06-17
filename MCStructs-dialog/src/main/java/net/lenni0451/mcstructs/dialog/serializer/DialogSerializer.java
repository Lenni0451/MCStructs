package net.lenni0451.mcstructs.dialog.serializer;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.dialog.Dialog;
import net.lenni0451.mcstructs.dialog.serializer.v1_21_6.DialogCodecs_v1_21_6;
import net.lenni0451.mcstructs.registry.EitherEntry;
import net.lenni0451.mcstructs.registry.NoOpRegistry;
import net.lenni0451.mcstructs.registry.TypedTagEntryList;

public abstract class DialogSerializer {

    /**
     * The dialog codec for 1.21.6.
     */
    public static final DialogSerializer V1_21_6 = new DialogCodecs_v1_21_6(new NoOpRegistry(Identifier.defaultNamespace("dialog")));
    /**
     * The latest dialog codec.
     */
    public static final DialogSerializer LATEST = V1_21_6;

    /**
     * Get the direct codec for dialogs.<br>
     * This codec does not perform registry lookups.
     *
     * @return The direct codec
     */
    public abstract Codec<Dialog> getDirectCodec();

    /**
     * Get the codec for dialogs that performs registry lookups.<br>
     * The result might either be a registry entry or a direct dialog object.
     * If it's a registry entry, it has to be resolved by the registry implementation.
     *
     * @return The registry codec
     */
    public abstract Codec<EitherEntry<Dialog>> getCodec();

    /**
     * Get the codec for a tag or list of dialogs.<br>
     * This codec performs registry lookups for the tag, a registry entry or a direct dialog object.
     *
     * @return The tag or entry list codec
     */
    public abstract Codec<TypedTagEntryList<Dialog>> getListCodec();

}
