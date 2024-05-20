package Batch20Exam.MyOne;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    private static Queue<PopCornBag> outputBin = new LinkedList<>();
    private static int max_size = 3;
    private static  class Cooker implements Runnable {
        @Override
        public void run() {
             while (true){
                 synchronized (outputBin) {
                    while(outputBin.size()==max_size){
                        System.out.println("Buffer Full! .Waiting for Robot arm to take a popcorn");
                        try {
                            outputBin.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    outputBin.add(new PopCornBag());
                    System.out.println(Thread.currentThread().getName()+"Created a pop corn bag ,Buffer size is:"+outputBin.size());
                    outputBin.notifyAll();
                 }
                 try {
                     Thread.sleep(2000);
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }
             }

        }

    }

    private static class RobotArm implements Runnable {

        @Override
        public void run() {
            while (true) {
                 synchronized (outputBin){
                    while (outputBin.isEmpty()) {
                        System.out.println("No Bags.Waiting for new Bags!");
                        try {

                            outputBin.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    outputBin.remove();
                    System.out.println("One Bag was taken by Robot.Current bags:" + outputBin.size());
                    outputBin.notifyAll();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public static void main(String[] args)  {
        Thread Raju =new Thread(new Cooker(),"Raju");
        Thread Jinna = new Thread(new Cooker(),"Jinna");
        Thread zx101 = new Thread(new RobotArm(),"zx101");
        Raju.start();
        Jinna.start();
        zx101.start();

    }
}
