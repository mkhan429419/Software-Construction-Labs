package expressivo;

import java.util.concurrent.CopyOnWriteArrayList;

public class Lab14Task3 {

    public static void main(String[] args) {
        // Shared thread-safe list
        CopyOnWriteArrayList<Integer> sharedList = new CopyOnWriteArrayList<>();

        // Create multiple threads to access the shared list
        Thread writerThread1 = new Thread(new ListWriter(sharedList, 1));
        Thread writerThread2 = new Thread(new ListWriter(sharedList, 2));
        Thread readerThread = new Thread(new ListReader(sharedList));

        // Start the threads
        writerThread1.start();
        writerThread2.start();
        readerThread.start();

        // Wait for threads to finish
        try {
            writerThread1.join();
            writerThread2.join();
            readerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state of the shared list
        System.out.println("Final Shared List: " + sharedList);
    }
}

// Writer class that adds numbers to the shared list
class ListWriter implements Runnable {
    private final CopyOnWriteArrayList<Integer> sharedList;
    private final int multiplier;

    public ListWriter(CopyOnWriteArrayList<Integer> sharedList, int multiplier) {
        this.sharedList = sharedList;
        this.multiplier = multiplier;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            sharedList.add(i * multiplier);
            System.out.println(Thread.currentThread().getName() + " added: " + (i * multiplier));
            try {
                Thread.sleep(100); // Simulate some delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Reader class that iterates over the shared list
class ListReader implements Runnable {
    private final CopyOnWriteArrayList<Integer> sharedList;

    public ListReader(CopyOnWriteArrayList<Integer> sharedList) {
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) { // Read the list 3 times
            System.out.println(Thread.currentThread().getName() + " reads: " + sharedList);
            try {
                Thread.sleep(150); // Simulate some delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
