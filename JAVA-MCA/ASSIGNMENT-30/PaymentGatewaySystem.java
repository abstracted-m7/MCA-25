import java.util.Scanner;

interface Payment {
    double MAX_LIMIT = 100; //changes
    boolean processPayment(double amount);
}

interface Refund {
    void processRefund(double amount);
}

class CardPayment implements Payment, Refund {

    public boolean processPayment(double amount) {
        if (amount > MAX_LIMIT) {
            System.out.println("Payment exceeds maximum limit.");
            return false;
        }
        System.out.println("Card payment of rs." + amount + " processed successfully.");
        return true;
    }

    public void processRefund(double amount) {
        System.out.println("Refund of rs." + amount + " issued to Card.");
    }
}

class WalletPayment implements Payment, Refund {

    public boolean processPayment(double amount) {
        if (amount > MAX_LIMIT) {
            System.out.println("Payment exceeds wallet limit.");
            return false; //chnages
        } else {
            System.out.println("Wallet payment of rs." + amount + " completed.");
            return true;
        }
    }

    public void processRefund(double amount) {
        System.out.println("Refund of rs." + amount + " credited to Wallet.");
    }
}

class UPIPayment implements Payment, Refund {

    public boolean processPayment(double amount) {
        if (amount > MAX_LIMIT) {
            System.out.println("UPI limit exceeded.");
            return false; //chnages
        } else {
            System.out.println("UPI payment of rs." + amount + " successful.");
            return true;
        }
    }

    public void processRefund(double amount) {
        System.out.println("Refund of rs." + amount + " sent via UPI.");
    }
}

public class PaymentGatewaySystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Payment p = null;
        Refund r = null;

        System.out.println("Select Payment Mode");
        System.out.println("1. Card Payment");
        System.out.println("2. Wallet Payment");
        System.out.println("3. UPI Payment");
        
        System.out.print("\nEnter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        switch (choice) {
            case 1:
                CardPayment card = new CardPayment();
                p = card;
                r = card;
                break;

            case 2:
                WalletPayment wallet = new WalletPayment();
                p = wallet;
                r = wallet;
                break;

            case 3:
                UPIPayment upi = new UPIPayment();
                p = upi;
                r = upi;
                break;

            default:
                System.out.println("Invalid choice");
                sc.close();
                return;
        }

        boolean suc = p.processPayment(amount);
        if(!suc){
            sc.close();
            return;
        }
        
        System.out.print("Do you want refund? (1-Yes / 0-No): ");
        int rf = sc.nextInt();

        if (rf == 1) {
            r.processRefund(amount);
            sc.close(); //chnages
            return;
        }else{
            System.out.println("Money debited from wallet..!!");
        }

        sc.close();
    }
}