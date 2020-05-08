package com.source.cas;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS：内存值A 预期值A 要更新的值B
 *
 * 1、可能会出现ABA问题
 *
 * 2、可能会出现一直自旋CAS，但是一直未更新成功，消耗CPU资源
 *
 * 3、只能保障一个变量的原子操作
 *
 * @author lihongjian
 * @since 2020/5/6
 */
public class CasTest {

    static AtomicInteger atomicInteger = new AtomicInteger(100);

    static AtomicStampedReference atomicStampedReference = new AtomicStampedReference(100,1);

    public static void main(String[] args) {
        Map<Object, Object> objectObjectMap = Collections.synchronizedMap(new HashMap<>());
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicInteger.compareAndSet(100, 110);
            atomicInteger.compareAndSet(110, 100);
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicInteger.compareAndSet(100, 120);
            System.out.println("atomicInteger："+result);
        });
        Thread t3 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100, 110,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            atomicStampedReference.compareAndSet(110, 100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println("atomicStampedReference 第一次更新");
        });
        Thread t4 = new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("t4获得的时间戳："+stamp);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100, 120, stamp, stamp+1);
            System.out.println("atomicStampedReference第二次更新："+result);
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();


    }
}
