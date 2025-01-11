package net.lenni0451.mcstructs.text.events.click.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

@EqualsAndHashCode(callSuper = false)
public class LegacyClickEvent extends ClickEvent {

    private LegacyData data;

    public LegacyClickEvent(final ClickEventAction action, final LegacyData data) {
        super(action);
        this.data = data;
    }

    public ClickEvent setAction(final ClickEventAction action) {
        this.action = action;
        return this;
    }

    public LegacyData getData() {
        return this.data;
    }

    public void setData(final LegacyData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("data", this.data)
                .toString();
    }


    public interface LegacyData {
    }

    @Data
    @AllArgsConstructor
    public static class LegacyUrlData implements LegacyData {
        private String url;
    }

    @Data
    @AllArgsConstructor
    public static class LegacyPageData implements LegacyData {
        private String page;
    }

}
