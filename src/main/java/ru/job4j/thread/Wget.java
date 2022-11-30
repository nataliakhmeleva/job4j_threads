package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String output;

    public Wget(String url, int speed, String output) {
        this.url = url;
        this.speed = speed;
        this.output = output;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(output)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int bytesWrite = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                bytesWrite += bytesRead;
                if (bytesWrite >= speed) {
                    long end = System.currentTimeMillis();
                    if (end - start < 1000) {
                        Thread.sleep(1000);
                    }
                    bytesWrite = 0;
                    start = System.currentTimeMillis();
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("The number of arguments should be three."
                    + " The first is the URL, the second is the speed, the third is the output.");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String output = args[2];
        Thread wget = new Thread(new Wget(url, speed, output));
        wget.start();
        wget.join();
    }
}
