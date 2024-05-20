package ProducerConsumerProblem;

import java.util.LinkedList;
import java.util.Queue;

class ProducerConsumer {
    private final int MAX_SIZE = 5;
    private final Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();
        Thread producerThread = new Thread(pc.new Producer(), "Producer");
        Thread consumerThread = new Thread(pc.new Consumer(), "Consumer");

        producerThread.start();
        consumerThread.start();
    }

    private class Producer implements Runnable {
        @Override
        public void run() {
            int value = 0;
            while (true) {
                try {
                    produce(value++);
                    Thread.sleep(1000); // Simulate time taken to produce an item
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        private void produce(int value) throws InterruptedException {
            synchronized (queue) {
                while (queue.size() == MAX_SIZE) {
                    System.out.println("Queue is full. Producer is waiting...");
                    queue.wait();
                }
                queue.add(value);
                System.out.println("Produced: " + value);
                queue.notifyAll();
            }
        }
    }

    private class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    consume();
                    Thread.sleep(1500); // Simulate time taken to consume an item
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        private void consume() throws InterruptedException {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    System.out.println("Queue is empty. Consumer is waiting...");
                    queue.wait();
                }
                int value = queue.poll();
                System.out.println("Consumed: " + value);
                queue.notifyAll();
            }
        }
    }
}

