package com.source.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *
 * 限制线程的执行数
 *
 * @author lihongjian
 * @since 2020/5/7
 */
public class SemaphoreTest {
    private static final int THREAD_COUNT = 30;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    //虽然线程池中有30个线程，但是只允许10个并发
    private static Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) {
        for(int i = 0 ; i < THREAD_COUNT ; i++){
            threadPool.execute(()->{
                try {
                    //获得许可证
                    semaphore.acquire();
//                    Thread.sleep(2000);
//                    //归还许可中
//                    semaphore.release();
                    System.out.println("save data");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(semaphore.getQueueLength());;
        threadPool.shutdown();
    }
}
