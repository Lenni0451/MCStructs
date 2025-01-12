package net.lenni0451.mcstructs.text.serializer.v1_21_5;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.click.types.*;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;

import static net.lenni0451.mcstructs.text.serializer.v1_21_5.ExtraCodecs_v1_21_5.CHAT_STRING;
import static net.lenni0451.mcstructs.text.serializer.v1_21_5.ExtraCodecs_v1_21_5.UNTRUSTED_URI;
import static net.lenni0451.mcstructs.text.serializer.verify.VerifyingConverter.verify;

public class StyleCodecs_v1_21_5 {

    public static final MapCodec<Style> MAP_CODEC = MapCodecMerger.mapCodec(
            TextFormattingCodec.CODEC.mapCodec("color").optional().defaulted(null), Style::getColor,
            ExtraCodecs_v1_21_5.ARGB_COLOR.mapCodec("shadow_color").optional().defaulted(null), Style::getShadowColor,
            Codec.BOOLEAN.mapCodec("obfuscated").optional().defaulted(null), Style::getObfuscated,
            Codec.BOOLEAN.mapCodec("bold").optional().defaulted(null), Style::getBold,
            Codec.BOOLEAN.mapCodec("strikethrough").optional().defaulted(null), Style::getStrikethrough,
            Codec.BOOLEAN.mapCodec("underlined").optional().defaulted(null), Style::getUnderlined,
            Codec.BOOLEAN.mapCodec("italic").optional().defaulted(null), Style::getItalic,
            ClickEventCodec.CODEC.mapCodec("click_event").optional().defaulted(null), Style::getClickEvent,
            HoverEventCodec.CODEC.mapCodec("hover_event").optional().defaulted(null), Style::getHoverEvent,
            Codec.STRING.mapCodec("insertion").optional().defaulted(null), Style::getInsertion,
            Codec.STRING_IDENTIFIER.mapCodec("font").optional().defaulted(null), Style::getFont,
            Style::new
    );
    public static final Codec<Style> CODEC = MAP_CODEC.asCodec();

    public static class TextFormattingCodec {
        public static final Codec<TextFormatting> CODEC = Codec.STRING.flatMap(formatting -> Result.success(formatting.serialize()), s -> {
            TextFormatting formatting = TextFormatting.parse(s);
            if (formatting == null) return Result.error("Unknown formatting: " + s);
            if (formatting.isRGBColor() && (formatting.getRgbValue() < 0 || formatting.getRgbValue() > 16777215)) return Result.error("Out of range RGB value: " + s);
            return Result.success(formatting);
        });
    }

    public static class ClickEventCodec {
        public static final MapCodec<OpenUrlClickEvent> OPEN_URL = MapCodecMerger.mapCodec(
                UNTRUSTED_URI.mapCodec("url").required(), OpenUrlClickEvent::getUrl,
                ClickEvent::openUrl
        );
        public static final MapCodec<OpenFileClickEvent> OPEN_FILE = MapCodecMerger.mapCodec(
                Codec.STRING.mapCodec("path").required(), OpenFileClickEvent::getPath,
                ClickEvent::openFile
        );
        public static final MapCodec<RunCommandClickEvent> RUN_COMMAND = MapCodecMerger.mapCodec(
                CHAT_STRING.mapCodec("command").required(), RunCommandClickEvent::getCommand,
                ClickEvent::runCommand
        );
        public static final MapCodec<SuggestCommandClickEvent> SUGGEST_COMMAND = MapCodecMerger.mapCodec(
                CHAT_STRING.mapCodec("command").required(), SuggestCommandClickEvent::getCommand,
                ClickEvent::suggestCommand
        );
        public static final MapCodec<ChangePageClickEvent> CHANGE_PAGE = MapCodecMerger.mapCodec(
                Codec.minInt(1).mapCodec("page").required(), ChangePageClickEvent::getPage,
                ClickEvent::changePage
        );
        public static final MapCodec<CopyToClipboardClickEvent> COPY_TO_CLIPBOARD = MapCodecMerger.mapCodec(
                Codec.STRING.mapCodec("value").required(), CopyToClipboardClickEvent::getValue,
                ClickEvent::copyToClipboard
        );
        public static final Codec<ClickEvent> CODEC = Codec.named(ClickEventAction.OPEN_URL, ClickEventAction.OPEN_FILE, ClickEventAction.RUN_COMMAND, ClickEventAction.SUGGEST_COMMAND, ClickEventAction.CHANGE_PAGE, ClickEventAction.COPY_TO_CLIPBOARD).verified(type -> {
            if (type.isUserDefinable()) return null;
            return Result.error("The action " + type.getName() + " is not user definable");
        }).typed("action", ClickEvent::getAction, action -> {
            switch (action) {
                case OPEN_URL:
                    return OPEN_URL;
                case OPEN_FILE:
                    return OPEN_FILE;
                case RUN_COMMAND:
                    return RUN_COMMAND;
                case SUGGEST_COMMAND:
                    return SUGGEST_COMMAND;
                case CHANGE_PAGE:
                    return CHANGE_PAGE;
                case COPY_TO_CLIPBOARD:
                    return COPY_TO_CLIPBOARD;
                default:
                    return MapCodec.failing("Unknown click event action: " + action);
            }
        });
    }

