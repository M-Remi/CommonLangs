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
    public static final char DEFAULT_ESCAPE = '$';

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

}
