package com.java.api.concurrent;

/**
 *
 * @Author: kevin
 * @Date: 2019/4/14
 */
public class SynchronizedDemo {

    String name;
    int count;
    static int clsCount;
    Integer lock = 1;
    Integer lock2 = 2;
    static Integer clsLock = 1;
    static Integer clsLock2 = 2;

    public SynchronizedDemo(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws InterruptedException {
        seperatingLine("sync on method");
        syncOnMethod();
        seperatingLine("sync on static method");
        syncOnStaticMethod();
        seperatingLine("sync on instance");
        syncOnInstance();
        seperatingLine("sync on class");
        syncOnClass();
        seperatingLine("sync on instance.field");
        syncOnField();
        seperatingLine("sync on class.field");
        syncOnStaticField();
    }

    /**
     * 锁是在实例上还是在方法上: 实例上
     *
     * @throws InterruptedException
     */
    static void syncOnMethod() throws InterruptedException {
        final int threadNum = 5;
        Thread[] threads = new Thread[threadNum];
        // 同一个实例的sync方法看看有没有同步
        SynchronizedDemo demo = new SynchronizedDemo("instance_s");
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(() -> {
                demo.syncMethod1();
                demo.syncMethod2();
            }, "thread_" + i);
            threads[i].start();
        }
        for (int i = 0; i < threadNum; i++) {
            threads[i].join();
        }

        seperatingLine();
        // 独立实例的sync方法间看有没有同步
        for (int i = 0; i < threadNum; i++) {
            SynchronizedDemo sd = new SynchronizedDemo("instance_" + i);
            threads[i] = new Thread(() -> {
                sd.syncMethod1();
                sd.syncMethod2();
            }, "thread_" + i);
            threads[i].start();
        }
        for (int i = 0; i < threadNum; i++) {
            threads[i].join();
        }
    }

    /**
     * 锁加在类上
     *
     * @throws InterruptedException
     */
    static void syncOnStaticMethod() throws InterruptedException {
        final int threadNum = 5;
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(() -> SynchronizedDemo.staticSyncMethod(), "thread_" + i);
            threads[i].start();
        }
        for (int i = 0; i < threadNum; i++) {
            threads[i].join();
        }
    }

    /**
     * 等价于`lock on method`
     *
     * @throws InterruptedException
     */
    static void syncOnInstance() throws InterruptedException {
        final int threadNum = 5;
        Thread[] threads = new Thread[threadNum];
        // lock on instance:
        final SynchronizedDemo demo = new SynchronizedDemo("lock-on-instance");
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(() -> {
                synchronized (demo) {
                    demo.count += 1;
                    System.out.println(Thread.currentThread().getName() + "," + demo.name + "| " + demo.count);
                }
            }, "thread_" + i);
            threads[i].start();
        }
        for (int i = 0; i < threadNum; i++) {
            threads[i].join();
        }
    }

    /**
     * 等价于`lock on static method`
     */
    static void syncOnClass() throws InterruptedException {
        final int threadNum = 5;
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(() -> {
                synchronized (SynchronizedDemo.class) {
                    SynchronizedDemo.clsCount += 1;
                    System.out.println(Thread.currentThread().getName() + "| " + SynchronizedDemo.clsCount);
                }
            }, "thread_" + i);
            threads[i].start();
        }
        for (int i = 0; i < threadNum; i++) {
            threads[i].join();
        }
    }

    /**
     * 锁定在实例的某个字段对象上
     *
     * NOTE: 被锁定的对象不能为null
     *
     * @throws InterruptedException
     */
    static void syncOnField() throws InterruptedException {
        final int threadNum = 5;
        Thread[] threads = new Thread[threadNum];
        SynchronizedDemo demo = new SynchronizedDemo("");
        for (int i = 0; i < threadNum; i++) {
            if(i % 2 == 0) {
                threads[i] = new Thread(() -> {
                    synchronized (demo.lock) {
                        System.out.println(Thread.currentThread().getName() + "| work START...");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "| work FINISH");
                    }
                }, "thread_" + i);
            } else {
                threads[i] = new Thread(() -> {
                    synchronized (demo.lock2) {
                        System.out.println(Thread.currentThread().getName() + "| work START...");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "| work FINISH");
                    }
                }, "thread_" + i);
            }
            threads[i].start();
        }
        for (int i = 0; i < threadNum; i++) {
            threads[i].join();
        }
    }

    /**
     * 锁定在类的某个字段对象上
     *
     * @throws InterruptedException
     */
    static void syncOnStaticField() throws InterruptedException {
        final int threadNum = 5;
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            if (i % 2 == 0) {
                threads[i] = new Thread(() -> {
                    synchronized (SynchronizedDemo.clsLock) {
                        System.out.println(Thread.currentThread().getName() + "| work START...");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "| work FINISH");
                    }
                }, "thread_" + i);
            } else {
                threads[i] = new Thread(() -> {
                    synchronized (SynchronizedDemo.clsLock2) {
                        System.out.println(Thread.currentThread().getName() + "| work START...");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "| work FINISH");
                    }
                }, "thread_" + i);
            }
            threads[i].start();
        }
        for (int i = 0; i < threadNum; i++) {
            threads[i].join();
        }
    }

    /**
     * sync锁的可重入性: 可重入--Java中线程获得对象锁的操作是以线程为单位的, 而不是以调用为单位的.
     */
    static synchronized void staticSyncMethod() {
        clsCount += 1;
        System.out.println(Thread.currentThread().getName() + "| " + clsCount);
        staticSyncMethodPlus();
    }

    static synchronized void staticSyncMethodPlus() {
        clsCount += 2;
        System.out.println(Thread.currentThread().getName() + "| " + clsCount);
    }

    synchronized void syncMethod1() {
        this.count += 1;
        System.out.println(Thread.currentThread().getName() + "," + this.name + "| " + this.count);
    }

    synchronized void syncMethod2() {
        this.count += 2;
        System.out.println(Thread.currentThread().getName() + "," + this.name + "| " + this.count);
    }

    static void seperatingLine() {
        System.out.println("--------------------");
    }

    static void seperatingLine(String name) {
        System.out.println("----------" + name + "----------");
    }
}
