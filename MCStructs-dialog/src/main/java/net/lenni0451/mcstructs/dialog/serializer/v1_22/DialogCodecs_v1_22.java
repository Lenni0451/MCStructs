package net.lenni0451.mcstructs.dialog.serializer.v1_22;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.dialog.ClickAction;
import net.lenni0451.mcstructs.dialog.Dialog;
import net.lenni0451.mcstructs.dialog.DialogType;
import net.lenni0451.mcstructs.dialog.body.BodyType;
import net.lenni0451.mcstructs.dialog.body.DialogBody;
import net.lenni0451.mcstructs.dialog.body.ItemBody;
import net.lenni0451.mcstructs.dialog.body.PlainMessageBody;
import net.lenni0451.mcstructs.dialog.impl.DialogListDialog;
import net.lenni0451.mcstructs.dialog.impl.NoticeDialog;
import net.lenni0451.mcstructs.dialog.impl.ServerLinksDialog;
import net.lenni0451.mcstructs.dialog.input.*;
import net.lenni0451.mcstructs.dialog.submit.CommandTemplateSubmit;
import net.lenni0451.mcstructs.dialog.submit.CustomFormSubmit;
import net.lenni0451.mcstructs.dialog.submit.CustomTemplateSubmit;
import net.lenni0451.mcstructs.dialog.template.ParsedTemplate;
import net.lenni0451.mcstructs.dialog.template.StringTemplate;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.ArrayList;
import java.util.List;

public class DialogCodecs_v1_22 {

    public static final Codec<Dialog> DIRECT_CODEC = Codec.lazyInit(() -> Codec.identified(DialogType.values()).typed(Dialog::getType, type -> {
        switch (type) {
            case NOTICE:
                return DialogCodecs.NOTICE_DIALOG_MAP_CODEC;
            case SERVER_LINKS:
                return DialogCodecs.SERVER_LINKS_DIALOG_MAP_CODEC;
            case DIALOG_LIST:
                return DialogCodecs.DIALOG_LIST_DIALOG_MAP_CODEC;
            case MULTI_ACTION:
                return null;
            case MULTI_ACTION_INPUT_FORM:
                return null;
            case SIMPLE_INPUT_FORM:
                return null;
            case CONFIRMATION:
                return null;
            default:
                throw new IllegalArgumentException("Unknown dialog type: " + type);
        }
    }));

    private static class InternalCodecs {
        private static final Codec<TextComponent> TEXT_COMPONENT_CODEC = TextComponentCodec.V1_22.getTextCodec();
        private static final Codec<ClickEvent> CLICK_EVENT_CODEC = null/*TODO*/;
        private static final Codec<ParsedTemplate> PARSED_TEMPLATE_CODEC = Codec.STRING.flatMap(template -> Result.success(template.getRaw()), s -> {
            try {
                return Result.success(new ParsedTemplate(s, StringTemplate.parse(s)));
            } catch (Throwable t) {
                return Result.error(t);
            }
        });
        private static final MapCodec<ClickAction> CLICK_ACTION_MAP_CODEC = MapCodecMerger.mapCodec(
                TEXT_COMPONENT_CODEC.mapCodec("label").required(), ClickAction::getLabel,
                TEXT_COMPONENT_CODEC.mapCodec("tooltip").optional().defaulted(null), ClickAction::getTooltip,
                Codec.minInt(1).mapCodec("width").optional().defaulted(200), ClickAction::getWidth,
                CLICK_EVENT_CODEC.mapCodec("on_click").optional().defaulted(null), ClickAction::getOnClick,
                ClickAction::new
        );
        private static final Codec<ClickAction> CLICK_ACTION_CODEC = CLICK_ACTION_MAP_CODEC.asCodec();
    }

    public static class BodyCodecs {
        public static final MapCodec<ItemBody> ITEM_BODY_MAP_CODEC = null/*TODO*/;
        public static final Codec<ItemBody> ITEM_BODY_CODEC = null/*TODO*/;

        public static final MapCodec<PlainMessageBody> PLAIN_MESSAGE_BODY_MAP_CODEC = MapCodecMerger.mapCodec(
                InternalCodecs.TEXT_COMPONENT_CODEC.mapCodec("contents").required(), PlainMessageBody::getContents,
                Codec.minInt(1).mapCodec("width").optional().defaulted(200), PlainMessageBody::getWidth,
                PlainMessageBody::new
        );
        public static final Codec<PlainMessageBody> PLAIN_MESSAGE_BODY_CODEC = Codec.withAlternative(PLAIN_MESSAGE_BODY_MAP_CODEC.asCodec(), InternalCodecs.TEXT_COMPONENT_CODEC, PlainMessageBody::new);

