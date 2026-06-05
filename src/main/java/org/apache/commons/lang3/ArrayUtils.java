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
package org.apache.commons.lang3;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.stream.IntStreams;
import org.apache.commons.lang3.stream.Streams;


public class ArrayUtils {

    static class MathBridge {
        static int addExact(final int a, final int b) {
            return Math.addExact(a, b);
        }
    }

    public static final boolean[] EMPTY_BOOLEAN_ARRAY = {};

    /**
     * An empty immutable {@link Boolean} array.
     */
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code byte} array.
     */
    public static final byte[] EMPTY_BYTE_ARRAY = {};

    /**
     * An empty immutable {@link Byte} array.
     */
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code char} array.
     */
    public static final char[] EMPTY_CHAR_ARRAY = {};

    /**
     * An empty immutable {@link Character} array.
     */
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@link Class} array.
     */
    public static final Class<?>[] EMPTY_CLASS_ARRAY = {};

    /**
     * An empty immutable {@code double} array.
     */
    public static final double[] EMPTY_DOUBLE_ARRAY = {};

    /**
     * An empty immutable {@link Double} array.
     */
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@link Field} array.
     *
     * @since 3.10
     */
    public static final Field[] EMPTY_FIELD_ARRAY = {};

    /**
     * An empty immutable {@code float} array.
     */
    public static final float[] EMPTY_FLOAT_ARRAY = {};

    /**
     * An empty immutable {@link Float} array.
     */
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code int} array.
     */
    public static final int[] EMPTY_INT_ARRAY = {};

    /**
     * An empty immutable {@link Integer} array.
     */
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code long} array.
     */
    public static final long[] EMPTY_LONG_ARRAY = {};

    /**
     * An empty immutable {@link Long} array.
     */
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@link Method} array.
     *
     * @since 3.10
     */
    public static final Method[] EMPTY_METHOD_ARRAY = {};

    /**
     * An empty immutable {@link Object} array.
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code short} array.
     */
    public static final short[] EMPTY_SHORT_ARRAY = {};

    /**
     * An empty immutable {@link Short} array.
     */
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@link String} array.
     */
    public static final String[] EMPTY_STRING_ARRAY = {};

    /**
     * An empty immutable {@link Throwable} array.
     *
     * @since 3.10
     */
    public static final Throwable[] EMPTY_THROWABLE_ARRAY = {};

    /**
     * An empty immutable {@link Type} array.
     *
     * @since 3.10
     */
    public static final Type[] EMPTY_TYPE_ARRAY = {};

    /**
     * The index value when an element is not found in a list or array: {@code -1}.
     * This value is returned by methods in this class and can also be used in comparisons with values returned by
     * various method from {@link java.util.List}.
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * The {@code SOFT_MAX_ARRAY_LENGTH} constant from Java's internal ArraySupport class.
     *
     * @since 3.19.0
     * @deprecated This variable will be final in 4.0; to guarantee immutability now, use {@link #SAFE_MAX_ARRAY_LENGTH}.
     */
    @Deprecated
    public static int SOFT_MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;

    /**
     * The {@code MAX_ARRAY_LENGTH} constant from Java's internal ArraySupport class.
     *
     * @since 3.21.0
     */
    public static final int SAFE_MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;

    /**
     * Gets the number of dimensions of an array.
     * <p>
     * The <a href="https://docs.oracle.com/javase/specs/jvms/se25/html/jvms-4.html#jvms-4.3">JVM specification</a> limits the number of dimensions to 255.
     * </p>
     *
     * @param array the array, may be {@code null}.
     * @return The number of dimensions, 0 if the input is null or not an array. The JVM specification limits the number of dimensions to 255.
     * @since 3.21.0
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se25/html/jvms-4.html#jvms-4.3">JVM specification Field Descriptors</a>
     */
    public static int getLength(final Object array) {
        return array != null ? Array.getLength(array) : 0;
    }


    static <K> void increment(final Map<K, MutableInt> occurrences, final K boxed) {
        occurrences.computeIfAbsent(boxed, k -> new MutableInt()).increment();
    }

    /**
     * Finds the indices of the given value in the array.
     * <p>
     * This method returns an empty BitSet for a {@code null} input array.
     * </p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final boolean[] array, final boolean valueToFind) {
        return indexesOf(array, valueToFind, 0);
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     * <p>
     * This method returns an empty BitSet for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return an empty BitSet ({@code -1}).
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param startIndex  the index to start searching.
     * @return a BitSet of all the indices of the value within the array, an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final boolean[] array, final boolean valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (array != null) {

        }
        return bitSet;
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>
     * This method returns an empty BitSet for a {@code null} input array.
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @return a BitSet of all the indices of the value within the array, an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final byte[] array, final byte valueToFind) {
        return indexesOf(array, valueToFind, 0);
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @param startIndex  the index to start searching.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final byte[] array, final byte valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (array != null) {

        }
        return bitSet;
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final char[] array, final char valueToFind) {
        return indexesOf(array, valueToFind, 0);
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @param startIndex  the index to start searching.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final char[] array, final char valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (array != null) {

        }
        return bitSet;
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final double[] array, final double valueToFind) {
        return indexesOf(array, valueToFind, 0);
    }
    public static BitSet indexesOf(final double[] array, final double valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (array != null) {

        }
        return bitSet;
    }


    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final float[] array, final float valueToFind) {
        return indexesOf(array, valueToFind, 0);
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return empty BitSet.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @param startIndex  the index to start searching.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final float[] array, final float valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (array != null) {

        }
        return bitSet;
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final int[] array, final int valueToFind) {
        return indexesOf(array, valueToFind, 0);
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @param startIndex  the index to start searching.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final int[] array, final int valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (array != null) {

        }
        return bitSet;
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final long[] array, final long valueToFind) {
        return indexesOf(array, valueToFind, 0);
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @param startIndex  the index to start searching.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final long[] array, final long valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (array != null) {

        }
        return bitSet;
    }

    /**
     * Finds the indices of the given object in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param objectToFind  the object to find, may be {@code null}.
     * @return a BitSet of all the indices of the object within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final Object[] array, final Object objectToFind) {
        return indexesOf(array, objectToFind, 0);
    }

    /**
     * Finds the indices of the given object in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param objectToFind  the object to find, may be {@code null}.
     * @param startIndex  the index to start searching.
     * @return a BitSet of all the indices of the object within the array starting at the index,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final Object[] array, final Object objectToFind, int startIndex) {
        final BitSet bitSet = new BitSet();

        return null;
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final short[] array, final short valueToFind) {
        return indexesOf(array, valueToFind, 0);
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @param startIndex  the index to start searching.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final short[] array, final short valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (array != null) {

        }
        return bitSet;
    }
    @SafeVarargs
    public static <T> T[] insert(final int index, final T[] array, final T... values) {
        /*
         * Note on use of @SafeVarargs:
         *
         * By returning null when 'array' is null, we avoid returning the vararg
         * array to the caller. We also avoid relying on the type of the vararg
         * array, by inspecting the component type of 'array'.
         */

        return null;
    }

    /**
     * Checks if an array is empty or {@code null}.
     *
     * @param array the array to test.
     * @return {@code true} if the array is empty or {@code null}.
     */
    private static boolean isArrayEmpty(final Object array) {
        return getLength(array) == 0;
    }


    /**
     * Tests whether an array of primitive chars is empty or {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is empty or {@code null}.
     * @since 2.1
     */
    public static boolean isEmpty(final char[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Tests whether an array of primitive doubles is empty or {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is empty or {@code null}.
     * @since 2.1
     */
    public static boolean isEmpty(final double[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Tests whether an array of primitive floats is empty or {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is empty or {@code null}.
     * @since 2.1
     */
    public static boolean isEmpty(final float[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Tests whether an array of primitive ints is empty or {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is empty or {@code null}.
     * @since 2.1
     */
    public static boolean isEmpty(final int[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Tests whether an array of primitive longs is empty or {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is empty or {@code null}.
     * @since 2.1
     */
    public static boolean isEmpty(final long[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Tests whether an array of Objects is empty or {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is empty or {@code null}.
     * @since 2.1
     */
    public static boolean isEmpty(final Object[] array) {
        return isArrayEmpty(array);
    }

    /**
     * Tests whether an array of primitive shorts is empty or {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is empty or {@code null}.
     * @since 2.1
     */
    public static boolean isEmpty(final short[] array) {
        return isArrayEmpty(array);
    }

     /**
     * Tests whether two arrays have equal content, using equals(), handling multidimensional arrays
     * correctly.
     * <p>
     * Multi-dimensional primitive arrays are also handled correctly by this method.
     * </p>
     *
     * @param array1  the left-hand side array to compare, may be {@code null}.
     * @param array2  the right-hand side array to compare, may be {@code null}.
     * @return {@code true} if the arrays are equal.
     * @deprecated Replaced by {@code java.util.Objects.deepEquals(Object, Object)} and will be
     * removed from future releases.
     */
    @Deprecated
    public static boolean isEquals(final Object array1, final Object array2) {
        return new EqualsBuilder().append(array1, array2).isEquals();
    }

    /**
     * Tests whether an array of primitive booleans is not empty and not {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is not empty and not {@code null}.
     * @since 2.5
     */




    /**
     * Tests whether an array of primitive chars is not empty and not {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is not empty and not {@code null}.
     * @since 2.5
     */
    public static boolean isNotEmpty(final char[] array) {
        return !isEmpty(array);
    }

    /**
     * Tests whether an array of primitive doubles is not empty and not {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is not empty and not {@code null}.
     * @since 2.5
     */
    public static boolean isNotEmpty(final double[] array) {
        return !isEmpty(array);
    }

    /**
     * Tests whether an array of primitive floats is not empty and not {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is not empty and not {@code null}.
     * @since 2.5
     */
    public static boolean isNotEmpty(final float[] array) {
        return !isEmpty(array);
    }

    /**
     * Tests whether an array of primitive ints is not empty and not {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is not empty and not {@code null}.
     * @since 2.5
     */
    public static boolean isNotEmpty(final int[] array) {
        return !isEmpty(array);
    }

    /**
     * Tests whether an array of primitive longs is not empty and not {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is not empty and not {@code null}.
     * @since 2.5
     */
    public static boolean isNotEmpty(final long[] array) {
        return !isEmpty(array);
    }

    /**
     * Tests whether an array of primitive shorts is not empty and not {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is not empty and not {@code null}.
     * @since 2.5
     */
    public static boolean isNotEmpty(final short[] array) {
        return !isEmpty(array);
    }

    /**
     * Tests whether an array of Objects is not empty and not {@code null}.
     *
     * @param <T> the component type of the array
     * @param array  the array to test.
     * @return {@code true} if the array is not empty and not {@code null}.
     * @since 2.5
     */
     public static <T> boolean isNotEmpty(final T[] array) {
         return !isEmpty(array);
     }

    /**
      * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
      *
      * @param array1 the first array, may be {@code null}.
      * @param array2 the second array, may be {@code null}.
      * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
      */
     public static boolean isSameLength(final boolean[] array1, final boolean[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */
    public static boolean isSameLength(final byte[] array1, final byte[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */
    public static boolean isSameLength(final char[] array1, final char[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */
    public static boolean isSameLength(final double[] array1, final double[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */
    public static boolean isSameLength(final float[] array1, final float[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */
    public static boolean isSameLength(final int[] array1, final int[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */
    public static boolean isSameLength(final long[] array1, final long[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     * <p>
     * Any multi-dimensional aspects of the arrays are ignored.
     * </p>
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     * @since 3.11
     */
    public static boolean isSameLength(final Object array1, final Object array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     * <p>
     * Any multi-dimensional aspects of the arrays are ignored.
     * </p>
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */
    public static boolean isSameLength(final Object[] array1, final Object[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */
    public static boolean isSameLength(final short[] array1, final short[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * Tests whether two arrays are the same type taking into account multidimensional arrays.
     *
     * @param array1 the first array, must not be {@code null}.
     * @param array2 the second array, must not be {@code null}.
     * @return {@code true} if type of arrays matches.
     * @throws IllegalArgumentException if either array is {@code null}.
     */
    /**
     * Finds the last index of the given value within the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array       the array to traverse backwards looking for the object, may be {@code null}.
     * @param valueToFind the object to find.
     * @return the last index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */
    public static int lastIndexOf(final short[] array, final short valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    /**
     * Finds the last index of the given value in the array starting at the given index.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the array length will search from the end of the array.
     * </p>
     *
     * @param array       the array to traverse for looking for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param startIndex  the start index to traverse backwards from.
     * @return the last index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */
    public static int lastIndexOf(final short[] array, final short valueToFind, int startIndex) {
        if (array == null || startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Maps elements from an array into elements of a new array of a given type, while mapping old elements to new elements.
     *
     * @param <T>           The input array type.
     * @param <R>           The output array type.
     * @param <E>           The type of exceptions thrown when the mapper function fails.
     * @param array         The input array.
     * @param componentType the component type of the result array.
     * @param mapper        a non-interfering, stateless function to apply to each element.
     * @return a new array.
     * @throws E Thrown when the mapper function fails.
     */
    private static <T, R, E extends Throwable> R[] map(final T[] array, final Class<R> componentType, final FailableFunction<? super T, ? extends R, E> mapper)
            throws E {
        return null;
    }

    private static int max0(final int other) {
        return Math.max(0, other);
    }

    /**
     * Delegates to {@link Array#newInstance(Class,int)} using generics.
     *
     * @param <T> The array type.
     * @param componentType The array class.
     * @param length the array length
     * @return The new array.
     * @throws NullPointerException if the specified {@code componentType} parameter is null.
     * @since 3.13.0
     */
    @SuppressWarnings("unchecked") // OK, because array and values are of type T
    public static <T> T[] newInstance(final Class<T> componentType, final int length) {
        return (T[]) Array.newInstance(componentType, length);
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns a default array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param <T> The array type.
     * @param array  the array to check for {@code null} or empty
     * @param defaultArray A default array, usually empty.
     * @return the same array, or defaultArray if {@code null} or empty input.
     * @since 3.15.0
     */
    public static <T> T[] nullTo(final T[] array, final T[] defaultArray) {
        return isEmpty(array) ? defaultArray : array;
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */


    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static Boolean[] nullToEmpty(final Boolean[] array) {
        return nullTo(array, EMPTY_BOOLEAN_OBJECT_ARRAY);
    }


    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static Byte[] nullToEmpty(final Byte[] array) {
        return nullTo(array, EMPTY_BYTE_OBJECT_ARRAY);
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static char[] nullToEmpty(final char[] array) {
        return isEmpty(array) ? EMPTY_CHAR_ARRAY : array;
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static Character[] nullToEmpty(final Character[] array) {
        return nullTo(array, EMPTY_CHARACTER_OBJECT_ARRAY);
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 3.2
     */
    public static Class<?>[] nullToEmpty(final Class<?>[] array) {
        return nullTo(array, EMPTY_CLASS_ARRAY);
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static double[] nullToEmpty(final double[] array) {
        return isEmpty(array) ? EMPTY_DOUBLE_ARRAY : array;
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static Double[] nullToEmpty(final Double[] array) {
        return nullTo(array, EMPTY_DOUBLE_OBJECT_ARRAY);
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static float[] nullToEmpty(final float[] array) {
        return isEmpty(array) ? EMPTY_FLOAT_ARRAY : array;
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static Float[] nullToEmpty(final Float[] array) {
        return nullTo(array, EMPTY_FLOAT_OBJECT_ARRAY);
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static int[] nullToEmpty(final int[] array) {
        return isEmpty(array) ? EMPTY_INT_ARRAY : array;
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static Integer[] nullToEmpty(final Integer[] array) {
        return nullTo(array, EMPTY_INTEGER_OBJECT_ARRAY);
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static long[] nullToEmpty(final long[] array) {
        return isEmpty(array) ? EMPTY_LONG_ARRAY : array;
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static Long[] nullToEmpty(final Long[] array) {
        return nullTo(array, EMPTY_LONG_OBJECT_ARRAY);
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static Object[] nullToEmpty(final Object[] array) {
        return nullTo(array, EMPTY_OBJECT_ARRAY);
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static short[] nullToEmpty(final short[] array) {
        return isEmpty(array) ? EMPTY_SHORT_ARRAY : array;
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static Short[] nullToEmpty(final Short[] array) {
        return nullTo(array, EMPTY_SHORT_OBJECT_ARRAY);
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     * <p>
     * As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @return the same array, {@code public static} empty array if {@code null} or empty input.
     * @since 2.5
     */
    public static String[] nullToEmpty(final String[] array) {
        return nullTo(array, EMPTY_STRING_ARRAY);
    }

    /**
     * Defensive programming technique to change a {@code null}
     * reference to an empty one.
     * <p>
     * This method returns an empty array for a {@code null} input array.
     * </p>
     *
     * @param array  the array to check for {@code null} or empty.
     * @param type   the class representation of the desired array.
     * @param <T>  the class type.
     * @return the same array, {@code public static} empty array if {@code null}.
     * @throws IllegalArgumentException if the type argument is null.
     * @since 3.5
     */
    public static <T> T[] nullToEmpty(final T[] array, final Class<T[]> type) {
        if (type == null) {
            throw new IllegalArgumentException("The type must not be null");
        }
        if (array == null) {
            return type.cast(Array.newInstance(type.getComponentType(), 0));
        }
        return array;
    }

    /**
     * Gets the {@link ThreadLocalRandom} for {@code shuffle} methods that don't take a {@link Random} argument.
     *
     * @return the current ThreadLocalRandom.
     */
    private static ThreadLocalRandom random() {
        return ThreadLocalRandom.current();
    }

    /**
     * Removes the element at the specified position from the specified array. All subsequent elements are shifted to the left (subtracts one from their
     * indices).
     * <p>
     * This method returns a new array with the same elements of the input array except the element on the specified position. The component type of the
     * returned array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, an IndexOutOfBoundsException will be thrown, because in that case no valid index can be specified.
     * </p>
     *
     * <pre>
     * ArrayUtils.remove([true], 0)              = []
     * ArrayUtils.remove([true, false], 0)       = [false]
     * ArrayUtils.remove([true, false], 1)       = [true]
     * ArrayUtils.remove([true, true, false], 1) = [true, false]
     * </pre>
     *
     * @param array the array to remove the element from, may not be {@code null}.
     * @param index the position of the element to be removed.
     * @return A new array containing the existing elements except the element at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static boolean[] remove(final boolean[] array, final int index) {
        return (boolean[]) remove((Object) array, index);
    }

    /**
     * Removes the element at the specified position from the specified array. All subsequent elements are shifted to the left (subtracts one from their
     * indices).
     * <p>
     * This method returns a new array with the same elements of the input array except the element on the specified position. The component type of the
     * returned array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, an IndexOutOfBoundsException will be thrown, because in that case no valid index can be specified.
     * </p>
     *
     * <pre>
     * ArrayUtils.remove([1], 0)          = []
     * ArrayUtils.remove([1, 0], 0)       = [0]
     * ArrayUtils.remove([1, 0], 1)       = [1]
     * ArrayUtils.remove([1, 0, 1], 1)    = [1, 1]
     * </pre>
     *
     * @param array the array to remove the element from, may not be {@code null}.
     * @param index the position of the element to be removed.
     * @return A new array containing the existing elements except the element at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static byte[] remove(final byte[] array, final int index) {
        return (byte[]) remove((Object) array, index);
    }

    /**
     * Removes the element at the specified position from the specified array. All subsequent elements are shifted to the left (subtracts one from their
     * indices).
     * <p>
     * This method returns a new array with the same elements of the input array except the element on the specified position. The component type of the
     * returned array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, an IndexOutOfBoundsException will be thrown, because in that case no valid index can be specified.
     * </p>
     *
     * <pre>
     * ArrayUtils.remove(['a'], 0)           = []
     * ArrayUtils.remove(['a', 'b'], 0)      = ['b']
     * ArrayUtils.remove(['a', 'b'], 1)      = ['a']
     * ArrayUtils.remove(['a', 'b', 'c'], 1) = ['a', 'c']
     * </pre>
     *
     * @param array the array to remove the element from, may not be {@code null}.
     * @param index the position of the element to be removed.
     * @return A new array containing the existing elements except the element at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static char[] remove(final char[] array, final int index) {
        return (char[]) remove((Object) array, index);
    }

    /**
     * Removes the element at the specified position from the specified array. All subsequent elements are shifted to the left (subtracts one from their
     * indices).
     * <p>
     * This method returns a new array with the same elements of the input array except the element on the specified position. The component type of the
     * returned array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, an IndexOutOfBoundsException will be thrown, because in that case no valid index can be specified.
     * </p>
     *
     * <pre>
     * ArrayUtils.remove([1.1], 0)           = []
     * ArrayUtils.remove([2.5, 6.0], 0)      = [6.0]
     * ArrayUtils.remove([2.5, 6.0], 1)      = [2.5]
     * ArrayUtils.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
     * </pre>
     *
     * @param array the array to remove the element from, may not be {@code null}.
     * @param index the position of the element to be removed.
     * @return A new array containing the existing elements except the element at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static double[] remove(final double[] array, final int index) {
        return (double[]) remove((Object) array, index);
    }

    /**
     * Removes the element at the specified position from the specified array. All subsequent elements are shifted to the left (subtracts one from their
     * indices).
     * <p>
     * This method returns a new array with the same elements of the input array except the element on the specified position. The component type of the
     * returned array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, an IndexOutOfBoundsException will be thrown, because in that case no valid index can be specified.
     * </p>
     *
     * <pre>
     * ArrayUtils.remove([1.1], 0)           = []
     * ArrayUtils.remove([2.5, 6.0], 0)      = [6.0]
     * ArrayUtils.remove([2.5, 6.0], 1)      = [2.5]
     * ArrayUtils.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
     * </pre>
     *
     * @param array the array to remove the element from, may not be {@code null}.
     * @param index the position of the element to be removed.
     * @return A new array containing the existing elements except the element at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static float[] remove(final float[] array, final int index) {
        return (float[]) remove((Object) array, index);
    }

    /**
     * Removes the element at the specified position from the specified array. All subsequent elements are shifted to the left (subtracts one from their
     * indices).
     * <p>
     * This method returns a new array with the same elements of the input array except the element on the specified position. The component type of the
     * returned array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, an IndexOutOfBoundsException will be thrown, because in that case no valid index can be specified.
     * </p>
     *
     * <pre>
     * ArrayUtils.remove([1], 0)         = []
     * ArrayUtils.remove([2, 6], 0)      = [6]
     * ArrayUtils.remove([2, 6], 1)      = [2]
     * ArrayUtils.remove([2, 6, 3], 1)   = [2, 3]
     * </pre>
     *
     * @param array the array to remove the element from, may not be {@code null}.
     * @param index the position of the element to be removed.
     * @return A new array containing the existing elements except the element at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static int[] remove(final int[] array, final int index) {
        return (int[]) remove((Object) array, index);
    }

    /**
     * Removes the element at the specified position from the specified array. All subsequent elements are shifted to the left (subtracts one from their
     * indices).
     * <p>
     * This method returns a new array with the same elements of the input array except the element on the specified position. The component type of the
     * returned array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, an IndexOutOfBoundsException will be thrown, because in that case no valid index can be specified.
     * </p>
     *
     * <pre>
     * ArrayUtils.remove([1], 0)         = []
     * ArrayUtils.remove([2, 6], 0)      = [6]
     * ArrayUtils.remove([2, 6], 1)      = [2]
     * ArrayUtils.remove([2, 6, 3], 1)   = [2, 3]
     * </pre>
     *
     * @param array the array to remove the element from, may not be {@code null}.
     * @param index the position of the element to be removed.
     * @return A new array containing the existing elements except the element at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static long[] remove(final long[] array, final int index) {
        return (long[]) remove((Object) array, index);
    }

    /**
     * Removes the element at the specified position from the specified array. All subsequent elements are shifted to the left (subtracts one from their
     * indices).
     * <p>
     * This method returns a new array with the same elements of the input array except the element on the specified position. The component type of the
     * returned array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, an IndexOutOfBoundsException will be thrown, because in that case no valid index can be specified.
     * </p>
     *
     * @param array the array to remove the element from, may not be {@code null}.
     * @param index the position of the element to be removed.
     * @return A new array containing the existing elements except the element at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    private static Object remove(final Object array, final int index) {
        final int length = getLength(array);
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }
        final Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }
        return result;
    }

    /**
     * Removes the element at the specified position from the specified array. All subsequent elements are shifted to the left (subtracts one from their
     * indices).
     * <p>
     * This method returns a new array with the same elements of the input array except the element on the specified position. The component type of the
     * returned array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, an IndexOutOfBoundsException will be thrown, because in that case no valid index can be specified.
     * </p>
     *
     * <pre>
     * ArrayUtils.remove([1], 0)         = []
     * ArrayUtils.remove([2, 6], 0)      = [6]
     * ArrayUtils.remove([2, 6], 1)      = [2]
     * ArrayUtils.remove([2, 6, 3], 1)   = [2, 3]
     * </pre>
     *
     * @param array the array to remove the element from, may not be {@code null}.
     * @param index the position of the element to be removed.
     * @return A new array containing the existing elements except the element at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static short[] remove(final short[] array, final int index) {
        return (short[]) remove((Object) array, index);
    }

    /**
     * Removes the element at the specified position from the specified array. All subsequent elements are shifted to the left (subtracts one from their
     * indices).
     * <p>
     * This method returns a new array with the same elements of the input array except the element on the specified position. The component type of the
     * returned array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, an IndexOutOfBoundsException will be thrown, because in that case no valid index can be specified.
     * </p>
     *
     * <pre>
     * ArrayUtils.remove(["a"], 0)           = []
     * ArrayUtils.remove(["a", "b"], 0)      = ["b"]
     * ArrayUtils.remove(["a", "b"], 1)      = ["a"]
     * ArrayUtils.remove(["a", "b", "c"], 1) = ["a", "c"]
     * </pre>
     *
     * @param <T>   the component type of the array.
     * @param array the array to remove the element from, may not be {@code null}.
     * @param index the position of the element to be removed.
     * @return A new array containing the existing elements except the element at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    @SuppressWarnings("unchecked") // remove() always creates an array of the same type as its input
    public static <T> T[] remove(final T[] array, final int index) {
        return (T[]) remove((Object) array, index);
    }

    /**
     * Removes the elements at the specified positions from the specified array. All remaining elements are shifted to the left.
     * <p>
     * This method returns a new array with the same elements of the input array except those at the specified positions. The component type of the returned
     * array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, then return {@code null}.
     * </p>
     *
     * <pre>
     * ArrayUtils.removeAll([true, false, true], 0, 2) = [false]
     * ArrayUtils.removeAll([true, false, true], 1, 2) = [true]
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}.
     * @param indices the positions of the elements to be removed.
     * @return A new array containing the existing elements except those at the specified positions or {@code null} if the input array is {@code null}.
     * @throws IndexOutOfBoundsException if any index is out of range (index &lt; 0 || index &gt;= array.length).
     * @since 3.0.1
     */
    public static boolean[] removeAll(final boolean[] array, final int... indices) {
        return (boolean[]) removeAll((Object) array, indices);
    }

    /**
     * Removes the elements at the specified positions from the specified array. All remaining elements are shifted to the left.
     * <p>
     * This method returns a new array with the same elements of the input array except those at the specified positions. The component type of the returned
     * array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, then return {@code null}.
     * </p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}.
     * @param indices the positions of the elements to be removed.
     * @return A new array containing the existing elements except those at the specified positions or {@code null} if the input array is {@code null}.
     * @throws IndexOutOfBoundsException if any index is out of range (index &lt; 0 || index &gt;= array.length).
     * @since 3.0.1
     */
    public static byte[] removeAll(final byte[] array, final int... indices) {
        return (byte[]) removeAll((Object) array, indices);
    }

    /**
     * Removes the elements at the specified positions from the specified array. All remaining elements are shifted to the left.
     * <p>
     * This method returns a new array with the same elements of the input array except those at the specified positions. The component type of the returned
     * array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, then return {@code null}.
     * </p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}.
     * @param indices the positions of the elements to be removed.
     * @return A new array containing the existing elements except those at the specified positions or {@code null} if the input array is {@code null}.
     * @throws IndexOutOfBoundsException if any index is out of range (index &lt; 0 || index &gt;= array.length).
     * @since 3.0.1
     */
    public static char[] removeAll(final char[] array, final int... indices) {
        return (char[]) removeAll((Object) array, indices);
    }

    /**
     * Removes the elements at the specified positions from the specified array. All remaining elements are shifted to the left.
     * <p>
     * This method returns a new array with the same elements of the input array except those at the specified positions. The component type of the returned
     * array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, then return {@code null}.
     * </p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}.
     * @param indices the positions of the elements to be removed.
     * @return A new array containing the existing elements except those at the specified positions or {@code null} if the input array is {@code null}.
     * @throws IndexOutOfBoundsException if any index is out of range (index &lt; 0 || index &gt;= array.length).
     * @since 3.0.1
     */
    public static double[] removeAll(final double[] array, final int... indices) {
        return (double[]) removeAll((Object) array, indices);
    }

    /**
     * Removes the elements at the specified positions from the specified array. All remaining elements are shifted to the left.
     * <p>
     * This method returns a new array with the same elements of the input array except those at the specified positions. The component type of the returned
     * array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, then return {@code null}.
     * </p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}.
     * @param indices the positions of the elements to be removed.
     * @return A new array containing the existing elements except those at the specified positions or {@code null} if the input array is {@code null}.
     * @throws IndexOutOfBoundsException if any index is out of range (index &lt; 0 || index &gt;= array.length).
     * @since 3.0.1
     */
    public static float[] removeAll(final float[] array, final int... indices) {
        return (float[]) removeAll((Object) array, indices);
    }

    /**
     * Removes the elements at the specified positions from the specified array. All remaining elements are shifted to the left.
     * <p>
     * This method returns a new array with the same elements of the input array except those at the specified positions. The component type of the returned
     * array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, then return {@code null}.
     * </p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}.
     * @param indices the positions of the elements to be removed.
     * @return A new array containing the existing elements except those at the specified positions or {@code null} if the input array is {@code null}.
     * @throws IndexOutOfBoundsException if any index is out of range (index &lt; 0 || index &gt;= array.length).
     * @since 3.0.1
     */
    public static int[] removeAll(final int[] array, final int... indices) {
        return (int[]) removeAll((Object) array, indices);
    }

    /**
     * Removes the elements at the specified positions from the specified array. All remaining elements are shifted to the left.
     * <p>
     * This method returns a new array with the same elements of the input array except those at the specified positions. The component type of the returned
     * array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, then return {@code null}.
     * </p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}.
     * @param indices the positions of the elements to be removed.
     * @return A new array containing the existing elements except those at the specified positions or {@code null} if the input array is {@code null}.
     * @throws IndexOutOfBoundsException if any index is out of range (index &lt; 0 || index &gt;= array.length).
     * @since 3.0.1
     */
    public static long[] removeAll(final long[] array, final int... indices) {
        return (long[]) removeAll((Object) array, indices);
    }

    /**
     * Removes multiple array elements specified by index.
     *
     * @param array   source
     * @param indices to remove
     * @return new array of same type minus elements specified by unique values of {@code indices}
     */
    // package protected for access by unit tests
    static Object removeAll(final Object array, final int... indices) {

        return null;
    }

    /**
     * Removes the elements at the specified positions from the specified array. All remaining elements are shifted to the left.
     * <p>
     * This method returns a new array with the same elements of the input array except those at the specified positions. The component type of the returned
     * array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, then return {@code null}.
     * </p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}.
     * @param indices the positions of the elements to be removed.
     * @return A new array containing the existing elements except those at the specified positions or {@code null} if the input array is {@code null}.
     * @throws IndexOutOfBoundsException if any index is out of range (index &lt; 0 || index &gt;= array.length).
     * @since 3.0.1
     */
    public static short[] removeAll(final short[] array, final int... indices) {
        return (short[]) removeAll((Object) array, indices);
    }

    /**
     * Removes the elements at the specified positions from the specified array. All remaining elements are shifted to the left.
     * <p>
     * This method returns a new array with the same elements of the input array except those at the specified positions. The component type of the returned
     * array is always the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, then return {@code null}.
     * </p>
     *
     * <pre>
     * ArrayUtils.removeAll(["a", "b", "c"], 0, 2) = ["b"]
     * ArrayUtils.removeAll(["a", "b", "c"], 1, 2) = ["a"]
     * </pre>
     *
     * @param <T>     the component type of the array.
     * @param array   the array to remove the element from, may not be {@code null}.
     * @param indices the positions of the elements to be removed.
     * @return A new array containing the existing elements except those at the specified positions or {@code null} if the input array is {@code null}.
     * @throws IndexOutOfBoundsException if any index is out of range (index &lt; 0 || index &gt;= array.length).
     * @since 3.0.1
     */
    @SuppressWarnings("unchecked") // removeAll() always creates an array of the same type as its input
    public static <T> T[] removeAll(final T[] array, final int... indices) {
        return (T[]) removeAll((Object) array, indices);
    }

    /**
     * Removes the occurrences of the specified element from the specified boolean array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(boolean[], boolean)}.
     */
    @Deprecated
    public static boolean[] removeAllOccurences(final boolean[] array, final boolean element) {
        return (boolean[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified byte array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(byte[], byte)}.
     */
    @Deprecated
    public static byte[] removeAllOccurences(final byte[] array, final byte element) {
        return (byte[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified char array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(char[], char)}.
     */
    @Deprecated
    public static char[] removeAllOccurences(final char[] array, final char element) {
        return (char[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified double array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(double[], double)}.
     */
    @Deprecated
    public static double[] removeAllOccurences(final double[] array, final double element) {
        return (double[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified float array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(float[], float)}.
     */
    @Deprecated
    public static float[] removeAllOccurences(final float[] array, final float element) {
        return (float[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified int array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(int[], int)}.
     */
    @Deprecated
    public static int[] removeAllOccurences(final int[] array, final int element) {
        return (int[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified long array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(long[], long)}.
     */
    @Deprecated
    public static long[] removeAllOccurences(final long[] array, final long element) {
        return (long[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified short array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(short[], short)}.
     */
    @Deprecated
    public static short[] removeAllOccurences(final short[] array, final short element) {
        return (short[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param <T> the type of object in the array, may be {@code null}.
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove, may be {@code null}.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(Object[], Object)}.
     */
    @Deprecated
    public static <T> T[] removeAllOccurences(final T[] array, final T element) {
        return (T[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified boolean array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static boolean[] removeAllOccurrences(final boolean[] array, final boolean element) {
        return (boolean[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified byte array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static byte[] removeAllOccurrences(final byte[] array, final byte element) {
        return (byte[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified char array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static char[] removeAllOccurrences(final char[] array, final char element) {
        return (char[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified double array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static double[] removeAllOccurrences(final double[] array, final double element) {
        return (double[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified float array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static float[] removeAllOccurrences(final float[] array, final float element) {
        return (float[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified int array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static int[] removeAllOccurrences(final int[] array, final int element) {
        return (int[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified long array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static long[] removeAllOccurrences(final long[] array, final long element) {
        return (long[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified short array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static short[] removeAllOccurrences(final short[] array, final short element) {
        return (short[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified array.
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contain such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param <T> the type of object in the array, may be {@code null}.
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param element the element to remove, may be {@code null}.
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static <T> T[] removeAllOccurrences(final T[] array, final T element) {
        return (T[]) removeAt(array, indexesOf(array, element));
    }

    /**
     * Removes multiple array elements specified by indices.
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param indices to remove.
     * @return new array of same type minus elements specified by the set bits in {@code indices}.
     */
    // package protected for access by unit tests
    static Object removeAt(final Object array, final BitSet indices) {
        if (array == null) {
            return null;
        }
        final int srcLength = getLength(array);
        // No need to check maxIndex here, because method only currently called from removeElements()
        // which guarantee to generate only valid bit entries.
//        final int maxIndex = indices.length();
//        if (maxIndex > srcLength) {
//            throw new IndexOutOfBoundsException("Index: " + (maxIndex-1) + ", Length: " + srcLength);
//        }
        final int removals = indices.cardinality(); // true bits are items to remove
        final Object result = Array.newInstance(array.getClass().getComponentType(), srcLength - removals);
        int srcIndex = 0;
        int destIndex = 0;
        int count;
        int set;
        while ((set = indices.nextSetBit(srcIndex)) != -1) {
            count = set - srcIndex;
            if (count > 0) {
                System.arraycopy(array, srcIndex, result, destIndex, count);
                destIndex += count;
            }
            srcIndex = indices.nextClearBit(set);
        }
        count = srcLength - srcIndex;
        if (count > 0) {
            System.arraycopy(array, srcIndex, result, destIndex, count);
        }
        return result;
    }


     public static boolean[] removeElements(final boolean[] array, final boolean... values) {
        return null;
    }

    /**
     * Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     * <p>
     * This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     * </p>
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param values  the values to be removed.
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */

     @SafeVarargs
    public static <T> T[] removeElements(final T[] array, final T... values) {

        final HashMap<T, MutableInt> occurrences = new HashMap<>(values.length);
        for (final T v : values) {
            increment(occurrences, v);
        }
        final BitSet toRemove = new BitSet();
        for (int i = 0; i < array.length; i++) {
            final T key = array[i];
            final MutableInt count = occurrences.get(key);
            if (count != null) {
                if (count.decrementAndGet() == 0) {
                    occurrences.remove(key);
                }
                toRemove.set(i);
            }
        }
        @SuppressWarnings("unchecked") // removeAll() always creates an array of the same type as its input
        final T[] result = (T[]) removeAt(array, toRemove);
        return result;
    }

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */
    public static void reverse(final boolean[] array) {
        if (array != null) {
            reverse(array, 0, array.length);
        }
    }

    /**
     * Reverses the order of the given array in the given range.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array
     *            the array to reverse, may be {@code null}.
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final boolean[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(array.length, endIndexExclusive) - 1;
        boolean tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */
    public static void reverse(final byte[] array) {
        if (array != null) {
            reverse(array, 0, array.length);
        }
    }

    /**
     * Reverses the order of the given array in the given range.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array               the array to reverse, may be {@code null}.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no change.
     * @param endIndexExclusive   elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no change. Overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final byte[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(array.length, endIndexExclusive) - 1;
        byte tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */
    public static void reverse(final char[] array) {
        if (array != null) {
            reverse(array, 0, array.length);
        }
    }

    /**
     * Reverses the order of the given array in the given range.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array               the array to reverse, may be {@code null}.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no change.
     * @param endIndexExclusive   elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no change. Overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final char[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(array.length, endIndexExclusive) - 1;
        char tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final double[] array) {
        if (array != null) {
            reverse(array, 0, array.length);
        }
    }

    /**
     * Reverses the order of the given array in the given range.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array               the array to reverse, may be {@code null}.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no change.
     * @param endIndexExclusive   elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no change. Overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final double[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(array.length, endIndexExclusive) - 1;
        double tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */
    public static void reverse(final float[] array) {
        if (array != null) {
            reverse(array, 0, array.length);
        }
    }

    /**
     * Reverses the order of the given array in the given range.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array               the array to reverse, may be {@code null}.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no change.
     * @param endIndexExclusive   elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no change. Overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final float[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(array.length, endIndexExclusive) - 1;
        float tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */
    public static void reverse(final int[] array) {
        if (array != null) {
            reverse(array, 0, array.length);
        }
    }

    /**
     * Reverses the order of the given array in the given range.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array
     *            the array to reverse, may be {@code null}.
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final int[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(array.length, endIndexExclusive) - 1;
        int tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */
    public static void reverse(final long[] array) {
        if (array != null) {
            reverse(array, 0, array.length);
        }
    }

    /**
     * Reverses the order of the given array in the given range.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array
     *            the array to reverse, may be {@code null}.
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final long[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(array.length, endIndexExclusive) - 1;
        long tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * Reverses the order of the given array.
     * <p>
     * There is no special handling for multi-dimensional arrays.
     * </p>
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */
    public static void reverse(final Object[] array) {
        if (array != null) {
            reverse(array, 0, array.length);
        }
    }

    /**
     * Reverses the order of the given array in the given range.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array
     *            the array to reverse, may be {@code null}.
     * @param startIndexInclusive
     *            the starting index. Under value (&lt;0) is promoted to 0, over value (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Under value (&lt; start index) results in no
     *            change. Over value (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final Object[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(array.length, endIndexExclusive) - 1;
        Object tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */
    public static void reverse(final short[] array) {
        if (array != null) {
            reverse(array, 0, array.length);
        }
    }

    /**
     * Reverses the order of the given array in the given range.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array
     *            the array to reverse, may be {@code null}.
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final short[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(array.length, endIndexExclusive) - 1;
        short tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * Sets all elements of the specified array, using the provided generator supplier to compute each element.
     * <p>
     * If the generator supplier throws an exception, it is relayed to the caller and the array is left in an indeterminate
     * state.
     * </p>
     *
     * @param <T> type of elements of the array, may be {@code null}.
     * @param array array to be initialized, may be {@code null}.
     * @param generator a function accepting an index and producing the desired value for that position.
     * @return the input array
     * @since 3.13.0
     */
    public static <T> T[] setAll(final T[] array, final IntFunction<? extends T> generator) {
        if (array != null && generator != null) {
            Arrays.setAll(array, generator);
        }
        return array;
    }

    /**
     * Sets all elements of the specified array, using the provided generator supplier to compute each element.
     * <p>
     * If the generator supplier throws an exception, it is relayed to the caller and the array is left in an indeterminate
     * state.
     * </p>
     *
     * @param <T> type of elements of the array, may be {@code null}.
     * @param array array to be initialized, may be {@code null}.
     * @param generator a function accepting an index and producing the desired value for that position.
     * @return the input array
     * @since 3.13.0
     */
    public static <T> T[] setAll(final T[] array, final Supplier<? extends T> generator) {
        if (array != null && generator != null) {
            for (int i = 0; i < array.length; i++) {
                array[i] = generator.get();
            }
        }
        return array;
    }

    /**
     * Shifts the order of the given boolean array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final boolean[] array, final int offset) {
        if (array != null) {
            shift(array, 0, array.length, offset);
        }
    }

    /**
     * Shifts the order of a series of elements in the given boolean array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}.
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final boolean[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array == null || startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
            return;
        }
        startIndexInclusive = max0(startIndexInclusive);
        endIndexExclusive = Math.min(endIndexExclusive, array.length);
        int n = endIndexExclusive - startIndexInclusive;
        if (n <= 1) {
            return;
        }
        offset %= n;
        if (offset < 0) {
            offset += n;
        }
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while (n > 1 && offset > 0) {
            final int nOffset = n - offset;

        }
    }

    /**
     * Shifts the order of the given byte array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */


    /**
     * Shifts the order of a series of elements in the given byte array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}.
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */


    /**
     * Shifts the order of the given char array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */



    /**
     * Shifts the order of the given double array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */




    /**
     * Shifts the order of a series of elements in the given float array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}.
     *
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */

    public static void shift(final int[] array, final int offset) {
        if (array != null) {
            shift(array, 0, array.length, offset);
        }
    }

    /**
     * Shifts the order of a series of elements in the given int array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}.
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final int[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array == null || startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
            return;
        }
        startIndexInclusive = max0(startIndexInclusive);
        endIndexExclusive = Math.min(endIndexExclusive, array.length);
        int n = endIndexExclusive - startIndexInclusive;
        if (n <= 1) {
            return;
        }
        offset %= n;
        if (offset < 0) {
            offset += n;
        }
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while (n > 1 && offset > 0) {
            final int nOffset = n - offset;
            if (offset > nOffset) {
                swap(array, startIndexInclusive, startIndexInclusive + n - nOffset,  nOffset);
                n = offset;
                offset -= nOffset;
            } else if (offset < nOffset) {
                swap(array, startIndexInclusive, startIndexInclusive + nOffset,  offset);
                startIndexInclusive += offset;
                n = nOffset;
            } else {
                swap(array, startIndexInclusive, startIndexInclusive + nOffset, offset);
                break;
            }
        }
    }

    /**
     * Shifts the order of the given long array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final long[] array, final int offset) {
        if (array != null) {
            shift(array, 0, array.length, offset);
        }
    }

    /**
     * Shifts the order of a series of elements in the given long array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}.
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final long[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array == null || startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
            return;
        }
        startIndexInclusive = max0(startIndexInclusive);
        endIndexExclusive = Math.min(endIndexExclusive, array.length);
        int n = endIndexExclusive - startIndexInclusive;
        if (n <= 1) {
            return;
        }
        offset %= n;
        if (offset < 0) {
            offset += n;
        }
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while (n > 1 && offset > 0) {
            final int nOffset = n - offset;
            if (offset > nOffset) {
                swap(array, startIndexInclusive, startIndexInclusive + n - nOffset,  nOffset);
                n = offset;
                offset -= nOffset;
            } else if (offset < nOffset) {
                swap(array, startIndexInclusive, startIndexInclusive + nOffset,  offset);
                startIndexInclusive += offset;
                n = nOffset;
            } else {
                swap(array, startIndexInclusive, startIndexInclusive + nOffset, offset);
                break;
            }
        }
    }

    /**
     * Shifts the order of the given array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final Object[] array, final int offset) {
        if (array != null) {
            shift(array, 0, array.length, offset);
        }
    }

    /**
     * Shifts the order of a series of elements in the given array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}.
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final Object[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array == null || startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
            return;
        }
        startIndexInclusive = max0(startIndexInclusive);
        endIndexExclusive = Math.min(endIndexExclusive, array.length);
        int n = endIndexExclusive - startIndexInclusive;
        if (n <= 1) {
            return;
        }
        offset %= n;
        if (offset < 0) {
            offset += n;
        }
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while (n > 1 && offset > 0) {
            final int nOffset = n - offset;
            if (offset > nOffset) {
                swap(array, startIndexInclusive, startIndexInclusive + n - nOffset,  nOffset);
                n = offset;
                offset -= nOffset;
            } else if (offset < nOffset) {
                swap(array, startIndexInclusive, startIndexInclusive + nOffset,  offset);
                startIndexInclusive += offset;
                n = nOffset;
            } else {
                swap(array, startIndexInclusive, startIndexInclusive + nOffset, offset);
                break;
            }
        }
    }

    /**
     * Shifts the order of the given short array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */


    /**
     * Shifts the order of a series of elements in the given short array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}.

     */



    public static void shuffle(final Object[] array) {
        shuffle(array, random());
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");


    }


    public static void shuffle(final Object[] array, final Random random) {
        if (array != null && random != null) {
            for (int i = array.length; i > 1; i--) {
                swap(array, i - 1, random.nextInt(i), 1);
            }
        }
    }

    /**
     * Shuffles randomly the elements of the specified array using the <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle
     * algorithm</a>.
     * <p>
     * This method uses the current {@link ThreadLocalRandom} as its random number generator.
     * </p>
     * <p>
     * Instances of {@link ThreadLocalRandom} are not cryptographically secure. For security-sensitive applications, consider using a {@code shuffle} method
     * with a {@link SecureRandom} argument.
     * </p>
     *
     * @param array the array to shuffle.
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final short[] array) {
        shuffle(array, random());
    }

    /**
     * Shuffles randomly the elements of the specified array using the <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle
     * algorithm</a>.
     *
     * @param array  the array to shuffle, no-op if {@code null}.
     * @param random the source of randomness used to permute the elements, no-op if {@code null}.
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final short[] array, final Random random) {
        if (array != null && random != null) {
            for (int i = array.length; i > 1; i--) {
                swap(array, i - 1, random.nextInt(i), 1);
            }
        }
    }

    /**
     * Tests whether the given data array starts with an expected array, for example, signature bytes.
     * <p>
     * If both arrays are null, the method returns true. The method return false when one array is null and the other not.
     * </p>
     *
     * @param data     The data to search, maybe larger than the expected data.
     * @param expected The expected data to find.
     * @return whether a match was found.
     * @since 3.18.0
     */
    public static boolean startsWith(final byte[] data, final byte[] expected) {
        if (data == expected) {
            return true;
        }
        if (data == null || expected == null) {
            return false;
        }
        final int dataLen = data.length;
        if (expected.length > dataLen) {
            return false;
        }
        if (expected.length == dataLen) {
            // delegate to Arrays.equals() which has optimizations on Java > 8
            return Arrays.equals(data, expected);
        }
        // Once we are on Java 9+ we can delegate to Arrays here as well (or not).
        for (int i = 0; i < expected.length; i++) {
            if (data[i] != expected[i]) {
                return false;
            }
        }
        return true;
    }




    /**
     * Swaps two elements in the given int array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}.
     * @param offset1 the index of the first element to swap.
     * @param offset2 the index of the second element to swap.
     * @since 3.5
     */
    public static void swap(final int[] array, final int offset1, final int offset2) {
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps a series of elements in the given int array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 2, 1) -&gt; [3, 2, 1, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 0, 1) -&gt; [1, 2, 3, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 2, 0, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], -3, 2, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 3, 3) -&gt; [4, 2, 3, 1]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}.
     * @param offset1 the index of the first element in the series to swap.
     * @param offset2 the index of the second element in the series to swap.
     * @param len the number of elements to swap starting with the given indices.
     * @since 3.5
     */
    public static void swap(final int[] array,  int offset1, int offset2, int len) {
        if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        offset1 = max0(offset1);
        offset2 = max0(offset2);
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final int aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

    /**
     * Swaps two elements in the given long array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([true, false, true], 0, 2) -&gt; [true, false, true]</li>
     *     <li>ArrayUtils.swap([true, false, true], 0, 0) -&gt; [true, false, true]</li>
     *     <li>ArrayUtils.swap([true, false, true], 1, 0) -&gt; [false, true, true]</li>
     *     <li>ArrayUtils.swap([true, false, true], 0, 5) -&gt; [true, false, true]</li>
     *     <li>ArrayUtils.swap([true, false, true], -1, 1) -&gt; [false, true, true]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}.
     * @param offset1 the index of the first element to swap.
     * @param offset2 the index of the second element to swap.
     * @since 3.5
     */
    public static void swap(final long[] array, final int offset1, final int offset2) {
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps a series of elements in the given long array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 2, 1) -&gt; [3, 2, 1, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 0, 1) -&gt; [1, 2, 3, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 2, 0, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], -3, 2, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 3, 3) -&gt; [4, 2, 3, 1]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}.
     * @param offset1 the index of the first element in the series to swap.
     * @param offset2 the index of the second element in the series to swap.
     * @param len the number of elements to swap starting with the given indices.
     * @since 3.5
     */
    public static void swap(final long[] array,  int offset1, int offset2, int len) {
        if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        offset1 = max0(offset1);
        offset2 = max0(offset2);
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final long aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

    /**
     * Swaps two elements in the given array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 0, 2) -&gt; ["3", "2", "1"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 0, 0) -&gt; ["1", "2", "3"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 1, 0) -&gt; ["2", "1", "3"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 0, 5) -&gt; ["1", "2", "3"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], -1, 1) -&gt; ["2", "1", "3"]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}.
     * @param offset1 the index of the first element to swap.
     * @param offset2 the index of the second element to swap.
     * @since 3.5
     */
    public static void swap(final Object[] array, final int offset1, final int offset2) {
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps a series of elements in the given array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], 0, 2, 1) -&gt; ["3", "2", "1", "4"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], 0, 0, 1) -&gt; ["1", "2", "3", "4"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], 2, 0, 2) -&gt; ["3", "4", "1", "2"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], -3, 2, 2) -&gt; ["3", "4", "1", "2"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], 0, 3, 3) -&gt; ["4", "2", "3", "1"]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}.
     * @param offset1 the index of the first element in the series to swap.
     * @param offset2 the index of the second element in the series to swap.
     * @param len the number of elements to swap starting with the given indices.
     * @since 3.5
     */
    public static void swap(final Object[] array,  int offset1, int offset2, int len) {
        if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        offset1 = max0(offset1);
        offset2 = max0(offset2);
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final Object aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

    /**
     * Swaps two elements in the given short array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}.
     * @param offset1 the index of the first element to swap.
     * @param offset2 the index of the second element to swap.
     * @since 3.5
     */
    public static void swap(final short[] array, final int offset1, final int offset2) {
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps a series of elements in the given short array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 2, 1) -&gt; [3, 2, 1, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 0, 1) -&gt; [1, 2, 3, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 2, 0, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], -3, 2, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 3, 3) -&gt; [4, 2, 3, 1]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}.
     * @param offset1 the index of the first element in the series to swap.
     * @param offset2 the index of the second element in the series to swap.
     * @param len the number of elements to swap starting with the given indices.
     * @since 3.5
     */
    public static void swap(final short[] array, int offset1, int offset2, int len) {
        if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        offset1 = max0(offset1);
        offset2 = max0(offset2);
        if (offset1 == offset2) {
            return;
        }
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final short aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

    /**
     * Create a type-safe generic array.
     * <p>
     * The Java language does not allow an array to be created from a generic type:
     * </p>
     * <pre>
    public static &lt;T&gt; T[] createAnArray(int size) {
        return new T[size]; // compiler error here
    }
    public static &lt;T&gt; T[] createAnArray(int size) {
        return (T[]) new Object[size]; // ClassCastException at runtime
    }
     * </pre>
     * <p>
     * Therefore new arrays of generic types can be created with this method.
     * For example, an array of Strings can be created:
     * </p>
     * <pre>{@code
     * String[] array = ArrayUtils.toArray("1", "2");
     * String[] emptyArray = ArrayUtils.<String>toArray();
     * }</pre>
     * <p>
     * The method is typically used in scenarios, where the caller itself uses generic types
     * that have to be combined into an array.
     * </p>
     * <p>
     * Note, this method makes only sense to provide arguments of the same type so that the
     * compiler can deduce the type of the array itself. While it is possible to select the
     * type explicitly like in
     * {@code Number[] array = ArrayUtils.<Number>toArray(Integer.valueOf(42), Double.valueOf(Math.PI))},
     * there is no real advantage when compared to
     * {@code new Number[] {Integer.valueOf(42), Double.valueOf(Math.PI)}}.
     * </p>
     *
     * @param  <T>   the array's element type.
     * @param  items  the varargs array items, null allowed.
     * @return the array, not null unless a null array is passed in.
     * @since 3.0
     */
    public static <T> T[] toArray(@SuppressWarnings("unchecked") final T... items) {
        return items;
    }

    /**
     * Converts the given array into a {@link java.util.Map}. Each element of the array must be either a {@link java.util.Map.Entry} or an Array, containing at
     * least two elements, where the first element is used as key and the second as value.
     * <p>
     * This method can be used to initialize:
     * </p>
     *
     * <pre>
     *
     * // Create a Map mapping colors.
     * Map colorMap = ArrayUtils.toMap(new String[][] { { "RED", "#FF0000" }, { "GREEN", "#00FF00" }, { "BLUE", "#0000FF" } });
     * </pre>
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array an array whose elements are either a {@link java.util.Map.Entry} or an Array containing at least two elements, may be {@code null}.
     * @return a {@link Map} that was created from the array.
     * @throws IllegalArgumentException if one element of this Array is itself an Array containing less than two elements.
     * @throws IllegalArgumentException if the array contains elements other than {@link java.util.Map.Entry} and an Array.
     */
    public static Map<Object, Object> toMap(final Object[] array) {
        if (array == null) {
            return null;
        }
        final Map<Object, Object> map = new HashMap<>((int) (array.length * 1.5));
        for (int i = 0; i < array.length; i++) {
            final Object object = array[i];
            if (object instanceof Map.Entry<?, ?>) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>) object;
                map.put(entry.getKey(), entry.getValue());
            } else if (object instanceof Object[]) {
                final Object[] entry = (Object[]) object;
                if (entry.length < 2) {
                    throw new IllegalArgumentException("Array element " + i + ", '"
                        + object
                        + "', has a length less than 2");
                }
                map.put(entry[0], entry[1]);
            } else {
                throw new IllegalArgumentException("Array element " + i + ", '"
                        + object
                        + "', is neither of type Map.Entry nor an Array");
            }
        }
        return map;
    }

    /**
     * Converts an array of primitive booleans to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  a {@code boolean} array.
     * @return a {@link Boolean} array, {@code null} if null array input.
     */
    public static Boolean[] toObject(final boolean[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        return setAll(new Boolean[array.length], i -> array[i] ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Converts an array of primitive bytes to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  a {@code byte} array.
     * @return a {@link Byte} array, {@code null} if null array input.
     */
    public static Byte[] toObject(final byte[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        return setAll(new Byte[array.length], i -> Byte.valueOf(array[i]));
    }

    /**
     * Converts an array of primitive chars to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array a {@code char} array.
     * @return a {@link Character} array, {@code null} if null array input.
     */
    public static Character[] toObject(final char[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        return setAll(new Character[array.length], i -> Character.valueOf(array[i]));
     }

    /**
     * Converts an array of primitive doubles to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  a {@code double} array.
     * @return a {@link Double} array, {@code null} if null array input.
     */
    public static Double[] toObject(final double[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        return setAll(new Double[array.length], i -> Double.valueOf(array[i]));
    }

    /**
     * Converts an array of primitive floats to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  a {@code float} array.
     * @return a {@link Float} array, {@code null} if null array input.
     */
    public static Float[] toObject(final float[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        return setAll(new Float[array.length], i -> Float.valueOf(array[i]));
    }

    /**
     * Converts an array of primitive ints to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  an {@code int} array.
     * @return an {@link Integer} array, {@code null} if null array input.
     */
    public static Integer[] toObject(final int[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        return setAll(new Integer[array.length], i -> Integer.valueOf(array[i]));
    }

    /**
     * Converts an array of primitive longs to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  a {@code long} array.
     * @return a {@link Long} array, {@code null} if null array input.
     */
    public static Long[] toObject(final long[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        return setAll(new Long[array.length], i -> Long.valueOf(array[i]));
    }

    /**
     * Converts an array of primitive shorts to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  a {@code short} array.
     * @return a {@link Short} array, {@code null} if null array input.
     */
    public static Short[] toObject(final short[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        return setAll(new Short[array.length], i -> Short.valueOf(array[i]));
    }

    /**
     * Converts an array of object Booleans to primitives.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     * <p>
     * Null array elements map to false, like {@code Boolean.parseBoolean(null)} and its callers return false.
     * </p>
     *
     * @param array a {@link Boolean} array, may be {@code null}.
     * @return a {@code boolean} array, {@code null} if null array input.
     */
    public static boolean[] toPrimitive(final Boolean[] array) {
        return toPrimitive(array, false);
    }

    /**
     * Converts an array of object Booleans to primitives handling {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Boolean} array, may be {@code null}.
     * @param valueForNull  the value to insert if {@code null} found.
     * @return a {@code boolean} array, {@code null} if null array input.
     */
    public static boolean[] toPrimitive(final Boolean[] array, final boolean valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            final Boolean b = array[i];
            result[i] = b == null ? valueForNull : b.booleanValue();
        }
        return result;
    }

    /**
     * Converts an array of object Bytes to primitives.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Byte} array, may be {@code null}.
     * @return a {@code byte} array, {@code null} if null array input.
     * @throws NullPointerException if an array element is {@code null}.
     */
    public static byte[] toPrimitive(final Byte[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    /**
     * Converts an array of object Bytes to primitives handling {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Byte} array, may be {@code null}.
     * @param valueForNull  the value to insert if {@code null} found.
     * @return a {@code byte} array, {@code null} if null array input.
     */
    public static byte[] toPrimitive(final Byte[] array, final byte valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            final Byte b = array[i];
            result[i] = b == null ? valueForNull : b.byteValue();
        }
        return result;
    }

    /**
     * Converts an array of object Characters to primitives.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Character} array, may be {@code null}.
     * @return a {@code char} array, {@code null} if null array input.
     * @throws NullPointerException if an array element is {@code null}.
     */
    public static char[] toPrimitive(final Character[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].charValue();
        }
        return result;
    }

    /**
     * Converts an array of object Character to primitives handling {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Character} array, may be {@code null}.
     * @param valueForNull  the value to insert if {@code null} found.
     * @return a {@code char} array, {@code null} if null array input.
     */
    public static char[] toPrimitive(final Character[] array, final char valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            final Character b = array[i];
            result[i] = b == null ? valueForNull : b.charValue();
        }
        return result;
    }

    /**
     * Converts an array of object Doubles to primitives.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Double} array, may be {@code null}.
     * @return a {@code double} array, {@code null} if null array input.
     * @throws NullPointerException if an array element is {@code null}.
     */
    public static double[] toPrimitive(final Double[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    /**
     * Converts an array of object Doubles to primitives handling {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Double} array, may be {@code null}.
     * @param valueForNull  the value to insert if {@code null} found.
     * @return a {@code double} array, {@code null} if null array input.
     */
    public static double[] toPrimitive(final Double[] array, final double valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            final Double b = array[i];
            result[i] = b == null ? valueForNull : b.doubleValue();
        }
        return result;
    }

    /**
     * Converts an array of object Floats to primitives.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Float} array, may be {@code null}.
     * @return a {@code float} array, {@code null} if null array input.
     * @throws NullPointerException if an array element is {@code null}.
     */
    public static float[] toPrimitive(final Float[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    /**
     * Converts an array of object Floats to primitives handling {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Float} array, may be {@code null}.
     * @param valueForNull  the value to insert if {@code null} found.
     * @return a {@code float} array, {@code null} if null array input.
     */
    public static float[] toPrimitive(final Float[] array, final float valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            final Float b = array[i];
            result[i] = b == null ? valueForNull : b.floatValue();
        }
        return result;
    }

    /**
     * Converts an array of object Integers to primitives.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Integer} array, may be {@code null}.
     * @return an {@code int} array, {@code null} if null array input.
     * @throws NullPointerException if an array element is {@code null}.
     */
    public static int[] toPrimitive(final Integer[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        final int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    public static String toString(final Object array, final String stringIfNull) {
        return array != null ? new ToStringBuilder(array, ToStringStyle.SIMPLE_STYLE).append(array).toString() : stringIfNull;
    }

    /**
     * Returns an array containing the string representation of each element in the argument array.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array the {@code Object[]} to be processed, may be {@code null}.
     * @return {@code String[]} of the same size as the source with its element's string representation, {@code null} if null array input.
     * @since 3.6
     */
    public static String[] toStringArray(final Object[] array) {
        return toStringArray(array, "null");
    }

    /**
     * Returns an array containing the string representation of each element in the argument array handling {@code null} elements.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array                the Object[] to be processed, may be {@code null}.
     * @param valueForNullElements the value to insert if {@code null} is found.
     * @return a {@link String} array, {@code null} if null array input.
     * @since 3.6
     */
    public static String[] toStringArray(final Object[] array, final String valueForNullElements) {
        if (null == array) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_STRING_ARRAY;
        }
        return map(array, String.class, e -> Objects.toString(e, valueForNullElements));
    }

    /**
     * ArrayUtils instances should NOT be constructed in standard programming. Instead, the class should be used as {@code ArrayUtils.clone(new int[] {2})}.
     * <p>
     * This constructor is public to permit tools that require a JavaBean instance to operate.
     * </p>
     *
     * @deprecated TODO Make private in 4.0.
     */
    @Deprecated
    public ArrayUtils() {
        // empty
    }
}
