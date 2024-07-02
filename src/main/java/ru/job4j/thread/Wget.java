package ru.job4j.thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String filename;

    public Wget(String url, int speed, String filename) {
        this.url = url;
        this.speed = speed;
        this.filename = filename;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File(filename);
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[speed];
            int bytesRead = 0;
            var downloadStart = System.currentTimeMillis();
            System.out.println("downloadStart = " + downloadStart);
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                if (bytesRead == speed) {
                    var downloadEnd = System.currentTimeMillis() - downloadStart;
                    System.out.println("bytesRead = " + bytesRead);
                    System.out.println("downloadEnd(ms) = " + downloadEnd);
                    if (downloadEnd < 1000) {
                        System.out.println("Thread sleep = " + (1000 - downloadEnd) + " ms");
                        Thread.sleep(1000 - downloadEnd);
                        downloadStart = System.currentTimeMillis();
                    }
                    bytesRead = 0;
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args[0] == null || args[0].isBlank()) {
            throw new IllegalArgumentException("url is empty");
        }
        String url = args[0];
        if (args[1] == null || args[1].isBlank()) {
            throw new IllegalArgumentException("url is empty");
        }

        int speed = Integer.parseInt(args[1]);

        if (speed <= 0) {
            throw new IllegalArgumentException("speed must be greater than 0");
        }
        if (args[2].isBlank()) {
            throw new IllegalArgumentException("filename must not be blank");
        }
        String filename = args[2];
        Thread wget = new Thread(new Wget(url, speed, filename));
        wget.start();
        wget.join();
    }
}