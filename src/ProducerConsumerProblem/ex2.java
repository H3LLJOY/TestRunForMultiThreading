package ProducerConsumerProblem;

import java.util.ArrayList;
import java.util.List;

public  class ex2 {
    static final List<Integer> buffer = new ArrayList<>();

    public static void main(String args[]){
        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());
        producer.start();
        consumer.start();

    }


    private static class Producer implements  Runnable{

        @Override
        public void run() {
            while(true){
                synchronized (buffer) {
                    if (buffer.size() == 5) {
                        try {
                            System.out.println("waiting for Consuming");
                            buffer.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        buffer.add(2);
                        System.out.println("Produced :" + buffer.size());
                        buffer.notify();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    private static class Consumer implements Runnable {

        @Override
        public void run() {
            while(true){
                synchronized (buffer){
                    if(!buffer.isEmpty()){
                        buffer.removeFirst();
                        System.out.println("consumed one item and num of left items in buffer :"+buffer.size());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        buffer.notify();
                    }
                    else {
                        try {
                            System.out.println("Waiting for producing");
                            buffer.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

}


