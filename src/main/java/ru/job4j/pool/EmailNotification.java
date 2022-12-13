package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void send(String subject, String body, String email) {
    }

    private void emailTo(User user) {
        String subject = "Notification %sto email %s.".formatted(user.getUsername(), user.getEmail());
        String body = "Add a new event to %s.".formatted(user.getUsername());
        pool.submit(() -> send(subject, body, user.getEmail()));
        close();
    }

    private void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Execute " + Thread.currentThread().getName());
    }
}
