package net.lenni0451.mcstructs.converter.codec;

import net.lenni0451.mcstructs.converter.model.Either;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EitherTest {

    @Test
    void map() {
        Either<String, Integer> left = Either.left("5");
        Either<Integer, String> leftMapped = left.map(Integer::parseInt, String::valueOf);
        assertTrue(leftMapped.isLeft());
        assertEquals(5, leftMapped.getLeft());

        Either<String, Integer> right = Either.right(10);
        Either<Integer, String> rightMapped = right.map(Integer::parseInt, String::valueOf);
        assertTrue(rightMapped.isRight());
        assertEquals("10", rightMapped.getRight());
    }

}
