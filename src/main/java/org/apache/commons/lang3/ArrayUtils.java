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

/**
 * Operations on arrays, primitive arrays (like {@code int[]}) and
 * primitive wrapper arrays (like {@code Integer[]}).
 * <p>
 * This class tries to handle {@code null} input gracefully.
 * An exception will not be thrown for a {@code null}
 * array input. However, an Object array that contains a {@code null}
 * element may throw an exception. Each method documents its behavior.
 * </p>
 * <p>
 * #ThreadSafe#
 * </p>
 *
 * @since 2.0
 */
public class ArrayUtils {

    /**
     * Bridge class to {@link Math} methods for testing purposes.
     */


    /**
     * An empty immutable {@code boolean} array.
     */
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

    /**
     * The {@code MAX_ARRAY_LENGTH} constant from Java's internal ArraySupport class.
     *
     * @since 3.21.0
     */
    public static final int SAFE_MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;

    /**
     * Copies the given array and adds the given element at the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add(null, true)          = [true]
     * ArrayUtils.add([true], false)       = [true, false]
     * ArrayUtils.add([true, false], true) = [true, false, true]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}.
     * @param element  the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 2.1
     */

    /**
     * Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * <p>
     * This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add(null, 0, true)          = [true]
     * ArrayUtils.add([true], 0, false)       = [false, true]
     * ArrayUtils.add([false], 1, true)       = [false, true]
     * ArrayUtils.add([true, false], 1, true) = [true, true, false]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}.
     * @param index  the position of the new object.
     * @param element  the object to add.
     * @return A new array containing the existing elements and the new element.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, boolean[], boolean...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */

    /**
     * Copies the given array and adds the given element at the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}.
     * @param element  the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 2.1
     */

    /**
     * Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * <p>
     * This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add([1], 0, 2)         = [2, 1]
     * ArrayUtils.add([2, 6], 2, 3)      = [2, 6, 3]
     * ArrayUtils.add([2, 6], 0, 1)      = [1, 2, 6]
     * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}.
     * @param index  the position of the new object.
     * @param element  the object to add.
     * @return A new array containing the existing elements and the new element.
     * @throws IndexOutOfBoundsException if the index is out of range.
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, byte[], byte...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */

    /**
     * Copies the given array and adds the given element at the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add(null, '0')       = ['0']
     * ArrayUtils.add(['1'], '0')      = ['1', '0']
     * ArrayUtils.add(['1', '0'], '1') = ['1', '0', '1']
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}.
     * @param element  the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 2.1
     */

    /**
     * Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * <p>
     * This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add(null, 0, 'a')            = ['a']
     * ArrayUtils.add(['a'], 0, 'b')           = ['b', 'a']
     * ArrayUtils.add(['a', 'b'], 0, 'c')      = ['c', 'a', 'b']
     * ArrayUtils.add(['a', 'b'], 1, 'k')      = ['a', 'k', 'b']
     * ArrayUtils.add(['a', 'b', 'c'], 1, 't') = ['a', 't', 'b', 'c']
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}.
     * @param index  the position of the new object.
     * @param element  the object to add.
     * @return A new array containing the existing elements and the new element.
     * @throws IndexOutOfBoundsException if the index is out of range.
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, char[], char...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */

    /**
     * Copies the given array and adds the given element at the end of the new array.
     *
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}.
     * @param element  the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 2.1
     */

    /**
     * Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * <p>
     * This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add([1.1], 0, 2.2)              = [2.2, 1.1]
     * ArrayUtils.add([2.3, 6.4], 2, 10.5)        = [2.3, 6.4, 10.5]
     * ArrayUtils.add([2.6, 6.7], 0, -4.8)        = [-4.8, 2.6, 6.7]
     * ArrayUtils.add([2.9, 6.0, 0.3], 2, 1.0)    = [2.9, 6.0, 1.0, 0.3]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}.
     * @param index  the position of the new object.
     * @param element  the object to add.
     * @return A new array containing the existing elements and the new element.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, double[], double...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */


    /**
     * Copies the given array and adds the given element at the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}.
     * @param element  the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 2.1
     */


    /**
     * Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * <p>
     * This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add([1.1f], 0, 2.2f)               = [2.2f, 1.1f]
     * ArrayUtils.add([2.3f, 6.4f], 2, 10.5f)        = [2.3f, 6.4f, 10.5f]
     * ArrayUtils.add([2.6f, 6.7f], 0, -4.8f)        = [-4.8f, 2.6f, 6.7f]
     * ArrayUtils.add([2.9f, 6.0f, 0.3f], 2, 1.0f)   = [2.9f, 6.0f, 1.0f, 0.3f]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}.
     * @param index  the position of the new object.
     * @param element  the object to add.
     * @return A new array containing the existing elements and the new element.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     *
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */


    /**
     * Copies the given array and adds the given element at the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}.
     * @param element  the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 2.1
     */

    /**
     * Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * <p>
     * This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add([1], 0, 2)         = [2, 1]
     * ArrayUtils.add([2, 6], 2, 10)     = [2, 6, 10]
     * ArrayUtils.add([2, 6], 0, -4)     = [-4, 2, 6]
     * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}.
     * @param index  the position of the new object.
     * @param element  the object to add.
     * @return A new array containing the existing elements and the new element.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, int[], int...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */


    /**
     * Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * <p>
     * This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add([1L], 0, 2L)           = [2L, 1L]
     * ArrayUtils.add([2L, 6L], 2, 10L)      = [2L, 6L, 10L]
     * ArrayUtils.add([2L, 6L], 0, -4L)      = [-4L, 2L, 6L]
     * ArrayUtils.add([2L, 6L, 3L], 2, 1L)   = [2L, 6L, 1L, 3L]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}.
     * @param index  the position of the new object.
     * @param element  the object to add.
     * @return A new array containing the existing elements and the new element.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, long[], long...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */

    /**
     * Copies the given array and adds the given element at the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}.
     * @param element  the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 2.1
     */


    /**
     * Underlying implementation of add(array, index, element) methods.
     * The last parameter is the class, which may not equal element.getClass
     * for primitives.
     *
     * @param array  the array to add the element to, may be {@code null}.
     * @param index  the position of the new object.
     * @param element  the object to add.
     * @param clazz the type of the element being added.
     * @return A new array containing the existing elements and the new element.
     */
    private static Object add(final Object array, final int index, final Object element, final Class<?> clazz) {

        return null;
    }

    /**
     * Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * <p>
     * This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add([1], 0, 2)         = [2, 1]
     * ArrayUtils.add([2, 6], 2, 10)     = [2, 6, 10]
     * ArrayUtils.add([2, 6], 0, -4)     = [-4, 2, 6]
     * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}.
     * @param index  the position of the new object.
     * @param element  the object to add.
     * @return A new array containing the existing elements and the new element.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, short[], short...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */


    /**
     * Copies the given array and adds the given element at the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}.
     * @param element  the object to add at the last index of the new array.
     * @return A new array containing the existing elements plus the new element.
     * @since 2.1
     */


