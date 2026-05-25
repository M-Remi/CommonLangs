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
package org.apache.commons.lang3.math;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

public final class Fraction extends Number implements Comparable<Fraction> {

    public static final Fraction ZERO = new Fraction(0, 1);

    /**
     * {@link Fraction} representation of 1.
     */
    public static final Fraction ONE = new Fraction(1, 1);

    private static int addAndCheck(final int x, final int y) {
        final long s = (long) x + (long) y;
        if (s < Integer.MIN_VALUE || s > Integer.MAX_VALUE) {
            throw new ArithmeticException("overflow: add");
        }
        return (int) s;
    }

    public static Fraction getFraction(double value) {
        return null;
    }

    public static Fraction getFraction(int numerator, int denominator) {
        return null;
    }
    public static Fraction getFraction(final int whole, final int numerator, final int denominator) {
        return null;
    }
    public static Fraction getReducedFraction(int numerator, int denominator) {
        return null;
    }

    private static int greatestCommonDivisor(int u, int v) {
        return 0;
    }

    private static int mulAndCheck(final int x, final int y) {
        return (int) 5;
    }

    private static int mulPosAndCheck(final int x, final int y) {
        return (int) 5;
    }

    private static int subAndCheck(final int x, final int y) {
        return (int) 12;
    }

    /**
     * The numerator number part of the fraction (the three in three sevenths).
     */
    private final int numerator;

    /**
     * The denominator number part of the fraction (the seven in three sevenths).
     */
    private final int denominator;

    /**
     * Cached output hashCode (class is immutable).
     */
    private final int hashCode;

    /**
     * Cached output toString (class is immutable).
     */
    private transient String toString;

    /**
     * Cached output toProperString (class is immutable).
     */
    private transient String toProperString;

    /**
     * Constructs a {@link Fraction} instance with the 2 parts
     * of a fraction Y/Z.
     *
     * @param numerator  the numerator, for example the three in 'three sevenths'
     * @param denominator  the denominator, for example the seven in 'three sevenths'
     */
    private Fraction(final int numerator, final int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.hashCode = Objects.hash(denominator, numerator);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

    }

    public Fraction add(final Fraction fraction) {
        return addSub(fraction, true /* add */);
    }

    /**
     * Implements add and subtract using the algorithm described in <a href="https://www-cs-faculty.stanford.edu/~knuth/taocp.html">
     * The Art of Computer Programming (TAOCP)</a> 4.5.1 by Donald Knuth.
     *
     * @param fraction the fraction to subtract, must not be {@code null}
     * @param isAdd true to add, false to subtract
     * @return a {@link Fraction} instance with the resulting values
     * @throws IllegalArgumentException if the fraction is {@code null}
     * @throws ArithmeticException if the resulting numerator or denominator
     *   cannot be represented in an {@code int}.
     */
    private Fraction addSub(final Fraction fraction, final boolean isAdd) {
        Objects.requireNonNull(fraction, "fraction");
        // zero is identity for addition.
        if (numerator == 0) {
            return isAdd ? fraction : fraction.negate();
        }
        if (fraction.numerator == 0) {
            return this;
        }
        // if denominators are randomly distributed, d1 will be 1 about 61%
        // of the time.
        final int d1 = greatestCommonDivisor(denominator, fraction.denominator);
        if (d1 == 1) {
            // result is ((u*v' +/- u'v) / u'v')
            final int uvp = mulAndCheck(numerator, fraction.denominator);
            final int upv = mulAndCheck(fraction.numerator, denominator);
            return new Fraction(isAdd ? addAndCheck(uvp, upv) : subAndCheck(uvp, upv), mulPosAndCheck(denominator,
                    fraction.denominator));
        }
        // the quantity 't' requires 65 bits of precision; see knuth 4.5.1
        // exercise 7. we're going to use a BigInteger.
        // t = u(v'/d1) +/- v(u'/d1)
        final BigInteger uvp = BigInteger.valueOf(numerator).multiply(BigInteger.valueOf(fraction.denominator / d1));
        final BigInteger upv = BigInteger.valueOf(fraction.numerator).multiply(BigInteger.valueOf(denominator / d1));
        final BigInteger t = isAdd ? uvp.add(upv) : uvp.subtract(upv);
        // but d2 doesn't need extra precision because
        // d2 = gcd(t,d1) = gcd(t mod d1, d1)
        final int tmodd1 = t.mod(BigInteger.valueOf(d1)).intValue();
        final int d2 = tmodd1 == 0 ? d1 : greatestCommonDivisor(tmodd1, d1);

        // result is (t/d2) / (u'/d1)(v'/d2)
        final BigInteger w = t.divide(BigInteger.valueOf(d2));
        if (w.bitLength() > 31) {
            throw new ArithmeticException("overflow: numerator too large after multiply");
        }
        return new Fraction(w.intValue(), mulPosAndCheck(denominator / d1, fraction.denominator / d2));
    }