        public static final Codec<DialogBody> CODEC = Codec.identified(BodyType.values()).typed(DialogBody::getType, bodyType -> {
            switch (bodyType) {
                case ITEM:
                    return ITEM_BODY_MAP_CODEC;
                case PLAIN_MESSAGE:
                    return PLAIN_MESSAGE_BODY_MAP_CODEC;
                default:
                    throw new IllegalArgumentException("Unknown body type: " + bodyType);
            }
        });
        public static Codec<List<DialogBody>> COMPACT_LIST_CODEC = Codec.compactList(CODEC);
    }

    public static class InputCodecs {
        public static final MapCodec<BooleanInput> BOOLEAN_INPUT_MAP_CODEC = MapCodecMerger.mapCodec(
                InternalCodecs.TEXT_COMPONENT_CODEC.mapCodec("label").required(), BooleanInput::getLabel,
                Codec.BOOLEAN.mapCodec("initial").optional().defaulted(false), BooleanInput::isInitial,
                Codec.STRING.mapCodec("on_true").optional().defaulted("true"), BooleanInput::getOnTrue,
                Codec.STRING.mapCodec("on_false").optional().defaulted("false"), BooleanInput::getOnFalse,
                BooleanInput::new
        );
        public static final MapCodec<NumberRangeInput> NUMBER_RANGE_INPUT_MAP_CODEC = MapCodecMerger.mapCodec(
                Codec.minInt(1).mapCodec("width").optional().defaulted(200), NumberRangeInput::getWidth,
                InternalCodecs.TEXT_COMPONENT_CODEC.mapCodec("label").required(), NumberRangeInput::getLabel,
                Codec.STRING.mapCodec("label_format").optional().defaulted("options.generic_value"), NumberRangeInput::getLabelFormat,
                MapCodecMerger.mapCodec(
                        Codec.DOUBLE.mapCodec("start").required(), NumberRangeInput.Range::getStart,
                        Codec.DOUBLE.mapCodec("end").required(), NumberRangeInput.Range::getEnd,
                        Codec.DOUBLE.mapCodec("initial").optional().defaulted(null), NumberRangeInput.Range::getInitial,
                        Codec.minInt(1).mapCodec("steps").required(), NumberRangeInput.Range::getSteps,
                        NumberRangeInput.Range::new
                ), NumberRangeInput::getRange,
                NumberRangeInput::new
        );
        public static final MapCodec<SingleOptionInput> SINGLE_OPTION_INPUT_MAP_CODEC = MapCodecMerger.mapCodec(
                Codec.minInt(1).mapCodec("width").optional().defaulted(200), SingleOptionInput::getWidth,
                Codec.withAlternative(MapCodecMerger.codec(
                        Codec.STRING.mapCodec("id").required(), SingleOptionInput.Entry::getId,
                        InternalCodecs.TEXT_COMPONENT_CODEC.mapCodec("display").optional().defaulted(null), SingleOptionInput.Entry::getDisplay,
                        Codec.BOOLEAN.mapCodec("initial").optional().defaulted(false), SingleOptionInput.Entry::isInitial,
                        SingleOptionInput.Entry::new
                ), Codec.STRING, string -> new SingleOptionInput.Entry(string, false)).nonEmptyList().mapCodec("options").required(), SingleOptionInput::getOptions,
                InternalCodecs.TEXT_COMPONENT_CODEC.mapCodec("label").required(), SingleOptionInput::getLabel,
                Codec.BOOLEAN.mapCodec("label_visible").optional().defaulted(true), SingleOptionInput::isLabelVisible,
                SingleOptionInput::new
        );
        public static final MapCodec<TextInput> TEXT_INPUT_MAP_CODEC = MapCodecMerger.mapCodec(
                Codec.minInt(1).mapCodec("with").optional().defaulted(200), TextInput::getWidth,
                InternalCodecs.TEXT_COMPONENT_CODEC.mapCodec("label").required(), TextInput::getLabel,
                Codec.BOOLEAN.mapCodec("label_visible").optional().defaulted(true), TextInput::isLabelVisible,
                Codec.STRING.mapCodec("initial").optional().defaulted(""), TextInput::getInitial,
                TextInput::new
        );

