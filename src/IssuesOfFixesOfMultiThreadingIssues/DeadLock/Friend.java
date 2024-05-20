package IssuesOfFixesOfMultiThreadingIssues.DeadLock;

/**
 * This demonstrates a possible deadlock.
 */

class Friend {
    private String name;
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public  Friend(String name){
        this.name=name;
    }

    public  void bow(Friend bower) {
        synchronized (lock1) {
            System.out.format("%s is bowing to %s\n", this.name, bower.getName());
            bower.bowBack(this);
        }
    }

    public  void bowBack(Friend bower) {
        synchronized (lock2) {
            System.out.format("%s is bowing back to %s\n", this.name, bower.getName());
        }
    }

    public String getName(){
        return name;
    }
}

