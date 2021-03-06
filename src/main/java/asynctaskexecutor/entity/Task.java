package main.java.asynctaskexecutor.entity;

import main.java.asynctaskexecutor.service.QueueService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Callable;

public class Task implements Comparable<Task> {

    private static QueueService.QueueSingleton queueService = QueueService.QueueSingleton.INSTANCE;

    private LocalDateTime time;
    private Callable taskExecute;

    public Task() {
    }

    public Task(LocalDateTime time, Callable taskExecute) {
        this.time = time;
        this.taskExecute = taskExecute;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Callable getTaskExecute() {
        return taskExecute;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(time, task.time) &&
                Objects.equals(taskExecute, task.taskExecute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, taskExecute);
    }

    @Override
    public int compareTo(Task o) {
        // for example, if tasks in queue more then 100 - system overloading and we cant do sorting more
        if (queueService.size() > 100) {
            return 0;
        } else {
            return this.getTime().compareTo(o.getTime());
        }
    }

    @Override
    public String toString() {
        try {
            return "Task{" +
                    "time=" + time +
                    ", taskExecute=" + taskExecute.call() +
                    '}';
        } catch (Exception e) {
            return "error : " + e;
        }
    }
}
