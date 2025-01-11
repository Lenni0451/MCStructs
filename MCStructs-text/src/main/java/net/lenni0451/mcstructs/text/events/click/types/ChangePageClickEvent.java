package net.lenni0451.mcstructs.text.events.click.types;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

@EqualsAndHashCode
public class ChangePageClickEvent extends ClickEvent {

    private int page;

    public ChangePageClickEvent(final int page) {
        super(ClickEventAction.CHANGE_PAGE);
        this.page = page;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("page", this.page)
                .toString();
    }

}
