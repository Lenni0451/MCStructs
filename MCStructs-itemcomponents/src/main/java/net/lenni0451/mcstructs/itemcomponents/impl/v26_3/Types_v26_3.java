package net.lenni0451.mcstructs.itemcomponents.impl.v26_3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.Types_v1_21_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_2.Types_v26_2.ItemStackTemplate;
import net.lenni0451.mcstructs.registry.Holder;
import net.lenni0451.mcstructs.registry.ResourceKey;
import net.lenni0451.mcstructs.registry.TagEntryList;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;
import java.util.List;

public class Types_v26_3 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArmorTrimMaterial {
        public static final String PALETTE_ID = "palette_id";
        public static final String DESCRIPTION = "description";

        private Identifier paletteId;
        private TextComponent description;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArmorTrim {
        public static final String MATERIAL = "material";
        public static final String PATTERN = "pattern";

        private Holder<ArmorTrimMaterial> material;
        private Holder<Types_v1_21_5.ArmorTrimPattern> pattern;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Instrument {
        public static final String SOUND_EVENT = "sound_event";
        public static final String USE_DURATION = "use_duration";
        public static final String RANGE = "range";
        public static final String DURABILITY_DAMAGE = "durability_damage";
        public static final String DESCRIPTION = "description";

        private Holder<Types_v1_20_5.SoundEvent> soundEvent;
        private float useDuration;
        private float range;
        private int durabilityDamage = 0;
        private TextComponent description;

        public Instrument(final Holder<Types_v1_20_5.SoundEvent> soundEvent, final float useDuration, final float range, final TextComponent description) {
            this(soundEvent, useDuration, range, 0, description);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PotDecorations {
        public static final String BACK = "back";
        public static final String LEFT = "left";
        public static final String RIGHT = "right";
        public static final String FRONT = "front";

        @Nullable
        private ItemStackTemplate back;
        @Nullable
        private ItemStackTemplate left;
        @Nullable
        private ItemStackTemplate right;
        @Nullable
        private ItemStackTemplate front;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResolvableInt {
        private Integer value;
        private ResourceKey key;

        public ResolvableInt(final int value) {
            this.value = value;
        }

        public ResolvableInt(final ResourceKey key) {
            this.key = key;
        }

        public boolean isConstant() {
            return this.value != null;
        }

        public static ResolvableInt of(final int value) {
            return new ResolvableInt(value);
        }

        public static ResolvableInt of(final ResourceKey key) {
            return new ResolvableInt(key);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResolvableFloat {
        private Float value;
        private ResourceKey key;

        public ResolvableFloat(final float value) {
            this.value = value;
        }

        public ResolvableFloat(final ResourceKey key) {
            this.key = key;
        }

        public boolean isConstant() {
            return this.value != null;
        }

        public static ResolvableFloat of(final float value) {
            return new ResolvableFloat(value);
        }

        public static ResolvableFloat of(final ResourceKey key) {
            return new ResolvableFloat(key);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Compostable {
        public static final String LAYERS = "layers";

        private ResolvableInt layers;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CookingFuel {
        public static final String BURN_TIME = "burn_time";
        public static final String SPEED_MULTIPLIER = "speed_multiplier";

        private ResolvableInt burnTime;
        private ResolvableFloat speedMultiplier;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrewingFuel {
        public static final String USES = "uses";
        public static final String SPEED_MULTIPLIER = "speed_multiplier";

        private ResolvableInt uses;
        private ResolvableFloat speedMultiplier;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VillagerFood {
        public static final String NUTRITION = "nutrition";

        private int nutrition;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MobVisibility {
        public static final String TARGETING_ENTITY_TYPES = "targeting_entity_types";
        public static final String VISIBILITY = "visibility";

        private TagEntryList targetingEntityTypes;
        private float visibility;
    }

    @Data
    @NoArgsConstructor
    public static class SignText {
        public static final String MESSAGES = "messages";
        public static final String FILTERED_MESSAGES = "filtered_messages";
        public static final String COLOR = "color";
        public static final String HAS_GLOWING_TEXT = "has_glowing_text";

        private List<TextComponent> messages;
        private List<TextComponent> filteredMessages;
        private Types_v1_20_5.DyeColor color = Types_v1_20_5.DyeColor.BLACK;
        private boolean hasGlowingText = false;

        public SignText(final List<TextComponent> messages) {
            this.messages = messages;
            this.filteredMessages = messages;
        }

        public SignText(final List<TextComponent> messages, @Nullable final List<TextComponent> filteredMessages) {
            this.messages = messages;
            this.filteredMessages = filteredMessages != null ? filteredMessages : messages;
        }

        public SignText(final List<TextComponent> messages, @Nullable final List<TextComponent> filteredMessages, @Nullable final Types_v1_20_5.DyeColor color, final boolean hasGlowingText) {
            this.messages = messages;
            this.filteredMessages = filteredMessages != null ? filteredMessages : messages;
            this.color = color != null ? color : Types_v1_20_5.DyeColor.BLACK;
            this.hasGlowingText = hasGlowingText;
        }

        public List<TextComponent> getFilteredMessagesForSerialization() {
            if (this.filteredMessages == null || this.filteredMessages.equals(this.messages)) return null;
            return this.filteredMessages;
        }

        public void setFilteredMessages(@Nullable final List<TextComponent> filteredMessages) {
            this.filteredMessages = filteredMessages != null ? filteredMessages : this.messages;
        }

        public void setColor(@Nullable final Types_v1_20_5.DyeColor color) {
            this.color = color != null ? color : Types_v1_20_5.DyeColor.BLACK;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class TeleportRandomly implements Types_v1_21_2.ConsumeEffect {
        public static final String DIAMETER = "diameter";
        public static final String DIRECTIONAL_PARTICLES = "directional_particles";

        private final Type type = Type.TELEPORT_RANDOMLY;
        private float diameter = 16F;
        private boolean directionalParticles = true;

        public TeleportRandomly(final float diameter) {
            this.diameter = diameter;
        }

        @Override
        public Type getType() {
            return this.type;
        }
    }

}
