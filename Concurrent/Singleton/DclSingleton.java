package DCL;

import java.util.Spliterator;

public class Singleton {
    private static volatile Singleton instance;
    private Singleton() {
        System.out.println("Create a singleton : A");
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized(Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public void dosomething() {
        System.out.println("The singleton is still alive!");
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        singleton.dosomething();
    }
}


