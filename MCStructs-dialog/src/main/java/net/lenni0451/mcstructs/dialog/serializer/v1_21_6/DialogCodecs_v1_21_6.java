package net.lenni0451.mcstructs.dialog.serializer.v1_21_6;

import lombok.RequiredArgsConstructor;
import net.lenni0451.mcstructs.converter.SerializedData;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.impl.v1_21_5.NbtConverter_v1_21_5;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.dialog.*;
import net.lenni0451.mcstructs.dialog.action.CommandTemplateAction;
import net.lenni0451.mcstructs.dialog.action.CustomAllAction;
import net.lenni0451.mcstructs.dialog.action.DialogAction;
import net.lenni0451.mcstructs.dialog.action.StaticAction;
import net.lenni0451.mcstructs.dialog.body.BodyType;
import net.lenni0451.mcstructs.dialog.body.DialogBody;
import net.lenni0451.mcstructs.dialog.body.ItemBody;
import net.lenni0451.mcstructs.dialog.body.PlainMessageBody;
import net.lenni0451.mcstructs.dialog.impl.*;
import net.lenni0451.mcstructs.dialog.input.*;
import net.lenni0451.mcstructs.dialog.serializer.DialogSerializer;
import net.lenni0451.mcstructs.dialog.template.ParsedTemplate;
import net.lenni0451.mcstructs.dialog.template.StringTemplate;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.registry.EitherEntry;
import net.lenni0451.mcstructs.registry.Registry;
import net.lenni0451.mcstructs.registry.TypedTagEntryList;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.click.types.*;
import net.lenni0451.mcstructs.text.serializer.v1_21_6.StyleCodecs_v1_21_6;
import net.lenni0451.mcstructs.text.serializer.v1_21_6.TextCodecs_v1_21_6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
public class DialogCodecs_v1_21_6 extends DialogSerializer {

    private final Registry registry;
    private final DialogCodecs dialogCodecs = new DialogCodecs();
    public final Codec<Dialog> directCodec = Codec.identified(DialogType.values()).typed(Dialog::getType, type -> {
        switch (type) {
            case NOTICE:
                return this.dialogCodecs.noticeDialogMapCodec;
            case SERVER_LINKS:
                return this.dialogCodecs.serverLinksDialogMapCodec;
            case DIALOG_LIST:
                return this.dialogCodecs.dialogListDialogMapCodec;
            case MULTI_ACTION:
                return this.dialogCodecs.multiActionDialogMapCodec;
            case CONFIRMATION:
                return this.dialogCodecs.confirmationDialogMapCodec;
            default:
                throw new IllegalArgumentException("Unknown dialog type: " + type);
        }
    });
    public final Codec<EitherEntry<Dialog>> codec = EitherEntry.codec(this.registry, this.directCodec);
    public final Codec<TypedTagEntryList<Dialog>> listCodec = TypedTagEntryList.codec(this.registry, this.directCodec, false);

