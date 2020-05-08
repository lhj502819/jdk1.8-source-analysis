package com.source.cyclicbarrier;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 通过CyclicBarrier实现多线程计算数据，最后合并计算结果。
 * 用一个Excel保存了用户所有银行流水，每个Sheet保存了一个账户近一年的每笔银行流水，现在需要统计用户的日均银行流水
 * 先用多线程处理每个sheet里的银行流水，都执行完成之后，得到每个sheet的日均银行流水，最后再用barrierAction用这些线程的计算结果
 * 计算出整个Excel的日均银行流水
 *
 * @author lihongjian
 * @since 2020/5/7
 */
public class BankWaterService implements Runnable{


    /**
     * 创建4个屏障，处理完之后执行当前类的run方法
     */
    CyclicBarrier cyclicBarrier = new CyclicBarrier(4,this);

    /**
     * 假设只有4个sheet，所以只启动4个线程
     */
    private Executor executor = Executors.newFixedThreadPool(4);

    /**
     * 保存每个sheet页计算的结果
     */
    private ConcurrentHashMap<String,Integer> result = new ConcurrentHashMap<>();

    private void count(){
        for(int i = 0 ; i < 4 ; i++){
            int finalI = i;
            executor.execute(()->{
                //模仿计算完成插入数据
                result.put(Thread.currentThread().getName(),1);
                //计算完成，插入一个屏障
                try {
                    System.out.println("被阻塞线程数量"+cyclicBarrier.getNumberWaiting());
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void run() {
        int resultRece = 0;
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            resultRece+=entry.getValue();
        }
        //将结果输出
        System.out.println(resultRece);
    }

    public static void main(String[] args) {
        BankWaterService bankWaterService = new BankWaterService();
        bankWaterService.count();
    }
}
