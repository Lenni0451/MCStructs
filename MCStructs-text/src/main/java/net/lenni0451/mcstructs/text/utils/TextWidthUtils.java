package net.lenni0451.mcstructs.text.utils;

import net.lenni0451.mcstructs.text.ATextComponent;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class TextWidthUtils {

    private static float[] charWidths = null;

    /**
     * Calculate the width of a component using the default widths.<br>
     * Minecraft takes the ceil of the width.<br>
     * <br>
     * This method reads the default char widths from the {@code charwidths.bin} resource.<br>
     * The widths array will be cached after the first call.
     *
     * @param component The component to calculate the width of
     * @return The width of the component
     * @throws IllegalStateException If the charwidths.bin resource could not be found
     * @throws RuntimeException      If the charwidths.bin resource could not be read
     */
    public static float getComponentWidth(final ATextComponent component) {
        if (charWidths == null) {
            InputStream is = TextUtils.class.getResourceAsStream("/mcstructs/text/charwidths.bin");
            if (is == null) throw new IllegalStateException("Could not find charwidths.bin");
            try (GZIPInputStream gis = new GZIPInputStream(is)) {
                charWidths = new float[gis.read() << 24 | gis.read() << 16 | gis.read() << 8 | gis.read()];
                for (int i = 0; i < charWidths.length; i++) charWidths[i] = gis.read();
            } catch (IOException e) {
                throw new RuntimeException("Failed to read char widths", e);
            }
        }
        return getComponentWidth(component, charWidths, 1);
    }

    /**
     * Calculate the width of a component using the given widths array.<br>
     * Minecraft takes the ceil of the width.
     *
     * @param component The component to calculate the width of
     * @param widths    The char widths array
     * @return The width of the component
     */
    public static float getComponentWidth(final ATextComponent component, final float[] widths) {
        return getComponentWidth(component, widths, 1);
    }

    /**
     * Calculate the width of a component using the given widths array.<br>
     * The bold offset is 1 for the vanilla font and 0.5 for unihex fonts.<br>
     * Minecraft takes the ceil of the width.
     *
     * @param component  The component to calculate the width of
     * @param widths     The char widths array
     * @param boldOffset The bold offset
     * @return The width of the component
     */
    public static float getComponentWidth(final ATextComponent component, final float[] widths, final float boldOffset) {
        float[] width = new float[]{0};
        component.forEach(comp -> {
            char[] chars = comp.asSingleString().toCharArray();
            for (char c : chars) width[0] += c >= widths.length ? 0 : widths[c];
            if (comp.getStyle().isBold()) width[0] += boldOffset * chars.length;
        });
        return width[0];
    }

}
