package net.lenni0451.mcstructs.text.events.click;

import net.lenni0451.mcstructs.converter.types.NamedType;

import javax.annotation.Nullable;
import java.util.function.BiPredicate;

/**
 * The click event actions.<br>
 * Click events have been added in Minecraft 1.7.
 */
public enum ClickEventAction implements NamedType {

    OPEN_URL("open_url", true),
    OPEN_FILE("open_file", false),
    RUN_COMMAND("run_command", true),
    /**
     * Removed in minecraft 1.9.
     */
    TWITCH_USER_INFO("twitch_user_info", false),
    SUGGEST_COMMAND("suggest_command", true),
    CHANGE_PAGE("change_page", true),
    /**
     * Added in minecraft 1.16.
     */
    COPY_TO_CLIPBOARD("copy_to_clipboard", true);

    @Nullable
    public static ClickEventAction byName(final String name) {
        return byName(name, true);
    }

    @Nullable
    public static ClickEventAction byName(final String name, final boolean ignoreCase) {
        return byName(name, ignoreCase ? String::equalsIgnoreCase : String::equals);
    }

    @Nullable
    public static ClickEventAction byName(final String name, final BiPredicate<String, String> predicate) {
        for (ClickEventAction hoverEventAction : values()) {
            if (predicate.test(name, hoverEventAction.getName())) return hoverEventAction;
        }
        return null;
    }


    private final String name;
    private final boolean userDefinable;

    ClickEventAction(final String name, final boolean userDefinable) {
        this.name = name;
        this.userDefinable = userDefinable;
    }

    /**
     * @return The name of the action
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return If the action can be defined by the user
     */
    public boolean isUserDefinable() {
        return this.userDefinable;
    }

}
