package com.java.api.concurrent;

import java.util.concurrent.*;

/**
 * @Author: kevin
 * @Date: 2019/4/13
 */
public class ForkJoinDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool executor = new ForkJoinPool();
        // RecursiveTask(有返回值)
        CountTask count = new CountTask(0, 10);
        executor.execute(count);
        System.out.println("count from 0-10:" + count.get());

        // RecursiveAction(无返回值)，通过.join()等待结果
        PrintTask print = new PrintTask(0, 10);
        executor.execute(print);
        print.join();
    }

    static class PrintTask extends RecursiveAction {
        private int lower;
        private int upper;

        public PrintTask(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        protected void compute() {
            boolean canCompute = (upper == lower);
            if (canCompute) {
                System.out.println(lower);
            } else {
                int middle = (upper + lower) / 2;
                PrintTask leftTask = new PrintTask(lower, middle);
                leftTask.fork();
                PrintTask rightTask = new PrintTask(middle + 1, upper);
                rightTask.fork();

                leftTask.join();
                rightTask.join();
            }
        }
    }

    static class CountTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 2;
        private int start;
        private int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            boolean canCompute = (end - start) < THRESHOLD;
            if (canCompute) {
                // compute sub-task
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                // recursively divide task
                int middle = (start + end) / 2;
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle + 1, end);
                // do work
                leftTask.fork();
                rightTask.fork();
                // wait for result
                int leftRes = leftTask.join();
                int rightRes = rightTask.join();
                // merge result
                sum = leftRes + rightRes;
            }
            return sum;
        }
    }
}
