package net.lenni0451.mcstructs.itemcomponents.impl.v26_2;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_1.TypeSerializers_v26_1;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_2.Types_v26_2.ItemStackTemplate;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

public class TypeSerializers_v26_2 extends TypeSerializers_v26_1 {

    public TypeSerializers_v26_2(final ItemComponentRegistry registry, final TextComponentCodec textComponentCodec) {
        super(registry, textComponentCodec);
    }

    public Codec<ItemStackTemplate> itemStackTemplate() {
        return this.init("item_stack_template", () -> Codec.withAlternative(
                MapCodecMerger.codec(
                        this.registry.getRegistries().item.entryCodec().mapCodec(ItemStackTemplate.ID).required(), ItemStackTemplate::getId,
                        Codec.rangedInt(1, 99).mapCodec(ItemStackTemplate.COUNT).optional().elseGet(() -> 1), ItemStackTemplate::getCount,
                        this.registry.getMapCodec().mapCodec(ItemStackTemplate.COMPONENTS).optional().defaulted(ItemComponentMap::isEmpty, () -> new ItemComponentMap(this.registry)), ItemStackTemplate::getComponents,
                        ItemStackTemplate::new
                ),
                this.registry.getRegistries().item.entryCodec(),
                id -> new ItemStackTemplate(id, 1, new ItemComponentMap(this.registry))
        ));
    }

}
