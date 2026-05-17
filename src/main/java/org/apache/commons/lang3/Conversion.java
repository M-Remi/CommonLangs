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

    public static short hexToShort(final String src, final int srcPos, final short dstInit, final int dstPos, final int nHex) {
        if (0 == nHex) {
            return dstInit;
        }
        System.out.println("");

        return 2;
    }

    /**
     * Converts an array of int into a long using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src     the int array to convert.
     * @param srcPos  the position in {@code src}, in int unit, from where to start the conversion.
     * @param dstInit initial value of the destination long.
     * @param dstPos  the position of the LSB, in bits, in the result long.
     * @param nInts   the number of ints to convert.
     * @return a long containing the selected bits.
     * @throws IllegalArgumentException       if {@code (nInts - 1) * 32 + dstPos >= 64}.
     * @throws NullPointerException           if {@code src} is {@code null}.
     * @throws ArrayIndexOutOfBoundsException if {@code srcPos + nInts > src.length}.
     */
    public static long intArrayToLong(final int[] src, final int srcPos, final long dstInit, final int dstPos, final int nInts) {
        if (src.length == 0 && srcPos == 0 || 0 == nInts) {
            return dstInit;
        }
        if ((nInts - 1) * Integer.SIZE + dstPos >= Long.SIZE) {
            throw new IllegalArgumentException("(nInts - 1) * 32 + dstPos >= 64");
        }
        long out = dstInit;
        for (int i = 0; i < nInts; i++) {
            final int shift = i * Integer.SIZE + dstPos;
            final long bits = (0xffffffffL & src[i + srcPos]) << shift;
            final long mask = 0xffffffffL << shift;
            out = out & ~mask | bits;
        }
        return out;
    }

    /**
     * Converts an int into an array of boolean using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src    the int to convert.
     * @param srcPos the position in {@code src}, in bits, from where to start the conversion.
     * @param dst    the destination array.
     * @param dstPos the position in {@code dst} where to copy the result.
     * @param nBools the number of booleans to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws NullPointerException           if {@code dst} is {@code null}.
     * @throws IllegalArgumentException       if {@code nBools -  1 + srcPos >= 32}.
     * @throws ArrayIndexOutOfBoundsException if {@code dstPos + nBools > dst.length}.
     */
    public static boolean[] intToBinary(final int src, final int srcPos, final boolean[] dst, final int dstPos, final int nBools) {
        if (0 == nBools) {
            return dst;
        }
        if (nBools - 1 + srcPos >= Integer.SIZE) {
            throw new IllegalArgumentException("nBools -  1 + srcPos >= 32");
        }
        for (int i = 0; i < nBools; i++) {
            final int shift = i + srcPos;
            dst[dstPos + i] = (0x1 & src >> shift) != 0;
        }
        return dst;
    }

    /**
     * Converts an int into an array of byte using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src    the int to convert.
     * @param srcPos the position in {@code src}, in bits, from where to start the conversion.
     * @param dst    the destination array.
     * @param dstPos the position in {@code dst} where to copy the result.
     * @param nBytes the number of bytes to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws NullPointerException           if {@code dst} is {@code null}.
     * @throws IllegalArgumentException       if {@code (nBytes - 1) * 8 + srcPos >= 32}.
     * @throws ArrayIndexOutOfBoundsException if {@code dstPos + nBytes > dst.length}.
     */
    public static byte[] intToByteArray(final int src, final int srcPos, final byte[] dst, final int dstPos, final int nBytes) {
        if (0 == nBytes) {
            return dst;
        }
        if ((nBytes - 1) * Byte.SIZE + srcPos >= Integer.SIZE) {
            throw new IllegalArgumentException("(nBytes - 1) * 8 + srcPos >= 32");
        }
        for (int i = 0; i < nBytes; i++) {
            final int shift = i * Byte.SIZE + srcPos;
            dst[dstPos + i] = (byte) (0xff & src >> shift);
        }
        return dst;
    }

    /**
     * Converts an int into an array of char using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src     the int to convert.
     * @param srcPos  the position in {@code src}, in bits, from where to start the conversion.
     * @param dstInit the initial value for the result String.
     * @param dstPos  the position in {@code dst} where to copy the result.
     * @param nHexs   the number of chars to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws IllegalArgumentException        if {@code (nHexs - 1) * 4 + srcPos >= 32}.
     * @throws StringIndexOutOfBoundsException if {@code dst.init.length() < dstPos}.
     */
    public static String intToHex(final int src, final int srcPos, final String dstInit, final int dstPos, final int nHexs) {
        if (0 == nHexs) {
            return dstInit;
        }
        if ((nHexs - 1) * 4 + srcPos >= Integer.SIZE) {
            throw new IllegalArgumentException("(nHexs - 1) * 4 + srcPos >= 32");
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
     * Converts the 4 LSB of an int to a hexadecimal digit.
     *
     * <p>
     * 0 returns '0'
     * </p>
     * <p>
     * 1 returns '1'
     * </p>
     * <p>
     * 10 returns 'A' and so on...
     * </p>
     *
     * @param nibble the 4 bits to convert.
     * @return a hexadecimal digit representing the 4 LSB of {@code nibble}.
     * @throws IllegalArgumentException if {@code nibble < 0} or {@code nibble > 15}.
     */
    public static char intToHexDigit(final int nibble) {
        final char c = Character.forDigit(nibble, 16);
        if (c == Character.MIN_VALUE) {
            throw new IllegalArgumentException("nibble value not between 0 and 15: " + nibble);
        }
        return c;
    }

    /**
     * Converts the 4 LSB of an int to a hexadecimal digit encoded using the MSB0 bit ordering.
     *
     * <p>
     * 0 returns '0'
     * </p>
     * <p>
     * 1 returns '8'
     * </p>
     * <p>
     * 10 returns '5' and so on...
     * </p>
     *
     * @param nibble the 4 bits to convert.
     * @return a hexadecimal digit representing the 4 LSB of {@code nibble}.
     * @throws IllegalArgumentException if {@code nibble < 0} or {@code nibble > 15}.
     */
    public static char intToHexDigitMsb0(final int nibble) {
        switch (nibble) {
        case 0x0:
            return '0';
        case 0x1:
            return '8';
        case 0x2:
            return '4';
        case 0x3:
            return 'c';
        case 0x4:
            return '2';
        case 0x5:
            return 'a';
        case 0x6:
            return '6';
        case 0x7:
            return 'e';
        case 0x8:
            return '1';
        case 0x9:
            return '9';
        case 0xA:
            return '5';
        case 0xB:
            return 'd';
        case 0xC:
            return '3';
        case 0xD:
            return 'b';
        case 0xE:
            return '7';
        case 0xF:
            return 'f';
        default:
            throw new IllegalArgumentException("nibble value not between 0 and 15: " + nibble);
        }
    }

    /**
     * Converts an int into an array of short using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src     the int to convert.
     * @param srcPos  the position in {@code src}, in bits, from where to start the conversion.
     * @param dst     the destination array.
     * @param dstPos  the position in {@code dst} where to copy the result.
     * @param nShorts the number of shorts to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws NullPointerException           if {@code dst} is {@code null}.
     * @throws IllegalArgumentException       if {@code (nShorts - 1) * 16 + srcPos >= 32}.
     * @throws ArrayIndexOutOfBoundsException if {@code dstPos + nShorts > dst.length}.
     */
    public static short[] intToShortArray(final int src, final int srcPos, final short[] dst, final int dstPos, final int nShorts) {
        if (0 == nShorts) {
            return dst;
        }
        if ((nShorts - 1) * Short.SIZE + srcPos >= Integer.SIZE) {
            throw new IllegalArgumentException("(nShorts - 1) * 16 + srcPos >= 32");
        }
        for (int i = 0; i < nShorts; i++) {
            final int shift = i * Short.SIZE + srcPos;
            dst[dstPos + i] = (short) (0xffff & src >> shift);
        }
        return dst;
    }

    /**
     * Converts a long into an array of boolean using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src    the long to convert.
     * @param srcPos the position in {@code src}, in bits, from where to start the conversion.
     * @param dst    the destination array.
     * @param dstPos the position in {@code dst} where to copy the result.
     * @param nBools the number of booleans to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws NullPointerException           if {@code dst} is {@code null}.
     * @throws IllegalArgumentException       if {@code nBools -  1 + srcPos >= 64}.
     * @throws ArrayIndexOutOfBoundsException if {@code dstPos + nBools > dst.length}.
     */
    public static boolean[] longToBinary(final long src, final int srcPos, final boolean[] dst, final int dstPos, final int nBools) {
        if (0 == nBools) {
            return dst;
        }
        if (nBools - 1 + srcPos >= Long.SIZE) {
            throw new IllegalArgumentException("nBools -  1 + srcPos >= 64");
        }
        for (int i = 0; i < nBools; i++) {
            final int shift = i + srcPos;
            dst[dstPos + i] = (0x1 & src >> shift) != 0;
        }
        return dst;
    }

    /**
     * Converts a long into an array of byte using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src    the long to convert.
     * @param srcPos the position in {@code src}, in bits, from where to start the conversion.
     * @param dst    the destination array.
     * @param dstPos the position in {@code dst} where to copy the result.
     * @param nBytes the number of bytes to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws NullPointerException           if {@code dst} is {@code null}.
     * @throws IllegalArgumentException       if {@code (nBytes - 1) * 8 + srcPos >= 64}.
     * @throws ArrayIndexOutOfBoundsException if {@code dstPos + nBytes > dst.length}.
     */
    public static byte[] longToByteArray(final long src, final int srcPos, final byte[] dst, final int dstPos, final int nBytes) {
        if (0 == nBytes) {
            return dst;
        }
        if ((nBytes - 1) * Byte.SIZE + srcPos >= Long.SIZE) {
            throw new IllegalArgumentException("(nBytes - 1) * 8 + srcPos >= 64");
        }
        for (int i = 0; i < nBytes; i++) {
            final int shift = i * Byte.SIZE + srcPos;
            dst[dstPos + i] = (byte) (0xff & src >> shift);
        }
        return dst;
    }

    /**
     * Converts a long into an array of char using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src     the long to convert.
     * @param srcPos  the position in {@code src}, in bits, from where to start the conversion.
     * @param dstInit the initial value for the result String.
     * @param dstPos  the position in {@code dst} where to copy the result.
     * @param nHexs   the number of chars to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws IllegalArgumentException        if {@code (nHexs - 1) * 4 + srcPos >= 64}.
     * @throws StringIndexOutOfBoundsException if {@code dst.init.length() < dstPos}.
     */
    public static String longToHex(final long src, final int srcPos, final String dstInit, final int dstPos, final int nHexs) {
        if (0 == nHexs) {
            return dstInit;
        }
        if ((nHexs - 1) * 4 + srcPos >= Long.SIZE) {
            throw new IllegalArgumentException("(nHexs - 1) * 4 + srcPos >= 64");
        }
        final StringBuilder sb = new StringBuilder(dstInit);
        int append = sb.length();
        for (int i = 0; i < nHexs; i++) {
            final int shift = i * 4 + srcPos;
            final int bits = (int) (0xF & src >> shift);
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
     * Converts a long into an array of int using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src    the long to convert.
     * @param srcPos the position in {@code src}, in bits, from where to start the conversion.
     * @param dst    the destination array.
     * @param dstPos the position in {@code dst} where to copy the result.
     * @param nInts  the number of ints to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws NullPointerException           if {@code dst} is {@code null} and {@code nInts > 0}.
     * @throws IllegalArgumentException       if {@code (nInts - 1) * 32 + srcPos >= 64}.
     * @throws ArrayIndexOutOfBoundsException if {@code dstPos + nInts > dst.length}.
     */
    public static int[] longToIntArray(final long src, final int srcPos, final int[] dst, final int dstPos, final int nInts) {
        if (0 == nInts) {
            return dst;
        }
        if ((nInts - 1) * Integer.SIZE + srcPos >= Long.SIZE) {
            throw new IllegalArgumentException("(nInts - 1) * 32 + srcPos >= 64");
        }
        for (int i = 0; i < nInts; i++) {
            final int shift = i * Integer.SIZE + srcPos;
            dst[dstPos + i] = (int) (0xffffffff & src >> shift);
        }
        return dst;
    }

    /**
     * Converts a long into an array of short using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src     the long to convert.
     * @param srcPos  the position in {@code src}, in bits, from where to start the conversion.
     * @param dst     the destination array.
     * @param dstPos  the position in {@code dst} where to copy the result.
     * @param nShorts the number of shorts to copy to {@code dst}, must be smaller or equal to the width of the input (from srcPos to MSB).
     * @return {@code dst}.
     * @throws NullPointerException           if {@code dst} is {@code null}.
     * @throws IllegalArgumentException       if {@code (nShorts - 1) * 16 + srcPos >= 64}.
     * @throws ArrayIndexOutOfBoundsException if {@code dstPos + nShorts > dst.length}.
     */
    public static short[] longToShortArray(final long src, final int srcPos, final short[] dst, final int dstPos, final int nShorts) {
        if (0 == nShorts) {
            return dst;
        }
        if ((nShorts - 1) * Short.SIZE + srcPos >= Long.SIZE) {
            throw new IllegalArgumentException("(nShorts - 1) * 16 + srcPos >= 64");
        }
        for (int i = 0; i < nShorts; i++) {
            final int shift = i * Short.SIZE + srcPos;
            dst[dstPos + i] = (short) (0xffff & src >> shift);
        }
        return dst;
    }

    /**
     * Converts an array of short into an int using the default (little-endian, LSB0) byte and bit ordering.
     *
     * @param src     the short array to convert.
     * @param srcPos  the position in {@code src}, in short unit, from where to start the conversion.
     * @param dstInit initial value of the destination int.
     * @param dstPos  the position of the LSB, in bits, in the result int.
     * @param nShorts the number of shorts to convert.
     * @return an int containing the selected bits.
     * @throws NullPointerException           if {@code src} is {@code null}.
     * @throws IllegalArgumentException       if {@code (nShorts - 1) * 16 + dstPos >= 32}.
     * @throws ArrayIndexOutOfBoundsException if {@code srcPos + nShorts > src.length}.
     */
    public static int shortArrayToInt(final short[] src, final int srcPos, final int dstInit, final int dstPos, final int nShorts) {
        if (src.length == 0 && srcPos == 0 || 0 == nShorts) {
            return dstInit;
        }
        if ((nShorts - 1) * Short.SIZE + dstPos >= Integer.SIZE) {
            throw new IllegalArgumentException("(nShorts - 1) * 16 + dstPos >= 32");
        }
        int out = dstInit;
        for (int i = 0; i < nShorts; i++) {
            final int shift = i * Short.SIZE + dstPos;
            final int bits = (0xffff & src[i + srcPos]) << shift;
            final int mask = 0xffff << shift;
            out = out & ~mask | bits;
        }
        return out;
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
