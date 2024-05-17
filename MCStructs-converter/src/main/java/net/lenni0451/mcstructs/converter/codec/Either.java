package net.lenni0451.mcstructs.converter.codec;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@ToString
@EqualsAndHashCode
public class Either<L, R> {

    public static <L, R> Either<L, R> left(@Nonnull L left) {
        return new Either<>(left, null);
    }

    public static <L, R> Either<L, R> right(@Nonnull R right) {
        return new Either<>(null, right);
    }


    @Nullable
    private final L left;
    @Nullable
    private final R right;

    private Either(@Nullable L left, @Nullable R right) {
        this.left = left;
        this.right = right;
    }

    public boolean isLeft() {
        return this.left != null;
    }

    public boolean isRight() {
        return this.right != null;
    }

}
