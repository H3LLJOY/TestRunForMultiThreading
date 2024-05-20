package IssuesOfFixesOfMultiThreadingIssues.LiveLock;

class Spoon {
    private Diner owner;

    public Spoon(Diner d) {
        owner = d;
    }

    public synchronized void use() {
        System.out.printf("%s has eaten!%n", owner.getName());
    }

    public Diner getOwner() {
        return owner;
    }

    public void setOwner(Diner d) {
        owner = d;
    }
}

class Diner {
    private String name;
    private boolean isHungry;

    public Diner(String n) {
        name = n;
        isHungry = true;
    }

    public String getName() {
        return name;
    }

    public boolean isHungry() {
        return isHungry;
    }

    public void eatWith(Spoon spoon, Diner spouse) {
        while (isHungry) {
            if (spoon.getOwner() != this) {
                try {
                    Thread.sleep(1); // Wait for the spoon to become available
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            if (spouse.isHungry) {
                System.out.printf("%s: You eat first, dear %s!%n", name, spouse.getName());
                spoon.setOwner(spouse);
                continue;
            }

            spoon.use();
            isHungry = false;
            System.out.printf("%s: I have eaten!%n", name);
            spoon.setOwner(spouse);
        }
    }
}

public class LivelockExample {
    public static void main(String[] args) {
        Diner husband = new Diner("Husband");
        Diner wife = new Diner("Wife");

        Spoon spoon = new Spoon(husband);

        Thread husbandThread = new Thread(() -> husband.eatWith(spoon, wife));
        Thread wifeThread = new Thread(() -> wife.eatWith(spoon, husband));

        husbandThread.start();
        wifeThread.start();
    }
}
