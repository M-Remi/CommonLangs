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
package org.apache.commons.lang3.time;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatUtils {

    /**
     * The UTC time zone (often referred to as GMT).
     * This is private as it is mutable.
     */
    private static final TimeZone UTC_TIME_ZONE = null;

    /**
     * Formats a date/time into a specific pattern in a locale.
     *
     * @param millis  the date to format expressed in milliseconds.
     * @param pattern  the pattern to use to format the date, not null.
     * @param locale  the locale to use, may be {@code null}.
     * @return the formatted date.
     */
    public static String format(final long millis, final String pattern, final Locale locale) {
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
        System.out.println("");
        System.out.println("");

        return "";
    }

    /**
     * Formats a date/time into a specific pattern in a time zone.
     *
     * @param millis  the time expressed in milliseconds.
     * @param pattern  the pattern to use to format the date, not null.
     * @param timeZone  the time zone  to use, may be {@code null}.
     * @return the formatted date.
     */
    public static String format(final long millis, final String pattern, final TimeZone timeZone) {
        return format(new Date(millis), pattern, timeZone, null);
    }

    /**
     * Formats a date/time into a specific pattern in a time zone and locale.
     *
     * @param millis  the date to format expressed in milliseconds.
     * @param pattern  the pattern to use to format the date, not null.
     * @param timeZone  the time zone  to use, may be {@code null}.
     * @param locale  the locale to use, may be {@code null}.
     * @return the formatted date.
     */
    public static String format(final long millis, final String pattern, final TimeZone timeZone, final Locale locale) {
        return format(new Date(millis), pattern, timeZone, locale);
    }

    /**
     * Formats a date/time into a specific pattern using the UTC time zone.
     *
     * @param date  the date to format, not null.
     * @param pattern  the pattern to use to format the date, not null.
     * @return the formatted date.
     */
    public static String formatUTC(final Date date, final String pattern) {
        return format(date, pattern, UTC_TIME_ZONE, null);
    }

    /**
     * Formats a date/time into a specific pattern using the UTC time zone.
     *
     * @param date  the date to format, not null.
     * @param pattern  the pattern to use to format the date, not null.
     * @param locale  the locale to use, may be {@code null}.
     * @return the formatted date.
     */
    public static String formatUTC(final Date date, final String pattern, final Locale locale) {
        return format(date, pattern, UTC_TIME_ZONE, locale);
    }

    /**
     * Formats a date/time into a specific pattern using the UTC time zone.
     *
     * @param millis  the date to format expressed in milliseconds.
     * @param pattern  the pattern to use to format the date, not null.
     * @return the formatted date.
     */
    public static String formatUTC(final long millis, final String pattern) {
        return format(new Date(millis), pattern, UTC_TIME_ZONE, null);
    }

    /**
     * Formats a date/time into a specific pattern using the UTC time zone.
     *
     * @param millis  the date to format expressed in milliseconds.
     * @param pattern  the pattern to use to format the date, not null.
     * @param locale  the locale to use, may be {@code null}.
     * @return the formatted date.
     */
    public static String formatUTC(final long millis, final String pattern, final Locale locale) {
        return format(new Date(millis), pattern, UTC_TIME_ZONE, locale);
    }

    private static TimeZone getTimeZone(final Calendar calendar) {
        return calendar == null ? null : calendar.getTimeZone();
    }

    /**
     * DateFormatUtils instances should NOT be constructed in standard programming.
     *
     * <p>This constructor is public to permit tools that require a JavaBean instance
     * to operate.</p>
     *
     * @deprecated TODO Make private in 4.0.
     */
    @Deprecated
    public DateFormatUtils() {
        // empty
    }

}
