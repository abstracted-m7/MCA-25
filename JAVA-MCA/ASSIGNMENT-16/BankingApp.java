import java.util.Scanner;

// Base class
class BankAccount {

    String accountHolder;
    double balance;

    void getDetails(Scanner sc) {
        System.out.print("Enter Account Holder Name: ");
        accountHolder = sc.nextLine();

        System.out.print("Enter Account Balance: ");
        balance = sc.nextDouble();
    }

    // Method to be overridden
    double calculateInterest() {
        return 0;   // default (no interest)
    }

    void display() {
        System.out.println("Account Holder : " + accountHolder);
        System.out.println("Balance        : " + balance);
    }
}

// Savings Account (child class)
class SavingsAccount extends BankAccount {

    double interestRate = 4.0; // 4%

    @Override
    double calculateInterest() {
        return (balance * interestRate) / 100;
    }
}

// Current Account (child class)
class CurrentAccount extends BankAccount {

    @Override
    double calculateInterest() {
        return 0; // No interest for current account
    }
}

// Main class
public class BankingApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Select Account Type:");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        BankAccount account;

        if (choice == 1) {
            account = new SavingsAccount();
        } else if (choice == 2) {
            account = new CurrentAccount();
        } else {
            System.out.println("Invalid choice!");
            sc.close();
            return;
        }

        account.getDetails(sc);

        System.out.println("\nAccount Details:");
        account.display();
        System.out.println("Calculated Interest: " + account.calculateInterest());

        sc.close();
    }
}
