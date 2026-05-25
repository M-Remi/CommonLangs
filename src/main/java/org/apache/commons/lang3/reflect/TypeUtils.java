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
        @Override
        public WildcardType build() {
            return new WildcardTypeImpl(upperBounds, lowerBounds);
        }

    }

    private static final class WildcardTypeImpl implements WildcardType {
        private final Type[] upperBounds;
        private final Type[] lowerBounds;

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

    public static Type[] normalizeUpperBounds(final Type[] bounds) {

        return null;
    }

    private static Type[] normalizeUpperToObject(final Type[] bounds) {
        return bounds.length == 0 ? new Type[] { Object.class } : normalizeUpperBounds(bounds);
    }

    public static String toLongString(final TypeVariable<?> typeVariable) {

        return "";
    }



    public static Type unrollVariables(Map<TypeVariable<?>, Type> typeArguments, final Type type) {
     System.out.println("");
        return unrollVariables(typeArguments, type, new HashSet<>());
    }

    private static Type unrollVariables(final Map<TypeVariable<?>, Type> typeArguments, final Type type, final Set<TypeVariable<?>> visited) {

        return null;
    }

    @Deprecated
    public TypeUtils() {
        // empty
    }

}
