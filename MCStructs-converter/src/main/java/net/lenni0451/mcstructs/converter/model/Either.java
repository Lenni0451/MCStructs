package net.lenni0451.mcstructs.converter.model;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Function;

public class Either<L, R> {

    public static <L, R> Either<L, R> left(@Nonnull L left) {
        return new Either<>(left, null);
    }

    public static <L, R> Either<L, R> right(@Nonnull R right) {
        return new Either<>(null, right);
    }

    public static <T> T unwrap(final Either<? extends T, ? extends T> either) {
        return either.isLeft() ? either.getLeft() : either.getRight();
    }


    private final L left;
    private final R right;

    private Either(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return this.left;
    }

    public R getRight() {
        return this.right;
    }

    public boolean isLeft() {
        return this.left != null;
    }

    public boolean isRight() {
        return this.right != null;
    }

    public <ML, MR> Either<ML, MR> map(final Function<L, ML> leftMapper, final Function<R, MR> rightMapper) {
        if (this.isLeft()) {
            return Either.left(leftMapper.apply(this.left));
        } else {
            return Either.right(rightMapper.apply(this.right));
        }
    }

    public <T> T xmap(final Function<L, T> leftMapper, final Function<R, T> rightMapper) {
        return this.isLeft() ? leftMapper.apply(this.left) : rightMapper.apply(this.right);
    }

    public Either<R, L> swap() {
        if (this.isLeft()) {
            return Either.right(this.left);
        } else {
            return Either.left(this.right);
        }
    }

    @Override
    public String toString() {
        if (this.isLeft()) {
            return "Left{" + this.left + "}";
        } else {
            return "Right{" + this.right + "}";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Either<?, ?> either = (Either<?, ?>) o;
        return Objects.equals(this.left, either.left) && Objects.equals(this.right, either.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.left, this.right);
    }

}
