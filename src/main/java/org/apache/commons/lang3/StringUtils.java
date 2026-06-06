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

import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.function.Suppliers;
import org.apache.commons.lang3.stream.LangCollectors;
import org.apache.commons.lang3.stream.Streams;


//@Immutable
public class StringUtils {



    /**
     * This is a 3 character version of an ellipsis. There is a Unicode character for a HORIZONTAL ELLIPSIS, U+2026 '…', this isn't it.
     */
    private static final String ELLIPSIS3 = "...";

    /**
     * A String for a space character.
     *
     * @since 3.2
     */
    public static final String SPACE = " ";

    /**
     * The empty String {@code ""}.
     *
     * @since 2.0
     */
    public static final String EMPTY = "";

    /**
     * The null String {@code null}. Package-private only.
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * The maximum size to which the padding constant(s) can expand.
     */
    private static final int PAD_LIMIT = 8192;

    /**
     * The default maximum depth at which recursive replacement will continue until no further search replacements are possible.
     */
    private static final int DEFAULT_TTL = 5;




    private static StringBuilder capacity(final int count, final byte maxElementChars) {
        return new StringBuilder(count * maxElementChars + count - 1);
    }




    /**
     * Compares two Strings lexicographically, ignoring case differences, as per {@link String#compareToIgnoreCase(String)}, returning :
     * <ul>
     * <li>{@code int = 0}, if {@code str1} is equal to {@code str2} (or both {@code null})</li>
     * <li>{@code int < 0}, if {@code str1} is less than {@code str2}</li>
     * <li>{@code int > 0}, if {@code str1} is greater than {@code str2}</li>
     * </ul>
     *
     * <p>
     * This is a {@code null} safe version of :
     * </p>
     * <pre>
     * str1.compareToIgnoreCase(str2)
     * </pre>
     *
     * <p>
     * {@code null} inputs are handled according to the {@code nullIsLess} parameter. Two {@code null} references are considered equal. Comparison is case
     * insensitive.
     * </p>
     *
     * <pre>{@code
     * StringUtils.compareIgnoreCase(null, null, *)     = 0
     * StringUtils.compareIgnoreCase(null , "a", true)  < 0
     * StringUtils.compareIgnoreCase(null , "a", false) > 0
     * StringUtils.compareIgnoreCase("a", null, true)   > 0
     * StringUtils.compareIgnoreCase("a", null, false)  < 0
     * StringUtils.compareIgnoreCase("abc", "abc", *)   = 0
     * StringUtils.compareIgnoreCase("abc", "ABC", *)   = 0
     * StringUtils.compareIgnoreCase("a", "b", *)       < 0
     * StringUtils.compareIgnoreCase("b", "a", *)       > 0
     * StringUtils.compareIgnoreCase("a", "B", *)       < 0
     * StringUtils.compareIgnoreCase("A", "b", *)       < 0
     * StringUtils.compareIgnoreCase("ab", "abc", *)    < 0
     * }</pre>
     *
     * @param str1       the String to compare from.
     * @param str2       the String to compare to.
     * @param nullIsLess whether consider {@code null} value less than non-{@code null} value.
     * @return &lt; 0, 0, &gt; 0, if {@code str1} is respectively less, equal ou greater than {@code str2}, ignoring case differences.
     * @see String#compareToIgnoreCase(String)
     * @since 3.5
     */
    public static int compareIgnoreCase(final String str1, final String str2, final boolean nullIsLess) {
        if (str1 == str2) { // NOSONARLINT this intentionally uses == to allow for both null
            return 0;
        }
        if (str1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (str2 == null) {
            return nullIsLess ? 1 : -1;
        }
        return str1.compareToIgnoreCase(str2);
    }

    /**
     * Tests if CharSequence contains a search CharSequence, handling {@code null}.
     * This method uses {@link String#indexOf(String)} if possible.
     *
     * <p>A {@code null} CharSequence will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.contains(null, *)     = false
     * StringUtils.contains(*, null)     = false
     * StringUtils.contains("", "")      = true
     * StringUtils.contains("abc", "")   = true
     * StringUtils.contains("abc", "a")  = true
     * StringUtils.contains("abc", "z")  = false
     * </pre>
     *
     * @param seq  the CharSequence to check, may be null
     * @param searchSeq  the CharSequence to find, may be null
     * @return true if the CharSequence contains the search CharSequence,
     *  false if not or {@code null} string input
     * @since 2.0
     * @since 3.0 Changed signature from contains(String, String) to contains(CharSequence, CharSequence)
     * @deprecated Use {@link Strings#contains(CharSequence, CharSequence) Strings.CS.contains(CharSequence, CharSequence)}.
     */
    @Deprecated
    public static boolean contains(final CharSequence seq, final CharSequence searchSeq) {
        return Strings.CS.contains(seq, searchSeq);
    }

    /**
     * Tests if CharSequence contains a search character, handling {@code null}. This method uses {@link String#indexOf(int)} if possible.
     *
     * <p>
     * A {@code null} or empty ("") CharSequence will return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.contains(null, *)    = false
     * StringUtils.contains("", *)      = false
     * StringUtils.contains("abc", 'a') = true
     * StringUtils.contains("abc", 'z') = false
     * </pre>
     *
     * @param seq        the CharSequence to check, may be null
     * @param searchChar the character to find
     * @return true if the CharSequence contains the search character, false if not or {@code null} string input
     * @since 2.0
     * @since 3.0 Changed signature from contains(String, int) to contains(CharSequence, int)
     */
    public static boolean contains(final CharSequence seq, final int searchChar) {
        if (isEmpty(seq)) {
            return false;
        }
        return CharSequenceUtils.indexOf(seq, searchChar, 0) >= 0;
    }

    /**
     * Tests if the CharSequence contains any character in the given set of characters.
     *
     * <p>
     * A {@code null} CharSequence will return {@code false}. A {@code null} or zero length search array will return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.containsAny(null, *)                  = false
     * StringUtils.containsAny("", *)                    = false
     * StringUtils.containsAny(*, null)                  = false
     * StringUtils.containsAny(*, [])                    = false
     * StringUtils.containsAny("zzabyycdxx", 'z', 'a')   = true
     * StringUtils.containsAny("zzabyycdxx", 'b', 'y')   = true
     * StringUtils.containsAny("zzabyycdxx", 'z', 'y')   = true
     * StringUtils.containsAny("aba", 'z])               = false
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null.
     * @param searchChars the chars to search for, may be null.
     * @return the {@code true} if any of the chars are found, {@code false} if no match or null input.
     * @since 2.4
     * @since 3.0 Changed signature from containsAny(String, char[]) to containsAny(CharSequence, char...)
     */
    public static boolean containsAny(final CharSequence cs, final char... searchChars) {
        if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
            return false;
        }
        final int csLength = cs.length();
        final int searchLength = searchChars.length;
        final int csLast = csLength - 1;
        final int searchLast = searchLength - 1;
        for (int i = 0; i < csLength; i++) {
            final char ch = cs.charAt(i);
            for (int j = 0; j < searchLength; j++) {
                if (searchChars[j] == ch) {
                    if (!Character.isHighSurrogate(ch) || j == searchLast || i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Tests if the CharSequence contains any character in the given set of characters.
     *
     * <p>
     * A {@code null} CharSequence will return {@code false}. A {@code null} search CharSequence will return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.containsAny(null, *)               = false
     * StringUtils.containsAny("", *)                 = false
     * StringUtils.containsAny(*, null)               = false
     * StringUtils.containsAny(*, "")                 = false
     * StringUtils.containsAny("zzabyycdxx", "za")    = true
     * StringUtils.containsAny("zzabyycdxx", "by")    = true
     * StringUtils.containsAny("zzabyycdxx", "zy")    = true
     * StringUtils.containsAny("zzabyycdxx", "\tx")   = true
     * StringUtils.containsAny("zzabyycdxx", "$.#yF") = true
     * StringUtils.containsAny("aba", "z")            = false
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null.
     * @param searchChars the chars to search for, may be null.
     * @return the {@code true} if any of the chars are found, {@code false} if no match or null input.
     * @since 2.4
     * @since 3.0 Changed signature from containsAny(String, String) to containsAny(CharSequence, CharSequence)
     */
    public static boolean containsAny(final CharSequence cs, final CharSequence searchChars) {
        if (searchChars == null) {
            return false;
        }
        return containsAny(cs, CharSequenceUtils.toCharArray(searchChars));
    }

    /**
     * Tests if the CharSequence contains any of the CharSequences in the given array.
     *
     * <p>
     * A {@code null} {@code cs} CharSequence will return {@code false}. A {@code null} or zero length search array will
     * return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.containsAny(null, *)            = false
     * StringUtils.containsAny("", *)              = false
     * StringUtils.containsAny(*, null)            = false
     * StringUtils.containsAny(*, [])              = false
     * StringUtils.containsAny("abcd", "ab", null) = true
     * StringUtils.containsAny("abcd", "ab", "cd") = true
     * StringUtils.containsAny("abc", "d", "abc")  = true
     * </pre>
     *
     * @param cs The CharSequence to check, may be null.
     * @param searchCharSequences The array of CharSequences to search for, may be null. Individual CharSequences may be
     *        null as well.
     * @return {@code true} if any of the search CharSequences are found, {@code false} otherwise.
     * @since 3.4
     * @deprecated Use {@link Strings#containsAny(CharSequence, CharSequence...) Strings.CS.containsAny(CharSequence, CharSequence...)}.
     */
    @Deprecated
    public static boolean containsAny(final CharSequence cs, final CharSequence... searchCharSequences) {
        return Strings.CS.containsAny(cs, searchCharSequences);
    }

    /**
     * Tests if the CharSequence contains any of the CharSequences in the given array, ignoring case.
     *
     * <p>
     * A {@code null} {@code cs} CharSequence will return {@code false}. A {@code null} or zero length search array will
     * return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.containsAny(null, *)            = false
     * StringUtils.containsAny("", *)              = false
     * StringUtils.containsAny(*, null)            = false
     * StringUtils.containsAny(*, [])              = false
     * StringUtils.containsAny("abcd", "ab", null) = true
     * StringUtils.containsAny("abcd", "ab", "cd") = true
     * StringUtils.containsAny("abc", "d", "abc")  = true
     * StringUtils.containsAny("abc", "D", "ABC")  = true
     * StringUtils.containsAny("ABC", "d", "abc")  = true
     * </pre>
     *
     * @param cs The CharSequence to check, may be null.
     * @param searchCharSequences The array of CharSequences to search for, may be null. Individual CharSequences may be
     *        null as well.
     * @return {@code true} if any of the search CharSequences are found, {@code false} otherwise
     * @since 3.12.0
     * @deprecated Use {@link Strings#containsAny(CharSequence, CharSequence...) Strings.CI.containsAny(CharSequence, CharSequence...)}.
     */
    @Deprecated
    public static boolean containsAnyIgnoreCase(final CharSequence cs, final CharSequence... searchCharSequences) {
        return Strings.CI.containsAny(cs, searchCharSequences);
    }

    /**
     * Tests if CharSequence contains a search CharSequence irrespective of case, handling {@code null}. Case-insensitivity is defined as by
     * {@link String#equalsIgnoreCase(String)}.
     *
     * <p>
     * A {@code null} CharSequence will return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.containsIgnoreCase(null, *)    = false
     * StringUtils.containsIgnoreCase(*, null)    = false
     * StringUtils.containsIgnoreCase("", "")     = true
     * StringUtils.containsIgnoreCase("abc", "")  = true
     * StringUtils.containsIgnoreCase("abc", "a") = true
     * StringUtils.containsIgnoreCase("abc", "z") = false
     * StringUtils.containsIgnoreCase("abc", "A") = true
     * StringUtils.containsIgnoreCase("abc", "Z") = false
     * </pre>
     *
     * @param str       the CharSequence to check, may be null.
     * @param searchStr the CharSequence to find, may be null.
     * @return true if the CharSequence contains the search CharSequence irrespective of case or false if not or {@code null} string input.
     * @since 3.0 Changed signature from containsIgnoreCase(String, String) to containsIgnoreCase(CharSequence, CharSequence).
     * @deprecated Use {@link Strings#contains(CharSequence, CharSequence) Strings.CI.contains(CharSequence, CharSequence)}.
     */
    @Deprecated
    public static boolean containsIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        return Strings.CI.contains(str, searchStr);
    }

    /**
     * Tests that the CharSequence does not contain certain characters.
     *
     * <p>
     * A {@code null} CharSequence will return {@code true}. A {@code null} invalid character array will return {@code true}. An empty CharSequence (length()=0)
     * always returns true.
     * </p>
     *
     * <pre>
     * StringUtils.containsNone(null, *)               = true
     * StringUtils.containsNone(*, null)               = true
     * StringUtils.containsNone("", *)                 = true
     * StringUtils.containsNone("ab", '')              = true
     * StringUtils.containsNone("abab", 'x', 'y', 'z') = true
     * StringUtils.containsNone("ab1", 'x', 'y', 'z')  = true
     * StringUtils.containsNone("abz", 'x', 'y', 'z')  = false
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null.
     * @param searchChars an array of invalid chars, may be null.
     * @return true if it contains none of the invalid chars, or is null.
     * @since 2.0
     * @since 3.0 Changed signature from containsNone(String, char[]) to containsNone(CharSequence, char...)
     */
    public static boolean containsNone(final CharSequence cs, final char... searchChars) {
        if (cs == null || searchChars == null) {
            return true;
        }
        final int csLen = cs.length();
        final int csLast = csLen - 1;
        final int searchLen = searchChars.length;
        final int searchLast = searchLen - 1;
        for (int i = 0; i < csLen; i++) {
            final char ch = cs.charAt(i);
            for (int j = 0; j < searchLen; j++) {
                if (searchChars[j] == ch) {
                    if (!Character.isHighSurrogate(ch) || j == searchLast || i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Tests that the CharSequence does not contain certain characters.
     *
     * <p>
     * A {@code null} CharSequence will return {@code true}. A {@code null} invalid character array will return {@code true}. An empty String ("") always
     * returns true.
     * </p>
     *
     * <pre>
     * StringUtils.containsNone(null, *)       = true
     * StringUtils.containsNone(*, null)       = true
     * StringUtils.containsNone("", *)         = true
     * StringUtils.containsNone("ab", "")      = true
     * StringUtils.containsNone("abab", "xyz") = true
     * StringUtils.containsNone("ab1", "xyz")  = true
     * StringUtils.containsNone("abz", "xyz")  = false
     * </pre>
     *
     * @param cs           the CharSequence to check, may be null.
     * @param invalidChars a String of invalid chars, may be null.
     * @return true if it contains none of the invalid chars, or is null.
     * @since 2.0
     * @since 3.0 Changed signature from containsNone(String, String) to containsNone(CharSequence, String)
     */
    public static boolean containsNone(final CharSequence cs, final String invalidChars) {
        if (invalidChars == null) {
            return true;
        }
        return containsNone(cs, invalidChars.toCharArray());
    }

    /**
     * Tests if the CharSequence contains only certain characters.
     *
     * <p>
     * A {@code null} CharSequence will return {@code false}. A {@code null} valid character array will return {@code false}. An empty CharSequence (length()=0)
     * always returns {@code true}.
     * </p>
     *
     * <pre>
     * StringUtils.containsOnly(null, *)               = false
     * StringUtils.containsOnly(*, null)               = false
     * StringUtils.containsOnly("", *)                 = true
     * StringUtils.containsOnly("ab", '')              = false
     * StringUtils.containsOnly("abab", 'a', 'b', 'c') = true
     * StringUtils.containsOnly("ab1", 'a', 'b', 'c')  = false
     * StringUtils.containsOnly("abz", 'a', 'b', 'c')  = false
     * </pre>
     *
     * @param cs    the String to check, may be null.
     * @param valid an array of valid chars, may be null.
     * @return true if it only contains valid chars and is non-null.
     * @since 3.0 Changed signature from containsOnly(String, char[]) to containsOnly(CharSequence, char...)
     */
    public static boolean containsOnly(final CharSequence cs, final char... valid) {
        // All these pre-checks are to maintain API with an older version
        if (valid == null || cs == null) {
            return false;
        }
        if (cs.length() == 0) {
            return true;
        }
        if (valid.length == 0) {
            return false;
        }
        return indexOfAnyBut(cs, valid) == INDEX_NOT_FOUND;
    }

    /**
     * Tests if the CharSequence contains only certain characters.
     *
     * <p>
     * A {@code null} CharSequence will return {@code false}. A {@code null} valid character String will return {@code false}. An empty String (length()=0)
     * always returns {@code true}.
     * </p>
     *
     * <pre>
     * StringUtils.containsOnly(null, *)       = false
     * StringUtils.containsOnly(*, null)       = false
     * StringUtils.containsOnly("", *)         = true
     * StringUtils.containsOnly("ab", "")      = false
     * StringUtils.containsOnly("abab", "abc") = true
     * StringUtils.containsOnly("ab1", "abc")  = false
     * StringUtils.containsOnly("abz", "abc")  = false
     * </pre>
     *
     * @param cs         the CharSequence to check, may be null.
     * @param validChars a String of valid chars, may be null.
     * @return true if it only contains valid chars and is non-null.
     * @since 2.0
     * @since 3.0 Changed signature from containsOnly(String, String) to containsOnly(CharSequence, String)
     */
    public static boolean containsOnly(final CharSequence cs, final String validChars) {
        if (cs == null || validChars == null) {
            return false;
        }
        return containsOnly(cs, validChars.toCharArray());
    }

    /**
     * Tests whether the given CharSequence contains any whitespace characters.
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.containsWhitespace(null)       = false
     * StringUtils.containsWhitespace("")         = false
     * StringUtils.containsWhitespace("ab")       = false
     * StringUtils.containsWhitespace(" ab")      = true
     * StringUtils.containsWhitespace("a b")      = true
     * StringUtils.containsWhitespace("ab ")      = true
     * </pre>
     *
     * @param seq the CharSequence to check (may be {@code null}).
     * @return {@code true} if the CharSequence is not empty and contains at least 1 (breaking) whitespace character.
     * @since 3.0
     */
    public static boolean containsWhitespace(final CharSequence seq) {
        if (isEmpty(seq)) {
            return false;
        }
        final int strLen = seq.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(seq.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static void convertRemainingAccentCharacters(final StringBuilder decomposed) {
        for (int i = 0; i < decomposed.length(); i++) {
            final char charAt = decomposed.charAt(i);
            switch (charAt) {
            case '\u0141':
                decomposed.setCharAt(i, 'L');
                break;
            case '\u0142':
                decomposed.setCharAt(i, 'l');
                break;
            // D with stroke
            case '\u0110':
                // LATIN CAPITAL LETTER D WITH STROKE
                decomposed.setCharAt(i, 'D');
                break;
            case '\u0111':
                // LATIN SMALL LETTER D WITH STROKE
                decomposed.setCharAt(i, 'd');
                break;
            // I with bar
            case '\u0197':
                decomposed.setCharAt(i, 'I');
                break;
            case '\u0268':
                decomposed.setCharAt(i, 'i');
                break;
            case '\u1D7B':
                decomposed.setCharAt(i, 'I');
                break;
            case '\u1DA4':
                decomposed.setCharAt(i, 'i');
                break;
            case '\u1DA7':
                decomposed.setCharAt(i, 'I');
                break;
            // U with bar
            case '\u0244':
                // LATIN CAPITAL LETTER U BAR
                decomposed.setCharAt(i, 'U');
                break;
            case '\u0289':
                // LATIN SMALL LETTER U BAR
                decomposed.setCharAt(i, 'u');
                break;
            case '\u1D7E':
                // LATIN SMALL CAPITAL LETTER U WITH STROKE
                decomposed.setCharAt(i, 'U');
                break;
            case '\u1DB6':
                // MODIFIER LETTER SMALL U BAR
                decomposed.setCharAt(i, 'u');
                break;
            // T with stroke
            case '\u0166':
                // LATIN CAPITAL LETTER T WITH STROKE
                decomposed.setCharAt(i, 'T');
                break;
            case '\u0167':
                // LATIN SMALL LETTER T WITH STROKE
                decomposed.setCharAt(i, 't');
                break;
            default:
                break;
            }
        }
    }

    /**
     * Counts how many times the char appears in the given string.
     *
     * <p>
     * A {@code null} or empty ("") String input returns {@code 0}.
     * </p>
     *
     * <pre>
     * StringUtils.countMatches(null, *)     = 0
     * StringUtils.countMatches("", *)       = 0
     * StringUtils.countMatches("abba", 0)   = 0
     * StringUtils.countMatches("abba", 'a') = 2
     * StringUtils.countMatches("abba", 'b') = 2
     * StringUtils.countMatches("abba", 'x') = 0
     * </pre>
     *
     * @param str the CharSequence to check, may be null.
     * @param ch  the char to count.
     * @return the number of occurrences, 0 if the CharSequence is {@code null}.
     * @since 3.4
     */
    public static int countMatches(final CharSequence str, final char ch) {
        if (isEmpty(str)) {
            return 0;
        }
        int count = 0;
        // We could also call str.toCharArray() for faster lookups but that would generate more garbage.
        for (int i = 0; i < str.length(); i++) {
            if (ch == str.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts how many times the substring appears in the larger string. Note that the code only counts non-overlapping matches.
     *
     * <p>
     * A {@code null} or empty ("") String input returns {@code 0}.
     * </p>
     *
     * <pre>
     * StringUtils.countMatches(null, *)        = 0
     * StringUtils.countMatches("", *)          = 0
     * StringUtils.countMatches("abba", null)   = 0
     * StringUtils.countMatches("abba", "")     = 0
     * StringUtils.countMatches("abba", "a")    = 2
     * StringUtils.countMatches("abba", "ab")   = 1
     * StringUtils.countMatches("abba", "xxx")  = 0
     * StringUtils.countMatches("ababa", "aba") = 1
     * </pre>
     *
     * @param str the CharSequence to check, may be null.
     * @param sub the substring to count, may be null.
     * @return the number of occurrences, 0 if either CharSequence is {@code null}.
     * @since 3.0 Changed signature from countMatches(String, String) to countMatches(CharSequence, CharSequence)
     */
    public static int countMatches(final CharSequence str, final CharSequence sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = CharSequenceUtils.indexOf(str, sub, idx)) != INDEX_NOT_FOUND) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    /**
     * Returns either the passed in CharSequence, or if the CharSequence is {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}), or
     * {@code null}), the value of {@code defaultStr}.
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.defaultIfBlank(null, "NULL")  = "NULL"
     * StringUtils.defaultIfBlank("", "NULL")    = "NULL"
     * StringUtils.defaultIfBlank(" ", "NULL")   = "NULL"
     * StringUtils.defaultIfBlank("bat", "NULL") = "bat"
     * StringUtils.defaultIfBlank("", null)      = null
     * </pre>
     *
     * @param <T>        the specific kind of CharSequence.
     * @param str        the CharSequence to check, may be null.
     * @param defaultStr the default CharSequence to return if {@code str} is {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}), or
     *                   {@code null}); may be null.
     * @return the passed in CharSequence, or the default.
     * @see StringUtils#defaultString(String, String)
     * @see #isBlank(CharSequence)
     */
    public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    /**
     * Returns either the passed in CharSequence, or if the CharSequence is empty or {@code null}, the value of {@code defaultStr}.
     *
     * <pre>
     * StringUtils.defaultIfEmpty(null, "NULL")  = "NULL"
     * StringUtils.defaultIfEmpty("", "NULL")    = "NULL"
     * StringUtils.defaultIfEmpty(" ", "NULL")   = " "
     * StringUtils.defaultIfEmpty("bat", "NULL") = "bat"
     * StringUtils.defaultIfEmpty("", null)      = null
     * </pre>
     *
     * @param <T>        the specific kind of CharSequence.
     * @param str        the CharSequence to check, may be null.
     * @param defaultStr the default CharSequence to return if the input is empty ("") or {@code null}, may be null.
     * @return the passed in CharSequence, or the default.
     * @see StringUtils#defaultString(String, String)
     */
    public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    /**
     * Returns either the passed in String, or if the String is {@code null}, an empty String ("").
     *
     * <pre>
     * StringUtils.defaultString(null)  = ""
     * StringUtils.defaultString("")    = ""
     * StringUtils.defaultString("bat") = "bat"
     * </pre>
     *
     * @param str the String to check, may be null.
     * @return the passed in String, or the empty String if it was {@code null}.
     * @see Objects#toString(Object, String)
     * @see String#valueOf(Object)
     */
    public static String defaultString(final String str) {
        return Objects.toString(str, EMPTY);
    }

    /**
     * Returns either the given String, or if the String is {@code null}, {@code nullDefault}.
     *
     * <pre>
     * StringUtils.defaultString(null, "NULL")  = "NULL"
     * StringUtils.defaultString("", "NULL")    = ""
     * StringUtils.defaultString("bat", "NULL") = "bat"
     * </pre>
     * <p>
     * Since this is now provided by Java, instead call {@link Objects#toString(Object, String)}:
     * </p>
     *
     * <pre>
     * Objects.toString(null, "NULL")  = "NULL"
     * Objects.toString("", "NULL")    = ""
     * Objects.toString("bat", "NULL") = "bat"
     * </pre>
     *
     * @param str         the String to check, may be null.
     * @param nullDefault the default String to return if the input is {@code null}, may be null.
     * @return the passed in String, or the default if it was {@code null}.
     * @see Objects#toString(Object, String)
     * @see String#valueOf(Object)
     * @deprecated Use {@link Objects#toString(Object, String)}.
     */
    @Deprecated
    public static String defaultString(final String str, final String nullDefault) {
        return Objects.toString(str, nullDefault);
    }

    /**
     * Deletes all whitespaces from a String as defined by {@link Character#isWhitespace(char)}.
     *
     * <pre>
     * StringUtils.deleteWhitespace(null)         = null
     * StringUtils.deleteWhitespace("")           = ""
     * StringUtils.deleteWhitespace("abc")        = "abc"
     * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
     * </pre>
     *
     * @param str the String to delete whitespace from, may be null.
     * @return the String without whitespaces, {@code null} if null String input.
     */
    public static String deleteWhitespace(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        final int sz = str.length();
        final char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }
        if (count == 0) {
            return EMPTY;
        }
        return new String(chs, 0, count);
    }

    /**
     * Compares two Strings, and returns the portion where they differ. More precisely, return the remainder of the second String, starting from where it's
     * different from the first. This means that the difference between "abc" and "ab" is the empty String and not "c".
     *
     * <p>
     * For example, {@code difference("i am a machine", "i am a robot") -> "robot"}.
     * </p>
     *
     * <pre>
     * StringUtils.difference(null, null)       = null
     * StringUtils.difference("", "")           = ""
     * StringUtils.difference("", "abc")        = "abc"
     * StringUtils.difference("abc", "")        = ""
     * StringUtils.difference("abc", "abc")     = ""
     * StringUtils.difference("abc", "ab")      = ""
     * StringUtils.difference("ab", "abxyz")    = "xyz"
     * StringUtils.difference("abcde", "abxyz") = "xyz"
     * StringUtils.difference("abcde", "xyz")   = "xyz"
     * </pre>
     *
     * @param str1 the first String, may be null.
     * @param str2 the second String, may be null.
     * @return the portion of str2 where it differs from str1; returns the empty String if they are equal.
     * @see #indexOfDifference(CharSequence,CharSequence)
     * @since 2.0
     */
    public static String difference(final String str1, final String str2) {
        if (str1 == null) {
            return str2;
        }
        if (str2 == null) {
            return str1;
        }
        final int at = indexOfDifference(str1, str2);
        if (at == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str2.substring(at);
    }

    /**
     * Tests if a CharSequence ends with a specified suffix.
     *
     * <p>
     * {@code null}s are handled without exceptions. Two {@code null} references are considered to be equal. The comparison is case-sensitive.
     * </p>
     *
     * <pre>
     * StringUtils.endsWith(null, null)      = true
     * StringUtils.endsWith(null, "def")     = false
     * StringUtils.endsWith("abcdef", null)  = false
     * StringUtils.endsWith("abcdef", "def") = true
     * StringUtils.endsWith("ABCDEF", "def") = false
     * StringUtils.endsWith("ABCDEF", "cde") = false
     * StringUtils.endsWith("ABCDEF", "")    = true
     * </pre>
     *
     * @param str    the CharSequence to check, may be null.
     * @param suffix the suffix to find, may be null.
     * @return {@code true} if the CharSequence ends with the suffix, case-sensitive, or both {@code null}.
     * @see String#endsWith(String)
     * @since 2.4
     * @since 3.0 Changed signature from endsWith(String, String) to endsWith(CharSequence, CharSequence)
     * @deprecated Use {@link Strings#endsWith(CharSequence, CharSequence) Strings.CS.endsWith(CharSequence, CharSequence)}.
     */
    @Deprecated
    public static boolean endsWith(final CharSequence str, final CharSequence suffix) {
        return Strings.CS.endsWith(str, suffix);
    }

    /**
     * Tests if a CharSequence ends with any of the provided case-sensitive suffixes.
     *
     * <pre>
     * StringUtils.endsWithAny(null, null)                  = false
     * StringUtils.endsWithAny(null, new String[] {"abc"})  = false
     * StringUtils.endsWithAny("abcxyz", null)              = false
     * StringUtils.endsWithAny("abcxyz", new String[] {""}) = true
     * StringUtils.endsWithAny("abcxyz", new String[] {"xyz"}) = true
     * StringUtils.endsWithAny("abcxyz", new String[] {null, "xyz", "abc"}) = true
     * StringUtils.endsWithAny("abcXYZ", "def", "XYZ")      = true
     * StringUtils.endsWithAny("abcXYZ", "def", "xyz")      = false
     * </pre>
     *
     * @param sequence      the CharSequence to check, may be null.
     * @param searchStrings the case-sensitive CharSequences to find, may be empty or contain {@code null}.
     * @return {@code true} if the input {@code sequence} is {@code null} AND no {@code searchStrings} are provided, or the input {@code sequence} ends in any
     *         of the provided case-sensitive {@code searchStrings}.
     * @see StringUtils#endsWith(CharSequence, CharSequence)
     * @since 3.0
     * @deprecated Use {@link Strings#endsWithAny(CharSequence, CharSequence...) Strings.CS.endsWithAny(CharSequence, CharSequence...)}.
     */
    @Deprecated
    public static boolean endsWithAny(final CharSequence sequence, final CharSequence... searchStrings) {
        return Strings.CS.endsWithAny(sequence, searchStrings);
    }

    /**
     * Case-insensitive check if a CharSequence ends with a specified suffix.
     *
     * <p>
     * {@code null}s are handled without exceptions. Two {@code null} references are considered to be equal. The comparison is case insensitive.
     * </p>
     *
     * <pre>
     * StringUtils.endsWithIgnoreCase(null, null)      = true
     * StringUtils.endsWithIgnoreCase(null, "def")     = false
     * StringUtils.endsWithIgnoreCase("abcdef", null)  = false
     * StringUtils.endsWithIgnoreCase("abcdef", "def") = true
     * StringUtils.endsWithIgnoreCase("ABCDEF", "def") = true
     * StringUtils.endsWithIgnoreCase("ABCDEF", "cde") = false
     * </pre>
     *
     * @param str    the CharSequence to check, may be null
     * @param suffix the suffix to find, may be null
     * @return {@code true} if the CharSequence ends with the suffix, case-insensitive, or both {@code null}
     * @see String#endsWith(String)
     * @since 2.4
     * @since 3.0 Changed signature from endsWithIgnoreCase(String, String) to endsWithIgnoreCase(CharSequence, CharSequence)
     * @deprecated Use {@link Strings#endsWith(CharSequence, CharSequence) Strings.CI.endsWith(CharSequence, CharSequence)}.
     */
    @Deprecated
    public static boolean endsWithIgnoreCase(final CharSequence str, final CharSequence suffix) {
        return Strings.CI.endsWith(str, suffix);
    }

    /**
     * Compares two CharSequences, returning {@code true} if they represent equal sequences of characters.
     *
     * <p>
     * {@code null}s are handled without exceptions. Two {@code null} references are considered to be equal. The comparison is <strong>case-sensitive</strong>.
     * </p>
     *
     * <pre>
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     * </pre>
     *
     * @param cs1 the first CharSequence, may be {@code null}.
     * @param cs2 the second CharSequence, may be {@code null}.
     * @return {@code true} if the CharSequences are equal (case-sensitive), or both {@code null}.
     * @since 3.0 Changed signature from equals(String, String) to equals(CharSequence, CharSequence)
     * @see Object#equals(Object)
     * @see #equalsIgnoreCase(CharSequence, CharSequence)
     * @deprecated Use {@link Strings#equals(CharSequence, CharSequence) Strings.CS.equals(CharSequence, CharSequence)}.
     */
    @Deprecated
    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        return Strings.CS.equals(cs1, cs2);
    }

    /**
     * Compares given {@code string} to a CharSequences vararg of {@code searchStrings}, returning {@code true} if the {@code string} is equal to any of the
     * {@code searchStrings}.
     *
     * <pre>
     * StringUtils.equalsAny(null, (CharSequence[]) null) = false
     * StringUtils.equalsAny(null, null, null)    = true
     * StringUtils.equalsAny(null, "abc", "def")  = false
     * StringUtils.equalsAny("abc", null, "def")  = false
     * StringUtils.equalsAny("abc", "abc", "def") = true
     * StringUtils.equalsAny("abc", "ABC", "DEF") = false
     * </pre>
     *
     * @param string        to compare, may be {@code null}.
     * @param searchStrings a vararg of strings, may be {@code null}.
     * @return {@code true} if the string is equal (case-sensitive) to any other element of {@code searchStrings}; {@code false} if {@code searchStrings} is
     *         null or contains no matches.
     * @since 3.5
     * @deprecated Use {@link Strings#equalsAny(CharSequence, CharSequence...) Strings.CS.equalsAny(CharSequence, CharSequence...)}.
     */
    @Deprecated
    public static boolean equalsAny(final CharSequence string, final CharSequence... searchStrings) {
        return Strings.CS.equalsAny(string, searchStrings);
    }

    /**
     * Compares given {@code string} to a CharSequences vararg of {@code searchStrings},
     * returning {@code true} if the {@code string} is equal to any of the {@code searchStrings}, ignoring case.
     *
     * <pre>
     * StringUtils.equalsAnyIgnoreCase(null, (CharSequence[]) null) = false
     * StringUtils.equalsAnyIgnoreCase(null, null, null)    = true
     * StringUtils.equalsAnyIgnoreCase(null, "abc", "def")  = false
     * StringUtils.equalsAnyIgnoreCase("abc", null, "def")  = false
     * StringUtils.equalsAnyIgnoreCase("abc", "abc", "def") = true
     * StringUtils.equalsAnyIgnoreCase("abc", "ABC", "DEF") = true
     * </pre>
     *
     * @param string to compare, may be {@code null}.
     * @param searchStrings a vararg of strings, may be {@code null}.
     * @return {@code true} if the string is equal (case-insensitive) to any other element of {@code searchStrings};
     * {@code false} if {@code searchStrings} is null or contains no matches.
     * @since 3.5
     * @deprecated Use {@link Strings#equalsAny(CharSequence, CharSequence...) Strings.CI.equalsAny(CharSequence, CharSequence...)}.
     */
    @Deprecated
    public static boolean equalsAnyIgnoreCase(final CharSequence string, final CharSequence... searchStrings) {
        return Strings.CI.equalsAny(string, searchStrings);
    }

    /**
     * Compares two CharSequences, returning {@code true} if they represent equal sequences of characters, ignoring case.
     *
     * <p>
     * {@code null}s are handled without exceptions. Two {@code null} references are considered equal. The comparison is <strong>case insensitive</strong>.
     * </p>
     *
     * <pre>
     * StringUtils.equalsIgnoreCase(null, null)   = true
     * StringUtils.equalsIgnoreCase(null, "abc")  = false
     * StringUtils.equalsIgnoreCase("abc", null)  = false
     * StringUtils.equalsIgnoreCase("abc", "abc") = true
     * StringUtils.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param cs1 the first CharSequence, may be {@code null}.
     * @param cs2 the second CharSequence, may be {@code null}.
     * @return {@code true} if the CharSequences are equal (case-insensitive), or both {@code null}.
     * @since 3.0 Changed signature from equalsIgnoreCase(String, String) to equalsIgnoreCase(CharSequence, CharSequence)
     * @see #equals(CharSequence, CharSequence)
     * @deprecated Use {@link Strings#equals(CharSequence, CharSequence) Strings.CI.equals(CharSequence, CharSequence)}.
     */
    @Deprecated
    public static boolean equalsIgnoreCase(final CharSequence cs1, final CharSequence cs2) {
        return Strings.CI.equals(cs1, cs2);
    }

    /**
     * Returns the first value in the array which is not empty (""), {@code null} or whitespace only.
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <p>
     * If all values are blank or the array is {@code null} or empty then {@code null} is returned.
     * </p>
     *
     * <pre>
     * StringUtils.firstNonBlank(null, null, null)     = null
     * StringUtils.firstNonBlank(null, "", " ")        = null
     * StringUtils.firstNonBlank("abc")                = "abc"
     * StringUtils.firstNonBlank(null, "xyz")          = "xyz"
     * StringUtils.firstNonBlank(null, "", " ", "xyz") = "xyz"
     * StringUtils.firstNonBlank(null, "xyz", "abc")   = "xyz"
     * StringUtils.firstNonBlank()                     = null
     * </pre>
     *
     * @param <T>    the specific kind of CharSequence.
     * @param values the values to test, may be {@code null} or empty.
     * @return the first value from {@code values} which is not blank, or {@code null} if there are no non-blank values.
     * @since 3.8
     */
    @SafeVarargs
    public static <T extends CharSequence> T firstNonBlank(final T... values) {
        if (values != null) {
            for (final T val : values) {
                if (isNotBlank(val)) {
                    return val;
                }
            }
        }
        return null;
    }

    /**
     * Returns the first value in the array which is not empty.
     *
     * <p>
     * If all values are empty or the array is {@code null} or empty then {@code null} is returned.
     * </p>
     *
     * <pre>
     * StringUtils.firstNonEmpty(null, null, null)   = null
     * StringUtils.firstNonEmpty(null, null, "")     = null
     * StringUtils.firstNonEmpty(null, "", " ")      = " "
     * StringUtils.firstNonEmpty("abc")              = "abc"
     * StringUtils.firstNonEmpty(null, "xyz")        = "xyz"
     * StringUtils.firstNonEmpty("", "xyz")          = "xyz"
     * StringUtils.firstNonEmpty(null, "xyz", "abc") = "xyz"
     * StringUtils.firstNonEmpty()                   = null
     * </pre>
     *
     * @param <T>    the specific kind of CharSequence.
     * @param values the values to test, may be {@code null} or empty.
     * @return the first value from {@code values} which is not empty, or {@code null} if there are no non-empty values.
     * @since 3.8
     */
    @SafeVarargs
    public static <T extends CharSequence> T firstNonEmpty(final T... values) {
        if (values != null) {
            for (final T val : values) {
                if (isNotEmpty(val)) {
                    return val;
                }
            }
        }
        return null;
    }

    /**
     * Calls {@link String#getBytes(Charset)} in a null-safe manner.
     *
     * @param string input string.
     * @param charset The {@link Charset} to encode the {@link String}. If null, then use the default Charset.
     * @return The empty byte[] if {@code string} is null, the result of {@link String#getBytes(Charset)} otherwise.
     * @see String#getBytes(Charset)
     * @since 3.10
     */
    public static byte[] getBytes(final String string, final Charset charset) {
        return string == null ? ArrayUtils.EMPTY_BYTE_ARRAY : string.getBytes(Charsets.toCharset(charset));
    }

    /**
     * Calls {@link String#getBytes(String)} in a null-safe manner.
     *
     * @param string input string.
     * @param charset The {@link Charset} name to encode the {@link String}. If null, then use the default Charset.
     * @return The empty byte[] if {@code string} is null, the result of {@link String#getBytes(String)} otherwise.
     * @throws UnsupportedEncodingException Thrown when the named charset is not supported.
     * @see String#getBytes(String)
     * @since 3.10
     */
    public static byte[] getBytes(final String string, final String charset) throws UnsupportedEncodingException {
        return string == null ? ArrayUtils.EMPTY_BYTE_ARRAY : string.getBytes(Charsets.toCharsetName(charset));
    }

    /**
     * Compares all Strings in an array and returns the initial sequence of characters that is common to all of them.
     *
     * <p>
     * For example, {@code getCommonPrefix("i am a machine", "i am a robot") -&gt; "i am a "}
     * </p>
     *
     * <pre>
     * StringUtils.getCommonPrefix(null)                             = ""
     * StringUtils.getCommonPrefix(new String[] {})                  = ""
     * StringUtils.getCommonPrefix(new String[] {"abc"})             = "abc"
     * StringUtils.getCommonPrefix(new String[] {null, null})        = ""
     * StringUtils.getCommonPrefix(new String[] {"", ""})            = ""
     * StringUtils.getCommonPrefix(new String[] {"", null})          = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", null, null}) = ""
     * StringUtils.getCommonPrefix(new String[] {null, null, "abc"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", "abc"})         = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", ""})         = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", "abc"})      = "abc"
     * StringUtils.getCommonPrefix(new String[] {"abc", "a"})        = "a"
     * StringUtils.getCommonPrefix(new String[] {"ab", "abxyz"})     = "ab"
     * StringUtils.getCommonPrefix(new String[] {"abcde", "abxyz"})  = "ab"
     * StringUtils.getCommonPrefix(new String[] {"abcde", "xyz"})    = ""
     * StringUtils.getCommonPrefix(new String[] {"xyz", "abcde"})    = ""
     * StringUtils.getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) = "i am a "
     * </pre>
     *
     * @param strs array of String objects, entries may be null.
     * @return the initial sequence of characters that are common to all Strings in the array; empty String if the array is null, the elements are all null or
     *         if there is no common prefix.
     * @since 2.4
     */
    public static String getCommonPrefix(final String... strs) {
        if (ArrayUtils.isEmpty(strs)) {
            return EMPTY;
        }
        final int smallestIndexOfDiff = indexOfDifference(strs);
        if (smallestIndexOfDiff == INDEX_NOT_FOUND) {
            // all strings were identical
            if (strs[0] == null) {
                return EMPTY;
            }
            return strs[0];
        }
        if (smallestIndexOfDiff == 0) {
            // there were no common initial characters
            return EMPTY;
        }
        // we found a common initial character sequence
        return strs[0].substring(0, smallestIndexOfDiff);
    }

    /**
     * Checks if a String {@code str} contains Unicode digits, if yes then concatenate all the digits in {@code str} and return it as a String.
     *
     * <p>
     * An empty ("") String will be returned if no digits found in {@code str}.
     * </p>
     *
     * <pre>
     * StringUtils.getDigits(null)                 = null
     * StringUtils.getDigits("")                   = ""
     * StringUtils.getDigits("abc")                = ""
     * StringUtils.getDigits("1000$")              = "1000"
     * StringUtils.getDigits("1123~45")            = "112345"
     * StringUtils.getDigits("(541) 754-3010")     = "5417543010"
     * StringUtils.getDigits("\u0967\u0968\u0969") = "\u0967\u0968\u0969"
     * </pre>
     *
     * @param str the String to extract digits from, may be null.
     * @return String with only digits, or an empty ("") String if no digits found, or {@code null} String if {@code str} is null.
     * @since 3.6
     */
    public static String getDigits(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        final int len = str.length();
        final char[] buffer = new char[len];
        int count = 0;

        for (int i = 0; i < len; i++) {
            final char tempChar = str.charAt(i);
            if (Character.isDigit(tempChar)) {
                buffer[count++] = tempChar;
            }
        }
        return new String(buffer, 0, count);
    }

    /**
     * Gets the Fuzzy Distance which indicates the similarity score between two Strings.
     *
     * <p>
     * This string matching algorithm is similar to the algorithms of editors such as Sublime Text, TextMate, Atom and others. One point is given for every
     * matched character. Subsequent matches yield two bonus points. A higher score indicates a higher similarity.
     * </p>
     *
     * <pre>
     * StringUtils.getFuzzyDistance(null, null, null)                                    = Throws {@link IllegalArgumentException}
     * StringUtils.getFuzzyDistance("", "", Locale.ENGLISH)                              = 0
     * StringUtils.getFuzzyDistance("Workshop", "b", Locale.ENGLISH)                     = 0
     * StringUtils.getFuzzyDistance("Room", "o", Locale.ENGLISH)                         = 1
     * StringUtils.getFuzzyDistance("Workshop", "w", Locale.ENGLISH)                     = 1
     * StringUtils.getFuzzyDistance("Workshop", "ws", Locale.ENGLISH)                    = 2
     * StringUtils.getFuzzyDistance("Workshop", "wo", Locale.ENGLISH)                    = 4
     * StringUtils.getFuzzyDistance("Apache Software Foundation", "asf", Locale.ENGLISH) = 3
     * </pre>
     *
     * @param term   a full term that should be matched against, must not be null.
     * @param query  the query that will be matched against a term, must not be null.
     * @param locale This string matching logic is case-insensitive. A locale is necessary to normalize both Strings to lower case.
     * @return result score.
     * @throws IllegalArgumentException if either String input {@code null} or Locale input {@code null}.
     * @since 3.4
     * @deprecated As of 3.6, use Apache Commons Text
     *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/FuzzyScore.html">
     *             FuzzyScore</a> instead.
     */
    @Deprecated
    public static int getFuzzyDistance(final CharSequence term, final CharSequence query, final Locale locale) {
        if (term == null || query == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        if (locale == null) {
            throw new IllegalArgumentException("Locale must not be null");
        }
        // fuzzy logic is case-insensitive. We normalize the Strings to lower
        // case right from the start. Turning characters to lower case
        // via Character.toLowerCase(char) is unfortunately insufficient
        // as it does not accept a locale.
        final String termLowerCase = term.toString().toLowerCase(locale);
        final String queryLowerCase = query.toString().toLowerCase(locale);
        // the resulting score
        int score = 0;
        // the position in the term which will be scanned next for potential
        // query character matches
        int termIndex = 0;
        // index of the previously matched character in the term
        int previousMatchingCharacterIndex = Integer.MIN_VALUE;
        for (int queryIndex = 0; queryIndex < queryLowerCase.length(); queryIndex++) {
            final char queryChar = queryLowerCase.charAt(queryIndex);
            boolean termCharacterMatchFound = false;
            for (; termIndex < termLowerCase.length() && !termCharacterMatchFound; termIndex++) {
                final char termChar = termLowerCase.charAt(termIndex);
                if (queryChar == termChar) {
                    // simple character matches result in one point
                    score++;
                    // subsequent character matches further improve
                    // the score.
                    if (previousMatchingCharacterIndex + 1 == termIndex) {
                        score += 2;
                    }
                    previousMatchingCharacterIndex = termIndex;
                    // we can leave the nested loop. Every character in the
                    // query can match at most one character in the term.
                    termCharacterMatchFound = true;
                }
            }
        }
        return score;
    }

    /**
     * Returns either the passed in CharSequence, or if the CharSequence is {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}), or
     * {@code null}), the value supplied by {@code defaultStrSupplier}.
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <p>
     * Caller responsible for thread-safety and exception handling of default value supplier
     * </p>
     *
     * <pre>
     * {@code
     * StringUtils.getIfBlank(null, () -> "NULL")   = "NULL"
     * StringUtils.getIfBlank("", () -> "NULL")     = "NULL"
     * StringUtils.getIfBlank(" ", () -> "NULL")    = "NULL"
     * StringUtils.getIfBlank("bat", () -> "NULL")  = "bat"
     * StringUtils.getIfBlank("", () -> null)       = null
     * StringUtils.getIfBlank("", null)             = null
     * }</pre>
     *
     * @param <T>             the specific kind of CharSequence.
     * @param str             the CharSequence to check, may be null.
     * @param defaultSupplier the supplier of default CharSequence to return if the input is {@link #isBlank(CharSequence) blank} (whitespaces, empty
     *                        ({@code ""}), or {@code null}); may be null.
     * @return the passed in CharSequence, or the default
     * @see StringUtils#defaultString(String, String)
     * @see #isBlank(CharSequence)
     * @since 3.10
     */
    public static <T extends CharSequence> T getIfBlank(final T str, final Supplier<T> defaultSupplier) {
        return isBlank(str) ? Suppliers.get(defaultSupplier) : str;
    }

    /**
     * Returns either the passed in CharSequence, or if the CharSequence is empty or {@code null}, the value supplied by {@code defaultStrSupplier}.
     *
     * <p>
     * Caller responsible for thread-safety and exception handling of default value supplier
     * </p>
     *
     * <pre>
     * {@code
     * StringUtils.getIfEmpty(null, () -> "NULL")    = "NULL"
     * StringUtils.getIfEmpty("", () -> "NULL")      = "NULL"
     * StringUtils.getIfEmpty(" ", () -> "NULL")     = " "
     * StringUtils.getIfEmpty("bat", () -> "NULL")   = "bat"
     * StringUtils.getIfEmpty("", () -> null)        = null
     * StringUtils.getIfEmpty("", null)              = null
     * }
     * </pre>
     *
     * @param <T>             the specific kind of CharSequence.
     * @param str             the CharSequence to check, may be null.
     * @param defaultSupplier the supplier of default CharSequence to return if the input is empty ("") or {@code null}, may be null.
     * @return the passed in CharSequence, or the default.
     * @see StringUtils#defaultString(String, String)
     * @since 3.10
     */
    public static <T extends CharSequence> T getIfEmpty(final T str, final Supplier<T> defaultSupplier) {
        return isEmpty(str) ? Suppliers.get(defaultSupplier) : str;
    }

    /**
     * Gets the Jaro Winkler Distance which indicates the similarity score between two Strings.
     *
     * <p>
     * The Jaro measure is the weighted sum of percentage of matched characters from each file and transposed characters. Winkler increased this measure for
     * matching initial characters.
     * </p>
     *
     * <p>
     * This implementation is based on the Jaro Winkler similarity algorithm from
     * <a href="https://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance">https://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance</a>.
     * </p>
     *
     * <pre>
     * StringUtils.getJaroWinklerDistance(null, null)          = Throws {@link IllegalArgumentException}
     * StringUtils.getJaroWinklerDistance("", "")              = 0.0
     * StringUtils.getJaroWinklerDistance("", "a")             = 0.0
     * StringUtils.getJaroWinklerDistance("aaapppp", "")       = 0.0
     * StringUtils.getJaroWinklerDistance("frog", "fog")       = 0.93
     * StringUtils.getJaroWinklerDistance("fly", "ant")        = 0.0
     * StringUtils.getJaroWinklerDistance("elephant", "hippo") = 0.44
     * StringUtils.getJaroWinklerDistance("hippo", "elephant") = 0.44
     * StringUtils.getJaroWinklerDistance("hippo", "zzzzzzzz") = 0.0
     * StringUtils.getJaroWinklerDistance("hello", "hallo")    = 0.88
     * StringUtils.getJaroWinklerDistance("ABC Corporation", "ABC Corp") = 0.93
     * StringUtils.getJaroWinklerDistance("D N H Enterprises Inc", "D &amp; H Enterprises, Inc.") = 0.95
     * StringUtils.getJaroWinklerDistance("My Gym Children's Fitness Center", "My Gym. Childrens Fitness") = 0.92
     * StringUtils.getJaroWinklerDistance("PENNSYLVANIA", "PENNCISYLVNIA") = 0.88
     * </pre>
     *
     * @param first  the first String, must not be null.
     * @param second the second String, must not be null.
     * @return result distance.
     * @throws IllegalArgumentException if either String input {@code null}.
     * @since 3.3
     * @deprecated As of 3.6, use Apache Commons Text
     *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/JaroWinklerDistance.html">
     *             JaroWinklerDistance</a> instead.
     */
    @Deprecated
    public static double getJaroWinklerDistance(final CharSequence first, final CharSequence second) {
        final double DEFAULT_SCALING_FACTOR = 0.1;

        if (first == null || second == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        final int[] mtp = matches(first, second);
        final double m = mtp[0];
        if (m == 0) {
            return 0D;
        }
        final double j = (m / first.length() + m / second.length() + (m - mtp[1]) / m) / 3;
        final double jw = j < 0.7D ? j : j + Math.min(DEFAULT_SCALING_FACTOR, 1D / mtp[3]) * mtp[2] * (1D - j);
        return Math.round(jw * 100.0D) / 100.0D;
    }

    /**
     * Gets the Levenshtein distance between two Strings.
     *
     * <p>
     * This is the number of changes needed to change one String into another, where each change is a single character modification (deletion, insertion or
     * substitution).
     * </p>
     *
     * <p>
     * The implementation uses a single-dimensional array of length s.length() + 1. See
     * <a href="https://blog.softwx.net/2014/12/optimizing-levenshtein-algorithm-in-c.html">
     * https://blog.softwx.net/2014/12/optimizing-levenshtein-algorithm-in-c.html</a> for details.
     * </p>
     *
     * <pre>
     * StringUtils.getLevenshteinDistance(null, *)             = Throws {@link IllegalArgumentException}
     * StringUtils.getLevenshteinDistance(*, null)             = Throws {@link IllegalArgumentException}
     * StringUtils.getLevenshteinDistance("", "")              = 0
     * StringUtils.getLevenshteinDistance("", "a")             = 1
     * StringUtils.getLevenshteinDistance("aaapppp", "")       = 7
     * StringUtils.getLevenshteinDistance("frog", "fog")       = 1
     * StringUtils.getLevenshteinDistance("fly", "ant")        = 3
     * StringUtils.getLevenshteinDistance("elephant", "hippo") = 7
     * StringUtils.getLevenshteinDistance("hippo", "elephant") = 7
     * StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz") = 8
     * StringUtils.getLevenshteinDistance("hello", "hallo")    = 1
     * </pre>
     *
     * @param s the first String, must not be null.
     * @param t the second String, must not be null.
     * @return result distance.
     * @throws IllegalArgumentException if either String input {@code null}.
     * @since 3.0 Changed signature from getLevenshteinDistance(String, String) to getLevenshteinDistance(CharSequence, CharSequence)
     * @deprecated As of 3.6, use Apache Commons Text
     *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/LevenshteinDistance.html">
     *             LevenshteinDistance</a> instead.
     */
    @Deprecated
    public static int getLevenshteinDistance(CharSequence s, CharSequence t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        int n = s.length();
        int m = t.length();

        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }

        if (n > m) {
            // swap the input strings to consume less memory
            final CharSequence tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }

        final int[] p = new int[n + 1];
        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t
        int upperleft;
        int upper;

        char jOfT; // jth character of t
        int cost;

        for (i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (j = 1; j <= m; j++) {
            upperleft = p[0];
            jOfT = t.charAt(j - 1);
            p[0] = j;

            for (i = 1; i <= n; i++) {
                upper = p[i];
                cost = s.charAt(i - 1) == jOfT ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                p[i] = Math.min(Math.min(p[i - 1] + 1, p[i] + 1), upperleft + cost);
                upperleft = upper;
            }
        }

        return p[n];
    }

    /**
     * Gets the Levenshtein distance between two Strings if it's less than or equal to a given threshold.
     *
     * <p>
     * This is the number of changes needed to change one String into another, where each change is a single character modification (deletion, insertion or
     * substitution).
     * </p>
     *
     * <p>
     * This implementation follows from Algorithms on Strings, Trees and Sequences by Dan Gusfield and Chas Emerick's implementation of the Levenshtein distance
     * algorithm.
     * </p>
     *
     * <pre>
     * StringUtils.getLevenshteinDistance(null, *, *)             = Throws {@link IllegalArgumentException}
     * StringUtils.getLevenshteinDistance(*, null, *)             = Throws {@link IllegalArgumentException}
     * StringUtils.getLevenshteinDistance(*, *, -1)               = Throws {@link IllegalArgumentException}
     * StringUtils.getLevenshteinDistance("", "", 0)              = 0
     * StringUtils.getLevenshteinDistance("aaapppp", "", 8)       = 7
     * StringUtils.getLevenshteinDistance("aaapppp", "", 7)       = 7
     * StringUtils.getLevenshteinDistance("aaapppp", "", 6))      = -1
     * StringUtils.getLevenshteinDistance("elephant", "hippo", 7) = 7
     * StringUtils.getLevenshteinDistance("elephant", "hippo", 6) = -1
     * StringUtils.getLevenshteinDistance("hippo", "elephant", 7) = 7
     * StringUtils.getLevenshteinDistance("hippo", "elephant", 6) = -1
     * </pre>
     *
     * @param s         the first String, must not be null.
     * @param t         the second String, must not be null.
     * @param threshold the target threshold, must not be negative.
     * @return result distance, or {@code -1} if the distance would be greater than the threshold.
     * @throws IllegalArgumentException if either String input {@code null} or negative threshold.
     * @deprecated As of 3.6, use Apache Commons Text
     *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/LevenshteinDistance.html">
     *             LevenshteinDistance</a> instead.
     */
    @Deprecated
    public static int getLevenshteinDistance(CharSequence s, CharSequence t, final int threshold) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold must not be negative");
        }

        /*
        This implementation only computes the distance if it's less than or equal to the
        threshold value, returning -1 if it's greater.  The advantage is performance: unbounded
        distance is O(nm), but a bound of k allows us to reduce it to O(km) time by only
        computing a diagonal stripe of width 2k + 1 of the cost table.
        It is also possible to use this to compute the unbounded Levenshtein distance by starting
        the threshold at 1 and doubling each time until the distance is found; this is O(dm), where
        d is the distance.

        One subtlety comes from needing to ignore entries on the border of our stripe
        for example,
        p[] = |#|#|#|*
        d[] =  *|#|#|#|
        We must ignore the entry to the left of the leftmost member
        We must ignore the entry above the rightmost member

        Another subtlety comes from our stripe running off the matrix if the strings aren't
        of the same size.  Since string s is always swapped to be the shorter of the two,
        the stripe will always run off to the upper right instead of the lower left of the matrix.

        As a concrete example, suppose s is of length 5, t is of length 7, and our threshold is 1.
        In this case we're going to walk a stripe of length 3.  The matrix would look like so:

           1 2 3 4 5
        1 |#|#| | | |
        2 |#|#|#| | |
        3 | |#|#|#| |
        4 | | |#|#|#|
        5 | | | |#|#|
        6 | | | | |#|
        7 | | | | | |

        Note how the stripe leads off the table as there is no possible way to turn a string of length 5
        into one of length 7 in edit distance of 1.

        Additionally, this implementation decreases memory usage by using two
        single-dimensional arrays and swapping them back and forth instead of allocating
        an entire n by m matrix.  This requires a few minor changes, such as immediately returning
        when it's detected that the stripe has run off the matrix and initially filling the arrays with
        large values so that entries we don't compute are ignored.

        See Algorithms on Strings, Trees and Sequences by Dan Gusfield for some discussion.
         */

        int n = s.length(); // length of s
        int m = t.length(); // length of t

        // if one string is empty, the edit distance is necessarily the length of the other
        if (n == 0) {
            return m <= threshold ? m : -1;
        }
        if (m == 0) {
            return n <= threshold ? n : -1;
        }
        if (Math.abs(n - m) > threshold) {
            // no need to calculate the distance if the length difference is greater than the threshold
            return -1;
        }

        if (n > m) {
            // swap the two strings to consume less memory
            final CharSequence tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }

        int[] p = new int[n + 1]; // 'previous' cost array, horizontally
        int[] d = new int[n + 1]; // cost array, horizontally
        int[] tmp; // placeholder to assist in swapping p and d

        // fill in starting table values
        final int boundary = Math.min(n, threshold) + 1;
        for (int i = 0; i < boundary; i++) {
            p[i] = i;
        }
        // these fills ensure that the value above the rightmost entry of our
        // stripe will be ignored in following loop iterations
        Arrays.fill(p, boundary, p.length, Integer.MAX_VALUE);
        Arrays.fill(d, Integer.MAX_VALUE);

        // iterates through t
        for (int j = 1; j <= m; j++) {
            final char jOfT = t.charAt(j - 1); // jth character of t
            d[0] = j;

            // compute stripe indices, constrain to array size
            final int min = Math.max(1, j - threshold);
            final int max = j > Integer.MAX_VALUE - threshold ? n : Math.min(n, j + threshold);

            // the stripe may lead off of the table if s and t are of different sizes
            if (min > max) {
                return -1;
            }

            // ignore entry left of leftmost
            if (min > 1) {
                d[min - 1] = Integer.MAX_VALUE;
            }

            // iterates through [min, max] in s
            for (int i = min; i <= max; i++) {
                if (s.charAt(i - 1) == jOfT) {
                    // diagonally left and up
                    d[i] = p[i - 1];
                } else {
                    // 1 + minimum of cell to the left, to the top, diagonally left and up
                    d[i] = 1 + Math.min(Math.min(d[i - 1], p[i]), p[i - 1]);
                }
            }

            // copy current distance counts to 'previous row' distance counts
            tmp = p;
            p = d;
            d = tmp;
        }

        // if p[n] is greater than the threshold, there's no guarantee on it being the correct
        // distance
        if (p[n] <= threshold) {
            return p[n];
        }
        return -1;
    }

    /**
     * Finds the first index within a CharSequence, handling {@code null}. This method uses {@link String#indexOf(String, int)} if possible.
     *
     * <p>
     * A {@code null} CharSequence will return {@code -1}.
     * </p>
     *
     * <pre>
     * StringUtils.indexOf(null, *)          = -1
     * StringUtils.indexOf(*, null)          = -1
     * StringUtils.indexOf("", "")           = 0
     * StringUtils.indexOf("", *)            = -1 (except when * = "")
     * StringUtils.indexOf("aabaabaa", "a")  = 0
     * StringUtils.indexOf("aabaabaa", "b")  = 2
     * StringUtils.indexOf("aabaabaa", "ab") = 1
     * StringUtils.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param seq       the CharSequence to check, may be null.
     * @param searchSeq the CharSequence to find, may be null.
     * @return the first index of the search CharSequence, -1 if no match or {@code null} string input.
     * @since 2.0
     * @since 3.0 Changed signature from indexOf(String, String) to indexOf(CharSequence, CharSequence)
     * @deprecated Use {@link Strings#indexOf(CharSequence, CharSequence) Strings.CS.indexOf(CharSequence, CharSequence)}.
     */
    @Deprecated
    public static int indexOf(final CharSequence seq, final CharSequence searchSeq) {
        return Strings.CS.indexOf(seq, searchSeq);
    }

    /**
     * Finds the first index within a CharSequence, handling {@code null}. This method uses {@link String#indexOf(String, int)} if possible.
     *
     * <p>
     * A {@code null} CharSequence will return {@code -1}. A negative start position is treated as zero. An empty ("") search CharSequence always matches. A
     * start position greater than the string length only matches an empty search CharSequence.
     * </p>
     *
     * <pre>
     * StringUtils.indexOf(null, *, *)          = -1
     * StringUtils.indexOf(*, null, *)          = -1
     * StringUtils.indexOf("", "", 0)           = 0
     * StringUtils.indexOf("", *, 0)            = -1 (except when * = "")
     * StringUtils.indexOf("aabaabaa", "a", 0)  = 0
     * StringUtils.indexOf("aabaabaa", "b", 0)  = 2
     * StringUtils.indexOf("aabaabaa", "ab", 0) = 1
     * StringUtils.indexOf("aabaabaa", "b", 3)  = 5
     * StringUtils.indexOf("aabaabaa", "b", 9)  = -1
     * StringUtils.indexOf("aabaabaa", "b", -1) = 2
     * StringUtils.indexOf("aabaabaa", "", 2)   = 2
     * StringUtils.indexOf("abc", "", 9)        = 3
     * </pre>
     *
     * @param seq       the CharSequence to check, may be null.
     * @param searchSeq the CharSequence to find, may be null.
     * @param startPos  the start position, negative treated as zero.
     * @return the first index of the search CharSequence (always &ge; startPos), -1 if no match or {@code null} string input.
     * @since 2.0
     * @since 3.0 Changed signature from indexOf(String, String, int) to indexOf(CharSequence, CharSequence, int)
     * @deprecated Use {@link Strings#indexOf(CharSequence, CharSequence, int) Strings.CS.indexOf(CharSequence, CharSequence, int)}.
     */
    @Deprecated
    public static int indexOf(final CharSequence seq, final CharSequence searchSeq, final int startPos) {
        return Strings.CS.indexOf(seq, searchSeq, startPos);
    }

    /**
     * Returns the index within {@code seq} of the first occurrence of the specified character. If a character with value {@code searchChar} occurs in the
     * character sequence represented by {@code seq} {@link CharSequence} object, then the index (in Unicode code units) of the first such occurrence is
     * returned. For values of {@code searchChar} in the range from 0 to 0xFFFF (inclusive), this is the smallest value <em>k</em> such that:
     *
     * <pre>
     * this.charAt(<em>k</em>) == searchChar
     * </pre>
     *
     * <p>
     * is true. For other values of {@code searchChar}, it is the smallest value <em>k</em> such that:
     * </p>
     *
     * <pre>
     * this.codePointAt(<em>k</em>) == searchChar
     * </pre>
     *
     * <p>
     * is true. In either case, if no such character occurs in {@code seq}, then {@code INDEX_NOT_FOUND (-1)} is returned.
     * </p>
     *
     * <p>
     * Furthermore, a {@code null} or empty ("") CharSequence will return {@code INDEX_NOT_FOUND (-1)}.
     * </p>
     *
     * <pre>
     * StringUtils.indexOf(null, *)         = -1
     * StringUtils.indexOf("", *)           = -1
     * StringUtils.indexOf("aabaabaa", 'a') = 0
     * StringUtils.indexOf("aabaabaa", 'b') = 2
     * StringUtils.indexOf("aaaaaaaa", 'Z') = -1
     * </pre>
     *
     * @param seq        the CharSequence to check, may be null.
     * @param searchChar the character to find.
     * @return the first index of the search character, -1 if no match or {@code null} string input.
     * @since 2.0
     * @since 3.0 Changed signature from indexOf(String, int) to indexOf(CharSequence, int)
     * @since 3.6 Updated {@link CharSequenceUtils} call to behave more like {@link String}
     */
    public static int indexOf(final CharSequence seq, final int searchChar) {
        if (isEmpty(seq)) {
            return INDEX_NOT_FOUND;
        }
        return CharSequenceUtils.indexOf(seq, searchChar, 0);
    }

    /**
     * Returns the index within {@code seq} of the first occurrence of the specified character, starting the search at the specified index.
     * <p>
     * If a character with value {@code searchChar} occurs in the character sequence represented by the {@code seq} {@link CharSequence} object at an index no
     * smaller than {@code startPos}, then the index of the first such occurrence is returned. For values of {@code searchChar} in the range from 0 to 0xFFFF
     * (inclusive), this is the smallest value <em>k</em> such that:
     * </p>
     *
     * <pre>
     * (this.charAt(<em>k</em>) == searchChar) &amp;&amp; (<em>k</em> &gt;= startPos)
     * </pre>
     *
     * <p>
     * is true. For other values of {@code searchChar}, it is the smallest value <em>k</em> such that:
     * </p>
     *
     * <pre>
     * (this.codePointAt(<em>k</em>) == searchChar) &amp;&amp; (<em>k</em> &gt;= startPos)
     * </pre>
     *
     * <p>
     * is true. In either case, if no such character occurs in {@code seq} at or after position {@code startPos}, then {@code -1} is returned.
     * </p>
     *
     * <p>
     * There is no restriction on the value of {@code startPos}. If it is negative, it has the same effect as if it were zero: this entire string may be
     * searched. If it is greater than the length of this string, it has the same effect as if it were equal to the length of this string:
     * {@code (INDEX_NOT_FOUND) -1} is returned. Furthermore, a {@code null} or empty ("") CharSequence will return {@code (INDEX_NOT_FOUND) -1}.
     * </p>
     * <p>
     * All indices are specified in {@code char} values (Unicode code units).
     * </p>
     *
     * <pre>
     * StringUtils.indexOf(null, *, *)          = -1
     * StringUtils.indexOf("", *, *)            = -1
     * StringUtils.indexOf("aabaabaa", 'b', 0)  = 2
     * StringUtils.indexOf("aabaabaa", 'b', 3)  = 5
     * StringUtils.indexOf("aabaabaa", 'b', 9)  = -1
     * StringUtils.indexOf("aabaabaa", 'b', -1) = 2
     * </pre>
     *
     * @param seq        the CharSequence to check, may be null.
     * @param searchChar the character to find.
     * @param startPos   the start position, negative treated as zero.
     * @return the first index of the search character (always &ge; startPos), -1 if no match or {@code null} string input.
     * @since 2.0
     * @since 3.0 Changed signature from indexOf(String, int, int) to indexOf(CharSequence, int, int)
     * @since 3.6 Updated {@link CharSequenceUtils} call to behave more like {@link String}
     */
    public static int indexOf(final CharSequence seq, final int searchChar, final int startPos) {
        if (isEmpty(seq)) {
            return INDEX_NOT_FOUND;
        }
        return CharSequenceUtils.indexOf(seq, searchChar, startPos);
    }

    /**
     * Search a CharSequence to find the first index of any character in the given set of characters.
     *
     * <p>
     * A {@code null} String will return {@code -1}. A {@code null} or zero length search array will return {@code -1}.
     * </p>
     *
     * <pre>
     * StringUtils.indexOfAny(null, *)                  = -1
     * StringUtils.indexOfAny("", *)                    = -1
     * StringUtils.indexOfAny(*, null)                  = -1
     * StringUtils.indexOfAny(*, [])                    = -1
     * StringUtils.indexOfAny("zzabyycdxx", 'z', 'a')   = 0
     * StringUtils.indexOfAny("zzabyycdxx", 'b', 'y')   = 3
     * StringUtils.indexOfAny("aba", 'z')               = -1
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null.
     * @param searchChars the chars to search for, may be null.
     * @return the index of any of the chars, -1 if no match or null input.
     * @since 2.0
     * @since 3.0 Changed signature from indexOfAny(String, char[]) to indexOfAny(CharSequence, char...)
     */
    public static int indexOfAny(final CharSequence cs, final char... searchChars) {
        return indexOfAny(cs, 0, searchChars);
    }

    /**
     * Find the first index of any of a set of potential substrings.
     *
     * <p>
     * A {@code null} CharSequence will return {@code -1}. A {@code null} or zero length search array will return {@code -1}. A {@code null} search array entry
     * will be ignored, but a search array containing "" will return {@code 0} if {@code str} is not null. This method uses {@link String#indexOf(String)} if
     * possible.
     * </p>
     *
     * <pre>
     * StringUtils.indexOfAny(null, *)                    = -1
     * StringUtils.indexOfAny(*, null)                    = -1
     * StringUtils.indexOfAny(*, [])                      = -1
     * StringUtils.indexOfAny("zzabyycdxx", "ab", "cd")   = 2
     * StringUtils.indexOfAny("zzabyycdxx", "cd", "ab")   = 2
     * StringUtils.indexOfAny("zzabyycdxx", "mn", "op")   = -1
     * StringUtils.indexOfAny("zzabyycdxx", "zab", "aby") = 1
     * StringUtils.indexOfAny("zzabyycdxx", "")           = 0
     * StringUtils.indexOfAny("", "")                     = 0
     * StringUtils.indexOfAny("", "a")                    = -1
     * </pre>
     *
     * @param str        the CharSequence to check, may be null.
     * @param searchStrs the CharSequences to search for, may be null.
     * @return the first index of any of the searchStrs in str, -1 if no match.
     * @since 3.0 Changed signature from indexOfAny(String, String[]) to indexOfAny(CharSequence, CharSequence...)
     */
    public static int indexOfAny(final CharSequence str, final CharSequence... searchStrs) {
        if (str == null || searchStrs == null) {
            return INDEX_NOT_FOUND;
        }
        // String's can't have a MAX_VALUEth index.
        int ret = Integer.MAX_VALUE;
        int tmp;
        for (final CharSequence search : searchStrs) {
            if (search == null) {
                continue;
            }
            tmp = CharSequenceUtils.indexOf(str, search, 0);
            if (tmp == INDEX_NOT_FOUND) {
                continue;
            }
            if (tmp < ret) {
                ret = tmp;
            }
        }
        return ret == Integer.MAX_VALUE ? INDEX_NOT_FOUND : ret;
    }

    /**
     * Search a CharSequence to find the first index of any character in the given set of characters.
     *
     * <p>
     * A {@code null} String will return {@code -1}. A {@code null} or zero length search array will return {@code -1}.
     * </p>
     * <p>
     * The following is the same as {@code indexOfAny(cs, 0, searchChars)}.
     * </p>
     * <pre>
     * StringUtils.indexOfAny(null, 0, *)                  = -1
     * StringUtils.indexOfAny("", 0, *)                    = -1
     * StringUtils.indexOfAny(*, 0, null)                  = -1
     * StringUtils.indexOfAny(*, 0, [])                    = -1
     * StringUtils.indexOfAny("zzabyycdxx", 0, ['z', 'a']) = 0
     * StringUtils.indexOfAny("zzabyycdxx", 0, ['b', 'y']) = 3
     * StringUtils.indexOfAny("aba", 0, ['z'])             = -1
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null.
     * @param csStart Start searching the input {@code cs} at this index.
     * @param searchChars the chars to search for, may be null.
     * @return the index of any of the chars, -1 if no match or null input.
     * @since 2.0
     * @since 3.0 Changed signature from indexOfAny(String, char[]) to indexOfAny(CharSequence, char...)
     */
    public static int indexOfAny(final CharSequence cs, final int csStart, final char... searchChars) {
        if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
            return INDEX_NOT_FOUND;
        }
        final int csLen = cs.length();
        final int csLast = csLen - 1;
        final int searchLen = searchChars.length;
        final int searchLast = searchLen - 1;
        for (int i = csStart; i < csLen; i++) {
            final char ch = cs.charAt(i);
            for (int j = 0; j < searchLen; j++) {
                if (searchChars[j] == ch) {
                    // ch is a supplementary character
                    if (i >= csLast || j >= searchLast || !Character.isHighSurrogate(ch) || searchChars[j + 1] == cs.charAt(i + 1)) {
                        return i;
                    }
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Search a CharSequence to find the first index of any character in the given set of characters.
     *
     * <p>
     * A {@code null} String will return {@code -1}. A {@code null} search string will return {@code -1}.
     * </p>
     *
     * <pre>
     * StringUtils.indexOfAny(null, *)            = -1
     * StringUtils.indexOfAny("", *)              = -1
     * StringUtils.indexOfAny(*, null)            = -1
     * StringUtils.indexOfAny(*, "")              = -1
     * StringUtils.indexOfAny("zzabyycdxx", "za") = 0
     * StringUtils.indexOfAny("zzabyycdxx", "by") = 3
     * StringUtils.indexOfAny("aba", "z")         = -1
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null.
     * @param searchChars the chars to search for, may be null.
     * @return the index of any of the chars, -1 if no match or null input.
     * @since 2.0
     * @since 3.0 Changed signature from indexOfAny(String, String) to indexOfAny(CharSequence, String)
     */
    public static int indexOfAny(final CharSequence cs, final String searchChars) {
        if (isEmpty(cs) || isEmpty(searchChars)) {
            return INDEX_NOT_FOUND;
        }
        return indexOfAny(cs, searchChars.toCharArray());
    }

    /**
     * Searches a CharSequence to find the first index of any character not in the given set of characters, i.e., find index i of first char in cs such that
     * (cs.codePointAt(i) ∉ { x ∈ codepoints(searchChars) })
     *
     * <p>
     * A {@code null} CharSequence will return {@code -1}. A {@code null} or zero length search array will return {@code -1}.
     * </p>
     *
     * <pre>
     * StringUtils.indexOfAnyBut(null, *)                              = -1
     * StringUtils.indexOfAnyBut("", *)                                = -1
     * StringUtils.indexOfAnyBut(*, null)                              = -1
     * StringUtils.indexOfAnyBut(*, [])                                = -1
     * StringUtils.indexOfAnyBut("zzabyycdxx", new char[] {'z', 'a'} ) = 3
     * StringUtils.indexOfAnyBut("aba", new char[] {'z'} )             = 0
     * StringUtils.indexOfAnyBut("aba", new char[] {'a', 'b'} )        = -1
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null.
     * @param searchChars the chars to search for, may be null.
     * @return the index of any of the chars, -1 if no match or null input.
     * @since 2.0
     * @since 3.0 Changed signature from indexOfAnyBut(String, char[]) to indexOfAnyBut(CharSequence, char...)
     */
    public static int indexOfAnyBut(final CharSequence cs, final char... searchChars) {
        if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
            return INDEX_NOT_FOUND;
        }
        return indexOfAnyBut(cs, CharBuffer.wrap(searchChars));
    }

    /**
     * Search a CharSequence to find the first index of any character not in the given set of characters, i.e., find index i of first char in seq such that
     * (seq.codePointAt(i) ∉ { x ∈ codepoints(searchChars) })
     *
     * <p>
     * A {@code null} CharSequence will return {@code -1}. A {@code null} or empty search string will return {@code -1}.
     * </p>
     *
     * <pre>
     * StringUtils.indexOfAnyBut(null, *)            = -1
     * StringUtils.indexOfAnyBut("", *)              = -1
     * StringUtils.indexOfAnyBut(*, null)            = -1
     * StringUtils.indexOfAnyBut(*, "")              = -1
     * StringUtils.indexOfAnyBut("zzabyycdxx", "za") = 3
     * StringUtils.indexOfAnyBut("zzabyycdxx", "")   = -1
     * StringUtils.indexOfAnyBut("aba", "ab")        = -1
     * </pre>
     *
     * @param seq         the CharSequence to check, may be null.
     * @param searchChars the chars to search for, may be null.
     * @return the index of any of the chars, -1 if no match or null input.
     * @since 2.0
     * @since 3.0 Changed signature from indexOfAnyBut(String, String) to indexOfAnyBut(CharSequence, CharSequence)
     */
    public static int indexOfAnyBut(final CharSequence seq, final CharSequence searchChars) {
        if (isEmpty(seq) || isEmpty(searchChars)) {
            return INDEX_NOT_FOUND;
        }
        final Set<Integer> searchSetCodePoints = searchChars.codePoints()
                .boxed().collect(Collectors.toSet());
        // advance character index from one interpreted codepoint to the next
        for (int curSeqCharIdx = 0; curSeqCharIdx < seq.length();) {
            final int curSeqCodePoint = Character.codePointAt(seq, curSeqCharIdx);
            if (!searchSetCodePoints.contains(curSeqCodePoint)) {
                return curSeqCharIdx;
            }
            curSeqCharIdx += Character.charCount(curSeqCodePoint); // skip indices to paired low-surrogates
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Compares all CharSequences in an array and returns the index at which the CharSequences begin to differ.
     *
     * <p>
     * For example, {@code indexOfDifference(new String[] {"i am a machine", "i am a robot"}) -> 7}
     * </p>
     *
     * <pre>
     * StringUtils.indexOfDifference(null)                             = -1
     * StringUtils.indexOfDifference(new String[] {})                  = -1
     * StringUtils.indexOfDifference(new String[] {"abc"})             = -1
     * StringUtils.indexOfDifference(new String[] {null, null})        = -1
     * StringUtils.indexOfDifference(new String[] {"", ""})            = -1
     * StringUtils.indexOfDifference(new String[] {"", null})          = 0
     * StringUtils.indexOfDifference(new String[] {"abc", null, null}) = 0
     * StringUtils.indexOfDifference(new String[] {null, null, "abc"}) = 0
     * StringUtils.indexOfDifference(new String[] {"", "abc"})         = 0
     * StringUtils.indexOfDifference(new String[] {"abc", ""})         = 0
     * StringUtils.indexOfDifference(new String[] {"abc", "abc"})      = -1
     * StringUtils.indexOfDifference(new String[] {"abc", "a"})        = 1
     * StringUtils.indexOfDifference(new String[] {"ab", "abxyz"})     = 2
     * StringUtils.indexOfDifference(new String[] {"abcde", "abxyz"})  = 2
     * StringUtils.indexOfDifference(new String[] {"abcde", "xyz"})    = 0
     * StringUtils.indexOfDifference(new String[] {"xyz", "abcde"})    = 0
     * StringUtils.indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
     * </pre>
     *
     * @param css array of CharSequences, entries may be null.
     * @return the index where the strings begin to differ; -1 if they are all equal.
     * @since 2.4
     * @since 3.0 Changed signature from indexOfDifference(String...) to indexOfDifference(CharSequence...)
     */
    public static int indexOfDifference(final CharSequence... css) {
        if (ArrayUtils.getLength(css) <= 1) {
            return INDEX_NOT_FOUND;
        }
        boolean anyStringNull = false;
        boolean allStringsNull = true;
        final int arrayLen = css.length;
        int shortestStrLen = Integer.MAX_VALUE;
        int longestStrLen = 0;
        // find the min and max string lengths; this avoids checking to make
        // sure we are not exceeding the length of the string each time through
        // the bottom loop.
        for (final CharSequence cs : css) {
            if (cs == null) {
                anyStringNull = true;
                shortestStrLen = 0;
            } else {
                allStringsNull = false;
                shortestStrLen = Math.min(cs.length(), shortestStrLen);
                longestStrLen = Math.max(cs.length(), longestStrLen);
            }
        }
        // handle lists containing all nulls or all empty strings
        if (allStringsNull || longestStrLen == 0 && !anyStringNull) {
            return INDEX_NOT_FOUND;
        }
        // handle lists containing some nulls or some empty strings
        if (shortestStrLen == 0) {
            return 0;
        }
        // find the position with the first difference across all strings
        int firstDiff = -1;
        for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
            final char comparisonChar = css[0].charAt(stringPos);
            for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
                if (css[arrayPos].charAt(stringPos) != comparisonChar) {
                    firstDiff = stringPos;
                    break;
                }
            }
            if (firstDiff != -1) {
                break;
            }
        }
        if (firstDiff == -1 && shortestStrLen != longestStrLen) {
            // we compared all of the characters up to the length of the
            // shortest string and didn't find a match, but the string lengths
            // vary, so return the length of the shortest string.
            return shortestStrLen;
        }
        return firstDiff;
    }

    /**
     * Compares two CharSequences, and returns the index at which the CharSequences begin to differ.
     *
     * <p>
     * For example, {@code indexOfDifference("i am a machine", "i am a robot") -> 7}
     * </p>
     *
     * <pre>
     * StringUtils.indexOfDifference(null, null)       = -1
     * StringUtils.indexOfDifference("", "")           = -1
     * StringUtils.indexOfDifference("", "abc")        = 0
     * StringUtils.indexOfDifference("abc", "")        = 0
     * StringUtils.indexOfDifference("abc", "abc")     = -1
     * StringUtils.indexOfDifference("ab", "abxyz")    = 2
     * StringUtils.indexOfDifference("abcde", "abxyz") = 2
     * StringUtils.indexOfDifference("abcde", "xyz")   = 0
     * </pre>
     *
     * @param cs1 the first CharSequence, may be null.
     * @param cs2 the second CharSequence, may be null.
     * @return the index where cs1 and cs2 begin to differ; -1 if they are equal.
     * @since 2.0
     * @since 3.0 Changed signature from indexOfDifference(String, String) to indexOfDifference(CharSequence, CharSequence)
     */
    public static int indexOfDifference(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return INDEX_NOT_FOUND;
        }
        if (cs1 == null || cs2 == null) {
            return 0;
        }
        int i;
        for (i = 0; i < cs1.length() && i < cs2.length(); ++i) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                break;
            }
        }
        if (i < cs2.length() || i < cs1.length()) {
            return i;
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Case in-sensitive find of the first index within a CharSequence.
     *
     * <p>
     * A {@code null} CharSequence will return {@code -1}. A negative start position is treated as zero. An empty ("") search CharSequence always matches. A
     * start position greater than the string length only matches an empty search CharSequence.
     * </p>
     *
     * <pre>
     * StringUtils.indexOfIgnoreCase(null, *)          = -1
     * StringUtils.indexOfIgnoreCase(*, null)          = -1
     * StringUtils.indexOfIgnoreCase("", "")           = 0
     * StringUtils.indexOfIgnoreCase(" ", " ")         = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "a")  = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "b")  = 2
     * StringUtils.indexOfIgnoreCase("aabaabaa", "ab") = 1
     * </pre>
     *
     * @param str       the CharSequence to check, may be null.
     * @param searchStr the CharSequence to find, may be null.
     * @return the first index of the search CharSequence, -1 if no match or {@code null} string input.
     * @since 2.5
     * @since 3.0 Changed signature from indexOfIgnoreCase(String, String) to indexOfIgnoreCase(CharSequence, CharSequence)
     * @deprecated Use {@link Strings#indexOf(CharSequence, CharSequence) Strings.CI.indexOf(CharSequence, CharSequence)}.
     */
    @Deprecated
    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        return Strings.CI.indexOf(str, searchStr);
    }

    /**
     * Case in-sensitive find of the first index within a CharSequence from the specified position.
     *
     * <p>
     * A {@code null} CharSequence will return {@code -1}. A negative start position is treated as zero. An empty ("") search CharSequence always matches. A
     * start position greater than the string length only matches an empty search CharSequence.
     * </p>
     *
     * <pre>
     * StringUtils.indexOfIgnoreCase(null, *, *)          = -1
     * StringUtils.indexOfIgnoreCase(*, null, *)          = -1
     * StringUtils.indexOfIgnoreCase("", "", 0)           = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
     * StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
     * StringUtils.indexOfIgnoreCase("aabaabaa", "B", -1) = 2
     * StringUtils.indexOfIgnoreCase("aabaabaa", "", 2)   = 2
     * StringUtils.indexOfIgnoreCase("abc", "", 9)        = -1
     * </pre>
     *
     * @param str       the CharSequence to check, may be null.
     * @param searchStr the CharSequence to find, may be null.
     * @param startPos  the start position, negative treated as zero.
     * @return the first index of the search CharSequence (always &ge; startPos), -1 if no match or {@code null} string input.
     * @since 2.5
     * @since 3.0 Changed signature from indexOfIgnoreCase(String, String, int) to indexOfIgnoreCase(CharSequence, CharSequence, int)
     * @deprecated Use {@link Strings#indexOf(CharSequence, CharSequence, int) Strings.CI.indexOf(CharSequence, CharSequence, int)}.
     */
    @Deprecated
    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr, final int startPos) {
        return Strings.CI.indexOf(str, searchStr, startPos);
    }

    /**
     * Tests if all of the CharSequences are empty (""), null or whitespace only.
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.isAllBlank(null)             = true
     * StringUtils.isAllBlank(null, "foo")      = false
     * StringUtils.isAllBlank(null, null)       = true
     * StringUtils.isAllBlank("", "bar")        = false
     * StringUtils.isAllBlank("bob", "")        = false
     * StringUtils.isAllBlank("  bob  ", null)  = false
     * StringUtils.isAllBlank(" ", "bar")       = false
     * StringUtils.isAllBlank("foo", "bar")     = false
     * StringUtils.isAllBlank(new String[] {})  = true
     * </pre>
     *
     * @param css the CharSequences to check, may be null or empty.
     * @return {@code true} if all of the CharSequences are empty or null or whitespace only.
     * @since 3.6
     */
    public static boolean isAllBlank(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (isNotBlank(cs)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if all of the CharSequences are empty ("") or null.
     *
     * <pre>
     * StringUtils.isAllEmpty(null)             = true
     * StringUtils.isAllEmpty(null, "")         = true
     * StringUtils.isAllEmpty(new String[] {})  = true
     * StringUtils.isAllEmpty(null, "foo")      = false
     * StringUtils.isAllEmpty("", "bar")        = false
     * StringUtils.isAllEmpty("bob", "")        = false
     * StringUtils.isAllEmpty("  bob  ", null)  = false
     * StringUtils.isAllEmpty(" ", "bar")       = false
     * StringUtils.isAllEmpty("foo", "bar")     = false
     * </pre>
     *
     * @param css the CharSequences to check, may be null or empty.
     * @return {@code true} if all of the CharSequences are empty or null.
     * @since 3.6
     */
    public static boolean isAllEmpty(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (isNotEmpty(cs)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only lowercase characters.
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.isAllLowerCase(null)   = false
     * StringUtils.isAllLowerCase("")     = false
     * StringUtils.isAllLowerCase("  ")   = false
     * StringUtils.isAllLowerCase("abc")  = true
     * StringUtils.isAllLowerCase("abC")  = false
     * StringUtils.isAllLowerCase("ab c") = false
     * StringUtils.isAllLowerCase("ab1c") = false
     * StringUtils.isAllLowerCase("ab/c") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if only contains lowercase characters, and is non-null.
     * @since 2.5
     * @since 3.0 Changed signature from isAllLowerCase(String) to isAllLowerCase(CharSequence)
     */
    public static boolean isAllLowerCase(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLowerCase(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only uppercase characters.
     *
     * <p>{@code null} will return {@code false}.
     * An empty String (length()=0) will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.isAllUpperCase(null)   = false
     * StringUtils.isAllUpperCase("")     = false
     * StringUtils.isAllUpperCase("  ")   = false
     * StringUtils.isAllUpperCase("ABC")  = true
     * StringUtils.isAllUpperCase("aBC")  = false
     * StringUtils.isAllUpperCase("A C")  = false
     * StringUtils.isAllUpperCase("A1C")  = false
     * StringUtils.isAllUpperCase("A/C")  = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if only contains uppercase characters, and is non-null.
     * @since 2.5
     * @since 3.0 Changed signature from isAllUpperCase(String) to isAllUpperCase(CharSequence)
     */
    public static boolean isAllUpperCase(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isUpperCase(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only Unicode letters.
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.isAlpha(null)   = false
     * StringUtils.isAlpha("")     = false
     * StringUtils.isAlpha("  ")   = false
     * StringUtils.isAlpha("abc")  = true
     * StringUtils.isAlpha("ab2c") = false
     * StringUtils.isAlpha("ab-c") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if only contains letters, and is non-null.
     * @since 3.0 Changed signature from isAlpha(String) to isAlpha(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    public static boolean isAlpha(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetter(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only Unicode letters or digits.
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.isAlphanumeric(null)   = false
     * StringUtils.isAlphanumeric("")     = false
     * StringUtils.isAlphanumeric("  ")   = false
     * StringUtils.isAlphanumeric("abc")  = true
     * StringUtils.isAlphanumeric("ab c") = false
     * StringUtils.isAlphanumeric("ab2c") = true
     * StringUtils.isAlphanumeric("ab-c") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if only contains letters or digits, and is non-null.
     * @since 3.0 Changed signature from isAlphanumeric(String) to isAlphanumeric(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    public static boolean isAlphanumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetterOrDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only Unicode letters, digits or space ({@code ' '}).
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code true}.
     * </p>
     *
     * <pre>
     * StringUtils.isAlphanumericSpace(null)   = false
     * StringUtils.isAlphanumericSpace("")     = true
     * StringUtils.isAlphanumericSpace("  ")   = true
     * StringUtils.isAlphanumericSpace("abc")  = true
     * StringUtils.isAlphanumericSpace("ab c") = true
     * StringUtils.isAlphanumericSpace("ab2c") = true
     * StringUtils.isAlphanumericSpace("ab-c") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if only contains letters, digits or space, and is non-null.
     * @since 3.0 Changed signature from isAlphanumericSpace(String) to isAlphanumericSpace(CharSequence)
     */
    public static boolean isAlphanumericSpace(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            final char nowChar = cs.charAt(i);
            if (nowChar != ' ' && !Character.isLetterOrDigit(nowChar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only Unicode letters and space (' ').
     *
     * <p>
     * {@code null} will return {@code false} An empty CharSequence (length()=0) will return {@code true}.
     * </p>
     *
     * <pre>
     * StringUtils.isAlphaSpace(null)   = false
     * StringUtils.isAlphaSpace("")     = true
     * StringUtils.isAlphaSpace("  ")   = true
     * StringUtils.isAlphaSpace("abc")  = true
     * StringUtils.isAlphaSpace("ab c") = true
     * StringUtils.isAlphaSpace("ab2c") = false
     * StringUtils.isAlphaSpace("ab-c") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if only contains letters and space, and is non-null.
     * @since 3.0 Changed signature from isAlphaSpace(String) to isAlphaSpace(CharSequence)
     */
    public static boolean isAlphaSpace(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            final char nowChar = cs.charAt(i);
            if (nowChar != ' ' && !Character.isLetter(nowChar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if any of the CharSequences are {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}), or {@code null}).
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.isAnyBlank((String) null)    = true
     * StringUtils.isAnyBlank((String[]) null)  = false
     * StringUtils.isAnyBlank(null, "foo")      = true
     * StringUtils.isAnyBlank(null, null)       = true
     * StringUtils.isAnyBlank("", "bar")        = true
     * StringUtils.isAnyBlank("bob", "")        = true
     * StringUtils.isAnyBlank("  bob  ", null)  = true
     * StringUtils.isAnyBlank(" ", "bar")       = true
     * StringUtils.isAnyBlank(new String[] {})  = false
     * StringUtils.isAnyBlank(new String[]{""}) = true
     * StringUtils.isAnyBlank("foo", "bar")     = false
     * </pre>
     *
     * @param css the CharSequences to check, may be null or empty.
     * @return {@code true} if any of the CharSequences are {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}), or {@code null}).
     * @see #isBlank(CharSequence)
     * @since 3.2
     */
    public static boolean isAnyBlank(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return false;
        }
        for (final CharSequence cs : css) {
            if (isBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests if any of the CharSequences are empty ("") or null.
     *
     * <pre>
     * StringUtils.isAnyEmpty((String) null)    = true
     * StringUtils.isAnyEmpty((String[]) null)  = false
     * StringUtils.isAnyEmpty(null, "foo")      = true
     * StringUtils.isAnyEmpty("", "bar")        = true
     * StringUtils.isAnyEmpty("bob", "")        = true
     * StringUtils.isAnyEmpty("  bob  ", null)  = true
     * StringUtils.isAnyEmpty(" ", "bar")       = false
     * StringUtils.isAnyEmpty("foo", "bar")     = false
     * StringUtils.isAnyEmpty(new String[]{})   = false
     * StringUtils.isAnyEmpty(new String[]{""}) = true
     * </pre>
     *
     * @param css  the CharSequences to check, may be null or empty.
     * @return {@code true} if any of the CharSequences are empty or null.
     * @since 3.2
     */
    public static boolean isAnyEmpty(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return false;
        }
        for (final CharSequence cs : css) {
            if (isEmpty(cs)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests if the CharSequence contains only ASCII printable characters.
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code true}.
     * </p>
     *
     * <pre>
     * StringUtils.isAsciiPrintable(null)     = false
     * StringUtils.isAsciiPrintable("")       = true
     * StringUtils.isAsciiPrintable(" ")      = true
     * StringUtils.isAsciiPrintable("Ceki")   = true
     * StringUtils.isAsciiPrintable("ab2c")   = true
     * StringUtils.isAsciiPrintable("!ab-c~") = true
     * StringUtils.isAsciiPrintable("\u0020") = true
     * StringUtils.isAsciiPrintable("\u0021") = true
     * StringUtils.isAsciiPrintable("\u007e") = true
     * StringUtils.isAsciiPrintable("\u007f") = false
     * StringUtils.isAsciiPrintable("Ceki G\u00fclc\u00fc") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if every character is in the range 32 through 126.
     * @since 2.1
     * @since 3.0 Changed signature from isAsciiPrintable(String) to isAsciiPrintable(CharSequence)
     */
    public static boolean isAsciiPrintable(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!CharUtils.isAsciiPrintable(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if a CharSequence is empty ({@code "")}, null, or contains only whitespace as defined by {@link Character#isWhitespace(char)}.
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if the CharSequence is null, empty or whitespace only.
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if a CharSequence is empty ("") or null.
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>
     * NOTE: This method changed in Lang version 2.0. It no longer trims the CharSequence. That functionality is available in isBlank().
     * </p>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if the CharSequence is empty or null.
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * Tests if the CharSequence contains mixed casing of both uppercase and lowercase characters.
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence ({@code length()=0}) will return {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.isMixedCase(null)    = false
     * StringUtils.isMixedCase("")      = false
     * StringUtils.isMixedCase(" ")     = false
     * StringUtils.isMixedCase("ABC")   = false
     * StringUtils.isMixedCase("abc")   = false
     * StringUtils.isMixedCase("aBc")   = true
     * StringUtils.isMixedCase("A c")   = true
     * StringUtils.isMixedCase("A1c")   = true
     * StringUtils.isMixedCase("a/C")   = true
     * StringUtils.isMixedCase("aC\t")  = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if the CharSequence contains both uppercase and lowercase characters.
     * @since 3.5
     */
    public static boolean isMixedCase(final CharSequence cs) {
        if (isEmpty(cs) || cs.length() == 1) {
            return false;
        }
        boolean containsUppercase = false;
        boolean containsLowercase = false;
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            final char nowChar = cs.charAt(i);
            if (Character.isUpperCase(nowChar)) {
                containsUppercase = true;
            } else if (Character.isLowerCase(nowChar)) {
                containsLowercase = true;
            }
            if (containsUppercase && containsLowercase) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests if none of the CharSequences are empty (""), null or whitespace only.
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.isNoneBlank((String) null)    = false
     * StringUtils.isNoneBlank((String[]) null)  = true
     * StringUtils.isNoneBlank(null, "foo")      = false
     * StringUtils.isNoneBlank(null, null)       = false
     * StringUtils.isNoneBlank("", "bar")        = false
     * StringUtils.isNoneBlank("bob", "")        = false
     * StringUtils.isNoneBlank("  bob  ", null)  = false
     * StringUtils.isNoneBlank(" ", "bar")       = false
     * StringUtils.isNoneBlank(new String[] {})  = true
     * StringUtils.isNoneBlank(new String[]{""}) = false
     * StringUtils.isNoneBlank("foo", "bar")     = true
     * </pre>
     *
     * @param css the CharSequences to check, may be null or empty.
     * @return {@code true} if none of the CharSequences are empty or null or whitespace only.
     * @since 3.2
     */
    public static boolean isNoneBlank(final CharSequence... css) {
        return !isAnyBlank(css);
    }

    /**
     * Tests if none of the CharSequences are empty ("") or null.
     *
     * <pre>
     * StringUtils.isNoneEmpty((String) null)    = false
     * StringUtils.isNoneEmpty((String[]) null)  = true
     * StringUtils.isNoneEmpty(null, "foo")      = false
     * StringUtils.isNoneEmpty("", "bar")        = false
     * StringUtils.isNoneEmpty("bob", "")        = false
     * StringUtils.isNoneEmpty("  bob  ", null)  = false
     * StringUtils.isNoneEmpty(new String[] {})  = true
     * StringUtils.isNoneEmpty(new String[]{""}) = false
     * StringUtils.isNoneEmpty(" ", "bar")       = true
     * StringUtils.isNoneEmpty("foo", "bar")     = true
     * </pre>
     *
     * @param css  the CharSequences to check, may be null or empty.
     * @return {@code true} if none of the CharSequences are empty or null.
     * @since 3.2
     */
    public static boolean isNoneEmpty(final CharSequence... css) {
        return !isAnyEmpty(css);
    }

    /**
     * Tests if a CharSequence is not {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}), or {@code null}).
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if the CharSequence is not {@link #isBlank(CharSequence) blank} (whitespaces, empty ({@code ""}), or {@code null}).
     * @see #isBlank(CharSequence)
     * @since 2.0
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * Tests if a CharSequence is not empty ("") and not null.
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null.
     * @return {@code true} if the CharSequence is not empty and not null.
     * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * Tests if the CharSequence contains only Unicode digits. A decimal point is not a Unicode digit and returns false.
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code false}.
     * </p>
     *
     * <p>
     * Note that the method does not allow for a leading sign, either positive or negative. Also, if a String passes the numeric test, it may still generate a
     * NumberFormatException when parsed by Integer.parseInt or Long.parseLong, e.g. if the value is outside the range for int or long respectively.
     * </p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = false
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * StringUtils.isNumeric("-123") = false
     * StringUtils.isNumeric("+123") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if only contains digits, and is non-null.
     * @since 3.0 Changed signature from isNumeric(String) to isNumeric(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only Unicode digits or space ({@code ' '}). A decimal point is not a Unicode digit and returns false.
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code true}.
     * </p>
     *
     * <pre>
     * StringUtils.isNumericSpace(null)   = false
     * StringUtils.isNumericSpace("")     = true
     * StringUtils.isNumericSpace("  ")   = true
     * StringUtils.isNumericSpace("123")  = true
     * StringUtils.isNumericSpace("12 3") = true
     * StringUtils.isNumericSpace("\u0967\u0968\u0969")   = true
     * StringUtils.isNumericSpace("\u0967\u0968 \u0969")  = true
     * StringUtils.isNumericSpace("ab2c") = false
     * StringUtils.isNumericSpace("12-3") = false
     * StringUtils.isNumericSpace("12.3") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if only contains digits or space, and is non-null.
     * @since 3.0 Changed signature from isNumericSpace(String) to isNumericSpace(CharSequence)
     */
    public static boolean isNumericSpace(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            final char nowChar = cs.charAt(i);
            if (nowChar != ' ' && !Character.isDigit(nowChar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the CharSequence contains only whitespace.
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <p>
     * {@code null} will return {@code false}. An empty CharSequence (length()=0) will return {@code true}.
     * </p>
     *
     * <pre>
     * StringUtils.isWhitespace(null)   = false
     * StringUtils.isWhitespace("")     = true
     * StringUtils.isWhitespace("  ")   = true
     * StringUtils.isWhitespace("abc")  = false
     * StringUtils.isWhitespace("ab2c") = false
     * StringUtils.isWhitespace("ab-c") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null.
     * @return {@code true} if only contains whitespace, and is non-null.
     * @since 2.0
     * @since 3.0 Changed signature from isWhitespace(String) to isWhitespace(CharSequence)
     */
    public static boolean isWhitespace(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)             = null
     * StringUtils.join([], *)               = ""
     * StringUtils.join([null], *)           = ""
     * StringUtils.join([false, false], ';') = "false;false"
     * </pre>
     *
     * @param array     the array of values to join together, may be null.
     * @param delimiter the separator character to use.
     * @return the joined String, {@code null} if null array input.
     * @since 3.12.0
     */
    public static String join(final boolean[] array, final char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)                  = null
     * StringUtils.join([], *)                    = ""
     * StringUtils.join([null], *)                = ""
     * StringUtils.join([true, false, true], ';') = "true;false;true"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @param startIndex
     *            the first index to start joining from. It is an error to pass in a start index past the end of the
     *            array.
     * @param endIndex
     *            the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *            the array.
     * @return the joined String, {@code null} if null array input.
     * @since 3.12.0
     */
    public static String join(final boolean[] array, final char delimiter, final int startIndex, final int endIndex) {
        // See StringUtilsJoinBenchmark
        if (array == null) {
            return null;
        }
        final int count = endIndex - startIndex;
        if (count <= 0) {
            return EMPTY;
        }
        final byte maxElementChars = 5; // "false"
        final StringBuilder stringBuilder = capacity(count, maxElementChars);
        stringBuilder.append(array[startIndex]);
        for (int i = startIndex + 1; i < endIndex; i++) {
            stringBuilder.append(delimiter).append(array[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)         = null
     * StringUtils.join([], *)           = ""
     * StringUtils.join([null], *)       = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final byte[] array, final char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)         = null
     * StringUtils.join([], *)           = ""
     * StringUtils.join([null], *)       = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @param startIndex
     *            the first index to start joining from. It is an error to pass in a start index past the end of the
     *            array.
     * @param endIndex
     *            the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *            the array.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final byte[] array, final char delimiter, final int startIndex, final int endIndex) {
        // See StringUtilsJoinBenchmark
        if (array == null) {
            return null;
        }
        final int count = endIndex - startIndex;
        if (count <= 0) {
            return EMPTY;
        }
        final byte maxElementChars = 4; // "-128"
        final StringBuilder stringBuilder = capacity(count, maxElementChars);
        stringBuilder.append(array[startIndex]);
        for (int i = startIndex + 1; i < endIndex; i++) {
            stringBuilder.append(delimiter).append(array[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)         = null
     * StringUtils.join([], *)           = ""
     * StringUtils.join([null], *)       = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final char[] array, final char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)         = null
     * StringUtils.join([], *)           = ""
     * StringUtils.join([null], *)       = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @param startIndex
     *            the first index to start joining from. It is an error to pass in a start index past the end of the
     *            array.
     * @param endIndex
     *            the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *            the array.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final char[] array, final char delimiter, final int startIndex, final int endIndex) {
        // See StringUtilsJoinBenchmark
        if (array == null) {
            return null;
        }
        final int count = endIndex - startIndex;
        if (count <= 0) {
            return EMPTY;
        }
        final byte maxElementChars = 1;
        final StringBuilder stringBuilder = capacity(count, maxElementChars);
        stringBuilder.append(array[startIndex]);
        for (int i = startIndex + 1; i < endIndex; i++) {
            stringBuilder.append(delimiter).append(array[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)          = null
     * StringUtils.join([], *)            = ""
     * StringUtils.join([null], *)        = ""
     * StringUtils.join([1, 2, 3], ';')   = "1;2;3"
     * StringUtils.join([1, 2, 3], null)  = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final double[] array, final char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)          = null
     * StringUtils.join([], *)            = ""
     * StringUtils.join([null], *)        = ""
     * StringUtils.join([1, 2, 3], ';')   = "1;2;3"
     * StringUtils.join([1, 2, 3], null)  = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @param startIndex
     *            the first index to start joining from. It is an error to pass in a start index past the end of the
     *            array.
     * @param endIndex
     *            the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *            the array.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final double[] array, final char delimiter, final int startIndex, final int endIndex) {
        // See StringUtilsJoinBenchmark
        if (array == null) {
            return null;
        }
        final int count = endIndex - startIndex;
        if (count <= 0) {
            return EMPTY;
        }
        final byte maxElementChars = 22; // "1.7976931348623157E308"
        final StringBuilder stringBuilder = capacity(count, maxElementChars);
        stringBuilder.append(array[startIndex]);
        for (int i = startIndex + 1; i < endIndex; i++) {
            stringBuilder.append(delimiter).append(array[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)          = null
     * StringUtils.join([], *)            = ""
     * StringUtils.join([null], *)        = ""
     * StringUtils.join([1, 2, 3], ';')   = "1;2;3"
     * StringUtils.join([1, 2, 3], null)  = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @return the joined String, {@code null} if null array input
     * @since 3.2
     */
    public static String join(final float[] array, final char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)          = null
     * StringUtils.join([], *)            = ""
     * StringUtils.join([null], *)        = ""
     * StringUtils.join([1, 2, 3], ';')   = "1;2;3"
     * StringUtils.join([1, 2, 3], null)  = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @param startIndex
     *            the first index to start joining from. It is an error to pass in a start index past the end of the
     *            array.
     * @param endIndex
     *            the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *            the array.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final float[] array, final char delimiter, final int startIndex, final int endIndex) {
        // See StringUtilsJoinBenchmark
        if (array == null) {
            return null;
        }
        final int count = endIndex - startIndex;
        if (count <= 0) {
            return EMPTY;
        }
        final byte maxElementChars = 12; // "3.4028235E38"
        final StringBuilder stringBuilder = capacity(count, maxElementChars);
        stringBuilder.append(array[startIndex]);
        for (int i = startIndex + 1; i < endIndex; i++) {
            stringBuilder.append(delimiter).append(array[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)          = null
     * StringUtils.join([], *)            = ""
     * StringUtils.join([null], *)        = ""
     * StringUtils.join([1, 2, 3], ';')   = "1;2;3"
     * StringUtils.join([1, 2, 3], null)  = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param separator
     *            the separator character to use.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final int[] array, final char separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)          = null
     * StringUtils.join([], *)            = ""
     * StringUtils.join([null], *)        = ""
     * StringUtils.join([1, 2, 3], ';')   = "1;2;3"
     * StringUtils.join([1, 2, 3], null)  = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @param startIndex
     *            the first index to start joining from. It is an error to pass in a start index past the end of the
     *            array.
     * @param endIndex
     *            the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *            the array.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final int[] array, final char delimiter, final int startIndex, final int endIndex) {
        // See StringUtilsJoinBenchmark
        if (array == null) {
            return null;
        }
        final int count = endIndex - startIndex;
        if (count <= 0) {
            return EMPTY;
        }
        final byte maxElementChars = 11; // "-2147483648"
        final StringBuilder stringBuilder = capacity(count, maxElementChars);
        stringBuilder.append(array[startIndex]);
        for (int i = startIndex + 1; i < endIndex; i++) {
            stringBuilder.append(delimiter).append(array[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Joins the elements of the provided {@link Iterable} into a single String containing the provided elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the iteration are represented by empty strings.
     * </p>
     *
     * <p>
     * See the examples here: {@link #join(Object[],char)}.
     * </p>
     *
     * @param iterable  the {@link Iterable} providing the values to join together, may be null.
     * @param separator the separator character to use.
     * @return the joined String, {@code null} if null iterator input.
     * @since 2.3
     */
    public static String join(final Iterable<?> iterable, final char separator) {
        return iterable != null ? join(iterable.iterator(), separator) : null;
    }

    /**
     * Joins the elements of the provided {@link Iterable} into a single String containing the provided elements.
     *
     * <p>
     * No delimiter is added before or after the list. A {@code null} separator is the same as an empty String ("").
     * </p>
     *
     * <p>
     * See the examples here: {@link #join(Object[],String)}.
     * </p>
     *
     * @param iterable  the {@link Iterable} providing the values to join together, may be null.
     * @param separator the separator character to use, null treated as "".
     * @return the joined String, {@code null} if null iterator input.
     * @since 2.3
     */
    public static String join(final Iterable<?> iterable, final String separator) {
        return iterable != null ? join(iterable.iterator(), separator) : null;
    }

    /**
     * Joins the elements of the provided {@link Iterator} into a single String containing the provided elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the iteration are represented by empty strings.
     * </p>
     *
     * <p>
     * See the examples here: {@link #join(Object[],char)}.
     * </p>
     *
     * @param iterator  the {@link Iterator} of values to join together, may be null.
     * @param separator the separator character to use.
     * @return the joined String, {@code null} if null iterator input.
     * @since 2.0
     */
    public static String join(final Iterator<?> iterator, final char separator) {
        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        return Streams.of(iterator).collect(LangCollectors.joining(ObjectUtils.toString(String.valueOf(separator)), EMPTY, EMPTY, ObjectUtils::toString));
    }

    /**
     * Joins the elements of the provided {@link Iterator} into a single String containing the provided elements.
     *
     * <p>
     * No delimiter is added before or after the list. A {@code null} separator is the same as an empty String ("").
     * </p>
     *
     * <p>
     * See the examples here: {@link #join(Object[],String)}.
     * </p>
     *
     * @param iterator  the {@link Iterator} of values to join together, may be null.
     * @param separator the separator character to use, null treated as "".
     * @return the joined String, {@code null} if null iterator input.
     */
    public static String join(final Iterator<?> iterator, final String separator) {
        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        return Streams.of(iterator).collect(LangCollectors.joining(ObjectUtils.toString(separator), EMPTY, EMPTY, ObjectUtils::toString));
    }

    /**
     * Joins the elements of the provided {@link List} into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
     * </pre>
     *
     * @param list       the {@link List} of values to join together, may be null.
     * @param separator  the separator character to use.
     * @param startIndex the first index to start joining from. It is an error to pass in a start index past the end of the list.
     * @param endIndex   the index to stop joining from (exclusive). It is an error to pass in an end index past the end of the list.
     * @return the joined String, {@code null} if null list input.
     * @since 3.8
     */
    public static String join(final List<?> list, final char separator, final int startIndex, final int endIndex) {
        if (list == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return EMPTY;
        }
        final List<?> subList = list.subList(startIndex, endIndex);
        return join(subList.iterator(), separator);
    }

    /**
     * Joins the elements of the provided {@link List} into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
     * </pre>
     *
     * @param list       the {@link List} of values to join together, may be null.
     * @param separator  the separator character to use.
     * @param startIndex the first index to start joining from. It is an error to pass in a start index past the end of the list.
     * @param endIndex   the index to stop joining from (exclusive). It is an error to pass in an end index past the end of the list.
     * @return the joined String, {@code null} if null list input.
     * @since 3.8
     */
    public static String join(final List<?> list, final String separator, final int startIndex, final int endIndex) {
        if (list == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return EMPTY;
        }
        final List<?> subList = list.subList(startIndex, endIndex);
        return join(subList.iterator(), separator);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param separator
     *            the separator character to use.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final long[] array, final char separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
     * StringUtils.join([1, 2, 3], null) = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @param startIndex
     *            the first index to start joining from. It is an error to pass in a start index past the end of the
     *            array.
     * @param endIndex
     *            the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *            the array.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final long[] array, final char delimiter, final int startIndex, final int endIndex) {
        // See StringUtilsJoinBenchmark
        if (array == null) {
            return null;
        }
        final int count = endIndex - startIndex;
        if (count <= 0) {
            return EMPTY;
        }
        final byte maxElementChars = 20; // "-9223372036854775808"
        final StringBuilder stringBuilder = capacity(count, maxElementChars);
        stringBuilder.append(array[startIndex]);
        for (int i = startIndex + 1; i < endIndex; i++) {
            stringBuilder.append(delimiter).append(array[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
     * </pre>
     *
     * @param array     the array of values to join together, may be null.
     * @param delimiter the separator character to use.
     * @return the joined String, {@code null} if null array input.
     * @since 2.0
     */
    public static String join(final Object[] array, final char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
     * </pre>
     *
     * @param array      the array of values to join together, may be null.
     * @param delimiter  the separator character to use.
     * @param startIndex the first index to start joining from. It is an error to pass in a start index past the end of the array.
     * @param endIndex   the index to stop joining from (exclusive). It is an error to pass in an end index past the end of the array.
     * @return the joined String, {@code null} if null array input.
     * @since 2.0
     */
    public static String join(final Object[] array, final char delimiter, final int startIndex, final int endIndex) {
        return join(array, String.valueOf(delimiter), startIndex, endIndex);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. A {@code null} separator is the same as an empty String (""). Null objects or empty strings within the
     * array are represented by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array     the array of values to join together, may be null.
     * @param delimiter the separator character to use, null treated as "".
     * @return the joined String, {@code null} if null array input.
     */
    public static String join(final Object[] array, final String delimiter) {
        return array != null ? join(array, ObjectUtils.toString(delimiter), 0, array.length) : null;
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. A {@code null} separator is the same as an empty String (""). Null objects or empty strings within the
     * array are represented by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *, *, *)                = null
     * StringUtils.join([], *, *, *)                  = ""
     * StringUtils.join([null], *, *, *)              = ""
     * StringUtils.join(["a", "b", "c"], "--", 0, 3)  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], "--", 1, 3)  = "b--c"
     * StringUtils.join(["a", "b", "c"], "--", 2, 3)  = "c"
     * StringUtils.join(["a", "b", "c"], "--", 2, 2)  = ""
     * StringUtils.join(["a", "b", "c"], null, 0, 3)  = "abc"
     * StringUtils.join(["a", "b", "c"], "", 0, 3)    = "abc"
     * StringUtils.join([null, "", "a"], ',', 0, 3)   = ",,a"
     * </pre>
     *
     * @param array      the array of values to join together, may be null.
     * @param delimiter  the separator character to use, null treated as "".
     * @param startIndex the first index to start joining from.
     * @param endIndex   the index to stop joining from (exclusive).
     * @return the joined String, {@code null} if null array input; or the empty string if {@code endIndex - startIndex <= 0}. The number of joined entries is
     *         given by {@code endIndex - startIndex}.
     * @throws ArrayIndexOutOfBoundsException ife<br>
     *                                        {@code startIndex < 0} or <br>
     *                                        {@code startIndex >= array.length()} or <br>
     *                                        {@code endIndex < 0} or <br>
     *                                        {@code endIndex > array.length()}
     */
    public static String join(final Object[] array, final String delimiter, final int startIndex, final int endIndex) {
        return array != null ? Streams.of(array).skip(startIndex).limit(Math.max(0, endIndex - startIndex))
                .collect(LangCollectors.joining(delimiter, EMPTY, EMPTY, ObjectUtils::toString)) : null;
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)          = null
     * StringUtils.join([], *)            = ""
     * StringUtils.join([null], *)        = ""
     * StringUtils.join([1, 2, 3], ';')   = "1;2;3"
     * StringUtils.join([1, 2, 3], null)  = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final short[] array, final char delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
     * by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null, *)          = null
     * StringUtils.join([], *)            = ""
     * StringUtils.join([null], *)        = ""
     * StringUtils.join([1, 2, 3], ';')   = "1;2;3"
     * StringUtils.join([1, 2, 3], null)  = "123"
     * </pre>
     *
     * @param array
     *            the array of values to join together, may be null.
     * @param delimiter
     *            the separator character to use.
     * @param startIndex
     *            the first index to start joining from. It is an error to pass in a start index past the end of the
     *            array.
     * @param endIndex
     *            the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
     *            the array.
     * @return the joined String, {@code null} if null array input.
     * @since 3.2
     */
    public static String join(final short[] array, final char delimiter, final int startIndex, final int endIndex) {
        // See StringUtilsJoinBenchmark
        if (array == null) {
            return null;
        }
        final int count = endIndex - startIndex;
        if (count <= 0) {
            return EMPTY;
        }
        final byte maxElementChars = 6; // "-32768"
        final StringBuilder stringBuilder = capacity(count, maxElementChars);
        stringBuilder.append(array[startIndex]);
        for (int i = startIndex + 1; i < endIndex; i++) {
            stringBuilder.append(delimiter).append(array[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     *
     * <p>
     * No separator is added to the joined String. Null objects or empty strings within the array are represented by empty strings.
     * </p>
     *
     * <pre>
     * StringUtils.join(null)            = null
     * StringUtils.join([])              = ""
     * StringUtils.join([null])          = ""
     * StringUtils.join("a", "b", "c")   = "abc"
     * StringUtils.join(null, "", "a")   = "a"
     * </pre>
     *
     * @param <T>      the specific type of values to join together.
     * @param elements the values to join together, may be null.
     * @return the joined String, {@code null} if null array input.
     * @since 2.0
     * @since 3.0 Changed signature to use varargs
     */
    @SafeVarargs
    public static <T> String join(final T... elements) {
        return join(elements, null);
    }

    /**
     * Joins the elements of the provided varargs into a single String containing the provided elements.
     *
     * <p>
     * No delimiter is added before or after the list. {@code null} elements and separator are treated as empty Strings ("").
     * </p>
     *
     * <pre>
     * StringUtils.joinWith(",", "a", "b")        = "a,b"
     * StringUtils.joinWith(",", "a", "b","")     = "a,b,"
     * StringUtils.joinWith(",", "a", null, "b")  = "a,,b"
     * StringUtils.joinWith(null, "a", "b")       = "ab"
     * </pre>
     *
     * @param delimiter the separator character to use, null treated as "".
     * @param array     the varargs providing the values to join together. {@code null} elements are treated as "".
     * @return the joined String.
     * @throws IllegalArgumentException if a null varargs is provided.
     * @since 3.5
     */
    public static String joinWith(final String delimiter, final Object... array) {
        if (array == null) {
            throw new IllegalArgumentException("Object varargs must not be null");
        }
        return join(array, delimiter);
    }

    /**
     * Finds the last index within a CharSequence, handling {@code null}. This method uses {@link String#lastIndexOf(String)} if possible.
     *
     * <p>
     * A {@code null} CharSequence will return {@code -1}.
     * </p>
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *)          = -1
     * StringUtils.lastIndexOf(*, null)          = -1
     * StringUtils.lastIndexOf("", "")           = 0
     * StringUtils.lastIndexOf("aabaabaa", "a")  = 7
     * StringUtils.lastIndexOf("aabaabaa", "b")  = 5
     * StringUtils.lastIndexOf("aabaabaa", "ab") = 4
     * StringUtils.lastIndexOf("aabaabaa", "")   = 8
     * </pre>
     *
     * @param seq       the CharSequence to check, may be null.
     * @param searchSeq the CharSequence to find, may be null.
     * @return the last index of the search String, -1 if no match or {@code null} string input.
     * @since 2.0
     * @since 3.0 Changed signature from lastIndexOf(String, String) to lastIndexOf(CharSequence, CharSequence)
     * @deprecated Use {@link Strings#lastIndexOf(CharSequence, CharSequence) Strings.CS.lastIndexOf(CharSequence, CharSequence)}.
     */
    @Deprecated
    public static int lastIndexOf(final CharSequence seq, final CharSequence searchSeq) {
        return Strings.CS.lastIndexOf(seq, searchSeq);
    }

    /**
     * Finds the last index within a CharSequence, handling {@code null}. This method uses {@link String#lastIndexOf(String, int)} if possible.
     *
     * <p>
     * A {@code null} CharSequence will return {@code -1}. A negative start position returns {@code -1}. An empty ("") search CharSequence always matches unless
     * the start position is negative. A start position greater than the string length searches the whole string. The search starts at the startPos and works
     * backwards; matches starting after the start position are ignored.
     * </p>
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *, *)          = -1
     * StringUtils.lastIndexOf(*, null, *)          = -1
     * StringUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
     * StringUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
     * StringUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
     * StringUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
     * StringUtils.lastIndexOf("aabaabaa", "b", -1) = -1
     * StringUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
     * StringUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
     * StringUtils.lastIndexOf("aabaabaa", "b", 1)  = -1
     * StringUtils.lastIndexOf("aabaabaa", "b", 2)  = 2
     * StringUtils.lastIndexOf("aabaabaa", "ba", 2)  = 2
     * </pre>
     *
     * @param seq       the CharSequence to check, may be null.
     * @param searchSeq the CharSequence to find, may be null.
     * @param startPos  the start position, negative treated as zero.
     * @return the last index of the search CharSequence (always &le; startPos), -1 if no match or {@code null} string input.
     * @since 2.0
     * @since 3.0 Changed signature from lastIndexOf(String, String, int) to lastIndexOf(CharSequence, CharSequence, int)
     * @deprecated Use {@link Strings#lastIndexOf(CharSequence, CharSequence, int) Strings.CS.lastIndexOf(CharSequence, CharSequence, int)}.
     */
    @Deprecated
    public static int lastIndexOf(final CharSequence seq, final CharSequence searchSeq, final int startPos) {
        return Strings.CS.lastIndexOf(seq, searchSeq, startPos);
    }

    /**
     * Returns the index within {@code seq} of the last occurrence of the specified character. For values of {@code searchChar} in the range from 0 to 0xFFFF
     * (inclusive), the index (in Unicode code units) returned is the largest value <em>k</em> such that:
     *
     * <pre>
     * this.charAt(<em>k</em>) == searchChar
     * </pre>
     *
     * <p>
     * is true. For other values of {@code searchChar}, it is the largest value <em>k</em> such that:
     * </p>
     *
     * <pre>
     * this.codePointAt(<em>k</em>) == searchChar
     * </pre>
     *
     * <p>
     * is true. In either case, if no such character occurs in this string, then {@code -1} is returned. Furthermore, a {@code null} or empty ("")
     * {@link CharSequence} will return {@code -1}. The {@code seq} {@link CharSequence} object is searched backwards starting at the last character.
     * </p>
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *)         = -1
     * StringUtils.lastIndexOf("", *)           = -1
     * StringUtils.lastIndexOf("aabaabaa", 'a') = 7
     * StringUtils.lastIndexOf("aabaabaa", 'b') = 5
     * </pre>
     *
     * @param seq        the {@link CharSequence} to check, may be null.
     * @param searchChar the character to find.
     * @return the last index of the search character, -1 if no match or {@code null} string input.
     * @since 2.0
     * @since 3.0 Changed signature from lastIndexOf(String, int) to lastIndexOf(CharSequence, int)
     * @since 3.6 Updated {@link CharSequenceUtils} call to behave more like {@link String}
     */
    public static int lastIndexOf(final CharSequence seq, final int searchChar) {
        if (isEmpty(seq)) {
            return INDEX_NOT_FOUND;
        }
        return CharSequenceUtils.lastIndexOf(seq, searchChar, seq.length());
    }

    /**
     * Returns the index within {@code seq} of the last occurrence of the specified character, searching backward starting at the specified index. For values of
     * {@code searchChar} in the range from 0 to 0xFFFF (inclusive), the index returned is the largest value <em>k</em> such that:
     *
     * <pre>
     * (this.charAt(<em>k</em>) == searchChar) &amp;&amp; (<em>k</em> &lt;= startPos)
     * </pre>
     *
     * <p>
     * is true. For other values of {@code searchChar}, it is the largest value <em>k</em> such that:
     * </p>
     *
     * <pre>
     * (this.codePointAt(<em>k</em>) == searchChar) &amp;&amp; (<em>k</em> &lt;= startPos)
     * </pre>
     *
     * <p>
     * is true. In either case, if no such character occurs in {@code seq} at or before position {@code startPos}, then {@code -1} is returned. Furthermore, a
     * {@code null} or empty ("") {@link CharSequence} will return {@code -1}. A start position greater than the string length searches the whole string. The
     * search starts at the {@code startPos} and works backwards; matches starting after the start position are ignored.
     * </p>
     *
     * <p>
     * All indices are specified in {@code char} values (Unicode code units).
     * </p>
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *, *)          = -1
     * StringUtils.lastIndexOf("", *,  *)           = -1
     * StringUtils.lastIndexOf("aabaabaa", 'b', 8)  = 5
     * StringUtils.lastIndexOf("aabaabaa", 'b', 4)  = 2
     * StringUtils.lastIndexOf("aabaabaa", 'b', 0)  = -1
     * StringUtils.lastIndexOf("aabaabaa", 'b', 9)  = 5
     * StringUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
     * StringUtils.lastIndexOf("aabaabaa", 'a', 0)  = 0
     * </pre>
     *
     * @param seq        the CharSequence to check, may be null.
     * @param searchChar the character to find.
     * @param startPos   the start position.
     * @return the last index of the search character (always &le; startPos), -1 if no match or {@code null} string input.
     * @since 2.0
     * @since 3.0 Changed signature from lastIndexOf(String, int, int) to lastIndexOf(CharSequence, int, int)
     */
    public static int lastIndexOf(final CharSequence seq, final int searchChar, final int startPos) {
        if (isEmpty(seq)) {
            return INDEX_NOT_FOUND;
        }
        return CharSequenceUtils.lastIndexOf(seq, searchChar, startPos);
    }

    /**
     * Finds the latest index of any substring in a set of potential substrings.
     *
     * <p>
     * A {@code null} CharSequence will return {@code -1}. A {@code null} search array will return {@code -1}. A {@code null} or zero length search array entry
     * will be ignored, but a search array containing "" will return the length of {@code str} if {@code str} is not null. This method uses
     * {@link String#indexOf(String)} if possible
     * </p>
     *
     * <pre>
     * StringUtils.lastIndexOfAny(null, *)                    = -1
     * StringUtils.lastIndexOfAny(*, null)                    = -1
     * StringUtils.lastIndexOfAny(*, [])                      = -1
     * StringUtils.lastIndexOfAny(*, [null])                  = -1
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["ab", "cd"]) = 6
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["cd", "ab"]) = 6
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", "op"]) = -1
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", "op"]) = -1
     * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", ""])   = 10
     * </pre>
     *
     * @param str        the CharSequence to check, may be null.
     * @param searchStrs the CharSequences to search for, may be null.
     * @return the last index of any of the CharSequences, -1 if no match.
     * @since 3.0 Changed signature from lastIndexOfAny(String, String[]) to lastIndexOfAny(CharSequence, CharSequence)
     */
    public static int lastIndexOfAny(final CharSequence str, final CharSequence... searchStrs) {
        if (str == null || searchStrs == null) {
            return INDEX_NOT_FOUND;
        }
        int ret = INDEX_NOT_FOUND;
        int tmp;
        for (final CharSequence search : searchStrs) {
            if (search == null) {
                continue;
            }
            tmp = CharSequenceUtils.lastIndexOf(str, search, str.length());
            if (tmp > ret) {
                ret = tmp;
            }
        }
        return ret;
    }

    /**
     * Case in-sensitive find of the last index within a CharSequence.
     *
     * <p>
     * A {@code null} CharSequence will return {@code -1}. A negative start position returns {@code -1}. An empty ("") search CharSequence always matches unless
     * the start position is negative. A start position greater than the string length searches the whole string.
     * </p>
     *
     * <pre>
     * StringUtils.lastIndexOfIgnoreCase(null, *)          = -1
     * StringUtils.lastIndexOfIgnoreCase(*, null)          = -1
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A")  = 7
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B")  = 5
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB") = 4
     * </pre>
     *
     * @param str       the CharSequence to check, may be null.
     * @param searchStr the CharSequence to find, may be null.
     * @return the first index of the search CharSequence, -1 if no match or {@code null} string input.
     * @since 2.5
     * @since 3.0 Changed signature from lastIndexOfIgnoreCase(String, String) to lastIndexOfIgnoreCase(CharSequence, CharSequence)
     * @deprecated Use {@link Strings#lastIndexOf(CharSequence, CharSequence) Strings.CI.lastIndexOf(CharSequence, CharSequence)}.
     */
    @Deprecated
    public static int lastIndexOfIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        return Strings.CI.lastIndexOf(str, searchStr);
    }

    /**
     * Case in-sensitive find of the last index within a CharSequence from the specified position.
     *
     * <p>
     * A {@code null} CharSequence will return {@code -1}. A negative start position returns {@code -1}. An empty ("") search CharSequence always matches unless
     * the start position is negative. A start position greater than the string length searches the whole string. The search starts at the startPos and works
     * backwards; matches starting after the start position are ignored.
     * </p>
     *
     * <pre>
     * StringUtils.lastIndexOfIgnoreCase(null, *, *)          = -1
     * StringUtils.lastIndexOfIgnoreCase(*, null, *)          = -1
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 8)  = 7
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 8)  = 5
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB", 8) = 4
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 9)  = 5
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", -1) = -1
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 0)  = -1
     * </pre>
     *
     * @param str       the CharSequence to check, may be null.
     * @param searchStr the CharSequence to find, may be null.
     * @param startPos  the start position.
     * @return the last index of the search CharSequence (always &le; startPos), -1 if no match or {@code null} input.
     * @since 2.5
     * @since 3.0 Changed signature from lastIndexOfIgnoreCase(String, String, int) to lastIndexOfIgnoreCase(CharSequence, CharSequence, int)
     * @deprecated Use {@link Strings#lastIndexOf(CharSequence, CharSequence, int) Strings.CI.lastIndexOf(CharSequence, CharSequence, int)}.
     */
    @Deprecated
    public static int lastIndexOfIgnoreCase(final CharSequence str, final CharSequence searchStr, final int startPos) {
        return Strings.CI.lastIndexOf(str, searchStr, startPos);
    }

    /**
     * Finds the n-th last index within a String, handling {@code null}. This method uses {@link String#lastIndexOf(String)}.
     *
     * <p>
     * A {@code null} String will return {@code -1}.
     * </p>
     *
     * <pre>
     * StringUtils.lastOrdinalIndexOf(null, *, *)          = -1
     * StringUtils.lastOrdinalIndexOf(*, null, *)          = -1
     * StringUtils.lastOrdinalIndexOf("", "", *)           = 0
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 1)  = 7
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 2)  = 6
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 1)  = 5
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 2)  = 2
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 1) = 4
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2) = 1
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 1)   = 8
     * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 2)   = 8
     * </pre>
     *
     * <p>
     * Note that 'tail(CharSequence str, int n)' may be implemented as:
     * </p>
     *
     * <pre>
     * str.substring(lastOrdinalIndexOf(str, "\n", n) + 1)
     * </pre>
     *
     * @param str       the CharSequence to check, may be null.
     * @param searchStr the CharSequence to find, may be null.
     * @param ordinal   the n-th last {@code searchStr} to find.
     * @return the n-th last index of the search CharSequence, {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} string input.
     * @since 2.5
     * @since 3.0 Changed signature from lastOrdinalIndexOf(String, String, int) to lastOrdinalIndexOf(CharSequence, CharSequence, int)
     */
    public static int lastOrdinalIndexOf(final CharSequence str, final CharSequence searchStr, final int ordinal) {
        return ordinalIndexOf(str, searchStr, ordinal, true);
    }

    /**
     * Gets the leftmost {@code len} characters of a String.
     *
     * <p>
     * If {@code len} characters are not available, or the String is {@code null}, the String will be returned without an exception. An empty String is returned
     * if len is negative.
     * </p>
     *
     * <pre>
     * StringUtils.left(null, *)    = null
     * StringUtils.left(*, -ve)     = ""
     * StringUtils.left("", *)      = ""
     * StringUtils.left("abc", 0)   = ""
     * StringUtils.left("abc", 2)   = "ab"
     * StringUtils.left("abc", 4)   = "abc"
     * </pre>
     *
     * @param str the String to get the leftmost characters from, may be null.
     * @param len the length of the required String.
     * @return the leftmost characters, {@code null} if null String input.
     */
    public static String left(final String str, final int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    /**
     * Left pad a String with spaces (' ').
     *
     * <p>
     * The String is padded to the size of {@code size}.
     * </p>
     *
     * <pre>
     * StringUtils.leftPad(null, *)   = null
     * StringUtils.leftPad("", 3)     = "   "
     * StringUtils.leftPad("bat", 3)  = "bat"
     * StringUtils.leftPad("bat", 5)  = "  bat"
     * StringUtils.leftPad("bat", 1)  = "bat"
     * StringUtils.leftPad("bat", -1) = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null.
     * @param size the size to pad to.
     * @return left padded String or original String if no padding is necessary, {@code null} if null String input.
     */
    public static String leftPad(final String str, final int size) {
        return leftPad(str, size, ' ');
    }

    /**
     * Left pad a String with a specified character.
     *
     * <p>
     * Pad to a size of {@code size}.
     * </p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)     = null
     * StringUtils.leftPad("", 3, 'z')     = "zzz"
     * StringUtils.leftPad("bat", 3, 'z')  = "bat"
     * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
     * StringUtils.leftPad("bat", 1, 'z')  = "bat"
     * StringUtils.leftPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str     the String to pad out, may be null.
     * @param size    the size to pad to.
     * @param padChar the character to pad with.
     * @return left padded String or original String if no padding is necessary, {@code null} if null String input.
     * @since 2.0
     */
    public static String leftPad(final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return repeat(padChar, pads).concat(str);
    }

    /**
     * Left pad a String with a specified String.
     *
     * <p>
     * Pad to a size of {@code size}.
     * </p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)      = null
     * StringUtils.leftPad("", 3, "z")      = "zzz"
     * StringUtils.leftPad("bat", 3, "yz")  = "bat"
     * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
     * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
     * StringUtils.leftPad("bat", 1, "yz")  = "bat"
     * StringUtils.leftPad("bat", -1, "yz") = "bat"
     * StringUtils.leftPad("bat", 5, null)  = "  bat"
     * StringUtils.leftPad("bat", 5, "")    = "  bat"
     * </pre>
     *
     * @param str    the String to pad out, may be null.
     * @param size   the size to pad to.
     * @param padStr the String to pad with, null or empty treated as single space.
     * @return left padded String or original String if no padding is necessary, {@code null} if null String input.
     */
    public static String leftPad(final String str, final int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = SPACE;
        }
        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }
        if (pads == padLen) {
            return padStr.concat(str);
        }
        if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        }
        final char[] padding = new char[pads];
        final char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; i++) {
            padding[i] = padChars[i % padLen];
        }
        return new String(padding).concat(str);
    }

    /**
     * Gets a CharSequence length or {@code 0} if the CharSequence is {@code null}.
     *
     * @param cs a CharSequence or {@code null}.
     * @return CharSequence length or {@code 0} if the CharSequence is {@code null}.
     * @since 2.4
     * @since 3.0 Changed signature from length(String) to length(CharSequence)
     */
    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /**
     * Converts a String to lower case as per {@link String#toLowerCase()}.
     *
     * <p>
     * A {@code null} input String returns {@code null}.
     * </p>
     *
     * <pre>
     * StringUtils.lowerCase(null)  = null
     * StringUtils.lowerCase("")    = ""
     * StringUtils.lowerCase("aBc") = "abc"
     * </pre>
     *
     * <p>
     * <strong>Note:</strong> As described in the documentation for {@link String#toLowerCase()}, the result of this method is affected by the current locale.
     * For platform-independent case transformations, the method {@link #lowerCase(String, Locale)} should be used with a specific locale (e.g.
     * {@link Locale#ENGLISH}).
     * </p>
     *
     * @param str the String to lower case, may be null.
     * @return the lower cased String, {@code null} if null String input.
     */
    public static String lowerCase(final String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }

    /**
     * Converts a String to lower case as per {@link String#toLowerCase(Locale)}.
     *
     * <p>
     * A {@code null} input String returns {@code null}.
     * </p>
     *
     * <pre>
     * StringUtils.lowerCase(null, Locale.ENGLISH)  = null
     * StringUtils.lowerCase("", Locale.ENGLISH)    = ""
     * StringUtils.lowerCase("aBc", Locale.ENGLISH) = "abc"
     * </pre>
     *
     * @param str    the String to lower case, may be null.
     * @param locale the locale that defines the case transformation rules, must not be null.
     * @return the lower cased String, {@code null} if null String input.
     * @since 2.5
     */
    public static String lowerCase(final String str, final Locale locale) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase(LocaleUtils.toLocale(locale));
    }

    private static int[] matches(final CharSequence first, final CharSequence second) {
        final CharSequence max;
        final CharSequence min;
        if (first.length() > second.length()) {
            max = first;
            min = second;
        } else {
            max = second;
            min = first;
        }
        final int range = Math.max(max.length() / 2 - 1, 0);
        final int[] matchIndexes = ArrayFill.fill(new int[min.length()], -1);
        final boolean[] matchFlags = new boolean[max.length()];
        int matches = 0;
        for (int mi = 0; mi < min.length(); mi++) {
            final char c1 = min.charAt(mi);
            for (int xi = Math.max(mi - range, 0), xn = Math.min(mi + range + 1, max.length()); xi < xn; xi++) {
                if (!matchFlags[xi] && c1 == max.charAt(xi)) {
                    matchIndexes[mi] = xi;
                    matchFlags[xi] = true;
                    matches++;
                    break;
                }
            }
        }
        final char[] ms1 = new char[matches];
        final char[] ms2 = new char[matches];
        for (int i = 0, si = 0; i < min.length(); i++) {
            if (matchIndexes[i] != -1) {
                ms1[si] = min.charAt(i);
                si++;
            }
        }
        for (int i = 0, si = 0; i < max.length(); i++) {
            if (matchFlags[i]) {
                ms2[si] = max.charAt(i);
                si++;
            }
        }
        int transpositions = 0;
        for (int mi = 0; mi < ms1.length; mi++) {
            if (ms1[mi] != ms2[mi]) {
                transpositions++;
            }
        }
        int prefix = 0;
        for (int mi = 0; mi < min.length(); mi++) {
            if (first.charAt(mi) != second.charAt(mi)) {
                break;
            }
            prefix++;
        }
        return new int[] { matches, transpositions / 2, prefix, max.length() };
    }

    /**
     * Gets {@code len} characters from the middle of a String.
     *
     * <p>
     * If {@code len} characters are not available, the remainder of the String will be returned without an exception. If the String is {@code null},
     * {@code null} will be returned. An empty String is returned if len is negative or exceeds the length of {@code str}.
     * </p>
     *
     * <pre>
     * StringUtils.mid(null, *, *)    = null
     * StringUtils.mid(*, *, -ve)     = ""
     * StringUtils.mid("", 0, *)      = ""
     * StringUtils.mid("abc", 0, 2)   = "ab"
     * StringUtils.mid("abc", 0, 4)   = "abc"
     * StringUtils.mid("abc", 2, 4)   = "c"
     * StringUtils.mid("abc", 4, 2)   = ""
     * StringUtils.mid("abc", -2, 2)  = "ab"
     * </pre>
     *
     * @param str the String to get the characters from, may be null.
     * @param pos the position to start from, negative treated as zero.
     * @param len the length of the required String.
     * @return the middle characters, {@code null} if null String input.
     */
    public static String mid(final String str, int pos, final int len) {
        if (str == null) {
            return null;
        }
        if (len < 0 || pos > str.length()) {
            return EMPTY;
        }
        if (pos < 0) {
            pos = 0;
        }
        if (str.length() <= pos + len) {
            return str.substring(pos);
        }
        return str.substring(pos, pos + len);
    }

    /**
     * Similar to <a href="https://www.w3.org/TR/xpath/#function-normalize-space">https://www.w3.org/TR/xpath/#function-normalize -space</a>
     *
     * <p>
     * This function returns the argument string with whitespace normalized by using {@code {@link #trim(String)}} to remove leading and trailing whitespace and
     * then replacing sequences of whitespace characters by a single space.
     * </p>
     * In XML, whitespace characters are the same as those allowed by the <a href="https://www.w3.org/TR/REC-xml/#NT-S">S</a> production, which is S ::= (#x20 |
     * #x9 | #xD | #xA)+
     * <p>
     * Java's regexp pattern \s defines whitespace as [ \t\n\x0B\f\r]
     * </p>
     * <p>
     * For reference:
     * </p>
     * <ul>
     * <li>\x0B = vertical tab</li>
     * <li>\f = #xC = form feed</li>
     * <li>#x20 = space</li>
     * <li>#x9 = \t</li>
     * <li>#xA = \n</li>
     * <li>#xD = \r</li>
     * </ul>
     *
     * <p>
     * The difference is that Java's whitespace includes vertical tab and form feed, which this function will also normalize. Additionally {@code {@link
     * #trim(String)}} removes control characters (char &lt;= 32) from both ends of this String.
     * </p>
     *
     * @param str the source String to normalize whitespaces from, may be null.
     * @return the modified string with whitespace normalized, {@code null} if null String input.
     * @see Pattern
     * @see #trim(String)
     * @see <a href="https://www.w3.org/TR/xpath/#function-normalize-space">https://www.w3.org/TR/xpath/#function-normalize-space</a>
     * @since 3.0
     */
    public static String normalizeSpace(final String str) {
        // LANG-1020: Improved performance significantly by normalizing manually instead of using regex
        // See https://github.com/librucha/commons-lang-normalizespaces-benchmark for performance test
        if (isEmpty(str)) {
            return str;
        }
        final int size = str.length();
        final char[] newChars = new char[size];
        int count = 0;
        int whitespacesCount = 0;
        boolean startWhitespaces = true;
      System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        if (startWhitespaces) {
            return EMPTY;
        }
        return new String(newChars, 0, count - (whitespacesCount > 0 ? 1 : 0)).trim();
    }


     private static int ordinalIndexOf(final CharSequence str, final CharSequence searchStr, final int ordinal, final boolean lastIndex) {

        return 1;
    }

}