        public static final MapCodec<DialogInput> MAP_CODEC = Codec.identified(InputType.values()).typedMap(DialogInput::getType, inputType -> {
            switch (inputType) {
                case BOOLEAN:
                    return BOOLEAN_INPUT_MAP_CODEC;
                case NUMBER_RANGE:
                    return NUMBER_RANGE_INPUT_MAP_CODEC;
                case SINGLE_OPTION:
                    return SINGLE_OPTION_INPUT_MAP_CODEC;
                case TEXT:
                    return TEXT_INPUT_MAP_CODEC;
                default:
                    throw new IllegalArgumentException("Unknown input type: " + inputType);
            }
        });
    }

    public static class SubmitCodecs {
        public static final MapCodec<CommandTemplateSubmit> COMMAND_TEMPLATE_SUBMIT_MAP_CODEC = MapCodecMerger.mapCodec(
                InternalCodecs.PARSED_TEMPLATE_CODEC.mapCodec("template").required(), CommandTemplateSubmit::getTemplate,
                CommandTemplateSubmit::new
        );
        public static final MapCodec<CustomTemplateSubmit> CUSTOM_TEMPLATE_SUBMIT_MAP_CODEC = MapCodecMerger.mapCodec(
                Codec.STRING_IDENTIFIER.mapCodec("id").required(), CustomTemplateSubmit::getId,
                InternalCodecs.PARSED_TEMPLATE_CODEC.mapCodec("template").required(), CustomTemplateSubmit::getTemplate,
                CustomTemplateSubmit::new
        );
        public static final MapCodec<CustomFormSubmit> CUSTOM_FORM_SUBMIT_MAP_CODEC = MapCodecMerger.mapCodec(
                Codec.STRING_IDENTIFIER.mapCodec("id").required(), CustomFormSubmit::getId,
                CustomFormSubmit::new
        );
    }

    public static class DialogCodecs {
        public static final MapCodec<NoticeDialog> NOTICE_DIALOG_MAP_CODEC = MapCodecMerger.mapCodec(
                InternalCodecs.TEXT_COMPONENT_CODEC.mapCodec("title").required(), NoticeDialog::getTitle,
                InternalCodecs.TEXT_COMPONENT_CODEC.mapCodec("external_title").optional().defaulted(null), NoticeDialog::getExternalTitle,
                Codec.BOOLEAN.mapCodec("can_close_with_escape").optional().defaulted(true), NoticeDialog::isCanCloseWithEscape,
                BodyCodecs.COMPACT_LIST_CODEC.mapCodec("body").optional().defaulted(List::isEmpty, ArrayList::new), NoticeDialog::getBody,
                InternalCodecs.CLICK_ACTION_CODEC.mapCodec("action").optional().defaulted(action -> action.equals(NoticeDialog.defaultAction()), NoticeDialog::defaultAction), NoticeDialog::getAction,
                NoticeDialog::new
        );
        public static final MapCodec<ServerLinksDialog> SERVER_LINKS_DIALOG_MAP_CODEC = MapCodecMerger.mapCodec(
                InternalCodecs.TEXT_COMPONENT_CODEC.mapCodec("title").required(), ServerLinksDialog::getTitle,
                InternalCodecs.TEXT_COMPONENT_CODEC.mapCodec("external_title").optional().defaulted(null), ServerLinksDialog::getExternalTitle,
                Codec.BOOLEAN.mapCodec("can_close_with_escape").optional().defaulted(true), ServerLinksDialog::isCanCloseWithEscape,
                BodyCodecs.COMPACT_LIST_CODEC.mapCodec("body").optional().defaulted(List::isEmpty, ArrayList::new), ServerLinksDialog::getBody,
                InternalCodecs.CLICK_EVENT_CODEC.mapCodec("on_cancel").optional().defaulted(null), ServerLinksDialog::getOnCancel,
                Codec.minInt(1).mapCodec("columns").optional().defaulted(2), ServerLinksDialog::getColumns,
                Codec.minInt(1).mapCodec("button_width").optional().defaulted(150), ServerLinksDialog::getButtonWidth,
                ServerLinksDialog::new
        );
        public static final MapCodec<DialogListDialog> DIALOG_LIST_DIALOG_MAP_CODEC = MapCodecMerger.mapCodec(
                Internal.TEXT_COMPONENT_CODEC.mapCodec("title").required(), DialogListDialog::getTitle,
                Internal.TEXT_COMPONENT_CODEC.mapCodec("external_title").optional().defaulted(null), DialogListDialog::getExternalTitle,
                Codec.BOOLEAN.mapCodec("can_close_with_escape").optional().defaulted(true), DialogListDialog::isCanCloseWithEscape,
                Body.COMPACT_LIST_CODEC.mapCodec("body").optional().defaulted(List::isEmpty, ArrayList::new), DialogListDialog::getBody,
                );
    }

}
