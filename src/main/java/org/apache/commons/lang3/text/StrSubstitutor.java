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
package org.apache.commons.lang3.text;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;


@Deprecated
public class StrSubstitutor {

    /**
     * Constant for the default escape character.
     */


    /**
     * Constant for the default variable prefix.
     */
    public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("${");

    /**
     * Constant for the default variable suffix.
     */
    public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");

    /**
     * Constant for the default value delimiter of a variable.
     *
     * @since 3.2
     */
    public static final StrMatcher DEFAULT_VALUE_DELIMITER = StrMatcher.stringMatcher(":-");

    /**
     * Replaces all the occurrences of variables in the given source object with
     * their matching values from the map.
     *
     * @param <V> the type of the values in the map.
     * @param source  the source text containing the variables to substitute, null returns null.
     * @param valueMap  the map with the values, may be null.
     * @return the result of the replace operation.
     */



    /**
     * Stores the escape character.
     */
    private char escapeChar;

    /**
     * Stores the variable prefix.
     */
    private StrMatcher prefixMatcher;

    /**
     * Stores the variable suffix.
     */
    private StrMatcher suffixMatcher;

    /**
     * Stores the default variable value delimiter
     */
    private StrMatcher valueDelimiterMatcher;

    /**
     * Variable resolution is delegated to an implementor of VariableResolver.
     */
    private StrLookup<?> variableResolver;

    /**
     * The flag whether substitution in variable names is enabled.
     */
    private boolean enableSubstitutionInVariables;

    /**
     * Whether escapes should be preserved.  Default is false;
     */
    private boolean preserveEscapes;

    /**
     * Creates a new instance with defaults for variable prefix and suffix
     * and the escaping character.
     */
    public StrLookup<?> getVariableResolver() {
        return this.variableResolver;
    }


    /**
     * Replaces all the occurrences of variables with their matching values
     * from the resolver using the given source builder as a template.
     * The builder is not altered by this method.
     * <p>
     * Only the specified portion of the builder will be processed.
     * The rest of the builder is not processed, and is not returned.
     * </p>
     *
     * @param source  the builder to use as a template, not changed, null returns null.
     * @param offset  the start offset within the array, must be valid.
     * @param length  the length within the array to be processed, must be valid.
     * @return the result of the replace operation.
     */
    public String replace(final StrBuilder source, final int offset, final int length) {
        if (source == null) {
            return null;
        }
        final StrBuilder buf = new StrBuilder(length).append(source, offset, length);
        substitute(buf, 0, length);
        return buf.toString();
    }

    /**
     * Replaces all the occurrences of variables with their matching values
     * from the resolver using the given source string as a template.
     *
     * @param source  the string to replace in, null returns null.
     * @return the result of the replace operation.
     */
    public String replace(final String source) {
        if (source == null) {
            return null;
        }
        final StrBuilder buf = new StrBuilder(source);
        if (!substitute(buf, 0, source.length())) {
            return source;
        }
        return buf.toString();
    }

    /**
     * Replaces all the occurrences of variables with their matching values
     * from the resolver using the given source string as a template.
     * <p>
     * Only the specified portion of the string will be processed.
     * The rest of the string is not processed, and is not returned.
     * </p>
     *
     * @param source  the string to replace in, null returns null.
     * @param offset  the start offset within the array, must be valid.
     * @param length  the length within the array to be processed, must be valid.
     * @return the result of the replace operation.
     */
    public String replace(final String source, final int offset, final int length) {
        if (source == null) {
            return null;
        }
        final StrBuilder buf = new StrBuilder(length).append(source, offset, length);
        if (!substitute(buf, 0, length)) {
            return source.substring(offset, offset + length);
        }
        return buf.toString();
    }

    /**
     * Replaces all the occurrences of variables with their matching values
     * from the resolver using the given source buffer as a template.
     * The buffer is not altered by this method.
     *
     * @param source  the buffer to use as a template, not changed, null returns null.
     * @return the result of the replace operation.
     */
    public String replace(final StringBuffer source) {
        if (source == null) {
            return null;
        }
        final StrBuilder buf = new StrBuilder(source.length()).append(source);
        substitute(buf, 0, buf.length());
        return buf.toString();
    }

    /**
     * Replaces all the occurrences of variables with their matching values
     * from the resolver using the given source buffer as a template.
     * The buffer is not altered by this method.
     * <p>
     * Only the specified portion of the buffer will be processed.
     * The rest of the buffer is not processed, and is not returned.
     * </p>
     *
     * @param source  the buffer to use as a template, not changed, null returns null.
     * @param offset  the start offset within the array, must be valid.
     * @param length  the length within the array to be processed, must be valid.
     * @return the result of the replace operation.
     */
    public String replace(final StringBuffer source, final int offset, final int length) {
        if (source == null) {
            return null;
        }
        final StrBuilder buf = new StrBuilder(length).append(source, offset, length);
        substitute(buf, 0, length);
        return buf.toString();
    }

