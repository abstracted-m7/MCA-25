// Q24. Design a java program to demonstrate partial abstraction using abstract classes.

// Abstract class
abstract class Shape {

    // Abstract method 
    abstract void draw();

    // Concrete method
    void message() {
        System.out.println("This is a shape.");
    }
}

// child class 1
class Circle extends Shape {

    @Override
    void draw() {
        System.out.println("Drawing a Circle");
    }
}

// child class 2
class Rectangle extends Shape {

    @Override
    void draw() {
        System.out.println("Drawing a Rectangle");
    }
}

public class PartialAbstractionDemo {
    public static void main(String[] args) {

        Shape s1 = new Circle();
        s1.draw();
        s1.message();

        Shape s2 = new Rectangle();
        s2.draw();
        s2.message();
    }
}