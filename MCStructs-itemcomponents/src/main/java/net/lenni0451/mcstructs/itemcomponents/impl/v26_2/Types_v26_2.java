package net.lenni0451.mcstructs.itemcomponents.impl.v26_2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.registry.RegistryEntry;

public class Types_v26_2 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemStackTemplate {
        public static final String ID = "id";
        public static final String COUNT = "count";
        public static final String COMPONENTS = "components";

        private RegistryEntry id;
        private int count = 1;
        private ItemComponentMap components;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SulfurCubeContent {
        private ItemStackTemplate absorbedBlockItemStack;
    }

}
