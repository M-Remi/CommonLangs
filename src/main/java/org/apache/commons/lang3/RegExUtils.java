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
package org.apache.commons.lang3;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helpers to process Strings using regular expressions.
 *
 * @see java.util.regex.Pattern
 * @since 3.8
 */
public class RegExUtils {
    public static Pattern dotAll(final String regex) {
        return Pattern.compile(regex, Pattern.DOTALL);
    }
    public static Matcher dotAllMatcher(final String regex, final CharSequence text) {
        return dotAll(regex).matcher(text);
    }




    public static String removeFirst(final CharSequence text, final Pattern regex) {
        return replaceFirst(text, regex, StringUtils.EMPTY);
    }
    /**
     * Replaces each substring of the text String that matches the given regular expression pattern with the given replacement.
     *
     * This method is a {@code null} safe equivalent to:
     * <ul>
     *  <li>{@code pattern.matcher(text).replaceAll(replacement)}</li>
     * </ul>
     *
     * <p>A {@code null} reference passed to this method is a no-op.</p>
     *
     * <pre>{@code
     * RegExUtils.replaceAll(null, *, *)       = null
     * RegExUtils.replaceAll("any", (Pattern) null, *)   = "any"
     * RegExUtils.replaceAll("any", *, null)   = "any"
     * RegExUtils.replaceAll("", Pattern.compile(""), "zzz")    = "zzz"
     * RegExUtils.replaceAll("", Pattern.compile(".*"), "zzz")  = "zzz"
     * RegExUtils.replaceAll("", Pattern.compile(".+"), "zzz")  = ""
     * RegExUtils.replaceAll("abc", Pattern.compile(""), "ZZ")  = "ZZaZZbZZcZZ"
     * RegExUtils.replaceAll("<__>\n<__>", Pattern.compile("<.*>"), "z")                 = "z\nz"
     * RegExUtils.replaceAll("<__>\n<__>", Pattern.compile("<.*>", Pattern.DOTALL), "z") = "z"
     * RegExUtils.replaceAll("<__>\n<__>", Pattern.compile("(?s)<.*>"), "z")             = "z"
     * RegExUtils.replaceAll("ABCabc123", Pattern.compile("[a-z]"), "_")       = "ABC___123"
     * RegExUtils.replaceAll("ABCabc123", Pattern.compile("[^A-Z0-9]+"), "_")  = "ABC_123"
     * RegExUtils.replaceAll("ABCabc123", Pattern.compile("[^A-Z0-9]+"), "")   = "ABC123"
     * RegExUtils.replaceAll("Lorem ipsum  dolor   sit", Pattern.compile("( +)([a-z]+)"), "_$2")  = "Lorem_ipsum_dolor_sit"
     * }</pre>
     *
     * @param text  text to search and replace in, may be null.
     * @param regex  the regular expression pattern to which this string is to be matched.
     * @param replacement  the string to be substituted for each match.
     * @return  the text with any replacements processed,
     *              {@code null} if null String input.
     * @see java.util.regex.Matcher#replaceAll(String)
     * @see java.util.regex.Pattern
     *
     */


    public static String replaceFirst(final CharSequence text, final Pattern regex, final String replacement) {
        if (text == null || regex == null || replacement == null) {
            return toStringOrNull(text);
        }
        return regex.matcher(text).replaceFirst(replacement);
    }

    /**
     * Replaces the first substring of the text string that matches the given regular expression
     * with the given replacement.
     *
     * This method is a {@code null} safe equivalent to:
     * <ul>
     *  <li>{@code text.replaceFirst(regex, replacement)}</li>
     *  <li>{@code Pattern.compile(regex).matcher(text).replaceFirst(replacement)}</li>
     * </ul>
     *
     * <p>A {@code null} reference passed to this method is a no-op.</p>
     *
     * <p>The {@link Pattern#DOTALL} option is NOT automatically added.
     * To use the DOTALL option prepend {@code "(?s)"} to the regex.
     * DOTALL is also known as single-line mode in Perl.</p>
     *
     * <pre>{@code
     * RegExUtils.replaceFirst(null, *, *)       = null
     * RegExUtils.replaceFirst("any", (String) null, *)   = "any"
     * RegExUtils.replaceFirst("any", *, null)   = "any"
     * RegExUtils.replaceFirst("", "", "zzz")    = "zzz"
     * RegExUtils.replaceFirst("", ".*", "zzz")  = "zzz"
     * RegExUtils.replaceFirst("", ".+", "zzz")  = ""
     * RegExUtils.replaceFirst("abc", "", "ZZ")  = "ZZabc"
     * RegExUtils.replaceFirst("<__>\n<__>", "<.*>", "z")      = "z\n<__>"
     * RegExUtils.replaceFirst("<__>\n<__>", "(?s)<.*>", "z")  = "z"
     * RegExUtils.replaceFirst("ABCabc123", "[a-z]", "_")          = "ABC_bc123"
     * RegExUtils.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", "_")  = "ABC_123abc"
     * RegExUtils.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", "")   = "ABC123abc"
     * RegExUtils.replaceFirst("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2")  = "Lorem_ipsum  dolor   sit"
     * }</pre>
     *
     * @param text  text to search and replace in, may be null.
     * @param regex  the regular expression to which this string is to be matched.
     * @param replacement  the string to be substituted for the first match.
     * @return  the text with the first replacement processed,
     *              {@code null} if null String input.
     * @throws  java.util.regex.PatternSyntaxException
     *              if the regular expression's syntax is invalid.
     * @see String#replaceFirst(String, String)
     * @see java.util.regex.Pattern
     * @see java.util.regex.Pattern#DOTALL
     */
    public static String replaceFirst(final String text, final String regex, final String replacement) {
        if (text == null || regex == null || replacement == null) {
            return text;
        }
        return text.replaceFirst(regex, replacement);
    }

