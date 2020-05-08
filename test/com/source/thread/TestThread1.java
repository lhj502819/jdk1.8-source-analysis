package com.source.thread;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author lihongjian
 * @since 2020/4/14
 */
public class TestThread1 {
    public static void main(String[] args) {
        new Thread(new TimeWating(),"TimeWatingThread").start();
        new Thread(new Waiting(),"WaitingThread").start();
        //使用两个Blocked线程，一个获取锁成功，另一个被阻塞
        new Thread(new Blocked(),"BlockedThread-1").start();
        new Thread(new Blocked(),"BlockedThread-2").start();
    }
    //该线程不断睡眠
    public static class TimeWating implements Runnable{
        @Override
        public void run() {
            while (true){
                SleepUtils.second(100);
            }
        }
    }
    //该线程在Waiting.class上等待
    static class Waiting implements Runnable{
        @Override
        public void run() {
            while (true){
                synchronized (Waiting.class){
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //该线程在Blocked.class实例上加锁之后，不回释放该锁
    static class Blocked implements Runnable{
        @Override
        public void run() {
            synchronized (Blocked.class){
                SleepUtils.second(100);
            }
        }
    }

}

class SleepUtils{
    public static final void second(long seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
