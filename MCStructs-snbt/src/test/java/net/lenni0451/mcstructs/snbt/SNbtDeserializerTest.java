package net.lenni0451.mcstructs.snbt;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SNbtDeserializerTest {

    protected static final String FAIL = "{}{}"; //This fails the deserialization with an SNbtDeserializeException thrown in every deserializer
    protected static final String[] TESTS = {
            "{id:5}",
            "{id:5b}",
            "{id:hello,test:1,}",
            "{id:[3.2,64.5,129.5]}",
            "{id:[1,2, 3, 4,5]}",
            "{id:[I;1,2, 3, 4,5]}",
            "{id:1b,b:true}",
            "{id:[L;1l,2L,3L]}",
            "{id:[I;1i,2I,3I]}",
            "{id:minecraft:stone}",
            "{id:'minecraft:stone'}",
            "{id:1,id:2}",
            "{id:-20b,test:3.19f}",
            "{id:9000b,int:2147483649}",
            "{id:[I;1,2,3,]}",
            "{id:[1,2,3,]}",
            "{id:1337|}",
            "{id:\"Hello\"}",
            "{\"id\":\"Hello\"}",
            "{'id':\"Hello\"}",
            "{'id':'Hello'}",
            "{test:[{a:[0,1,2]},{pos:1337}]}",
            "{test:[1,2,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,3]}",
            "{test:[I;]}",
            "{test:\"Test\\\\String\"}",
            "{test:\"Test\\String\"}"
    };

    protected static void executeTests(final SNbtSerializer<?> serializer, final String[] expectedResults) {
        assertEquals(TESTS.length, expectedResults.length);

        for (int i = 0; i < TESTS.length; i++) {
            String test = TESTS[i];
            INbtTag expected = serializer.tryDeserialize(expectedResults[i]);

            try {
                INbtTag result = serializer.deserialize(test);
                assertEquals(expected, result, "Test " + i + " failed: " + test);
            } catch (SNbtDeserializeException e) {
                assertNull(expected, "Test " + i + " failed: " + test);
            }
        }
    }

}
