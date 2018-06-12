package com.atsho.atservice.volatiletest;

public class TestVolatile {

    public static volatile int numb = 0;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    numb++;
                }
            }).start();
        }
        Thread.sleep(2000);
        System.out.println(numb);
    }

}
