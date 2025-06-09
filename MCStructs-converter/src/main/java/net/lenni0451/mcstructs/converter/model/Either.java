package net.lenni0451.mcstructs.converter.model;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Function;

public abstract class Either<L, R> {

    public static <L, R> Either<L, R> left(@Nonnull L left) {
        return new Left<>(left);
    }

    public static <L, R> Either<L, R> right(@Nonnull R right) {
        return new Right<>(right);
    }

    public static <T> T unwrap(final Either<? extends T, ? extends T> either) {
        return either.isLeft() ? either.getLeft() : either.getRight();
    }


    public abstract L getLeft();

    public abstract R getRight();

    public boolean isLeft() {
        return this.getLeft() != null;
    }

    public boolean isRight() {
        return this.getRight() != null;
    }

    public <ML, MR> Either<ML, MR> map(final Function<L, ML> leftMapper, final Function<R, MR> rightMapper) {
        if (this.isLeft()) {
            return Either.left(leftMapper.apply(this.getLeft()));
        } else {
            return Either.right(rightMapper.apply(this.getRight()));
        }
    }

    public <T> T xmap(final Function<L, T> leftMapper, final Function<R, T> rightMapper) {
        return this.isLeft() ? leftMapper.apply(this.getLeft()) : rightMapper.apply(this.getRight());
    }

    public Either<R, L> swap() {
        if (this.isLeft()) {
            return Either.right(this.getLeft());
        } else {
            return Either.left(this.getRight());
        }
    }

    @Override
    public String toString() {
        if (this.isLeft()) {
            return "Left{" + this.getLeft() + "}";
        } else {
            return "Right{" + this.getRight() + "}";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Either<?, ?> either = (Either<?, ?>) o;
        return Objects.equals(this.getLeft(), either.getLeft()) && Objects.equals(this.getRight(), either.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getLeft(), this.getRight());
    }

    private static final class Left<L, R> extends Either<L, R> {
        private final L left;

        private Left(final L left) {
            this.left = left;
        }

        @Override
        public L getLeft() {
            return this.left;
        }

        @Override
        public R getRight() {
            return null;
        }
    }

    private static final class Right<L, R> extends Either<L, R> {
        private final R right;

        private Right(final R right) {
            this.right = right;
        }

        @Override
        public L getLeft() {
            return null;
        }

        @Override
        public R getRight() {
            return this.right;
        }
    }

}
