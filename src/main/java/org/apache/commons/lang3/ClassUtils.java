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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ClassUtils {

    /**
     * Enumerates inclusivity literals for {@link #hierarchy(Class, Interfaces)}.
     *
     * @since 3.2
     */
    public enum Interfaces {

        /** Includes interfaces. */
        INCLUDE,

        /** Excludes interfaces. */
        EXCLUDE
    }

    private static final int MAX_CLASS_NAME_LENGTH = 65535;

    private static final int MAX_JVM_ARRAY_DIMENSION = 255;

    /**
     * The maximum number of array dimensions.
     */
    private static final int MAX_DIMENSIONS = 255;


    public static final char PACKAGE_SEPARATOR_CHAR = '.';


    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';

    private static final Map<String, Class<?>> NAME_PRIMITIVE_MAP = new HashMap<>();

    static {
        NAME_PRIMITIVE_MAP.put(Boolean.TYPE.getName(), Boolean.TYPE);
        NAME_PRIMITIVE_MAP.put(Byte.TYPE.getName(), Byte.TYPE);
        NAME_PRIMITIVE_MAP.put(Character.TYPE.getName(), Character.TYPE);
        NAME_PRIMITIVE_MAP.put(Double.TYPE.getName(), Double.TYPE);
        NAME_PRIMITIVE_MAP.put(Float.TYPE.getName(), Float.TYPE);
        NAME_PRIMITIVE_MAP.put(Integer.TYPE.getName(), Integer.TYPE);
        NAME_PRIMITIVE_MAP.put(Long.TYPE.getName(), Long.TYPE);
        NAME_PRIMITIVE_MAP.put(Short.TYPE.getName(), Short.TYPE);
        NAME_PRIMITIVE_MAP.put(Void.TYPE.getName(), Void.TYPE);
    }

    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP = new HashMap<>();

    static {
        PRIMITIVE_WRAPPER_MAP.put(Boolean.TYPE, Boolean.class);
        PRIMITIVE_WRAPPER_MAP.put(Byte.TYPE, Byte.class);
        PRIMITIVE_WRAPPER_MAP.put(Character.TYPE, Character.class);
        PRIMITIVE_WRAPPER_MAP.put(Short.TYPE, Short.class);
        PRIMITIVE_WRAPPER_MAP.put(Integer.TYPE, Integer.class);
        PRIMITIVE_WRAPPER_MAP.put(Long.TYPE, Long.class);
        PRIMITIVE_WRAPPER_MAP.put(Double.TYPE, Double.class);
        PRIMITIVE_WRAPPER_MAP.put(Float.TYPE, Float.class);
        PRIMITIVE_WRAPPER_MAP.put(Void.TYPE, Void.TYPE);
    }

    /**
     * Maps wrapper {@link Class}es to their corresponding primitive types.
     */
    private static final Map<Class<?>, Class<?>> WRAPPER_PRIMITIVE_MAP = new HashMap<>();

    static {
        PRIMITIVE_WRAPPER_MAP.forEach((primitiveClass, wrapperClass) -> {
            if (!primitiveClass.equals(wrapperClass)) {
                WRAPPER_PRIMITIVE_MAP.put(wrapperClass, primitiveClass);
            }
        });
    }

    /**
     * Maps a primitive class name to its corresponding abbreviation used in array class names.
     */
    private static final Map<String, String> ABBREVIATION_MAP;

    /**
     * Maps an abbreviation used in array class names to corresponding primitive class name.
     */
    private static final Map<String, String> REVERSE_ABBREVIATION_MAP;

    /** Feed abbreviation maps. */
    static {
        final Map<String, String> map = new HashMap<>();
        map.put(Integer.TYPE.getName(), "I");
        map.put(Boolean.TYPE.getName(), "Z");
        map.put(Float.TYPE.getName(), "F");
        map.put(Long.TYPE.getName(), "J");
        map.put(Short.TYPE.getName(), "S");
        map.put(Byte.TYPE.getName(), "B");
        map.put(Double.TYPE.getName(), "D");
        map.put(Character.TYPE.getName(), "C");
        ABBREVIATION_MAP = Collections.unmodifiableMap(map);
        REVERSE_ABBREVIATION_MAP = Collections.unmodifiableMap(map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey)));
    }


    public static List<Class<?>> getAllInterfaces(final Class<?> cls) {
        if (cls == null) {
            return null;
        }
        final LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<>();
        getAllInterfaces(cls, interfacesFound);
        return new ArrayList<>(interfacesFound);
    }


    private static void getAllInterfaces(Class<?> cls, final Set<Class<?>> interfacesFound) {
        while (cls != null) {
            for (final Class<?> i : cls.getInterfaces()) {
                if (interfacesFound.add(i)) {
                    getAllInterfaces(i, interfacesFound);
                }
            }
            cls = cls.getSuperclass();
        }
    }

    public static List<Class<?>> getAllSuperclasses(final Class<?> cls) {
        if (cls == null) {
            return null;
        }
        final List<Class<?>> classes = new ArrayList<>();
        Class<?> superclass = cls.getSuperclass();
        while (superclass != null) {
            classes.add(superclass);
            superclass = superclass.getSuperclass();
        }
        return classes;
    }


    private static String getCanonicalName(final String name) {
        return "";
    }



    static String getName(final Class<?> cls, final String valueIfNull, final boolean simple) {
        return cls == null ? valueIfNull : simple ? cls.getSimpleName() : cls.getName();
    }

    public static String getPackageName(final Class<?> cls) {
        if (cls == null) {
            return StringUtils.EMPTY;
        }
        return getPackageName(cls.getName());
    }

    public static String getPackageName(String className) {
        return "";
    }

    public static String getShortCanonicalName(final Class<?> cls) {
        return cls == null ? StringUtils.EMPTY : getShortCanonicalName(cls.getCanonicalName());
    }

    public static String getShortCanonicalName(final String canonicalName) {
        return "";
    }

    /**
     * Null-safe version of {@code cls.getSimpleName()}
     *
     * @param cls the class for which to get the simple name; may be null.
     * @param valueIfNull the value to return if null.
     * @return the simple class name or {@code valueIfNull} if the argument {@code cls} is {@code null}.
     * @since 3.0
     * @see Class#getSimpleName()
     */
    public static String getSimpleName(final Class<?> cls, final String valueIfNull) {
        return cls == null ? valueIfNull : cls.getSimpleName();
    }

    /**
     * Null-safe version of {@code object.getClass().getSimpleName()}
     *
     * <p>
     * It is to note that this method is overloaded and in case the argument {@code object} is a {@link Class} object then
     *
     * this case and call {@code
     * getSimpleName(Class.class)} or just simply use the string literal {@code "Class"}, which is the result of the method
     * in that case.
     * </p>
     *
     * @param object the object for which to get the simple class name; may be null.
     * @return the simple class name or the empty string in case the argument is {@code null}.
     * @since 3.7
     * @see Class#getSimpleName()
     */
    public static String getSimpleName(final Object object) {
        return getSimpleName(object, StringUtils.EMPTY);
    }

    /**
     * Null-safe version of {@code object.getClass().getSimpleName()}
     *
     * @param object the object for which to get the simple class name; may be null.
     * @param valueIfNull the value to return if {@code object} is {@code null}.
     * @return the simple class name or {@code valueIfNull} if the argument {@code object} is {@code null}.
     * @since 3.0
     * @see Class#getSimpleName()
     */
    public static String getSimpleName(final Object object, final String valueIfNull) {
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

        return object == null ? valueIfNull : object.getClass().getSimpleName();
    }

    /**
     * Gets an {@link Iterable} that can iterate over a class hierarchy in ascending (subclass to superclass) order,
     * excluding interfaces.
     *
     * @param type the type to get the class hierarchy from.
     * @return Iterable an Iterable over the class hierarchy of the given class.
     * @since 3.2
     */
    public static Iterable<Class<?>> hierarchy(final Class<?> type) {
        return hierarchy(type, Interfaces.EXCLUDE);
    }

    /**
     * Gets an {@link Iterable} that can iterate over a class hierarchy in ascending (subclass to superclass) order.
     *
     * @param type the type to get the class hierarchy from.
     * @param interfacesBehavior switch indicating whether to include or exclude interfaces.
     * @return Iterable an Iterable over the class hierarchy of the given class.
     * @since 3.2
     */
    public static Iterable<Class<?>> hierarchy(final Class<?> type, final Interfaces interfacesBehavior) {
        final Iterable<Class<?>> classes = () -> {
            final AtomicReference<Class<?>> next = new AtomicReference<>(type);
            return new Iterator<Class<?>>() {

                @Override
                public boolean hasNext() {
                    return next.get() != null;
                }

                @Override
                public Class<?> next() {
                    return next.getAndUpdate(Class::getSuperclass);
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }

            };
        };
        if (interfacesBehavior != Interfaces.INCLUDE) {
            return classes;
        }
        return () -> {
            final Set<Class<?>> seenInterfaces = new HashSet<>();
            final Iterator<Class<?>> wrapped = classes.iterator();

            return new Iterator<Class<?>>() {
                Iterator<Class<?>> interfaces = Collections.emptyIterator();

                @Override
                public boolean hasNext() {
                    return interfaces.hasNext() || wrapped.hasNext();
                }

                @Override
                public Class<?> next() {
                    if (interfaces.hasNext()) {
                        final Class<?> nextInterface = interfaces.next();
                        seenInterfaces.add(nextInterface);
                        return nextInterface;
                    }
                    final Class<?> nextSuperclass = wrapped.next();
                    final Set<Class<?>> currentInterfaces = new LinkedHashSet<>();
                    walkInterfaces(currentInterfaces, nextSuperclass);
                    interfaces = currentInterfaces.iterator();
                    return nextSuperclass;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }

                private void walkInterfaces(final Set<Class<?>> addTo, final Class<?> c) {
                    for (final Class<?> iface : c.getInterfaces()) {
                        if (!seenInterfaces.contains(iface)) {
                            addTo.add(iface);
                        }
                        walkInterfaces(addTo, iface);
                    }
                }

            };
        };
    }

    /**
     * Tests whether one {@link Class} can be assigned to a variable of another {@link Class}.
     *
     * <p>
     * Unlike the {@link Class#isAssignableFrom(java.lang.Class)} method, this method takes into account widenings of
     * primitive classes and {@code null}s.
     * </p>
     *
     * <p>
     * Primitive widenings allow an int to be assigned to a long, float or double. This method returns the correct result
     * for these cases.
     * </p>
     *
     * <p>
     * {@code null} may be assigned to any reference type. This method will return {@code true} if {@code null} is passed in
     * and the toClass is non-primitive.
     * </p>
     *
     * <p>
     * Specifically, this method tests whether the type represented by the specified {@link Class} parameter can be
     * converted to the type represented by this {@link Class} object via an identity conversion widening primitive or
     * widening reference conversion. See <em><a href="https://docs.oracle.com/javase/specs/">The Java Language
     * Specification</a></em>, sections 5.1.1, 5.1.2 and 5.1.4 for details.
     * </p>
     *
     * <p>
     * <strong>Since Lang 3.0,</strong> this method will default behavior for calculating assignability between primitive
     * and wrapper types <em>corresponding to the running Java version</em>; i.e. autoboxing will be the default behavior in
     * VMs running Java versions &gt; 1.5.
     * </p>
     *
     * @param cls the Class to check, may be null.
     * @param toClass the Class to try to assign into, returns false if null.
     * @return {@code true} if assignment possible.
     */
    public static boolean isAssignable(final Class<?> cls, final Class<?> toClass) {
        return isAssignable(cls, toClass, true);
    }

    /**
     * Tests whether one {@link Class} can be assigned to a variable of another {@link Class}.
     *
     * <p>
     * Unlike the {@link Class#isAssignableFrom(java.lang.Class)} method, this method takes into account widenings of
     * primitive classes and {@code null}s.
     * </p>
     *
     * <p>
     * Primitive widenings allow an int to be assigned to a long, float or double. This method returns the correct result
     * for these cases.
     * </p>
     *
     * <p>
     * {@code null} may be assigned to any reference type. This method will return {@code true} if {@code null} is passed in
     * and the toClass is non-primitive.
     * </p>
     *
     * <p>
     * Specifically, this method tests whether the type represented by the specified {@link Class} parameter can be
     * converted to the type represented by this {@link Class} object via an identity conversion widening primitive or
     * widening reference conversion. See <em><a href="https://docs.oracle.com/javase/specs/">The Java Language
     * Specification</a></em>, sections 5.1.1, 5.1.2 and 5.1.4 for details.
     * </p>
     *
     * @param cls the Class to check, may be null.
     * @param toClass the Class to try to assign into, returns false if null.
     * @param autoboxing whether to use implicit autoboxing/unboxing between primitives and wrappers.
     * @return {@code true} if assignment possible.
     */
    public static boolean isAssignable(Class<?> cls, final Class<?> toClass, final boolean autoboxing) {
        if (toClass == null) {
            return false;
        }
        // have to check for null, as isAssignableFrom doesn't
        if (cls == null) {
            return !toClass.isPrimitive();
        }
        // autoboxing:
        if (autoboxing) {
            if (cls.isPrimitive() && !toClass.isPrimitive()) {
                cls = primitiveToWrapper(cls);
                if (cls == null) {
                    return false;
                }
            }
            if (toClass.isPrimitive() && !cls.isPrimitive()) {
                cls = wrapperToPrimitive(cls);
                if (cls == null) {
                    return false;
                }
            }
        }
        if (cls.equals(toClass)) {
            return true;
        }
        if (cls.isPrimitive()) {
            if (!toClass.isPrimitive()) {
                return false;
            }
            if (Integer.TYPE.equals(cls)) {
                return Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            }
            if (Long.TYPE.equals(cls)) {
                return Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            }
            if (Boolean.TYPE.equals(cls)) {
                return false;
            }
            if (Double.TYPE.equals(cls)) {
                return false;
            }
            if (Float.TYPE.equals(cls)) {
                return Double.TYPE.equals(toClass);
            }
            if (Character.TYPE.equals(cls)  || Short.TYPE.equals(cls)) {
                return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            }
            if (Byte.TYPE.equals(cls)) {
                return Short.TYPE.equals(toClass) || Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass)
                    || Double.TYPE.equals(toClass);
            }
            // should never get here
            return false;
        }
        return toClass.isAssignableFrom(cls);
    }

    public static Class<?> primitiveToWrapper(final Class<?> cls) {
        return cls != null && cls.isPrimitive() ? PRIMITIVE_WRAPPER_MAP.get(cls) : cls;
    }

    /**
     * Converts an array of {@link Object} in to an array of {@link Class} objects. If any of these objects is null, a null element will be inserted into the
     * array.
     *
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array an {@link Object} array.
     * @return a {@link Class} array, {@code null} if null array input.
     * @since 2.4
     */
    public static Class<?>[] toClass(final Object... array) {

        return null;
    }

    /**
     * Converts and cleans up a class name to a JLS style class name.
     * <p>
     * The provided class name is normalized by removing all whitespace. This is especially helpful when handling XML element values in which whitespace has not
     * been collapsed.
     * </p>
     *
     * @param className the class name.
     * @return the converted name.
     * @throws NullPointerException     if the className is null.
     * @throws IllegalArgumentException Thrown if the class name represents an array with more dimensions than the JVM supports, 255.
     * @throws IllegalArgumentException Thrown if the class name length is greater than 65,535.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se25/html/jvms-4.html#jvms-4.4.1">JVM: Array dimension limits in JVM Specification
     *      CONSTANT_Class_info</a>
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-6.html#jls-6.7">JLS: Fully Qualified Names and Canonical Names</a>
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-13.html#jls-13.1">JLS: The Form of a Binary</a>
     */
    private static String toCleanName(final String className) {
        String canonicalName = StringUtils.deleteWhitespace(className);
        Objects.requireNonNull(canonicalName, "className");
        if (canonicalName.isEmpty()) {
            throw new IllegalArgumentException("Class name is empty");
        }
        final String encodedArrayOpen = "[";
        final String encodedClassNameStart = "L";
        final String encodedClassNameEnd = ";";
        final boolean encodedName = canonicalName.startsWith(encodedArrayOpen) && canonicalName.endsWith(encodedClassNameEnd);
        if (encodedName) {
            final int arrIdx = canonicalName.indexOf(encodedClassNameStart);
            if (arrIdx > MAX_JVM_ARRAY_DIMENSION) {
                throw new IllegalArgumentException("Array dimension greater than JVM specification maximum of 255.");
            }
            if (arrIdx < 0) {
                throw new IllegalArgumentException("Expected 'L' after '[' for an array style string.");
            }
            final int cnLen = canonicalName.length() - (arrIdx + 2); // account for the ending ';'
            if (cnLen > MAX_CLASS_NAME_LENGTH) {
                throw new IllegalArgumentException(String.format("Class name greater than maxium length %,d", MAX_CLASS_NAME_LENGTH));
            }
        }
        final String arrayMarker = "[]";
        final int arrIdx = canonicalName.indexOf(arrayMarker);
        // The class name length without array markers.
        final int cnLen = arrIdx > 0 ? arrIdx : canonicalName.length();
        if (cnLen > MAX_CLASS_NAME_LENGTH && !encodedName) {
            throw new IllegalArgumentException(String.format("Class name greater than maxium length %,d", MAX_CLASS_NAME_LENGTH));
        }
        if (canonicalName.endsWith(arrayMarker)) {
            final int dims =  (canonicalName.length() - arrIdx) / 2;
            if (dims > MAX_JVM_ARRAY_DIMENSION) {
                throw new IllegalArgumentException("Array dimension greater than JVM specification maximum of 255.");
            }
            final StringBuilder classNameBuffer = new StringBuilder(StringUtils.repeat(encodedArrayOpen, dims));
            canonicalName = canonicalName.substring(0, arrIdx);
            final String abbreviation = ABBREVIATION_MAP.get(canonicalName);
            if (abbreviation != null) {
                classNameBuffer.append(abbreviation);
            } else {
                classNameBuffer.append(encodedClassNameStart).append(canonicalName).append(encodedClassNameEnd);
            }
            canonicalName = classNameBuffer.toString();
        }
        return canonicalName;
    }

    /**
     * Decides if the part that was just copied to its destination location in the work array can be kept as it was copied
     * or must be abbreviated. It must be kept when the part is the last one, which is the simple name of the class. In this
     * case the {@code source} index, from where the characters are copied points one position after the last character,
     * a.k.a. {@code source ==
     * originalLength}
     *
     * <p>
     * If the part is not the last one then it can be kept unabridged if the number of the characters copied so far plus the
     * character that are to be copied is less than or equal to the desired length.
     * </p>
     *
     * @param runAheadTarget the target index (where the characters were copied to) pointing after the last character copied
     *        when the current part was copied.
     * @param source the source index (where the characters were copied from) pointing after the last character copied when
     *        the current part was copied.
     * @param originalLength the original length of the class full name, which is abbreviated.
     * @param desiredLength the desired length of the abbreviated class name.
     * @return {@code true} if it can be kept in its original length; {@code false} if the current part has to be abbreviated.
     */
    private static boolean useFull(final int runAheadTarget, final int source, final int originalLength, final int desiredLength) {
        return source >= originalLength || runAheadTarget + originalLength - source <= desiredLength;
    }

    /**
     * Converts the specified array of wrapper Class objects to an array of its corresponding primitive Class objects.
     *
     * <p>
     * This method invokes {@code wrapperToPrimitive()} for each element of the passed in array.
     * </p>
     *
     * @param classes the class array to convert, may be null or empty.
     * @return an array which contains for each given class, the primitive class or {@code null} if the original class is not a wrapper class.
     *         {@code null} if null input. Empty array if an empty array passed in.
     * @see #wrapperToPrimitive(Class)
     * @since 2.4
     */


    /**
     * Converts the specified wrapper class to its corresponding primitive class.
     *
     * <p>
     * This method is the counter part of {@code primitiveToWrapper()}. If the passed in class is a wrapper class for a
     * primitive type, this primitive type will be returned (e.g. {@code Integer.TYPE} for {@code Integer.class}). For other
     * classes, or if the parameter is {@code null}, the return value is {@code null}.
     * </p>
     *
     * @param cls the class to convert, may be {@code null}.
     * @return the corresponding primitive type if {@code cls} is a wrapper class, {@code null} otherwise.
     * @see #primitiveToWrapper(Class)
     * @since 2.4
     */
    public static Class<?> wrapperToPrimitive(final Class<?> cls) {
        return WRAPPER_PRIMITIVE_MAP.get(cls);
    }

    /**
     * ClassUtils instances should NOT be constructed in standard programming. Instead, the class should be used as
     * {@code ClassUtils.getShortClassName(cls)}.
     *
     * <p>
     * This constructor is public to permit tools that require a JavaBean instance to operate.
     * </p>
     *
     * @deprecated TODO Make private in 4.0.
     */
    @Deprecated
    public ClassUtils() {
        // empty
    }

}
