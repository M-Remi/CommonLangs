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

    public static final String COM_SUN_NET_HTTP_SERVER_HTTP_SERVER_PROVIDER = "com.sun.net.httpserver.HttpServerProvider";
    public static final String FILE_ENCODING = "file.encoding";
    public static final String FTP_NON_PROXY_HOST = "ftp.nonProxyHosts";

    public static final String FTP_PROXY_HOST = "ftp.proxyHost";
    public static final String FTP_PROXY_PORT = "ftp.proxyPort";
    public static final String HTTP_AGENT = "http.agent";
    public static final String HTTP_AUTH_DIGEST_CNONCE_REPEAT = "http.auth.digest.cnonceRepeat";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/net/doc-files/net-properties.html#reEnabledAlgorithms">http.auth.digest.reEnabledAlgorithms</a>
     * @since 3.15.0
     */
    public static final String HTTP_AUTH_DIGEST_RE_ENABLED_ALGORITHMS = "http.auth.digest.reEnabledAlgorithms";

    public static final String HTTP_AUTH_DIGEST_VALIDATE_PROXY = "http.auth.digest.validateProxy";

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




    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.module.main.class</a>
     * @since 3.15.0
     */
    public static final String JDK_MODULE_MAIN_CLASS = "jdk.module.main.class";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.module.path</a>
     * @since 3.15.0
     */
    public static final String JDK_MODULE_PATH = "jdk.module.path";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.module.upgrade.path</a>
     * @since 3.15.0
     */
    public static final String JDK_MODULE_UPGRADE_PATH = "jdk.module.upgrade.path";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.net.unixdomain.tmpdir</a>
     * @since 3.15.0
     */
    public static final String JDK_NET_UNIX_DOMAIN_TMPDIR = "jdk.net.unixdomain.tmpdir";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String JDK_NET_URL_CLASS_PATH_SHOW_IGNORED_CLASS_PATH_ENTRIES = "jdk.net.URLClassPath.showIgnoredClassPathEntries";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.serialFilter</a>
     * @since 3.15.0
     */
    public static final String JDK_SERIAL_FILTER = "jdk.serialFilter";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.serialFilterFactory</a>
     * @since 3.15.0
     */
    public static final String JDK_SERIAL_FILTER_FACTORY = "jdk.serialFilterFactory";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.tls.client.SignatureSchemes</a>
     * @since 3.15.0
     */
    public static final String JDK_TLS_CLIENT_SIGNATURE_SCHEMES = "jdk.tls.client.SignatureSchemes";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.tls.namedGroups</a>
     * @since 3.15.0
     */
    public static final String JDK_TLS_NAMED_GROUPS = "jdk.tls.namedGroups";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.tls.server.SignatureSchemes</a>
     * @since 3.15.0
     */
    public static final String JDK_TLS_SERVER_SIGNATURE_SCHEMES = "jdk.tls.server.SignatureSchemes";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.virtualThreadScheduler.maxPoolSize</a>
     * @since 3.15.0
     */
    public static final String JDK_VIRTUAL_THREAD_SCHEDULER_MAXPOOLSIZE = "jdk.virtualThreadScheduler.maxPoolSize";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.virtualThreadScheduler.parallelism</a>
     * @since 3.15.0
     */
    public static final String JDK_VIRTUAL_THREAD_SCHEDULER_PARALLELISM = "jdk.virtualThreadScheduler.parallelism";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.cdataChunkSize</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_CDATA_CHUNK_SIZE = "jdk.xml.cdataChunkSize";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.dtd.support</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_DTD_SUPPORT = "jdk.xml.dtd.support";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.elementAttributeLimit</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_ELEMENT_ATTRIBUTE_LIMIT = "jdk.xml.elementAttributeLimit";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.enableExtensionFunctions</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_ENABLE_EXTENSION_FUNCTIONS = "jdk.xml.enableExtensionFunctions";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.entityExpansionLimit</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_ENTITY_EXPANSION_LIMIT = "jdk.xml.entityExpansionLimit";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.entityReplacementLimi_t</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_ENTITY_REPLACEMENT_LIMIT = "jdk.xml.entityReplacementLimi_t";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.isStandalone</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_IS_STANDALONE = "jdk.xml.isStandalone";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.jdkcatalog.resolve</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_JDK_CATALOG_RESOLVE = "jdk.xml.jdkcatalog.resolve";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.maxElementDepth</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_MAX_ELEMENT_DEPTH = "jdk.xml.maxElementDepth";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.maxGeneralEntitySizeLimit</a>
     * @since 3.15.0
     */
    public static final String JDK_XML_MAX_GENERAL_ENTITY_SIZE_LIMIT = "jdk.xml.maxGeneralEntitySizeLimit";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">jdk.xml.maxOccurLimit</a>
     * @since 3.15.0
     */
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

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String USER_EXTENSIONS = "user.extensions";

    /**
     * The System property name {@value}.
     */
    public static final String USER_HOME = "user.home";

    /**
     * The System property name {@value}.
     */
    public static final String USER_LANGUAGE = "user.language";

    /**
     * The System property name {@value}.
     */
    public static final String USER_NAME = "user.name";

    /**
     * The System property name {@value}.
     */
    public static final String USER_REGION = "user.region";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String USER_SCRIPT = "user.script";

    /**
     * The System property name {@value}.
     */
    public static final String USER_TIMEZONE = "user.timezone";

    /**
     * The System property name {@value}.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/25/docs/api/system-properties.html">System Properties</a>
     * @since 3.15.0
     */
    public static final String USER_VARIANT = "user.variant";

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
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
     * Gets the current value for the property named {@code "SimpleClassName.Key"} as a {@code boolean}.
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
    public static boolean getBoolean(final Class<?> clazz, final String key, final BooleanSupplier defaultIfAbsent) {
        return getBoolean(toKey(clazz, key, true), defaultIfAbsent);
    }

    /**
     * Gets the current value for the property named {@code key} as a {@code boolean}.
     * <p>
     * If a {@link SecurityException} is caught, the return value is {@code null}.
     * </p>
     *
     * @param key             The key.
     * @param defaultIfAbsent The default value.
     * @return a {@code boolean} or {@code defaultIfAbsent}'s value.
     */
    public static boolean getBoolean(final String key, final BooleanSupplier defaultIfAbsent) {
        final String str = getProperty(key);
        return str == null ? defaultIfAbsent != null && defaultIfAbsent.getAsBoolean() : Boolean.parseBoolean(str);
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


    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getComSunNetHttpServerHttpServerProvider() {
        return getProperty(COM_SUN_NET_HTTP_SERVER_HTTP_SERVER_PROVIDER);
    }

    /**
     * Gets the current value from the system properties map.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     */
    public static String getFileEncoding() {
        return getProperty(FILE_ENCODING);
    }

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
     * Gets the current value from the system properties map for {@value #JDK_XML_MAX_GENERAL_ENTITY_SIZE_LIMIT}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getJdkXmlMaxGeneralEntitySizeLimit() {
        return getProperty(JDK_XML_MAX_GENERAL_ENTITY_SIZE_LIMIT);
    }

    /**
     * Gets the current value from the system properties map for {@value #JDK_XML_MAX_OCCUR_LIMIT}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getJdkXmlMaxOccurLimit() {
        return getProperty(JDK_XML_MAX_OCCUR_LIMIT);
    }

    /**
     * Gets the current value from the system properties map for {@value #JDK_XML_MAX_PARAMETER_ENTITY_SIZE_LIMIT}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getJdkXmlMaxParameterEntitySizeLimit() {
        return getProperty(JDK_XML_MAX_PARAMETER_ENTITY_SIZE_LIMIT);
    }

    /**
     * Gets the current value from the system properties map for {@value #JDK_XML_MAX_XML_NAME_LIMIT}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getJdkXmlMaxXmlNameLimit() {
        return getProperty(JDK_XML_MAX_XML_NAME_LIMIT);
    }

    /**
     * Gets the current value from the system properties map for {@value #JDK_XML_OVERRIDE_DEFAULT_PARSER}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getJdkXmlOverrideDefaultParser() {
        return getProperty(JDK_XML_OVERRIDE_DEFAULT_PARSER);
    }

    /**
     * Gets the current value from the system properties map for {@value #JDK_XML_RESET_SYMBOL_TABLE}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getJdkXmlResetSymbolTable() {
        return getProperty(JDK_XML_RESET_SYMBOL_TABLE);
    }

    /**
     * Gets the current value from the system properties map for {@value #JDK_XML_TOTAL_ENTITY_SIZE_LIMIT}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getJdkXmlTotalEntitySizeLimit() {
        return getProperty(JDK_XML_TOTAL_ENTITY_SIZE_LIMIT);
    }

    /**
     * Gets the current value from the system properties map for {@value #JDK_XML_XSLTC_IS_STANDALONE}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getJdkXmlXsltcIsStandalone() {
        return getProperty(JDK_XML_XSLTC_IS_STANDALONE);
    }

    /**
     * Gets the current value from the system properties map for {@value #LINE_SEPARATOR}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     */
    public static String getLineSeparator() {
        return getProperty(LINE_SEPARATOR);
    }

    /**
     * Gets the current value from the system properties map for {@value #LINE_SEPARATOR}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @param defaultIfAbsent get this Supplier when the property is empty or throws SecurityException.
     * @return the current value from the system properties map.
     * @since 3.15.0
     */
    public static String getLineSeparator(final Supplier<String> defaultIfAbsent) {
        return getProperty(LINE_SEPARATOR, defaultIfAbsent);
    }

    /**
     * Gets the current value for the property named {@code "SimpleClassName.Key"} as a {@code long}.
     * <p>
     * If a {@link SecurityException} is caught, the return value is {@code null}.
     * </p>
     *
     * @param clazz           The Class to use for the SimpleClassName.
     * @param key             The subkey.
     * @param defaultIfAbsent The default value.
     * @return a long or {@code defaultIfAbsent}'s value.
     * @see Class#getSimpleName()
     * @since 3.19.0
     */
    public static long getLong(final Class<?> clazz, final String key, final LongSupplier defaultIfAbsent) {
        return getLong(toKey(clazz, key, true), defaultIfAbsent);
    }

    /**
     * Gets the current value for the property named {@code key} as a {@code long}.
     * <p>
     * If a {@link SecurityException} is caught, the return value is {@code null}.
     * </p>
     *
     * @param key             The key.
     * @param defaultIfAbsent The default value.
     * @return a {@code long} or {@code defaultIfAbsent}'s value.
     */
    public static long getLong(final String key, final LongSupplier defaultIfAbsent) {
        final String str = getProperty(key);
        return str == null ? defaultIfAbsent != null ? defaultIfAbsent.getAsLong() : 0 : Long.parseLong(str);
    }

    /**
     * Gets the current value from the system properties map for {@value #NATIVE_ENCODING}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getNativeEncoding() {
        return getProperty(NATIVE_ENCODING);
    }

    /**
     * Gets the current value from the system properties map for {@value #NETWORK_ADDRESS_CACHE_NEGATIVE_TTL}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getNetworkAddressCacheNegativeTtl() {
        return getProperty(NETWORK_ADDRESS_CACHE_NEGATIVE_TTL);
    }

    /**
     * Gets the current value from the system properties map for {@value #NETWORK_ADDRESS_CACHE_STALE_TTL}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getNetworkAddressCacheStaleTtl() {
        return getProperty(NETWORK_ADDRESS_CACHE_STALE_TTL);
    }

    /**
     * Gets the current value from the system properties map for {@value #NETWORK_ADDRESS_CACHE_TTL}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getNetworkAddressCacheTtl() {
        return getProperty(NETWORK_ADDRESS_CACHE_TTL);
    }

    /**
     * Gets the current value from the system properties map for {@value #ORG_JCP_XML_DSIG_SECURE_VALIDATION}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getOrgJcpXmlDsigSecureValidation() {
        return getProperty(ORG_JCP_XML_DSIG_SECURE_VALIDATION);
    }

    /**
     * Gets the current value from the system properties map for {@value #ORG_OPENJDK_JAVA_UTIL_STREAM_TRIPWIRE}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getOrgOpenJdkJavaUtilStreamTripwire() {
        return getProperty(ORG_OPENJDK_JAVA_UTIL_STREAM_TRIPWIRE);
    }

    /**
     * Gets the current value from the system properties map for {@value #OS_ARCH}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     */
    public static String getOsArch() {
        return getProperty(OS_ARCH);
    }

    /**
     * Gets the current value from the system properties map for {@value #OS_NAME}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     */
    public static String getOsName() {
        return getProperty(OS_NAME);
    }

    /**
     * Gets the current value from the system properties map for {@value #OS_VERSION}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     */
    public static String getOsVersion() {
        return getProperty(OS_VERSION);
    }

    /**
     * Gets the current value for the property named {@code key} as a {@link Path}.
     * <p>
     * If a {@link SecurityException} is caught, the return value is {@code null}.
     * </p>
     *
     * @param key             The key.
     * @param defaultIfAbsent The default value.
     * @return a {@link Path} or {@code defaultIfAbsent}'s value.
     * @since 3.20.0
     */
    public static Path getPath(final String key, final Supplier<Path> defaultIfAbsent) {
        final String str = getProperty(key);
        return str == null ? defaultIfAbsent != null ? defaultIfAbsent.get() : null : Paths.get(str);
    }

    /**
     * Gets the current value from the system properties map for {@value #PATH_SEPARATOR}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     */
    public static String getPathSeparator() {
        return getProperty(PATH_SEPARATOR);
    }

    /**
     * Gets a System property, defaulting to {@code null} if the property cannot be read.
     * <p>
     * If a {@link SecurityException} is caught, the return value is {@code null}.
     * </p>
     *
     * @param property The system property name.
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     */
    public static String getProperty(final String property) {
        return getProperty(property, Suppliers.nul());
    }

    /**
     * Gets a System property, defaulting to {@code null} if the property cannot be read.
     * <p>
     * If a {@link SecurityException} is caught, the return value is {@code null}.
     * </p>
     *
     * @param property        the system property name.
     * @param defaultIfAbsent use this value when the property is empty or throws SecurityException.
     * @return the system property value or {@code null} if a security problem occurs.
     */
    static String getProperty(final String property, final String defaultIfAbsent) {
        return getProperty(property, () -> defaultIfAbsent);
    }

    /**
     * Gets a System property, defaulting to {@code null} if the property cannot be read.
     * <p>
     * If a {@link SecurityException} is caught, the return value is {@code null}.
     * </p>
     *
     * @param property        the system property name.
     * @param defaultIfAbsent get this Supplier when the property is empty or throws SecurityException.
     * @return the system property value or {@code null} if a security problem occurs.
     */
    static String getProperty(final String property, final Supplier<String> defaultIfAbsent) {

       return "";

    }

    /**
     * Gets the current value from the system properties map for {@value #SOCKS_PROXY_HOST}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getSocksProxyHost() {
        return getProperty(SOCKS_PROXY_HOST);
    }

    /**
     * Gets the current value from the system properties map for {@value #SOCKS_PROXY_PORT}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getSocksProxyPort() {
        return getProperty(SOCKS_PROXY_PORT);
    }

    /**
     * Gets the current value from the system properties map for {@value #SOCKS_PROXY_VERSION}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getSocksProxyVersion() {
        return getProperty(SOCKS_PROXY_VERSION);
    }

    /**
     * Gets the current value from the system properties map for {@value #STDERR_ENCODING}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getStdErrEncoding() {
        return getProperty(STDERR_ENCODING);
    }

    /**
     * Gets the current value from the system properties map for {@value #STDOUT_ENCODING}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getStdOutEncoding() {
        return getProperty(STDOUT_ENCODING);
    }

    /**
     * Gets the current value from the system properties map for {@value #SUN_NET_HTTP_SERVER_DRAIN_AMOUNT}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getSunNetHttpServerDrainAmount() {
        return getProperty(SUN_NET_HTTP_SERVER_DRAIN_AMOUNT);
    }

    /**
     * Gets the current value from the system properties map for {@value #SUN_NET_HTTP_SERVER_IDLE_INTERVAL}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getSunNetHttpServerIdleInterval() {
        return getProperty(SUN_NET_HTTP_SERVER_IDLE_INTERVAL);
    }

    /**
     * Gets the current value from the system properties map for {@value #SUN_NET_HTTP_SERVER_MAX_IDLE_CONNECTIONS}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getSunNetHttpServerMaxIdleConnections() {
        return getProperty(SUN_NET_HTTP_SERVER_MAX_IDLE_CONNECTIONS);
    }

    /**
     * Gets the current value from the system properties map for {@value #SUN_NET_HTTP_SERVER_MAX_REQ_HEADERS}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getSunNetHttpServerMaxReqHeaders() {
        return getProperty(SUN_NET_HTTP_SERVER_MAX_REQ_HEADERS);
    }

    /**
     * Gets the current value from the system properties map for {@value #SUN_NET_HTTP_SERVER_MAX_REQ_TIME}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getSunNetHttpServerMaxReqTime() {
        return getProperty(SUN_NET_HTTP_SERVER_MAX_REQ_TIME);
    }

    /**
     * Gets the current value from the system properties map for {@value #SUN_NET_HTTP_SERVER_MAX_RSP_TIME}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getSunNetHttpServerMaxRspTime() {
        return getProperty(SUN_NET_HTTP_SERVER_MAX_RSP_TIME);
    }

    /**
     * Gets the current value from the system properties map for {@value #SUN_NET_HTTP_SERVER_NO_DELAY}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getSunNetHttpServerNoDelay() {
        return getProperty(SUN_NET_HTTP_SERVER_NO_DELAY);
    }

    /**
     * Gets the current value from the system properties map for {@value #SUN_SECURITY_KRB5_PRINCIPAL}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     * @since 3.15.0
     */
    public static String getSunSecurityKrb5Principal() {
        return getProperty(SUN_SECURITY_KRB5_PRINCIPAL);
    }

    /**
     * Gets the current value from the system properties map for {@value #USER_COUNTRY}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     */
    public static String getUserCountry() {
        return getProperty(USER_COUNTRY);
    }

    /**
     * Gets the current value from the system properties map for {@value #USER_DIR}.
     * <p>
     * Returns {@code null} if the property cannot be read due to a {@link SecurityException}.
     * </p>
     *
     * @return The system property value or {@code null} if the property is absent or a security problem occurs.
     */

    private static String toKey(final Class<?> clazz, final String key, final boolean simpleKey) {
        return "";
    }


}
