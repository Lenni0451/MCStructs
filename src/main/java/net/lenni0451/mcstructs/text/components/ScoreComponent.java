package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.text.ATextComponent;

public class ScoreComponent extends ATextComponent {

    private final String name;
    private final String objective;

    private String value;

    public ScoreComponent(final String name, final String objective) {
        this.name = name;
        this.objective = objective;
    }

    public String getName() {
        return this.name;
    }

    public String getObjective() {
        return this.objective;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String asString() {
        return this.value;
    }

    @Override
    public ATextComponent copy() {
        return new ScoreComponent(this.name, this.objective);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }

}
