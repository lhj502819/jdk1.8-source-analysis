package com.source.reentrantlock;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lihongjian
 * @since 2020/5/4
 */
public class FairAndUnFairTest {
    //公平锁
    private static final ReentrantLock2 fairReentrantLock = new ReentrantLock2(true);
    //非公平锁
    private static final ReentrantLock2 unfairReentrantLock = new ReentrantLock2(false);
    @Test
    public void testFair() throws InterruptedException {
        testLock(fairReentrantLock);
    }

    private static void testLock(ReentrantLock2 lock) {
        for(int i = 0 ; i < 5 ; i++){
            Job job = new Job(lock);
            job.start();
        }
    }

    private static class Job extends Thread {
        private ReentrantLock2 reentrantLock2;

        public Job(ReentrantLock2 reentrantLock2) {
            this.reentrantLock2 = reentrantLock2;
        }

        @Override
        public void run() {
            reentrantLock2.lock();
            try {
                System.out.println("-----------------"+Thread.currentThread().getName()+"-----------------");
                Collection<Thread> queuedThreads = reentrantLock2.getQueuedThreads();
                for (Thread queuedThread : queuedThreads) {
                    System.out.print(queuedThread.getName()+",");
                }
                System.out.println();
            } catch (Exception e) {

            }finally {
                reentrantLock2.unlock();
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {

        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        @Override
        protected Collection<Thread> getQueuedThreads() {
            List<Thread> list = new ArrayList<>(super.getQueuedThreads());//获取到等待队列中的线程
            Collections.reverse(list);
            return list;
        }
    }
}
