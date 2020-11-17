package com.java.api.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Description: 基于AQS构建的线程独占锁
 * @Author: kevin
 * @Date: 2019/5/4
 */
public class MutexBasedOnAQS implements Lock {
    /**
     * 自定义同步器；按代码规范放置在类开头
     */
    static class MutexSync extends AbstractQueuedSynchronizer {

        /**
         * state 为0表示未占用，1表示占用
         *
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 多线程操作；用CAS方式设置状态
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 单线程操作；设置状态
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException("already in `released` state");
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 引用AQS中定义的condition
         *
         * @return
         */
        final Condition newCondition() {
            return new ConditionObject();
        }
    }

    private final MutexSync sync = new MutexSync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }


    public static void main(String[] args) throws InterruptedException {
        MutexBasedOnAQS mutex = new MutexBasedOnAQS();
        Condition flag = mutex.newCondition();
        Thread thr1 = new Thread(getLockJob(mutex), "thread-1");
        Thread thr2 = new Thread(getLockJob(mutex), "thread-2");
        thr1.start();
        thr2.start();
        thr1.join();
        thr2.join();
        System.out.println("--- separating line ---");
        thr1 = new Thread(waitJob(mutex, flag), "thread-1");
        thr2 = new Thread(singalJob(mutex, flag), "thread-2");
        thr1.start();
        thr2.start();
        thr1.join();
        thr2.join();
    }

    static Runnable getLockJob(final MutexBasedOnAQS mutex) {
        return () -> {
            try {
                TimeUnit.SECONDS.sleep(Math.round(5 * Math.random()));
                mutex.lock();
                System.out.println(Thread.currentThread().getName() + " | get lock");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + " | release lock");
                mutex.unlock();
            }
        };
    }

    static Runnable waitJob(final MutexBasedOnAQS mutex, final Condition condition) {
        return () -> {
            try {
                mutex.lock();
                System.out.println(Thread.currentThread().getName() + " | await...");
                condition.await();
                System.out.println(Thread.currentThread().getName() + " | run again");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mutex.unlock();
            }
        };
    }

    static Runnable singalJob(final MutexBasedOnAQS mutex, final Condition condition) {
        return () -> {
            try {
                mutex.lock();
                TimeUnit.SECONDS.sleep(Math.round(5 * Math.random()));
                System.out.println(Thread.currentThread().getName() + " | singal others...");
                condition.signalAll();
                TimeUnit.SECONDS.sleep(Math.round(5 * Math.random()));
                System.out.println(Thread.currentThread().getName() + " | keep run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mutex.unlock();
            }
        };
    }
}
