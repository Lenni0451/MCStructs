package net.lenni0451.mcstructs.text.components;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.nbt.NbtDataSource;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
public class NbtComponent extends TextComponent {

    private String component;
    private boolean resolve;
    private TextComponent separator;
    private NbtDataSource dataSource;

    public NbtComponent(final String component, final boolean resolve, final NbtDataSource dataSource) {
        this(component, resolve, null, dataSource);
    }

    public NbtComponent(final String component, final boolean resolve, @Nullable final TextComponent separator, final NbtDataSource dataSource) {
        this.component = component;
        this.resolve = resolve;
        this.separator = separator;
        this.dataSource = dataSource;
    }

    /**
     * @return The component of this component
     */
    public String getComponent() {
        return this.component;
    }

    /**
     * Set the component of this component.
     *
     * @param component The component
     * @return This component
     */
    public NbtComponent setComponent(final String component) {
        this.component = component;
        return this;
    }

    /**
     * @return Whether this component should be resolved
     */
    public boolean isResolve() {
        return this.resolve;
    }

    /**
     * Set whether this component should be resolved.
     *
     * @param resolve Whether this component should be resolved
     * @return This component
     */
    public NbtComponent setResolve(final boolean resolve) {
        this.resolve = resolve;
        return this;
    }

    /**
     * @return The separator of this component
     */
    @Nullable
    public TextComponent getSeparator() {
        return this.separator;
    }

    /**
     * Set the separator of this component.
     *
     * @param separator The separator
     * @return This component
     */
    public NbtComponent setSeparator(final TextComponent separator) {
        this.separator = separator;
        return this;
    }

    /**
     * @return The data source of this component
     */
    public NbtDataSource getDataSource() {
        return this.dataSource;
    }

    /**
     * Set the data source of this component.
     *
     * @param dataSource The data source
     */
    public void setDataSource(final NbtDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String asSingleString() {
        return "";
    }

    @Override
    public TextComponent copy() {
        return this.copyMetaTo(this.shallowCopy());
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
