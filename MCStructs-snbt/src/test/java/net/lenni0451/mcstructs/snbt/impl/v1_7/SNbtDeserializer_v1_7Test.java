package net.lenni0451.mcstructs.snbt.impl.v1_7;

import net.lenni0451.mcstructs.snbt.SNbtDeserializerTest;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import org.junit.jupiter.api.Test;

class SNbtDeserializer_v1_7Test extends SNbtDeserializerTest {

    private static final String[] expectedResults = {
            "{id:5,}",
            "{id:5b,}",
            "{test:1,id:\"hello\",}",
            "{id:[0:3.2d,1:64.5d,2:129.5d,],}",
            "{id:[1,2,3,4,5,],}",
            "{id:[0:\"I;1\",],}",
            "{b:1b,id:1b,}",
            "{id:[0:\"L;1l\",],}",
            "{id:[0:\"I;1i\",1:\"2I\",2:\"3I\",],}",
            "{id:\"minecraft:stone\",}",
            "{id:\"'minecraft:stone'\",}",
            "{id:2,}",
            "{test:3.19f,id:-20b,}",
            "{id:\"9000b\",int:\"2147483649\",}",
            "{id:[0:\"I;1\",],}",
            "{id:[1,2,3,],}",
            "{id:1337.0d,}",
            "{id:\"Hello\",}",
            "{\"id\":\"Hello\",}",
            "{'id':\"Hello\",}",
            "{'id':\"'Hello'\",}",
            "{test:[0:{a:[0,1,2,],},1:{pos:1337,},],}",
            "{test:\"[1,2,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,3]\",}",
            "{test:[0:\"I;\",],}",
            "{test:\"Test\\\\String\"}",
            "{test:\"Test\\String\"}"
    };

    @Test
    void runTests() {
        executeTests(SNbtSerializer.V1_7, expectedResults);
    }

}