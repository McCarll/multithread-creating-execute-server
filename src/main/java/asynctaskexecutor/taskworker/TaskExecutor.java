package main.java.asynctaskexecutor.taskworker;

import main.java.asynctaskexecutor.entity.Task;
import main.java.asynctaskexecutor.service.QueueService;


import static main.java.asynctaskexecutor.Main.threadPoolExecutor;

public class TaskExecutor extends Thread {

    private static QueueService.QueueSingleton queueService = QueueService.QueueSingleton.INSTANCE;

    private volatile boolean stop = false;

    public boolean isStop() {
        return stop;
    }

    public void kill() {
        stop = true;
    }

    @Override
    public void run() {
        do {
            try {
                if (!queueService.isEmpty()) {
                    Task task = queueService.getNext();
                    System.out.println("executor : " + task.getTime() + " :: " + task.getTaskExecute().call());
                }
            } catch (Exception e) {
                System.out.println("executor = [" + e + "]");
            }
        } while (isStop() != true | !queueService.isEmpty() | threadPoolExecutor.getActiveCount() != 0);
    }

}

