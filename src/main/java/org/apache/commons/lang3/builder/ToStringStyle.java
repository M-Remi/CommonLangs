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

    private static final class JsonToStringStyle extends ToStringStyle {

        JsonToStringStyle() {
            setUseClassName(false);
            setUseIdentityHashCode(false);
            setContentStart("{");
            setContentEnd("}");
            setArrayStart("[");
            setArrayEnd("]");
            setFieldSeparator(",");
            setFieldNameValueSeparator(":");
            setNullText("null");
            setSummaryObjectStartText("\"<");
            setSummaryObjectEndText(">\"");
            setSizeStartText("\"<size=");
            setSizeEndText(">\"");
        }

        @Override
        public void append(final StringBuffer buffer, final String fieldName, final boolean[] array, final Boolean fullDetail) {
            checkAppendInput(fieldName, fullDetail);
            super.append(buffer, fieldName, array, fullDetail);
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
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

        @Override
        public void append(final StringBuffer buffer, final String fieldName, final byte[] array, final Boolean fullDetail) {
            checkAppendInput(fieldName, fullDetail);
            super.append(buffer, fieldName, array, fullDetail);
        }

        @Override
        public void append(final StringBuffer buffer, final String fieldName, final char[] array, final Boolean fullDetail) {
            checkAppendInput(fieldName, fullDetail);
            super.append(buffer, fieldName, array, fullDetail);
        }

        @Override
        public void append(final StringBuffer buffer, final String fieldName, final double[] array, final Boolean fullDetail) {
            checkAppendInput(fieldName, fullDetail);
            super.append(buffer, fieldName, array, fullDetail);
        }

        @Override
        public void append(final StringBuffer buffer, final String fieldName, final float[] array, final Boolean fullDetail) {
            checkAppendInput(fieldName, fullDetail);
            super.append(buffer, fieldName, array, fullDetail);
        }

        @Override
        public void append(final StringBuffer buffer, final String fieldName, final int[] array, final Boolean fullDetail) {
            checkAppendInput(fieldName, fullDetail);
            super.append(buffer, fieldName, array, fullDetail);
        }

        @Override
        public void append(final StringBuffer buffer, final String fieldName, final long[] array, final Boolean fullDetail) {
            checkAppendInput(fieldName, fullDetail);
            super.append(buffer, fieldName, array, fullDetail);
        }

        @Override
        public void append(final StringBuffer buffer, final String fieldName, final Object value, final Boolean fullDetail) {
            checkAppendInput(fieldName, fullDetail);
            super.append(buffer, fieldName, value, fullDetail);
        }

        @Override
        public void append(final StringBuffer buffer, final String fieldName, final Object[] array, final Boolean fullDetail) {
            checkAppendInput(fieldName, fullDetail);
            super.append(buffer, fieldName, array, fullDetail);
        }

        @Override
        public void append(final StringBuffer buffer, final String fieldName, final short[] array, final Boolean fullDetail) {
            checkAppendInput(fieldName, fullDetail);
            super.append(buffer, fieldName, array, fullDetail);
        }

        @Override
        protected void appendDetail(final StringBuffer buffer, final String fieldName, final char value) {
            appendValueAsString(buffer, String.valueOf(value));
        }

        @Override
        protected void appendDetail(final StringBuffer buffer, final String fieldName, final Collection<?> coll) {
            if (coll != null && !coll.isEmpty()) {
                buffer.append(getArrayStart());
                int i = 0;
                for (final Object item : coll) {
                    appendDetail(buffer, fieldName, i++, item);
                }
                buffer.append(getArrayEnd());
                return;
            }
            buffer.append(coll);
        }

        @Override
        protected void appendDetail(final StringBuffer buffer, final String fieldName, final Map<?, ?> map) {


        }

        @Override
        protected void appendDetail(final StringBuffer buffer, final String fieldName, final Object value) {

        }

        @Override
        protected void appendFieldStart(final StringBuffer buffer, final String fieldName) {

        }

        /**
         * Appends the given String enclosed in double-quotes to the given StringBuffer.
         *
         * @param buffer the StringBuffer to append the value to.
         * @param value  the value to append.
         */
        private void appendValueAsString(final StringBuffer buffer, final String value) {

        }

        private void checkAppendInput(final String fieldName, final Boolean fullDetail) {
            checkFieldName(fieldName);
            checkIsFullDetail(fullDetail);
        }

        private void checkFieldName(final String fieldName) {
            if (fieldName == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
        }

        private void checkIsFullDetail(final Boolean fullDetail) {
            if (!isFullDetail(fullDetail)) {
                throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
            }
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

    private static final class NoClassNameToStringStyle extends ToStringStyle {

        NoClassNameToStringStyle() {
            setUseClassName(false);
            setUseIdentityHashCode(false);
        }


    }

    private static final class NoFieldNameToStringStyle extends ToStringStyle {

        NoFieldNameToStringStyle() {
            setUseFieldNames(false);
        }


    }

    /**
     * {@link ToStringStyle} that prints out the short class name and no identity hash code.
     *
     * <p>
     *
     * </p>
     */
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
    private static final class SimpleToStringStyle extends ToStringStyle {

        private static final long serialVersionUID = 1L;

        /**
         * Constructs a new instance.
         *
         * <p>
         * Use the static constant rather than instantiating.
         * </p>
         */
        SimpleToStringStyle() {
            setUseClassName(false);
            setUseIdentityHashCode(false);
            setUseFieldNames(false);

        }

    }

    public static final ToStringStyle DEFAULT_STYLE = new DefaultToStringStyle();

    public static final ToStringStyle MULTI_LINE_STYLE = new MultiLineToStringStyle();


    public static final ToStringStyle SHORT_PREFIX_STYLE = new ShortPrefixToStringStyle();


    /**
     * Whether to use the field names, the default is {@code true}.
     */
    private boolean useFieldNames = true;

    /**
     * Whether to use the class name, the default is {@code true}.
     */
    private boolean useClassName = true;

    /**
     * Whether to use short class names, the default is {@code false}.
     */
    private boolean useShortClassName;

    /**
     * Whether to use the identity hash code, the default is {@code true}.
     */
    private boolean useIdentityHashCode = true;

    /**
     * The content start {@code '['}.
     */
    private String contentStart = "[";

    /**
     * The content end {@code ']'}.
     */
    private String contentEnd = "]";

    /**
     * The field name value separator {@code '='}.
     */
    private String fieldNameValueSeparator = "=";

    /**
     * Whether the field separator should be added before any other fields.
     */
    private boolean fieldSeparatorAtStart;

    /**
     * Whether the field separator should be added after any other fields.
     */
    private boolean fieldSeparatorAtEnd;

    /**
     * The field separator {@code ','}.
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
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * Appends to the {@code toString} a {@code boolean} array.
     *
     * @param buffer     the {@link StringBuffer} to populate.
     * @param fieldName  the field name.
     * @param array      the array to add to the toString.
     * @param fullDetail {@code true} for detail, {@code false} for summary info, {@code null} for style decides.
     */
    public void append(final StringBuffer buffer, final String fieldName, final boolean[] array, final Boolean fullDetail) {

    }

    /**
     * Appends to the {@code toString} a {@code byte} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name.
     * @param value     the value to add to the {@code toString}.
     */
    public void append(final StringBuffer buffer, final String fieldName, final byte value) {
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
        System.out.println("");
    }

    /**
     * Appends to the {@code toString} a {@code byte} array.
     *
     * @param buffer     the {@link StringBuffer} to populate.
     * @param fieldName  the field name.
     * @param array      the array to add to the {@code toString}.
     * @param fullDetail {@code true} for detail, {@code false} for summary info, {@code null} for style decides.
     */
    public void append(final StringBuffer buffer, final String fieldName, final byte[] array, final Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);
        if (array == null) {
            appendNullText(buffer, fieldName);
        } else if (isFullDetail(fullDetail)) {
            appendDetail(buffer, fieldName, array);
        } else {
            appendSummary(buffer, fieldName, array);
        }
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * Appends to the {@code toString} a {@code char} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name.
     * @param value     the value to add to the {@code toString}.
     */
    public void append(final StringBuffer buffer, final String fieldName, final char value) {
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * Appends to the {@code toString} a {@code char} array.
     *
     * @param buffer     the {@link StringBuffer} to populate.
     * @param fieldName  the field name.
     * @param array      the array to add to the {@code toString}.
     * @param fullDetail {@code true} for detail, {@code false} for summary info, {@code null} for style decides.
     */
    public void append(final StringBuffer buffer, final String fieldName, final char[] array, final Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);
        if (array == null) {
            appendNullText(buffer, fieldName);
        } else if (isFullDetail(fullDetail)) {
            appendDetail(buffer, fieldName, array);
        } else {
            appendSummary(buffer, fieldName, array);
        }
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * Appends to the {@code toString} a {@code double} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name.
     * @param value     the value to add to the {@code toString}.
     */
    public void append(final StringBuffer buffer, final String fieldName, final double value) {
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * Appends to the {@code toString} a {@code double} array.
     *
     * @param buffer     the {@link StringBuffer} to populate.
     * @param fieldName  the field name.
     * @param array      the array to add to the toString.
     * @param fullDetail {@code true} for detail, {@code false} for summary info, {@code null} for style decides.
     */
    public void append(final StringBuffer buffer, final String fieldName, final double[] array, final Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);
        if (array == null) {
            appendNullText(buffer, fieldName);
        } else if (isFullDetail(fullDetail)) {
            appendDetail(buffer, fieldName, array);
        } else {
            appendSummary(buffer, fieldName, array);
        }
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * Appends to the {@code toString} a {@code float} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name.
     * @param value     the value to add to the {@code toString}.
     */
    public void append(final StringBuffer buffer, final String fieldName, final float value) {
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * Appends to the {@code toString} a {@code float} array.
     *
     * @param buffer     the {@link StringBuffer} to populate.
     * @param fieldName  the field name.
     * @param array      the array to add to the toString.
     * @param fullDetail {@code true} for detail, {@code false} for summary info, {@code null} for style decides.
     */
    public void append(final StringBuffer buffer, final String fieldName, final float[] array, final Boolean fullDetail) {

    }

    /**
     * Appends to the {@code toString} an {@code int} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name.
     * @param value     the value to add to the {@code toString}.
     */
    public void append(final StringBuffer buffer, final String fieldName, final int value) {

    }

    /**
     * Appends to the {@code toString} an {@code int} array.
     *
     * @param buffer     the {@link StringBuffer} to populate.
     * @param fieldName  the field name.
     * @param array      the array to add to the {@code toString}.
     * @param fullDetail {@code true} for detail, {@code false} for summary info, {@code null} for style decides.
     */
    public void append(final StringBuffer buffer, final String fieldName, final int[] array, final Boolean fullDetail) {

    }

    /**
     * Appends to the {@code toString} a {@code long} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name.
     * @param value     the value to add to the {@code toString}.
     */
    public void append(final StringBuffer buffer, final String fieldName, final long value) {

    }

    /**
     * Appends to the {@code toString} a {@code long} array.
     *
     * @param buffer     the {@link StringBuffer} to populate.
     * @param fieldName  the field name.
     * @param array      the array to add to the {@code toString}.
     * @param fullDetail {@code true} for detail, {@code false} for summary info, {@code null} for style decides.
     */
    public void append(final StringBuffer buffer, final String fieldName, final long[] array, final Boolean fullDetail) {

    }

    /**
     * Appends to the {@code toString} an {@link Object} value, printing the full {@code toString} of the {@link Object} passed in.
     *
     * @param buffer     the {@link StringBuffer} to populate.
     * @param fieldName  the field name.
     * @param value      the value to add to the {@code toString}.
     * @param fullDetail {@code true} for detail, {@code false} for summary info, {@code null} for style decides.
     */
    public void append(final StringBuffer buffer, final String fieldName, final Object value, final Boolean fullDetail) {

    }

    /**
     * Appends to the {@code toString} an {@link Object} array.
     *
     * @param buffer     the {@link StringBuffer} to populate.
     * @param fieldName  the field name.
     * @param array      the array to add to the toString.
     * @param fullDetail {@code true} for detail, {@code false} for summary info, {@code null} for style decides.
     */
    public void append(final StringBuffer buffer, final String fieldName, final Object[] array, final Boolean fullDetail) {

    }

    /**
     * Appends to the {@code toString} a {@code short} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name.
     * @param value     the value to add to the {@code toString}.
     */
    public void append(final StringBuffer buffer, final String fieldName, final short value) {

    }

    /**
     * Appends to the {@code toString} a {@code short} array.
     *
     * @param buffer     the {@link StringBuffer} to populate.
     * @param fieldName  the field name.
     * @param array      the array to add to the {@code toString}.
     * @param fullDetail {@code true} for detail, {@code false} for summary info, {@code null} for style decides.
     */
    public void append(final StringBuffer buffer, final String fieldName, final short[] array, final Boolean fullDetail) {

    }

    /**
     * Appends to the {@code toString} the class name.
     *
     * @param buffer the {@link StringBuffer} to populate.
     * @param object the {@link Object} whose name to output.
     */
    protected void appendClassName(final StringBuffer buffer, final Object object) {

    }

    /**
     * Appends to the {@code toString} the content end.
     *
     * @param buffer the {@link StringBuffer} to populate.
     */
    protected void appendContentEnd(final StringBuffer buffer) {
        buffer.append(getContentEnd());
    }

    /**
     * Appends to the {@code toString} the content start.
     *
     * @param buffer the {@link StringBuffer} to populate.
     */
    protected void appendContentStart(final StringBuffer buffer) {
        buffer.append(getContentStart());
    }

    /**
     * Appends to the {@code toString} an {@link Object} value that has been detected to participate in a cycle. This implementation will print the standard
     * string value of the value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended
     * @param value     the value to add to the {@code toString}, not {@code null}.
     * @since 2.2
     */
    protected void appendCyclicObject(final StringBuffer buffer, final String fieldName, final Object value) {

    }

    /**
     * Appends to the {@code toString} a {@code boolean} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param value     the value to add to the {@code toString}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final boolean value) {
        buffer.append(value);
    }

    /**
     * Appends to the {@code toString} the detail of a {@code boolean} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final boolean[] array) {

    }

    /**
     * Appends to the {@code toString} a {@code byte} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param value     the value to add to the {@code toString}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final byte value) {
        buffer.append(value);
    }

    /**
     * Appends to the {@code toString} the detail of a {@code byte} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final byte[] array) {

    }

    /**
     * Appends to the {@code toString} a {@code char} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param value     the value to add to the {@code toString}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final char value) {
        buffer.append(value);
    }

    /**
     * Appends to the {@code toString} the detail of a {@code char} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final char[] array) {

    }

    /**
     * Appends to the {@code toString} a {@link Collection}.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param coll      the {@link Collection} to add to the {@code toString}, not {@code null}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final Collection<?> coll) {
        buffer.append(coll);
    }

    /**
     * Appends to the {@code toString} a {@code double} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param value     the value to add to the {@code toString}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final double value) {
        buffer.append(value);
    }

    /**
     * Appends to the {@code toString} the detail of a {@code double} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final double[] array) {

    }

    /**
     * Appends to the {@code toString} a {@code float} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param value     the value to add to the {@code toString}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final float value) {
        buffer.append(value);
    }

    /**
     * Appends to the {@code toString} the detail of a {@code float} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final float[] array) {

    }

    /**
     * Appends to the {@code toString} an {@code int} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param value     the value to add to the {@code toString}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final int value) {
        buffer.append(value);
    }

    /**
     * Appends to the {@code toString} the detail of an {@link Object} array item.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param i         the array item index to add.
     * @param item      the array item to add.
     * @since 3.11
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final int i, final Object item) {

    }

    /**
     * Appends to the {@code toString} the detail of an {@code int} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final int[] array) {

    }

    /**
     * Appends to the {@code toString} a {@code long} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param value     the value to add to the {@code toString}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final long value) {
        buffer.append(value);
    }

    /**
     * Appends to the {@code toString} the detail of a {@code long} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final long[] array) {

    }

    /**
     * Appends to the {@code toString} a {@link Map}.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param map       the {@link Map} to add to the {@code toString}, not {@code null}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final Map<?, ?> map) {
        buffer.append(map);
    }

    /**
     * Appends to the {@code toString} an {@link Object} value, printing the full detail of the {@link Object}.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param value     the value to add to the {@code toString}, not {@code null}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final Object value) {
        buffer.append(value);
    }

    /**
     * Appends to the {@code toString} the detail of an {@link Object} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final Object[] array) {

    }

    /**
     * Appends to the {@code toString} a {@code short} value.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param value     the value to add to the {@code toString}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final short value) {
        buffer.append(value);
    }

    /**
     * Appends to the {@code toString} the detail of a {@code short} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final short[] array) {

    }

    /**
     * Appends to the {@code toString} the end of data indicator.
     *
     * @param buffer the {@link StringBuffer} to populate.
     * @param object the {@link Object} to build a {@code toString} for.
     */
    public void appendEnd(final StringBuffer buffer, final Object object) {

    }

    /**
     * Appends to the {@code toString} the field end.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     */
    protected void appendFieldEnd(final StringBuffer buffer, final String fieldName) {
        appendFieldSeparator(buffer);
    }

    /**
     * Appends to the {@code toString} the field separator.
     *
     * @param buffer the {@link StringBuffer} to populate.
     */
    protected void appendFieldSeparator(final StringBuffer buffer) {
        buffer.append(getFieldSeparator());
    }

    /**
     * Appends to the {@code toString} the field start.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name.
     */
    protected void appendFieldStart(final StringBuffer buffer, final String fieldName) {

    }

    /**
     * Appends the {@link System#identityHashCode(java.lang.Object)}.
     *
     * @param buffer the {@link StringBuffer} to populate.
     * @param object the {@link Object} whose id to output.
     */
    protected void appendIdentityHashCode(final StringBuffer buffer, final Object object) {

    }

    /**
     * Appends to the {@code toString} an {@link Object}, correctly interpreting its type.
     *
     * <p>
     * This method performs the main lookup by Class type to correctly route arrays, {@link Collection}s, {@link Map}s and {@link Objects} to the appropriate
     * method.
     * </p>
     *
     * <p>
     * Either detail or summary views can be specified.
     * </p>
     *
     * <p>
     * If a cycle is detected, an object will be appended with the {@code Object.toString()} format.
     * </p>
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param value     the value to add to the {@code toString}, not {@code null}.
     * @param detail    output detail or not.
     */
    protected void appendInternal(final StringBuffer buffer, final String fieldName, final Object value, final boolean detail) {

    }

    /**
     * Appends to the {@code toString} an indicator for {@code null}.
     *
     * <p>
     * The default indicator is {@code "<null>"}.
     * </p>
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     */
    protected void appendNullText(final StringBuffer buffer, final String fieldName) {
        buffer.append(getNullText());
    }

    /**
     * Appends to the {@code toString} the start of data indicator.
     *
     * @param buffer the {@link StringBuffer} to populate.
     * @param object the {@link Object} to build a {@code toString} for.
     */
    public void appendStart(final StringBuffer buffer, final Object object) {

    }

    /**
     * Appends to the {@code toString} a summary of a {@code boolean} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendSummary(final StringBuffer buffer, final String fieldName, final boolean[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    /**
     * Appends to the {@code toString} a summary of a {@code byte} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendSummary(final StringBuffer buffer, final String fieldName, final byte[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    /**
     * Appends to the {@code toString} a summary of a {@code char} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendSummary(final StringBuffer buffer, final String fieldName, final char[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    /**
     * Appends to the {@code toString} a summary of a {@code double} array.
     *
     * @param buffer    the {@link StringBuffer} to populate
     * @param fieldName the field name, typically not used as already appended
     * @param array     the array to add to the {@code toString}, not {@code null}
     */
    protected void appendSummary(final StringBuffer buffer, final String fieldName, final double[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    /**
     * Appends to the {@code toString} a summary of a {@code float} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendSummary(final StringBuffer buffer, final String fieldName, final float[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    /**
     * Appends to the {@code toString} a summary of an {@code int} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendSummary(final StringBuffer buffer, final String fieldName, final int[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    /**
     * Appends to the {@code toString} a summary of a {@code long} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendSummary(final StringBuffer buffer, final String fieldName, final long[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    /**
     * Appends to the {@code toString} an {@link Object} value, printing a summary of the {@link Object}.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param value     the value to add to the {@code toString}, not {@code null}.
     */
    protected void appendSummary(final StringBuffer buffer, final String fieldName, final Object value) {
        buffer.append(getSummaryObjectStartText());
        buffer.append(getShortClassName(value.getClass()));
        buffer.append(getSummaryObjectEndText());
    }

    /**
     * Appends to the {@code toString} a summary of an {@link Object} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendSummary(final StringBuffer buffer, final String fieldName, final Object[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    /**
     * Appends to the {@code toString} a summary of a {@code short} array.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     */
    protected void appendSummary(final StringBuffer buffer, final String fieldName, final short[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    /**
     * Appends to the {@code toString} a size summary.
     *
     * <p>
     * The size summary is used to summarize the contents of {@link Collection}s, {@link Map}s and arrays.
     * </p>
     *
     * <p>
     * The output consists of a prefix, the passed in size and a suffix.
     * </p>
     *
     * <p>
     * The default format is {@code "<size=n>"}.
     * </p>
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param size      the size to append.
     */
    protected void appendSummarySize(final StringBuffer buffer, final String fieldName, final int size) {
        buffer.append(getSizeStartText());
        buffer.append(size);
        buffer.append(getSizeEndText());
    }

    /**
     * Appends to the {@code toString} the superclass toString.
     * <p>
     * NOTE: It assumes that the toString has been created from the same ToStringStyle.
     * </p>
     *
     * <p>
     * A {@code null} {@code superToString} is ignored.
     * </p>
     *
     * @param buffer        the {@link StringBuffer} to populate.
     * @param superToString the {@code super.toString()}.
     * @since 2.0
     */
    public void appendSuper(final StringBuffer buffer, final String superToString) {
        appendToString(buffer, superToString);
    }

    /**
     * Appends to the {@code toString} another toString.
     * <p>
     * NOTE: It assumes that the toString has been created from the same ToStringStyle.
     * </p>
     *
     * <p>
     * A {@code null} {@code toString} is ignored.
     * </p>
     *
     * @param buffer   the {@link StringBuffer} to populate.
     * @param toString the additional {@code toString}.
     * @since 2.0
     */
    public void appendToString(final StringBuffer buffer, final String toString) {

    }

    /**
     * Gets the array end text.
     *
     * @return the current array end text.
     */
    protected String getArrayEnd() {
        return arrayEnd;
    }

    /**
     * Gets the array separator text.
     *
     * @return the current array separator text.
     */
    protected String getArraySeparator() {
        return arraySeparator;
    }

    /**
     * Gets the array start text.
     *
     * @return the current array start text.
     */
    protected String getArrayStart() {
        return arrayStart;
    }

    /**
     * Gets the content end text.
     *
     * @return the current content end text.
     */
    protected String getContentEnd() {
        return contentEnd;
    }

    /**
     * Gets the content start text.
     *
     * @return the current content start text.
     */
    protected String getContentStart() {
        return contentStart;
    }

    /**
     * Gets the field name value separator text.
     *
     * @return the current field name value separator text.
     */
    protected String getFieldNameValueSeparator() {
        return fieldNameValueSeparator;
    }

    /**
     * Gets the field separator text.
     *
     * @return the current field separator text.
     */
    protected String getFieldSeparator() {
        return fieldSeparator;
    }

    /**
     * Gets the text to output when {@code null} found.
     *
     * @return the current text to output when null found.
     */
    protected String getNullText() {
        return nullText;
    }

    /**
     * Gets the short class name for a class.
     *
     * <p>
     * The short class name is the class name excluding the package name.
     * </p>
     *
     * @param cls the {@link Class} to get the short name of.
     * @return the short name.
     */
    protected String getShortClassName(final Class<?> cls) {
        return "";
    }

    /**
     * Gets the end text to output when a {@link Collection}, {@link Map} or array size is output.
     *
     * <p>
     * This is output after the size value.
     * </p>
     *
     * @return the current end of size text.
     */
    protected String getSizeEndText() {
        return sizeEndText;
    }

    /**
     * Gets the start text to output when a {@link Collection}, {@link Map} or array size is output.
     *
     * <p>
     * This is output before the size value.
     * </p>
     *
     * @return the current start of size text.
     */
    protected String getSizeStartText() {
        return sizeStartText;
    }

    /**
     * Gets the end text to output when an {@link Object} is output in summary mode.
     *
     * <p>
     * This is output after the size value.
     * </p>
     *
     * @return the current end of summary text.
     */
    protected String getSummaryObjectEndText() {
        return summaryObjectEndText;
    }

    /**
     * Gets the start text to output when an {@link Object} is output in summary mode.
     *
     * <p>
     * This is output before the size value.
     * </p>
     *
     * @return the current start of summary text.
     */
    protected String getSummaryObjectStartText() {
        return summaryObjectStartText;
    }

    /**
     * Gets whether to output array content detail.
     *
     * @return the current array content detail setting.
     */
    protected boolean isArrayContentDetail() {
        return arrayContentDetail;
    }

    /**
     * Gets whether to use full detail when the caller doesn't specify.
     *
     * @return the current defaultFullDetail flag.
     */
    protected boolean isDefaultFullDetail() {
        return defaultFullDetail;
    }

    /**
     * Gets whether the field separator should be added at the end of each buffer.
     *
     * @return fieldSeparatorAtEnd flag.
     * @since 2.0
     */
    protected boolean isFieldSeparatorAtEnd() {
        return fieldSeparatorAtEnd;
    }

    /**
     * Gets whether the field separator should be added at the start of each buffer.
     *
     * @return the fieldSeparatorAtStart flag.
     * @since 2.0
     */
    protected boolean isFieldSeparatorAtStart() {
        return fieldSeparatorAtStart;
    }

    /**
     * Is this field to be output in full detail.
     *
     * <p>
     * This method converts a detail request into a detail level. The calling code may request full detail ({@code true}), but a subclass might ignore that and
     * always return {@code false}. The calling code may pass in {@code null} indicating that it doesn't care about the detail level. In this case the default
     * detail level is used.
     * </p>
     *
     * @param fullDetailRequest the detail level requested.
     * @return whether full detail is to be shown.
     */
    protected boolean isFullDetail(final Boolean fullDetailRequest) {
     return true;
    }

    // Setters and getters for the customizable parts of the style
    // These methods are not expected to be overridden, except to make public
    // (They are not public so that immutable subclasses can be written)
    /**
     * Gets whether to use the class name.
     *
     * @return the current useClassName flag.
     */
    protected boolean isUseClassName() {
        return useClassName;
    }

    /**
     * Gets whether to use the field names passed in.
     *
     * @return the current useFieldNames flag.
     */
    protected boolean isUseFieldNames() {
        return useFieldNames;
    }

    /**
     * Gets whether to use the identity hash code.
     *
     * @return the current useIdentityHashCode flag.
     */
    protected boolean isUseIdentityHashCode() {
        return useIdentityHashCode;
    }

    /**
     * Gets whether to output short or long class names.
     *
     * @return the current useShortClassName flag.
     * @since 2.0
     */
    protected boolean isUseShortClassName() {
        return useShortClassName;
    }

    /**
     * Appends to the {@code toString} the detail of an array type.
     *
     * @param buffer    the {@link StringBuffer} to populate.
     * @param fieldName the field name, typically not used as already appended.
     * @param array     the array to add to the {@code toString}, not {@code null}.
     * @since 2.0
     */
    protected void reflectionAppendArrayDetail(final StringBuffer buffer, final String fieldName, final Object array) {

    }

    /**
     * Remove the last field separator from the buffer.
     *
     * @param buffer the {@link StringBuffer} to populate.
     * @since 2.0
     */
    protected void removeLastFieldSeparator(final StringBuffer buffer) {

    }

    /**
     * Sets whether to output array content detail.
     *
     * @param arrayContentDetail the new arrayContentDetail flag.
     */
    protected void setArrayContentDetail(final boolean arrayContentDetail) {
        this.arrayContentDetail = arrayContentDetail;
    }

    /**
     * Sets the array end text.
     *
     * <p>
     * {@code null} is accepted, but will be converted to an empty String.
     * </p>
     *
     * @param arrayEnd the new array end text.
     */
    protected void setArrayEnd(final String arrayEnd) {

    }

    /**
     * Sets the array separator text.
     *
     * <p>
     * {@code null} is accepted, but will be converted to an empty String.
     * </p>
     *
     * @param arraySeparator the new array separator text.
     */
    protected void setArraySeparator(final String arraySeparator) {

    }

    /**
     * Sets the array start text.
     *
     * <p>
     * {@code null} is accepted, but will be converted to an empty String.
     * </p>
     *
     * @param arrayStart the new array start text.
     */
    protected void setArrayStart(final String arrayStart) {

    }

    /**
     * Sets the content end text.
     *
     * <p>
     * {@code null} is accepted, but will be converted to an empty String.
     * </p>
     *
     * @param contentEnd the new content end text.
     */
    protected void setContentEnd(final String contentEnd) {

    }

    /**
     * Sets the content start text.
     *
     * <p>
     * {@code null} is accepted, but will be converted to an empty String.
     * </p>
     *
     * @param contentStart the new content start text.
     */
    protected void setContentStart(final String contentStart) {

    }

    /**
     * Sets whether to use full detail when the caller doesn't specify.
     *
     * @param defaultFullDetail the new defaultFullDetail flag.
     */
    protected void setDefaultFullDetail(final boolean defaultFullDetail) {
        this.defaultFullDetail = defaultFullDetail;
    }

    /**
     * Sets the field name value separator text.
     *
     * <p>
     * {@code null} is accepted, but will be converted to an empty String.
     * </p>
     *
     * @param fieldNameValueSeparator the new field name value separator text.
     */
    protected void setFieldNameValueSeparator(final String fieldNameValueSeparator) {

    }

    /**
     * Sets the field separator text.
     *
     * <p>
     * {@code null} is accepted, but will be converted to an empty String.
     * </p>
     *
     * @param fieldSeparator the new field separator text.
     */
    protected void setFieldSeparator(final String fieldSeparator) {

    }

    /**
     * Sets whether the field separator should be added at the end of each buffer.
     *
     * @param fieldSeparatorAtEnd the fieldSeparatorAtEnd flag.
     * @since 2.0
     */
    protected void setFieldSeparatorAtEnd(final boolean fieldSeparatorAtEnd) {
        this.fieldSeparatorAtEnd = fieldSeparatorAtEnd;
    }

    /**
     * Sets whether the field separator should be added at the start of each buffer.
     *
     * @param fieldSeparatorAtStart the fieldSeparatorAtStart flag.
     * @since 2.0
     */
    protected void setFieldSeparatorAtStart(final boolean fieldSeparatorAtStart) {
        this.fieldSeparatorAtStart = fieldSeparatorAtStart;
    }

    /**
     * Sets the text to output when {@code null} found.
     *
     * <p>
     * {@code null} is accepted, but will be converted to an empty String.
     * </p>
     *
     * @param nullText the new text to output when null found.
     */
    protected void setNullText(final String nullText) {
    }

    /**
     * Sets the end text to output when a {@link Collection}, {@link Map} or array size is output.
     *
     * <p>
     * This is output after the size value.
     * </p>
     *
     * <p>
     * {@code null} is accepted, but will be converted to an empty String.
     * </p>
     *
     * @param sizeEndText the new end of size text.
     */
    protected void setSizeEndText(final String sizeEndText) {

    }

    /**
     * Sets the start text to output when a {@link Collection}, {@link Map} or array size is output.
     *
     * <p>
     * This is output before the size value.
     * </p>
     *
     * <p>
     * {@code null} is accepted, but will be converted to an empty String.
     * </p>
     *
     * @param sizeStartText the new start of size text.
     */
    protected void setSizeStartText(final String sizeStartText) {

    }

    /**
     * Sets the end text to output when an {@link Object} is output in summary mode.
     *
     * <p>
     * This is output after the size value.
     * </p>
     *
     * <p>
     * {@code null} is accepted, but will be converted to an empty String.
     * </p>
     *
     * @param summaryObjectEndText the new end of summary text.
     */
    protected void setSummaryObjectEndText(final String summaryObjectEndText) {

    }

    /**
     * Sets the start text to output when an {@link Object} is output in summary mode.
     *
     * <p>
     * This is output before the size value.
     * </p>
     *
     * <p>
     * {@code null} is accepted, but will be converted to an empty String.
     * </p>
     *
     * @param summaryObjectStartText the new start of summary text.
     */
    protected void setSummaryObjectStartText(final String summaryObjectStartText) {
    }

    /**
     * Sets whether to use the class name.
     *
     * @param useClassName the new useClassName flag.
     */
    protected void setUseClassName(final boolean useClassName) {
        this.useClassName = useClassName;
    }

    /**
     * Sets whether to use the field names passed in.
     *
     * @param useFieldNames the new useFieldNames flag.
     */
    protected void setUseFieldNames(final boolean useFieldNames) {
        this.useFieldNames = useFieldNames;
    }

    /**
     * Sets whether to use the identity hash code.
     *
     * @param useIdentityHashCode the new useIdentityHashCode flag.
     */
    protected void setUseIdentityHashCode(final boolean useIdentityHashCode) {
        this.useIdentityHashCode = useIdentityHashCode;
    }

    /**
     * Sets whether to output short or long class names.
     *
     * @param useShortClassName the new useShortClassName flag.
     * @since 2.0
     */
    protected void setUseShortClassName(final boolean useShortClassName) {
        this.useShortClassName = useShortClassName;
    }
}
