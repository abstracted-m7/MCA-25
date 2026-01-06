import java.util.Scanner;

class CalculatorOperations {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Error: Division by zero");
        }
        return (double) a / b;
    }

}


public class calculator {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        System.err.print("Enter the first number: ");
        int num1 = sc.nextInt();

        sc.nextLine();
        
        System.err.print("Enter the second number: ");
        int num2 = sc.nextInt();

        CalculatorOperations cal = new CalculatorOperations();
        int res = cal.add(num1, num2);
        System.err.println("The sum is : "+res);
    }
}
