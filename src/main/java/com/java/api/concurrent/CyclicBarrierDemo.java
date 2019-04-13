package com.java.api.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: kevin
 * @Date: 2019/4/13
 */
public class CyclicBarrierDemo {
    final static CyclicBarrier barrier = new CyclicBarrier(2, new BarrierAction());

    public static void main(String[] args) {
        System.out.println("main begin---");
        new Thread(new Job(), "thread-A").start();
        new Thread(new Job(), "thread-B").start();
        System.out.println("main end---");
    }

    static class Job implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "| job START, do await...");
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "| job FINISH");
        }
    }

    static class BarrierAction implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "| do barrier-action");
        }
    }
}