    public static class InternalCodecs {
        private static final Codec<TextComponent> TEXT_CODEC = TextCodecs_v1_21_6.TEXT;
        public static final Codec<ParsedTemplate> PARSED_TEMPLATE_CODEC = Codec.STRING.flatMap(template -> Result.success(template.getRaw()), s -> {
            try {
                return Result.success(new ParsedTemplate(s, StringTemplate.parse(s)));
            } catch (Throwable t) {
                return Result.error(t);
            }
        });
        public static final Codec<String> PARSED_TEMPLATE_VARIABLE_CODEC = Codec.STRING.verified(s -> {
            if (StringTemplate.isValidVariableName(s)) return null;
            return Result.error("Invalid variable name: " + s);
        });
        public static final Codec<CompoundTag> COMPOUND_TAG_CODEC = Codec.RAW.flatMap(entries -> Result.success(new SerializedData<>(entries, NbtConverter_v1_21_5.INSTANCE)), serializedData -> serializedData.convert(NbtConverter_v1_21_5.INSTANCE).map(NbtTag::asCompoundTag));
        public static final Codec<ActionButton> ACTION_BUTTON_CODEC = MapCodecMerger.codec(
                TEXT_CODEC.mapCodec("label").required(), ActionButton::getLabel,
                TEXT_CODEC.mapCodec("tooltip").optional().defaulted(null), ActionButton::getTooltip,
                Codec.rangedInt(1, 1024).mapCodec("width").optional().defaulted(200), ActionButton::getWidth,
                ActionCodecs.DIALOG_ACTION_CODEC.mapCodec("action").optional().defaulted(null), ActionButton::getAction,
                ActionButton::new
        );
        public static final Codec<AfterAction> AFTER_ACTION_CODEC = Codec.named(AfterAction.values());
        public static final Codec<Input> INPUT_CODEC = MapCodecMerger.codec(
                PARSED_TEMPLATE_VARIABLE_CODEC.mapCodec("key").required(), Input::getKey,
                InputCodecs.DIALOG_INPUT_MAP_CODEC, Input::getControl,
                Input::new
        );
    }

    public static class ActionCodecs {
        public static final MapCodec<CommandTemplateAction> COMMAND_TEMPLATE_ACTION_MAP_CODEC = MapCodecMerger.mapCodec(
                InternalCodecs.PARSED_TEMPLATE_CODEC.mapCodec("template").required(), CommandTemplateAction::getTemplate,
                CommandTemplateAction::new
        );
        public static final MapCodec<CustomAllAction> CUSTOM_ALL_ACTION_MAP_CODEC = MapCodecMerger.mapCodec(
                Codec.STRING_IDENTIFIER.mapCodec("id").required(), CustomAllAction::getId,
                InternalCodecs.COMPOUND_TAG_CODEC.mapCodec("additions").optional().defaulted(null), CustomAllAction::getAdditions,
                CustomAllAction::new
        );
        public static final MapCodec<StaticAction> OPEN_URL_STATIC_ACTION_MAP_CODEC = StyleCodecs_v1_21_6.ClickEventCodec.OPEN_URL.mapThrowing(action -> (OpenUrlClickEvent) action.getValue(), StaticAction::new);
        public static final MapCodec<StaticAction> RUN_COMMAND_STATIC_ACTION_MAP_CODEC = StyleCodecs_v1_21_6.ClickEventCodec.RUN_COMMAND.mapThrowing(action -> (RunCommandClickEvent) action.getValue(), StaticAction::new);
        public static final MapCodec<StaticAction> SUGGEST_COMMAND_STATIC_ACTION_MAP_CODEC = StyleCodecs_v1_21_6.ClickEventCodec.SUGGEST_COMMAND.mapThrowing(action -> (SuggestCommandClickEvent) action.getValue(), StaticAction::new);
        public static final MapCodec<StaticAction> CHANGE_PAGE_STATIC_ACTION_MAP_CODEC = StyleCodecs_v1_21_6.ClickEventCodec.CHANGE_PAGE.mapThrowing(action -> (ChangePageClickEvent) action.getValue(), StaticAction::new);
        public static final MapCodec<StaticAction> COPY_TO_CLIPBOARD_STATIC_ACTION_MAP_CODEC = StyleCodecs_v1_21_6.ClickEventCodec.COPY_TO_CLIPBOARD.mapThrowing(action -> (CopyToClipboardClickEvent) action.getValue(), StaticAction::new);
        public static final MapCodec<StaticAction> SHOW_DIALOG_STATIC_ACTION_MAP_CODEC = StyleCodecs_v1_21_6.ClickEventCodec.SHOW_DIALOG.mapThrowing(action -> (ShowDialogClickEvent) action.getValue(), StaticAction::new);
        public static final MapCodec<StaticAction> CUSTOM_STATIC_ACTION_MAP_CODEC = StyleCodecs_v1_21_6.ClickEventCodec.CUSTOM.mapThrowing(action -> (CustomClickEvent) action.getValue(), StaticAction::new);

