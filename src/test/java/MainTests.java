package test.java;

import jdk.nashorn.internal.ir.annotations.Ignore;
import main.java.asynctaskexecutor.entity.Task;
import main.java.asynctaskexecutor.service.QueueService;
import main.java.asynctaskexecutor.taskworker.TaskCreator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTests {

    @Test
    @Ignore // this test is part  of testSortQueueNotWorkingWithHiload
    public void testSortQueue() {
        QueueService.QueueSingleton queueService = QueueService.QueueSingleton.INSTANCE;
        TaskCreator taskCreator = new TaskCreator(100);
        taskCreator.run();// fill the queue random values
        assertTrue(queueService.size() == 100);
        AtomicReference<Task> tempTask = new AtomicReference<>(new Task());
        tempTask.get().setTime(LocalDateTime.of(0, Month.JANUARY, 1, 1, 1));
        while (queueService.size() != 0) {
            // check that poll work is correct
            Task nextTask = queueService.getNext();
            assertTrue(tempTask.get().getTime().compareTo(nextTask.getTime()) < 0);
            tempTask.set(nextTask);
        }
        assertTrue(queueService.size() == 0);
    }

    @Test
    public void testSortQueueNotWorkingWithHiload() {
        QueueService.QueueSingleton queueService = QueueService.QueueSingleton.INSTANCE;
        TaskCreator taskCreator = new TaskCreator(10000);
        taskCreator.run();// fill the queue random values
        assertTrue(queueService.size() == 10000);
        AtomicReference<Task> tempTask = new AtomicReference<>(new Task());
        tempTask.get().setTime(LocalDateTime.of(0, Month.JANUARY, 1, 1, 1));
        AtomicInteger count = new AtomicInteger(0);
        while (queueService.size() >= 100) {
            // check that poll work is correct
            Task nextTask = queueService.getNext();
//            sometimes we can get few "sorted" values - it's random
            if (nextTask.getTime().compareTo(tempTask.get().getTime()) < 0 && count.get() < 10) {
                count.incrementAndGet();
            } else {
//                but if "random" is so often - it's not random and we have unworking sorting method
                assertFalse(nextTask.getTime().compareTo(tempTask.get().getTime()) < 0);
                count.set(0);
            }
            tempTask.set(nextTask);
        }
        assertTrue(queueService.size() == 99);
//        now we must check that sort is enable
//        need reset tempTask because item in 100 position can be more then item in 99 position(with sort)
        tempTask.get().setTime(LocalDateTime.of(0, Month.JANUARY, 1, 1, 1));
        while (queueService.size() != 0) {
            Task nextTask = queueService.getNext();
            assertTrue(tempTask.get().getTime().compareTo(nextTask.getTime()) < 0);
            tempTask.set(nextTask);
        }
        assertTrue(queueService.size() == 0);
    }


}