    /**
     * Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * <p>
     * This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element.
     * </p>
     * <pre>
     * ArrayUtils.add(null, 0, null)      = Throws {@link IllegalArgumentException}
     * ArrayUtils.add(null, 0, "a")       = ["a"]
     * ArrayUtils.add(["a"], 1, null)     = ["a", null]
     * ArrayUtils.add(["a"], 1, "b")      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], 3, "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param <T> the component type of the array.
     * @param array  the array to add the element to, may be {@code null}.
     * @param index  the position of the new object.
     * @param element  the object to add.
     * @return A new array containing the existing elements and the new element.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; array.length).
     * @throws IllegalArgumentException if both array and element are null.
     * @deprecated this method has been superseded by {@link #insert(int, Object[], Object...) insert(int, T[], T...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */

    /**
     * Copies the given array and adds the given element at the end of the new array.
     * <p>
     * The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element, unless the element itself is null,
     * in which case the return type is Object[]
     * </p>
     * <pre>
     * ArrayUtils.add(null, null)      = Throws {@link IllegalArgumentException}
     * ArrayUtils.add(null, "a")       = ["a"]
     * ArrayUtils.add(["a"], null)     = ["a", null]
     * ArrayUtils.add(["a"], "b")      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param <T> the component type of the array.
     * @param array  the array to "add" the element to, may be {@code null}.
     * @param element  the object to add, may be {@code null}.
     * @return A new array containing the existing elements plus the new element
     * The returned array type will be that of the input array (unless null),
     * in which case it will have the same type as the element.
     * If both are null, an IllegalArgumentException is thrown.
     * @throws IllegalArgumentException if both arguments are null.
     * @since 2.1
     */

    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     * </p>
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll(null, null)     = null
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new boolean[] array or {@code null}.
     * @since 2.1
     */


    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     * </p>
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll(null, null)     = null
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new byte[] array or {@code null}.
     * @since 2.1
     */


    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     * </p>
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll(null, null)     = null
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new char[] array or {@code null}.
     * @since 2.1
     */


    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     * </p>
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll(null, null)     = null
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new double[] array or {@code null}.
     * @since 2.1
     */


    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     * </p>
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll(null, null)     = null
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new float[] array or {@code null}.
     * @since 2.1
     */

    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     * </p>
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll(null, null)     = null
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new int[] array or {@code null}.
     * @since 2.1
     */


    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     * </p>
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll(null, null)     = null
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new long[] array or {@code null}.
     * @since 2.1
     */


    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     * </p>
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll(null, null)     = null
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new short[] array or {@code null}.
     * @since 2.1
     */


    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     * </p>
     * <pre>
     * ArrayUtils.addAll(null, null)     = null
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll(null, null)     = null
     * ArrayUtils.addAll([null], [null]) = [null, null]
     * ArrayUtils.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
     * </pre>
     *
     * @param <T> the component type of the array.
     * @param array1  the first array whose elements are added to the new array, may be {@code null}.
     * @param array2  the second array whose elements are added to the new array, may be {@code null}.
     * @return The new array, {@code null} if both arrays are {@code null}.
     *      The type of the new array is the type of the first array,
     *      unless the first array is null, in which case the type is the same as the second array.
     * @throws IllegalArgumentException if the array types are incompatible.
     * @since 2.1
     */

    /**
     * Safely adds the length of an array to a running total, checking for overflow.
     *
     * @param totalLength the current accumulated length
     * @param array the array whose length should be added (can be {@code null},
     *              in which case its length is considered 0)
     * @return the new total length after adding the array's length
     * @throws IllegalArgumentException if total arrays length exceed {@link ArrayUtils#SAFE_MAX_ARRAY_LENGTH}.
     */

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     * <pre>
     * ArrayUtils.addFirst(null, true)          = [true]
     * ArrayUtils.addFirst([true], false)       = [false, true]
     * ArrayUtils.addFirst([true, false], true) = [true, true, false]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */


    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     * <pre>
     * ArrayUtils.addFirst(null, 1)   = [1]
     * ArrayUtils.addFirst([1], 0)    = [0, 1]
     * ArrayUtils.addFirst([1, 0], 1) = [1, 1, 0]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */


    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     * <pre>
     * ArrayUtils.addFirst(null, '1')       = ['1']
     * ArrayUtils.addFirst(['1'], '0')      = ['0', '1']
     * ArrayUtils.addFirst(['1', '0'], '1') = ['1', '1', '0']
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */


    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     * <pre>
     * ArrayUtils.addFirst(null, 1)   = [1]
     * ArrayUtils.addFirst([1], 0)    = [0, 1]
     * ArrayUtils.addFirst([1, 0], 1) = [1, 1, 0]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     * <pre>
     * ArrayUtils.addFirst(null, 1)   = [1]
     * ArrayUtils.addFirst([1], 0)    = [0, 1]
     * ArrayUtils.addFirst([1, 0], 1) = [1, 1, 0]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */


    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     * <pre>
     * ArrayUtils.addFirst(null, 1)   = [1]
     * ArrayUtils.addFirst([1], 0)    = [0, 1]
     * ArrayUtils.addFirst([1, 0], 1) = [1, 1, 0]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */


    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     * <pre>
     * ArrayUtils.addFirst(null, 1)   = [1]
     * ArrayUtils.addFirst([1], 0)    = [0, 1]
     * ArrayUtils.addFirst([1, 0], 1) = [1, 1, 0]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     * <pre>
     * ArrayUtils.addFirst(null, 1)   = [1]
     * ArrayUtils.addFirst([1], 0)    = [0, 1]
     * ArrayUtils.addFirst([1, 0], 1) = [1, 1, 0]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element, unless the element itself is null, in which case the return type is Object[]
     * </p>
     * <pre>
     * ArrayUtils.addFirst(null, null)      = Throws {@link IllegalArgumentException}
     * ArrayUtils.addFirst(null, "a")       = ["a"]
     * ArrayUtils.addFirst(["a"], null)     = [null, "a"]
     * ArrayUtils.addFirst(["a"], "b")      = ["b", "a"]
     * ArrayUtils.addFirst(["a", "b"], "c") = ["c", "a", "b"]
     * </pre>
     *
     * @param <T> the component type of the array.
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add, may be {@code null}.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element. If both are null,
     *         an IllegalArgumentException is thrown.
     * @throws IllegalArgumentException if both arguments are null.
     * @since 3.10
     */


    /**
     * A fluent version of {@link System#arraycopy(Object, int, Object, int, int)} that returns the destination array.
     *
     * @param <T>       the type.
     * @param source    the source array.
     * @param sourcePos starting position in the source array.
     * @param destPos   starting position in the destination data.
     * @param length    the number of array elements to be copied.
     * @param allocator allocates the array to populate and return.
     * @return dest
     * @throws IndexOutOfBoundsException if copying would cause access of data outside array bounds.
     * @throws ArrayStoreException       if an element in the {@code src} array could not be stored into the {@code dest} array because of a type
     *                                   mismatch.
     * @throws NullPointerException      if either {@code src} or {@code dest} is {@code null}.
     * @since 3.15.0
     */

    /**
     * A fluent version of {@link System#arraycopy(Object, int, Object, int, int)} that returns the destination array.
     *
     * @param <T>       the type.
     * @param source    the source array.
     * @param sourcePos starting position in the source array.
     * @param destPos   starting position in the destination data.
     * @param length    the number of array elements to be copied.
     * @param allocator allocates the array to populate and return.
     * @return dest
     * @throws IndexOutOfBoundsException if copying would cause access of data outside array bounds.
     * @throws ArrayStoreException       if an element in the {@code src} array could not be stored into the {@code dest} array because of a type
     *                                   mismatch.
     * @throws NullPointerException      if either {@code src} or {@code dest} is {@code null}.
     * @since 3.15.0
     */

