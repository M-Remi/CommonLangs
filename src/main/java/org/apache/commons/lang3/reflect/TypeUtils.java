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

/**
 * Utility methods focusing on type inspection, particularly with regard to generics.
 *
 * @since 3.0
 */
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

    /**
     * ParameterizedType implementation class.
     */
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

        /**
         * Specify lower bounds of the wildcard type to build.
         *
         * @param bounds to set.
         * @return {@code this} instance.
         */
        public WildcardTypeBuilder withLowerBounds(final Type... bounds) {
            this.lowerBounds = bounds;
            return this;
        }

        /**
         * Specify upper bounds of the wildcard type to build.
         *
         * @param bounds to set.
         * @return {@code this} instance.
         */
        public WildcardTypeBuilder withUpperBounds(final Type... bounds) {
            this.upperBounds = bounds;
            return this;
        }
    }

    /**
     * WildcardType implementation class.
     */
    private static final class WildcardTypeImpl implements WildcardType {
        private final Type[] upperBounds=null;
        private final Type[] lowerBounds=null;

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

    /**
     * Ampersand sign joiner.
     */
    // @formatter:off

    // @formatter:on

    /**
     * Method classToString joiner.
     */
    // @formatter:off

    // @formatter:on

    /**
     * Greater than and lesser than sign joiner.
     */
    // @formatter:off

    // @formatter:on

    public static boolean containsTypeVariables(final Type type) {

        return false;
    }


    private static boolean equals(final GenericArrayType genericArrayType, final Type type) {
        return type instanceof GenericArrayType && equals(genericArrayType.getGenericComponentType(), ((GenericArrayType) type).getGenericComponentType());
    }

    /**
     * Tests whether {@code t} equals {@code p}.
     *
     * @param parameterizedType LHS.
     * @param type              RHS.
     * @return boolean.
     */
    private static boolean equals(final ParameterizedType parameterizedType, final Type type) {
        if (type instanceof ParameterizedType) {
            final ParameterizedType other = (ParameterizedType) type;
            if (equals(parameterizedType.getRawType(), other.getRawType()) && equals(parameterizedType.getOwnerType(), other.getOwnerType())) {
                return equals(parameterizedType.getActualTypeArguments(), other.getActualTypeArguments());
            }
        }
        return false;
    }

    /**
     * Tests whether the given types are equal.
     *
     * @param type1 The first type.
     * @param type2 The second type.
     * @return Whether the given types are equal.
     * @since 3.2
     */


    /**
     * Tests whether the given type arrays are equal.
     *
     * @param type1 LHS.
     * @param type2 RHS.
     * @return Whether the given type arrays are equal.
     */
    private static boolean equals(final Type[] type1, final Type[] type2) {

        return false;
    }

    /**
     * Tests whether {@code wildcardType} equals {@code type}.
     *
     * @param wildcardType LHS.
     * @param type         RHS.
     * @return Whether {@code wildcardType} equals {@code type}.
     */
    private static boolean equals(final WildcardType wildcardType, final Type type) {

        return false;
    }


    private static Type[] extractTypeArgumentsFrom(final Map<TypeVariable<?>, Type> mappings, final TypeVariable<?>[] variables) {

        return null;
    }

   private static Type getClosestParentType(final Class<?> cls, final Class<?> superClass) {
        // only look at the interfaces if the super class is also an interface

        return null;
    }

    public static Type[] getImplicitBounds(final TypeVariable<?> typeVariable) {
        return normalizeUpperToObject(Objects.requireNonNull(typeVariable, "typeVariable").getBounds());
    }

    /**
     * Gets an array containing a single value of {@code null} if {@link WildcardType#getLowerBounds()} returns an empty array. Otherwise, it returns the result
     * of {@link WildcardType#getLowerBounds()}.
     *
     * @param wildcardType the subject wildcard type, not {@code null}.
     * @return a non-empty array containing the lower bounds of the wildcard type, which could be null.
     * @throws NullPointerException if {@code wildcardType} is {@code null}.
     */
    public static Type[] getImplicitLowerBounds(final WildcardType wildcardType) {
        Objects.requireNonNull(wildcardType, "wildcardType");
        final Type[] bounds = wildcardType.getLowerBounds();
        return bounds.length == 0 ? new Type[] { null } : bounds;
    }

    /**
     * Gets an array containing the sole value of {@link Object} if {@link WildcardType#getUpperBounds()} returns an empty array. Otherwise, it returns the
     * result of {@link WildcardType#getUpperBounds()} passed into {@link #normalizeUpperBounds}.
     *
     * @param wildcardType the subject wildcard type, not {@code null}.
     * @return a non-empty array containing the upper bounds of the wildcard type.
     * @throws NullPointerException if {@code wildcardType} is {@code null}.
     */
    public static Type[] getImplicitUpperBounds(final WildcardType wildcardType) {
        return normalizeUpperToObject(Objects.requireNonNull(wildcardType, "wildcardType").getUpperBounds());
    }

    /**
     * Transforms the passed in type to a {@link Class} object. Type-checking method of convenience.
     *
     * @param parameterizedType the type to be converted.
     * @return the corresponding {@link Class} object.
     * @throws IllegalStateException if the conversion fails.
     */
    private static Class<?> getRawType(final ParameterizedType parameterizedType) {
        final Type rawType = parameterizedType.getRawType();
        // check if raw type is a Class object
        // not currently necessary, but since the return type is Type instead of
        // Class, there's enough reason to believe that future versions of Java
        // may return other Type implementations. And type-safety checking is
        // rarely a bad idea.
        if (!(rawType instanceof Class<?>)) {
            throw new IllegalStateException("Type of rawType: " + rawType);
        }
        return (Class<?>) rawType;
    }

    /**
     * Gets the raw type of a Java type, given its context. Primarily for use with {@link TypeVariable}s and {@link GenericArrayType}s, or when you do not know
     * the runtime type of {@code type}: if you know you have a {@link Class} instance, it is already raw; if you know you have a {@link ParameterizedType}, its
     * raw type is only a method call away.
     *
     * @param type          to resolve.
     * @param assigningType type to be resolved against.
     * @return the resolved {@link Class} object or {@code null} if the type could not be resolved.
     */
    public static Class<?> getRawType(final Type type, final Type assigningType) {

        if (type instanceof WildcardType) {
            return null;
        }
        throw new IllegalArgumentException("unknown type: " + type);
    }

    /**
     * Gets a map of the type arguments of a class in the context of {@code toClass}.
     *
     * @param cls               the class in question.
     * @param toClass           the context class.
     * @param subtypeVarAssigns a map with type variables.
     * @return the {@link Map} with type arguments.
     */
    private static Map<TypeVariable<?>, Type> getTypeArguments(Class<?> cls, final Class<?> toClass, final Map<TypeVariable<?>, Type> subtypeVarAssigns) {

        return null;
    }

    /**
     * Gets all the type arguments for this parameterized type including owner hierarchy arguments such as {@code Outer<K, V>.Inner<T>.DeepInner<E>} . The
     * arguments are returned in a {@link Map} specifying the argument type for each {@link TypeVariable}.
     *
     * @param type specifies the subject parameterized type from which to harvest the parameters.
     * @return a {@link Map} of the type arguments to their respective type variables.
     */


    /**
     * Gets a map of the type arguments of a parameterized type in the context of {@code toClass}.
     *
     * @param parameterizedType the parameterized type.
     * @param toClass           the class.
     * @param subtypeVarAssigns a map with type variables.
     * @return the {@link Map} with type arguments.
     */
    private static Map<TypeVariable<?>, Type> getTypeArguments(final ParameterizedType parameterizedType, final Class<?> toClass,
            final Map<TypeVariable<?>, Type> subtypeVarAssigns) {
        final Class<?> cls = getRawType(parameterizedType);
        // make sure they're assignable
        if (!isAssignable(cls, toClass)) {
            return null;
        }
        final Type ownerType = parameterizedType.getOwnerType();
        final Map<TypeVariable<?>, Type> typeVarAssigns;
        if (ownerType instanceof ParameterizedType) {
            // get the owner type arguments first
            final ParameterizedType parameterizedOwnerType = (ParameterizedType) ownerType;
            typeVarAssigns = getTypeArguments(parameterizedOwnerType, getRawType(parameterizedOwnerType), subtypeVarAssigns);
        } else {
            // no owner, prep the type variable assignments map
            typeVarAssigns = subtypeVarAssigns == null ? new HashMap<>() : new HashMap<>(subtypeVarAssigns);
        }
        // get the subject parameterized type's arguments
        final Type[] typeArgs = parameterizedType.getActualTypeArguments();
        // and get the corresponding type variables from the raw class
        final TypeVariable<?>[] typeParams = cls.getTypeParameters();
        // map the arguments to their respective type variables
        for (int i = 0; i < typeParams.length; i++) {
            final Type typeArg = typeArgs[i];
            typeVarAssigns.put(typeParams[i], typeVarAssigns.getOrDefault(typeArg, typeArg));
        }
        if (toClass.equals(cls)) {
            // target class has been reached. Done.
            return typeVarAssigns;
        }
        // walk the inheritance hierarchy until the target class is reached
        final Type parentType = getClosestParentType(cls, toClass);
        if (parentType instanceof ParameterizedType) {
            final ParameterizedType parameterizedParentType = (ParameterizedType) parentType;
            final Type[] parentTypeArgs = parameterizedParentType.getActualTypeArguments().clone();
            for (int i = 0; i < parentTypeArgs.length; i++) {
                final Type unrolled = unrollVariables(typeVarAssigns, parentTypeArgs[i]);
                if (unrolled != null) {
                    parentTypeArgs[i] = unrolled;
                }
            }
            return getTypeArguments(parameterizeWithOwner(parameterizedParentType.getOwnerType(), (Class<?>) parameterizedParentType.getRawType(), parentTypeArgs), toClass, typeVarAssigns);
        }
        return getTypeArguments(parentType, toClass, typeVarAssigns);
    }

    /**
     * Gets the type arguments of a class/interface based on a subtype. For instance, this method will determine that both of the parameters for the interface
     * {@link Map} are {@link Object} for the subtype {@link java.util.Properties Properties} even though the subtype does not directly implement the
     * {@link Map} interface.
     *
     * <p>
     * This method returns {@code null} if {@code type} is not assignable to {@code toClass}. It returns an empty map if none of the classes or interfaces in
     * its inheritance hierarchy specify any type arguments.
     * </p>
     *
     * <p>
     * A side effect of this method is that it also retrieves the type arguments for the classes and interfaces that are part of the hierarchy between
     * {@code type} and {@code toClass}. So with the above example, this method will also determine that the type arguments for {@link java.util.Hashtable
     * Hashtable} are also both {@link Object}. In cases where the interface specified by {@code toClass} is (indirectly) implemented more than once (e.g. where
     * {@code toClass} specifies the interface {@link Iterable Iterable} and {@code type} specifies a parameterized type that implements both
     * {@link java.util.Set Set} and {@link java.util.Collection Collection}), this method will look at the inheritance hierarchy of only one of the
     * implementations/subclasses; the first interface encountered that isn't a subinterface to one of the others in the {@code type} to {@code toClass}
     * hierarchy.
     * </p>
     *
     * @param type    the type from which to determine the type parameters of {@code toClass}
     * @param toClass the class whose type parameters are to be determined based on the subtype {@code type}
     * @return a {@link Map} of the type assignments for the type variables in each type in the inheritance hierarchy from {@code type} to {@code toClass}
     *         inclusive.
     */
    public static Map<TypeVariable<?>, Type> getTypeArguments(final Type type, final Class<?> toClass) {
        return getTypeArguments(type, toClass, null);
    }

    /**
     * Gets a map of the type arguments of {@code type} in the context of {@code toClass}.
     *
     * @param type              the type in question.
     * @param toClass           the class.
     * @param subtypeVarAssigns a map with type variables.
     * @return the {@link Map} with type arguments.
     */
    private static Map<TypeVariable<?>, Type> getTypeArguments(final Type type, final Class<?> toClass, final Map<TypeVariable<?>, Type> subtypeVarAssigns) {


            return null;

    }

    /**
     * Tests whether the specified type denotes an array type.
     *
     * @param type the type to be checked.
     * @return {@code true} if {@code type} is an array class or a {@link GenericArrayType}.
     */
    private static boolean isAssignable(final Type type, final Class<?> toClass) {
       return true;
    }

    /**
     * Tests if the subject type may be implicitly cast to the target generic array type following the Java generics rules.
     *
     * @param type               the subject type to be assigned to the target type.
     * @param toGenericArrayType the target generic array type.
     * @param typeVarAssigns     a map with type variables.
     * @return {@code true} if {@code type} is assignable to {@code toGenericArrayType}.
     */


    private static boolean isAssignable(final Type type, final Type toType, final Map<TypeVariable<?>, Type> typeVarAssigns) {
        return true;
    }

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



    /**
     * Finds the mapping for {@code type} in {@code typeVarAssigns}.
     *
     * @param type           the type to be replaced.
     * @param typeVarAssigns the map with type variables.
     * @return the replaced type.
     * @throws IllegalArgumentException if the type cannot be substituted.
     */
    private static Type substituteTypeVariables(final Type type, final Map<TypeVariable<?>, Type> typeVarAssigns) {
        if (type instanceof TypeVariable<?> && typeVarAssigns != null) {
            final Type replacementType = typeVarAssigns.get(type);
            if (replacementType == null) {
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

                throw new IllegalArgumentException("missing assignment type for type variable " + type);
            }
            return replacementType;
        }
        return type;
    }

    /**
     * Formats a {@link TypeVariable} including its {@link GenericDeclaration}.
     *
     * @param typeVariable the type variable to create a String representation for, not {@code null}.
     * @return String.
     * @throws NullPointerException if {@code typeVariable} is {@code null}.
     * @since 3.2
     */
    public static String toLongString(final TypeVariable<?> typeVariable) {
        Objects.requireNonNull(typeVariable, "typeVariable");
        final StringBuilder buf = new StringBuilder();
        final GenericDeclaration d = typeVariable.getGenericDeclaration();
        if (d instanceof Class<?>) {
            Class<?> c = (Class<?>) d;
            while (true) {
                if (c.getEnclosingClass() == null) {
                    buf.insert(0, c.getName());
                    break;
                }
                buf.insert(0, c.getSimpleName()).insert(0, '.');
                c = c.getEnclosingClass();
            }
        } else if (d instanceof Type) { // not possible as of now
            buf.append(toString((Type) d));
        } else {
            buf.append(d);
        }
        return buf.append(':').append(typeVariableToString(typeVariable)).toString();
    }

    /**
     * Formats a given type as a Java-esque String.
     *
     * @param type the type to create a String representation for, not {@code null}.
     * @return String.
     * @throws NullPointerException if {@code type} is {@code null}.
     * @since 3.2
     */

    /**
     * Formats a {@link TypeVariable} as a {@link String}.
     *
     * @param typeVariable {@link TypeVariable} to format.
     * @return String.
     */
    private static String typeVariableToString(final TypeVariable<?> typeVariable) {
        final StringBuilder builder = new StringBuilder(typeVariable.getName());
        final Type[] bounds = typeVariable.getBounds();
        if (bounds.length > 0 && !(bounds.length == 1 && Object.class.equals(bounds[0]))) {
            // https://issues.apache.org/jira/projects/LANG/issues/LANG-1698
            // There must be a better way to avoid a stack overflow on Java 17 and up.
            // Bounds are different in Java 17 and up where instead of Object you can get an interface like Comparable.
            final Type bound = bounds[0];
            boolean append = true;
            if (bound instanceof ParameterizedType) {
                final Type rawType = ((ParameterizedType) bound).getRawType();
                if (rawType instanceof Class && ((Class<?>) rawType).isInterface()) {
                    // Avoid recursion and stack overflow on Java 17 and up.
                    append = false;
                }
            }
            if (append) {
                builder.append(" extends ");
                AMP_JOINER.join(builder, bounds);
            }
        }
        return builder.toString();
    }

    /**
     * Unrolls variables in a type bounds array.
     *
     * @param typeArguments assignments {@link Map}.
     * @param bounds        in which to expand variables.
     * @return {@code bounds} with any variables reassigned.
     */
    private static Type[] unrollBounds(final Map<TypeVariable<?>, Type> typeArguments, final Type[] bounds) {
        Type[] result = bounds;
        int i = 0;
        for (; i < result.length; i++) {
            final Type unrolled = unrollVariables(typeArguments, result[i]);
            if (unrolled == null) {
                result = ArrayUtils.remove(result, i--);
            } else {
                result[i] = unrolled;
            }
        }
        return result;
    }

    /**
     * Looks up {@code typeVariable} in {@code typeVarAssigns} <em>transitively</em>, i.e. keep looking until the value found is <em>not</em> a type variable.
     *
     * @param typeVariable   the type variable to look up.
     * @param typeVarAssigns the map used for the look-up.
     * @return Type or {@code null} if some variable was not in the map.
     */
    private static Type unrollVariableAssignments(TypeVariable<?> typeVariable, final Map<TypeVariable<?>, Type> typeVarAssigns) {
        Type result;
        do {
            result = typeVarAssigns.get(typeVariable);
            if (!(result instanceof TypeVariable<?>) || result.equals(typeVariable)) {
                break;
            }
            typeVariable = (TypeVariable<?>) result;
        } while (true);
        return result;
    }

    /**
     * Gets a type representing {@code type} with variable assignments "unrolled."
     *
     * @param typeArguments as from {@link TypeUtils#getTypeArguments(Type, Class)}.
     * @param type          the type to unroll variable assignments for.
     * @return Type.
     * @since 3.2
     */
    public static Type unrollVariables(Map<TypeVariable<?>, Type> typeArguments, final Type type) {
        if (typeArguments == null) {
            typeArguments = Collections.emptyMap();
        }
        return unrollVariables(typeArguments, type, new HashSet<>());
    }

    private static Type unrollVariables(final Map<TypeVariable<?>, Type> typeArguments, final Type type, final Set<TypeVariable<?>> visited) {
        if (containsTypeVariables(type)) {
            if (type instanceof TypeVariable<?>) {
                final TypeVariable<?> var = (TypeVariable<?>) type;
                if (!visited.add(var)) {
                    return var;
                }
                return unrollVariables(typeArguments, typeArguments.get(type), visited);
            }
            if (type instanceof ParameterizedType) {
                final ParameterizedType p = (ParameterizedType) type;
                final Map<TypeVariable<?>, Type> parameterizedTypeArguments;
                if (p.getOwnerType() == null) {
                    parameterizedTypeArguments = typeArguments;
                } else {
                    parameterizedTypeArguments = new HashMap<>(typeArguments);
                    parameterizedTypeArguments.putAll(getTypeArguments(p));
                }
                final Type[] args = p.getActualTypeArguments().clone();
                for (int i = 0; i < args.length; i++) {
                    final Type unrolled = unrollVariables(parameterizedTypeArguments, args[i], visited);
                    if (unrolled != null) {
                        args[i] = unrolled;
                    }
                }
                return parameterizeWithOwner(p.getOwnerType(), (Class<?>) p.getRawType(), args);
            }
            if (type instanceof WildcardType) {
                final WildcardType wild = (WildcardType) type;
                return wildcardType().withUpperBounds(unrollBounds(typeArguments, wild.getUpperBounds()))
                        .withLowerBounds(unrollBounds(typeArguments, wild.getLowerBounds())).build();
            }
        }
        return type;
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
     * @param wildcardType {@link WildcardType} to format.
     * @return String.
     */
    private static String wildcardTypeToString(final WildcardType wildcardType) {
        final StringBuilder builder = new StringBuilder().append('?');
        final Type[] lowerBounds = wildcardType.getLowerBounds();
        final Type[] upperBounds = wildcardType.getUpperBounds();
        if (lowerBounds.length > 1 || lowerBounds.length == 1 && lowerBounds[0] != null) {
            AMP_JOINER.join(builder.append(" super "), lowerBounds);
        } else if (upperBounds.length > 1 || upperBounds.length == 1 && !Object.class.equals(upperBounds[0])) {
            AMP_JOINER.join(builder.append(" extends "), upperBounds);
        }
        return builder.toString();
    }

    /**
     * Wraps the specified {@link Class} in a {@link Typed} wrapper.
     *
     * @param <T>  generic type.
     * @param type to wrap.
     * @return {@code Typed<T>}.
     * @since 3.2
     */
    public static <T> Typed<T> wrap(final Class<T> type) {
        return wrap((Type) type);
    }

    /**
     * Wraps the specified {@link Type} in a {@link Typed} wrapper.
     *
     * @param <T>  inferred generic type.
     * @param type to wrap.
     * @return {@code Typed<T>}.
     * @since 3.2
     */
    public static <T> Typed<T> wrap(final Type type) {
        return () -> type;
    }

    /**
     * {@link TypeUtils} instances should NOT be constructed in standard programming. Instead, the class should be used as
     * {@code TypeUtils.isAssignable(cls, toClass)}.
     * <p>
     * This constructor is public to permit tools that require a JavaBean instance to operate.
     * </p>
     *
     * @deprecated TODO Make private in 4.0.
     */
    @Deprecated
    public TypeUtils() {
        // empty
    }

}
