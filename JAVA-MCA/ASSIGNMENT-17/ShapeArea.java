import java.util.Scanner;

// Abstract base class
abstract class Shape {

    // Abstract method
    abstract double calculateArea();

    // Common method
    void displayArea(double area) {
        System.out.println("Area = " + area);
    }
}

// Rectangle class
class Rectangle extends Shape {

    double length, breadth;

    Rectangle(double l, double b) {
        length = l;
        breadth = b;
    }

    @Override
    double calculateArea() {
        return length * breadth;
    }
}

// Circle class
class Circle extends Shape {

    double radius;

    Circle(double r) {
        radius = r;
    }

    @Override
    double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// Triangle class
class Triangle extends Shape {

    double base, height;

    Triangle(double b, double h) {
        base = b;
        height = h;
    }

    @Override
    double calculateArea() {
        return 0.5 * base * height;
    }
}

// Main class
public class ShapeArea {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Choose Shape:");
        System.out.println("1. Rectangle");
        System.out.println("2. Circle");
        System.out.println("3. Triangle");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        Shape shape; //abstract class object

        switch (choice) {

            case 1:
                System.out.print("Enter length: ");
                double l = sc.nextDouble();
                System.out.print("Enter breadth: ");
                double b = sc.nextDouble();
                shape = new Rectangle(l, b);
                break;

            case 2:
                System.out.print("Enter radius: ");
                double r = sc.nextDouble();
                shape = new Circle(r);
                break;

            case 3:
                System.out.print("Enter base: ");
                double base = sc.nextDouble();
                System.out.print("Enter height: ");
                double height = sc.nextDouble();
                shape = new Triangle(base, height);
                break;

            default:
                System.out.println("Invalid choice!");
                sc.close();
                return;
        }

        double area = shape.calculateArea();
        shape.displayArea(area);

        sc.close();
    }
}
