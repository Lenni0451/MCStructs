package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v1_21_5.NbtConverter_v1_21_5;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.Types_v1_21_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_1.ItemComponents_v26_1;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.registry.TagEntryList;

import java.util.Arrays;
import java.util.Collections;

import static net.lenni0451.mcstructs.itemcomponents.impl.v26_1.Types_v26_1.BlocksAttacks;
import static net.lenni0451.mcstructs.itemcomponents.impl.v26_1.Types_v26_1.DamageResistant;

public class Test_v26_1 extends ItemComponentTest<ItemComponents_v26_1> {

    @Override
    protected ItemComponents_v26_1 getRegistry() {
        return ItemComponentRegistry.V26_1;
    }

    @Override
    protected DataConverter<NbtTag> getConverter() {
        return NbtConverter_v1_21_5.INSTANCE;
    }

    @Override
    protected void register(ItemComponents_v26_1 registry) {
        this.register(registry.ADDITIONAL_TRADE_COST, 13);
        this.register(registry.DYE, Types_v1_20_5.DyeColor.CYAN);
        this.register(registry.PROVIDES_BANNER_PATTERNS, new TagEntryList(Collections.singletonList(registry.getRegistries().bannerPattern.getEntry(Identifier.of("test")))));
        this.register(registry.BLOCKS_ATTACKS, new BlocksAttacks(12, 34, Arrays.asList(new Types_v1_21_5.BlocksAttacks.DamageReduction(12, new TagEntryList(registry.getRegistries().damageType.getTag(Identifier.of("test"))), 1, 2)), new Types_v1_21_5.BlocksAttacks.ItemDamageFunction(12, 34, 56), new TagEntryList(Collections.singletonList(registry.getRegistries().damageType.getEntry(Identifier.of("test")))), registry.getRegistries().sound.getHolder(Identifier.of("test")), registry.getRegistries().sound.getHolder(Identifier.of("test"))));
        this.register(registry.DAMAGE_RESISTANT, new DamageResistant(new TagEntryList(Collections.singletonList(registry.getRegistries().damageType.getEntry(Identifier.of("test"))))));
        this.register(registry.PIG_SOUND_VARIANT, registry.getRegistries().pigSoundVariant.getEntry(Identifier.of("test")));
        this.register(registry.COW_SOUND_VARIANT, registry.getRegistries().cowSoundVariant.getEntry(Identifier.of("test")));
        this.register(registry.CHICKEN_SOUND_VARIANT, registry.getRegistries().chickenSoundVariant.getEntry(Identifier.of("test")));
        this.register(registry.CAT_SOUND_VARIANT, registry.getRegistries().catSoundVariant.getEntry(Identifier.of("test")));
    }

}
