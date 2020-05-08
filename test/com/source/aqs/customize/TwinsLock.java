package com.source.aqs.customize;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 同步工具类
 *
 * TwinsLocl能偶在同一时刻支持多个线程访问，这显然是共享式访问。因此需要使用AQS同步器的acquireShared(int arg)方法
 * 和Share相关的方法。
 * 1、需要重写tryAcquireShared(int arg)方法和tryReleaseShared(int arg)方法
 * 2、定义资源数，同一时刻允许两个线程同时访问，表明同步资源数为2.可以设置初始状态为status为2，当一个线程进行获取，status减1
 *    该线程释放则status+1，状态的合法范围值为0，1，2，其中0表示当前已经有两个线程获取了同步资源，再有其他线程访问应该被阻塞
 * 3、当同步状态变更时，需要使用compareAndSet(int expect,int update)方法做原子性保障
 *
 * @author lihongjian
 * @since 2020/5/4
 */
public class TwinsLock implements Lock {

    private Sync sync = new Sync(3);

    public static final class Sync extends AbstractQueuedSynchronizer{

        public Sync(int count) {
            if(count < 0){
                throw new IllegalArgumentException("count must large than zero");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            for(;;){
                int currnet = getState();
                int newCount = currnet - reduceCount;
                if(newCount < 0 || compareAndSetState(currnet,newCount))
                    return newCount;
            }
        }

        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for (;;){
                int currnet = getState();
                int newCount = currnet + returnCount;
                if(compareAndSetState(currnet,newCount))//因为是共享式释放同步状态，因此需要使用CAS操作
                    return true;
            }
        }
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