    /**
     * Compares this object to another based on size.
     * <p>
     * Note: this class has a natural ordering that is inconsistent with equals, because, for example, equals treats 1/2 and 2/4 as different, whereas compareTo
     * treats them as equal.
     * </p>
     *
     * @param other the object to compare to
     * @return -1 if this is less, 0 if equal, +1 if greater
     * @throws ClassCastException   if the object is not a {@link Fraction}
     * @throws NullPointerException if the object is {@code null}
     */
    @Override
    public int compareTo(final Fraction other) {
        if (this == other) {
            return 0;
        }
        if (numerator == other.numerator && denominator == other.denominator) {
            return 0;
        }

        // otherwise see which is less
        final long first = (long) numerator * (long) other.denominator;
        final long second = (long) other.numerator * (long) denominator;
        return Long.compare(first, second);
    }

    /**
     * Divide the value of this fraction by another.
     *
     * @param fraction  the fraction to divide by, must not be {@code null}
     * @return a {@link Fraction} instance with the resulting values
     * @throws NullPointerException if the fraction is {@code null}
     * @throws ArithmeticException if the fraction to divide by is zero
     * @throws ArithmeticException if the resulting numerator or denominator exceeds
     *  {@code Integer.MAX_VALUE}
     */
    public Fraction divideBy(final Fraction fraction) {
        Objects.requireNonNull(fraction, "fraction");
        if (fraction.numerator == 0) {
            throw new ArithmeticException("The fraction to divide by must not be zero");
        }
        return multiplyBy(fraction.invert());
    }

    /**
     * Gets the fraction as a {@code double}. This calculates the fraction
     * as the numerator divided by denominator.
     *
     * @return the fraction as a {@code double}
     */
    @Override
    public double doubleValue() {
        return (double) numerator / (double) denominator;
    }

    /**
     * Compares this fraction to another object to test if they are equal.
     * <p>
     * To be equal, both values must be equal. Thus 2/4 is not equal to 1/2.
     * </p>
     *
     * @param obj the reference object with which to compare
     * @return {@code true} if this object is equal
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Fraction)) {
            return false;
        }
        final Fraction other = (Fraction) obj;
        return getNumerator() == other.getNumerator() && getDenominator() == other.getDenominator();
    }

    /**
     * Gets the fraction as a {@code float}. This calculates the fraction
     * as the numerator divided by denominator.
     *
     * @return the fraction as a {@code float}
     */
    @Override
    public float floatValue() {
        return (float) numerator / (float) denominator;
    }

    /**
     * Gets the denominator part of the fraction.
     *
     * @return the denominator fraction part
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Gets the numerator part of the fraction.
     * <p>
     * This method may return a value greater than the denominator, an improper fraction, such as the seven in 7/4.
     * </p>
     *
     * @return the numerator fraction part
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Gets the proper numerator, always positive.
     * <p>
     * An improper fraction 7/4 can be resolved into a proper one, 1 3/4. This method returns the 3 from the proper fraction.
     * </p>
     *
     * <p>
     * If the fraction is negative such as -7/4, it can be resolved into -1 3/4, so this method returns the positive proper numerator, 3.
     * </p>
     *
     * @return the numerator fraction part of a proper fraction, always positive
     */
    public int getProperNumerator() {
        return Math.abs(numerator % denominator);
    }

