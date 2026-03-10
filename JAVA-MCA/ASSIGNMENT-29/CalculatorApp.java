/*
    Q29. Develop a modular calculator system in Java using interface-based design principles. The application should define an interface that declares basic arithmetic operations and implement this interface through multiple concrete classes, each responsible for performing specific calculations. The program should provide a menu-driven interaction model that allows users to select operations dynamically, demonstrating polymorphism through interface references.

*/

import java.util.Scanner;

interface Operation {
    double calculate(double a, double b);
}

class Addition implements Operation {
    public double calculate(double a, double b) {
        return a + b;
    }
}

class Subtraction implements Operation{
    public double calculate(double a, double b){
        return a - b;
    }
}

class Multiplication implements Operation{
    public double calculate(double a, double b){
        return a * b;
    }
}

class Division implements Operation {
    public double calculate(double a, double b) {
        try {
            if(b == 0){
                throw new ArithmeticException();
            }
            return a / b;
        } 
        catch(ArithmeticException e){
            System.out.println("Error: Division by zero.");
            return 0;
        }
    }
}

class Modulo implements Operation{
    public double calculate(double a, double b){
        return a % b;
    }
}

public class CalculatorApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Operation op = null;
        int ch;
        double a,b;

        //menu
        System.out.println("\n---- Calculator Menu ----");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        System.out.println("5. Modulo");
        System.out.println("6. Exit");
        
        do {
            System.out.println("\n--------------------------------");
            System.out.print("Enter your choice: ");
            ch = sc.nextInt();

            sc.nextLine();



            //take input
            if (ch >= 1 && ch <= 5) {
                System.out.print("\nEnter first num: ");
                a = sc.nextDouble();

                System.out.print("Enter second num: ");
                b = sc.nextDouble();
            }else{
                a=b=0;
            }

            switch (ch) {
                case 1:
                    op = new Addition();
                    break;
                case 2:
                    op = new Subtraction();
                    break;
                case 3:
                    op =  new Multiplication();
                    break;
                case 4:
                    op = new Division();
                    break;
                case 5:
                    op = new Modulo();
                    break;
                case 6:
                    System.out.println("Exiting..!!");
                    sc.close();
                    break;
                default:
                    System.out.println("Invalid chice..!!");
                    break;
            }

            if (ch >= 1 && ch <= 5){
                double res = op.calculate(a, b);
                System.out.println("\nThe result: "+res);
            }

        } while (ch != 6);
        

        sc.close();
    }
}