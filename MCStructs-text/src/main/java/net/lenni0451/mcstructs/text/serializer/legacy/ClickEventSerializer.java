package net.lenni0451.mcstructs.text.serializer.legacy;

import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.click.types.*;

import java.util.function.Predicate;

public class ClickEventSerializer<T extends ClickEvent> extends EventSerializer<ClickEvent, T, ClickEventAction, String> {

    public static final ClickEventSerializer<OpenUrlClickEvent> OPEN_URL = create(
            OpenUrlClickEvent.class::isInstance,
            OpenUrlClickEvent::asString,
            ClickEventAction.OPEN_URL,
            OpenUrlClickEvent::new
    );
    public static final ClickEventSerializer<OpenFileClickEvent> OPEN_FILE = create(
            OpenFileClickEvent.class::isInstance,
            OpenFileClickEvent::getPath,
            ClickEventAction.OPEN_FILE,
            OpenFileClickEvent::new
    );
    public static final ClickEventSerializer<RunCommandClickEvent> RUN_COMMAND = create(
            RunCommandClickEvent.class::isInstance,
            RunCommandClickEvent::getCommand,
            ClickEventAction.RUN_COMMAND,
            RunCommandClickEvent::new
    );
    public static final ClickEventSerializer<TwitchUserInfoClickEvent> TWITCH_USER_INFO = create(
            TwitchUserInfoClickEvent.class::isInstance,
            TwitchUserInfoClickEvent::getUser,
            ClickEventAction.TWITCH_USER_INFO,
            TwitchUserInfoClickEvent::new
    );
    public static final ClickEventSerializer<SuggestCommandClickEvent> SUGGEST_COMMAND = create(
            SuggestCommandClickEvent.class::isInstance,
            SuggestCommandClickEvent::getCommand,
            ClickEventAction.SUGGEST_COMMAND,
            SuggestCommandClickEvent::new
    );
    public static final ClickEventSerializer<ChangePageClickEvent> CHANGE_PAGE = create(
            ChangePageClickEvent.class::isInstance,
            ChangePageClickEvent::asString,
            ClickEventAction.CHANGE_PAGE,
            ChangePageClickEvent::new
    );

    private static <T extends ClickEvent> ClickEventSerializer<T> create(final Predicate<ClickEvent> classMatcher, final BasicIOFunction<T, String> serializer, final ClickEventAction action, final BasicIOFunction<String, T> deserializer) {
        return new ClickEventSerializer<>(classMatcher, serializer, action, deserializer);
    }

    protected ClickEventSerializer(final Predicate<ClickEvent> classMatcher, final IOFunction<T, String> serializer, final ClickEventAction action, final IOFunction<String, T> deserializer) {
        super(classMatcher, serializer, action, deserializer);
    }

}