    /**
     * A fluent version of {@link System#arraycopy(Object, int, Object, int, int)} that returns the destination array.
     *
     * @param <T>       the type.
     * @param source    the source array.
     * @param sourcePos starting position in the source array.
     * @param dest      the destination array.
     * @param destPos   starting position in the destination data.
     * @param length    the number of array elements to be copied.
     * @return dest
     * @throws IndexOutOfBoundsException if copying would cause access of data outside array bounds.
     * @throws ArrayStoreException       if an element in the {@code src} array could not be stored into the {@code dest} array because of a type
     *                                   mismatch.
     * @throws NullPointerException      if either {@code src} or {@code dest} is {@code null}.
     * @since 3.15.0
     */


    /**
     * Clones an array or returns {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, {@code null} if {@code null} input.
     */

    /**
     * Clones an array or returns {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, {@code null} if {@code null} input.
     */

    /**
     * Clones an array or returns {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, {@code null} if {@code null} input.
     */

    /**
     * Clones an array or returns {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, {@code null} if {@code null} input.
     */

    /**
     * Clones an array or returns {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, {@code null} if {@code null} input.
     */
  /**
     * Clones an array or returns {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, {@code null} if {@code null} input.
     */

    /**
     * Clones an array or returns {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, {@code null} if {@code null} input.
     */

    /**
     * Clones an array or returns {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array the array to clone, may be {@code null}.
     * @return the cloned array, {@code null} if {@code null} input.
     */

    /**
     * Shallow clones an array or returns {@code null}.
     * <p>
     * The objects in the array are not cloned, thus there is no special handling for multi-dimensional arrays.
     * </p>
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param <T>   the component type of the array.
     * @param array the array to shallow clone, may be {@code null}.
     * @return the cloned array, {@code null} if {@code null} input.
     */

    /**
     * Concatenates multiple boolean arrays into a single array.
     * <p>
     * This method combines all input arrays in the order they are provided,
     * creating a new array that contains all elements from the input arrays.
     * The resulting array length is the sum of lengths of all non-null input arrays.
     * </p>
     *
     * @param arrays the arrays to concatenate. Can be empty, contain nulls,
     *               or be null itself (treated as empty varargs).
     * @return a new boolean array containing all elements from the input arrays
     *         in the order they appear, or an empty array if no elements are present.
     * @throws NullPointerException if the input array of arrays is null.
     * @throws IllegalArgumentException if total arrays length exceed {@link ArrayUtils#SAFE_MAX_ARRAY_LENGTH}.
     * @since 3.21.0
     */


    /**
     * Concatenates multiple byte arrays into a single array.
     * <p>
     * This method combines all input arrays in the order they are provided,
     * creating a new array that contains all elements from the input arrays.
     * The resulting array length is the sum of lengths of all non-null input arrays.
     * </p>
     *
     * @param arrays the arrays to concatenate. Can be empty, contain nulls,
     *               or be null itself (treated as empty varargs).
     * @return a new byte array containing all elements from the input arrays
     *         in the order they appear, or an empty array if no elements are present.
     * @throws NullPointerException if the input array of arrays is null.
     * @throws IllegalArgumentException if total arrays length exceed {@link ArrayUtils#SAFE_MAX_ARRAY_LENGTH}.
     * @since 3.21.0
     */


    /**
     * Concatenates multiple char arrays into a single array.
     * <p>
     * This method combines all input arrays in the order they are provided,
     * creating a new array that contains all elements from the input arrays.
     * The resulting array length is the sum of lengths of all non-null input arrays.
     * </p>
     *
     * @param arrays the arrays to concatenate. Can be empty, contain nulls,
     *               or be null itself (treated as empty varargs).
     * @return a new char array containing all elements from the input arrays
     *         in the order they appear, or an empty array if no elements are present.
     * @throws NullPointerException if the input array of arrays is null.
     * @throws IllegalArgumentException if total arrays length exceed {@link ArrayUtils#SAFE_MAX_ARRAY_LENGTH}.
     * @since 3.21.0
     */

    /**
     * Concatenates multiple double arrays into a single array.
     * <p>
     * This method combines all input arrays in the order they are provided,
     * creating a new array that contains all elements from the input arrays.
     * The resulting array length is the sum of lengths of all non-null input arrays.
     * </p>
     *
     * @param arrays the arrays to concatenate. Can be empty, contain nulls,
     *               or be null itself (treated as empty varargs).
     * @return a new double array containing all elements from the input arrays
     *         in the order they appear, or an empty array if no elements are present.
     * @throws NullPointerException if the input array of arrays is null.
     * @throws IllegalArgumentException if total arrays length exceed {@link ArrayUtils#SAFE_MAX_ARRAY_LENGTH}.
     * @since 3.21.0
     */
      /**
     * Concatenates multiple float arrays into a single array.
     * <p>
     * This method combines all input arrays in the order they are provided,
     * creating a new array that contains all elements from the input arrays.
     * The resulting array length is the sum of lengths of all non-null input arrays.
     * </p>
     *
     * @param arrays the arrays to concatenate. Can be empty, contain nulls,
     *               or be null itself (treated as empty varargs).
     * @return a new float array containing all elements from the input arrays
     *         in the order they appear, or an empty array if no elements are present.
     * @throws NullPointerException if the input array of arrays is null.
     * @throws IllegalArgumentException if total arrays length exceed {@link ArrayUtils#SAFE_MAX_ARRAY_LENGTH}.
     * @since 3.21.0
     */

    /**
     * Concatenates multiple int arrays into a single array.
     * <p>
     * This method combines all input arrays in the order they are provided,
     * creating a new array that contains all elements from the input arrays.
     * The resulting array length is the sum of lengths of all non-null input arrays.
     * </p>
     *
     * @param arrays the arrays to concatenate. Can be empty, contain nulls,
     *               or be null itself (treated as empty varargs).
     * @return a new int array containing all elements from the input arrays
     *         in the order they appear, or an empty array if no elements are present.
     * @throws NullPointerException if the input array of arrays is null.
     * @throws IllegalArgumentException if total arrays length exceed {@link ArrayUtils#SAFE_MAX_ARRAY_LENGTH}.
     * @since 3.21.0
     */

    /**
     * Concatenates multiple long arrays into a single array.
     * <p>
     * This method combines all input arrays in the order they are provided,
     * creating a new array that contains all elements from the input arrays.
     * The resulting array length is the sum of lengths of all non-null input arrays.
     * </p>
     *
     * @param arrays the arrays to concatenate. Can be empty, contain nulls,
     *               or be null itself (treated as empty varargs).
     * @return a new long array containing all elements from the input arrays
     *         in the order they appear, or an empty array if no elements are present.
     * @throws NullPointerException if the input array of arrays is null.
     * @throws IllegalArgumentException if total arrays length exceed {@link ArrayUtils#SAFE_MAX_ARRAY_LENGTH}.
     * @since 3.21.0
     */

