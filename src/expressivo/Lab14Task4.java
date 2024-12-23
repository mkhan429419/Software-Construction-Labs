package expressivo;

import java.util.Random;

public class Lab14Task4 {

    public static void main(String[] args) {
        // Shared BankAccount object
        BankAccount account = new BankAccount(1000); // Initial balance: 1000

        // Creating multiple client threads
        Thread client1 = new Thread(new Client(account, "Client1"));
        Thread client2 = new Thread(new Client(account, "Client2"));
        Thread client3 = new Thread(new Client(account, "Client3"));

        // Start the threads
        client1.start();
        client2.start();
        client3.start();

        // Wait for threads to finish
        try {
            client1.join();
            client2.join();
            client3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display the final account balance
        System.out.println("Final Account Balance: $" + account.getBalance());
    }
}

// BankAccount class representing a shared bank account
class BankAccount {
    private int balance;

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    // Synchronized method to deposit money
    public synchronized void deposit(int amount) {
        balance += amount;
        System.out.println(Thread.currentThread().getName() + " deposited $" + amount + ", New Balance: $" + balance);
    }

    // Synchronized method to withdraw money
    public synchronized void withdraw(int amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew $" + amount + ", New Balance: $" + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " attempted to withdraw $" + amount + ", Insufficient Funds!");
        }
    }

    // Method to get the current balance
    public synchronized int getBalance() {
        return balance;
    }
}

// Client class representing a bank client performing transactions
class Client implements Runnable {
    private final BankAccount account;
    private final Random random = new Random();

    public Client(BankAccount account, String clientName) {
        this.account = account;
        Thread.currentThread().setName(clientName);
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) { // Each client performs 5 transactions
            int amount = random.nextInt(200) + 1; // Random amount between 1 and 200
            if (random.nextBoolean()) {
                account.deposit(amount); // Deposit
            } else {
                account.withdraw(amount); // Withdraw
            }
            try {
                Thread.sleep(200); // Simulate delay between transactions
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
