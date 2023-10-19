package net.lenni0451.mcstructs.text.events.hover;

/**
 * The hover event actions.<br>
 * Hover events have been added in Minecraft 1.7.
 */
public enum HoverEventAction {

    SHOW_TEXT("show_text", true),
    SHOW_ACHIEVEMENT("show_achievement", true),
    SHOW_ITEM("show_item", true),
    /**
     * This action has been added in minecraft 1.8.
     */
    SHOW_ENTITY("show_entity", true);

    public static HoverEventAction getByName(final String name) {
        for (HoverEventAction hoverEventAction : values()) {
            if (hoverEventAction.getName().equalsIgnoreCase(name)) return hoverEventAction;
        }
        return null;
    }

    public static HoverEventAction getByName(final String name, final boolean ignoreCase) {
        for (HoverEventAction hoverEventAction : values()) {
            if (ignoreCase) {
                if (hoverEventAction.getName().equalsIgnoreCase(name)) return hoverEventAction;
            } else {
                if (hoverEventAction.getName().equals(name)) return hoverEventAction;
            }
        }
        return null;
    }


    private final String name;
    private final boolean userDefinable;

    HoverEventAction(final String name, final boolean userDefinable) {
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
