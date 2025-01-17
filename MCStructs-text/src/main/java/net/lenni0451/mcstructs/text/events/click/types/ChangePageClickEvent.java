package net.lenni0451.mcstructs.text.events.click.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

@EqualsAndHashCode(callSuper = false)
public class ChangePageClickEvent extends ClickEvent {

    private PageHolder page;

    public ChangePageClickEvent(final String page) {
        super(ClickEventAction.CHANGE_PAGE);
        try {
            this.page = new IntHolder(Integer.parseInt(page));
        } catch (Throwable t) {
            this.page = new StringHolder(page);
        }
    }

    public ChangePageClickEvent(final int page) {
        super(ClickEventAction.CHANGE_PAGE);
        this.page = new IntHolder(page);
    }

    public PageHolder getHolder() {
        return this.page;
    }

    public String asString() {
        if (this.page instanceof StringHolder) {
            return ((StringHolder) this.page).getPage();
        } else {
            return String.valueOf(((IntHolder) this.page).getPage());
        }
    }

    public int asInt() throws NumberFormatException {
        if (this.page instanceof StringHolder) {
            return Integer.parseInt(((StringHolder) this.page).getPage());
        } else {
            return ((IntHolder) this.page).getPage();
        }
    }

    public ChangePageClickEvent setPage(final String page) {
        this.page = new StringHolder(page);
        return this;
    }

    public ChangePageClickEvent setPage(final int page) {
        this.page = new IntHolder(page);
        return this;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("page", this.page)
                .toString();
    }


    public interface PageHolder {
    }

    @Data
    @AllArgsConstructor
    public static class StringHolder implements PageHolder {
        private String page;

        @Override
        public String toString() {
            return this.page;
        }
    }

    @Data
    @AllArgsConstructor
    public static class IntHolder implements PageHolder {
        private int page;

        @Override
        public String toString() {
            return String.valueOf(this.page);
        }
    }

}
