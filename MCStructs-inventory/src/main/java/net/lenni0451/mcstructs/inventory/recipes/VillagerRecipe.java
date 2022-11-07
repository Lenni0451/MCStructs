package net.lenni0451.mcstructs.inventory.recipes;

import net.lenni0451.mcstructs.items.AItemStack;

public class VillagerRecipe<I, S extends AItemStack<I, S>> {

    private final S input1;
    private final S input2;
    private final S output;
    private final boolean enabled;

    public VillagerRecipe(final S input1, final S input2, final S output, final boolean enabled) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.enabled = enabled;
    }

    public S getInput1() {
        return this.input1;
    }

    public S getInput2() {
        return this.input2;
    }

    public boolean hasInput2() {
        return this.input2 != null;
    }

    public S getOutput() {
        return this.output;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

}
