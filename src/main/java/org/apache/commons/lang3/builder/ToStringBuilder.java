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

import java.util.Objects;
public class ToStringBuilder implements Builder<String> {

    /**
     * The default style of output to use, not null.
     */
    private static volatile ToStringStyle defaultStyle = ToStringStyle.DEFAULT_STYLE;

    public static ToStringStyle getDefaultStyle() {
        return defaultStyle;
    }

    private final StringBuffer buffer;

    /**
     * The object being output, may be null.
     */
    private final Object object;

    /**
     * The style of output to use, not null.
     */
    private final ToStringStyle style;


    /**
     * Append to the {@code toString} a {@code byte} array.
     *
     * @param fieldName  the field name
     * @param array  the array to add to the {@code toString}
     * @return {@code this} instance.
     */
    public ToStringBuilder append(final String fieldName, final byte[] array) {
        style.append(buffer, fieldName, array, null);
        return this;
    }

    /**
     * Append to the {@code toString} a {@code byte}
     * array.
     *
     * <p>A boolean parameter controls the level of detail to show.
     * Setting {@code true} will output the array in full. Setting
     * {@code false} will output a summary, typically the size of
     * the array.
     *
     * @param fieldName  the field name
     * @param array  the array to add to the {@code toString}
     * @param fullDetail  {@code true} for detail, {@code false}
     *  for summary info
     * @return {@code this} instance.
     */
    public ToStringBuilder append(final String fieldName, final byte[] array, final boolean fullDetail) {
        style.append(buffer, fieldName, array, Boolean.valueOf(fullDetail));
        return this;
    }

    /**
     * Append to the {@code toString} a {@code char}
     * value.
     *
     * @param fieldName  the field name
     * @param value  the value to add to the {@code toString}
     * @return {@code this} instance.
     */
    public ToStringBuilder append(final String fieldName, final char value) {
        style.append(buffer, fieldName, value);
        return this;
    }

    /**
     * Append to the {@code toString} a {@code char}
     * array.
     *
     * @param fieldName  the field name
     * @param array  the array to add to the {@code toString}
     * @return {@code this} instance.
     */
    public ToStringBuilder append(final String fieldName, final char[] array) {
        style.append(buffer, fieldName, array, null);
        return this;
    }

    /**
     * Append to the {@code toString} a {@code char}
     * array.
     *
     * <p>A boolean parameter controls the level of detail to show.
     * Setting {@code true} will output the array in full. Setting
     * {@code false} will output a summary, typically the size of
     * the array.</p>
     *
     * @param fieldName  the field name
     * @param array  the array to add to the {@code toString}
     * @param fullDetail  {@code true} for detail, {@code false}
     *  for summary info
     * @return {@code this} instance.
     */
    public ToStringBuilder append(final String fieldName, final char[] array, final boolean fullDetail) {
        style.append(buffer, fieldName, array, Boolean.valueOf(fullDetail));
        return this;
    }

    /**
     * Append to the {@code toString} a {@code double}
     * value.
     *
     * @param fieldName  the field name
     * @param value  the value to add to the {@code toString}
     * @return {@code this} instance.
     */
    public ToStringBuilder append(final String fieldName, final double value) {
        style.append(buffer, fieldName, value);
        return this;
    }

    /**
     * Append to the {@code toString} a {@code double}
     * array.
     *
     * @param fieldName  the field name
     * @param array  the array to add to the {@code toString}
     * @return {@code this} instance.
     */


    public ToStringBuilder appendSuper(final String superToString) {
        if (superToString != null) {
            style.appendSuper(buffer, superToString);
        }
        return this;
    }

    public ToStringBuilder appendToString(final String toString) {
        if (toString != null) {
            style.appendToString(buffer, toString);
        }
        return this;
    }

    @Override
    public String build() {
        return toString();
    }

    public StringBuffer getStringBuffer() {
        return buffer;
    }
    @Override
    public String toString() {
        if (getObject() == null) {
            getStringBuffer().append(getStyle().getNullText());
        } else {
            style.appendEnd(getStringBuffer(), getObject());
        }
        return getStringBuffer().toString();
    }
}
