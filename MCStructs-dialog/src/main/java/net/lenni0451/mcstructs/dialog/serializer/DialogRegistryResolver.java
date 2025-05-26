package net.lenni0451.mcstructs.dialog.serializer;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.dialog.Dialog;

@FunctionalInterface
public interface DialogRegistryResolver {

    Dialog resolve(final Identifier identifier);

}
