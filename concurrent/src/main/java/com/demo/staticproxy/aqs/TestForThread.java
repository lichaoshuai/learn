package com.demo.staticproxy.aqs;

import java.util.concurrent.Semaphore;

/**
 * @author chaoshuai.li
 * @date 2021/2/23
 * @description
 */
public class TestForThread {
    private static Semaphore A = new Semaphore(1);
    private static Semaphore B = new Semaphore(1);
    private static Semaphore C = new Semaphore(1);


    static class ThreadA extends Thread {

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    A.acquire();
                    System.out.println("A");
                    B.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    static class ThreadB extends Thread {

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    B.acquire();
                    System.out.println("B");
                    C.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    static class ThreadC extends Thread {

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    C.acquire();
                    System.out.println("C");
                    A.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        // 开始只有A可以获取, BC都不可以获取, 保证了A最先执行
        B.acquire();
        C.acquire();
        new ThreadA().start();
        new ThreadB().start();
        new ThreadC().start();
    }
}
