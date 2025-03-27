package net.lenni0451.mcstructs.text.events.hover;

import net.lenni0451.mcstructs.converter.types.NamedType;

import javax.annotation.Nullable;
import java.util.function.BiPredicate;

/**
 * The hover event actions.<br>
 * Hover events have been added in Minecraft 1.7.
 */
public enum HoverEventAction implements NamedType {

    SHOW_TEXT("show_text", true),
    /**
     * Removed in Minecraft 1.12.
     */
    SHOW_ACHIEVEMENT("show_achievement", true),
    SHOW_ITEM("show_item", true),
    /**
     * Added in Minecraft 1.8.
     */
    SHOW_ENTITY("show_entity", true);

    @Nullable
    public static HoverEventAction byName(final String name) {
        return byName(name, true);
    }

    @Nullable
    public static HoverEventAction byName(final String name, final boolean ignoreCase) {
        return byName(name, ignoreCase ? String::equalsIgnoreCase : String::equals);
    }

    @Nullable
    public static HoverEventAction byName(final String name, final BiPredicate<String, String> predicate) {
        for (HoverEventAction hoverEventAction : values()) {
            if (predicate.test(name, hoverEventAction.getName())) return hoverEventAction;
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
