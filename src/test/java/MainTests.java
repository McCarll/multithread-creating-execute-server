package test.java;

import main.java.asynctaskexecutor.entity.Task;
import main.java.asynctaskexecutor.taskworker.TaskCreator;
import main.java.asynctaskexecutor.taskworker.TaskExecutor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.atomic.AtomicReference;

import static main.java.asynctaskexecutor.Main.queue;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTests {

    TaskCreator creatorThread;
    TaskExecutor taskExecutor;

    @Test
    public void testSortQueue() throws Exception {
        creatorThread = new TaskCreator(99);
        creatorThread.run();
        creatorThread.join();
        assertTrue(queue.size() == 99);
        AtomicReference<Task> tempTask = new AtomicReference<>(new Task());
        tempTask.get().setTime(LocalDateTime.of(0, Month.JANUARY, 1, 1, 1));
        queue.forEach(task -> {
            Task currentTask = queue.poll();
            assertTrue(tempTask.get().getTime().isBefore(currentTask.getTime()));
            tempTask.set(currentTask);
        });
        taskExecutor = new TaskExecutor();
        taskExecutor.run();
        taskExecutor.kill();
        taskExecutor.join();
        assertTrue(queue.size() == 0);
    }


}
