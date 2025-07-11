package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.ItemComponents_v1_21_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.registry.TagEntryList;

import java.util.Arrays;
import java.util.Collections;

public class Test_v1_21_2 extends ItemComponentTest<ItemComponents_v1_21_2> {

    @Override
    protected ItemComponents_v1_21_2 getRegistry() {
        return ItemComponentRegistry.V1_21_2;
    }

    @Override
    protected DataConverter<NbtTag> getConverter() {
        return NbtConverter_v1_20_3.INSTANCE;
    }

    @Override
    protected void register(ItemComponents_v1_21_2 registry) {
        register(registry.ITEM_MODEL, Identifier.of("test"));
        register(registry.FOOD, new Types_v1_21_2.Food(123, 0.24F, false));
        register(registry.CONSUMABLE, new Types_v1_21_2.Consumable(1.87F, Types_v1_21_2.Consumable.ItemUseAnimation.BOW, registry.getRegistries().sound.getLeftEntry(Identifier.of("test")), false, Arrays.asList(new Types_v1_21_2.ConsumeEffect.ClearAllEffects(), new Types_v1_21_2.ConsumeEffect.RemoveEffects(new TagEntryList(registry.getRegistries().statusEffect.getTag(Identifier.of("test")))))));
        register(registry.USE_REMAINDER, new Types_v1_20_5.ItemStack(registry.getRegistries().item.getEntry(Identifier.of("test")), 123, registry.getItemDefaults()));
        register(registry.USE_COOLDOWN, new Types_v1_21_2.UseCooldown(1.23F, Identifier.of("test")));
        register(registry.DAMAGE_RESISTANT, new Types_v1_21_2.DamageResistant(registry.getRegistries().damageType.getTag(Identifier.of("test"))));
        register(registry.ENCHANTABLE, new Types_v1_21_2.Enchantable(123));
        register(registry.EQUIPPABLE, new Types_v1_21_2.Equippable(Types_v1_21_2.EquipmentSlot.CHEST, registry.getRegistries().sound.getLeftEntry(Identifier.of("test")), Identifier.of("test"), Identifier.of("test2"), new TagEntryList(Arrays.asList(registry.getRegistries().entityType.getEntry(Identifier.of("test1")), registry.getRegistries().entityType.getEntry(Identifier.of("test2")))), false, false, false));
        register(registry.REPAIRABLE, new Types_v1_21_2.Repairable(new TagEntryList(Arrays.asList(registry.getRegistries().item.getEntry(Identifier.of("test1")), registry.getRegistries().item.getEntry(Identifier.of("test2"))))));
        register(registry.GLIDER, true);
        register(registry.TOOLTIP_STYLE, Identifier.of("test"));
        register(registry.DEATH_PROTECTION, new Types_v1_21_2.DeathProtection(Arrays.asList(new Types_v1_21_2.ConsumeEffect.ClearAllEffects(), new Types_v1_21_2.ConsumeEffect.TeleportRandomly(10))));
        register(registry.LOCK, new Types_v1_21_2.ItemPredicate(new TagEntryList(registry.getRegistries().item.getTag(Identifier.of("test"))), new Types_v1_21_2.MinMaxInt(1, 10), Collections.emptyMap(), Collections.emptyMap()));
    }

}
