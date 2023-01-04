package net.lenni0451.mcstructs.snbt.impl.v1_12;

import net.lenni0451.mcstructs.snbt.SNbtDeserializerTest;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import org.junit.jupiter.api.Test;

class SNbtDeserializer_v1_12Test extends SNbtDeserializerTest {

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
            FAIL,
            "{id:2}",
            "{test:3.19f,id:-20b}",
            "{id:\"9000b\",int:\"2147483649\"}",
            "{id:[I;1,2,3]}",
            "{id:[1,2,3]}",
            FAIL,
            "{id:\"Hello\"}",
            "{id:\"Hello\"}",
            FAIL,
            FAIL,
            "{test:[{a:[0,1,2]},{pos:1337}]}",
            FAIL,
            "{test:[I;]}",
            "{test:\"Test\\\\String\"}",
            "{test:\"Test\\String\"}"
    };

    @Test
    void runTests() {
        executeTests(SNbtSerializer.V1_12, expectedResults);
    }

}