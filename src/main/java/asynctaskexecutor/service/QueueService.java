package main.java.asynctaskexecutor.service;

import main.java.asynctaskexecutor.entity.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class QueueService {

    private static BlockingQueue<Task> queue = new PriorityBlockingQueue<>();

    private static AtomicInteger counter = new AtomicInteger(-1);

    public enum QueueSingleton {
        INSTANCE;

        public void add(Task task) {
            queue.add(task);
        }

        public Task getNext() {
            return queue.poll();
        }

        public int size() {
            return queue.size();
        }

        public boolean isEmpty() {
            return size() == 0 ? true : false;
        }

        public int getNextNumber() {
            return counter.incrementAndGet();
        }

    }


}
