package net.lenni0451.mcstructs.text.events.hover.impl;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;

/**
 * The implementation for text hover events.
 */
@EqualsAndHashCode(callSuper = false)
public class AchievementHoverEvent extends HoverEvent {

    private String statistic;

    public AchievementHoverEvent(final String statistic) {
        super(HoverEventAction.SHOW_ACHIEVEMENT);
        this.statistic = statistic;
    }

    /**
     * @return The statistic of this hover event
     */
    public String getStatistic() {
        return this.statistic;
    }

    /**
     * Set the statistic of this hover event.
     *
     * @param statistic The new statistic
     * @return This instance for chaining
     */
    public AchievementHoverEvent setStatistic(final String statistic) {
        this.statistic = statistic;
        return this;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("statistic", this.statistic)
                .toString();
    }

}
