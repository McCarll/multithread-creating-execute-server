package main.java.asynctaskexecutor;

import main.java.asynctaskexecutor.taskworker.TaskCreator;
import main.java.asynctaskexecutor.taskworker.TaskExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    public static void main(String... args) throws Exception {
        TaskExecutor executorThread = new TaskExecutor();
        threadPoolExecutor.execute(new TaskCreator(60));
        threadPoolExecutor.execute(new TaskCreator(70));
        executorThread.start();
        Thread.sleep(5000);// for example, now system have timeout
        System.out.println("\n\n\n============\n\n\n");
        threadPoolExecutor.execute(new TaskCreator(103));
        threadPoolExecutor.awaitTermination(50, TimeUnit.MILLISECONDS);
        threadPoolExecutor.shutdown();
        executorThread.kill();
    }

}
