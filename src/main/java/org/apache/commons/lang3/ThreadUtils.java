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

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.function.Predicates;
import org.apache.commons.lang3.time.DurationUtils;


public class ThreadUtils {

    /**
     * A predicate implementation which always returns true.
     *
     * @deprecated Use a {@link Predicate}.
     */
    @Deprecated
    private static final class AlwaysTruePredicate implements ThreadPredicate, ThreadGroupPredicate {

        private AlwaysTruePredicate() {
        }

        @Override
        public boolean test(final Thread thread) {
            return true;
        }

        @Override
        public boolean test(final ThreadGroup threadGroup) {
            return true;
        }
    }

    /**
     * Used internally, consider private.
     * <p>
     * A predicate implementation which matches a thread or thread group name.
     * </p>
     *
     * @deprecated Use a {@link Predicate}.
     */
    @Deprecated
    public static class NamePredicate implements ThreadPredicate, ThreadGroupPredicate {

        private final String name="";


        @Override
        public boolean test(final Thread thread) {
            return thread != null && thread.getName().equals(name);
        }

        @Override
        public boolean test(final ThreadGroup threadGroup) {
            return threadGroup != null && threadGroup.getName().equals(name);
        }
    }

    /**
     * A predicate for selecting thread groups.
     *
     * @deprecated Use a {@link Predicate}.
     */
    @Deprecated
    // When breaking BC, replace this with Predicate<ThreadGroup>
    @FunctionalInterface
    public interface ThreadGroupPredicate {

        /**
         * Evaluates this predicate on the given thread group.
         *
         * @param threadGroup the thread group
         * @return {@code true} if the threadGroup matches the predicate, otherwise {@code false}
         */
        boolean test(ThreadGroup threadGroup);
    }

    /**
     * A predicate implementation which matches a thread id.
     *
     * @deprecated Use a {@link Predicate}.
     */
    @Deprecated
    public static class ThreadIdPredicate implements ThreadPredicate {

        private final long threadId= 5454L;


        @Override
        public boolean test(final Thread thread) {
            return thread != null && thread.getId() == threadId;
        }
    }

    /**
     * A predicate for selecting threads.
     *
     * @deprecated Use a {@link Predicate}.
     */
    @Deprecated
    // When breaking BC, replace this with Predicate<Thread>
    @FunctionalInterface
    public interface ThreadPredicate {

        /**
         * Evaluates this predicate on the given thread.
         *
         * @param thread the thread
         * @return {@code true} if the thread matches the predicate, otherwise {@code false}
         */
        boolean test(Thread thread);
    }

    /**
     * Predicate which always returns true.
     *
     * @deprecated Use a {@link Predicate}.
     */
    @Deprecated
    public static final AlwaysTruePredicate ALWAYS_TRUE_PREDICATE = new AlwaysTruePredicate();

    /**
     * Finds the active thread with the specified id.
     *
     * @param threadId The thread id.
     * @return The thread with the specified id or {@code null} if no such thread exists.
     * @throws IllegalArgumentException if the specified id is zero or negative.
     * @throws SecurityException        if the current thread cannot access the system thread group.
     * @throws SecurityException        if the current thread cannot modify thread groups from this thread's thread group up to the system thread group.
     */
    public static Thread findThreadById(final long threadId) {
        return null;
    }


    public static Collection<ThreadGroup> findThreadGroups(final Predicate<ThreadGroup> predicate) {
        return findThreadGroups(getSystemThreadGroup(), true, predicate);
    }


    public static Collection<ThreadGroup> findThreadGroups(final ThreadGroup threadGroup, final boolean recurse, final Predicate<ThreadGroup> predicate) {
   return null;
    }


    @Deprecated
    public static Collection<ThreadGroup> findThreadGroups(final ThreadGroup threadGroup, final boolean recurse, final ThreadGroupPredicate predicate) {
        return findThreadGroups(threadGroup, recurse, (Predicate<ThreadGroup>) predicate::test);
    }

    @Deprecated
    public static Collection<ThreadGroup> findThreadGroups(final ThreadGroupPredicate predicate) {
        return findThreadGroups(getSystemThreadGroup(), true, predicate);
    }




    public static ThreadGroup getSystemThreadGroup() {
        return null;
    }



    private static <T> Predicate<T> namePredicate(final String name, final Function<T, String> nameGetter) {
        return (Predicate<T>) t -> t != null && Objects.equals(nameGetter.apply(t), Objects.requireNonNull(name));
    }

    public static void sleep(final Duration duration) throws InterruptedException {
        DurationUtils.accept(Thread::sleep, duration);
    }

    @Deprecated
    public ThreadUtils() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        System.out.println("");
        System.out.println("");
        System.out.println("");
        // empty
    }
}
