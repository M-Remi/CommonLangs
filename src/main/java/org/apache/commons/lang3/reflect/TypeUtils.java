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
    private static final class ParameterizedTypeImpl implements ParameterizedType {
        private final Class<?> raw;
        private final Type useOwner;
        private final Type[] typeArguments;

        /**
         * Constructs a new instance.
         *
         * @param rawClass      type.
         * @param useOwner      owner type to use, if any.
         * @param typeArguments formal type arguments.
         */
        private ParameterizedTypeImpl(final Class<?> rawClass, final Type useOwner, final Type[] typeArguments) {
            this.raw = rawClass;
            this.useOwner = useOwner;
            this.typeArguments = Arrays.copyOf(typeArguments, typeArguments.length, Type[].class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {
            return obj == this || obj instanceof ParameterizedType && TypeUtils.equals(this, (ParameterizedType) obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type[] getActualTypeArguments() {
            return typeArguments.clone();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type getOwnerType() {
            return useOwner;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type getRawType() {
            return raw;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int result = 71 << 4;
            result |= raw.hashCode();
            result <<= 4;
            result |= Objects.hashCode(useOwner);
            result <<= 8;
            result |= Arrays.hashCode(typeArguments);
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
     * {@link WildcardType} builder.
     *
     * @since 3.2
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


    /**
     * Method classToString joiner.
     */
    // @formatter:off

    /**
     * Greater than and lesser than sign joiner.
     */
    // @formatter:off

    // @formatter:on

    /**
     * A wildcard instance matching {@code ?}.
     *
     * @since 3.2
     */

    /**
     * Formats a {@link Class} as a {@link String}.
     *
     * @param cls {@link Class} to format.
     * @return The class as a String.
     */



    public static boolean containsTypeVariables(final Type type) {
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        return false;
    }

    private static boolean containsVariableTypeSameParametrizedTypeBound(final TypeVariable<?> typeVariable, final ParameterizedType parameterizedType) {
      return true;
    }





    private static boolean equals(final GenericArrayType genericArrayType, final Type type) {
        return true;
    }

    /**
     * Tests whether {@code t} equals {@code p}.
     *
     * @param parameterizedType LHS.
     * @param type              RHS.
     * @return boolean.
     */
    private static boolean equals(final ParameterizedType parameterizedType, final Type type) {

        return false;
    }





    private static boolean equals(final Type[] type1, final Type[] type2) {

        return false;
    }


    private static boolean equals(final WildcardType wildcardType, final Type type) {

        return false;
    }


    private static Type[] extractTypeArgumentsFrom(final Map<TypeVariable<?>, Type> mappings, final TypeVariable<?>[] variables) {
        final Type[] result = new Type[variables.length];
        int index = 0;

        return result;
    }

    private static int[] findRecursiveTypes(final ParameterizedType parameterizedType) {
               return null;
    }

    /**
     * Creates a generic array type instance.
     *
     * @param componentType the type of the elements of the array. For example the component type of {@code boolean[]} is {@code boolean}.
     * @return {@link GenericArrayType}.
     * @since 3.2
     */
    public static GenericArrayType genericArrayType(final Type componentType) {
        return new GenericArrayTypeImpl(Objects.requireNonNull(componentType, "componentType"));
    }

    /**
     * Formats a {@link GenericArrayType} as a {@link String}.
     *
     * @param genericArrayType {@link GenericArrayType} to format.
     * @return String.
     */
    private static String genericArrayTypeToString(final GenericArrayType genericArrayType) {
        return String.format("%s[]", toString(genericArrayType.getGenericComponentType()));
    }

    /**
     * Gets the array component type of {@code type}.
     *
     * @param type the type to be checked.
     * @return component type or null if type is not an array type.
     */
    public static Type getArrayComponentType(final Type type) {
        if (type instanceof Class<?>) {
            final Class<?> cls = (Class<?>) type;
            return cls.isArray() ? cls.getComponentType() : null;
        }
        if (type instanceof GenericArrayType) {
            return ((GenericArrayType) type).getGenericComponentType();
        }
        return null;
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
        if (type instanceof Class<?>) {
            // it is raw, no problem
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            // simple enough to get the raw type of a ParameterizedType
            return getRawType((ParameterizedType) type);
        }
        if (type instanceof TypeVariable<?>) {
            if (assigningType == null) {
                return null;
            }
            // get the entity declaring this type variable
            final Object genericDeclaration = ((TypeVariable<?>) type).getGenericDeclaration();
            // can't get the raw type of a method- or constructor-declared type
            // variable
            if (!(genericDeclaration instanceof Class<?>)) {
                return null;
            }
            // get the type arguments for the declaring class/interface based
            // on the enclosing type
            final Map<TypeVariable<?>, Type> typeVarAssigns = getTypeArguments(assigningType, (Class<?>) genericDeclaration);
            // enclosingType has to be a subclass (or subinterface) of the
            // declaring type
            if (typeVarAssigns == null) {
                return null;
            }
            // get the argument assigned to this type variable
            final Type typeArgument = typeVarAssigns.get(type);
            if (typeArgument == null) {
                return null;
            }
            // get the argument for this type variable
            return getRawType(typeArgument, assigningType);
        }
        if (type instanceof GenericArrayType) {
            // get raw component type
            final Class<?> rawComponentType = getRawType(((GenericArrayType) type).getGenericComponentType(), assigningType);
            // create array type from raw component type and return its class
            return rawComponentType != null ? Array.newInstance(rawComponentType, 0).getClass() : null;
        }
        // (hand-waving) this is not the method you're looking for
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


    private static Map<TypeVariable<?>, Type> getTypeArguments(final ParameterizedType parameterizedType, final Class<?> toClass,
            final Map<TypeVariable<?>, Type> subtypeVarAssigns) {
        return null;
    }



    private static boolean isAssignable(final Type type, final Class<?> toClass) {
        return true;
    }



    private static boolean isAssignable(final Type type, final Type toType, final Map<TypeVariable<?>, Type> typeVarAssigns) {
      return true;
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
        // capture the type variables from the owner type that have assignments
        final Type ownerType = parameterizedType.getOwnerType();
        if (ownerType instanceof ParameterizedType) {
            // recursion to make sure the owner's owner type gets processed
            mapTypeVariablesToArguments(cls, (ParameterizedType) ownerType, typeVarAssigns);
        }
        // parameterizedType is a generic interface/class (or it's in the owner
        // hierarchy of said interface/class) implemented/extended by the class
        // cls. Find out which type variables of cls are type arguments of
        // parameterizedType:
        final Type[] typeArgs = parameterizedType.getActualTypeArguments();
        // of the cls's type variables that are arguments of parameterizedType,
        // find out which ones can be determined from the super type's arguments
        final TypeVariable<?>[] typeVars = getRawType(parameterizedType).getTypeParameters();
        // use List view of type parameters of cls so the contains() method can be used:
        final List<TypeVariable<Class<T>>> typeVarList = Arrays.asList(cls.getTypeParameters());
        for (int i = 0; i < typeArgs.length; i++) {
            final TypeVariable<?> typeVar = typeVars[i];
            final Type typeArg = typeArgs[i];
            // argument of parameterizedType is a type variable of cls
            if (typeVarList.contains(typeArg)
                    // type variable of parameterizedType has an assignment in
                    // the super type.
                    && typeVarAssigns.containsKey(typeVar)) {
                // map the assignment to the cls's type variable
                typeVarAssigns.put((TypeVariable<?>) typeArg, typeVarAssigns.get(typeVar));
            }
        }
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

        return null;
    }



    private static Type substituteTypeVariables(final Type type, final Map<TypeVariable<?>, Type> typeVarAssigns) {

        return null;
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
     * Determines whether or not specified types satisfy the bounds of their mapped type variables. When a type parameter extends another (such as
     * {@code <T, S extends T>}), uses another as a type parameter (such as {@code <T, S extends Comparable>>}), or otherwise depends on another type variable
     * to be specified, the dependencies must be included in {@code typeVarAssigns}.
     *
     * @param typeVariableMap specifies the potential types to be assigned to the type variables, not {@code null}.
     * @return whether or not the types can be assigned to their respective type variables.
     * @throws NullPointerException if {@code typeVariableMap} is {@code null}.
     */
    public static boolean typesSatisfyVariables(final Map<TypeVariable<?>, Type> typeVariableMap) {
        Objects.requireNonNull(typeVariableMap, "typeVariableMap");
        // all types must be assignable to all the bounds of their mapped
        // type variable.
        for (final Map.Entry<TypeVariable<?>, Type> entry : typeVariableMap.entrySet()) {
            final TypeVariable<?> typeVar = entry.getKey();
            final Type type = entry.getValue();
            for (final Type bound : getImplicitBounds(typeVar)) {
                if (!isAssignable(type, substituteTypeVariables(bound, typeVariableMap), typeVariableMap)) {
                    return false;
                }
            }
        }
        return true;
    }

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
