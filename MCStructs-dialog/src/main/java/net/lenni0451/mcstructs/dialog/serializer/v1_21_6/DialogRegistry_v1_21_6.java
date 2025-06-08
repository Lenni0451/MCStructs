package net.lenni0451.mcstructs.dialog.serializer.v1_21_6;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.dialog.DialogType;
import net.lenni0451.mcstructs.registry.Registry;
import net.lenni0451.mcstructs.registry.RegistryEntry;
import net.lenni0451.mcstructs.registry.RegistryTag;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DialogRegistry_v1_21_6 extends Registry {

    private final List<Identifier> dialogs = new ArrayList<>();

    public DialogRegistry_v1_21_6() {
        super(Identifier.defaultNamespace("dialog"));
        this.dialogs.add(DialogType.NOTICE.getIdentifier());
        this.dialogs.add(DialogType.SERVER_LINKS.getIdentifier());
        this.dialogs.add(DialogType.DIALOG_LIST.getIdentifier());
        this.dialogs.add(DialogType.MULTI_ACTION.getIdentifier());
        this.dialogs.add(DialogType.CONFIRMATION.getIdentifier());
    }

    @Nullable
    @Override
    public RegistryEntry getEntry(Identifier id) {
        if (!this.dialogs.contains(id)) return null;
        return new RegistryEntry(this, id);
    }

    @Nullable
    @Override
    public RegistryEntry getEntry(int networkId) {
        if (networkId < 0 || networkId >= this.dialogs.size()) return null;
        return new RegistryEntry(this, this.dialogs.get(networkId));
    }

    @Nullable
    @Override
    public Integer getNetworkId(Identifier id) {
        int index = this.dialogs.indexOf(id);
        if (index == -1) return null;
        return index;
    }

    @Nullable
    @Override
    public Identifier getId(int networkId) {
        if (networkId < 0 || networkId >= this.dialogs.size()) return null;
        return this.dialogs.get(networkId);
    }

    @Nullable
    @Override
    public RegistryTag getTag(Identifier tag) {
        return new RegistryTag(this, tag);
    }

}
