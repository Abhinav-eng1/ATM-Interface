import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATMInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User("12345", "6789"); // Dummy credentials

        System.out.println("Welcome to the ATM!");

        System.out.print("Enter User ID: ");
        String enteredId = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String enteredPin = scanner.nextLine();

        if (user.authenticate(enteredId, enteredPin)) {
            while (true) {
                System.out.println("\n--- ATM Menu ---");
                System.out.println("1. Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        user.printTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        if (user.withdraw(withdrawAmount)) {
                            System.out.println("Withdrawal successful.");
                        } else {
                            System.out.println("Insufficient balance.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        user.deposit(depositAmount);
                        System.out.println( "Deposit successful.");
                        break;
                    case 4:
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        scanner.nextLine(); // Clear buffer
                        System.out.print("Enter recipient User ID: ");
                        String recipientId = scanner.nextLine();

                        // For simplicity, create a dummy recipient
                        User receiver = new User(recipientId, "0000");

                        if (user.transfer(receiver, transferAmount)) {
                            System.out.println("Transfer successful.");
                        } else {
                            System.out.println("Insufficient balance.");
                        }
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } else {
            System.out.println("Invalid User ID or PIN. Access Denied.");
        }

        scanner.close();
    }
}

// --- User class below ---
class User {
    private String userId;
    private String pin;
    private double balance;
    private List<String> transactionHistory;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 1000.0; // Starting balance
        this.transactionHistory = new ArrayList<>();
    }

    public boolean authenticate(String id, String pin) {
        return this.userId.equals(id) && this.pin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: Rs." + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: Rs." + amount);
            return true;
        } else {
            return false;
        }
    }

    public boolean transfer(User receiver, double amount) {
        if (amount <= balance) {
            balance -= amount;
            receiver.deposit(amount);
            transactionHistory.add("Transferred Rs." + amount + " to User ID: " + receiver.userId);
            return true;
        } else {
            return false;
        }
    }

    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println( "\n Transaction History:");
            for (String tx : transactionHistory) {
                System.out.println("- " + tx);
            }
        }
    }
}
