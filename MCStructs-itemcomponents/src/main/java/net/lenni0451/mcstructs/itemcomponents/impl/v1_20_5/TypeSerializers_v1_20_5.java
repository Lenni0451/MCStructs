package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.exceptions.InvalidTypeException;
import net.lenni0451.mcstructs.itemcomponents.impl.TypeSerializers;
import net.lenni0451.mcstructs.itemcomponents.serialization.BaseTypes;
import net.lenni0451.mcstructs.itemcomponents.serialization.interfaces.MergedComponentSerializer;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.HashMap;
import java.util.Map;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.*;

class TypeSerializers_v1_20_5 extends TypeSerializers {

    public final MergedComponentSerializer<ATextComponent> TEXT_COMPONENT = BaseTypes.STRING.map(TextComponentCodec.V1_20_5::serializeJsonString, TextComponentCodec.V1_20_5::deserializeJson);
    public final MergedComponentSerializer<ItemStack> ITEM_STACK = new MergedComponentSerializer<ItemStack>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, ItemStack component) {
            T map = converter.emptyMap();
            converter.put(map, ItemStack.ID, BaseTypes.IDENTIFIER.serialize(converter, component.getId()));
            converter.put(map, ItemStack.COUNT, BaseTypes.INTEGER.serialize(converter, component.getCount()));
            if (!component.getComponents().isEmpty()) converter.put(map, ItemStack.COMPONENTS, registry.mapTo(converter, component.getComponents()));
            return map;
        }

        @Override
        public <T> ItemStack deserialize(DataConverter<T> converter, T data) {
            Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
            return new ItemStack(
                    BaseTypes.IDENTIFIER.fromMap(converter, map, ItemStack.ID),
                    BaseTypes.INTEGER.fromMap(converter, map, ItemStack.COUNT, 1),
                    map.containsKey(ItemStack.COMPONENTS) ? registry.mapFrom(converter, map.get(ItemStack.COMPONENTS)) : new ItemComponentMap(registry)
            );
        }
    }.withVerifier(stack -> {
        this.registry.getRegistryVerifier().item.verify(stack.getId());
        if (stack.getCount() < 1) throw new IllegalArgumentException("Item count must be at least 1");
    });
    public final MergedComponentSerializer<CompoundTag> COMPOUND_TAG = new MergedComponentSerializer<CompoundTag>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, CompoundTag component) {
            return NbtConverter_v1_20_3.INSTANCE.convertTo(converter, component);
        }

        @Override
        public <T> CompoundTag deserialize(DataConverter<T> converter, T data) {
            INbtTag tag = converter.convertTo(NbtConverter_v1_20_3.INSTANCE, data);
            if (!tag.isCompoundTag()) throw InvalidTypeException.of(tag, CompoundTag.class);
            return tag.asCompoundTag();
        }
    };
    public final MergedComponentSerializer<DyeColor> DYE_COLOR = BaseTypes.named(DyeColor.values());
    public final MergedComponentSerializer<BannerPattern.Pattern> BANNER_PATTERN_PATTERN = BaseTypes.oneOf(
            new MergedComponentSerializer<BannerPattern.Pattern>() {
                @Override
                public <T> T serialize(DataConverter<T> converter, BannerPattern.Pattern component) {
                    T map = converter.emptyMap();
                    converter.put(map, BannerPattern.Pattern.ASSET_ID, BaseTypes.IDENTIFIER.serialize(converter, component.getAssetId()));
                    converter.put(map, BannerPattern.Pattern.TRANSLATION_KEY, BaseTypes.STRING.serialize(converter, component.getTranslationKey()));
                    return map;
                }

                @Override
                public <T> BannerPattern.Pattern deserialize(DataConverter<T> converter, T data) {
                    Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
                    return new BannerPattern.Pattern(
                            BaseTypes.IDENTIFIER.fromMap(converter, map, BannerPattern.Pattern.ASSET_ID),
                            BaseTypes.STRING.fromMap(converter, map, BannerPattern.Pattern.TRANSLATION_KEY)
                    );
                }
            },
            BaseTypes.IDENTIFIER.map(BannerPattern.Pattern::getAssetId, id -> new BannerPattern.Pattern(id, "" /*TODO: Translation key?*/))
    ).withVerifier(this.registry.getRegistryVerifier().bannerPattern.map(BannerPattern.Pattern::getAssetId));
    public final MergedComponentSerializer<FireworkExplosions.ExplosionShape> FIREWORK_EXPLOSIONS_EXPLOSION_SHAPE = BaseTypes.named(FireworkExplosions.ExplosionShape.values());
    public final MergedComponentSerializer<WritableBook.Page> WRITABLE_BOOK_PAGE = BaseTypes.oneOf(
            new MergedComponentSerializer<WritableBook.Page>() {
                @Override
                public <T> T serialize(DataConverter<T> converter, WritableBook.Page component) {
                    T map = converter.emptyMap();
                    converter.put(map, "raw", BaseTypes.STRING.serialize(converter, component.getRaw()));
                    if (component.getFiltered() != null) converter.put(map, "filtered", BaseTypes.STRING.serialize(converter, component.getFiltered()));
                    return map;
                }

                @Override
                public <T> WritableBook.Page deserialize(DataConverter<T> converter, T data) {
                    Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
                    return new WritableBook.Page(
                            BaseTypes.STRING.fromMap(converter, map, "raw"),
                            BaseTypes.STRING.fromMap(converter, map, "filtered", null)
                    );
                }
            },
            BaseTypes.STRING.map(WritableBook.Page::getRaw, raw -> new WritableBook.Page(raw, null))
    );
    public final MergedComponentSerializer<Map<Identifier, Integer>> ENCHANTMENT_LEVELS = BaseTypes.INTEGER.stringMapOf().map(map -> {
        Map<String, Integer> out = new HashMap<>();
        for (Map.Entry<Identifier, Integer> entry : map.entrySet()) {
            registry.getRegistryVerifier().enchantment.verify(entry.getKey());
            out.put(entry.getKey().get(), entry.getValue());
        }
        return out;
    }, map -> {
        Map<Identifier, Integer> out = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            Identifier id = Identifier.of(entry.getKey());
            registry.getRegistryVerifier().enchantment.verify(id);
            out.put(id, entry.getValue());
        }
        return out;
    });
    public final MergedComponentSerializer<ContainerSlot> CONTAINER_SLOT = new MergedComponentSerializer<ContainerSlot>() {
        private final MergedComponentSerializer<Integer> slot = BaseTypes.INTEGER.withVerifier(i -> {
            if (i < 0) throw new IllegalArgumentException("Slot must be at least 0");
            if (i > 255) throw new IllegalArgumentException("Slot must be at most 255");
        });

        @Override
        public <T> T serialize(DataConverter<T> converter, ContainerSlot component) {
            T map = converter.emptyMap();
            converter.put(map, ContainerSlot.SLOT, this.slot.serialize(converter, component.getSlot()));
            converter.put(map, ContainerSlot.ITEM, TypeSerializers_v1_20_5.this.ITEM_STACK.serialize(converter, component.getItem()));
            return map;
        }

        @Override
        public <T> ContainerSlot deserialize(DataConverter<T> converter, T data) {
            Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
            return new ContainerSlot(
                    this.slot.fromMap(converter, map, ContainerSlot.SLOT),
                    TypeSerializers_v1_20_5.this.ITEM_STACK.fromMap(converter, map, ContainerSlot.ITEM)
            );
        }
    };

    public TypeSerializers_v1_20_5(final ItemComponentRegistry registry) {
        super(registry);
    }

}
