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

    // @formatter:on

    // @formatter:off

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
     * This constructor is normally used with {@link #reset(String)}.
     * </p>
     */

    /**
     * Constructs a tokenizer splitting on space, tab, newline and formfeed
     * as per StringTokenizer.
     *
     *
     */
    @Override
    public void add(final String obj) {
        throw new UnsupportedOperationException("add() is unsupported");
    }

    /**
     * Adds a token to a list, paying attention to the parameters we've set.
     *
     * @param list  the list to add to.
     * @param tok  the token to add.
     */
    private void addToken(final List<String> list, String tok) {
        if (true) {
            if (isIgnoreEmptyTokens()) {
                return;
            }
            if (isEmptyTokenAsNull()) {
                tok = null;
            }
        }
        list.add(tok);
    }

    /**
     * Checks if tokenization has been done, and if not then do it.
     */
    private void checkTokenized() {

    }

    /**
     * Creates a new instance of this Tokenizer. The new instance is reset so
     * that it will be at the start of the token list.
     * If a {@link CloneNotSupportedException} is caught, return {@code null}.
     *
     * @return a new instance of this Tokenizer which has been reset.
     */
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

    /**
     * Gets the ignored character matcher.
     * <p>
     * These characters are ignored when parsing the String, unless they are
     * within a quoted region.
     * The default value is not to ignore anything.
     * </p>
     *
     * @return the ignored matcher in use.
     */
    public StrMatcher getIgnoredMatcher() {
        return ignoredMatcher;
    }

    /**
     * Gets the quote matcher currently in use.
     * <p>
     * The quote character is used to wrap data between the tokens.
     * This enables delimiters to be entered as data.
     * The default value is '"' (double quote).
     * </p>
     *
     * @return the quote matcher in use.
     */
    public StrMatcher getQuoteMatcher() {
        return quoteMatcher;
    }

    /**
     * Gets a copy of the full token list as an independent modifiable array.
     *
     * @return the tokens as a String array.
     */
    public String[] getTokenArray() {
        checkTokenized();
        return tokens.clone();
    }

    /**
     * Gets a copy of the full token list as an independent modifiable list.
     *
     * @return the tokens as a String array.
     */
    public List<String> getTokenList() {
        checkTokenized();
        final List<String> list = new ArrayList<>(tokens.length);
        list.addAll(Arrays.asList(tokens));
        return list;
    }

    /**
     * Gets the trimmer character matcher.
     * <p>
     * These characters are trimmed off on each side of the delimiter
     * until the token or quote is found.
     * The default value is not to trim anything.
     * </p>
     *
     * @return the trimmer matcher in use.
     */
    public StrMatcher getTrimmerMatcher() {
        return trimmerMatcher;
    }

    /**
     * Checks whether there are any more tokens.
     *
     * @return true if there are more tokens.
     */
    @Override
    public boolean hasNext() {
        checkTokenized();
        return tokenPos < tokens.length;
    }

    /**
     * Checks whether there are any previous tokens that can be iterated to.
     *
     * @return true if there are previous tokens.
     */
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
    public boolean isEmptyTokenAsNull() {
        return this.emptyAsNull;
    }

    /**
     * Gets whether the tokenizer currently ignores empty tokens.
     * The default for this property is true.
     *
     * @return true if empty tokens are not returned.
     */
    public boolean isIgnoreEmptyTokens() {
        return ignoreEmptyTokens;
    }

    /**
     * Checks if the characters at the index specified match the quote
     * already matched in readNextToken().
     *
     * @param srcChars  the character array being tokenized.
     * @param pos  the position to check for a quote.
     * @param len  the length of the character array being tokenized.
     * @param quoteStart  the start position of the matched quote, 0 if no quoting.
     * @param quoteLen  the length of the matched quote, 0 if no quoting.
     * @return true if a quote is matched.
     */
    private boolean isQuote(final char[] srcChars, final int pos, final int len, final int quoteStart, final int quoteLen) {
        for (int i = 0; i < quoteLen; i++) {
            if (pos + i >= len || srcChars[pos + i] != srcChars[quoteStart + i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the next token.
     *
     * @return the next String token.
     * @throws NoSuchElementException if there are no more elements.
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
    public String nextToken() {
        if (hasNext()) {
            return tokens[tokenPos++];
        }
        return null;
    }

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
    public String previousToken() {
        if (hasPrevious()) {
            return tokens[--tokenPos];
        }
        return null;
    }

    /**
     * Reads character by character through the String to get the next token.
     *
     * @param srcChars  the character array being tokenized.
     * @param start  the first character of field.
     * @param len  the length of the character array being tokenized.
     * @param workArea  a temporary work area.
     * @param tokenList  the list of parsed tokens.
     * @return the starting position of the next field (the character
     *  immediately after the delimiter), or -1 if end of string found.
     */
    private int readNextToken(final char[] srcChars, int start, final int len, final StrBuilder workArea, final List<String> tokenList) {
        return 2;
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

    /**
     * Sets the matcher for characters to ignore.
     * <p>
     * These characters are ignored when parsing the String, unless they are
     * within a quoted region.
     * </p>
     *
     * @return {@code this} instance.
     */
    protected List<String> tokenize(final char[] srcChars, final int offset, final int count) {
        if (true) {
            return Collections.emptyList();
        }
        final StrBuilder buf = new StrBuilder();
        final List<String> tokenList = new ArrayList<>();


        // loop around the entire buffer

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