        public static final Codec<DialogAction> DIALOG_ACTION_CODEC = Codec.STRING_IDENTIFIER.typed(new Function<DialogAction, Identifier>() {
            private final Identifier commandTemplateAction = Identifier.defaultNamespace("dynamic/run_command");
            private final Identifier customAllAction = Identifier.defaultNamespace("dynamic/custom");
            private final Identifier openUrlStaticAction = Identifier.defaultNamespace(ClickEventAction.OPEN_URL.getName());
            private final Identifier runCommandStaticAction = Identifier.defaultNamespace(ClickEventAction.RUN_COMMAND.getName());
            private final Identifier suggestCommandStaticAction = Identifier.defaultNamespace(ClickEventAction.SUGGEST_COMMAND.getName());
            private final Identifier changePageStaticAction = Identifier.defaultNamespace(ClickEventAction.CHANGE_PAGE.getName());
            private final Identifier copyToClipboardStaticAction = Identifier.defaultNamespace(ClickEventAction.COPY_TO_CLIPBOARD.getName());
            private final Identifier showDialogStaticAction = Identifier.defaultNamespace(ClickEventAction.SHOW_DIALOG.getName());
            private final Identifier customStaticAction = Identifier.defaultNamespace(ClickEventAction.CUSTOM.getName());

            @Override
            public Identifier apply(DialogAction dialogAction) {
                if (dialogAction instanceof CommandTemplateAction) {
                    return this.commandTemplateAction;
                } else if (dialogAction instanceof CustomAllAction) {
                    return this.customAllAction;
                } else if (dialogAction instanceof StaticAction) {
                    ClickEventAction action = ((StaticAction) dialogAction).getValue().getAction();
                    switch (action) {
                        case OPEN_URL:
                            return this.openUrlStaticAction;
                        case RUN_COMMAND:
                            return this.runCommandStaticAction;
                        case SUGGEST_COMMAND:
                            return this.suggestCommandStaticAction;
                        case CHANGE_PAGE:
                            return this.changePageStaticAction;
                        case COPY_TO_CLIPBOARD:
                            return this.copyToClipboardStaticAction;
                        case SHOW_DIALOG:
                            return this.showDialogStaticAction;
                        case CUSTOM:
                            return this.customStaticAction;
                    }
                }
                return null;
            }
        }, new Function<Identifier, MapCodec<? extends DialogAction>>() {
            private final Map<Identifier, MapCodec<? extends DialogAction>> actionMap = ActionCodecs.map(
                    Identifier.defaultNamespace("dynamic/run_command"), COMMAND_TEMPLATE_ACTION_MAP_CODEC,
                    Identifier.defaultNamespace("dynamic/custom"), CUSTOM_ALL_ACTION_MAP_CODEC,
                    Identifier.defaultNamespace(ClickEventAction.OPEN_URL.getName()), OPEN_URL_STATIC_ACTION_MAP_CODEC,
                    Identifier.defaultNamespace(ClickEventAction.RUN_COMMAND.getName()), RUN_COMMAND_STATIC_ACTION_MAP_CODEC,
                    Identifier.defaultNamespace(ClickEventAction.SUGGEST_COMMAND.getName()), SUGGEST_COMMAND_STATIC_ACTION_MAP_CODEC,
                    Identifier.defaultNamespace(ClickEventAction.CHANGE_PAGE.getName()), CHANGE_PAGE_STATIC_ACTION_MAP_CODEC,
                    Identifier.defaultNamespace(ClickEventAction.COPY_TO_CLIPBOARD.getName()), COPY_TO_CLIPBOARD_STATIC_ACTION_MAP_CODEC,
                    Identifier.defaultNamespace(ClickEventAction.SHOW_DIALOG.getName()), SHOW_DIALOG_STATIC_ACTION_MAP_CODEC,
                    Identifier.defaultNamespace(ClickEventAction.CUSTOM.getName()), CUSTOM_STATIC_ACTION_MAP_CODEC
            );
            private final MapCodec<DialogAction> unknown = MapCodec.failing("Unknown dialog action");

            @Override
            public MapCodec<? extends DialogAction> apply(Identifier identifier) {
                return this.actionMap.getOrDefault(identifier, this.unknown);
            }
        });

