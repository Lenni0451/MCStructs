package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.ItemComponents_v1_21;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21;
import net.lenni0451.mcstructs.nbt.NbtTag;

import java.util.Collections;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21.AttributeModifier;
import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21.AttributeModifiers;

public class Test_v1_21 extends ItemComponentTest<ItemComponents_v1_21> {

    @Override
    protected ItemComponents_v1_21 getRegistry() {
        return ItemComponentRegistry.V1_21;
    }

    @Override
    protected DataConverter<NbtTag> getConverter() {
        return NbtConverter_v1_20_3.INSTANCE;
    }

    @Override
    protected void register(ItemComponents_v1_21 registry) {
        register(registry.ATTRIBUTE_MODIFIERS, new AttributeModifiers(Collections.singletonList(new AttributeModifier(registry.getRegistries().attributeModifier.getEntry(Identifier.of("attr")), new AttributeModifier.EntityAttribute(Identifier.of("te", "st"), 12.34, AttributeModifier.EntityAttribute.Operation.ADD_MULTIPLIED_TOTAL))), false));
        register(registry.FOOD, new Types_v1_21.Food(10, 20, true, 14, new Types_v1_20_5.ItemStack(registry.getRegistries().item.getEntry(Identifier.of("test", "test")), 63, new ItemComponentMap(registry)), Collections.singletonList(new Types_v1_20_5.Food.Effect(new Types_v1_20_5.StatusEffect(registry.getRegistries().statusEffect.getEntry(Identifier.of("test")), 123, 456, true, true, false, null), 0.5F))));
        register(registry.JUKEBOX_PLAYABLE, new Types_v1_21.JukeboxPlayable(registry.getRegistries().jukeboxSong.getHolder(Identifier.of("test")), false));
    }

}
