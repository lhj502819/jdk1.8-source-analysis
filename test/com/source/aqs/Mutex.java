package com.source.aqs;

import com.sun.org.apache.regexp.internal.RE;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * TODO
 *
 * @author lihongjian
 * @since 2020/5/3
 */
public class Mutex implements Lock {
    //静态内部类，自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer {
        //是否处于占用状态
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        //状态为0时获取锁
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {//CAS进行状态的修改
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //释放锁，修改状态为0
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            //不需要使用CAS操作，因为当前线程已经获取到了锁，肯定可以执行成功
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        //返回一个Condition，每个Condition都包含了一个condition队列
        Condition newCondition() {
            return new ConditionObject();
        }
    }

    private Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

    @Override
    public void unlock() {
        sync.release(0);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public static void main(String[] args) throws InterruptedException {
        Mutex mutex = new Mutex();
        test2(mutex);
        Thread t = new Thread(()->{
            try {
//                synchronized (mutex){
//                    System.out.println();
//                }
                mutex.lock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
//        System.out.println(222);
    }
    public static void test2(Mutex mutex){

        new Thread(()->{
            try {
                mutex.lock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    public static void test3(Mutex mutex){

        Thread thread = new Thread(() -> {
            try {
                synchronized (mutex) {
                    Thread.sleep(100000000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public static void test1(){
        Mutex mutex = new Mutex();
        Thread t = new Thread(()->{
            try {
                mutex.lock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t .start();
        t.isInterrupted();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            mutex.lock();
            System.out.println("1111");
        }).start();

//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                mutex.lock();
//                System.out.println("i");
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                mutex.unlock();
//            }).start();
//        }
    }
}
