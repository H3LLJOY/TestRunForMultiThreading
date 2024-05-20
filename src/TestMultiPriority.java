public class TestMultiPriority extends Thread{
    @Override
    public void run(){
        System.out.println("thread Name:"+Thread.currentThread().getName());
        System.out.println("thread priority:"+Thread.currentThread().getPriority());
    }
}
