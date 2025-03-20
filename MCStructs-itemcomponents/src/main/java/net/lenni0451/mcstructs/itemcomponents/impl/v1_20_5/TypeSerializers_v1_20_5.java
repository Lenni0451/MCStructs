package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.TypeSerializers;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.StringTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.Map;
import java.util.function.Function;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.*;

public class TypeSerializers_v1_20_5 extends TypeSerializers {

    protected static final String CUSTOM_DATA = "custom_data";
    protected static final String STRING_OR_RAW_COMPOUND_TAG = "string_or_raw_compound_tag";
    protected static final String ITEM_STACK = "item_stack";
    protected static final String BLOCK_POS = "block_pos";
    protected static final String RAW_TEXT_COMPONENT = "raw_text_component";
    protected static final String TEXT_COMPONENT = "text_component";
    protected static final String PLAYER_NAME = "player_name";
    protected static final String DYE_COLOR = "dye_color";
    protected static final String ENCHANTMENT_LEVELS = "enchantment_levels";
    protected static final String SOUND_EVENT = "sound_event";
    protected static final String BLOCK_PREDICATE = "block_predicate";
    protected static final String ATTRIBUTE_MODIFIER = "attribute_modifier";
    protected static final String ARMOR_TRIM_MATERIAL = "armor_trim_material";
    protected static final String ARMOR_TRIM_PATTERN = "armor_trim_pattern";
    protected static final String STATUS_EFFECT = "status_effect";

    public TypeSerializers_v1_20_5(final ItemComponentRegistry registry, final TextComponentCodec textComponentCodec) {
        super(registry, textComponentCodec);
    }

    public Codec<CompoundTag> customData() {
        return this.init(CUSTOM_DATA, () -> new Codec<CompoundTag>() {
            @Override
            public <T> Result<T> serialize(DataConverter<T> converter, CompoundTag component) {
                return Result.success(NbtConverter_v1_20_3.INSTANCE.convertTo(converter, component));
            }

            @Override
            public <T> Result<CompoundTag> deserialize(DataConverter<T> converter, T data) {
                NbtTag tag = converter.convertTo(NbtConverter_v1_20_3.INSTANCE, data);
                if (!tag.isCompoundTag()) return Result.unexpected(tag, CompoundTag.class);
                return Result.success(tag.asCompoundTag());
            }
        });
    }

    public Codec<CompoundTag> stringOrRawCompoundTag() {
        return this.init(STRING_OR_RAW_COMPOUND_TAG, () -> Codec.oneOf(
                Codec.STRING.mapThrowing(SNbt.V1_14::serialize, SNbt.V1_14::deserialize),
                new Codec<CompoundTag>() {
                    @Override
                    public <T> Result<T> serialize(DataConverter<T> converter, CompoundTag component) {
                        return Result.success(NbtConverter_v1_20_3.INSTANCE.convertTo(converter, component));
                    }

                    @Override
                    public <T> Result<CompoundTag> deserialize(DataConverter<T> converter, T data) {
                        NbtTag tag = converter.convertTo(NbtConverter_v1_20_3.INSTANCE, data);
                        if (!tag.isCompoundTag()) return Result.unexpected(tag, CompoundTag.class);
                        return Result.success(tag.asCompoundTag());
                    }
                }
        ));
    }

