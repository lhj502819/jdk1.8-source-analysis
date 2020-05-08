package com.source.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * TODO
 *
 * @author lihongjian
 * @since 2020/5/7
 */
public class ArrayBlockingQueueTest {
    public static void main(String[] args) {
        ArrayBlockingQueue fairQueue = new ArrayBlockingQueue(1000,true);
    }
}
