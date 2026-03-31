import java.util.Scanner;

class InvalidNumberException extends Exception {
    public InvalidNumberException(String message) {
        super(message);
    }
}
// number generator
class NumberGenerator extends Thread {
    int number;
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a positive number: ");
        number = sc.nextInt();
        try {
            if (number < 0) {
                throw new InvalidNumberException("Negative numbers are not allowed.");
            }
            System.out.println("You entered: " + number);
        } catch (InvalidNumberException e) {
            System.out.println(e.getMessage());
            number = -1; // Set to -1 to indicate an error
        } finally {
            sc.close();
        }
    }
}

// prime checker
class PrimeChecker extends Thread {
    int number;

    public PrimeChecker(int number) {
        this.number = number;
    }

    public void run() {
        boolean isPrime = true;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                isPrime = false;
                break;
            }
        }
        System.out.println("[Prime Checker] " + number + (isPrime ? " is a prime number." : " is not a prime number."));
    }
}
// factorial calculator
class FactorialCalculator extends Thread {
    int number;
    public FactorialCalculator(int number) {
        this.number = number;
    }
    public void run() {
        long factorial = 1;
        for (int i = 1; i <= number; i++) {
            factorial *= i;
        }
        System.out.println("[Factorial] " + number + "! = " + factorial);
    }
}
// main class
public class MultiThread {

    public static void main(String[] args) {
        try {
            NumberGenerator t1 =  new NumberGenerator();
            t1.start();
            t1.join();
            if(t1.number <= 0) return;

            PrimeChecker t2 = new PrimeChecker(t1.number);
            FactorialCalculator t3 =  new FactorialCalculator(t1.number);

            t2.start();
            t3.start();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted: " + e.getMessage());
        }
    }
}