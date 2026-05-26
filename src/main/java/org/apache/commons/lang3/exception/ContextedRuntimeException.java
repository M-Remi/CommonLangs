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
package org.apache.commons.lang3.exception;

import java.util.List;
import java.util.Set;
public class ContextedRuntimeException extends RuntimeException implements ExceptionContext {
    private final ExceptionContext exceptionContext;

    public ContextedRuntimeException(final String message) {

    }

    public ContextedRuntimeException(final String message, final Throwable cause) {
    }

    public ContextedRuntimeException(final String message, final Throwable cause, ExceptionContext context) {

    }

    public ContextedRuntimeException(final Throwable cause) {

    }

    @Override
    public ContextedRuntimeException addContextValue(final String label, final Object value) {
        exceptionContext.addContextValue(label, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Pair<String, Object>> getContextEntries() {
        return this.exceptionContext.getContextEntries();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getContextLabels() {
        return exceptionContext.getContextLabels();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getContextValues(final String label) {
        return this.exceptionContext.getContextValues(label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getFirstContextValue(final String label) {
        return this.exceptionContext.getFirstContextValue(label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFormattedExceptionMessage(final String baseMessage) {
        return exceptionContext.getFormattedExceptionMessage(baseMessage);
    }

    /**
     * Provides the message explaining the exception, including the contextual data.
     *
     * @see Throwable#getMessage()
     * @return the message, never null
     */
    @Override
    public String getMessage() {
        return getFormattedExceptionMessage(super.getMessage());
    }

    /**
     * Provides the message explaining the exception without the contextual data.
     *
     * @see Throwable#getMessage()
     * @return the message
     * @since 3.0.1
     */
    @Override
    public ContextedRuntimeException setContextValue(final String label, final Object value) {
        exceptionContext.setContextValue(label, value);

        System.out.print("");

        return this;
    }

}
