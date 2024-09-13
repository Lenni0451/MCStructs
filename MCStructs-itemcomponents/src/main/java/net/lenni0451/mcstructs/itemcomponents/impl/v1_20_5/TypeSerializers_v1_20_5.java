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
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.Map;
import java.util.function.Function;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.*;

public class TypeSerializers_v1_20_5 extends TypeSerializers {

    protected static final String CUSTOM_DATA = "custom_data";
    protected static final String STRING_OR_RAW_COMPOUND_TAG = "string_or_raw_compound_tag";
    protected static final String ITEM_STACK = "item_stack";
    protected static final String BLOCK_POS = "block_pos";
    protected static final String RAW_TEXT_COMPONENT = "RAW_TEXT_COMPONENT";
    protected static final String PLAYER_NAME = "player_name";
    protected static final String DYE_COLOR = "dye_color";
    protected static final String ENCHANTMENT_LEVELS = "enchantment_levels";
    protected static final String SOUND_EVENT = "sound_event";
    protected static final String BLOCK_PREDICATE = "block_predicate";
    protected static final String ATTRIBUTE_MODIFIER = "attribute_modifier";
    protected static final String ARMOR_TRIM_MATERIAL = "armor_trim_material";
    protected static final String ARMOR_TRIM_PATTERN = "armor_trim_pattern";
    protected static final String STATUS_EFFECT = "status_effect";

    public TypeSerializers_v1_20_5(final ItemComponentRegistry registry) {
        super(registry);
    }

    public Codec<CompoundTag> customData() {
        return this.init(CUSTOM_DATA, () -> new Codec<CompoundTag>() {
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
        });
    }

    public Codec<CompoundTag> stringOrRawCompoundTag() {
        return this.init(STRING_OR_RAW_COMPOUND_TAG, () -> Codec.oneOf(
                Codec.STRING.mapThrowing(SNbtSerializer.V1_14::serialize, SNbtSerializer.V1_14::deserialize),
                new Codec<CompoundTag>() {
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
                }
        ));
    }

