package net.lenni0451.mcstructs.itemcomponents.impl.v1_21;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.MapCodec;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.TypeSerializers_v1_20_5;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.ItemStack;

public class TypeSerializers_v1_21 extends TypeSerializers_v1_20_5 {

    protected static final String SINGLE_ITEM_STACK = "single_item_stack";

    public TypeSerializers_v1_21(final ItemComponentRegistry registry) {
        super(registry);
    }

    @Override
    public Codec<CompoundTag> customData() {
        return this.init(CUSTOM_DATA, () -> Codec.oneOf(
                super.customData(),
                Codec.STRING.mapThrowing(SNbtSerializer.V1_14::serialize, SNbtSerializer.V1_14::deserialize)
        ));
    }

    public Codec<ItemStack> singleItemStack() {
        return this.init(SINGLE_ITEM_STACK, () -> MapCodec.of(
                Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().item).mapCodec(ItemStack.ID), ItemStack::getId,
                this.registry.getMapCodec().mapCodec(ItemStack.COMPONENTS).defaulted(() -> new ItemComponentMap(this.registry), ItemComponentMap::isEmpty), ItemStack::getComponents,
                (id, components) -> new ItemStack(id, 1, components)
        ));
    }

}