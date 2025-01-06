package net.lenni0451.mcstructs.text.stringformat.handling;

import net.lenni0451.mcstructs.text.stringformat.StringFormat;

/**
 * Option to modify the behavior of how colors are handled in a {@link StringFormat}.
 */
public enum ColorHandling {

    /**
     * Treat colors like a reset for all formattings (like bold, italic, etc.).<br>
     * This is the default behavior for Minecraft.
     */
    RESET,
    /**
     * Treat colors like regular formatting.<br>
     * Other formattings like bold, italic, etc. will not be reset when a color is found.<br>
     * This is similar to the behavior of Minecraft Bedrock Edition.
     */
    FORMATTING,

}
