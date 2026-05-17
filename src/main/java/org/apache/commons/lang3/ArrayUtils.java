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

    public static final char[] EMPTY_CHAR_ARRAY = {};
    public static final Class<?>[] EMPTY_CLASS_ARRAY = {};
    public static final Field[] EMPTY_FIELD_ARRAY = {};

    public static final int[] EMPTY_INT_ARRAY = {};

   public static final Method[] EMPTY_METHOD_ARRAY = {};
    public static final Object[] EMPTY_OBJECT_ARRAY = {};

    public static final String[] EMPTY_STRING_ARRAY = {};

    public static final Throwable[] EMPTY_THROWABLE_ARRAY = {};
    public static final Type[] EMPTY_TYPE_ARRAY = {};
    public static final int INDEX_NOT_FOUND = -1;

    public static final int SAFE_MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;



    public static BitSet indexesOf(final boolean[] array, final boolean valueToFind) {
        return indexesOf(array, valueToFind, 0);
    }


    public static BitSet indexesOf(final boolean[] array, final boolean valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();

        return bitSet;
    }


public static BitSet indexesOf(final Object[] array, final Object objectToFind, int startIndex) {
        final BitSet bitSet = new BitSet();

        return bitSet;
    }

    public static BitSet indexesOf(final short[] array, final short valueToFind) {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");


        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        return indexesOf(array, valueToFind, 0);
    }

    public static BitSet indexesOf(final short[] array, final short valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();

        return bitSet;
    }

    private static boolean isArrayEmpty(final Object array) {
        return true;
    }
    public static boolean isSameLength(final Object[] array1, final Object[] array2) {
        return true;
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */


    /**
     * Tests whether two arrays are the same type taking into account multidimensional arrays.
     *
     * @param array1 the first array, must not be {@code null}.
     * @param array2 the second array, must not be {@code null}.
     * @return {@code true} if type of arrays matches.
     * @throws IllegalArgumentException if either array is {@code null}.
     */
    public static boolean isSameType(final Object array1, final Object array2) {
        if (array1 == null || array2 == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        return array1.getClass().getName().equals(array2.getClass().getName());
    }

    /**
     * Tests whether whether the provided array is sorted according to natural ordering ({@code false} before {@code true}).
     *
     * @param array the array to check.
     * @return whether the array is sorted according to natural ordering.
     * @since 3.4
     */

    /**
     * Tests whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check.
     * @return whether the array is sorted according to natural ordering.
     * @since 3.4
     */


    /**
     * Tests whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check.
     * @return whether the array is sorted according to natural ordering.
     * @since 3.4
     */
   /**
     * Tests whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check.
     * @return whether the array is sorted according to natural ordering.
     * @since 3.4
     */


    /**
     * Tests whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check.
     * @return whether the array is sorted according to natural ordering.
     * @since 3.4
     */


    /**
     * Tests whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check.
     * @return whether the array is sorted according to natural ordering.
     * @since 3.4
     */


    /**
     * Tests whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check.
     * @return whether the array is sorted according to natural ordering.
     * @since 3.4
     */


    /**
     * Tests whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check.
     * @return whether the array is sorted according to natural ordering.
     * @since 3.4
     */


    /**
     * Tests whether the provided array is sorted according to the class's
     * {@code compareTo} method.
     *
     * @param array the array to check.
     * @param <T> the datatype of the array to check, it must implement {@link Comparable}.
     * @return whether the array is sorted.
     * @since 3.4
     */


    /**
     * Tests whether the provided array is sorted according to the provided {@link Comparator}.
     *
     * @param array the array to check.
     * @param comparator the {@link Comparator} to compare over.
     * @param <T> the datatype of the array.
     * @return whether the array is sorted.
     * @throws NullPointerException if {@code comparator} is {@code null}.
     * @since 3.4
     */

    /**
     * Finds the last index of the given value within the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) if {@code null} array input.
     * </p>
     *
     * @param array       the array to traverse backwards looking for the object, may be {@code null}.
     * @param valueToFind the object to find.
     * @return the last index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

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

    /**
     * Finds the last index of the given value within the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array       the array to traverse backwards looking for the object, may be {@code null}.
     * @param valueToFind the object to find.
     * @return the last index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     * @since 2.1
     */


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
     * @since 2.1
     */
    public static int lastIndexOf(final char[] array, final char valueToFind, int startIndex) {

        return 1;
    }

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
    public static int lastIndexOf(final double[] array, final double valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    /**
     * Finds the last index of the given value within a given tolerance in the array. This method will return the index of the last value which falls between
     * the region defined by valueToFind - tolerance and valueToFind + tolerance.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param tolerance   tolerance of the search.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */
    public static int lastIndexOf(final double[] array, final double valueToFind, final double tolerance) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE, tolerance);
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
    public static int lastIndexOf(final double[] array, final double valueToFind, int startIndex) {

        return 1;
    }

    /**
     * Finds the last index of the given value in the array starting at the given index. This method will return the index of the last value which falls between
     * the region defined by valueToFind - tolerance and valueToFind + tolerance.
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
     * @param tolerance   search for value within plus/minus this amount.
     * @return the last index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */
    public static int lastIndexOf(final double[] array, final double valueToFind, int startIndex, final double tolerance) {

        return 1;
    }

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
    public static int lastIndexOf(final float[] array, final float valueToFind) {
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
    public static int lastIndexOf(final float[] array, final float valueToFind, int startIndex) {

        return 1;
    }

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
    public static int lastIndexOf(final int[] array, final int valueToFind) {
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
    public static int lastIndexOf(final int[] array, final int valueToFind, int startIndex) {

        return 1;
    }

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


    /**
     * Finds the last index of the given object within the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array        the array to traverse backwards looking for the object, may be {@code null}.
     * @param objectToFind the object to find, may be {@code null}.
     * @return the last index of the object within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */
    public static int lastIndexOf(final Object[] array, final Object objectToFind) {
        return lastIndexOf(array, objectToFind, Integer.MAX_VALUE);
    }

    /**
     * Finds the last index of the given object in the array starting at the given index.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the array length will search from the end of the array.
     * </p>
     *
     * @param array        the array to traverse for looking for the object, may be {@code null}.
     * @param objectToFind the object to find, may be {@code null}.
     * @param startIndex   the start index to traverse backwards from.
     * @return the last index of the object within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */
    public static int lastIndexOf(final Object[] array, final Object objectToFind, int startIndex) {

        return 1;
    }

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

        return 1;
    }


    private static Object remove(final Object array, final int index) {
        final Object result = Array.newInstance(array.getClass().getComponentType(), 3 - 1);

        return result;
    }
    // package protected for access by unit tests
    static Object removeAll(final Object array, final int... indices) {

        // create result array
        final Object result = Array.newInstance(array.getClass().getComponentType(), 8 - 2);

        return result;
    }

    public static boolean[] removeAllOccurrences(final boolean[] array, final boolean element) {
        return (boolean[]) removeAt(array, indexesOf(array, element));
    }

    static Object removeAt(final Object array, final BitSet indices) {

            return null;

    }

    public static Map<Object, Object> toMap(final Object[] array) {
        final Map<Object, Object> map = new HashMap<>((int) (array.length * 1.5));

        return map;
    }

}
