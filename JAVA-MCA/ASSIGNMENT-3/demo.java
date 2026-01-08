import java.util.Scanner;

class CalculatorOperations {

    // Add
    double add(int a, int b) {
        return a + b;
    }

    // Sub
    double sub(int a, int b) {
        return a - b;
    }

    // Multi
    double multi(int a, int b) {
        return (double) a * b;
    }

    // Divi - Cast to double to avoid integer division truncation
    double divi(int a, int b) {
        if (b == 0) {
            System.out.println("Error: Division by zero!");
            return 0;
        }
        return (double) a / b;
    }

    // Power
    double power(int a, int b) {
        return Math.pow(a, b);
    }

    // Modulo
    double modulo(int a, int b) {
        return a % b;
    }
}

public class demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CalculatorOperations cal = new CalculatorOperations(); // Create object once
        int choice, num1, num2;
        double res;


        do {
            // Menu
            System.out.println("\n==== MENU ====");
            System.out.println("1. Sum\n2. Sub\n3. Multiplication\n4. Division\n5. Power\n6. Modulo\n0. Exit");
            System.out.println("========================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            if (choice == 0) {
                System.out.println("Thank You. Visit again..!!");
                break;                
            }

            System.out.print("Enter number 1: ");
            num1 = sc.nextInt();
            System.out.print("Enter number 2: ");
            num2 = sc.nextInt();


            switch (choice) {
                case 1:
                    res = cal.add(num1, num2);
                    System.out.println("Result (Addition): " + res);
                    break;
                case 2:
                    res = cal.sub(num1, num2);
                    System.out.println("Result (Subtraction): " + res);
                    break;
                case 3:
                    res = cal.multi(num1, num2);
                    System.out.println("Result (Multiplication): " + res);
                    break;
                case 4:
                    res = cal.divi(num1, num2);
                    System.out.println("Result (Division): " + res);
                    break;
                case 5:
                    res = cal.power(num1, num2);
                    System.out.println("Result (Power): " + res);
                    break;
                case 6:
                    res = cal.modulo(num1, num2);
                    System.out.println("Result (Modulo): " + res);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        } while (choice != 0);

        sc.close();
    }
}