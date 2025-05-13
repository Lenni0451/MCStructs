package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v1_21_5.NbtConverter_v1_21_5;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_22.ItemComponents_v1_22;
import net.lenni0451.mcstructs.itemcomponents.registry.TagEntryList;
import net.lenni0451.mcstructs.nbt.NbtTag;

import java.util.Arrays;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_22.Types_v1_22.AttributeModifier;
import static net.lenni0451.mcstructs.itemcomponents.impl.v1_22.Types_v1_22.Equippable;

public class Test_v1_22 extends ItemComponentTest<ItemComponents_v1_22> {

    @Override
    protected ItemComponents_v1_22 getRegistry() {
        return ItemComponentRegistry.V1_22;
    }

    @Override
    protected DataConverter<NbtTag> getConverter() {
        return NbtConverter_v1_21_5.INSTANCE;
    }

    @Override
    protected void register(ItemComponents_v1_22 registry) {
        register(registry.ATTRIBUTE_MODIFIERS, Arrays.asList(new AttributeModifier(registry.getRegistries().attributeModifier.getEntry(Identifier.of("test")), new Types_v1_21.AttributeModifier.EntityAttribute(Identifier.of("test"), 1, Types_v1_21.AttributeModifier.EntityAttribute.Operation.ADD_MULTIPLIED_TOTAL))));
        register(registry.EQUIPPABLE, new Equippable(Types_v1_21_2.EquipmentSlot.CHEST, registry.getRegistries().sound.getLeftEntry(Identifier.of("test")), Identifier.of("test"), Identifier.of("test2"), new TagEntryList(Arrays.asList(registry.getRegistries().entityType.getEntry(Identifier.of("test1")), registry.getRegistries().entityType.getEntry(Identifier.of("test2")))), false, false, false, true, registry.getRegistries().sound.getLeftEntry(Identifier.of("test"))));
    }

}
