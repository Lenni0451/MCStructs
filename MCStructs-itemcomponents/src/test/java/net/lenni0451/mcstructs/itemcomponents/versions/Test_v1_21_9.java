package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v1_21_5.NbtConverter_v1_21_5;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9.ItemComponents_v1_21_9;
import net.lenni0451.mcstructs.nbt.NbtTag;

import java.util.HashMap;
import java.util.UUID;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9.Types_v1_21_9.*;

public class Test_v1_21_9 extends ItemComponentTest<ItemComponents_v1_21_9> {

    @Override
    protected ItemComponents_v1_21_9 getRegistry() {
        return ItemComponentRegistry.V1_21_9;
    }

    @Override
    protected DataConverter<NbtTag> getConverter() {
        return NbtConverter_v1_21_5.INSTANCE;
    }

    @Override
    protected void register(ItemComponents_v1_21_9 registry) {
        register(registry.PROFILE, new ResolvableProfile(new Types_v1_20_5.GameProfile("Hello", UUID.randomUUID(), new HashMap<>()), new PlayerSkinPatch(new ResourceTexture(Identifier.of("test1")), new ResourceTexture(Identifier.of("test2")), new ResourceTexture(Identifier.of("test3")), PlayerModelType.WIDE)));
    }

}
