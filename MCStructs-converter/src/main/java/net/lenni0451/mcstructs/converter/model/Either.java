package net.lenni0451.mcstructs.converter.model;

import lombok.EqualsAndHashCode;

import javax.annotation.Nonnull;
import java.util.function.Function;

public interface Either<L, R> {

    static <L, R> Either<L, R> left(@Nonnull L left) {
        return new Left<>(left);
    }

    static <L, R> Either<L, R> right(@Nonnull R right) {
        return new Right<>(right);
    }

    static <T> T unwrap(final Either<? extends T, ? extends T> either) {
        return either.isLeft() ? either.getLeft() : either.getRight();
    }


    L getLeft();

    R getRight();

    boolean isLeft();

    boolean isRight();

    <ML, MR> Either<ML, MR> map(final Function<L, ML> leftMapper, final Function<R, MR> rightMapper);

    <T> T xmap(final Function<L, T> leftMapper, final Function<R, T> rightMapper);

    Either<R, L> swap();

    @Override
    String toString();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();


    @EqualsAndHashCode(callSuper = false)
    class Left<L, R> implements Either<L, R> {
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

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public <ML, MR> Either<ML, MR> map(Function<L, ML> leftMapper, Function<R, MR> rightMapper) {
            return Either.left(leftMapper.apply(this.getLeft()));
        }

        @Override
        public <T> T xmap(Function<L, T> leftMapper, Function<R, T> rightMapper) {
            return leftMapper.apply(this.getLeft());
        }

        @Override
        public Either<R, L> swap() {
            return Either.right(this.left);
        }

        @Override
        public String toString() {
            return "Left{" + this.getLeft() + "}";
        }
    }

    @EqualsAndHashCode(callSuper = false)
    class Right<L, R> implements Either<L, R> {
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

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public <ML, MR> Either<ML, MR> map(Function<L, ML> leftMapper, Function<R, MR> rightMapper) {
            return Either.right(rightMapper.apply(this.right));
        }

        @Override
        public <T> T xmap(Function<L, T> leftMapper, Function<R, T> rightMapper) {
            return rightMapper.apply(this.right);
        }

        @Override
        public Either<R, L> swap() {
            return Either.left(this.right);
        }

        @Override
        public String toString() {
            return "Right{" + this.getRight() + "}";
        }
    }

}
