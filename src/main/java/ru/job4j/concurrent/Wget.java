package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\rLoading : " + i  + "%");
        }
    }
}
