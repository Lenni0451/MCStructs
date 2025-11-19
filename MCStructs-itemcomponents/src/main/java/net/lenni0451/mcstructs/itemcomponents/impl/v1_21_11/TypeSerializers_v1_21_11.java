package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9.TypeSerializers_v1_21_9;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.Types_v1_21_11.KineticWeapon;

public class TypeSerializers_v1_21_11 extends TypeSerializers_v1_21_9 {

    protected static final String KINETIC_WEAPON_CONDITION = "kinetic_weapon_condition";

    public TypeSerializers_v1_21_11(final ItemComponentRegistry registry, final TextComponentCodec textComponentCodec) {
        super(registry, textComponentCodec);
    }

    public Codec<KineticWeapon.Condition> kineticWeaponCondition() {
        return this.init(KINETIC_WEAPON_CONDITION, () -> MapCodecMerger.codec(
                Codec.minInt(0).mapCodec(KineticWeapon.Condition.MAX_DURATION_TICKS).required(), KineticWeapon.Condition::getMaxDurationTicks,
                Codec.FLOAT.mapCodec(KineticWeapon.Condition.MIN_SPEED).optional().defaulted(0F), KineticWeapon.Condition::getMinSpeed,
                Codec.FLOAT.mapCodec(KineticWeapon.Condition.MIN_RELATIVE_SPEED).optional().defaulted(0F), KineticWeapon.Condition::getMinRelativeSpeed,
                KineticWeapon.Condition::new
        ));
    }

}
