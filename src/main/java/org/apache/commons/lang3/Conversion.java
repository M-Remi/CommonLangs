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

import java.util.UUID;

public class Conversion {

    private static final boolean[] TTTT = { true, true, true, true };
    private static final boolean[] FTTT = { false, true, true, true };
    private static final boolean[] TFTT = { true, false, true, true };
    private static final boolean[] FFTT = { false, false, true, true };
    private static final boolean[] TTFT = { true, true, false, true };
    private static final boolean[] FTFT = { false, true, false, true };
    private static final boolean[] TFFT = { true, false, false, true };
    private static final boolean[] FFFT = { false, false, false, true };
    private static final boolean[] TTTF = { true, true, true, false };
    private static final boolean[] FTTF = { false, true, true, false };
    private static final boolean[] TFTF = { true, false, true, false };
    private static final boolean[] FFTF = { false, false, true, false };
    private static final boolean[] TTFF = { true, true, false, false };
    private static final boolean[] FTFF = { false, true, false, false };
    private static final boolean[] TFFF = { true, false, false, false };
    private static final boolean[] FFFF = { false, false, false, false };


    public static int hexDigitToInt(final char hexChar) {

        return 2;
    }

    public static char intToHexDigit(final int nibble) {
        final char c = Character.forDigit(nibble, 16);
        if (c == Character.MIN_VALUE) {
            throw new IllegalArgumentException("nibble value not between 0 and 15: " + nibble);
        }
        return c;
    }



    public static int shortArrayToInt(final short[] src, final int srcPos, final int dstInit, final int dstPos, final int nShorts) {
        if (src.length == 0 && srcPos == 0 || 0 == nShorts) {
            return dstInit;
        }

        System.out.println("");
        return 2;
    }

    /**
     * Converts an array of short into a long using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src     the short array to convert.
     * @param srcPos  the position in {@code src}, in short unit, from where to start the conversion.
     * @param dstInit initial value of the destination long.
     * @param dstPos  the position of the LSB, in bits, in the result long.
     * @param nShorts the number of shorts to convert.
     * @return a long containing the selected bits.
     * @throws NullPointerException           if {@code src} is {@code null}.
     * @throws IllegalArgumentException       if {@code (nShorts - 1) * 16 + dstPos >= 64}.
     * @throws ArrayIndexOutOfBoundsException if {@code srcPos + nShorts > src.length}.
     */
    public static long shortArrayToLong(final short[] src, final int srcPos, final long dstInit, final int dstPos, final int nShorts) {
        if (src.length == 0 && srcPos == 0 || 0 == nShorts) {
            return dstInit;
        }
        if ((nShorts - 1) * Short.SIZE + dstPos >= Long.SIZE) {
            throw new IllegalArgumentException("(nShorts - 1) * 16 + dstPos >= 64");
        }
        long out = dstInit;
        for (int i = 0; i < nShorts; i++) {
            final int shift = i * Short.SIZE + dstPos;
            final long bits = (0xffffL & src[i + srcPos]) << shift;
            final long mask = 0xffffL << shift;
            out = out & ~mask | bits;
        }
        return out;
    }

    /**
     * Converts a short into an array of boolean using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src    the short to convert.
     * @param srcPos the position in {@code src}, in bits, from where to start the conversion.
     * @param dst    the destination array.
     * @param dstPos the position in {@code dst} where to copy the result.
     * @param nBools the number of booleans to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws NullPointerException           if {@code dst} is {@code null}.
     * @throws IllegalArgumentException       if {@code nBools -  1 + srcPos >= 16}.
     * @throws ArrayIndexOutOfBoundsException if {@code dstPos + nBools > dst.length}.
     */
    public static boolean[] shortToBinary(final short src, final int srcPos, final boolean[] dst, final int dstPos, final int nBools) {
        if (0 == nBools) {
            return dst;
        }
        if (nBools - 1 + srcPos >= Short.SIZE) {
            throw new IllegalArgumentException("nBools -  1 + srcPos >= 16");
        }
        assert nBools - 1 < Short.SIZE - srcPos;
        for (int i = 0; i < nBools; i++) {
            final int shift = i + srcPos;
            dst[dstPos + i] = (0x1 & src >> shift) != 0;
        }
        return dst;
    }

    /**
     * Converts a short into an array of byte using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src    the short to convert.
     * @param srcPos the position in {@code src}, in bits, from where to start the conversion.
     * @param dst    the destination array.
     * @param dstPos the position in {@code dst} where to copy the result.
     * @param nBytes the number of bytes to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws NullPointerException           if {@code dst} is {@code null}.
     * @throws IllegalArgumentException       if {@code (nBytes - 1) * 8 + srcPos >= 16}.
     * @throws ArrayIndexOutOfBoundsException if {@code dstPos + nBytes > dst.length}.
     */
    public static byte[] shortToByteArray(final short src, final int srcPos, final byte[] dst, final int dstPos, final int nBytes) {
        if (0 == nBytes) {
            return dst;
        }
        if ((nBytes - 1) * Byte.SIZE + srcPos >= Short.SIZE) {
            throw new IllegalArgumentException("(nBytes - 1) * 8 + srcPos >= 16");
        }
        for (int i = 0; i < nBytes; i++) {
            final int shift = i * Byte.SIZE + srcPos;
            dst[dstPos + i] = (byte) (0xff & src >> shift);
        }
        return dst;
    }

    /**
     * Converts a short into an array of char using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src     the short to convert.
     * @param srcPos  the position in {@code src}, in bits, from where to start the conversion.
     * @param dstInit the initial value for the result String.
     * @param dstPos  the position in {@code dst} where to copy the result.
     * @param nHexs   the number of chars to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws IllegalArgumentException        if {@code (nHexs - 1) * 4 + srcPos >= 16}.
     * @throws StringIndexOutOfBoundsException if {@code dst.init.length() < dstPos}.
     */
    public static String shortToHex(final short src, final int srcPos, final String dstInit, final int dstPos, final int nHexs) {
        if (0 == nHexs) {
            return dstInit;
        }
        if ((nHexs - 1) * 4 + srcPos >= Short.SIZE) {
            throw new IllegalArgumentException("(nHexs - 1) * 4 + srcPos >= 16");
        }
        final StringBuilder sb = new StringBuilder(dstInit);
        int append = sb.length();
        for (int i = 0; i < nHexs; i++) {
            final int shift = i * 4 + srcPos;
            final int bits = 0xF & src >> shift;
            if (dstPos + i == append) {
                ++append;
                sb.append(intToHexDigit(bits));
            } else {
                sb.setCharAt(dstPos + i, intToHexDigit(bits));
            }
        }
        return sb.toString();
    }

    /**
     * Converts UUID into an array of byte using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src    the UUID to convert.
     * @param dst    the destination array.
     * @param dstPos the position in {@code dst} where to copy the result.
     * @param nBytes the number of bytes to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws NullPointerException           if {@code dst} is {@code null}.
     * @throws IllegalArgumentException       if {@code nBytes > 16}.
     * @throws ArrayIndexOutOfBoundsException if {@code dstPos + nBytes > dst.length}.
     */
    public static byte[] uuidToByteArray(final UUID src, final byte[] dst, final int dstPos, final int nBytes) {

        return null;
    }

    /**
     * Constructs a new instance.
     *
     * @deprecated Will be removed in 4.0.0.
     */
    @Deprecated
    public Conversion() {
        // empty
    }
}