    /**
     * Concatenates multiple short arrays into a single array.
     * <p>
     * This method combines all input arrays in the order they are provided,
     * creating a new array that contains all elements from the input arrays.
     * The resulting array length is the sum of lengths of all non-null input arrays.
     * </p>
     *
     * @param arrays the arrays to concatenate. Can be empty, contain nulls,
     *               or be null itself (treated as empty varargs).
     * @return a new short array containing all elements from the input arrays
     *         in the order they appear, or an empty array if no elements are present.
     * @throws NullPointerException if the input array of arrays is null.
     * @throws IllegalArgumentException if total arrays length exceed {@link ArrayUtils#SAFE_MAX_ARRAY_LENGTH}.
     * @since 3.21.0
     */

    /**
     * Checks if the value is in the given array.
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * </p>
     *
     * @param array  the array to search.
     * @param valueToFind  the value to find.
     * @return {@code true} if the array contains the object.
     */


    /**
     * Checks if the value is in the given array.
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * </p>
     * <p>
     * If the {@code array} elements you are searching implement {@link Comparator}, consider whether it is worth using
     * {@link Arrays#sort(byte[])} and {@link Arrays#binarySearch(byte[], byte)}.
     * </p>
     *
     * @param array  the array to search.
     * @param valueToFind  the value to find.
     * @return {@code true} if the array contains the object.
     */


    /**
     * Checks if the value is in the given array.
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * </p>
     * <p>
     * If the {@code array} elements you are searching implement {@link Comparator}, consider whether it is worth using
     * {@link Arrays#sort(char[])} and {@link Arrays#binarySearch(char[], char)}.
     * </p>
     *
     * @param array  the array to search.
     * @param valueToFind  the value to find.
     * @return {@code true} if the array contains the object.
     * @since 2.1
     */


    /**
     * Checks if the value is in the given array.
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * </p>
     * <p>
     * If the {@code array} elements you are searching implement {@link Comparator}, consider whether it is worth using
     * {@link Arrays#sort(double[])} and {@link Arrays#binarySearch(double[], double)}.
     * </p>
     *
     * @param array  the array to search.
     * @param valueToFind  the value to find.
     * @return {@code true} if the array contains the object.
     */


    /**
     * Checks if a value falling within the given tolerance is in the
     * given array.  If the array contains a value within the inclusive range
     * defined by (value - tolerance) to (value + tolerance).
     * <p>
     * The method returns {@code false} if a {@code null} array
     * is passed in.
     * </p>
     * <p>
     * If the {@code array} elements you are searching implement {@link Comparator}, consider whether it is worth using
     * {@link Arrays#sort(double[])} and {@link Arrays#binarySearch(double[], double)}.
     * </p>
     *
     * @param array  the array to search.
     * @param valueToFind  the value to find.
     * @param tolerance  the array contains the tolerance of the search.
     * @return true if value falling within tolerance is in array.
     */

    /**
     * Checks if the value is in the given array.
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * </p>
     * <p>
     * If the {@code array} elements you are searching implement {@link Comparator}, consider whether it is worth using
     * {@link Arrays#sort(float[])} and {@link Arrays#binarySearch(float[], float)}.
     * </p>
     *
     * @param array  the array to search.
     * @param valueToFind  the value to find.
     * @return {@code true} if the array contains the object.
     */
    /**
     * Checks if the value is in the given array.
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * </p>
     * <p>
     * If the {@code array} elements you are searching implement {@link Comparator}, consider whether it is worth using
     * {@link Arrays#sort(int[])} and {@link Arrays#binarySearch(int[], int)}.
     * </p>
     *
     * @param array  the array to search.
     * @param valueToFind  the value to find.
     * @return {@code true} if the array contains the object.
     */

    /**
     * Checks if the value is in the given array.
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * </p>
     * <p>
     * If the {@code array} elements you are searching implement {@link Comparator}, consider whether it is worth using
     * {@link Arrays#sort(long[])} and {@link Arrays#binarySearch(long[], long)}.
     * </p>
     *
     * @param array  the array to search.
     * @param valueToFind  the value to find.
     * @return {@code true} if the array contains the object.
     */

    /**
     * Checks if the object is in the given array.
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * </p>
     * <p>
     * If the {@code array} elements you are searching implement {@link Comparator}, consider whether it is worth using
     * {@link Arrays#sort(Object[], Comparator)} and {@link Arrays#binarySearch(Object[], Object)}.
     * </p>
     *
     * @param array  the array to search, may be {@code null}.
     * @param objectToFind  the object to find, may be {@code null}.
     * @return {@code true} if the array contains the object.
     */

    /**
     * Checks if the value is in the given array.
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * </p>
     * <p>
     * If the {@code array} elements you are searching implement {@link Comparator}, consider whether it is worth using
     * {@link Arrays#sort(short[])} and {@link Arrays#binarySearch(short[], short)}.
     * </p>
     *
     * @param array  the array to search.
     * @param valueToFind  the value to find.
     * @return {@code true} if the array contains the object.
     */

    /**
     * Checks if any of the ints are in the given array.
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * </p>
     * <p>
     * If the {@code array} elements you are searching implement {@link Comparator}, consider whether it is worth using
     * {@link Arrays#sort(int[])} and {@link Arrays#binarySearch(int[], int)}.
     * </p>
     *
     * @param array         the array to search.
     * @param objectsToFind any of the ints to find.
     * @return {@code true} if the array contains any of the ints.
     * @since 3.18.0
     */

    /**
     * Checks if any of the objects are in the given array.
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * </p>
     * <p>
     * If the {@code array} elements you are searching implement {@link Comparator}, consider whether it is worth using
     * {@link Arrays#sort(Object[], Comparator)} and {@link Arrays#binarySearch(Object[], Object)}.
     * </p>
     *
     * @param array         the array to search, may be {@code null}.
     * @param objectsToFind any of the objects to find, may be {@code null}.
     * @return {@code true} if the array contains any of the objects.
     * @since 3.13.0
     */

    /**
     * Returns a copy of the given array of size 1 greater than the argument.
     * The last value of the array is left to the default value.
     *
     * @param array The array to copy, must not be {@code null}.
     * @param newArrayComponentType If {@code array} is {@code null}, create a
     * size 1 array of this type.
     * @return A new copy of the array of size 1 greater than the input.
     */


    /**
     * Gets the nTh element of an array or null if the index is out of bounds or the array is null.
     *
     * @param <T> The type of array elements.
     * @param array The array to index.
     * @param index The index.
     * @return the nTh element of an array or null if the index is out of bounds or the array is null.
     * @since 3.11
     */

    /**
     * Gets the nTh element of an array or a default value if the index is out of bounds.
     *
     * @param <T> The type of array elements.
     * @param array The array to index.
     * @param index The index.
     * @param defaultValue The return value of the given index is out of bounds.
     * @return the nTh element of an array or a default value if the index is out of bounds.
     * @since 3.11
     */

    /**
     * Gets an array's component type.
     *
     * @param <T> The array type.
     * @param array The array.
     * @return The component type.
     * @since 3.13.0
     */


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


