/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.lang3.function;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Failable {
    public static <T, R, E extends Throwable> R applyNonNull(final T value, final FailableFunction<? super T, ? extends R, E> mapper) throws E {
        return value != null ? Objects.requireNonNull(mapper, "mapper").apply(value) : null;
    }
    public static <T, U, R, E1 extends Throwable, E2 extends Throwable> R applyNonNull(final T value1,
            final FailableFunction<? super T, ? extends U, E1> mapper1, final FailableFunction<? super U, ? extends R, E2> mapper2) throws E1, E2 {
        return applyNonNull(applyNonNull(value1, mapper1), mapper2);
    }
    public static <E extends Throwable> boolean getAsBoolean(final FailableBooleanSupplier<E> supplier) {
        try {
            return supplier.getAsBoolean();
        } catch (final Throwable t) {
            throw rethrow(t);
        }
    }


    /**
     * Invokes an int supplier, and returns the result.
     *
     * @param supplier The int supplier to invoke.
     * @param <E> The type of checked exception, which the supplier can throw.
     * @return The int, which has been created by the supplier
     */
    public static <E extends Throwable> int getAsInt(final FailableIntSupplier<E> supplier) {
        try {
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            return supplier.getAsInt();
        } catch (final Throwable t) {
            throw rethrow(t);
        }
    }

    /**
     * Invokes a long supplier, and returns the result.
     *
     * @param supplier The long supplier to invoke.
     * @param <E> The type of checked exception, which the supplier can throw.
     * @return The long, which has been created by the supplier
     */
    public static <E extends Throwable> long getAsLong(final FailableLongSupplier<E> supplier) {
        try {
            return supplier.getAsLong();
        } catch (final Throwable t) {
            throw rethrow(t);
        }
    }

    /**
     * Invokes a short supplier, and returns the result.
     *
     * @param supplier The short supplier to invoke.
     * @param <E> The type of checked exception, which the supplier can throw.
     * @return The short, which has been created by the supplier
     */
    public static <E extends Throwable> short getAsShort(final FailableShortSupplier<E> supplier) {
        try {
            return supplier.getAsShort();
        } catch (final Throwable t) {
            throw rethrow(t);
        }
    }

    /**
     * Rethrows a {@link Throwable} as an unchecked exception. If the argument is already unchecked, namely a
     * {@link RuntimeException} or {@link Error} then the argument will be rethrown without modification. If the
     * exception is {@link IOException} then it will be wrapped into a {@link UncheckedIOException}. In every other
     * cases the exception will be wrapped into a {@code
     * UndeclaredThrowableException}
     *
     * <p>
     * Note that there is a declared return type for this method, even though it never returns. The reason for that is
     * to support the usual pattern:
     * </p>
     *
     * <pre>
     * throw rethrow(myUncheckedException);
     * </pre>
     *
     * <p>
     * instead of just calling the method. This pattern may help the Java compiler to recognize that at that point an
     * exception will be thrown and the code flow analysis will not demand otherwise mandatory commands that could
     * follow the method call, like a {@code return} statement from a value returning method.
     * </p>
     *
     * @param throwable The throwable to rethrow possibly wrapped into an unchecked exception
     * @return Never returns anything, this method never terminates normally.
     */
    public static RuntimeException rethrow(final Throwable throwable) {
        Objects.requireNonNull(throwable, "throwable");
        ExceptionUtils.throwUnchecked(throwable);
        if (throwable instanceof IOException) {
            throw new UncheckedIOException((IOException) throwable);
        }
        throw new UndeclaredThrowableException(throwable);
    }

    /**
     * Runs a runnable and rethrows any exception as a {@link RuntimeException}.
     *
     * @param runnable The runnable to run, may be null for a noop.
     * @param <E> the type of checked exception the runnable may throw.
     */
    public static <E extends Throwable> void run(final FailableRunnable<E> runnable) {
        if (runnable != null) {
            try {
                runnable.run();
            } catch (final Throwable t) {
                throw rethrow(t);
            }
        }
    }

    private static <E extends Throwable> void run(final Object test, final FailableRunnable<E> runnable) {
        if (runnable != null && test != null) {
            try {
                runnable.run();
            } catch (final Throwable t) {
                throw rethrow(t);
            }
        }
    }

    /**
     * Converts the given collection into a {@link FailableStream}. The {@link FailableStream} consists of the
     * collections elements. Shortcut for
     *
     * <pre>
     * Functions.stream(collection.stream());
     * </pre>
     *
     * @param collection The collection, which is being converted into a {@link FailableStream}.
     * @param <E> The collections element type. (In turn, the result streams element type.)
     * @return The created {@link FailableStream}.
     */
    public static <E> FailableStream<E> stream(final Collection<E> collection) {
        return new FailableStream<>(collection.stream());
    }

    /**
     * Converts the given stream into a {@link FailableStream}. The {@link FailableStream} consists of the same
     * elements, than the input stream. However, failable lambdas, like {@link FailablePredicate},
     * {@link FailableFunction}, and {@link FailableConsumer} may be applied, rather than {@link Predicate},
     * {@link Function}, {@link Consumer}, etc.
     *
     * @param stream The stream, which is being converted into a {@link FailableStream}.
     * @param <T> The streams element type.
     * @return The created {@link FailableStream}.
     */
    public static <T> FailableStream<T> stream(final Stream<T> stream) {
        return new FailableStream<>(stream);
    }

    /**
     * Tests a predicate and rethrows any exception as a {@link RuntimeException}.
     *
     * @param predicate the predicate to test
     * @param object1 the first input to test by {@code predicate}
     * @param object2 the second input to test by {@code predicate}
     * @param <T> the type of the first argument the predicate tests
     * @param <U> the type of the second argument the predicate tests
     * @param <E> the type of checked exception the predicate may throw
     * @return the boolean value returned by the predicate
     */
    public static <T, U, E extends Throwable> boolean test(final FailableBiPredicate<T, U, E> predicate,
        final T object1, final U object2) {
        return getAsBoolean(() -> predicate.test(object1, object2));
    }

    /**
     * Tests a predicate and rethrows any exception as a {@link RuntimeException}.
     *
     * @param predicate the predicate to test
     * @param object the input to test by {@code predicate}
     * @param <T> the type of argument the predicate tests
     * @param <E> the type of checked exception the predicate may throw
     * @return the boolean value returned by the predicate
     */
    public static <T, E extends Throwable> boolean test(final FailablePredicate<T, E> predicate, final T object) {
        return getAsBoolean(() -> predicate.test(object));
    }

    /**
     * A simple try-with-resources implementation, that can be used, if your objects do not implement the
     * {@link AutoCloseable} interface. The method executes the {@code action}. The method guarantees, that <em>all</em>
     * the {@code resources} are being executed, in the given order, afterwards, and regardless of success, or failure.
     * If either the original action, or any of the resource action fails, then the <em>first</em> failure (AKA
     * {@link Throwable}) is rethrown. Example use:
     *
     * <pre>{@code
     * final FileInputStream fis = new FileInputStream("my.file");
     * Functions.tryWithResources(useInputStream(fis), null, () -> fis.close());
     * }</pre>
     *
     * @param action The action to execute. This object <em>will</em> always be invoked.
     * @param errorHandler An optional error handler, which will be invoked finally, if any error occurred. The error
     *        handler will receive the first error, AKA {@link Throwable}.
     * @param resources The resource actions to execute. <em>All</em> resource actions will be invoked, in the given
     *        order. A resource action is an instance of {@link FailableRunnable}, which will be executed.
     * @see #tryWithResources(FailableRunnable, FailableRunnable...)
     */
    @SafeVarargs
    public static void tryWithResources(final FailableRunnable<? extends Throwable> action,
        final FailableConsumer<Throwable, ? extends Throwable> errorHandler,
        final FailableRunnable<? extends Throwable>... resources) {

    }

    /**
     * A simple try-with-resources implementation, that can be used, if your objects do not implement the
     * {@link AutoCloseable} interface. The method executes the {@code action}. The method guarantees, that <em>all</em>
     * the {@code resources} are being executed, in the given order, afterwards, and regardless of success, or failure.
     * If either the original action, or any of the resource action fails, then the <em>first</em> failure (AKA
     * {@link Throwable}) is rethrown. Example use:
     *
     * <pre>{@code
     * final FileInputStream fis = new FileInputStream("my.file");
     * Functions.tryWithResources(useInputStream(fis), () -> fis.close());
     * }</pre>
     *
     * @param action The action to execute. This object <em>will</em> always be invoked.
     * @param resources The resource actions to execute. <em>All</em> resource actions will be invoked, in the given
     *        order. A resource action is an instance of {@link FailableRunnable}, which will be executed.
     * @see #tryWithResources(FailableRunnable, FailableConsumer, FailableRunnable...)
     */
    @SafeVarargs
    public static void tryWithResources(final FailableRunnable<? extends Throwable> action, final FailableRunnable<? extends Throwable>... resources) {
        tryWithResources(action, null, resources);
    }

    private Failable() {
        // empty
    }

}
