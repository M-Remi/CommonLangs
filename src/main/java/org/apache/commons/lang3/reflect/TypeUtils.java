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
package org.apache.commons.lang3.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.Builder;

public class TypeUtils {

    /**
     * GenericArrayType implementation class.
     */
    private static final class GenericArrayTypeImpl implements GenericArrayType {
        private final Type componentType;

        /**
         * Constructs a new instance.
         *
         * @param componentType of this array type.
         */
        private GenericArrayTypeImpl(final Type componentType) {
            this.componentType = componentType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {
            return obj == this || obj instanceof GenericArrayType && TypeUtils.equals(this, (GenericArrayType) obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type getGenericComponentType() {
            return componentType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int result = 67 << 4;
            result |= componentType.hashCode();
            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return TypeUtils.toString(this);
        }
    }

    public static class WildcardTypeBuilder implements Builder<WildcardType> {
        private Type[] upperBounds;

        private Type[] lowerBounds;

        /**
         * Constructor.
         */
        private WildcardTypeBuilder() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public WildcardType build() {
            return new WildcardTypeImpl(upperBounds, lowerBounds);
        }

        public WildcardTypeBuilder withUpperBounds(final Type... bounds) {
            this.upperBounds = bounds;
            return this;
        }
    }

    /**
     * WildcardType implementation class.
     */
    private static final class WildcardTypeImpl implements WildcardType {
        private final Type[] upperBounds;
        private final Type[] lowerBounds;

        /**
         * Constructs a new instance.
         *
         * @param upperBounds of this type.
         * @param lowerBounds of this type.
         */
        private WildcardTypeImpl(final Type[] upperBounds, final Type[] lowerBounds) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {
            return obj == this || obj instanceof WildcardType && TypeUtils.equals(this, (WildcardType) obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type[] getLowerBounds() {
            return lowerBounds.clone();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type[] getUpperBounds() {
            return upperBounds.clone();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int result = 73 << 8;
            result |= Arrays.hashCode(upperBounds);
            result <<= 8;
            result |= Arrays.hashCode(lowerBounds);
            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return TypeUtils.toString(this);
        }
    }
    private static <T> String classToString(final Class<T> cls) {

        return null;
    }

    public static boolean containsTypeVariables(final Type type) {
        return false;
    }

    private static boolean containsVariableTypeSameParametrizedTypeBound(final TypeVariable<?> typeVariable, final ParameterizedType parameterizedType) {
        return true;
    }
    private static boolean equals(final GenericArrayType genericArrayType, final Type type) {
        return type instanceof GenericArrayType && equals(genericArrayType.getGenericComponentType(), ((GenericArrayType) type).getGenericComponentType());
    }

    private static boolean equals(final ParameterizedType parameterizedType, final Type type) {

        return false;
    }


    private static boolean equals(final Type[] type1, final Type[] type2) {

        return false;
    }


    private static boolean equals(final WildcardType wildcardType, final Type type) {
        return false;
    }


    private static Type getClosestParentType(final Class<?> cls, final Class<?> superClass) {
        // only look at the interfaces if the super class is also an interface

        return  null;
    }

    public static Type[] getImplicitBounds(final TypeVariable<?> typeVariable) {
        return normalizeUpperToObject(Objects.requireNonNull(typeVariable, "typeVariable").getBounds());
    }
  public static Type[] getImplicitLowerBounds(final WildcardType wildcardType) {
        Objects.requireNonNull(wildcardType, "wildcardType");
        final Type[] bounds = wildcardType.getLowerBounds();
        return bounds.length == 0 ? new Type[] { null } : bounds;
    }

    public static Type[] getImplicitUpperBounds(final WildcardType wildcardType) {
        return normalizeUpperToObject(Objects.requireNonNull(wildcardType, "wildcardType").getUpperBounds());
    }
    private static Class<?> getRawType(final ParameterizedType parameterizedType) {
        return null;
    }

    private static Map<TypeVariable<?>, Type> getTypeArguments(Class<?> cls, final Class<?> toClass, final Map<TypeVariable<?>, Type> subtypeVarAssigns) {

        return null;
    }

    private static Map<TypeVariable<?>, Type> getTypeArguments(final ParameterizedType parameterizedType, final Class<?> toClass,
            final Map<TypeVariable<?>, Type> subtypeVarAssigns) {
       return null;
    }

    private static Map<TypeVariable<?>, Type> getTypeArguments(final Type type, final Class<?> toClass, final Map<TypeVariable<?>, Type> subtypeVarAssigns) {
        return null;
    }



    private static boolean isAssignable(final Type type, final Class<?> toClass) {

        return true;
    }

    private static boolean isAssignable(final Type type, final GenericArrayType toGenericArrayType, final Map<TypeVariable<?>, Type> typeVarAssigns) {
      return true;
    }

    private static boolean isAssignable(final Type type, final ParameterizedType toParameterizedType, final Map<TypeVariable<?>, Type> typeVarAssigns) {

        return true;
    }



    private static boolean isAssignable(final Type type, final Type toType, final Map<TypeVariable<?>, Type> typeVarAssigns) {
        if (toType == null || toType instanceof Class<?>) {
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
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");


            return isAssignable(type, (Class<?>) toType);
        }
        if (toType instanceof ParameterizedType) {
            return isAssignable(type, (ParameterizedType) toType, typeVarAssigns);
        }
        if (toType instanceof GenericArrayType) {
            return isAssignable(type, (GenericArrayType) toType, typeVarAssigns);
        }
        if (toType instanceof WildcardType) {
            return isAssignable(type, (WildcardType) toType, typeVarAssigns);
        }
        if (toType instanceof TypeVariable<?>) {
            return isAssignable(type, (TypeVariable<?>) toType, typeVarAssigns);
        }
        throw new IllegalStateException("found an unhandled type: " + toType);
    }

    /**
     * Tests if the subject type may be implicitly cast to the target type variable following the Java generics rules.
     *
     * @param type           the subject type to be assigned to the target type.
     * @param toTypeVariable the target type variable.
     * @param typeVarAssigns a map with type variables.
     * @return {@code true} if {@code type} is assignable to {@code toTypeVariable}.
     */
    private static boolean isAssignable(final Type type, final TypeVariable<?> toTypeVariable, final Map<TypeVariable<?>, Type> typeVarAssigns) {
        if (type == null) {
            return true;
        }
        // only a null type can be assigned to null type which
        // would have cause the previous to return true
        if (toTypeVariable == null) {
            return false;
        }
        // all types are assignable to themselves
        if (toTypeVariable.equals(type)) {
            return true;
        }
        if (type instanceof TypeVariable<?>) {
            // a type variable is assignable to another type variable, if
            // and only if the former is the latter, extends the latter, or
            // is otherwise a descendant of the latter.
            final Type[] bounds = getImplicitBounds((TypeVariable<?>) type);
            for (final Type bound : bounds) {
                if (isAssignable(bound, toTypeVariable, typeVarAssigns)) {
                    return true;
                }
            }
        }
        if (type instanceof Class<?> || type instanceof ParameterizedType || type instanceof GenericArrayType || type instanceof WildcardType) {
            return false;
        }
        throw new IllegalStateException("found an unhandled type: " + type);
    }

    /**
     * Tests if the subject type may be implicitly cast to the target wildcard type following the Java generics rules.
     *
     * @param type           the subject type to be assigned to the target type.
     * @param toWildcardType the target wildcard type.
     * @param typeVarAssigns a map with type variables.
     * @return {@code true} if {@code type} is assignable to {@code toWildcardType}.
     */
    private static boolean isAssignable(final Type type, final WildcardType toWildcardType, final Map<TypeVariable<?>, Type> typeVarAssigns) {
        if (type == null) {
            return true;
        }
        // only a null type can be assigned to null type which
        // would have cause the previous to return true
        if (toWildcardType == null) {
            return false;
        }
        // all types are assignable to themselves
        if (toWildcardType.equals(type)) {
            return true;
        }
        final Type[] toUpperBounds = getImplicitUpperBounds(toWildcardType);
        final Type[] toLowerBounds = getImplicitLowerBounds(toWildcardType);
        if (type instanceof WildcardType) {
            final WildcardType wildcardType = (WildcardType) type;
            final Type[] upperBounds = getImplicitUpperBounds(wildcardType);
            final Type[] lowerBounds = getImplicitLowerBounds(wildcardType);
            for (Type toBound : toUpperBounds) {
                // if there are assignments for unresolved type variables,
                // now's the time to substitute them.
                toBound = substituteTypeVariables(toBound, typeVarAssigns);
                // each upper bound of the subject type has to be assignable to
                // each
                // upper bound of the target type
                for (final Type bound : upperBounds) {
                    if (!isAssignable(bound, toBound, typeVarAssigns)) {
                        return false;
                    }
                }
            }
            for (Type toBound : toLowerBounds) {
                // if there are assignments for unresolved type variables,
                // now's the time to substitute them.
                toBound = substituteTypeVariables(toBound, typeVarAssigns);
                // each lower bound of the target type has to be assignable to
                // each
                // lower bound of the subject type
                for (final Type bound : lowerBounds) {
                    if (!isAssignable(toBound, bound, typeVarAssigns)) {
                        return false;
                    }
                }
            }
            return true;
        }
        for (final Type toBound : toUpperBounds) {
            // if there are assignments for unresolved type variables,
            // now's the time to substitute them.
            if (!isAssignable(type, substituteTypeVariables(toBound, typeVarAssigns), typeVarAssigns)) {
                return false;
            }
        }
        for (final Type toBound : toLowerBounds) {
            // if there are assignments for unresolved type variables,
            // now's the time to substitute them.
            if (!isAssignable(substituteTypeVariables(toBound, typeVarAssigns), type, typeVarAssigns)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests whether the class contains a cyclical reference in the qualified name of a class. If any of the type parameters of A class is extending X class
     * which is in scope of A class, then it forms cycle.
     *
     * @param cls the class to test.
     * @return whether the class contains a cyclical reference.
     */
    private static boolean isCyclical(final Class<?> cls) {
        for (final TypeVariable<?> typeParameter : cls.getTypeParameters()) {
            for (final Type bound : typeParameter.getBounds()) {
                if (bound.getTypeName().contains(cls.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Tests if the given value can be assigned to the target type following the Java generics rules.
     *
     * @param value the value to be checked.
     * @param type  the target type.
     * @return {@code true} if {@code value} is an instance of {@code type}.
     */
    public static boolean isInstance(final Object value, final Type type) {
        if (type == null) {
            return false;
        }
        return value == null ? !(type instanceof Class<?>) || !((Class<?>) type).isPrimitive() : isAssignable(value.getClass(), type, null);
    }

    /**
     * Maps type variables.
     *
     * @param <T>               the generic type of the class in question.
     * @param cls               the class in question.
     * @param parameterizedType the parameterized type.
     * @param typeVarAssigns    the map to be filled.
     */
    private static <T> void mapTypeVariablesToArguments(final Class<T> cls, final ParameterizedType parameterizedType,
            final Map<TypeVariable<?>, Type> typeVarAssigns) {

    }

    /**
     * Strips out the redundant upper bound types in type variable types and wildcard types (or it would with wildcard types if multiple upper bounds were
     * allowed).
     *
     * <p>
     * Example, with the variable type declaration:
     * </p>
     *
     * <pre>{@code
     * <K extends java.util.Collection<String> & java.util.List<String>>
     * }</pre>
     *
     * <p>
     * since {@link List} is a subinterface of {@link Collection}, this method will return the bounds as if the declaration had been:
     * </p>
     *
     * <pre>{@code
     * <K extends java.util.List<String>>
     * }</pre>
     *
     * @param bounds an array of types representing the upper bounds of either {@link WildcardType} or {@link TypeVariable}, not {@code null}.
     * @return an array containing the values from {@code bounds} minus the redundant types.
     * @throws NullPointerException if {@code bounds} is {@code null}.
     */
    public static Type[] normalizeUpperBounds(final Type[] bounds) {

        return null;
    }

    /**
     * Delegates to {@link #normalizeUpperBounds(Type[])} unless {@code bounds} is empty in which case return an array with the element {@code Object.class}.
     *
     * @param bounds bounds an array of types representing the upper bounds of either {@link WildcardType} or {@link TypeVariable}, not {@code null}.
     * @return result from {@link #normalizeUpperBounds(Type[])} unless {@code bounds} is empty in which case return an array with the element
     *         {@code Object.class}.
     */
    private static Type[] normalizeUpperToObject(final Type[] bounds) {
        return bounds.length == 0 ? new Type[] { Object.class } : normalizeUpperBounds(bounds);
    }

    private static String parameterizedTypeToString(final ParameterizedType parameterizedType) {
        return "";
    }

    private static Type substituteTypeVariables(final Type type, final Map<TypeVariable<?>, Type> typeVarAssigns) {

        return null;
    }


    public static String toLongString(final TypeVariable<?> typeVariable) {

        return "";
    }


    /**
     * Unrolls variables in a type bounds array.
     *
     * @param typeArguments assignments {@link Map}.
     * @param bounds        in which to expand variables.
     * @return {@code bounds} with any variables reassigned.
     */
    private static Type[] unrollBounds(final Map<TypeVariable<?>, Type> typeArguments, final Type[] bounds) {

        return null;
    }

    private static Type unrollVariableAssignments(TypeVariable<?> typeVariable, final Map<TypeVariable<?>, Type> typeVarAssigns) {

        return null;
    }

    public static Type unrollVariables(Map<TypeVariable<?>, Type> typeArguments, final Type type) {
        if (typeArguments == null) {
            typeArguments = Collections.emptyMap();
        }
        return unrollVariables(typeArguments, type, new HashSet<>());
    }

    private static Type unrollVariables(final Map<TypeVariable<?>, Type> typeArguments, final Type type, final Set<TypeVariable<?>> visited) {

        return null;
    }

    /**
     * Creates a new {@link WildcardTypeBuilder}.
     *
     * @return a new {@link WildcardTypeBuilder}.
     * @since 3.2
     */
    public static WildcardTypeBuilder wildcardType() {
        return new WildcardTypeBuilder();
    }

    /**
     * Formats a {@link WildcardType} as a {@link String}.
     *
     *
     * @return String.
     */

    @Deprecated
    public TypeUtils() {
        // empty
    }

}