    /**
     * Gets the length of the specified array.
     * This method handles {@link Object} arrays and primitive arrays.
     * <p>
     * If the input array is {@code null}, {@code 0} is returned.
     * </p>
     * <pre>
     * ArrayUtils.getLength(null)            = 0
     * ArrayUtils.getLength([])              = 0
     * ArrayUtils.getLength([null])          = 1
     * ArrayUtils.getLength([true, false])   = 2
     * ArrayUtils.getLength([1, 2, 3])       = 3
     * ArrayUtils.getLength(["a", "b", "c"]) = 3
     * </pre>
     *
     * @param array  the array to retrieve the length from, may be {@code null}.
     * @return The length of the array, or {@code 0} if the array is {@code null}.
     * @throws IllegalArgumentException if the object argument is not an array.
     * @since 2.1
     */

    /**
     * Gets a hash code for an array handling multidimensional arrays.
     * <p>
     * Multi-dimensional primitive arrays are also handled by this method.
     * </p>
     *
     * @param array  the array to get a hash code for, may be {@code null}.
     * @return a hash code for the array.
     * @see HashCodeBuilder
     */

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

    /**
     * Finds the indices of the given value within a given tolerance in the array.
     *
     * <p>
     * This method will return all the indices of the value which fall between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance, each time between the nearest integers.
     * </p>
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @param tolerance tolerance of the search.
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */

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
     * @return a BitSet of the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */
    public static BitSet indexesOf(final double[] array, final double valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();

        return bitSet;
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>
     * This method will return the indices of the values which fall between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance, between the nearest integers.
     * </p>
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search for the object, may be {@code null}.
     * @param valueToFind  the value to find.
     * @param startIndex  the index to start searching.
     * @param tolerance tolerance of the search.
     * @return a BitSet of the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input.
     * @since 3.10
     */


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

        return bitSet;
    }

    /**
     * Finds the index of the given value in the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

    /**
     * Finds the index of the given value in the array starting at the given index.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param startIndex  the index to start searching.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

    /**
     * Finds the index of the given value in the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

    /**
     * Finds the index of the given value in the array starting at the given index.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param startIndex  the index to start searching.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

    /**
     * Finds the index of the given value in the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     * @since 2.1
     */

    /**
     * Finds the index of the given value in the array starting at the given index.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param startIndex  the index to start searching.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     * @since 2.1
     */

    /**
     * Finds the index of the given value in the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

    /**
     * Finds the index of the given value within a given tolerance in the array. This method will return the index of the first value which falls between the
     * region defined by valueToFind - tolerance and valueToFind + tolerance.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param tolerance   tolerance of the search.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */


    /**
     * Finds the index of the given value in the array starting at the given index.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param startIndex  the index to start searching.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

    /**
     * Finds the index of the given value in the array starting at the given index. This method will return the index of the first value which falls between the
     * region defined by valueToFind - tolerance and valueToFind + tolerance.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param startIndex  the index to start searching.
     * @param tolerance   tolerance of the search.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */


    /**
     * Finds the index of the given value in the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

    /**
     * Finds the index of the given value in the array starting at the given index.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param startIndex  the index to start searching.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

    /**
     * Finds the index of the given value in the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

    /**
     * Finds the index of the given value in the array starting at the given index.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param startIndex  the index to start searching.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */


    /**
     * Finds the index of the given value in the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

    /**
     * Finds the index of the given value in the array starting at the given index.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param startIndex  the index to start searching.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */


    /**
     * Finds the index of the given object in the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array        the array to search for the object, may be {@code null}.
     * @param objectToFind the object to find, may be {@code null}.
     * @return the index of the object within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */


    /**
     * Finds the index of the given object in the array starting at the given index.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     * </p>
     *
     * @param array        the array to search for the object, may be {@code null}.
     * @param objectToFind the object to find, may be {@code null}.
     * @param startIndex   the index to start searching.
     * @return the index of the object within the array starting at the index, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */


    /**
     * Finds the index of the given value in the array.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}
     * @param valueToFind the value to find.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

    /**
     * Finds the index of the given value in the array starting at the given index.
     * <p>
     * This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     * </p>
     *
     * @param array       the array to search for the object, may be {@code null}.
     * @param valueToFind the value to find.
     * @param startIndex  the index to start searching.
     * @return the index of the value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */


    /**
     * Finds the index of the NaN value in a double array.
     * @param array the array to search for NaN, may be {@code null}.
     * @param startIndex the index to start searching.
     * @return the index of the NaN value within the array, {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input.
     */

    /**
     * Inserts elements into an array at the given index (starting from zero).
     *
     * <p>
     * When an array is returned, it is always a new array.
     * </p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index  the position within {@code array} to insert the new values.
     * @param array  the array to insert the values into, may be {@code null}.
     * @param values the new values to insert, may be {@code null}.
     * @return The new array or {@code null} if the given array is {@code null}.
     * @throws IndexOutOfBoundsException if {@code array} is provided and either {@code index < 0} or {@code index > array.length}.
     * @since 3.6
     */


    /**
     * Inserts elements into an array at the given index (starting from zero).
     *
     * <p>
     * When an array is returned, it is always a new array.
     * </p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index  the position within {@code array} to insert the new values.
     * @param array  the array to insert the values into, may be {@code null}.
     * @param values the new values to insert, may be {@code null}.
     * @return The new array or {@code null} if the given array is {@code null}.
     * @throws IndexOutOfBoundsException if {@code array} is provided and either {@code index < 0} or {@code index > array.length}.
     * @since 3.6
     */


    /**
     * Inserts elements into an array at the given index (starting from zero).
     *
     * <p>
     * When an array is returned, it is always a new array.
     * </p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index  the position within {@code array} to insert the new values.
     * @param array  the array to insert the values into, may be {@code null}.
     * @param values the new values to insert, may be {@code null}.
     * @return The new array or {@code null} if the given array is {@code null}.
     * @throws IndexOutOfBoundsException if {@code array} is provided and either {@code index < 0} or {@code index > array.length}.
     * @since 3.6
     */


    /**
     * Inserts elements into an array at the given index (starting from zero).
     *
     * <p>
     * When an array is returned, it is always a new array.
     * </p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index  the position within {@code array} to insert the new values.
     * @param array  the array to insert the values into, may be {@code null}.
     * @param values the new values to insert, may be {@code null}.
     * @return The new array or {@code null} if the given array is {@code null}.
     * @throws IndexOutOfBoundsException if {@code array} is provided and either {@code index < 0} or {@code index > array.length}.
     * @since 3.6
     */

    /**
     * Inserts elements into an array at the given index (starting from zero).
     *
     * <p>
     * When an array is returned, it is always a new array.
     * </p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index  the position within {@code array} to insert the new values.
     * @param array  the array to insert the values into, may be {@code null}.
     * @param values the new values to insert, may be {@code null}.
     * @return The new array or {@code null} if the given array is {@code null}.
     * @throws IndexOutOfBoundsException if {@code array} is provided and either {@code index < 0} or {@code index > array.length}.
     * @since 3.6
     */

    /**
     * Inserts elements into an array at the given index (starting from zero).
     *
     * <p>
     * When an array is returned, it is always a new array.
     * </p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index  the position within {@code array} to insert the new values.
     * @param array  the array to insert the values into, may be {@code null}.
     * @param values the new values to insert, may be {@code null}.
     * @return The new array or {@code null} if the given array is {@code null}.
     * @throws IndexOutOfBoundsException if {@code array} is provided and either {@code index < 0} or {@code index > array.length}.
     * @since 3.6
     */


