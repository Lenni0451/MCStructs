package net.lenni0451.mcstructs.text.events.click.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

import javax.annotation.Nullable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CustomClickEvent extends ClickEvent {

    private Identifier id;
    @Nullable
    private NbtTag payload;

    public CustomClickEvent(final Identifier id, @Nullable final NbtTag payload) {
        super(ClickEventAction.CUSTOM);
        this.id = id;
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "";
    }

}
