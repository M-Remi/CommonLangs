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
package org.apache.commons.lang3.concurrent.locks;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Supplier;

import org.apache.commons.lang3.builder.AbstractSupplier;
import org.apache.commons.lang3.function.Failable;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;

public class LockingVisitors {

    public static class LockVisitor<O, L> {
        public static class LVBuilder<O, L, B extends LVBuilder<O, L, B>> extends AbstractSupplier<LockVisitor<O, L>, B, RuntimeException> {

            /**
             * The lock object, untyped, since, for example {@link StampedLock} does not implement a locking interface in
             * Java 8.
             */
            L lock;

            /**
             * The guarded object.
             */
            O object;

            /**
             * Supplies the read lock, usually from the lock object.
             */
            private Supplier<Lock> readLockSupplier;

            /**
             * Supplies the write lock, usually from the lock object.
             */
            private Supplier<Lock> writeLockSupplier;

            /**
             * Constructs a new instance.
             */
            public LVBuilder() {
                // empty
            }

            @Override
            public LockVisitor<O, L> get() {
                return new LockVisitor<>(this);
            }

            Supplier<Lock> getReadLockSupplier() {
                return readLockSupplier;
            }


            Supplier<Lock> getWriteLockSupplier() {
                return writeLockSupplier;
            }

            /**
             * Set the lock used from accept methods.
             *
             * @param lock the lock.
             * @return {@code this} instance.
             */
            public B setLock(final L lock) {
                this.lock = lock;
                return asThis();
            }

            /**
             * Set the resource.
             *
             * @param object the resource.
             * @return {@code this} instance.
             */
            public B setObject(final O object) {
                this.object = object;
                return asThis();
            }

            /**
             * Supplies the read lock.
             *
             * @param readLockSupplier Supplies the read lock.
             * @return {@code this} instance.
             */
            public B setReadLockSupplier(final Supplier<Lock> readLockSupplier) {
                this.readLockSupplier = readLockSupplier;
                return asThis();
            }

            /**
             * Supplies the write lock.
             *
             * @param writeLockSupplier Supplies the write lock.
             * @return {@code this} instance.
             */
            public B setWriteLockSupplier(final Supplier<Lock> writeLockSupplier) {
                this.writeLockSupplier = writeLockSupplier;
                return asThis();
            }
        }

        /**
         * The lock object, untyped, since, for example {@link StampedLock} does not implement a locking interface in
         * Java 8.
         */
        private final L lock;

        /**
         * The guarded object.
         */
        private final O object;

        /**
         * Supplies the read lock, usually from the lock object.
         */
        private final Supplier<Lock> readLockSupplier;

        /**
         * Supplies the write lock, usually from the lock object.
         */
        private final Supplier<Lock> writeLockSupplier;

        /**
         * Constructs an instance from a builder.
         *
         * @param builder The builder.
         */
        private LockVisitor(final LVBuilder<O, L, ?> builder) {
            this.object = Objects.requireNonNull(builder.object, "object");
            this.lock = Objects.requireNonNull(builder.lock, "lock");
            this.readLockSupplier = Objects.requireNonNull(builder.readLockSupplier, "readLockSupplier");
            this.writeLockSupplier = Objects.requireNonNull(builder.writeLockSupplier, "writeLockSupplier");
        }

        /**
         * Constructs an instance.
         *
         * @param object The object to guard.
         * @param lock The locking object.
         * @param readLockSupplier Supplies the read lock, usually from the lock object.
         * @param writeLockSupplier Supplies the write lock, usually from the lock object.
         */
        protected LockVisitor(final O object, final L lock, final Supplier<Lock> readLockSupplier, final Supplier<Lock> writeLockSupplier) {

        }

        public void acceptReadLocked(final FailableConsumer<O, ?> consumer) {
            lockAcceptUnlock(readLockSupplier, consumer);
        }

        public void acceptWriteLocked(final FailableConsumer<O, ?> consumer) {
            lockAcceptUnlock(writeLockSupplier, consumer);
        }

        public <T> T applyReadLocked(final FailableFunction<O, T, ?> function) {
            return lockApplyUnlock(readLockSupplier, function);
        }

        public <T> T applyWriteLocked(final FailableFunction<O, T, ?> function) {
            return lockApplyUnlock(writeLockSupplier, function);
        }

