package IssuesOfFixesOfMultiThreadingIssues.DeadLock;




public class ExampleFromGITHUB_BOW {
    public static void main(String[] args) {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        new Thread(() -> alphonse.bow(gaston)).start();
        new Thread(() -> gaston.bow(alphonse)).start();
    }
}