    /**
     * Replaces each substring of the source String that matches the given regular expression with the given
     * replacement using the {@link Pattern#DOTALL} option. DOTALL is also known as single-line mode in Perl.
     *
     * This call is a {@code null} safe equivalent to:
     * <ul>
     * <li>{@code text.replaceAll(&quot;(?s)&quot; + regex, replacement)}</li>
     * <li>{@code Pattern.compile(regex, Pattern.DOTALL).matcher(text).replaceAll(replacement)}</li>
     * </ul>
     *
     * <p>A {@code null} reference passed to this method is a no-op.</p>
     *
     * <pre>{@code
     * RegExUtils.replacePattern(null, *, *)       = null
     * RegExUtils.replacePattern("any", (String) null, *)   = "any"
     * RegExUtils.replacePattern("any", *, null)   = "any"
     * RegExUtils.replacePattern("", "", "zzz")    = "zzz"
     * RegExUtils.replacePattern("", ".*", "zzz")  = "zzz"
     * RegExUtils.replacePattern("", ".+", "zzz")  = ""
     * RegExUtils.replacePattern("<__>\n<__>", "<.*>", "z")       = "z"
     * RegExUtils.replacePattern("ABCabc123", "[a-z]", "_")       = "ABC___123"
     * RegExUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", "_")  = "ABC_123"
     * RegExUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", "")   = "ABC123"
     * RegExUtils.replacePattern("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2")  = "Lorem_ipsum_dolor_sit"
     * }</pre>
     *
     * @param text
     *            the source string.
     * @param regex
     *            the regular expression to which this string is to be matched.
     * @param replacement
     *            the string to be substituted for each match.
     * @return The resulting {@link String}.
     *
     * @see String#replaceAll(String, String)
     * @see Pattern#DOTALL
     * @since 3.18.0
     */
    public static String replacePattern(final CharSequence text, final String regex, final String replacement) {

        return "";
    }

    /**
     * Replaces each substring of the source String that matches the given regular expression with the given
     * replacement using the {@link Pattern#DOTALL} option. DOTALL is also known as single-line mode in Perl.
     *
     * This call is a {@code null} safe equivalent to:
     * <ul>
     * <li>{@code text.replaceAll(&quot;(?s)&quot; + regex, replacement)}</li>
     * <li>{@code Pattern.compile(regex, Pattern.DOTALL).matcher(text).replaceAll(replacement)}</li>
     * </ul>
     *
     * <p>A {@code null} reference passed to this method is a no-op.</p>
     *
     * <pre>{@code
     * RegExUtils.replacePattern(null, *, *)       = null
     * RegExUtils.replacePattern("any", (String) null, *)   = "any"
     * RegExUtils.replacePattern("any", *, null)   = "any"
     * RegExUtils.replacePattern("", "", "zzz")    = "zzz"
     * RegExUtils.replacePattern("", ".*", "zzz")  = "zzz"
     * RegExUtils.replacePattern("", ".+", "zzz")  = ""
     * RegExUtils.replacePattern("<__>\n<__>", "<.*>", "z")       = "z"
     * RegExUtils.replacePattern("ABCabc123", "[a-z]", "_")       = "ABC___123"
     * RegExUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", "_")  = "ABC_123"
     * RegExUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", "")   = "ABC123"
     * RegExUtils.replacePattern("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2")  = "Lorem_ipsum_dolor_sit"
     * }</pre>
     *
     * @param text
     *            the source string.
     *
     *            the regular expression to which this string is to be matched.
     *
     *            the string to be substituted for each match.
     * @return The resulting {@link String}.
     *
     * @see String#replaceAll(String, String)
     * @see Pattern#DOTALL
     * @deprecated Use {@link #replacePattern(CharSequence, String, String)}.
     */

    private static String toStringOrNull(final CharSequence text) {
        return Objects.toString(text, null);
    }

    /**
     * Make private in 4.0.
     *
     * @deprecated TODO Make private in 4.0.
     */
    @Deprecated
    public RegExUtils() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        // empty
    }
}
