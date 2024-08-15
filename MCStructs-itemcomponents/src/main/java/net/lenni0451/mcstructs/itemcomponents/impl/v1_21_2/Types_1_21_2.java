package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;

public class Types_1_21_2 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Enchantable {
        public static final String VALUE = "value";

        private int value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Repairable {
        public static final String ITEMS = "items";

        private Types_v1_20_5.TagEntryList items;
    }

}
