package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.Result;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.Either;
import net.lenni0451.mcstructs.converter.codec.MapCodec;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.TypeSerializers;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.StringTag;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.Map;
import java.util.function.Function;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.*;

class TypeSerializers_v1_20_5 extends TypeSerializers {

    public final Codec<ItemStack> ITEM_STACK = MapCodec.of(
            Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().item).mapCodec(ItemStack.ID), ItemStack::getId,
            Codec.rangedInt(1, Integer.MAX_VALUE).mapCodec(ItemStack.COUNT).requiredDefault(() -> 1), ItemStack::getCount,
            this.registry.getMapCodec().mapCodec(ItemStack.COMPONENTS).defaulted(() -> new ItemComponentMap(this.registry), ItemComponentMap::isEmpty), ItemStack::getComponents,
            ItemStack::new
    );
    public final Codec<CompoundTag> COMPOUND_TAG = new Codec<CompoundTag>() {
        @Override
        public <T> Result<T> serialize(DataConverter<T> converter, CompoundTag component) {
            return Result.success(NbtConverter_v1_20_3.INSTANCE.convertTo(converter, component));
        }

        @Override
        public <T> Result<CompoundTag> deserialize(DataConverter<T> converter, T data) {
            INbtTag tag = converter.convertTo(NbtConverter_v1_20_3.INSTANCE, data);
            if (!tag.isCompoundTag()) return Result.unexpected(tag, CompoundTag.class);
            return Result.success(tag.asCompoundTag());
        }
    };
    public final Codec<ATextComponent> RAW_TEXT_COMPONENT = new Codec<ATextComponent>() {
        @Override
        public <S> Result<S> serialize(DataConverter<S> converter, ATextComponent element) {
            S test = converter.createString("");
            if (test instanceof StringTag) {
                try {
                    return Result.success((S) TextComponentCodec.V1_20_5.serializeNbt(element));
                } catch (Throwable t) {
                    return Result.error(t);
                }
            } else if (test instanceof JsonPrimitive) {
                try {
                    return Result.success((S) TextComponentCodec.V1_20_5.serializeJsonTree(element));
                } catch (Throwable t) {
                    return Result.error(t);
                }
            } else {
                return Result.error("Unsupported data type: " + test.getClass().getName());
            }
        }

        @Override
        public <S> Result<ATextComponent> deserialize(DataConverter<S> converter, S data) {
            if (data instanceof INbtTag) {
                try {
                    return Result.success(TextComponentCodec.V1_20_5.deserializeNbtTree((INbtTag) data));
                } catch (Throwable t) {
                    return Result.error(t);
                }
            } else if (data instanceof JsonElement) {
                try {
                    return Result.success(TextComponentCodec.V1_20_5.deserializeJsonTree((JsonElement) data));
                } catch (Throwable t) {
                    return Result.error(t);
                }
            } else {
                return Result.error("Unsupported data type: " + data.getClass().getName());
            }
        }
    };
    public final Codec<DyeColor> DYE_COLOR = Codec.named(DyeColor.values());
    public final Codec<Either<Identifier, BannerPattern.Pattern>> BANNER_PATTERN_PATTERN = this.registryEntry(
            this.registry.getRegistryVerifier().bannerPattern,
            MapCodec.of(
                    Codec.STRING_IDENTIFIER.mapCodec(BannerPattern.Pattern.ASSET_ID), BannerPattern.Pattern::getAssetId,
                    Codec.STRING.mapCodec(BannerPattern.Pattern.TRANSLATION_KEY), BannerPattern.Pattern::getTranslationKey,
                    BannerPattern.Pattern::new
            )
    );
    public final Codec<Map<Identifier, Integer>> ENCHANTMENT_LEVELS = Codec.mapOf(Codec.STRING_IDENTIFIER.verified(registry.getRegistryVerifier().enchantment), Codec.INTEGER);
    public final Codec<ContainerSlot> CONTAINER_SLOT = MapCodec.of(
            Codec.rangedInt(0, 255).mapCodec(ContainerSlot.SLOT), ContainerSlot::getSlot,
            this.ITEM_STACK.mapCodec(ContainerSlot.ITEM), ContainerSlot::getItem,
            ContainerSlot::new
    );
    public final Codec<String> PLAYER_NAME = Codec.STRING.verified(name -> {
        if (name.length() > 16) return Result.error("Player name is too long (max. 16 characters)");
        if (name.chars().filter(c -> c <= 32 || c >= 127).findAny().isPresent()) return Result.error("Player name contains invalid characters");
        return Result.success(null);
    });
    public final Codec<BlockPos> BLOCK_POS = Codec.INT_ARRAY.verified(array -> {
        if (array.length != 3) return Result.error("BlockPos array must have a length of 3");
        return Result.success(null);
    }).map(pos -> new int[]{pos.getX(), pos.getY(), pos.getZ()}, array -> new BlockPos(array[0], array[1], array[2]));
    public final Codec<Either<Identifier, SoundEvent>> SOUND_EVENT = this.registryEntry(
            this.registry.getRegistryVerifier().sound,
            MapCodec.of(
                    Codec.STRING_IDENTIFIER.mapCodec(SoundEvent.SOUND_ID), SoundEvent::getSoundId,
                    Codec.FLOAT.mapCodec(SoundEvent.RANGE).lenient().optionalDefault(() -> 16F), SoundEvent::getRange,
                    SoundEvent::new
            )
    );
    public final Codec<ValueMatcher> VALUE_MATCHER = Codec.oneOf(
            Codec.STRING.verified(s -> {
                if (s == null) return Result.error("Value matcher cannot be null");
                return Result.success(null);
            }).map(ValueMatcher::getValue, ValueMatcher::new),
            MapCodec.of(
                    Codec.STRING.mapCodec(ValueMatcher.MIN).optionalDefault(() -> null), ValueMatcher::getMin,
                    Codec.STRING.mapCodec(ValueMatcher.MAX).optionalDefault(() -> null), ValueMatcher::getMax,
                    ValueMatcher::new
            )
    );
    public final Codec<Map<String, ValueMatcher>> CONDITION_LIST = Codec.mapOf(Codec.STRING, this.VALUE_MATCHER);
    public final Codec<BlockPredicate> BLOCK_PREDICATE = MapCodec.of(
            Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().block).optionalListOf().mapCodec(BlockPredicate.BLOCKS).optionalDefault(() -> null), BlockPredicate::getBlocks,
            this.CONDITION_LIST.mapCodec(BlockPredicate.STATE).optionalDefault(() -> null), BlockPredicate::getState,
            this.COMPOUND_TAG.mapCodec(BlockPredicate.NBT).optionalDefault(() -> null), BlockPredicate::getNbt,
            BlockPredicate::new
    );
    public final Codec<EntityAttributeModifier> ENTITY_ATTRIBUTE_MODIFIER = MapCodec.of(
            Codec.INT_ARRAY_UUID.mapCodec(EntityAttributeModifier.UUID), EntityAttributeModifier::getUuid,
            Codec.STRING.mapCodec(EntityAttributeModifier.NAME), EntityAttributeModifier::getName,
            Codec.DOUBLE.mapCodec(EntityAttributeModifier.AMOUNT), EntityAttributeModifier::getAmount,
            Codec.named(EntityAttributeModifier.Operation.values()).mapCodec(EntityAttributeModifier.OPERATION), EntityAttributeModifier::getOperation,
            EntityAttributeModifier::new
    );
    public final Codec<AttributeModifier> ATTRIBUTE_MODIFIER = MapCodec.of(
            Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().attributeModifier).mapCodec(AttributeModifier.TYPE), AttributeModifier::getType,
            this.ENTITY_ATTRIBUTE_MODIFIER.mapCodec(), AttributeModifier::getModifier,
            Codec.named(AttributeModifier.Slot.values()).mapCodec(AttributeModifier.SLOT).optionalDefault(() -> AttributeModifier.Slot.ANY), AttributeModifier::getSlot,
            AttributeModifier::new
    );
    public final Codec<Either<Identifier, ArmorTrimMaterial>> ARMOR_TRIM_MATERIAL = this.registryEntry(
            this.registry.getRegistryVerifier().armorTrimMaterial,
            MapCodec.of(
                    Codec.STRING.verified(s -> {
                        if (s.matches(Identifier.VALID_VALUE_CHARS)) return Result.error("Invalid armor trim material: " + s);
                        return Result.success(null);
                    }).mapCodec(ArmorTrimMaterial.ASSET_NAME), ArmorTrimMaterial::getAssetName,
                    Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().item).mapCodec(ArmorTrimMaterial.INGREDIENT), ArmorTrimMaterial::getIngredient,
                    Codec.FLOAT.mapCodec(ArmorTrimMaterial.ITEM_MODEL_INDEX), ArmorTrimMaterial::getItemModelIndex,
                    Codec.mapOf(
                            Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().armorMaterial),
                            Codec.STRING
                    ).mapCodec(ArmorTrimMaterial.OVERRIDE_ARMOR_MATERIALS), ArmorTrimMaterial::getOverrideArmorMaterials,
                    this.RAW_TEXT_COMPONENT.mapCodec(ArmorTrimMaterial.DESCRIPTION), ArmorTrimMaterial::getDescription,
                    ArmorTrimMaterial::new
            )
    );
    public final Codec<Either<Identifier, ArmorTrimPattern>> ARMOR_TRIM_PATTERN = this.registryEntry(
            this.registry.getRegistryVerifier().armorTrimPattern,
            MapCodec.of(
                    Codec.STRING_IDENTIFIER.mapCodec(ArmorTrimPattern.ASSET_ID), ArmorTrimPattern::getAssetId,
                    Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().item).mapCodec(ArmorTrimPattern.TEMPLATE_ITEM), ArmorTrimPattern::getTemplateItem,
                    this.RAW_TEXT_COMPONENT.mapCodec(ArmorTrimPattern.DESCRIPTION), ArmorTrimPattern::getDescription,
                    Codec.BOOLEAN.mapCodec(ArmorTrimPattern.DECAL).optionalDefault(() -> false), ArmorTrimPattern::isDecal,
                    ArmorTrimPattern::new
            )
    );

    public TypeSerializers_v1_20_5(final ItemComponentRegistry registry) {
        super(registry);
    }

    public Codec<ATextComponent> textComponent(final int maxLength) {
        return Codec.sizedString(0, maxLength).mapThrowing(TextComponentCodec.V1_20_5::serializeJsonString, TextComponentCodec.V1_20_5::deserializeJson);
    }

    public <T> Codec<RawFilteredPair<T>> rawFilteredPair(final Codec<T> codec) {
        return Codec.oneOf(
                MapCodec.of(
                        codec.mapCodec(RawFilteredPair.RAW), RawFilteredPair::getRaw,
                        codec.mapCodec(RawFilteredPair.FILTERED).optionalDefault(() -> null), RawFilteredPair::getFiltered,
                        RawFilteredPair::new
                ),
                codec.map(RawFilteredPair::getRaw, raw -> new RawFilteredPair<>(raw, null))
        );
    }

    public <T> Codec<Either<Identifier, T>> registryEntry(final Function<Identifier, Result<Void>> idVerifier, final Codec<T> entryCodec) {
        return Codec.either(Codec.STRING_IDENTIFIER.verified(idVerifier), entryCodec);
    }

}
