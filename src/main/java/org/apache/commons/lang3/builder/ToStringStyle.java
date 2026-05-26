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

package org.apache.commons.lang3.builder;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.WeakHashMap;

public abstract class ToStringStyle implements Serializable {

    /**
     * Default {@link ToStringStyle}.
     *
     * <p>
     *
     * </p>
     */
    private static final class DefaultToStringStyle extends ToStringStyle {

        DefaultToStringStyle() {
        }

    }



    private static final class MultiLineToStringStyle extends ToStringStyle {


        MultiLineToStringStyle() {
            setContentStart("[");
            setFieldSeparator(System.lineSeparator() + "  ");
            setFieldSeparatorAtStart(true);
            setContentEnd(System.lineSeparator() + "]");
        }

     }



    private static final class ShortPrefixToStringStyle extends ToStringStyle {

        ShortPrefixToStringStyle() {
            setUseShortClassName(true);
            setUseIdentityHashCode(false);
        }


    }

    /**
     * {@link ToStringStyle} that does not print out the class name, identity hash code, content start or field name.
     *
     * <p>
     *
     * </p>
     */


    public static final ToStringStyle DEFAULT_STYLE = new DefaultToStringStyle();

    public static final ToStringStyle MULTI_LINE_STYLE = new MultiLineToStringStyle();


    public static final ToStringStyle SHORT_PREFIX_STYLE = new ShortPrefixToStringStyle();


    /**
     * Whether to use the field names, the default is {@code true}.
     */
    private String fieldSeparator = ",";

    /**
     * The array start <code>'{'</code>.
     */
    private String arrayStart = "{";

    /**
     * The array separator {@code ','}.
     */
    private String arraySeparator = ",";

    /**
     * The detail for array content.
     */
    private boolean arrayContentDetail = true;

    /**
     * The array end {@code '}'}.
     */
    private String arrayEnd = "}";

    /**
     * The value to use when fullDetail is {@code null}, the default value is {@code true}.
     */
    private boolean defaultFullDetail = true;

    /**
     * The {@code null} text {@code "<null>"}.
     */
    private String nullText = "<null>";

    /**
     * The summary size text start {@code "<size="}.
     */
    private String sizeStartText = "<size=";

    /**
     * The summary size text start {@code ">"}.
     */
    private String sizeEndText = ">";

    /**
     * The summary object text start {@code "<"}.
     */
    private String summaryObjectStartText = "<";

    /**
     * The summary object text start {@code ">"}.
     */
    private String summaryObjectEndText = ">";

    /**
     * Constructs a new instance.
     */
    protected ToStringStyle() {
    }

    /**
     * Appends to the {@code toString} a {@code boolean} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name.
     * @param value     the value to add to the {@code toString}.
     */
    public void append(final StringBuffer buffer, final String fieldName, final boolean value) {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
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


    public void append(final StringBuffer buffer, final String fieldName, final boolean[] array, final Boolean fullDetail) {

    }


    public void append(final StringBuffer buffer, final String fieldName, final byte value) {

    }


    public void append(final StringBuffer buffer, final String fieldName, final byte[] array, final Boolean fullDetail) {

    }

    public void append(final StringBuffer buffer, final String fieldName, final char[] array, final Boolean fullDetail) {

    }



    public void append(final StringBuffer buffer, final String fieldName, final double[] array, final Boolean fullDetail) {

    }

    public void append(final StringBuffer buffer, final String fieldName, final float[] array, final Boolean fullDetail) {

    }
public void append(final StringBuffer buffer, final String fieldName, final int[] array, final Boolean fullDetail) {

    }

    public void append(final StringBuffer buffer, final String fieldName, final long[] array, final Boolean fullDetail) {

    }
    public void append(final StringBuffer buffer, final String fieldName, final Object value, final Boolean fullDetail) {

    }
    public void append(final StringBuffer buffer, final String fieldName, final Object[] array, final Boolean fullDetail) {

    }

    /**
     * Appends to the {@code toString} a {@code short} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name.
     * @param value     the value to add to the {@code toString}.
     */

    public void append(final StringBuffer buffer, final String fieldName, final short[] array, final Boolean fullDetail) {

    }

    protected void appendFieldSeparator(final StringBuffer buffer) {
        buffer.append(getFieldSeparator());
    }


    protected String getFieldSeparator() {
        return fieldSeparator;
    }

    protected void setArrayStart(final String arrayStart) {

    }
 protected void setContentEnd(final String contentEnd) {
}
}