        private static <K, V> Map<K, V> map(final K key1, final V value1, final K key2, final V value2, final K key3, final V value3, final K key4, final V value4, final K key5, final V value5, final K key6, final V value6, final K key7, final V value7, final K key8, final V value8, final K key9, final V value9) {
            Map<K, V> map = new HashMap<>();
            map.put(key1, value1);
            map.put(key2, value2);
            map.put(key3, value3);
            map.put(key4, value4);
            map.put(key5, value5);
            map.put(key6, value6);
            map.put(key7, value7);
            map.put(key8, value8);
            map.put(key9, value9);
            return map;
        }
    }

    public static class BodyCodecs {
        public static final MapCodec<PlainMessageBody> PLAIN_MESSAGE_BODY_MAP_CODEC = MapCodecMerger.mapCodec(
                InternalCodecs.TEXT_CODEC.mapCodec("contents").required(), PlainMessageBody::getContents,
                Codec.rangedInt(1, 1024).mapCodec("width").optional().defaulted(200), PlainMessageBody::getWidth,
                PlainMessageBody::new
        );
        public static final Codec<PlainMessageBody> PLAIN_MESSAGE_BODY_CODEC = Codec.withAlternative(PLAIN_MESSAGE_BODY_MAP_CODEC.asCodec(), InternalCodecs.TEXT_CODEC, text -> new PlainMessageBody(text, 200));
        public static final MapCodec<ItemBody> ITEM_BODY_MAP_CODEC = MapCodecMerger.mapCodec(
                Codec.RAW.mapCodec("item").required(), ItemBody::getItem,
                PLAIN_MESSAGE_BODY_CODEC.mapCodec("description").optional().defaulted(null), ItemBody::getDescription,
                Codec.BOOLEAN.mapCodec("show_decorations").optional().defaulted(true), ItemBody::isShowDecorations,
                Codec.BOOLEAN.mapCodec("show_tooltip").optional().defaulted(true), ItemBody::isShowTooltip,
                Codec.rangedInt(1, 256).mapCodec("width").optional().defaulted(16), ItemBody::getWidth,
                Codec.rangedInt(1, 256).mapCodec("height").optional().defaulted(16), ItemBody::getHeight,
                ItemBody::new
        );

        public static final Codec<DialogBody> DIALOG_BODY_CODEC = Codec.identified(BodyType.values()).typed(DialogBody::getType, type -> {
            switch (type) {
                case ITEM:
                    return ITEM_BODY_MAP_CODEC;
                case PLAIN_MESSAGE:
                    return PLAIN_MESSAGE_BODY_MAP_CODEC;
                default:
                    throw new IllegalArgumentException("Unknown dialog body type: " + type);
            }
        });
    }

