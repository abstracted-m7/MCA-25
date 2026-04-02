// Thread-0 depositing: 500
// Balance after deposit: 1500

// Thread-1 withdrawing: 300
// Balance after withdrawal: 1200

// Withdraw Error: Insufficient balance!
// Deposit Error: Deposit amount must be positive!

// Thread-4 withdrawing: 200
// Balance after withdrawal: 1000


// Custom Exception: Insufficient Balance
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

// Custom Exception: Invalid Amount
class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}

// Shared Resource: Bank Account
class BankAccount {
    private double balance;

    BankAccount(double balance) {
        this.balance = balance;
    }

    // Deposit Method
    public synchronized void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive!");
        }

        System.out.println(Thread.currentThread().getName() + " depositing: " + amount);
        balance += amount;
        System.out.println("Balance after deposit: " + balance);
    }

    // Withdraw Method
    public synchronized void withdraw(double amount)
            throws InsufficientBalanceException, InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive!");
        }

        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance!");
        }

        System.out.println(Thread.currentThread().getName() + " withdrawing: " + amount);
        balance -= amount;
        System.out.println("Balance after withdrawal: " + balance);
    }
}

// Deposit Thread
class DepositThread extends Thread {
    private BankAccount account;
    private double amount;

    DepositThread(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    public void run() {
        try {
            account.deposit(amount);
        } catch (InvalidAmountException e) {
            System.out.println("Deposit Error: " + e.getMessage());
        }
    }
}

// Withdraw Thread
class WithdrawThread extends Thread {
    private BankAccount account;
    private double amount;

    WithdrawThread(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    public void run() {
        try {
            account.withdraw(amount);
        } catch (InsufficientBalanceException | InvalidAmountException e) {
            System.out.println("Withdraw Error: " + e.getMessage());
        }
    }
}

// Main Class
public class BankingSystem {
    public static void main(String[] args) {

        BankAccount account = new BankAccount(1000); // Initial balance

        // Multiple transactions (threads)
        DepositThread d1 = new DepositThread(account, 500);
        WithdrawThread w1 = new WithdrawThread(account, 300);
        WithdrawThread w2 = new WithdrawThread(account, 1500); // will fail
        DepositThread d2 = new DepositThread(account, -100);   // invalid
        WithdrawThread w3 = new WithdrawThread(account, 200);

        d1.start();
        w1.start();
        w2.start();
        d2.start();
        w3.start();
    }
}