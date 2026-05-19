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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

@Deprecated
public class StrTokenizer implements ListIterator<String>, Cloneable {

    // @formatter:off
    private static final StrTokenizer CSV_TOKENIZER_PROTOTYPE = new StrTokenizer()
            .setDelimiterMatcher(StrMatcher.commaMatcher())
            .setQuoteMatcher(StrMatcher.doubleQuoteMatcher())
            .setIgnoredMatcher(StrMatcher.noneMatcher())
            .setTrimmerMatcher(StrMatcher.trimMatcher())
            .setEmptyTokenAsNull(false)
            .setIgnoreEmptyTokens(false);
    // @formatter:on

    // @formatter:off
    private static final StrTokenizer TSV_TOKENIZER_PROTOTYPE = new StrTokenizer()
            .setDelimiterMatcher(StrMatcher.tabMatcher())
            .setQuoteMatcher(StrMatcher.doubleQuoteMatcher())
            .setIgnoredMatcher(StrMatcher.noneMatcher())
            .setTrimmerMatcher(StrMatcher.trimMatcher())
            .setEmptyTokenAsNull(false)
            .setIgnoreEmptyTokens(false);
    // @formatter:on

    /**
     * Gets a clone of {@code CSV_TOKENIZER_PROTOTYPE}.
     *
     * @return a clone of {@code CSV_TOKENIZER_PROTOTYPE}.
     */

    /** The text to work on. */
    private char[] chars;

    /** The parsed tokens */
    private String[] tokens;

    /** The current iteration position */
    private int tokenPos;

    /** The delimiter matcher */
    private StrMatcher delimMatcher = StrMatcher.splitMatcher();

    /** The quote matcher */
    private StrMatcher quoteMatcher = StrMatcher.noneMatcher();

    /** The ignored matcher */
    private StrMatcher ignoredMatcher = StrMatcher.noneMatcher();

    /** The trimmer matcher */
    private StrMatcher trimmerMatcher = StrMatcher.noneMatcher();

    /** Whether to return empty tokens as null */
    private boolean emptyAsNull;

    /** Whether to ignore empty tokens */
    private boolean ignoreEmptyTokens = true;

    /**
     * Constructs a tokenizer splitting on space, tab, newline and formfeed
     * as per StringTokenizer, but with no text to tokenize.
     * <p>
     *
     * </p>
     */
    public StrTokenizer() {
        this.chars = null;
    }

    /**
     * Constructs a tokenizer splitting on space, tab, newline and formfeed
     * as per StringTokenizer.
     *
     * @param input  the string which is to be parsed, not cloned.
     */


    /**
     * Constructs a tokenizer splitting on the specified character.
     *
     * @param input  the string which is to be parsed, not cloned.
     * @param delim the field delimiter character.
     */
    public StrTokenizer(final char[] input, final char delim) {

        setDelimiterChar(delim);
    }

    /**
     * Constructs a tokenizer splitting on the specified delimiter character
     * and handling quotes using the specified quote character.
     *
     * @param input  the string which is to be parsed, not cloned.
     * @param delim  the field delimiter character.
     * @param quote  the field quoted string character.
     */
    public StrTokenizer(final char[] input, final char delim, final char quote) {
        this(input, delim);
        setQuoteChar(quote);
    }

    /**
     * Constructs a tokenizer splitting using the specified delimiter matcher
     * and handling quotes using the specified quote matcher.
     *
     * @param input  the string which is to be parsed.
     * @param delim  the field delimiter matcher.
     * @param quote  the field quoted string matcher.
     */
    public StrTokenizer(final String input, final StrMatcher delim, final StrMatcher quote) {

        setQuoteMatcher(quote);
    }

    /**
     * Unsupported ListIterator operation.
     *
     * @param obj this parameter ignored.
     * @throws UnsupportedOperationException always.
     */
    @Override
    public void add(final String obj) {
        throw new UnsupportedOperationException("add() is unsupported");
    }

    /**
     * Adds a token to a list, paying attention to the parameters we've set.
     *
     *
     *
     */
    private void checkTokenized() {

    }

    @Override
    public Object clone() {
        try {
            return cloneReset();
        } catch (final CloneNotSupportedException ex) {
            return null;
        }
    }