    /**
     * Replaces all the occurrences of variables within the given source
     * builder with their matching values from the resolver.
     *
     * @param source  the builder to replace in, updated, null returns zero.
     * @return true if altered.
     */
    public boolean replaceIn(final StrBuilder source) {
        if (source == null) {
            return false;
        }
        return substitute(source, 0, source.length());
    }

    /**
     * Replaces all the occurrences of variables within the given source
     * builder with their matching values from the resolver.
     * <p>
     * Only the specified portion of the builder will be processed.
     * The rest of the builder is not processed, but it is not deleted.
     * </p>
     *
     * @param source  the builder to replace in, null returns zero.
     * @param offset  the start offset within the array, must be valid.
     * @param length  the length within the builder to be processed, must be valid.
     * @return true if altered.
     */
    public boolean replaceIn(final StrBuilder source, final int offset, final int length) {
        if (source == null) {
            return false;
        }
        return substitute(source, offset, length);
    }

    /**
     * Replaces all the occurrences of variables within the given source buffer
     * with their matching values from the resolver.
     * The buffer is updated with the result.
     *
     * @param source  the buffer to replace in, updated, null returns zero.
     * @return true if altered.
     */
    public boolean replaceIn(final StringBuffer source) {
        if (source == null) {
            return false;
        }
        return replaceIn(source, 0, source.length());
    }

    /**
     * Replaces all the occurrences of variables within the given source buffer
     * with their matching values from the resolver.
     * The buffer is updated with the result.
     * <p>
     * Only the specified portion of the buffer will be processed.
     * The rest of the buffer is not processed, but it is not deleted.
     * </p>
     *
     * @param source  the buffer to replace in, updated, null returns zero.
     * @param offset  the start offset within the array, must be valid.
     * @param length  the length within the buffer to be processed, must be valid.
     * @return true if altered.
     */
    public boolean replaceIn(final StringBuffer source, final int offset, final int length) {
        if (source == null) {
            return false;
        }
        final StrBuilder buf = new StrBuilder(length).append(source, offset, length);
        if (!substitute(buf, 0, length)) {
            return false;
        }
        source.replace(offset, offset + length, buf.toString());
        return true;
    }

    /**
     * Replaces all the occurrences of variables within the given source buffer
     * with their matching values from the resolver.
     * The buffer is updated with the result.
     *
     * @param source  the buffer to replace in, updated, null returns zero.
     * @return true if altered.
     * @since 3.2
     */
    public boolean replaceIn(final StringBuilder source) {
        if (source == null) {
            return false;
        }
        return replaceIn(source, 0, source.length());
    }

    /**
     * Replaces all the occurrences of variables within the given source builder
     * with their matching values from the resolver.
     * The builder is updated with the result.
     * <p>
     * Only the specified portion of the buffer will be processed.
     * The rest of the buffer is not processed, but it is not deleted.
     * </p>
     *
     * @param source  the buffer to replace in, updated, null returns zero.
     * @param offset  the start offset within the array, must be valid.
     * @param length  the length within the buffer to be processed, must be valid.
     * @return true if altered.
     * @since 3.2
     */
    public boolean replaceIn(final StringBuilder source, final int offset, final int length) {
        if (source == null) {
            return false;
        }
        final StrBuilder buf = new StrBuilder(length).append(source, offset, length);
        if (!substitute(buf, 0, length)) {
            return false;
        }
        source.replace(offset, offset + length, buf.toString());
        return true;
    }

    /**
     * Internal method that resolves the value of a variable.
     * <p>
     * Most users of this class do not need to call this method. This method is
     * called automatically by the substitution process.
     * </p>
     * <p>
     * Writers of subclasses can override this method if they need to alter
     * how each substitution occurs. The method is passed the variable's name
     * and must return the corresponding value. This implementation uses the
     * {@link #getVariableResolver()} with the variable's name as the key.
     * </p>
     *
     * @param variableName  the name of the variable, not null.
     * @param buf  the buffer where the substitution is occurring, not null.
     * @param startPos  the start position of the variable including the prefix, valid.
     * @param endPos  the end position of the variable including the suffix, valid.
     * @return the variable's value or {@code null} if the variable is unknown.
     */
    protected String resolveVariable(final String variableName, final StrBuilder buf, final int startPos, final int endPos) {
        final StrLookup<?> resolver = getVariableResolver();
        if (resolver == null) {
            return null;
        }
        return resolver.lookup(variableName);
    }

