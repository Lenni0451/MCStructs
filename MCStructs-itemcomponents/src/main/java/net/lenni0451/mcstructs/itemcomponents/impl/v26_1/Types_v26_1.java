package net.lenni0451.mcstructs.itemcomponents.impl.v26_1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.Types_v1_21_5;
import net.lenni0451.mcstructs.registry.Holder;
import net.lenni0451.mcstructs.registry.TagEntryList;

import java.util.Collections;
import java.util.List;

public class Types_v26_1 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlocksAttacks {
        public static final String BLOCK_DELAY_SECONDS = "block_delay_seconds";
        public static final String DISABLE_COOLDOWN_SCALE = "disable_cooldown_scale";
        public static final String DAMAGE_REDUCTIONS = "damage_reductions";
        public static final String ITEM_DAMAGE = "item_damage";
        public static final String BYPASSED_BY = "bypassed_by";
        public static final String BLOCK_SOUND = "block_sound";
        public static final String DISABLED_SOUND = "disabled_sound";

        private float blockDelaySeconds = 0;
        private float disableCooldownScale = 1;
        private List<Types_v1_21_5.BlocksAttacks.DamageReduction> damageReductions = Collections.singletonList(new Types_v1_21_5.BlocksAttacks.DamageReduction(90, null, 0, 1));
        private Types_v1_21_5.BlocksAttacks.ItemDamageFunction itemDamage = null;
        private TagEntryList bypassedBy = null;
        private Holder<Types_v1_20_5.SoundEvent> blockSound = null;
        private Holder<Types_v1_20_5.SoundEvent> disabledSound = null;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DamageResistant {
        public static final String TYPES = "types";

        private TagEntryList types;
    }

}
