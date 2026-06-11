package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v26_2.NbtConverter_v26_2;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_2.ItemComponents_v26_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_2.Types_v26_2.ItemStackTemplate;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_2.Types_v26_2.SulfurCubeContent;
import net.lenni0451.mcstructs.nbt.NbtTag;

import java.util.Collections;

public class Test_v26_2 extends ItemComponentTest<ItemComponents_v26_2> {

    @Override
    protected ItemComponents_v26_2 getRegistry() {
        return ItemComponentRegistry.V26_2;
    }

    @Override
    protected DataConverter<NbtTag> getConverter() {
        return NbtConverter_v26_2.INSTANCE;
    }

    @Override
    protected void register(ItemComponents_v26_2 registry) {
        this.register(registry.CHARGED_PROJECTILES, Collections.singletonList(
                new ItemStackTemplate(registry.getRegistries().item.getEntry(Identifier.of("test")), 12, registry.getItemDefaults())
        ));
        this.register(registry.SULFUR_CUBE_CONTENT, new SulfurCubeContent(
                new ItemStackTemplate(registry.getRegistries().item.getEntry(Identifier.of("absorbed_block")), 1, registry.getItemDefaults())
        ));
    }

}
