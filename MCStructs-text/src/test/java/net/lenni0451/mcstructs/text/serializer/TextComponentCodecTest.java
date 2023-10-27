package net.lenni0451.mcstructs.text.serializer;

import com.google.gson.JsonElement;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.ScoreComponent;
import net.lenni0451.mcstructs.text.components.SelectorComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.components.TranslationComponent;
import net.lenni0451.mcstructs.text.components.nbt.BlockNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.EntityNbtComponent;
import net.lenni0451.mcstructs.text.components.nbt.StorageNbtComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TextComponentCodecTest {

    private final ATextComponent text = new StringComponent("test")
            .append(new TranslationComponent("translation", "arg1", 2))
            .append(new ScoreComponent("name", "objective"))
            .append(new SelectorComponent("selector", new StringComponent("separator")))
            .append(new BlockNbtComponent("raw", true, new StringComponent("separator"), "pos"))
            .append(new EntityNbtComponent("raw", true, new StringComponent("separator"), "selector"))
            .append(new StorageNbtComponent("raw", true, new StringComponent("separator"), Identifier.of("namespace", "id")));

    @Test
    void serializeDeserializeJson() {
        JsonElement json = TextComponentCodec.LATEST.serializeJsonTree(this.text);
        ATextComponent deserialized = TextComponentCodec.LATEST.deserializeJsonTree(json);
        Assertions.assertEquals(this.text, deserialized);
    }

    @Test
    void serializeDeserializeNbt() {
        INbtTag nbt = TextComponentCodec.LATEST.serializeNbt(this.text);
        ATextComponent deserialized = TextComponentCodec.LATEST.deserializeNbtTree(nbt);
        Assertions.assertEquals(this.text, deserialized);
    }

}
