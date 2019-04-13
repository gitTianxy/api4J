package com.java.api.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: kevin
 * @Date: 2019/4/13
 */
public class CountDownLatchDemo {
    final static CountDownLatch signal = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main begin---");
        new Thread(new Job(), "thread-A").start();
        new Thread(new Job(), "thread-B").start();
        signal.await();
        System.out.println("main end---");
    }

    static class Job implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "| job START, do count-down...");
            signal.countDown();
            System.out.println(Thread.currentThread().getName() + "| job FINISH");
        }
    }
}
