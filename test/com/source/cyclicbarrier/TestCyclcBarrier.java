package com.source.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * TODO
 *
 * @author lihongjian
 * @since 2020/5/7
 */
public class TestCyclcBarrier {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2,new Action());
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("1");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                System.out.println("2");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }
}

class Action implements Runnable{
    @Override
    public void run() {
        System.out.println("action ....");
    }
}