    public Codec<ItemStack> itemStack() {
        return this.init(ITEM_STACK, () -> MapCodecMerger.codec(
                Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().item).mapCodec(ItemStack.ID).required(), ItemStack::getId,
                Codec.rangedInt(1, Integer.MAX_VALUE).mapCodec(ItemStack.COUNT).optional().elseGet(() -> 1), ItemStack::getCount,
                this.registry.getMapCodec().mapCodec(ItemStack.COMPONENTS).optional().defaulted(ItemComponentMap::isEmpty, () -> new ItemComponentMap(this.registry)), ItemStack::getComponents,
                ItemStack::new
        ));
    }

    public Codec<BlockPos> blockPos() {
        return this.init(BLOCK_POS, () -> Codec.INT_ARRAY.verified(array -> {
            if (array.length != 3) return Result.error("BlockPos array must have a length of 3");
            return Result.success(null);
        }).map(pos -> new int[]{pos.getX(), pos.getY(), pos.getZ()}, array -> new BlockPos(array[0], array[1], array[2])));
    }

    public Codec<TextComponent> rawTextComponent() {
        return this.init(RAW_TEXT_COMPONENT, () -> new Codec<TextComponent>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, TextComponent element) {
                S test = converter.createString("");
                if (test instanceof StringTag) {
                    try {
                        return Result.success((S) TypeSerializers_v1_20_5.this.textComponentCodec.serializeNbtTree(element));
                    } catch (Throwable t) {
                        return Result.error(t);
                    }
                } else if (test instanceof JsonPrimitive) {
                    try {
                        return Result.success((S) TypeSerializers_v1_20_5.this.textComponentCodec.serializeJsonTree(element));
                    } catch (Throwable t) {
                        return Result.error(t);
                    }
                } else {
                    return Result.error("Unsupported data type: " + test.getClass().getName());
                }
            }

            @Override
            public <S> Result<TextComponent> deserialize(DataConverter<S> converter, S data) {
                if (data instanceof NbtTag) {
                    try {
                        return Result.success(TypeSerializers_v1_20_5.this.textComponentCodec.deserializeNbtTree((NbtTag) data));
                    } catch (Throwable t) {
                        return Result.error(t);
                    }
                } else if (data instanceof JsonElement) {
                    try {
                        return Result.success(TypeSerializers_v1_20_5.this.textComponentCodec.deserializeJsonTree((JsonElement) data));
                    } catch (Throwable t) {
                        return Result.error(t);
                    }
                } else {
                    return Result.error("Unsupported data type: " + data.getClass().getName());
                }
            }
        });
    }

    public Codec<TextComponent> textComponent(final int maxLength) {
        return Codec.sizedString(0, maxLength).mapThrowing(TypeSerializers_v1_20_5.this.textComponentCodec::serializeJsonString, TypeSerializers_v1_20_5.this.textComponentCodec::deserializeJson);
    }

    public Codec<String> playerName() {
        return this.init(PLAYER_NAME, () -> Codec.STRING.verified(name -> {
            if (name.length() > 16) return Result.error("Player name is too long (max. 16 characters)");
            if (name.chars().filter(c -> c <= 32 || c >= 127).findAny().isPresent()) return Result.error("Player name contains invalid characters");
            return Result.success(null);
        }));
    }

    public Codec<DyeColor> dyeColor() {
        return this.init(DYE_COLOR, () -> Codec.named(DyeColor.values()));
    }

    public Codec<Map<Identifier, Integer>> enchantmentLevels() {
        return this.init(ENCHANTMENT_LEVELS, () -> Codec.mapOf(Codec.STRING_IDENTIFIER.verified(registry.getRegistryVerifier().enchantment), Codec.INTEGER));
    }

    public Codec<Either<Identifier, SoundEvent>> soundEvent() {
        return this.init(SOUND_EVENT, () -> this.registryEntry(
                this.registry.getRegistryVerifier().sound,
                MapCodecMerger.codec(
                        Codec.STRING_IDENTIFIER.mapCodec(SoundEvent.SOUND_ID).required(), SoundEvent::getSoundId,
                        Codec.FLOAT.mapCodec(SoundEvent.RANGE).optional().lenient().defaulted(16F), SoundEvent::getRange,
                        SoundEvent::new
                )
        ));
    }

    public Codec<BlockPredicate> blockPredicate() {
        return this.init(BLOCK_PREDICATE, () -> MapCodecMerger.codec(
                this.tagEntryList(this.registry.getRegistryVerifier().blockTag, this.registry.getRegistryVerifier().block).mapCodec(BlockPredicate.BLOCKS).optional().defaulted(null), BlockPredicate::getBlocks,
                Codec.mapOf(Codec.STRING, Codec.oneOf(
                        Codec.STRING.verified(s -> {
                            if (s == null) return Result.error("Value matcher cannot be null");
                            return Result.success(null);
                        }).map(BlockPredicate.ValueMatcher::getValue, BlockPredicate.ValueMatcher::new),
                        MapCodecMerger.codec(
                                Codec.STRING.mapCodec(BlockPredicate.ValueMatcher.MIN).optional().defaulted(null), BlockPredicate.ValueMatcher::getMin,
                                Codec.STRING.mapCodec(BlockPredicate.ValueMatcher.MAX).optional().defaulted(null), BlockPredicate.ValueMatcher::getMax,
                                BlockPredicate.ValueMatcher::new
                        )
                )).mapCodec(BlockPredicate.STATE).optional().defaulted(null), BlockPredicate::getState,
                this.stringOrRawCompoundTag().mapCodec(BlockPredicate.NBT).optional().defaulted(null), BlockPredicate::getNbt,
                BlockPredicate::new
        ));
    }

    public Codec<AttributeModifier> attributeModifier() {
        return this.init(ATTRIBUTE_MODIFIER, () -> MapCodecMerger.codec(
                Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().attributeModifier).mapCodec(AttributeModifier.TYPE).required(), AttributeModifier::getType,
                MapCodecMerger.mapCodec(
                        Codec.INT_ARRAY_UUID.mapCodec(AttributeModifier.EntityAttribute.UUID).required(), AttributeModifier.EntityAttribute::getUuid,
                        Codec.STRING.mapCodec(AttributeModifier.EntityAttribute.NAME).required(), AttributeModifier.EntityAttribute::getName,
                        Codec.DOUBLE.mapCodec(AttributeModifier.EntityAttribute.AMOUNT).required(), AttributeModifier.EntityAttribute::getAmount,
                        Codec.named(AttributeModifier.EntityAttribute.Operation.values()).mapCodec(AttributeModifier.EntityAttribute.OPERATION).required(), AttributeModifier.EntityAttribute::getOperation,
                        AttributeModifier.EntityAttribute::new
                ), AttributeModifier::getModifier,
                Codec.named(AttributeModifier.Slot.values()).mapCodec(AttributeModifier.SLOT).optional().defaulted(AttributeModifier.Slot.ANY), AttributeModifier::getSlot,
                AttributeModifier::new
        ));
    }

    public Codec<Either<Identifier, ArmorTrimMaterial>> armorTrimMaterial() {
        return this.init(ARMOR_TRIM_MATERIAL, () -> this.registryEntry(
                this.registry.getRegistryVerifier().armorTrimMaterial,
                MapCodecMerger.codec(
                        Codec.STRING.verified(s -> {
                            if (s.matches(Identifier.VALID_VALUE_CHARS)) return Result.error("Invalid armor trim material: " + s);
                            return Result.success(null);
                        }).mapCodec(ArmorTrimMaterial.ASSET_NAME).required(), ArmorTrimMaterial::getAssetName,
                        Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().item).mapCodec(ArmorTrimMaterial.INGREDIENT).required(), ArmorTrimMaterial::getIngredient,
                        Codec.FLOAT.mapCodec(ArmorTrimMaterial.ITEM_MODEL_INDEX).required(), ArmorTrimMaterial::getItemModelIndex,
                        Codec.mapOf(
                                Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().armorMaterial),
                                Codec.STRING
                        ).mapCodec(ArmorTrimMaterial.OVERRIDE_ARMOR_MATERIALS).required(), ArmorTrimMaterial::getOverrideArmorMaterials,
                        this.rawTextComponent().mapCodec(ArmorTrimMaterial.DESCRIPTION).required(), ArmorTrimMaterial::getDescription,
                        ArmorTrimMaterial::new
                )
        ));
    }

    public Codec<Either<Identifier, ArmorTrimPattern>> armorTrimPattern() {
        return this.init(ARMOR_TRIM_PATTERN, () -> this.registryEntry(
                this.registry.getRegistryVerifier().armorTrimPattern,
                MapCodecMerger.codec(
                        Codec.STRING_IDENTIFIER.mapCodec(ArmorTrimPattern.ASSET_ID).required(), ArmorTrimPattern::getAssetId,
                        Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().item).mapCodec(ArmorTrimPattern.TEMPLATE_ITEM).required(), ArmorTrimPattern::getTemplateItem,
                        this.rawTextComponent().mapCodec(ArmorTrimPattern.DESCRIPTION).required(), ArmorTrimPattern::getDescription,
                        Codec.BOOLEAN.mapCodec(ArmorTrimPattern.DECAL).optional().defaulted(false), ArmorTrimPattern::isDecal,
                        ArmorTrimPattern::new
                )
        ));
    }

    public Codec<StatusEffect> statusEffect() {
        return this.init(STATUS_EFFECT, () -> MapCodecMerger.codec(
                Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().statusEffect).mapCodec(StatusEffect.ID).required(), StatusEffect::getId,
                MapCodec.recursive(thiz -> MapCodecMerger.mapCodec(
                        Codec.UNSIGNED_BYTE.mapCodec(StatusEffect.Parameters.AMPLIFIER).optional().defaulted(0), StatusEffect.Parameters::getAmplifier,
                        Codec.INTEGER.mapCodec(StatusEffect.Parameters.DURATION).optional().defaulted(0), StatusEffect.Parameters::getDuration,
                        Codec.BOOLEAN.mapCodec(StatusEffect.Parameters.AMBIENT).optional().defaulted(false), StatusEffect.Parameters::isAmbient,
                        Codec.BOOLEAN.mapCodec(StatusEffect.Parameters.SHOW_PARTICLES).optional().defaulted(true), StatusEffect.Parameters::isShowParticles,
                        Codec.BOOLEAN.mapCodec(StatusEffect.Parameters.SHOW_ICON).optional().defaulted(v -> false, () -> null), StatusEffect.Parameters::isShowIcon,
                        thiz.mapCodec(StatusEffect.Parameters.HIDDEN_EFFECT).optional().defaulted(null), StatusEffect.Parameters::getHiddenEffect,
                        StatusEffect.Parameters::new
                )), StatusEffect::getParameters,
                StatusEffect::new
        ));
    }

    public <T> Codec<RawFilteredPair<T>> rawFilteredPair(final Codec<T> codec) {
        return Codec.oneOf(
                MapCodecMerger.codec(
                        codec.mapCodec(RawFilteredPair.RAW).required(), RawFilteredPair::getRaw,
                        codec.mapCodec(RawFilteredPair.FILTERED).optional().defaulted(null), RawFilteredPair::getFiltered,
                        RawFilteredPair::new
                ),
                codec.map(RawFilteredPair::getRaw, raw -> new RawFilteredPair<>(raw, null))
        );
    }

    public <T> Codec<Either<Identifier, T>> registryEntry(final Function<Identifier, Result<Void>> idVerifier, final Codec<T> entryCodec) {
        return Codec.either(Codec.STRING_IDENTIFIER.verified(idVerifier), entryCodec);
    }

    public Codec<Identifier> tag(final Function<Identifier, Result<Void>> verifier) {
        return Codec.STRING.verified(tag -> {
            if (!tag.startsWith("#")) return Result.error("Tag must start with a #");
            else return null;
        }).mapThrowing(id -> "#" + id.get(), value -> Identifier.of(value.substring(1))).verified(verifier);
    }

    public Codec<TagEntryList> tagEntryList(final Function<Identifier, Result<Void>> tagVerifier, final Function<Identifier, Result<Void>> entryVerifier) {
        return Codec.oneOf(
                this.tag(tagVerifier).flatMap(list -> {
                    if (!list.isTag()) return Result.error("TagEntryList is not a tag");
                    return Result.success(list.getTag());
                }, tag -> Result.success(new TagEntryList(tag))),
                Codec.STRING_IDENTIFIER.verified(entryVerifier).optionalListOf().flatMap(list -> {
                    if (!list.isEntries()) return Result.error("TagEntryList is not a list of entries");
                    return Result.success(list.getEntries());
                }, entries -> Result.success(new TagEntryList(entries)))
        );
    }

}
