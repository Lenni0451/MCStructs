package net.lenni0451.mcstructs.text.utils;

public enum Compatibility {

    V1_6,
    V1_7,
    V1_8,
    V1_9,
    V1_12,
    V1_14,
    V1_15,
    V1_16,
    V1_17,
    V1_18,
    V1_19_4,
    V1_20_3,
    V1_20_5,
    V1_21_2,
    V1_21_4,
    V1_21_5,
    ;

    public boolean isIn(final Compatibility... other) {
        for (Compatibility compatibility : other) {
            if (this == compatibility) return true;
        }
        return false;
    }

    public static Compatibility[] ranged(final Compatibility from, final Compatibility to) {
        int fromIndex = from == null ? 0 : from.ordinal();
        int toIndex = to == null ? values().length - 1 : to.ordinal();
        if (fromIndex > toIndex) throw new IllegalArgumentException("The 'from' version must be lower than the 'to' version");

        Compatibility[] compatibilities = new Compatibility[toIndex - fromIndex + 1];
        System.arraycopy(values(), fromIndex, compatibilities, 0, compatibilities.length);
        return compatibilities;
    }

}
