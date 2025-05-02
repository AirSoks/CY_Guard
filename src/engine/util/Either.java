package engine.util;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A container representing a value of one of two possible types (a disjoint
 * union).
 * An instance of Either is either a Left (containing a value of type L) or
 * a Right (containing a value of type R).
 * <p>
 * By convention:
 * - Left is typically used to represent a failure or error case
 * - Right is typically used to represent a success case
 *
 * @param <L> the type of the left value
 * @param <R> the type of the right value
 */
public abstract class Either<L, R> {

    /**
     * Hidden constructor to prevent direct instantiation.
     */
    private Either() {
    }

    /**
     * Creates a Left instance containing the given value.
     *
     * @param <L>   the left type
     * @param <R>   the right type
     * @param value the left value
     * @return a new Left containing the value
     */
    public static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    /**
     * Creates a Right instance containing the given value.
     *
     * @param <L>   the left type
     * @param <R>   the right type
     * @param value the right value
     * @return a new Right containing the value
     */
    public static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }

    /**
     * Checks if this instance is a Left.
     *
     * @return true if this is a Left, false otherwise
     */
    public abstract boolean isLeft();

    /**
     * Checks if this instance is a Right.
     *
     * @return true if this is a Right, false otherwise
     */
    public abstract boolean isRight();

    /**
     * Gets the value from this Left.
     *
     * @return the left value
     * @throws UnsupportedOperationException if this is a Right
     */
    public abstract L getLeft();

    /**
     * Gets the value from this Right.
     *
     * @return the right value
     * @throws UnsupportedOperationException if this is a Left
     */
    public abstract R getRight();

    /**
     * Maps the right value using the given function.
     *
     * @param <T>    the result type
     * @param mapper the mapping function
     * @return a new Either with the mapped right value if this is a Right, or the
     *         same Left otherwise
     */
    public abstract <T> Either<L, T> map(Function<R, T> mapper);

    /**
     * Maps the left value using the given function.
     *
     * @param <T>    the result type
     * @param mapper the mapping function
     * @return a new Either with the mapped left value if this is a Left, or the
     *         same Right otherwise
     */
    public abstract <T> Either<T, R> mapLeft(Function<L, T> mapper);

    /**
     * Executes the given consumer if this is a Right, otherwise does nothing.
     *
     * @param consumer the consumer to execute with the right value
     * @return this Either instance for method chaining
     */
    public abstract Either<L, R> ifRight(Consumer<R> consumer);

    /**
     * Executes the given consumer if this is a Left, otherwise does nothing.
     *
     * @param consumer the consumer to execute with the left value
     * @return this Either instance for method chaining
     */
    public abstract Either<L, R> ifLeft(Consumer<L> consumer);

    /**
     * Applies one of the two provided functions depending on whether this is a Left
     * or Right.
     *
     * @param <T>     the result type
     * @param leftFn  function to apply if this is a Left
     * @param rightFn function to apply if this is a Right
     * @return the result of applying the appropriate function
     */
    public abstract <T> T fold(Function<L, T> leftFn, Function<R, T> rightFn);

    /**
     * Folds without returning a value.
     */
    public void consume(Consumer<L> leftFn, Consumer<R> rightFn) {
        if (isLeft()) {
            leftFn.accept(getLeft());
        } else {
            rightFn.accept(getRight());
        }
    }

    /**
     * The Left implementation of Either.
     *
     * @param <L> the left type
     * @param <R> the right type
     */
    public static final class Left<L, R> extends Either<L, R> {
        private final L value;

        private Left(L value) {
            this.value = value;
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
        public L getLeft() {
            return value;
        }

        @Override
        public R getRight() {
            throw new UnsupportedOperationException("Right value is not present.");
        }

        @Override
        public <T> Either<L, T> map(Function<R, T> mapper) {
            return Either.left(value);
        }

        @Override
        public <T> Either<T, R> mapLeft(Function<L, T> mapper) {
            return Either.left(mapper.apply(value));
        }

        @Override
        public Either<L, R> ifRight(Consumer<R> consumer) {
            // Do nothing as this is a Left
            return this;
        }

        @Override
        public Either<L, R> ifLeft(Consumer<L> consumer) {
            consumer.accept(value);
            return this;
        }

        @Override
        public <T> T fold(Function<L, T> leftFn, Function<R, T> rightFn) {
            return leftFn.apply(value);
        }

        @Override
        public String toString() {
            return "Left(" + value + ")";
        }
    }

    /**
     * The Right implementation of Either.
     *
     * @param <L> the left type
     * @param <R> the right type
     */
    public static final class Right<L, R> extends Either<L, R> {
        private final R value;

        private Right(R value) {
            this.value = value;
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
        public L getLeft() {
            throw new UnsupportedOperationException("Left value is not present.");
        }

        @Override
        public R getRight() {
            return value;
        }

        @Override
        public <T> Either<L, T> map(Function<R, T> mapper) {
            return Either.right(mapper.apply(value));
        }

        @Override
        public <T> Either<T, R> mapLeft(Function<L, T> mapper) {
            return Either.right(value);
        }

        @Override
        public Either<L, R> ifRight(Consumer<R> consumer) {
            consumer.accept(value);
            return this;
        }

        @Override
        public Either<L, R> ifLeft(Consumer<L> consumer) {
            // Do nothing as this is a Right
            return this;
        }

        @Override
        public <T> T fold(Function<L, T> leftFn, Function<R, T> rightFn) {
            return rightFn.apply(value);
        }

        @Override
        public String toString() {
            return "Right(" + value + ")";
        }
    }
}