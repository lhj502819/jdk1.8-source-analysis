package com.source.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Exchangeer（交换者）是一个用于线程间写作的工具类。Exchanger用于进行线程间的数据交换。
 * 它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。
 * 这两个线程通过exchange()方法交换数据，如果第一个线程限制性exchange()，它会一直等待第二个线程页执行exchange()方法
 * 当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产出来的数据传递给对方
 *
 * 可以用于遗传算法
 *
 * 可以用于校对工作：比如两个人校对两个人录入的数据。
 *
 * @author lihongjian
 * @since 2020/5/7
 */
public class ExchangerTest {


    private static final Exchanger<String> exger = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(()->{
            try {
                String A = "银行流水A";
                String B = exger.exchange(A);
                System.out.println("A和B的数据是否一致："+B.equals(A));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(()->{
            try {
                String B = "银行流水B";
                String A = exger.exchange(B);
                System.out.println("A和B的数据是否一致："+B.equals(A));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