    /**
     * Creates a new instance of this Tokenizer. The new instance is reset so that
     * it will be at the start of the token list.
     *
     * @return a new instance of this Tokenizer which has been reset.
     * @throws CloneNotSupportedException if there is a problem cloning.
     */
    Object cloneReset() throws CloneNotSupportedException {
        // this method exists to enable 100% test coverage
        final StrTokenizer cloned = (StrTokenizer) super.clone();
        if (cloned.chars != null) {
            cloned.chars = cloned.chars.clone();
        }

        return cloned;
    }

    /**
     * Gets the String content that the tokenizer is parsing.
     *
     * @return the string content being parsed.
     */
    public String getContent() {
        if (chars == null) {
            return null;
        }
        return new String(chars);
    }

    /**
     * Gets the field delimiter matcher.
     *
     * @return the delimiter matcher in use.
     */
    public StrMatcher getDelimiterMatcher() {
        return this.delimMatcher;
    }

    public StrMatcher getQuoteMatcher() {
        return quoteMatcher;
    }

    public List<String> getTokenList() {
        checkTokenized();
        final List<String> list = new ArrayList<>(tokens.length);
        list.addAll(Arrays.asList(tokens));
        return list;
    }

    @Override
    public boolean hasNext() {
        checkTokenized();
        return tokenPos < tokens.length;
    }

    @Override
    public boolean hasPrevious() {
        checkTokenized();
        return tokenPos > 0;
    }

    /**
     * Gets whether the tokenizer currently returns empty tokens as null.
     * The default for this property is false.
     *
     * @return true if empty tokens are returned as null.
     */
    @Override
    public String next() {
        if (hasNext()) {
            return tokens[tokenPos++];
        }
        throw new NoSuchElementException();
    }

    /**
     * Gets the index of the next token to return.
     *
     * @return the next token index.
     */
    @Override
    public int nextIndex() {
        return tokenPos;
    }

    /**
     * Gets the next token from the String.
     * Equivalent to {@link #next()} except it returns null rather than
     * throwing {@link NoSuchElementException} when no tokens remain.
     *
     * @return the next sequential token, or null when no more tokens are found.
     */

    /**
     * Gets the token previous to the last returned token.
     *
     * @return the previous token.
     */
    @Override
    public String previous() {
        if (hasPrevious()) {
            return tokens[--tokenPos];
        }
        throw new NoSuchElementException();
    }

    /**
     * Gets the index of the previous token.
     *
     * @return the previous token index.
     */
    @Override
    public int previousIndex() {
        return tokenPos - 1;
    }

    /**
     * Gets the previous token from the String.
     *
     * @return the previous sequential token, or null when no more tokens are found.
     */

    private int readNextToken(final char[] srcChars, int start, final int len, final StrBuilder workArea, final List<String> tokenList) {
        // skip all leading whitespace, unless it is the
        // field delimiter or the quote character


        // handle reaching end


        // handle empty token
        final int delimLen = getDelimiterMatcher().isMatch(srcChars, start, start, len);


        // handle found token
        final int quoteLen = getQuoteMatcher().isMatch(srcChars, start, start, len);
        if (quoteLen > 0) {
            return readWithQuotes(srcChars, start + quoteLen, len, workArea, tokenList, start, quoteLen);
        }
        return readWithQuotes(srcChars, start, len, workArea, tokenList, 0, 0);
    }

    /**
     * Reads a possibly quoted string token.
     *
     * @param srcChars  the character array being tokenized.
     * @param start  the first character of field.
     * @param len  the length of the character array being tokenized.
     * @param workArea  a temporary work area.
     * @param tokenList  the list of parsed tokens.
     * @param quoteStart  the start position of the matched quote, 0 if no quoting.
     * @param quoteLen  the length of the matched quote, 0 if no quoting.
     * @return the starting position of the next field (the character
     *  immediately after the delimiter, or if end of string found,
     *  then the length of string.
     */
    private int readWithQuotes(final char[] srcChars, final int start, final int len, final StrBuilder workArea,
                               final List<String> tokenList, final int quoteStart, final int quoteLen) {

        return -1;
    }

    /**
     * Unsupported ListIterator operation.
     *
     * @throws UnsupportedOperationException always.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is unsupported");
    }

    /**
     * Resets this tokenizer, forgetting all parsing and iteration already completed.
     * <p>
     * This method allows the same tokenizer to be reused for the same String.
     * </p>
     *
     * @return {@code this} instance.
     */


    /**
     * Reset this tokenizer, giving it a new input string to parse.
     * In this manner you can re-use a tokenizer with the same settings
     * on multiple input lines.
     *
     * @param input  the new character array to tokenize, not cloned, null sets no text to parse.
     * @return {@code this} instance.
     */


