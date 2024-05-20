public class HelloThread implements Runnable{
    @Override
    public void run() {
        for(int i = 0 ; i<10 ;i++){
            print();
        }
    }
    private void print(){
        System.out.println("Hello Thread !");
    }
}
