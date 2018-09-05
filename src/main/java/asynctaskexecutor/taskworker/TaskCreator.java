package main.java.asynctaskexecutor.taskworker;

import main.java.asynctaskexecutor.Main;
import main.java.asynctaskexecutor.entity.Task;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.Callable;

import static main.java.asynctaskexecutor.Main.counter;

public class TaskCreator extends Thread {

    private static int threadCount = 1000; // as default 1k messages may be in queue without comporator

    private int getThreadCount() {
        return threadCount;
    }

    private void setThreadCount(int threadCount) {
        TaskCreator.threadCount = threadCount;
    }

    public TaskCreator(Integer threadCount) {
        setThreadCount(threadCount);
    }

    public TaskCreator() {
    }

    @Override
    public void run() {
        while (counter.get() < getThreadCount() - 1) {
            Main.queue.add(createtask(counter.incrementAndGet()));
        }
    }

    public static Task createtask(Integer value) {
        LocalDateTime start = LocalDateTime.of(1970, Month.JANUARY, 1, 1, 1);
        long days = ChronoUnit.DAYS.between(start, LocalDateTime.now());
        LocalDateTime randomDate = start.plusDays(new Random().nextInt((int) days + 1));
        Callable callable = () -> value;
        try {
            System.out.println("creator : " + randomDate + " : " + " :: " + callable.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Task(randomDate, callable);
    }
}
