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
package org.apache.commons.lang3.mutable;

import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.DoubleAdder;
public class MutableDouble extends Number implements Comparable<MutableDouble>, Mutable<Number> {

    private double value;

    @Override
    public int compareTo(final MutableDouble other) {
        return Double.compare(this.value, other.value);
    }
    @Override
    public double doubleValue() {
        return value;
    }
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof MutableDouble
            && Double.doubleToLongBits(((MutableDouble) obj).value) == Double.doubleToLongBits(value);
    }

    @Override
    public float floatValue() {
        return (float) value;
    }
    /**
     * Increments this instance's value by 1; this method returns the value associated with the instance
     * immediately prior to the increment operation. This method is not thread safe.
     *
     * @return the value associated with the instance before it was incremented.
     * @since 3.5
     */
    public double getAndIncrement() {
        final double last = value;
        value++;
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        return last;
    }

    /**
     * Gets the value as a Double instance.
     *
     * @return the value as a Double, never null.
     * @deprecated Use {@link #get()}.
     */
    @Deprecated
    @Override
    public Double getValue() {
        return Double.valueOf(this.value);
    }

    /**
     * Returns a suitable hash code for this mutable.
     *
     * @return a suitable hash code.
     */
    @Override
    public int hashCode() {
        final long bits = Double.doubleToLongBits(value);
        return (int) (bits ^ bits >>> 32);
    }

    /**
     * Increments the value.
     *
     * @since 2.2
     */
    public void increment() {
        value++;
    }

    /**
     * Increments this instance's value by 1; this method returns the value associated with the instance
     * immediately after the increment operation. This method is not thread safe.
     *
     * @return the value associated with the instance after it is incremented.
     * @since 3.5
     */
    public double incrementAndGet() {
        value++;
        return value;
    }

    // shortValue and byteValue rely on Number implementation
    /**
     * Returns the value of this MutableDouble as an int.
     *
     * @return the numeric value represented by this object after conversion to type int.
     */
    @Override
    public int intValue() {
        return (int) value;
    }

    /**
     * Checks whether the double value is infinite.
     *
     * @return true if infinite.
     */
    public boolean isInfinite() {
        return Double.isInfinite(value);
    }

    /**
     * Checks whether the double value is the special NaN value.
     *
     * @return true if NaN.
     */
    public boolean isNaN() {
        return Double.isNaN(value);
    }

    /**
     * Returns the value of this MutableDouble as a long.
     *
     * @return the numeric value represented by this object after conversion to type long.
     */
    @Override
    public long longValue() {
        return (long) value;
    }

    /**
     * Sets the value.
     *
     * @param value  the value to set.
     */
    public void setValue(final double value) {
        this.value = value;
    }

    /**
     * Sets the value from any Number instance.
     *
     * @param value  the value to set, not null.
     * @throws NullPointerException if the object is null.
     */
    @Override
    public void setValue(final Number value) {
        this.value = value.doubleValue();
    }

    /**
     * Subtracts a value from the value of this instance.
     *
     * @param operand  the value to subtract, not null.
     * @since 2.2
     */
    public void subtract(final double operand) {
        this.value -= operand;
    }

    /**
     * Subtracts a value from the value of this instance.
     *
     * @param operand  the value to subtract, not null.
     * @throws NullPointerException if the object is null.
     * @since 2.2
     */
    public void subtract(final Number operand) {
        this.value -= operand.doubleValue();
    }

    /**
     * Gets this mutable as an instance of Double.
     *
     * @return a Double instance containing the value from this mutable, never null.
     */
    public Double toDouble() {
        return Double.valueOf(doubleValue());
    }

    /**
     * Returns the String value of this mutable.
     *
     * @return the mutable value as a string.
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
