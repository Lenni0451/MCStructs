package net.lenni0451.mcstructs.recipes;

import net.lenni0451.mcstructs.items.AItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The wrapper class for villager recipes.
 *
 * @param <I> The type of the item (e.g. Integer)
 * @param <S> The type of the item stack (e.g. LegacyItemStack)
 */
public class VillagerRecipe<I, S extends AItemStack<I, S>> {

    private final S input1;
    private final S input2;
    private final S output;
    private final boolean enabled;

    public VillagerRecipe(@Nonnull final S input1, @Nullable final S input2, @Nonnull final S output, final boolean enabled) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.enabled = enabled;
    }

    /**
     * @return The first input item stack
     */
    public S getInput1() {
        return this.input1;
    }

    /**
     * @return The second input item stack
     */
    @Nullable
    public S getInput2() {
        return this.input2;
    }

    /**
     * @return If the second input item stack is not null
     */
    public boolean hasInput2() {
        return this.input2 != null;
    }

    /**
     * @return The output item stack
     */
    public S getOutput() {
        return this.output;
    }

    /**
     * @return If the recipe is enabled
     */
    public boolean isEnabled() {
        return this.enabled;
    }

}