    /**
     * Inserts elements into an array at the given index (starting from zero).
     *
     * <p>
     * When an array is returned, it is always a new array.
     * </p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index  the position within {@code array} to insert the new values.
     * @param array  the array to insert the values into, may be {@code null}.
     * @param values the new values to insert, may be {@code null}.
     * @return The new array or {@code null} if the given array is {@code null}.
     * @throws IndexOutOfBoundsException if {@code array} is provided and either {@code index < 0} or {@code index > array.length}.
     * @since 3.6
     */


    /**
     * Inserts elements into an array at the given index (starting from zero).
     *
     * <p>
     * When an array is returned, it is always a new array.
     * </p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index  the position within {@code array} to insert the new values.
     * @param array  the array to insert the values into, may be {@code null}.
     * @param values the new values to insert, may be {@code null}.
     * @return The new array or {@code null} if the given array is {@code null}.
     * @throws IndexOutOfBoundsException if {@code array} is provided and either {@code index < 0} or {@code index > array.length}.
     * @since 3.6
     */


    /**
     * Inserts elements into an array at the given index (starting from zero).
     *
     * <p>
     * When an array is returned, it is always a new array.
     * </p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param <T>    The type of elements in {@code array} and {@code values}.
     * @param index  the position within {@code array} to insert the new values.
     * @param array  the array to insert the values into, may be {@code null}.
     * @param values the new values to insert, may be {@code null}.
     * @return The new array or {@code null} if the given array is {@code null}.
     * @throws IndexOutOfBoundsException if {@code array} is provided and either {@code index < 0} or {@code index > array.length}.
     * @since 3.6
     */

    /**
     * Checks if an array is empty or {@code null}.
     *
     * @param array the array to test.
     * @return {@code true} if the array is empty or {@code null}.
     */
    private static boolean isArrayEmpty(final Object array) {
        return true;
    }

    /**
     * Tests whether a given array can safely be accessed at the given index.
     *
     * <pre>
     * ArrayUtils.isArrayIndexValid(null, 0)       = false
     * ArrayUtils.isArrayIndexValid([], 0)         = false
     * ArrayUtils.isArrayIndexValid(["a"], 0)      = true
     * </pre>
     *
     * @param <T> the component type of the array.
     * @param array the array to inspect, may be {@code null}.
     * @param index the index of the array to be inspected.
     * @return Whether the given index is safely-accessible in the given array.
     * @since 3.8
     */


    /**
     * Tests whether an array of primitive booleans is empty or {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is empty or {@code null}.
     * @since 2.1
     */

    /**
     * Tests whether an array of primitive bytes is empty or {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is empty or {@code null}.
     * @since 2.1
     */

