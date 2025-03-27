package net.lenni0451.mcstructs.snbt.impl.v1_21_5;

import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.ListTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.snbt.SNbtDeserializerTest;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SNbtDeserializer_v1_21_5Test extends SNbtDeserializerTest {

    private static final String[] expectedResults = {
            "{id:5}",
            "{id:5b}",
            "{test:1,id:\"hello\"}",
            "{id:[3.2d,64.5d,129.5d]}",
            "{id:[1,2,3,4,5]}",
            "{id:[I;1,2,3,4,5]}",
            "{b:1b,id:1b}",
            "{id:[L;1L,2L,3L]}",
            FAIL,
            FAIL,
            "{id:\"minecraft:stone\"}",
            "{id:2}",
            "{test:3.19f,id:-20b}",
            "{id:\"9000b\",int:\"2147483649\"}",
            "{id:[I;1,2,3]}",
            "{id:[1,2,3]}",
            FAIL,
            "{id:\"Hello\"}",
            "{id:\"Hello\"}",
            "{id:\"Hello\"}",
            "{id:\"Hello\"}",
            "{test:[{a:[0,1,2]},{pos:1337}]}",
            FAIL,
            "{test:[I;]}",
            "{test:\"Test\\\\String\"}",
            "{test:\"Test\\String\"}",
            FAIL,
    };

    @Test
    void runTests() {
        executeTests(SNbt.V1_21_5, expectedResults);
    }

    @Test
    void testMarkerWrapping() throws SNbtDeserializeException {
        assertEquals(new CompoundTag().add("test", new ListTag<>()
                        .add(new CompoundTag().add("", "a"))
                        .add(new CompoundTag().add("", 1))
                        .add(new CompoundTag())),
                SNbt.V1_21_5.deserialize("{test:[\"a\",1,{}]}")
        );
    }

}
