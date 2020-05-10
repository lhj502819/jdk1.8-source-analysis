package com.source.threadpool;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author lihongjian
 * @since 2020/5/9
 */
public class ThreadPoolExecutorTest {
    /**
     * 向线程池提交任务的方式有两种
     * 1、execute() ：用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功。
     * <p>
     * 2、submit()：用于提交需要返回值的任务。线程池会返回一个future类型的对象，通过这个future对象可以判断任务是否执行成功
     * 并且可以通过future的get()方法来获取返回值，get()方法会阻塞当前线程直到任务完成，而使用get(long timeout,TimeUnit unit)方法
     * 则会阻塞当前线程一段时间之后立即返回，这时候有可能任务没有执行完
     */
    @Test
    public void testExecute() {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5,
                20, 5000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new RejectHandler());
        for(int i = 0 ; i < 50 ;i++){
            int finalI = i;
            threadPool.execute(()->{
                System.out.println("执行任务"+ finalI+"--"+Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
//        for (int i = 0; i < 50; i++) {
//            Callable<Object> callable = Executors.callable(() -> {
//                System.out.println("Thread invoke " + Thread.currentThread().getName());
//            });
//            threadPool.submit(callable);
//            try {
//                System.out.println(submit.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程池中线程数量：" + threadPool.getPoolSize());
        ;

    }


}

class Invoke implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("Thread invoke " + Thread.currentThread().getName());
        Thread.sleep(2000);
        return "Thread invoke " + Thread.currentThread().getName();
    }
}

class RejectHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("任务执行失败" + r.toString());
    }
}
