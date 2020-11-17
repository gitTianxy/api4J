package com.java.api.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Description: 基于AQS构建的运行两线程占用的锁
 * @Author: kevin
 * @Date: 2019/5/4
 */
public class TwinsLockBasedOnAQS implements Lock {

    private final TwinsLockSync lock = new TwinsLockSync();

    @Override
    public void lock() {
        lock.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        lock.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return lock.tryAcquireShared(1) >= 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return lock.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        lock.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    static class TwinsLockSync extends AbstractQueuedSynchronizer {

        public TwinsLockSync() {
            setState(2);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            while (true) {
                int current = getState();
                int newState = current - arg;
                if (newState < 0 || compareAndSetState(current, newState)) {
                    return newState;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            while (true) {
                int current = getState();
                int newState = current + arg;
                if (compareAndSetState(current, newState)) {
                    return true;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final TwinsLockBasedOnAQS lock = new TwinsLockBasedOnAQS();

        int threadNum = 10;
        Thread[] workers = new Thread[threadNum];
        for (int i=0;i<threadNum; i++) {
            workers[i] = worker(lock, "thread-" + i);
            workers[i].start();
        }
        for (int i=0;i<threadNum; i++) {
            workers[i].join();
        }
    }

    static Thread worker(final TwinsLockBasedOnAQS lock, final String name) {
        return new Thread(()->{
            try {
                lock.lock();
                Thread.sleep(1000L);
                System.out.println(name + " | get lock");
                Thread.sleep(1000L);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(name + " | release...");
            }
        },name);
    }
}
