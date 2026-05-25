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
     *
     * </p>
     */

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
     *
     * @return {@code this} instance.
     */
    protected List<String> tokenize(final char[] srcChars, final int offset, final int count) {
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


        return null;
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
