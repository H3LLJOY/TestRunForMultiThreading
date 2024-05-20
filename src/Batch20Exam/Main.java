package Batch20Exam;

import java.util.ArrayList;
//
//public class PopCornMachine {
//    private  Integer popCorns = 0;
//    private Integer cooks= 0;
//    private Boolean robotArmAvailable = true;
//
//    public static void main(String[] args) {
//
//    }
//
//
//    private class PopCorns implements Runnable{
//
//        @Override
//        public void run() {
//            while(true){
//                synchronized (popCorns){
//                    if(popCorns==3){
//                        System.out.println("Waiting for arm to pickUp");
//                        try {
//                            popCorns.wait();
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                    else{
//                        popCorns++;
//
//                        System.out.println("Created New PopCorn and available popCorns: "+popCorns);
//                    }
//
//                }
//            }
//        }
//    }
//}
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class PopcornDispenser {
    private final Queue<String> outputBin = new LinkedList<>();
    private final int maxCapacity = 3;
    private final Object lock = new Object();

    public void produce(String cookerName) throws InterruptedException {
        synchronized (lock) {
            while (outputBin.size() == maxCapacity) {
                System.out.println(cookerName + " is waiting for space in the output bin.");
                lock.wait();
            }
            outputBin.add(cookerName + " produced a popcorn bag.");
            System.out.println(cookerName + " produced a popcorn bag.");
            lock.notify();
        }
    }

    public String consume() throws InterruptedException {
        synchronized (lock) {
            while (outputBin.isEmpty()) {
                System.out.println("Robot arm is waiting for a popcorn bag.");
                lock.wait();
            }
            String popcornBag = outputBin.poll();
            System.out.println("Robot arm picked up a popcorn bag: " + popcornBag);
            lock.notify();
            return popcornBag;
        }
    }
}

class Cooker implements Runnable {
    private final PopcornDispenser dispenser;
    private final String name;

    public Cooker(PopcornDispenser dispenser, String name) {
        this.dispenser = dispenser;
        this.name = name;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                Thread.sleep(random.nextInt(1000));
                dispenser.produce(name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class RobotArm implements Runnable {
    private final PopcornDispenser dispenser;

    public RobotArm(PopcornDispenser dispenser) {
        this.dispenser = dispenser;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                Thread.sleep(random.nextInt(1000));
                dispenser.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        PopcornDispenser dispenser = new PopcornDispenser();
        new Thread(new Cooker(dispenser, "Cooker 1")).start();
        new Thread(new Cooker(dispenser, "Cooker 2")).start();
        new Thread(new RobotArm(dispenser)).start();
    }
}