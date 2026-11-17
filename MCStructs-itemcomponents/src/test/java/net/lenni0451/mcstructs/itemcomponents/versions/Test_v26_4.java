package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v26_2.NbtConverter_v26_2;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.Types_v1_21_11.Consumable;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_6.Types_v1_21_6.Equippable;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_4.ItemComponents_v26_4;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_4.Types_v26_4.DispenseBehavior;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_4.Types_v26_4.DispenseSound;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_4.Types_v26_4.FloatProvider;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_4.Types_v26_4.Vec3d;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.registry.Holder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class Test_v26_4 extends ItemComponentTest<ItemComponents_v26_4> {

    @Override
    protected ItemComponents_v26_4 getRegistry() {
        return ItemComponentRegistry.V26_4;
    }

    @Override
    protected DataConverter<NbtTag> getConverter() {
        return NbtConverter_v26_2.INSTANCE;
    }

    @Override
    protected void register(ItemComponents_v26_4 registry) {
        this.register(registry.CONSUMABLE, new Consumable(1.6F, Consumable.ItemUseAnimation.EAT, null, true, new ArrayList<>()));
        this.register(registry.EQUIPPABLE, new Equippable(Types_v1_21_2.EquipmentSlot.HEAD, null, Identifier.of("test"), null, null, true, true, true, false, true, registry.getRegistries().sound.getHolder(Identifier.of("test"))));
        this.register(registry.DISPENSE_BEHAVIOR, new Holder<>(Arrays.asList(
                new DispenseBehavior.Simple(DispenseBehavior.Type.SHEAR),
                new DispenseBehavior.ShootProjectile(new Vec3d(0, 0.1, 0), 0.7, 6F, 1.1F, new DispenseSound(
                        registry.getRegistries().sound.getHolder(Identifier.of("test")),
                        new FloatProvider.ConstantFloat(1F),
                        new FloatProvider.UniformFloat(0.8F, 1.2F)
                )),
                new DispenseBehavior.ShootProjectile(new Vec3d(1, 2, 3), 1, 0F, 2F),
                new DispenseBehavior.TransformBlock(registry.getRegistries().blockTransformer.getEntry(Identifier.of("test")))
        )));
    }

    @Test
    void testDispenseBehaviorReference() {
        Holder<?> holder = new Holder<>(this.getRegistry().getRegistries().dispenseBehavior.getEntry(Identifier.of("test")));
        NbtTag tag = this.getRegistry().DISPENSE_BEHAVIOR.getCodec().serialize(this.getConverter(), this.cast(holder)).get();
        assertTrue(tag.isStringTag());
        assertEquals(holder, this.getRegistry().DISPENSE_BEHAVIOR.getCodec().deserialize(this.getConverter(), tag).get());
    }

    @Test
    void testOptionalSounds() {
        CompoundTag consumable = (CompoundTag) this.getRegistry().CONSUMABLE.getCodec().serialize(this.getConverter(), new Consumable()).get();
        assertFalse(consumable.contains(Consumable.SOUND));
        assertNull(this.getRegistry().CONSUMABLE.getCodec().deserialize(this.getConverter(), new CompoundTag()).get().getSound());

        CompoundTag equippable = new CompoundTag().addString(Equippable.SLOT, "head");
        Equippable deserialized = this.getRegistry().EQUIPPABLE.getCodec().deserialize(this.getConverter(), equippable).get();
        assertNull(deserialized.getEquipSound());
        assertNull(deserialized.getShearingSound());
    }

    @Test
    void testComponentCount() {
        assertEquals(123, this.getRegistry().getComponentList().size());
    }

}