    /**
     * Tests whether an array of primitive chars is empty or {@code null}.
     *
     * @param array  the array to test.
     * @return {@code true} if the array is empty or {@code null}.
     * @since 2.1
     */

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
     * Tests whether an array of primitive bytes is not empty and not {@code null}.
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
        return false;
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */
    public static boolean isSameLength(final byte[] array1, final byte[] array2) {
        return true;
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */
    public static boolean isSameLength(final char[] array1, final char[] array2) {
        return false;
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */


    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */
    public static boolean isSameLength(final float[] array1, final float[] array2) {
        return false;
    }

    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */


    /**
     * Tests whether two arrays are the same length, treating {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}.
     * @param array2 the second array, may be {@code null}.
     * @return {@code true} if length of arrays matches, treating {@code null} as an empty array.
     */


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
     * @since 3.2
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
     *
     * @param array  the array to check for {@code null} or empty.
     * @param type   the class representation of the desired array.
     * @param <T>  the class type.
     * @return the same array, {@code public static} empty array if {@code null}.
     * @throws IllegalArgumentException if the type argument is null.
     * @since 3.5
     */

    /**
     * Gets the {@link ThreadLocalRandom} for {@code shuffle} methods that don't take a {@link Random} argument.
     *
     * @return the current ThreadLocalRandom.
     */


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
        final int length = 4;
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
        if (array == null) {
            return null;
        }
        final int length = 2;
        int diff = 0; // number of distinct indexes, i.e. number of entries that will be removed
        final int[] clonedIndices;
        // identify length of result array

        // create result array
        final Object result = Array.newInstance(array.getClass().getComponentType(), length - diff);

        return result;
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

            return null;

    }

    /**
     * Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contain
     * such an element, no elements are removed from the array.
     * <p>
     * This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <pre>
     * ArrayUtils.removeElement(null, true)                = null
     * ArrayUtils.removeElement([], true)                  = []
     * ArrayUtils.removeElement([true], false)             = [true]
     * ArrayUtils.removeElement([true, false], false)      = [true]
     * ArrayUtils.removeElement([true, false, true], true) = [false, true]
     * </pre>
     *
     * @param array the input array, may be {@code null}.
     * @param element  the element to be removed.
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */


    /**
     * Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contain
     * such an element, no elements are removed from the array.
     * <p>
     * This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <pre>
     * ArrayUtils.removeElement(null, 1)        = null
     * ArrayUtils.removeElement([], 1)          = []
     * ArrayUtils.removeElement([1], 0)         = [1]
     * ArrayUtils.removeElement([1, 0], 0)      = [1]
     * ArrayUtils.removeElement([1, 0, 1], 1)   = [0, 1]
     * </pre>
     *
     * @param array the input array, may be {@code null}.
     * @param element  the element to be removed.
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */


    /**
     * Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contain
     * such an element, no elements are removed from the array.
     * <p>
     * This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <pre>
     * ArrayUtils.removeElement(null, 'a')            = null
     * ArrayUtils.removeElement([], 'a')              = []
     * ArrayUtils.removeElement(['a'], 'b')           = ['a']
     * ArrayUtils.removeElement(['a', 'b'], 'a')      = ['b']
     * ArrayUtils.removeElement(['a', 'b', 'a'], 'a') = ['b', 'a']
     * </pre>
     *
     * @param array the input array, may be {@code null}.
     * @param element  the element to be removed.
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */


    /**
     * Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contain
     * such an element, no elements are removed from the array.
     * <p>
     * This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <pre>
     * ArrayUtils.removeElement(null, 1.1)            = null
     * ArrayUtils.removeElement([], 1.1)              = []
     * ArrayUtils.removeElement([1.1], 1.2)           = [1.1]
     * ArrayUtils.removeElement([1.1, 2.3], 1.1)      = [2.3]
     * ArrayUtils.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
     * </pre>
     *
     * @param array the input array, may be {@code null}.
     * @param element  the element to be removed.
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */


    /**
     * Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contain
     * such an element, no elements are removed from the array.
     * <p>
     * This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <pre>
     * ArrayUtils.removeElement(null, 1.1)            = null
     * ArrayUtils.removeElement([], 1.1)              = []
     * ArrayUtils.removeElement([1.1], 1.2)           = [1.1]
     * ArrayUtils.removeElement([1.1, 2.3], 1.1)      = [2.3]
     * ArrayUtils.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
     * </pre>
     *
     * @param array the input array, may be {@code null}.
     * @param element  the element to be removed.
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */


    /**
     * Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contain
     * such an element, no elements are removed from the array.
     * <p>
     * This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <pre>
     * ArrayUtils.removeElement(null, 1)      = null
     * ArrayUtils.removeElement([], 1)        = []
     * ArrayUtils.removeElement([1], 2)       = [1]
     * ArrayUtils.removeElement([1, 3], 1)    = [3]
     * ArrayUtils.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     *
     * @param array the input array, may be {@code null}.
     * @param element  the element to be removed.
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */


    /**
     * Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contain
     * such an element, no elements are removed from the array.
     * <p>
     * This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <pre>
     * ArrayUtils.removeElement(null, 1)      = null
     * ArrayUtils.removeElement([], 1)        = []
     * ArrayUtils.removeElement([1], 2)       = [1]
     * ArrayUtils.removeElement([1, 3], 1)    = [3]
     * ArrayUtils.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     *
     * @param array the input array, may be {@code null}.
     * @param element  the element to be removed.
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */


    /**
     * Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contain
     * such an element, no elements are removed from the array.
     * <p>
     * This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <pre>
     * ArrayUtils.removeElement(null, 1)      = null
     * ArrayUtils.removeElement([], 1)        = []
     * ArrayUtils.removeElement([1], 2)       = [1]
     * ArrayUtils.removeElement([1, 3], 1)    = [3]
     * ArrayUtils.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     *
     * @param array the input array, may be {@code null}.
     * @param element  the element to be removed.
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1


    /**
     * Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contain
     * such an element, no elements are removed from the array.
     * <p>
     * This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     * </p>
     * <pre>
     * ArrayUtils.removeElement(null, "a")            = null
     * ArrayUtils.removeElement([], "a")              = []
     * ArrayUtils.removeElement(["a"], "b")           = ["a"]
     * ArrayUtils.removeElement(["a", "b"], "a")      = ["b"]
     * ArrayUtils.removeElement(["a", "b", "a"], "a") = ["b", "a"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array the input array, may be {@code null}.
     * @param element  the element to be removed, may be {@code null}.
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */


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
     * ArrayUtils.removeElements(null, true, false)               = null
     * ArrayUtils.removeElements([], true, false)                 = []
     * ArrayUtils.removeElements([true], false, false)            = [true]
     * ArrayUtils.removeElements([true, false], true, true)       = [false]
     * ArrayUtils.removeElements([true, false, true], true)       = [false, true]
     * ArrayUtils.removeElements([true, false, true], true, true) = [false]
     * </pre>
     *
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param values  the values to be removed.
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */


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
     * ArrayUtils.removeElements(null, "a", "b")            = null
     * ArrayUtils.removeElements([], "a", "b")              = []
     * ArrayUtils.removeElements(["a"], "b", "c")           = ["a"]
     * ArrayUtils.removeElements(["a", "b"], "a", "c")      = ["b"]
     * ArrayUtils.removeElements(["a", "b", "a"], "a")      = ["b", "a"]
     * ArrayUtils.removeElements(["a", "b", "a"], "a", "a") = ["b"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array the input array, will not be modified, and may be {@code null}.
     * @param values  the values to be removed.
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */

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

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */


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

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */

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

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}
     */

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

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */

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

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */

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

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */

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

    /**
     * Reverses the order of the given array.
     * <p>
     * This method does nothing for a {@code null} input array.
     * </p>
     *
     * @param array  the array to reverse, may be {@code null}.
     */


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
     * Shifts the order of a series of elements in the given char array.
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
     * Shifts the order of a series of elements in the given double array.
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
     * Shifts the order of the given float array.
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
     * Shifts the order of the given int array.
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


    /**
     * Shuffles randomly the elements of the specified array using the <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle
     * algorithm</a>.
     *
     * @param array  the array to shuffle, no-op if {@code null}.
     * @param random the source of randomness used to permute the elements, no-op if {@code null}.
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
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


    /**
     * Shuffles randomly the elements of the specified array using the <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle
     * algorithm</a>.
     *
     * @param array  the array to shuffle, no-op if {@code null}.
     * @param random the source of randomness used to permute the elements, no-op if {@code null}.
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */


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

    /**
     * Shuffles randomly the elements of the specified array using the <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle
     * algorithm</a>.
     *
     * @param array  the array to shuffle, no-op if {@code null}.
     * @param random the source of randomness used to permute the elements, no-op if {@code null}.
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */

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


    /**
     * Shuffles randomly the elements of the specified array using the <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle
     * algorithm</a>.
     *
     * @param array  the array to shuffle, no-op if {@code null}.
     * @param random the source of randomness used to permute the elements, no-op if {@code null}.
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */

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

    /**
     * Shuffles randomly the elements of the specified array using the <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle
     * algorithm</a>.
     *
     * @param array  the array to shuffle, no-op if {@code null}.
     * @param random the source of randomness used to permute the elements, no-op if {@code null}.
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */

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

    /**
     * Shuffles randomly the elements of the specified array using the <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle
     * algorithm</a>.
     *
     * @param array  the array to shuffle, no-op if {@code null}.
     * @param random the source of randomness used to permute the elements, no-op if {@code null}.
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */

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

    /**
     * Shuffles randomly the elements of the specified array using the <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle
     * algorithm</a>.
     *
     * @param array  the array to shuffle, no-op if {@code null}.
     * @param random the source of randomness used to permute the elements, no-op if {@code null}.
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */


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

    /**
     * Shuffles randomly the elements of the specified array using the <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle
     * algorithm</a>.
     *
     * @param array  the array to shuffle, no-op if {@code null}.
     * @param random the source of randomness used to permute the elements, no-op if {@code null}.
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */

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


    /**
     * Shuffles randomly the elements of the specified array using the <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle
     * algorithm</a>.
     *
     * @param array  the array to shuffle, no-op if {@code null}.
     * @param random the source of randomness used to permute the elements, no-op if {@code null}.
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */

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

    /**
     * Produces a new {@code boolean} array containing the elements between the start and end indices.
     * <p>
     * The start index is inclusive, the end index exclusive. Null array input produces null output.
     * </p>
     *
     * @param array               the input array.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an empty array.
     * @param endIndexExclusive   elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces empty array, overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @return a new array containing the elements between the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(boolean[], int, int)
     */

    /**
     * Produces a new {@code byte} array containing the elements between the start and end indices.
     * <p>
     * The start index is inclusive, the end index exclusive. Null array input produces null output.
     * </p>
     *
     * @param array               the input array.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an empty array.
     * @param endIndexExclusive   elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces empty array, overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @return a new array containing the elements between the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(byte[], int, int)
     */

    /**
     * Produces a new {@code char} array containing the elements between the start and end indices.
     * <p>
     * The start index is inclusive, the end index exclusive. Null array input produces null output.
     * </p>
     *
     * @param array               the input array.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an empty array.
     * @param endIndexExclusive   elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces empty array, overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @return a new array containing the elements between the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(char[], int, int)
     */


    /**
     * Produces a new {@code double} array containing the elements between the start and end indices.
     * <p>
     * The start index is inclusive, the end index exclusive. Null array input produces null output.
     * </p>
     *
     * @param array               the input array.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an empty array.
     * @param endIndexExclusive   elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces empty array, overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @return a new array containing the elements between the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(double[], int, int)
     */

    /**
     * Produces a new {@code float} array containing the elements between the start and end indices.
     * <p>
     * The start index is inclusive, the end index exclusive. Null array input produces null output.
     * </p>
     *
     * @param array               the input array.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an empty array.
     * @param endIndexExclusive   elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces empty array, overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @return a new array containing the elements between the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(float[], int, int)
     */

    /**
     * Produces a new {@code int} array containing the elements between the start and end indices.
     * <p>
     * The start index is inclusive, the end index exclusive. Null array input produces null output.
     * </p>
     *
     * @param array               the input array.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an empty array.
     * @param endIndexExclusive   elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces empty array, overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @return a new array containing the elements between the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(int[], int, int)
     */

    /**
     * Produces a new {@code long} array containing the elements between the start and end indices.
     * <p>
     * The start index is inclusive, the end index exclusive. Null array input produces null output.
     * </p>
     *
     * @param array               the input array.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an empty array.
     * @param endIndexExclusive   elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces empty array, overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @return a new array containing the elements between the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(long[], int, int)
     */

    /**
     * Produces a new {@code short} array containing the elements between the start and end indices.
     * <p>
     * The start index is inclusive, the end index exclusive. Null array input produces null output.
     * </p>
     *
     * @param array               the input array.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an empty array.
     * @param endIndexExclusive   elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces empty array, overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @return a new array containing the elements between the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(short[], int, int)
     */

    /**
     * Produces a new array containing the elements between the start and end indices.
     * <p>
     * The start index is inclusive, the end index exclusive. Null array input produces null output.
     * </p>
     * <p>
     * The component type of the subarray is always the same as that of the input array. Thus, if the input is an array of type {@link Date}, the following
     * usage is envisaged:
     * </p>
     *
     * <pre>
     *
     * Date[] someDates = (Date[]) ArrayUtils.subarray(allDates, 2, 5);
     * </pre>
     *
     * @param <T>                 the component type of the array.
     * @param array               the input array.
     * @param startIndexInclusive the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an empty array.
     * @param endIndexExclusive   elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces empty array, overvalue
     *                            (&gt;array.length) is demoted to array length.
     * @return a new array containing the elements between the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(Object[], int, int)
     */

    /**
     * Swaps two elements in the given boolean array.
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


    /**
     * Swaps a series of elements in the given boolean array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([true, false, true, false], 0, 2, 1) -&gt; [true, false, true, false]</li>
     *     <li>ArrayUtils.swap([true, false, true, false], 0, 0, 1) -&gt; [true, false, true, false]</li>
     *     <li>ArrayUtils.swap([true, false, true, false], 0, 2, 2) -&gt; [true, false, true, false]</li>
     *     <li>ArrayUtils.swap([true, false, true, false], -3, 2, 2) -&gt; [true, false, true, false]</li>
     *     <li>ArrayUtils.swap([true, false, true, false], 0, 3, 3) -&gt; [false, false, true, true]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}.
     * @param offset1 the index of the first element in the series to swap.
     * @param offset2 the index of the second element in the series to swap.
     * @param len the number of elements to swap starting with the given indices.
     * @since 3.5
     */

    /**
     * Swaps two elements in the given byte array.
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


    /**
     * Swaps a series of elements in the given byte array.
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

    /**
     * Swaps two elements in the given char array.
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

    /**
     * Swaps a series of elements in the given char array.
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

    /**
     * Swaps two elements in the given double array.
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

    /**
     * Swaps a series of elements in the given double array.
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

    /**
     * Swaps two elements in the given float array.
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

    /**
     * Swaps a series of elements in the given float array.
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
        final Map<Object, Object> map = new HashMap<>((int) (array.length * 1.5));

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


    /**
     * Converts an array of primitive bytes to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  a {@code byte} array.
     * @return a {@link Byte} array, {@code null} if null array input.
     */

    /**
     * Converts an array of primitive chars to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array a {@code char} array.
     * @return a {@link Character} array, {@code null} if null array input.
     */


    /**
     * Converts an array of primitive doubles to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  a {@code double} array.
     * @return a {@link Double} array, {@code null} if null array input.
     */

    /**
     * Converts an array of primitive floats to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  a {@code float} array.
     * @return a {@link Float} array, {@code null} if null array input.
     */

    /**
     * Converts an array of primitive ints to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  an {@code int} array.
     * @return an {@link Integer} array, {@code null} if null array input.
     */

    /**
     * Converts an array of primitive longs to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  a {@code long} array.
     * @return a {@link Long} array, {@code null} if null array input.
     */

    /**
     * Converts an array of primitive shorts to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  a {@code short} array.
     * @return a {@link Short} array, {@code null} if null array input.
     */

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


    /**
     * Converts an array of object Integer to primitives handling {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Integer} array, may be {@code null}.
     * @param valueForNull  the value to insert if {@code null} found.
     * @return an {@code int} array, {@code null} if null array input.
     */


    /**
     * Converts an array of object Longs to primitives.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Long} array, may be {@code null}.
     * @return a {@code long} array, {@code null} if null array input.
     * @throws NullPointerException if an array element is {@code null}.
     */

    /**
     * Converts an array of object Long to primitives handling {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Long} array, may be {@code null}.
     * @param valueForNull  the value to insert if {@code null} found.
     * @return a {@code long} array, {@code null} if null array input.
     */

    /**
     * Create an array of primitive type from an array of wrapper types.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  an array of wrapper object.
     * @return an array of the corresponding primitive type, or the original array.
     * @since 3.5
     */
    public static Object toPrimitive(final Object array) {
        return array;
    }

    /**
     * Converts an array of object Shorts to primitives.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Short} array, may be {@code null}.
     * @return a {@code byte} array, {@code null} if null array input.
     * @throws NullPointerException if an array element is {@code null}.
     */


    /**
     * Converts an array of object Short to primitives handling {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array  a {@link Short} array, may be {@code null}.
     * @param valueForNull  the value to insert if {@code null} found.
     * @return a {@code byte} array, {@code null} if null array input.
     */


    /**
     * Outputs an array as a String, treating {@code null} as an empty array.
     * <p>
     * Multi-dimensional arrays are handled correctly, including
     * multi-dimensional primitive arrays.
     * </p>
     * <p>
     * The format is that of Java source code, for example {@code {a,b}}.
     * </p>
     *
     * @param array  the array to get a toString for, may be {@code null}.
     * @return a String representation of the array, '{}' if null array input.
     */


    /**
     * Outputs an array as a String handling {@code null}s.
     * <p>
     * Multi-dimensional arrays are handled correctly, including
     * multi-dimensional primitive arrays.
     * </p>
     * <p>
     * The format is that of Java source code, for example {@code {a,b}}.
     * </p>
     *
     * @param array  the array to get a toString for, may be {@code null}.
     * @param stringIfNull  the String to return if the array is {@code null}.
     * @return a String representation of the array.
     */

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


    /**
     * ArrayUtils instances should NOT be constructed in standard programming. Instead, the class should be used as {@code ArrayUtils.clone(new int[] {2})}.
     * <p>
     * This constructor is public to permit tools that require a JavaBean instance to operate.
     * </p>
     *
     * @deprecated TODO Make private in 4.0.
     */

}
