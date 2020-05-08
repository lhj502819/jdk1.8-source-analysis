package com.source.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author lihongjian
 * @since 2020/5/7
 */
public class Test1 {
    static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {
        test1();
        test2();
    }

    static void test1() {
        ReentrantLock reentrantLock1 = reentrantLock;

        reentrantLock1.lock();

        try {
            System.out.println("test1.........");
//            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock1.unlock();
        }
    }

    static void test2() {
        ReentrantLock reentrantLock1 = reentrantLock;

        reentrantLock1.lock();
        System.out.println("test2.........");
        reentrantLock1.unlock();
    }
}