    public static class HoverEventCodec {
        public static final MapCodec<TextHoverEvent> TEXT = MapCodecMerger.mapCodec(
                TextCodecs_v1_21_5.TEXT.mapCodec("text").required(), TextHoverEvent::getText,
                TextHoverEvent::new
        );
        public static final MapCodec<ItemHoverEvent> ITEM = MapCodecMerger.mapCodec(
                Codec.STRING_IDENTIFIER.converterVerified(verify(TextVerifier_v1_21_5.class, (id, verifier) -> {
                    if (!verifier.verifyRegistryItem(id)) {
                        return Result.error("Unknown item: " + id);
                    } else {
                        return null;
                    }
                })).mapCodec("id").required(), ItemHoverEvent::getItem,
                Codec.rangedInt(1, 99).mapCodec("count").optional().elseGet(() -> 1), ItemHoverEvent::getCount,
                NbtConverter_v1_20_3.INSTANCE.toCodec().verified(tag -> {
                    if (!tag.isCompoundTag()) return Result.error("Expected a compound tag");
                    return null;
                }).map(NbtTag::asCompoundTag, NbtTag::asCompoundTag).converterVerified(verify(TextVerifier_v1_21_5.class, (tag, verifier) -> {
                    if (!verifier.verifyDataComponents(tag)) {
                        return Result.error("Invalid data components: " + tag);
                    } else {
                        return null;
                    }
                })).mapCodec("components").optional().defaulted(null), ItemHoverEvent::getNbt,
                ItemHoverEvent::new
        );
        public static final MapCodec<EntityHoverEvent> ENTITY = MapCodecMerger.mapCodec(
                Codec.STRING_IDENTIFIER.converterVerified(verify(TextVerifier_v1_21_5.class, (id, verifier) -> {
                    if (!verifier.verifyRegistryEntity(id)) {
                        return Result.error("Unknown entity: " + id);
                    } else {
                        return null;
                    }
                })).mapCodec("id").required(), EntityHoverEvent::getEntityType,
                ExtraCodecs_v1_21_5.LENIENT_UUID.mapCodec("uuid").required(), EntityHoverEvent::getUuid,
                TextCodecs_v1_21_5.TEXT.mapCodec("name").optional().defaulted(null), EntityHoverEvent::getName,
                EntityHoverEvent::new
        );
        public static final Codec<HoverEvent> CODEC = Codec.named(HoverEventAction.SHOW_TEXT, HoverEventAction.SHOW_ITEM, HoverEventAction.SHOW_ENTITY).verified(action -> {
            if (action.isUserDefinable()) return null;
            return Result.error("The action " + action.getName() + " is not user definable");
        }).typed("action", HoverEvent::getAction, action -> {
            switch (action) {
                case SHOW_TEXT:
                    return TEXT;
                case SHOW_ITEM:
                    return ITEM;
                case SHOW_ENTITY:
                    return ENTITY;
                default:
                    return MapCodec.failing("Unknown hover event action: " + action);
            }
        });
    }

}
