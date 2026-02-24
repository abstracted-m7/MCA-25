//Q21. Create a Java program to demonstrate method overloading using different data types.

class MethodOverloadingExample {

    void display(int num) {
        System.out.println("Integer value: " + num);
    }

    void display(double num) {
        System.out.println("Double value: " + num);
    }

    void display(String text) {
        System.out.println("String value: " + text);
    }

    public static void main(String[] args) {
        MethodOverloadingExample obj = new MethodOverloadingExample();

        obj.display(10); 
        obj.display(5.5);
        obj.display("Hello");
    }
}