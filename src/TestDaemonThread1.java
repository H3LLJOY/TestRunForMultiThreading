public class TestDaemonThread1 extends Thread{
    @Override
    public void run(){
        if(Thread.currentThread().isDaemon())
            System.out.println("Daemon thread Works");
        else
            System.out.println("User Thread Works");
    }
}
