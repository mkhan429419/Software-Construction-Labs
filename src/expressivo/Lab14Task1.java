package expressivo;

public class Lab14Task1 {

    public static void main(String[] args) {
        // Creating thread using Thread class
        Thread numberThread = new NumberThread();

        // Creating thread using Runnable interface
        Thread squareThread = new Thread(new SquareRunnable());

        // Starting both threads
        numberThread.start();
        squareThread.start();
    }
}

// Thread to print numbers from 1 to 10
class NumberThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Number: " + i);
            try {
                Thread.sleep(500); // Adding delay for better observation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Runnable to print squares of numbers from 1 to 10
class SquareRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Square: " + (i * i));
            try {
                Thread.sleep(500); // Adding delay for better observation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
