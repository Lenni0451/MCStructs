package net.lenni0451.mcstructs.dialog.serializer;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.dialog.serializer.v1_21_6.DialogCodecs_v1_21_6;
import net.lenni0451.mcstructs.registry.NoOpRegistry;

public class DialogSerializer {

    public static final DialogSerializer V1_21_6 = new DialogCodecs_v1_21_6(new NoOpRegistry(Identifier.defaultNamespace("dialog")));
    public static final DialogSerializer LATEST = V1_21_6;

}