    public Codec<ItemStack> itemStack() {
        return this.init(ITEM_STACK, () -> MapCodec.of(
                Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().item).mapCodec(ItemStack.ID), ItemStack::getId,
                Codec.rangedInt(1, Integer.MAX_VALUE).mapCodec(ItemStack.COUNT).requiredDefault(() -> 1), ItemStack::getCount,
                this.registry.getMapCodec().mapCodec(ItemStack.COMPONENTS).defaulted(() -> new ItemComponentMap(this.registry), ItemComponentMap::isEmpty), ItemStack::getComponents,
                ItemStack::new
        ));
    }

    public Codec<BlockPos> blockPos() {
        return this.init(BLOCK_POS, () -> Codec.INT_ARRAY.verified(array -> {
            if (array.length != 3) return Result.error("BlockPos array must have a length of 3");
            return Result.success(null);
        }).map(pos -> new int[]{pos.getX(), pos.getY(), pos.getZ()}, array -> new BlockPos(array[0], array[1], array[2])));
    }

    public Codec<ATextComponent> rawTextComponent() {
        return this.init(RAW_TEXT_COMPONENT, () -> new Codec<ATextComponent>() {
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
        });
    }

    public Codec<ATextComponent> textComponent(final int maxLength) {
        return Codec.sizedString(0, maxLength).mapThrowing(TextComponentCodec.V1_20_5::serializeJsonString, TextComponentCodec.V1_20_5::deserializeJson);
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
                MapCodec.of(
                        Codec.STRING_IDENTIFIER.mapCodec(SoundEvent.SOUND_ID), SoundEvent::getSoundId,
                        Codec.FLOAT.mapCodec(SoundEvent.RANGE).lenient().optionalDefault(() -> 16F), SoundEvent::getRange,
                        SoundEvent::new
                )
        ));
    }

    public Codec<BlockPredicate> blockPredicate() {
        return this.init(BLOCK_PREDICATE, () -> MapCodec.of(
                this.tagEntryList(this.registry.getRegistryVerifier().blockTag, this.registry.getRegistryVerifier().block).mapCodec(BlockPredicate.BLOCKS).optionalDefault(() -> null), BlockPredicate::getBlocks,
                Codec.mapOf(Codec.STRING, Codec.oneOf(
                        Codec.STRING.verified(s -> {
                            if (s == null) return Result.error("Value matcher cannot be null");
                            return Result.success(null);
                        }).map(BlockPredicate.ValueMatcher::getValue, BlockPredicate.ValueMatcher::new),
                        MapCodec.of(
                                Codec.STRING.mapCodec(BlockPredicate.ValueMatcher.MIN).optionalDefault(() -> null), BlockPredicate.ValueMatcher::getMin,
                                Codec.STRING.mapCodec(BlockPredicate.ValueMatcher.MAX).optionalDefault(() -> null), BlockPredicate.ValueMatcher::getMax,
                                BlockPredicate.ValueMatcher::new
                        )
                )).mapCodec(BlockPredicate.STATE).optionalDefault(() -> null), BlockPredicate::getState,
                this.stringOrRawCompoundTag().mapCodec(BlockPredicate.NBT).optionalDefault(() -> null), BlockPredicate::getNbt,
                BlockPredicate::new
        ));
    }

    public Codec<AttributeModifier> attributeModifier() {
        return this.init(ATTRIBUTE_MODIFIER, () -> MapCodec.of(
                Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().attributeModifier).mapCodec(AttributeModifier.TYPE), AttributeModifier::getType,
                MapCodec.of(
                        Codec.INT_ARRAY_UUID.mapCodec(AttributeModifier.EntityAttribute.UUID), AttributeModifier.EntityAttribute::getUuid,
                        Codec.STRING.mapCodec(AttributeModifier.EntityAttribute.NAME), AttributeModifier.EntityAttribute::getName,
                        Codec.DOUBLE.mapCodec(AttributeModifier.EntityAttribute.AMOUNT), AttributeModifier.EntityAttribute::getAmount,
                        Codec.named(AttributeModifier.EntityAttribute.Operation.values()).mapCodec(AttributeModifier.EntityAttribute.OPERATION), AttributeModifier.EntityAttribute::getOperation,
                        AttributeModifier.EntityAttribute::new
                ).mapCodec(), AttributeModifier::getModifier,
                Codec.named(AttributeModifier.Slot.values()).mapCodec(AttributeModifier.SLOT).optionalDefault(() -> AttributeModifier.Slot.ANY), AttributeModifier::getSlot,
                AttributeModifier::new
        ));
    }

    public Codec<Either<Identifier, ArmorTrimMaterial>> armorTrimMaterial() {
        return this.init(ARMOR_TRIM_MATERIAL, () -> this.registryEntry(
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
                        this.rawTextComponent().mapCodec(ArmorTrimMaterial.DESCRIPTION), ArmorTrimMaterial::getDescription,
                        ArmorTrimMaterial::new
                )
        ));
    }

    public Codec<Either<Identifier, ArmorTrimPattern>> armorTrimPattern() {
        return this.init(ARMOR_TRIM_PATTERN, () -> this.registryEntry(
                this.registry.getRegistryVerifier().armorTrimPattern,
                MapCodec.of(
                        Codec.STRING_IDENTIFIER.mapCodec(ArmorTrimPattern.ASSET_ID), ArmorTrimPattern::getAssetId,
                        Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().item).mapCodec(ArmorTrimPattern.TEMPLATE_ITEM), ArmorTrimPattern::getTemplateItem,
                        this.rawTextComponent().mapCodec(ArmorTrimPattern.DESCRIPTION), ArmorTrimPattern::getDescription,
                        Codec.BOOLEAN.mapCodec(ArmorTrimPattern.DECAL).optionalDefault(() -> false), ArmorTrimPattern::isDecal,
                        ArmorTrimPattern::new
                )
        ));
    }

    public Codec<StatusEffect> statusEffect() {
        return this.init(STATUS_EFFECT, () -> MapCodec.of(
                Codec.STRING_IDENTIFIER.verified(this.registry.getRegistryVerifier().statusEffect).mapCodec(StatusEffect.ID), StatusEffect::getId,
                Codec.<StatusEffect.Parameters>recursive(thiz -> MapCodec.of(
                        Codec.UNSIGNED_BYTE.mapCodec(StatusEffect.Parameters.AMPLIFIER).optionalDefault(() -> 0), StatusEffect.Parameters::getAmplifier,
                        Codec.INTEGER.mapCodec(StatusEffect.Parameters.DURATION).optionalDefault(() -> 0), StatusEffect.Parameters::getDuration,
                        Codec.BOOLEAN.mapCodec(StatusEffect.Parameters.AMBIENT).optionalDefault(() -> false), StatusEffect.Parameters::isAmbient,
                        Codec.BOOLEAN.mapCodec(StatusEffect.Parameters.SHOW_PARTICLES).optionalDefault(() -> true), StatusEffect.Parameters::isShowParticles,
                        Codec.BOOLEAN.mapCodec(StatusEffect.Parameters.SHOW_ICON).defaulted(() -> null, v -> false), StatusEffect.Parameters::isShowIcon,
                        thiz.mapCodec(StatusEffect.Parameters.HIDDEN_EFFECT).optionalDefault(() -> null), StatusEffect.Parameters::getHiddenEffect,
                        StatusEffect.Parameters::new
                )).mapCodec(), StatusEffect::getParameters,
                StatusEffect::new
        ));
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
