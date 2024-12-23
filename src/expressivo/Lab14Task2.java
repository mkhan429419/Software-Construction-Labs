package expressivo;

public class Lab14Task2 {

    public static void main(String[] args) {
        // Shared counter object
        Counter counter = new Counter();

        // Creating three threads that access the shared counter
        Thread thread1 = new Thread(new CounterIncrementer(counter));
        Thread thread2 = new Thread(new CounterIncrementer(counter));
        Thread thread3 = new Thread(new CounterIncrementer(counter));

        // Starting the threads
        thread1.start();
        thread2.start();
        thread3.start();

        // Waiting for threads to finish
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Displaying the final value of the counter
        System.out.println("Final Counter Value: " + counter.getValue());
    }
}

// Shared Counter class
class Counter {
    private int value = 0;

    // Synchronized method to increment the counter
    public synchronized void increment() {
        value++;
    }

    // Method to get the current value of the counter
    public int getValue() {
        return value;
    }
}

// Runnable task that increments the counter 100 times
class CounterIncrementer implements Runnable {
    private final Counter counter;

    // Constructor to accept the shared counter object
    public CounterIncrementer(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            counter.increment();
        }
    }
}