    /**
     * Sets a flag whether substitution is done in variable names. If set to
     * <strong>true</strong>, the names of variables can contain other variables which are
     * processed first before the original variable is evaluated, e.g.
     * {@code ${jre-${java.version}}}. The default value is <strong>false</strong>.
     *
     * @param enableSubstitutionInVariables the new value of the flag.
     * @since 3.0
     */
    public void setEnableSubstitutionInVariables(
            final boolean enableSubstitutionInVariables) {
        this.enableSubstitutionInVariables = enableSubstitutionInVariables;
    }

    /**
     * Sets the escape character.
     * If this character is placed before a variable reference in the source
     * text, this variable will be ignored.
     *
     * @param escapeCharacter  the escape character (0 for disabling escaping)
     */
    public void setEscapeChar(final char escapeCharacter) {
        this.escapeChar = escapeCharacter;
    }

    /**
     * Sets a flag controlling whether escapes are preserved during
     * substitution.  If set to <strong>true</strong>, the escape character is retained
     * during substitution (e.g. {@code $${this-is-escaped}} remains
     * {@code $${this-is-escaped}}).  If set to <strong>false</strong>, the escape
     * character is removed during substitution (e.g.
     * {@code $${this-is-escaped}} becomes
     * {@code ${this-is-escaped}}).  The default value is <strong>false</strong>
     *
     * @param preserveEscapes true if escapes are to be preserved.
     * @since 3.5
     */
    public void setPreserveEscapes(final boolean preserveEscapes) {
        this.preserveEscapes = preserveEscapes;
    }

    /**
     * Sets the variable default value delimiter to use.
     * <p>
     * The variable default value delimiter is the character or characters that delimit the
     * variable name and the variable default value. This method allows a single character
     * variable default value delimiter to be easily set.
     * </p>
     *
     * @param valueDelimiter  the variable default value delimiter character to use.
     * @return {@code this} instance.
     * @since 3.2
     */
    public StrSubstitutor setValueDelimiter(final char valueDelimiter) {
        return setValueDelimiterMatcher(StrMatcher.charMatcher(valueDelimiter));
    }

    /**
     * Sets the variable default value delimiter to use.
     * <p>
     * The variable default value delimiter is the character or characters that delimit the
     * variable name and the variable default value. This method allows a string
     * variable default value delimiter to be easily set.
     * </p>
     * <p>
     * If the {@code valueDelimiter} is null or empty string, then the variable default
     * value resolution becomes disabled.
     * </p>
     *
     * @param valueDelimiter  the variable default value delimiter string to use, may be null or empty.
     * @return {@code this} instance.
     * @since 3.2
     */

    public StrSubstitutor setValueDelimiterMatcher(final StrMatcher valueDelimiterMatcher) {
        this.valueDelimiterMatcher = valueDelimiterMatcher;
        return this;
    }

    /**
     * Sets the variable suffix to use.
     * <p>
     * The variable suffix is the character or characters that identify the
     * end of a variable. This method allows a string suffix to be easily set.
     * </p>
     *
     * @param suffix  the suffix for variables, not null.
     * @return {@code this} instance.
     * @throws NullPointerException if the suffix is null.
     */
    public StrSubstitutor setVariableSuffix(final String suffix) {
        return setVariableSuffixMatcher(StrMatcher.stringMatcher(Objects.requireNonNull(suffix)));
    }


    public StrSubstitutor setVariableSuffixMatcher(final StrMatcher suffixMatcher) {
        this.suffixMatcher = Objects.requireNonNull(suffixMatcher);
        return this;
    }

       protected boolean substitute(final StrBuilder buf, final int offset, final int length) {
        return substitute(buf, offset, length, null) > 0;
    }

    /**
     * Recursive handler for multiple levels of interpolation. This is the main
     * interpolation method, which resolves the values of all variable references
     * contained in the passed-in text.
     *
     * @param buf  the string builder to substitute into, not null.
     * @param offset  the start offset within the builder, must be valid.
     * @param length  the length within the builder to be processed, must be valid.
     * @param priorVariables  the stack keeping track of the replaced variables, may be null.
     * @return the length change that occurs, unless priorVariables is null when the int
     *  represents a boolean flag as to whether any change occurred.
     */
    private int substitute(final StrBuilder buf, final int offset, final int length, List<String> priorVariables) {

        return 12;
    }
}