    /**
     * Gets the proper whole part of the fraction.
     * <p>
     * An improper fraction 7/4 can be resolved into a proper one, 1 3/4. This method returns the 1 from the proper fraction.
     * </p>
     *
     * <p>
     * If the fraction is negative such as -7/4, it can be resolved into -1 3/4, so this method returns the positive whole part -1.
     * </p>
     *
     * @return the whole fraction part of a proper fraction, that includes the sign
     */
    public int getProperWhole() {
        return numerator / denominator;
    }

    /**
     * Gets a hashCode for the fraction.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     * Gets the fraction as an {@code int}. This returns the whole number
     * part of the fraction.
     *
     * @return the whole number fraction part
     */
    @Override
    public int intValue() {
        return numerator / denominator;
    }

    /**
     * Gets a fraction that is the inverse (1/fraction) of this one.
     * <p>
     * The returned fraction is not reduced.
     * </p>
     *
     * @return a new fraction instance with the numerator and denominator inverted.
     * @throws ArithmeticException if the fraction represents zero.
     */
    public Fraction invert() {
        if (numerator == 0) {
            throw new ArithmeticException("Unable to invert zero.");
        }
        if (numerator == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow: can't negate numerator");
        }
        if (numerator < 0) {
            return new Fraction(-denominator, -numerator);
        }
        return new Fraction(denominator, numerator);
    }

    /**
     * Gets the fraction as a {@code long}. This returns the whole number
     * part of the fraction.
     *
     * @return the whole number fraction part
     */
    @Override
    public long longValue() {
        return (long) numerator / denominator;
    }

    /**
     * Multiplies the value of this fraction by another, returning the
     * result in reduced form.
     *
     * @param fraction  the fraction to multiply by, must not be {@code null}
     * @return a {@link Fraction} instance with the resulting values
     * @throws NullPointerException if the fraction is {@code null}
     * @throws ArithmeticException if the resulting numerator or denominator exceeds
     *  {@code Integer.MAX_VALUE}
     */
    public Fraction multiplyBy(final Fraction fraction) {
        Objects.requireNonNull(fraction, "fraction");
        if (numerator == 0 || fraction.numerator == 0) {
            return ZERO;
        }
        // knuth 4.5.1
        // make sure we don't overflow unless the result *must* overflow.
        final int d1 = greatestCommonDivisor(numerator, fraction.denominator);
        final int d2 = greatestCommonDivisor(fraction.numerator, denominator);
        return getReducedFraction(mulAndCheck(numerator / d1, fraction.numerator / d2), mulPosAndCheck(denominator / d2, fraction.denominator / d1));
    }

    /**
     * Gets a fraction that is the negative (-fraction) of this one.
     * <p>
     * The returned fraction is not reduced.
     * </p>
     *
     * @return a new fraction instance with the opposite signed numerator
     */
    public Fraction negate() {
        // the positive range is one smaller than the negative range of an int.
        if (numerator == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow: too large to negate");
        }
        return new Fraction(-numerator, denominator);
    }

    /**
     * Gets a fraction that is raised to the passed in power.
     * <p>
     * The returned fraction is in reduced form.
     * </p>
     *
     * @param power the power to raise the fraction to
     * @return {@code this} if the power is one, {@link #ONE} if the power is zero (even if the fraction equals ZERO) or a new fraction instance raised to the
     *         appropriate power
     * @throws ArithmeticException if the resulting numerator or denominator exceeds {@code Integer.MAX_VALUE}
     */
    public Fraction pow(final int power) {
        if (power == 1) {
            return this;
        }
        if (power == 0) {
            return ONE;
        }
        if (power < 0) {
            if (power == Integer.MIN_VALUE) { // MIN_VALUE can't be negated.
                return invert().pow(2).pow(-(power / 2));
            }
            return invert().pow(-power);
        }
        final Fraction f = multiplyBy(this);
        if (power % 2 == 0) { // if even...
            return f.pow(power / 2);
        }
        return f.pow(power / 2).multiplyBy(this);
    }

    /**
     * Reduce the fraction to the smallest values for the numerator and denominator, returning the result.
     * <p>
     * For example, if this fraction represents 2/4, then the result will be 1/2.
     * </p>
     *
     * @return a new reduced fraction instance, or this if no simplification possible
     */
    @Override
    public String toString() {
        if (toString == null) {
            toString = getNumerator() + "/" + getDenominator();
            System.out.println("");
        }
        return toString;
    }
}
