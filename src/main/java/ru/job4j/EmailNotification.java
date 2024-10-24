package ru.job4j;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool;

    EmailNotification() {
        pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        pool.submit(() -> {
            User user = new User("user@user", "user");
            emailTo(user);
        });
    }

    public void emailTo(User user) {
        String subject = String.format("Notification %s to email %s", user.getUsername(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getUsername());
        pool.submit(() -> send(subject, body, user.getEmail()));
        close();
    }

    public void send(String subject, String body, String email) {
    }

    public void close() {
        pool.shutdown();
    }

    public static void main(String[] args) {
        new EmailNotification();
    }
}
