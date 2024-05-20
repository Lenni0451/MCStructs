package net.lenni0451.mcstructs.itemcomponents.impl.v1_21;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.MapCodec;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.ItemComponents_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21.Food;

import java.util.ArrayList;
import java.util.List;

public class ItemComponents_v1_21 extends ItemComponents_v1_20_5 {

    private final TypeSerializers_v1_21 typeSerializers = new TypeSerializers_v1_21(this);

    public final ItemComponent<Food> FOOD = this.register("food", MapCodec.of(
            Codec.minInt(0).mapCodec(Food.NUTRITION), Food::getNutrition,
            Codec.FLOAT.mapCodec(Food.SATURATION), Food::getSaturation,
            Codec.BOOLEAN.mapCodec(Food.CAN_ALWAYS_EAT).optionalDefault(() -> false), Food::isCanAlwaysEat,
            Codec.minExclusiveFloat(0).mapCodec(Food.EAT_SECONDS).optionalDefault(() -> 1.6F), Food::getEatSeconds,
            this.typeSerializers.singleItemStack().mapCodec(Food.USING_CONVERTS_TO).optionalDefault(() -> null), Food::getUsingConvertsTo,
            MapCodec.of(
                    this.typeSerializers.statusEffect().mapCodec(Types_v1_20_5.Food.Effect.EFFECT), Types_v1_20_5.Food.Effect::getEffect,
                    Codec.rangedFloat(0, 1).mapCodec(Types_v1_20_5.Food.Effect.PROBABILITY).optionalDefault(() -> 1F), Types_v1_20_5.Food.Effect::getProbability,
                    Types_v1_20_5.Food.Effect::new
            ).listOf().mapCodec(Food.EFFECTS).defaulted(ArrayList::new, List::isEmpty), Food::getEffects,
            Food::new
    ));


    public ItemComponents_v1_21() {
    }

    public ItemComponents_v1_21(final RegistryVerifier registryVerifier) {
        super(registryVerifier);
    }

}