    public static class InputCodecs {
        public static final MapCodec<BooleanInput> BOOLEAN_INPUT_MAP_CODEC = MapCodecMerger.mapCodec(
                InternalCodecs.TEXT_CODEC.mapCodec("label").required(), BooleanInput::getLabel,
                Codec.BOOLEAN.mapCodec("initial").optional().defaulted(false), BooleanInput::isInitial,
                Codec.STRING.mapCodec("on_true").optional().defaulted("true"), BooleanInput::getOnTrue,
                Codec.STRING.mapCodec("on_false").optional().defaulted("false"), BooleanInput::getOnFalse,
                BooleanInput::new
        );
        public static final MapCodec<NumberRangeInput> NUMBER_RANGE_INPUT_MAP_CODEC = MapCodecMerger.mapCodec(
                Codec.rangedInt(1, 1024).mapCodec("width").optional().defaulted(200), NumberRangeInput::getWidth,
                InternalCodecs.TEXT_CODEC.mapCodec("label").required(), NumberRangeInput::getLabel,
                Codec.STRING.mapCodec("label_format").optional().defaulted("options.generic_value"), NumberRangeInput::getLabelFormat,
                MapCodecMerger.mapCodec(
                        Codec.FLOAT.mapCodec("start").required(), NumberRangeInput.Range::getStart,
                        Codec.FLOAT.mapCodec("end").required(), NumberRangeInput.Range::getEnd,
                        Codec.FLOAT.mapCodec("initial").optional().defaulted(null), NumberRangeInput.Range::getInitial,
                        Codec.minExclusiveFloat(0).mapCodec("step").optional().defaulted(null), NumberRangeInput.Range::getStep,
                        NumberRangeInput.Range::new
                ), NumberRangeInput::getRange,
                NumberRangeInput::new
        );
        public static final MapCodec<SingleOptionInput> SINGLE_OPTION_INPUT_MAP_CODEC = MapCodecMerger.mapCodec(
                Codec.rangedInt(1, 1024).mapCodec("width").optional().defaulted(200), SingleOptionInput::getWidth,
                MapCodecMerger.codec(
                        Codec.STRING.mapCodec("id").required(), SingleOptionInput.Entry::getId,
                        InternalCodecs.TEXT_CODEC.mapCodec("display").optional().defaulted(null), SingleOptionInput.Entry::getDisplay,
                        Codec.BOOLEAN.mapCodec("initial").optional().defaulted(false), SingleOptionInput.Entry::isInitial,
                        SingleOptionInput.Entry::new
                ).nonEmptyList().mapCodec("options").required(), SingleOptionInput::getOptions,
                InternalCodecs.TEXT_CODEC.mapCodec("label").required(), SingleOptionInput::getLabel,
                Codec.BOOLEAN.mapCodec("label_visible").optional().defaulted(true), SingleOptionInput::isLabelVisible,
                SingleOptionInput::new
        );
        public static final MapCodec<TextInput> TEXT_INPUT_MAP_CODEC = MapCodecMerger.mapCodec(
                Codec.rangedInt(1, 1024).mapCodec("with").optional().defaulted(200), TextInput::getWidth,
                InternalCodecs.TEXT_CODEC.mapCodec("label").required(), TextInput::getLabel,
                Codec.BOOLEAN.mapCodec("label_visible").optional().defaulted(true), TextInput::isLabelVisible,
                Codec.STRING.mapCodec("initial").optional().defaulted(""), TextInput::getInitial,
                Codec.minInt(1).mapCodec("max_length").optional().defaulted(32), TextInput::getMaxLength,
                MapCodecMerger.codec(
                        Codec.minInt(1).mapCodec("max_lines").optional().defaulted(null), TextInput.MultilineOptions::getMaxLines,
                        Codec.rangedInt(1, 512).mapCodec("height").optional().defaulted(null), TextInput.MultilineOptions::getHeight,
                        TextInput.MultilineOptions::new
                ).mapCodec("multiline").required(), TextInput::getMultiline,
                TextInput::new
        );

        public static final MapCodec<DialogInput> DIALOG_INPUT_MAP_CODEC = Codec.identified(InputType.values()).typedMap(DialogInput::getType, type -> {
            switch (type) {
                case BOOLEAN:
                    return BOOLEAN_INPUT_MAP_CODEC;
                case NUMBER_RANGE:
                    return NUMBER_RANGE_INPUT_MAP_CODEC;
                case SINGLE_OPTION:
                    return SINGLE_OPTION_INPUT_MAP_CODEC;
                case TEXT:
                    return TEXT_INPUT_MAP_CODEC;
                default:
                    throw new IllegalArgumentException("Unknown dialog input type: " + type);
            }
        });
    }

