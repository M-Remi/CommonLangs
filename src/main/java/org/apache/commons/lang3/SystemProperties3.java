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

public final class SystemProperties3 {

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




    public static final String JDK_XML_MAX_GENERAL_ENTITY_SIZE_LIMIT = "jdk.xml.maxGeneralEntitySizeLimit";
    public static final String JDK_XML_MAX_OCCUR_LIMIT = "jdk.xml.maxOccurLimit";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.maxParameterEntitySizeLimit</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_MAX_PARAMETER_ENTITY_SIZE_LIMIT = "jdk.xml.maxParameterEntitySizeLimit";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.maxXMLNameLimit</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_MAX_XML_NAME_LIMIT = "jdk.xml.maxXMLNameLimit";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.overrideDefaultParser</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_OVERRIDE_DEFAULT_PARSER = "jdk.xml.overrideDefaultParser";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.resetSymbolTable</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_RESET_SYMBOL_TABLE = "jdk.xml.resetSymbolTable";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.totalEntitySizeLimit</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_TOTAL_ENTITY_SIZE_LIMIT = "jdk.xml.totalEntitySizeLimit";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.xsltcIsStandalone</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_XSLTC_IS_STANDALONE = "jdk.xml.xsltcIsStandalone";

    /**
     * The System property name {@value}.
     */
    public static final String LINE_SEPARATOR = "line.separator";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">native.encoding</a>
     * @since 3.15.0
     */
    public static final String NATIVE_ENCODING = "native.encoding";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">networkaddress.cache.negative.ttl</a>
     * @since 3.15.0
     */
    public static final String NETWORK_ADDRESS_CACHE_NEGATIVE_TTL = "networkaddress.cache.negative.ttl";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">networkaddress.cache.stale.ttl</a>
     * @since 3.15.0
     */
    public static final String NETWORK_ADDRESS_CACHE_STALE_TTL = "networkaddress.cache.stale.ttl";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">networkaddress.cache.ttl</a>
     * @since 3.15.0
     */
    public static final String NETWORK_ADDRESS_CACHE_TTL = "networkaddress.cache.ttl";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">org.jcp.xml.dsig.securevalidation</a>
     * @since 3.15.0
     */
    public static final String ORG_JCP_XML_DSIG_SECURE_VALIDATION = "org.jcp.xml.dsig.securevalidation";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">org.openjdk.java.util.stream.tripwire</a>
     * @since 3.15.0
     */
    public static final String ORG_OPENJDK_JAVA_UTIL_STREAM_TRIPWIRE = "org.openjdk.java.util.stream.tripwire";

    /**
     * The System property name {@value}.
     */
    public static final String OS_ARCH = "os.arch";

    /**
     * The System property name {@value}.
     */
    public static final String OS_NAME = "os.name";

    /**
     * The System property name {@value}.
     */
    public static final String OS_VERSION = "os.version";

    /**
     * The System property name {@value}.
     */
    public static final String PATH_SEPARATOR = "path.separator";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String SOCKS_PROXY_HOST = "socksProxyHost";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String SOCKS_PROXY_PORT = "socksProxyPort";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String SOCKS_PROXY_VERSION = "socksProxyVersion";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String STDERR_ENCODING = "stderr.encoding";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String STDOUT_ENCODING = "stdout.encoding";

    /**
     * The System property name {@value}.
     *
     * @see <a href=
     *      "https://docs.oracle.com/en/java/javase/25/docs/api/jdk.httpserver/module-summary.html#sun.net.httpserver.drainAmount">sun.net.httpserver.drainAmount</a>
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String SUN_NET_HTTP_SERVER_DRAIN_AMOUNT = "sun.net.httpserver.drainAmount";

    /**
     * The System property name {@value}.
     *
     * @see <a href=
     *      "https://docs.oracle.com/en/java/javase/25/docs/api/jdk.httpserver/module-summary.html#sun.net.httpserver.idleInterval">sun.net.httpserver.idleInterval</a>
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String SUN_NET_HTTP_SERVER_IDLE_INTERVAL = "sun.net.httpserver.idleInterval";

    /**
     * The System property name {@value}.
     *
     * @see <a href=
     *      "https://docs.oracle.com/en/java/javase/25/docs/api/jdk.httpserver/module-summary.html#sun.net.httpserver.maxIdleConnections">sun.net.httpserver.maxIdleConnections</a>
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String SUN_NET_HTTP_SERVER_MAX_IDLE_CONNECTIONS = "sun.net.httpserver.maxIdleConnections";

    /**
     * The System property name {@value}.
     *
     * @see <a href=
     *      "https://docs.oracle.com/en/java/javase/25/docs/api/jdk.httpserver/module-summary.html#sun.net.httpserver.maxReqHeaders">sun.net.httpserver.maxReqHeaders</a>
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String SUN_NET_HTTP_SERVER_MAX_REQ_HEADERS = "sun.net.httpserver.maxReqHeaders";

    /**
     * The System property name {@value}.
     *
     * @see <a href=
     *      "https://docs.oracle.com/en/java/javase/25/docs/api/jdk.httpserver/module-summary.html#sun.net.httpserver.maxReqTime">sun.net.httpserver.maxReqTime</a>
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String SUN_NET_HTTP_SERVER_MAX_REQ_TIME = "sun.net.httpserver.maxReqTime";

    /**
     * The System property name {@value}.
     *
     * @see <a href=
     *      "https://docs.oracle.com/en/java/javase/25/docs/api/jdk.httpserver/module-summary.html#sun.net.httpserver.maxRspTime">sun.net.httpserver.maxRspTime</a>
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String SUN_NET_HTTP_SERVER_MAX_RSP_TIME = "sun.net.httpserver.maxRspTime";

    /**
     * The System property name {@value}.
     *
     * @see <a href=
     *      "https://docs.oracle.com/en/java/javase/25/docs/api/jdk.httpserver/module-summary.html#sun.net.httpserver.nodelay">sun.net.httpserver.nodelay</a>
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String SUN_NET_HTTP_SERVER_NO_DELAY = "sun.net.httpserver.nodelay";

    /**
     * The System property name {@value}.
     *
     * @see <a href=
     *      "https://docs.oracle.com/en/java/javase/25/docs/api/jdk.httpserver/module-summary.html#sun.security.krb5.principal">sun.security.krb5.principal</a>
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String SUN_SECURITY_KRB5_PRINCIPAL = "sun.security.krb5.principal";

    /**
     * The System property name {@value}.
     */
    public static final String USER_COUNTRY = "user.country";

    /**
     * The System property name {@value}.
     */
    public static final String USER_DIR = "user.dir";

    public static String getProperty(final String property) {
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
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        return getProperty(property, Suppliers.nul());
    }

    static String getProperty(final String property, final Supplier<String> defaultIfAbsent) {

       return "";

    }


}
