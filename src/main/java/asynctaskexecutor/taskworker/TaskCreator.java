package main.java.asynctaskexecutor.taskworker;

import main.java.asynctaskexecutor.entity.Task;
import main.java.asynctaskexecutor.service.QueueService;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.Callable;

public class TaskCreator extends Thread {

    private static QueueService.QueueSingleton queueService = QueueService.QueueSingleton.INSTANCE;
    private static int threadCount;

    private int getThreadCount() {
        return threadCount;
    }

    private void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public TaskCreator(Integer threadCount) {
        this.threadCount = threadCount;
    }

    public TaskCreator() {
        this.setThreadCount(1000);// as default generate 1k messages
    }

    @Override
    public void run() {
        while (threadCount-- > 0) {
            queueService.add(createtask(queueService.getNextNumber()));
        }
    }

    private static Task createtask(Integer value) {
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
