package com.source.map;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author lihongjian
 * @since 2020/5/6
 */
public class MapTest {
    public static void main(String[] args) {
//        Thread t1 = new Thread(() -> {
//            test();
//        });
//        Thread t2 = new Thread(() -> {
//            System.out.println(Thread.currentThread().getName()+"started "+ System.currentTimeMillis());
//            test2();
//            System.out.println(Thread.currentThread().getName()+"started "+ System.currentTimeMillis());
//        });
//        t1.start();
//        t2.start();
//        List<String> list = new ArrayList<>();
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        list.add("222");
//        System.out.println(list.toArray());
        Date date = new Date();
        String s = date.toString();
        System.out.println(s);
    }
    static synchronized void test(){
        System.out.println("--------test1------："+Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static synchronized void test2(){
        System.out.println("--------test2-----");
    }
}
