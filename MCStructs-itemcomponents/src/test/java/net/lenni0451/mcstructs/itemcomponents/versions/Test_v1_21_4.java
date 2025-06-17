package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.ItemComponents_v1_21_4;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.registry.TagEntryList;

import java.util.Arrays;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.Types_v1_21_4.CustomModelData;

public class Test_v1_21_4 extends ItemComponentTest<ItemComponents_v1_21_4> {

    @Override
    protected ItemComponents_v1_21_4 getRegistry() {
        return ItemComponentRegistry.V1_21_4;
    }

    @Override
    protected DataConverter<NbtTag> getConverter() {
        return NbtConverter_v1_20_3.INSTANCE;
    }

    @Override
    protected void register(ItemComponents_v1_21_4 registry) {
        register(registry.CUSTOM_MODEL_DATA, new CustomModelData(Arrays.asList(1F, 2F), Arrays.asList(true, false), Arrays.asList("test1", "test2"), Arrays.asList(0x123456, 0x789ABC)));
        register(registry.EQUIPPABLE, new Types_v1_21_2.Equippable(Types_v1_21_2.EquipmentSlot.CHEST, registry.getRegistries().sound.getLeftEntry(Identifier.of("test")), Identifier.of("test"), Identifier.of("test2"), new TagEntryList(Arrays.asList(registry.getRegistries().entityType.getEntry(Identifier.of("test1")), registry.getRegistries().entityType.getEntry(Identifier.of("test2")))), false, false, false));
    }

}
