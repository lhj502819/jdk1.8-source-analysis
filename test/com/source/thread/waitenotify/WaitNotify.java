package com.source.thread.waitenotify;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author lihongjian
 * @since 2020/5/5
 */
public class WaitNotify {

    static Object lock = new Object();

    static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(), "notifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    System.out.println(Thread.currentThread() + "flag is true. wait@" + LocalDateTime.now());
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread() + "flag is false. running@" + LocalDateTime.now());
            }
        }
    }


    static class Notify implements Runnable {
        @Override
        public void run() {
            //加锁
            synchronized (lock){
                System.out.println(Thread.currentThread() + "hold lock. notify@" + LocalDateTime.now());
                //获取到lock锁之后，notifyAll，修改flag
                lock.notifyAll();
                flag = false;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock){
                System.out.println(Thread.currentThread() + "hold lock. notify@" + LocalDateTime.now());
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
