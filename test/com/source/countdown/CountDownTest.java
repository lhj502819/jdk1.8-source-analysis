package com.source.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * TODO
 *
 * @author lihongjian
 * @since 2020/5/7
 */
public class CountDownTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread t1 = new Thread(() -> {
            System.out.println("第一个线程执行");
            countDownLatch.countDown();
        });
        Thread t2 = new Thread(() -> {
            System.out.println("第二个线程执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        });
        t1.start();
        t2.start();
        countDownLatch.await();
        System.out.println("执行完了"+Thread.currentThread().getName());
    }
}
