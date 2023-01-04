package net.lenni0451.mcstructs.text.events.click;

/**
 * The click event actions.<br>
 * Click events have been added in Minecraft 1.7.
 */
public enum ClickEventAction {

    OPEN_URL("open_url", true),
    OPEN_FILE("open_file", false),
    RUN_COMMAND("run_command", true),
    /**
     * This action has been removed in minecraft 1.9.
     */
    TWITCH_USER_INFO("twitch_user_info", false),
    SUGGEST_COMMAND("suggest_command", true),
    CHANGE_PAGE("change_page", true),
    /**
     * This action has been added in minecraft 1.16.
     */
    COPY_TO_CLIPBOARD("copy_to_clipboard", true);

    public static ClickEventAction getByName(final String name) {
        for (ClickEventAction clickEventAction : values()) {
            if (clickEventAction.getName().equalsIgnoreCase(name)) return clickEventAction;
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