package com.source.thread.join;

/**
 * TODO
 *
 * @author lihongjian
 * @since 2020/5/6
 */
public class ThreadJoinTest {

    public static void main(String[] args) {
        Thread thread = Thread.currentThread();
        for(int i = 0 ; i < 10 ; i++){
            Thread thread1 = new Thread(new Demo(thread), String.valueOf(i));
            thread1.start();
            thread = thread1;
        }
        System.out.println(Thread.currentThread().getName()+" terminate");
    }

    static class Demo implements Runnable{

        private Thread thread ;

        public Demo(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName()+" terminate");
        }
    }
}
