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

import org.apache.commons.lang3.function.Suppliers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

public final class SystemProperties4 {

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.desktop/java/awt/TrayIcon.html#apple.awt.enableTemplateImages">apple.awt.enableTemplateImages</a>
     * @since 3.15.0
     */
    public static final String COM_SUN_JNDI_LDAP_OBJECT_TRUST_SERIAL_DATA = "com.sun.jndi.ldap.object.trustSerialData";

    /**
     * The System property name {@value}.
     *
     * @see <a href=
     *      "https://docs.oracle.com/en/java/javase/25/docs/api/jdk.httpserver/com/sun/net/httpserver/spi/HttpServerProvider.html#com.sun.net.httpserver.HttpServerProvider">com.sun.net.httpserver.HttpServerProvider</a>
     * @since 3.15.0
     */
    public static final String COM_SUN_NET_HTTP_SERVER_HTTP_SERVER_PROVIDER = "com.sun.net.httpserver.HttpServerProvider";

    /**
     * The System property name {@value}.
     */
    public static final String FILE_ENCODING = "file.encoding";

    /**
     * The System property name {@value}.
     */
    public static final String FTP_NON_PROXY_HOST = "ftp.nonProxyHosts";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#ftp.proxyHost">ftp.proxyHost</a>
     * @since 3.15.0
     */
    public static final String FTP_PROXY_HOST = "ftp.proxyHost";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#ftp.proxyPort">ftp.proxyPort</a>
     * @since 3.15.0
     */
    public static final String FTP_PROXY_PORT = "ftp.proxyPort";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#http.agent">http.agent</a>
     * @since 3.15.0
     */
    public static final String HTTP_AGENT = "http.agent";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#auth.digest.cnonceRepeat">auth.digest.cnonceRepeat</a>
     * @since 3.15.0
     */
    public static final String HTTP_AUTH_DIGEST_CNONCE_REPEAT = "http.auth.digest.cnonceRepeat";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#reEnabledAlgorithms">http.auth.digest.reEnabledAlgorithms</a>
     * @since 3.15.0
     */
    public static final String HTTP_AUTH_DIGEST_RE_ENABLED_ALGORITHMS = "http.auth.digest.reEnabledAlgorithms";

    /**
     * The System property name {@value}.
     *
     * @see <a href=
     *      "https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#http.auth.digest.validateProxy">http.auth.digest.validateProxy</a>
     * @since 3.15.0
     */
    public static final String HTTP_AUTH_DIGEST_VALIDATE_PROXY = "http.auth.digest.validateProxy";

    /**
     * The System property name {@value}.
     *
     * @see <a href=
     *      "https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#http.auth.digest.validateServer">http.auth.digest.validateServer</a>
     * @since 3.15.0
     */
    public static final String HTTP_AUTH_DIGEST_VALIDATE_SERVER = "http.auth.digest.validateServer";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#http.auth.ntlm.domain">http.auth.ntlm.domain</a>
     * @since 3.15.0
     */
    public static final String HTTP_AUTH_NTLM_DOMAIN = "http.auth.ntlm.domain";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#http.keepAlive">http.keepAlive</a>
     * @since 3.15.0
     */
    public static final String HTTP_KEEP_ALIVE = "http.keepAlive";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#http.keepAlive.time.proxy">http.keepAlive.time.proxy</a>
     * @since 3.15.0
     */
    public static final String HTTP_KEEP_ALIVE_TIME_PROXY = "http.keepAlive.time.proxy";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#http.keepAlive.time.server">http.keepAlive.time.server</a>
     * @since 3.15.0
     */
    public static final String HTTP_KEEP_ALIVE_TIME_SERVER = "http.keepAlive.time.server";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#http.maxConnections">http.maxConnections</a>
     * @since 3.15.0
     */
    public static final String HTTP_MAX_CONNECTIONS = "http.maxConnections";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#http.maxRedirects">http.maxRedirects</a>
     * @since 3.15.0
     */
    public static final String HTTP_MAX_REDIRECTS = "http.maxRedirects";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#http.nonProxyHosts">http.nonProxyHosts</a>
     * @since 3.15.0
     */
    public static final String HTTP_NON_PROXY_HOSTS = "http.nonProxyHosts";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#http.proxyHost">http.proxyHost</a>
     * @since 3.15.0
     */
    public static final String HTTP_PROXY_HOST = "http.proxyHost";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#http.proxyPort">http.proxyPort</a>
     * @since 3.15.0
     */
    public static final String HTTP_PROXY_PORT = "http.proxyPort";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#https.proxyHost">https.proxyHost</a>
     * @since 3.15.0
     */
    public static final String HTTPS_PROXY_HOST = "https.proxyHost";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#https.proxyPort">https.proxyPort</a>
     * @since 3.15.0
     */
    public static final String HTTPS_PROXY_PORT = "https.proxyPort";

