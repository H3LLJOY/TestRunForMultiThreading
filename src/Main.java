// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
//        Thread hr1 = new Thread(new HelloRunnable(),"hr1");
//        Thread hr2 = new Thread(new HelloRunnable(),"hr2");
//        hr1.start();
//        hr1.join();
//        hr1.interrupt();
//        hr2.start();
//          Thread t1 = new TestDaemonThread1();
//          Thread t2 = new TestDaemonThread1();
//          Thread t3 = new TestDaemonThread1();
//          t1.setDaemon(true);
//          t1.start();
//
//          t2.start();
//          t3.start();
        TestMultiPriority m1 = new TestMultiPriority();
        TestMultiPriority m2 = new TestMultiPriority();

        m2.setPriority(Thread.MAX_PRIORITY);
        m1.start();
        //m1.setPriority(Thread.MIN_PRIORITY);
        m2.start();
    }
}