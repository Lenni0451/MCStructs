package net.lenni0451.mcstructs.text.serializer.v1_20_3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.impl.LazyInitCodec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.JavaConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.mapcodec.impl.FuzzyMapCodec;
import net.lenni0451.mcstructs.converter.mapcodec.impl.StrictSelectorMapCodec;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.converter.types.NamedType;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.*;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtSource;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtSource;
import net.lenni0451.mcstructs.text.components.nbt.NbtDataSource;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtSource;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TextCodecs_v1_20_3 {

    public static final Codec<TextComponent> TEXT = new LazyInitCodec<>(() -> {
        return Codec.recursive(thiz -> createCodec(thiz, StyleCodecs_v1_20_3.MAP_CODEC, ComponentType.values(), ComponentType::forComponent));
    });

    public static final MapCodec<StringComponent> STRING_COMPONENT = MapCodecMerger.mapCodec(
            Codec.STRING.mapCodec("text").required(), StringComponent::getText,
            StringComponent::new
    );
    public static final MapCodec<TranslationComponent> TRANSLATION_COMPONENT = MapCodecMerger.mapCodec(
            Codec.STRING.mapCodec("translate").required(), TranslationComponent::getKey,
            Codec.STRING.mapCodec("fallback").optional().lenient().defaulted(null), TranslationComponent::getFallback,
            Codec.either(
                    JavaConverter_v1_20_3.INSTANCE.toCodec().verified(o -> {
                        if (o instanceof Boolean || o instanceof Number || o instanceof String) {
                            return null;
                        }
                        return Result.error("Invalid value type: " + o.getClass().getName());
                    }),
                    TEXT
            ).map(o -> o instanceof TextComponent ? Either.right((TextComponent) o) : Either.left(o), either -> {
                if (either.isLeft()) return either.getLeft();
                String collapsed = tryCollapse(either.getRight());
                if (collapsed != null) return collapsed;
                else return either.getRight();
            }).listOf().mapCodec("with").optional().defaulted(List::isEmpty, ArrayList::new), comp -> Arrays.asList(comp.getArgs()),
            (key, fallback, arguments) -> new TranslationComponent(key, arguments).setFallback(fallback)
    );
    public static final MapCodec<KeybindComponent> KEYBIND_COMPONENT = MapCodecMerger.mapCodec(
            Codec.STRING.mapCodec("keybind").required(), KeybindComponent::getKeybind,
            KeybindComponent::new
    );
    public static final MapCodec<ScoreComponent> SCORE_COMPONENT = MapCodecMerger.mapCodec(
            Codec.STRING.mapCodec("name").required(), ScoreComponent::getName,
            Codec.STRING.mapCodec("objective").required(), ScoreComponent::getObjective,
            ScoreComponent::new
    );
    public static final MapCodec<SelectorComponent> SELECTOR_COMPONENT = MapCodecMerger.mapCodec(
            Codec.STRING.mapCodec("selector").required(), SelectorComponent::getSelector,
            TEXT.mapCodec("separator").optional().defaulted(null), SelectorComponent::getSeparator,
            SelectorComponent::new
    );
    public static final MapCodec<NbtComponent> NBT_COMPONENT = MapCodecMerger.mapCodec(
            Codec.STRING.mapCodec("nbt").required(), NbtComponent::getComponent,
            Codec.BOOLEAN.mapCodec("interpret").optional().lenient().defaulted(false), NbtComponent::isResolve,
            TEXT.mapCodec("separator").optional().lenient().defaulted(null), NbtComponent::getSeparator,
            createLegacyComponentMatcher(NbtDataSourceType.values(), NbtDataSourceType::getCodec, NbtDataSourceType::forDataSource, "source"), NbtComponent::getDataSource,
            NbtComponent::new
    );

    public static <T extends TextComponentType> Codec<TextComponent> createCodec(final Codec<TextComponent> codec, final MapCodec<Style> styleCodec, final T[] componentTypes, final Function<TextComponent, T> componentToType) {
        MapCodec<TextComponent> typedComponentCodec = createLegacyComponentMatcher(componentTypes, T::getCodec, componentToType, "type");
        Codec<TextComponent> styledCodec = MapCodecMerger.codec(
                typedComponentCodec, Function.identity(),
                codec.nonEmptyList().mapCodec("extra").optional().defaulted(List::isEmpty, ArrayList::new), TextComponent::getSiblings,
                styleCodec, TextComponent::getStyle,
                (textComponent, siblings, style) -> textComponent.append(siblings).setStyle(style)
        );
        return Codec.either(Codec.either(Codec.STRING, codec.nonEmptyList()), styledCodec).map(textComponent -> {
            String collapsed = tryCollapse(textComponent);
            if (collapsed != null) {
                return Either.left(Either.left(collapsed));
            } else {
                return Either.right(textComponent);
            }
        }, stringListOrComponent -> stringListOrComponent.xmap(
                stringOrList -> stringOrList.xmap(
                        StringComponent::new,
                        TextCodecs_v1_20_3::mergeComponents
                ),
                second -> second
        ));
    }

    public static <T extends NamedType, E> MapCodec<E> createLegacyComponentMatcher(final T[] types, final Function<T, MapCodec<? extends E>> codecSupplier, final Function<E, T> typeSupplier, final String name) {
        List<MapCodec<? extends E>> typeCodecs = new ArrayList<>();
        for (T type : types) typeCodecs.add(codecSupplier.apply(type));
        MapCodec<E> fuzzyCodec = new FuzzyMapCodec<>(typeCodecs, e -> codecSupplier.apply(typeSupplier.apply(e)));
        Codec<T> typeCodec = Codec.named(types);
        MapCodec<E> elementCodec = typeCodec.typedMap(name, typeSupplier, codecSupplier);
        return new StrictSelectorMapCodec<>(name, elementCodec, fuzzyCodec);
    }

    @Nullable
    private static String tryCollapse(final TextComponent component) {
        if (component instanceof StringComponent && component.getSiblings().isEmpty() && component.getStyle().isEmpty()) {
            return ((StringComponent) component).getText();
        }
        return null;
    }

    private static TextComponent mergeComponents(final List<TextComponent> components) {
        TextComponent component = components.get(0);
        for (int i = 1; i < components.size(); i++) {
            component = component.append(components.get(i));
        }
        return component;
    }


    public interface TextComponentType extends NamedType {
        MapCodec<? extends TextComponent> getCodec();
    }

    @Getter
    @AllArgsConstructor
    private enum ComponentType implements TextComponentType {
        STRING("text", STRING_COMPONENT),
        TRANSLATION("translatable", TRANSLATION_COMPONENT),
        KEYBIND("keybind", KEYBIND_COMPONENT),
        SCORE("score", SCORE_COMPONENT),
        SELECTOR("selector", SELECTOR_COMPONENT),
        NBT("nbt", NBT_COMPONENT);

        private final String name;
        private final MapCodec<? extends TextComponent> codec;

        public static ComponentType forComponent(final TextComponent component) {
            if (component instanceof StringComponent) return STRING;
            if (component instanceof TranslationComponent) return TRANSLATION;
            if (component instanceof KeybindComponent) return KEYBIND;
            if (component instanceof ScoreComponent) return SCORE;
            if (component instanceof SelectorComponent) return SELECTOR;
            if (component instanceof NbtComponent) return NBT;
            throw new IllegalArgumentException("Unknown component type: " + component.getClass().getName());
        }
    }

    @Getter
    @AllArgsConstructor
    private enum NbtDataSourceType implements NamedType {
        ENTITY("entity", MapCodecMerger.mapCodec(
                Codec.STRING.mapCodec("entity").required(), EntityNbtSource::getSelector,
                EntityNbtSource::new
        )),
        BLOCK("block", MapCodecMerger.mapCodec(
                Codec.STRING.mapCodec("block").required(), BlockNbtSource::getPos,
                BlockNbtSource::new
        )),
        STORAGE("storage", MapCodecMerger.mapCodec(
                Codec.STRING_IDENTIFIER.mapCodec("storage").required(), StorageNbtSource::getId,
                StorageNbtSource::new
        ));

        private final String name;
        private final MapCodec<? extends NbtDataSource> codec;

        public static NbtDataSourceType forDataSource(final NbtDataSource dataSource) {
            if (dataSource instanceof EntityNbtSource) return ENTITY;
            if (dataSource instanceof BlockNbtSource) return BLOCK;
            if (dataSource instanceof StorageNbtSource) return STORAGE;
            throw new IllegalArgumentException("Unknown data source type: " + dataSource.getClass().getName());
        }
    }

}
