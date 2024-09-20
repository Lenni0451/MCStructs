package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.ATextComponent;

import javax.annotation.Nullable;
import java.util.Objects;

public class ScoreComponent extends ATextComponent {

    private String name;
    private String objective;
    @Nullable
    private String value;

    public ScoreComponent(final String name, final String objective) {
        this(name, objective, null);
    }

    public ScoreComponent(final String name, final String objective, @Nullable final String value) {
        this.name = name;
        this.objective = objective;
        this.value = value;
    }

    /**
     * @return The name of this component
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of this component.
     *
     * @param name The name
     * @return This component
     */
    public ScoreComponent setName(final String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The objective of this component
     */
    public String getObjective() {
        return this.objective;
    }

    /**
     * Set the objective of this component.
     *
     * @param objective The objective
     * @return This component
     */
    public ScoreComponent setObjective(@Nullable final String objective) {
        this.objective = objective;
        return this;
    }

    /**
     * @return The value of this component
     */
    @Nullable
    public String getValue() {
        return this.value;
    }

    /**
     * Set the value of this component.
     *
     * @param value The new value
     * @return This component
     */
    public ScoreComponent setValue(@Nullable final String value) {
        this.value = value;
        return this;
    }

    @Override
    public String asSingleString() {
        return this.value;
    }

    @Override
    public ATextComponent copy() {
        return this.putMetaCopy(this.shallowCopy());
    }

    @Override
    public ATextComponent shallowCopy() {
        ScoreComponent copy = new ScoreComponent(this.name, this.objective);
        copy.value = this.value;
        return copy.setStyle(this.getStyle().copy());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreComponent that = (ScoreComponent) o;
        return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.name, that.name) && Objects.equals(this.objective, that.objective) && Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSiblings(), this.getStyle(), this.name, this.objective, this.value);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty())
                .add("style", this.getStyle(), style -> !style.isEmpty())
                .add("name", this.name)
                .add("objective", this.objective)
                .add("value", this.value, Objects::nonNull)
                .toString();
    }

}
