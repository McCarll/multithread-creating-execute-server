package main.java.asynctaskexecutor;

import main.java.asynctaskexecutor.entity.Task;
import main.java.asynctaskexecutor.taskworker.TaskCreator;
import main.java.asynctaskexecutor.taskworker.TaskExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static volatile AtomicInteger counter = new AtomicInteger(-1);

    public static BlockingQueue<Task> queue = new PriorityBlockingQueue<>();

    public static TaskCreator creatorThread1 = new TaskCreator();
    public static TaskCreator creatorThread2 = new TaskCreator();
    public static TaskExecutor executorThread = new TaskExecutor();

    public static void main(String... args) throws Exception {
        executorThread.start();

        creatorThread1.run();
        creatorThread2.run();

        creatorThread1.join();
        creatorThread2.join();
        executorThread.kill();
    }

}
