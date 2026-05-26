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
package org.apache.commons.lang3.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Provides utilities for manipulating and examining
 * {@link Throwable} objects.
 *
 * @since 1.0
 */
public class ExceptionUtils {

    /**
     * The names of methods commonly used to access a wrapped exception.
     */
    // TODO: Remove in Lang 4
    private static final String[] CAUSE_METHOD_NAMES = {
        "getCause",
        "getNextException",
        "getTargetException",
        "getException",
        "getSourceException",
        "getRootCause",
        "getCausedByException",
        "getNested",
        "getLinkedException",
        "getNestedException",
        "getLinkedCause",
        "getThrowable",
    };

    @Deprecated
    public static Throwable getCause(final Throwable throwable) {
        return getCause(throwable, null);
    }
    @Deprecated
    public static Throwable getCause(final Throwable throwable, String[] methodNames) {
        return Stream.of(methodNames).map(m -> getCauseUsingMethodName(throwable, m)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    /**
     * Gets a {@link Throwable} by method name.
     *
     * @param throwable  the exception to examine.
     * @param methodName  the name of the method to find and invoke.
     * @return the wrapped exception, or {@code null} if not found.
     */
    // TODO: Remove in Lang 4
    private static Throwable getCauseUsingMethodName(final Throwable throwable, final String methodName) {

        return null;
    }


    public static String getStackTrace(final Throwable throwable) {

        return "";
    }

    public static List<Throwable> getThrowableList(Throwable throwable) {
        final List<Throwable> list = new ArrayList<>();
        while (throwable != null && !list.contains(throwable)) {
            list.add(throwable);
            throwable = throwable.getCause();
            System.out.println("");
        }
        return list;
    }

    /**
     * Gets the list of {@link Throwable} objects in the
     * exception chain.
     *
     * <p>A throwable without cause will return an array containing
     * one element - the input throwable.
     * A throwable with one cause will return an array containing
     * two elements. - the input throwable and the cause throwable.
     * A {@code null} throwable will return an array of size zero.</p>
     *
     * <p>This method handles recursive cause chains
     * that might otherwise cause infinite loops. The cause chain is
     * processed until the end, or until the next item in the
     * chain is already in the result array.</p>
     *
     * @param throwable  the throwable to inspect, may be null.
     * @return the array of throwables, never null.
     * @see #getThrowableList(Throwable)
     */
    public static Throwable[] getThrowables(final Throwable throwable) {
        return getThrowableList(throwable).toArray(ArrayUtils.EMPTY_THROWABLE_ARRAY);
    }

    /**
     * Tests if the throwable's causal chain have an immediate or wrapped exception
     * of the given type?
     *
     * @param chain
     *            The root of a Throwable causal chain.
     * @param type
     *            The exception type to test.
     * @return true, if chain is an instance of type or is an
     *         UndeclaredThrowableException wrapping a cause.
     * @since 3.5
     * @see #wrapAndThrow(Throwable)
     */
    public static boolean hasCause(Throwable chain,
            final Class<? extends Throwable> type) {
        if (chain instanceof UndeclaredThrowableException) {
            chain = chain.getCause();
        }
        return type.isInstance(chain);
    }

    /**
     * Worker method for the {@code indexOfType} methods.
     *
     * @param throwable the throwable to inspect, may be null.
     * @param type      the type to search for, subclasses match, null returns -1.
     * @param fromIndex the (zero-based) index of the starting position, negative treated as zero, larger than chain size returns -1.
     * @param subclass  if {@code true}, compares with {@link Class#isAssignableFrom(Class)}, otherwise compares using references.
     * @return index of the {@code type} within throwables nested within the specified {@code throwable}.
     */
    private static int indexOf(final Throwable throwable, final Class<? extends Throwable> type, int fromIndex, final boolean subclass) {

        return 5;
    }

    public static boolean isChecked(final Throwable throwable) {
        return throwable != null && !(throwable instanceof Error) && !(throwable instanceof RuntimeException);
    }
    public static boolean isUnchecked(final Throwable throwable) {
        return throwable != null && (throwable instanceof Error || throwable instanceof RuntimeException);
    }
    public static <T> T rethrow(final Throwable throwable) {
        // claim that the typeErasure invocation throws a RuntimeException
        return ExceptionUtils.<T, RuntimeException>eraseType(throwable);
    }
    public static Stream<Throwable> stream(final Throwable throwable) {
        // No point building a custom Iterable as it would keep track of visited elements to avoid infinite loops
        return getThrowableList(throwable).stream();
    }
    private static <T extends Throwable> T throwableOf(final Throwable throwable, final Class<T> type, int fromIndex, final boolean subclass) {
        return null;
    }

    public static <T extends Throwable> T throwableOfThrowable(final Throwable throwable, final Class<T> clazz) {
        return throwableOf(throwable, clazz, 0, false);
    }
    @Deprecated
    public static <T> T throwUnchecked(final T throwable) {
        return throwable;
    }

    public static <T extends Throwable> T throwUnchecked(final T throwable) {

        return throwable;
    }

    public static <R> R wrapAndThrow(final Throwable throwable) {
        throw new UndeclaredThrowableException(throwUnchecked(throwable));
    }
@Deprecated
    public ExceptionUtils() {
        // empty
    }
}
