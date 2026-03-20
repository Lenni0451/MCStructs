package net.lenni0451.mcstructs.text.components;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.nbt.NbtDataSource;

import javax.annotation.Nullable;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class NbtComponent extends TextComponent {

    private String component;
    private boolean resolve;
    private boolean plain;
    private TextComponent separator;
    private NbtDataSource dataSource;

    public NbtComponent(final String component, final boolean resolve, final NbtDataSource dataSource) {
        this(component, resolve, null, dataSource);
    }

    public NbtComponent(final String component, final boolean resolve, final boolean plain, final NbtDataSource dataSource) {
        this(component, resolve, plain, null, dataSource);
    }

    public NbtComponent(final String component, final boolean resolve, @Nullable final TextComponent separator, final NbtDataSource dataSource) {
        this(component, resolve, false, separator, dataSource);
    }

    public NbtComponent(final String component, final boolean resolve, final boolean plain, @Nullable final TextComponent separator, final NbtDataSource dataSource) {
        this.component = component;
        this.resolve = resolve;
        this.plain = plain;
        this.separator = separator;
        this.dataSource = dataSource;
    }

    @Override
    public String asSingleString() {
        return "";
    }

    @Override
    public TextComponent shallowCopy() {
        NbtComponent copy = new NbtComponent(this.component, this.resolve, this.separator, this.dataSource);
        return copy.setStyle(this.getStyle().copy());
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty())
                .add("style", this.getStyle(), style -> !style.isEmpty())
                .add("component", this.component)
                .add("resolve", this.resolve)
                .add("separator", this.separator, Objects::nonNull)
                .toString();
    }

}