    public class DialogCodecs {
        public final MapCodec<ConfirmationDialog> confirmationDialogMapCodec = MapCodecMerger.mapCodec(
                InternalCodecs.TEXT_CODEC.mapCodec("title").required(), ConfirmationDialog::getTitle,
                InternalCodecs.TEXT_CODEC.mapCodec("external_title").optional().defaulted(null), ConfirmationDialog::getExternalTitle,
                Codec.BOOLEAN.mapCodec("can_close_with_escape").optional().defaulted(true), ConfirmationDialog::isCanCloseWithEscape,
                Codec.BOOLEAN.mapCodec("pause").optional().defaulted(true), ConfirmationDialog::isPause,
                InternalCodecs.AFTER_ACTION_CODEC.mapCodec("after_action").optional().defaulted(AfterAction.CLOSE), ConfirmationDialog::getAfterAction,
                BodyCodecs.DIALOG_BODY_CODEC.compactListOf().mapCodec("body").optional().defaulted(List::isEmpty, ArrayList::new), ConfirmationDialog::getBody,
                InternalCodecs.INPUT_CODEC.listOf().mapCodec("inputs").optional().defaulted(List::isEmpty, ArrayList::new), ConfirmationDialog::getInputs,
                InternalCodecs.ACTION_BUTTON_CODEC.mapCodec("yes").required(), ConfirmationDialog::getYesButton,
                InternalCodecs.ACTION_BUTTON_CODEC.mapCodec("no").required(), ConfirmationDialog::getNoButton,
                ConfirmationDialog::new
        );
        public final MapCodec<DialogListDialog> dialogListDialogMapCodec = MapCodecMerger.mapCodec(
                InternalCodecs.TEXT_CODEC.mapCodec("title").required(), DialogListDialog::getTitle,
                InternalCodecs.TEXT_CODEC.mapCodec("external_title").optional().defaulted(null), DialogListDialog::getExternalTitle,
                Codec.BOOLEAN.mapCodec("can_close_with_escape").optional().defaulted(true), DialogListDialog::isCanCloseWithEscape,
                Codec.BOOLEAN.mapCodec("pause").optional().defaulted(true), DialogListDialog::isPause,
                InternalCodecs.AFTER_ACTION_CODEC.mapCodec("after_action").optional().defaulted(AfterAction.CLOSE), DialogListDialog::getAfterAction,
                BodyCodecs.DIALOG_BODY_CODEC.compactListOf().mapCodec("body").optional().defaulted(List::isEmpty, ArrayList::new), DialogListDialog::getBody,
                InternalCodecs.INPUT_CODEC.listOf().mapCodec("inputs").optional().defaulted(List::isEmpty, ArrayList::new), DialogListDialog::getInputs,
                DialogCodecs_v1_21_6.this.listCodec.mapCodec("dialogs").required(), DialogListDialog::getDialogs,
                InternalCodecs.ACTION_BUTTON_CODEC.mapCodec("exit_action").optional().defaulted(null), DialogListDialog::getExitAction,
                Codec.minInt(1).mapCodec("columns").optional().defaulted(2), DialogListDialog::getColumns,
                Codec.rangedInt(1, 1024).mapCodec("button_width").optional().defaulted(200), DialogListDialog::getButtonWidth,
                DialogListDialog::new
        );
        public final MapCodec<MultiActionDialog> multiActionDialogMapCodec = MapCodecMerger.mapCodec(
                InternalCodecs.TEXT_CODEC.mapCodec("title").required(), MultiActionDialog::getTitle,
                InternalCodecs.TEXT_CODEC.mapCodec("external_title").optional().defaulted(null), MultiActionDialog::getExternalTitle,
                Codec.BOOLEAN.mapCodec("can_close_with_escape").optional().defaulted(true), MultiActionDialog::isCanCloseWithEscape,
                Codec.BOOLEAN.mapCodec("pause").optional().defaulted(true), MultiActionDialog::isPause,
                InternalCodecs.AFTER_ACTION_CODEC.mapCodec("after_action").optional().defaulted(AfterAction.CLOSE), MultiActionDialog::getAfterAction,
                BodyCodecs.DIALOG_BODY_CODEC.compactListOf().mapCodec("body").optional().defaulted(List::isEmpty, ArrayList::new), MultiActionDialog::getBody,
                InternalCodecs.INPUT_CODEC.listOf().mapCodec("inputs").optional().defaulted(List::isEmpty, ArrayList::new), MultiActionDialog::getInputs,
                InternalCodecs.ACTION_BUTTON_CODEC.nonEmptyList().mapCodec("actions").required(), MultiActionDialog::getActions,
                InternalCodecs.ACTION_BUTTON_CODEC.mapCodec("exit_action").optional().defaulted(null), MultiActionDialog::getExitAction,
                Codec.minInt(1).mapCodec("columns").optional().defaulted(2), MultiActionDialog::getColumns,
                MultiActionDialog::new
        );
        public final MapCodec<NoticeDialog> noticeDialogMapCodec = MapCodecMerger.mapCodec(
                InternalCodecs.TEXT_CODEC.mapCodec("title").required(), NoticeDialog::getTitle,
                InternalCodecs.TEXT_CODEC.mapCodec("external_title").optional().defaulted(null), NoticeDialog::getExternalTitle,
                Codec.BOOLEAN.mapCodec("can_close_with_escape").optional().defaulted(true), NoticeDialog::isCanCloseWithEscape,
                Codec.BOOLEAN.mapCodec("pause").optional().defaulted(true), NoticeDialog::isPause,
                InternalCodecs.AFTER_ACTION_CODEC.mapCodec("after_action").optional().defaulted(AfterAction.CLOSE), NoticeDialog::getAfterAction,
                BodyCodecs.DIALOG_BODY_CODEC.compactListOf().mapCodec("body").optional().defaulted(List::isEmpty, ArrayList::new), NoticeDialog::getBody,
                InternalCodecs.INPUT_CODEC.listOf().mapCodec("inputs").optional().defaulted(List::isEmpty, ArrayList::new), NoticeDialog::getInputs,
                InternalCodecs.ACTION_BUTTON_CODEC.mapCodec("action").optional().defaulted(NoticeDialog::isDefaultAction, NoticeDialog::defaultAction), NoticeDialog::getAction,
                NoticeDialog::new
        );
        public final MapCodec<ServerLinksDialog> serverLinksDialogMapCodec = MapCodecMerger.mapCodec(
                InternalCodecs.TEXT_CODEC.mapCodec("title").required(), ServerLinksDialog::getTitle,
                InternalCodecs.TEXT_CODEC.mapCodec("external_title").optional().defaulted(null), ServerLinksDialog::getExternalTitle,
                Codec.BOOLEAN.mapCodec("can_close_with_escape").optional().defaulted(true), ServerLinksDialog::isCanCloseWithEscape,
                Codec.BOOLEAN.mapCodec("pause").optional().defaulted(true), ServerLinksDialog::isPause,
                InternalCodecs.AFTER_ACTION_CODEC.mapCodec("after_action").optional().defaulted(AfterAction.CLOSE), ServerLinksDialog::getAfterAction,
                BodyCodecs.DIALOG_BODY_CODEC.compactListOf().mapCodec("body").optional().defaulted(List::isEmpty, ArrayList::new), ServerLinksDialog::getBody,
                InternalCodecs.INPUT_CODEC.listOf().mapCodec("inputs").optional().defaulted(List::isEmpty, ArrayList::new), ServerLinksDialog::getInputs,
                InternalCodecs.ACTION_BUTTON_CODEC.mapCodec("exit_action").optional().defaulted(null), ServerLinksDialog::getExitAction,
                Codec.minInt(1).mapCodec("columns").optional().defaulted(2), ServerLinksDialog::getColumns,
                Codec.rangedInt(1, 1024).mapCodec("button_width").optional().defaulted(150), ServerLinksDialog::getButtonWidth,
                ServerLinksDialog::new
        );
    }

}