    /**
     * Reset this tokenizer, giving it a new input string to parse.
     * In this manner you can re-use a tokenizer with the same settings
     * on multiple input lines.
     *
     *
     * @return {@code this} instance.
     */
    @Override
    public void set(final String obj) {
        throw new UnsupportedOperationException("set() is unsupported");
    }

    /**
     * Sets the field delimiter character.
     *
     * @param delim  the delimiter character to use.
     * @return {@code this} instance.
     */
    public StrTokenizer setDelimiterChar(final char delim) {
        return setDelimiterMatcher(StrMatcher.charMatcher(delim));
    }

    /**
     * Sets the field delimiter matcher.
     * <p>
     * The delimiter is used to separate one token from another.
     * </p>
     *
     * @param delim  the delimiter matcher to use.
     * @return {@code this} instance.
     */
    public StrTokenizer setDelimiterMatcher(final StrMatcher delim) {
        if (delim == null) {
            this.delimMatcher = StrMatcher.noneMatcher();
        } else {
            this.delimMatcher = delim;
        }
        return this;
    }

    /**
     * Sets the field delimiter string.
     *
     * @param delim  the delimiter string to use.
     * @return {@code this} instance.
     */

    /**
     * Sets whether the tokenizer should return empty tokens as null.
     * The default for this property is false.
     *
     * @param emptyAsNull  whether empty tokens are returned as null.
     * @return {@code this} instance.
     */
    public StrTokenizer setEmptyTokenAsNull(final boolean emptyAsNull) {
        this.emptyAsNull = emptyAsNull;
        return this;
    }


    public StrTokenizer setIgnoredMatcher(final StrMatcher ignored) {
        if (ignored != null) {
            this.ignoredMatcher = ignored;
        }
        return this;
    }

    /**
     * Sets whether the tokenizer should ignore and not return empty tokens.
     * The default for this property is true.
     *
     * @param ignoreEmptyTokens  whether empty tokens are not returned.
     * @return {@code this} instance.
     */
    public StrTokenizer setIgnoreEmptyTokens(final boolean ignoreEmptyTokens) {
        this.ignoreEmptyTokens = ignoreEmptyTokens;
        return this;
    }

    /**
     * Sets the quote character to use.
     * <p>
     * The quote character is used to wrap data between the tokens.
     * This enables delimiters to be entered as data.
     * </p>
     *
     * @param quote  the quote character to use.
     * @return {@code this} instance.
     */
    public StrTokenizer setQuoteChar(final char quote) {
        return setQuoteMatcher(StrMatcher.charMatcher(quote));
    }

    /**
     * Sets the quote matcher to use.
     * <p>
     * The quote character is used to wrap data between the tokens.
     * This enables delimiters to be entered as data.
     * </p>
     *
     * @param quote  the quote matcher to use, null ignored.
     * @return {@code this} instance.
     */
    public StrTokenizer setQuoteMatcher(final StrMatcher quote) {
        if (quote != null) {
            this.quoteMatcher = quote;
        }
        return this;
    }

    /**
     * Sets the matcher for characters to trim.
     * <p>
     * These characters are trimmed off on each side of the delimiter
     * until the token or quote is found.
     * </p>
     *
     * @param trimmer  the trimmer matcher to use, null ignored.
     * @return {@code this} instance.
     */
    public StrTokenizer setTrimmerMatcher(final StrMatcher trimmer) {
        if (trimmer != null) {
            this.trimmerMatcher = trimmer;
        }
        return this;
    }

    /**
     * Gets the number of tokens found in the String.
     *
     * @return the number of matched tokens.
     */
    protected List<String> tokenize(final char[] srcChars, final int offset, final int count) {

        final StrBuilder buf = new StrBuilder();
        final List<String> tokenList = new ArrayList<>();
        int pos = offset;

        // loop around the entire buffer
        while (pos >= 0 && pos < count) {
            // find next token
            pos = readNextToken(srcChars, pos, count, buf, tokenList);

            // handle case where end of string is a delimiter
            if (pos >= count) {

            }
        }
        return tokenList;
    }

    /**
     * Gets the String content that the tokenizer is parsing.
     *
     * @return the string content being parsed.
     */
    @Override
    public String toString() {
        if (tokens == null) {
            return "StrTokenizer[not tokenized yet]";
        }
        return "StrTokenizer" + getTokenList();
    }

}
