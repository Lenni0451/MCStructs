package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;

import java.util.ArrayList;
import java.util.List;

public class Types_v1_21_4 extends Types_v1_21_2 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomModelData {
        public static final String FLOATS = "floats";
        public static final String FLAGS = "flags";
        public static final String STRINGS = "strings";
        public static final String COLORS = "colors";

        private List<Float> floats = new ArrayList<>();
        private List<Boolean> flags = new ArrayList<>();
        private List<String> strings = new ArrayList<>();
        private List<Integer> colors = new ArrayList<>();
    }

}
