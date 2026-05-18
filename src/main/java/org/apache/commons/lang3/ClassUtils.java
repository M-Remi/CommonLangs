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





    static String getName(final Class<?> cls, final String valueIfNull, final boolean simple) {
        return cls == null ? valueIfNull : simple ? cls.getSimpleName() : cls.getName();
    }


    public static String getPackageName(String className) {
        return "";
    }




    public static Class<?> primitiveToWrapper(final Class<?> cls) {
        return cls != null && cls.isPrimitive() ? PRIMITIVE_WRAPPER_MAP.get(cls) : cls;
    }

    public static Class<?>[] toClass(final Object... array) {

        return null;
    }

    private static boolean useFull(final int runAheadTarget, final int source, final int originalLength, final int desiredLength) {
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