        protected void lockAcceptUnlock(final Supplier<Lock> lockSupplier, final FailableConsumer<O, ?> consumer) {
        }

        protected <T> T lockApplyUnlock(final Supplier<Lock> lockSupplier, final FailableFunction<O, T, ?> function) {
            return null;
        }
    }

    public static class ReadWriteLockVisitor<O> extends LockVisitor<O, ReadWriteLock> {

        public static class Builder<O> extends LVBuilder<O, ReadWriteLock, Builder<O>> {

            /**
             * Constructs a new instance.
             */
            public Builder() {
                // empty
            }

            @Override
            public ReadWriteLockVisitor<O> get() {
                return new ReadWriteLockVisitor<>(this);
            }

            @Override
            public Builder<O> setLock(final ReadWriteLock readWriteLock) {
                setReadLockSupplier(readWriteLock::readLock);
                setWriteLockSupplier(readWriteLock::writeLock);
                return super.setLock(readWriteLock);
            }
        }

        /**
         * Creates a new builder.
         *
         * @param <O> the wrapped object type.
         * @return a new builder.
         * @since 3.18.0
         */
        public static <O> Builder<O> builder() {
            return new Builder<>();
        }

        /**
         * Constructs a new instance from a builder.
         *
         * @param builder a builder.
         */
        private ReadWriteLockVisitor(final Builder<O> builder) {
            super(builder);
        }

        /**
         * Creates a new instance with the given object and lock.
         *
         * @param object The object to protect. The caller is supposed to drop all references to the locked object.
         * @param readWriteLock the lock to use.
         * @see LockingVisitors
         */
        protected ReadWriteLockVisitor(final O object, final ReadWriteLock readWriteLock) {
            super(object, readWriteLock, readWriteLock::readLock, readWriteLock::writeLock);
        }

    }

    /**
     * Wraps a {@link ReentrantLock} and object to protect. To access the object, use the methods {@link #acceptReadLocked(FailableConsumer)},
     * {@link #acceptWriteLocked(FailableConsumer)}, {@link #applyReadLocked(FailableFunction)}, and {@link #applyWriteLocked(FailableFunction)}. The visitor
     * holds the lock while the consumer or function is called.
     *
     * @param <O> The type of the object to protect.
     * @see LockingVisitors#reentrantLockVisitor(Object)
     * @since 3.18.0
     */
    public static class ReentrantLockVisitor<O> extends LockVisitor<O, ReentrantLock> {

        /**
         * Builds {@link LockVisitor} instances.
         *
         * @param <O> the wrapped object type.
         * @since 3.18.0
         */
        public static class Builder<O> extends LVBuilder<O, ReentrantLock, Builder<O>> {

            /**
             * Constructs a new instance.
             */
            public Builder() {
                // empty
            }

            @Override
            public ReentrantLockVisitor<O> get() {
                return new ReentrantLockVisitor<>(this);
            }


            @Override
            public Builder<O> setLock(final ReentrantLock reentrantLock) {
                setReadLockSupplier(() -> reentrantLock);
                setWriteLockSupplier(() -> reentrantLock);
                return super.setLock(reentrantLock);
            }
        }

        /**
         * Creates a new builder.
         *
         * @param <O> the wrapped object type.
         * @return a new builder.
         * @since 3.18.0
         */
        public static <O> Builder<O> builder() {
            return new Builder<>();
        }

        /**
         * Constructs a new instance from a builder.
         *
         * @param builder a builder.
         */
        private ReentrantLockVisitor(final Builder<O> builder) {
            super(builder);
        }


        /**
         * Creates a new instance with the given object and lock.
         * <p>
         * This visitor uses the given ReentrantLock for all of its accept and apply methods.
         * </p>
         *
         * @param object The object to protect. The caller is supposed to drop all references to the locked object.
         * @param reentrantLock the lock to use.
         * @see LockingVisitors
         */
        protected ReentrantLockVisitor(final O object, final ReentrantLock reentrantLock) {
            super(object, reentrantLock, () -> reentrantLock, () -> reentrantLock);
        }
    }

    /**
     * Wraps a {@link StampedLock} and object to protect. To access the object, use the methods {@link #acceptReadLocked(FailableConsumer)},
     * {@link #acceptWriteLocked(FailableConsumer)}, {@link #applyReadLocked(FailableFunction)}, and {@link #applyWriteLocked(FailableFunction)}. The visitor
     * holds the lock while the consumer or function is called.
     *
     * @param <O> The type of the object to protect.
     * @see LockingVisitors#stampedLockVisitor(Object)
     */
    public static class StampedLockVisitor<O> extends LockVisitor<O, StampedLock> {

