# Popcorn Dispenser Simulation

This Java program simulates a popcorn dispenser using the producer-consumer pattern with multiple threads. The program includes two cookers (`Raju` and `Jinna`) producing popcorn bags and a robot arm (`zx101`) that takes the bags from the output bin.

## Code

```java
package Batch20Exam.MyOne;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    private static Queue<PopCornBag> outputBin = new LinkedList<>();
    private static int max_size = 3;

    private static class Cooker implements Runnable {
        @Override
        public void run() {
            synchronized (outputBin) {
                while (true) {
                    while(outputBin.size() == max_size){
                        System.out.println("Buffer Full! Waiting for Robot arm to take a popcorn");
                        try {
                            outputBin.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    outputBin.add(new PopCornBag());
                    System.out.println(Thread.currentThread().getName() + " Created a popcorn bag, Buffer size is: " + outputBin.size());
                    outputBin.notifyAll();
                }
            }
        }
    }

    private static class RobotArm implements Runnable {
        @Override
        public void run() {
            synchronized (outputBin) {
                while (true) {
                    while (outputBin.isEmpty()) {
                        System.out.println("No Bags. Waiting for new Bags!");
                        try {
                            outputBin.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    outputBin.remove();
                    System.out.println("One Bag was taken by Robot. Current bags: " + outputBin.size());
                    outputBin.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread Raju = new Thread(new Cooker(), "Raju");
        Thread Jinna = new Thread(new Cooker(), "Jinna");
        Thread zx101 = new Thread(new RobotArm(), "zx101");
        zx101.start();
        Raju.start();
        Jinna.start();
    }
}
Explanation
This program defines two main classes, Cooker and RobotArm, each implementing the Runnable interface to enable multi-threading.

Cooker Class
The Cooker class simulates the process of creating popcorn bags. The run method in this class:

Uses a while (true) loop to continuously create popcorn bags.
Checks if the outputBin queue has reached its maximum capacity (max_size).
If the queue is full, it waits until notified by the RobotArm thread.
Adds a new PopCornBag to the outputBin and notifies all waiting threads.
RobotArm Class
The RobotArm class simulates the process of removing popcorn bags from the outputBin. The run method in this class:

Uses a while (true) loop to continuously remove popcorn bags.
Checks if the outputBin queue is empty.
If the queue is empty, it waits until notified by the Cooker threads.
Removes a PopCornBag from the outputBin and notifies all waiting threads.
main Method
The main method initializes and starts three threads:

Two Cooker threads (Raju and Jinna).
One RobotArm thread (zx101).
These threads run concurrently, simulating the continuous production and removal of popcorn bags in the dispenser.

Note
Ensure the PopCornBag class is defined elsewhere in your project, as it is used in the Cooker class but not defined in the provided code.
