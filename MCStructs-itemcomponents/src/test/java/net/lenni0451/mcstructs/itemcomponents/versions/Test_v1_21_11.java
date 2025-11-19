package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v1_21_5.NbtConverter_v1_21_5;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.ItemComponents_v1_21_11;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.registry.EitherEntry;
import net.lenni0451.mcstructs.registry.RegistryEntry;
import net.lenni0451.mcstructs.registry.TagEntryList;

import java.util.Arrays;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.Types_v1_21_11.*;

public class Test_v1_21_11 extends ItemComponentTest<ItemComponents_v1_21_11> {

    @Override
    protected ItemComponents_v1_21_11 getRegistry() {
        return ItemComponentRegistry.V1_21_11;
    }

    @Override
    protected DataConverter<NbtTag> getConverter() {
        return NbtConverter_v1_21_5.INSTANCE;
    }

    @Override
    protected void register(ItemComponents_v1_21_11 registry) {
        register(registry.USE_EFFECTS, new UseEffects(true, false, 1));
        register(registry.MINIMUM_ATTACK_CHARGE, 0.5F);
        register(registry.DAMAGE_TYPE, new EitherEntry<>(new DamageType("test", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 12, DamageEffects.HURT, DeathMessageType.INTENTIONAL_GAME_DESIGN)));
        register(registry.KINETIC_WEAPON, new KineticWeapon(5, 5, new KineticWeapon.Condition(13, 14, 15), new KineticWeapon.Condition(16, 17, 18), new KineticWeapon.Condition(19, 20, 21), 5, 6, new EitherEntry<>(new RegistryEntry(registry.getRegistries().sound, Identifier.of("test1"))), new EitherEntry<>(new RegistryEntry(registry.getRegistries().sound, Identifier.of("test2")))));
        register(registry.PIERCING_WEAPON, new PiercingWeapon(false, true, new EitherEntry<>(new RegistryEntry(registry.getRegistries().sound, Identifier.of("test1"))), new EitherEntry<>(new RegistryEntry(registry.getRegistries().sound, Identifier.of("test2")))));
        register(registry.SWING_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, 12));
        register(registry.CONSUMABLE, new Consumable(1.87F, Consumable.ItemUseAnimation.TRIDENT, registry.getRegistries().sound.getLeftEntry(Identifier.of("test")), false, Arrays.asList(new Types_v1_21_2.ConsumeEffect.ClearAllEffects(), new Types_v1_21_2.ConsumeEffect.RemoveEffects(new TagEntryList(registry.getRegistries().statusEffect.getTag(Identifier.of("test")))))));
        register(registry.ATTACK_RANGE, new AttackRange(1, 10, 0.75F, 2));
        register(registry.ZOMBIE_NAUTILUS_VARIANT, registry.getRegistries().zombieNautilusVariant.getEntry(Identifier.of("test")));
    }

}
