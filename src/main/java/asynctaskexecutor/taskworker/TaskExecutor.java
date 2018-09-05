package main.java.asynctaskexecutor.taskworker;

import main.java.asynctaskexecutor.Main;
import main.java.asynctaskexecutor.entity.Task;

import java.util.concurrent.Callable;

import static main.java.asynctaskexecutor.Main.executorThread;

public class TaskExecutor extends Thread {

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
                if (Main.queue.size() > 0) {
                    Task task = Main.queue.poll();
                    System.out.println("executor : " + task.getTime() + " :: " + task.getTaskExecute().call());
                }
            } catch (Exception e) {
                System.out.println("executor = [" + e + "]");
            }
        } while (isStop() != false | Main.queue.size() > 0);
    }

}