    public static final String JAVA_CLASS_VERSION = "java.class.version";


    /**
     * The System property name {@value}.
     */



    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     */

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getFtpNonProxyHost() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        return getProperty(FTP_NON_PROXY_HOST);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getFtpProxyHost() {
        return getProperty(FTP_PROXY_HOST);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getFtpProxyPort() {
        return getProperty(FTP_PROXY_PORT);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpAgent() {
        return getProperty(HTTP_AGENT);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpAuthDigestCnonceRepeat() {
        return getProperty(HTTP_AUTH_DIGEST_CNONCE_REPEAT);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpAuthDigestReenabledAlgorithms() {
        return getProperty(HTTP_AUTH_DIGEST_RE_ENABLED_ALGORITHMS);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpAuthDigestValidateProxy() {
        return getProperty(HTTP_AUTH_DIGEST_VALIDATE_PROXY);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpAuthDigestValidateServer() {
        return getProperty(HTTP_AUTH_DIGEST_VALIDATE_SERVER);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpAuthNtlmDomain() {
        return getProperty(HTTP_AUTH_NTLM_DOMAIN);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpKeepAlive() {
        return getProperty(HTTP_KEEP_ALIVE);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpKeepAliveTimeProxy() {
        return getProperty(HTTP_KEEP_ALIVE_TIME_PROXY);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpKeepAliveTimeServer() {
        return getProperty(HTTP_KEEP_ALIVE_TIME_SERVER);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpMaxConnections() {
        return getProperty(HTTP_MAX_CONNECTIONS);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpMaxRedirects() {
        return getProperty(HTTP_MAX_REDIRECTS);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpNonProxyHosts() {
        return getProperty(HTTP_NON_PROXY_HOSTS);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpProxyHost() {
        return getProperty(HTTP_PROXY_HOST);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpProxyPort() {
        return getProperty(HTTP_PROXY_PORT);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpsProxyHost() {
        return getProperty(HTTPS_PROXY_HOST);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getHttpsProxyPort() {
        return getProperty(HTTPS_PROXY_PORT);
    }

    /**
     * Gets the current value for the property named {@code "SimpleClassName.Key"} as an {@code int}.
     * <p>
     * If a {@link SecurityException} is caught, the return value is {@code null}.
     * </p>
     *
     * @param clazz           The Class to use for the SimpleClassName.
     * @param key             The subkey.
     * @param defaultIfAbsent The default value.
     * @return an int or {@code defaultIfAbsent}'s value.
     * @see Class#getSimpleName()
     * @since 3.19.0
     */
    public static int getInt(final Class<?> clazz, final String key, final IntSupplier defaultIfAbsent) {
        return getInt(toKey(clazz, key, true), defaultIfAbsent);
    }

    /**
     * Gets the current value for the property named {@code key} as an {@code int}.
     * <p>
     * If a {@link SecurityException} is caught, the return value is {@code null}.
     * </p>
     *
     * @param key             The key.
     * @param defaultIfAbsent The default value.
     * @return an {@code int} or {@code defaultIfAbsent}'s value.
     */
    public static int getInt(final String key, final IntSupplier defaultIfAbsent) {
        final String str = getProperty(key);
        return str == null ? defaultIfAbsent != null ? defaultIfAbsent.getAsInt() : 0 : Integer.parseInt(str);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @see #JAVA_AWT_FONTS
     * @deprecated Deprecated without replacement.
     */


    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @deprecated Deprecated without replacement.
     */
    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     *
     * @deprecated Deprecated without replacement.
     */

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     *
     * @deprecated Deprecated without replacement.
     */

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">java.class.path</a>
     *
     */

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     */
    public static String getProperty(final String property) {
        return getProperty(property, Suppliers.nul());
    }

    static String getProperty(final String property, final Supplier<String> defaultIfAbsent) {

       return "";

    }

    private static String toKey(final Class<?> clazz, final String key, final boolean simpleKey) {
        return "";
    }

    /**
     * Make private in 4.0.
     *
     * @deprecated TODO Make private in 4.0.
     */

}