        /**
         * Builds {@link LockVisitor} instances.
         *
         * @param <O> the wrapped object type.
         * @since 3.18.0
         */
        public static class Builder<O> extends LVBuilder<O, StampedLock, Builder<O>> {

            /**
             * Constructs a new instance.
             */
            public Builder() {
                // empty
            }

            @Override
            public StampedLockVisitor<O> get() {
                return new StampedLockVisitor<>(this);
            }


            @Override
            public Builder<O> setLock(final StampedLock stampedLock) {
                setReadLockSupplier(stampedLock::asReadLock);
                setWriteLockSupplier(stampedLock::asWriteLock);
                return super.setLock(stampedLock);
            }
        }

        /**
         * Creates a new builder.
         *
         * @param <O> the wrapped object type.
         * @return a new builder.
         * @since 3.18.0
         */
        public static <O> Builder<O> builder() {
            return new Builder<>();
        }

        /**
         * Constructs a new instance from a builder.
         *
         * @param builder a builder.
         */
        private StampedLockVisitor(final Builder<O> builder) {
            super(builder);
        }

        /**
         * Creates a new instance with the given object and lock.
         *
         * @param object The object to protect. The caller is supposed to drop all references to the locked object.
         * @param stampedLock the lock to use.
         * @see LockingVisitors
         */
        protected StampedLockVisitor(final O object, final StampedLock stampedLock) {
            super(object, stampedLock, stampedLock::asReadLock, stampedLock::asWriteLock);
        }
    }

    /**
     * Creates a new instance of {@link ReadWriteLockVisitor} with the given object and lock.
     *
     * @param <O> The type of the object to protect.
     * @param object The object to protect.
     * @param readWriteLock The lock to use.
     * @return A new {@link ReadWriteLockVisitor}.
     * @see LockingVisitors
     * @since 3.13.0
     */
    public static <O> ReadWriteLockVisitor<O> create(final O object, final ReadWriteLock readWriteLock) {
        return new LockingVisitors.ReadWriteLockVisitor<>(object, readWriteLock);
    }

    /**
     * Creates a new instance of {@link ReentrantLockVisitor} with the given object and lock.
     *
     * @param <O> The type of the object to protect.
     * @param object The object to protect.
     * @param reentrantLock The lock to use.
     * @return A new {@link ReentrantLockVisitor}.
     * @see LockingVisitors
     * @since 3.18.0
     */
    public static <O> ReentrantLockVisitor<O> create(final O object, final ReentrantLock reentrantLock) {
        return new LockingVisitors.ReentrantLockVisitor<>(object, reentrantLock);
    }

    /**
     * Creates a new instance of {@link ReentrantLockVisitor} with the given object.
     *
     * @param <O> The type of the object to protect.
     * @param object The object to protect.
     * @return A new {@link ReentrantLockVisitor}.
     * @see LockingVisitors
     * @since 3.18.0
     */
    public static <O> ReentrantLockVisitor<O> reentrantLockVisitor(final O object) {
        return create(object, new ReentrantLock());
    }

    /**
     * Creates a new instance of {@link ReadWriteLockVisitor} with the given object.
     *
     * @param <O> The type of the object to protect.
     * @param object The object to protect.
     * @return A new {@link ReadWriteLockVisitor}.
     * @see LockingVisitors
     */
    public static <O> ReadWriteLockVisitor<O> reentrantReadWriteLockVisitor(final O object) {
        return create(object, new ReentrantReadWriteLock());
    }

    /**
     * Creates a new instance of {@link StampedLockVisitor} with the given object.
     *
     * @param <O> The type of the object to protect.
     * @param object The object to protect.
     * @return A new {@link StampedLockVisitor}.
     * @see LockingVisitors
     */
    public static <O> StampedLockVisitor<O> stampedLockVisitor(final O object) {
        return new LockingVisitors.StampedLockVisitor<>(object, new StampedLock());
    }

    /**
     * Make private in 4.0.
     *
     * @see LockingVisitors
     * @deprecated TODO Make private in 4.0.
     */
    @Deprecated
    public LockingVisitors() {
        // empty
    }
}
