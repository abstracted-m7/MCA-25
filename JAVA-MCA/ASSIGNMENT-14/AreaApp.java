import java.util.Scanner;

class AreaCalculator {

    // Area of Square
    double area(double side) {
        return side * side;
    }

    // Area of Rectangle
    double area(double length, double breadth) {
        return length * breadth;
    }

    // Area of Circle
    double area(int radius) {
        return 3.14159 * radius * radius;
    }

    // Area of Triangle
    double area(double base, double height, boolean isTriangle) {
        return 0.5 * base * height;
    }
}


public class AreaApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AreaCalculator calc = new AreaCalculator();

        System.out.println("Choose Shape:");
        System.out.println("1. Square");
        System.out.println("2. Rectangle");
        System.out.println("3. Circle");
        System.out.println("4. Triangle");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        switch (choice) {

            case 1:
                System.out.print("Enter side: ");
                double side = sc.nextDouble();
                System.out.println("Area of Square: " + calc.area(side));
                break;

            case 2:
                System.out.print("Enter length: ");
                double length = sc.nextDouble();
                System.out.print("Enter breadth: ");
                double breadth = sc.nextDouble();
                System.out.println("Area of Rectangle: " + calc.area(length, breadth));
                break;

            case 3:
                System.out.print("Enter radius: ");
                int radius = sc.nextInt();
                System.out.println("Area of Circle: " + calc.area(radius));
                break;

            case 4:
                System.out.print("Enter base: ");
                double base = sc.nextDouble();
                System.out.print("Enter height: ");
                double height = sc.nextDouble();
                System.out.println("Area of Triangle: " + calc.area(base, height, true));
                break;

            default:
                System.out.println("Invalid choice!");
        }

        sc.close();
    }
}
