class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    String add(String a, String b) {
        return a + b;
    }
}

public class MethodOverloadingDemo {

    public static void main(String[] args) {

        Calculator calc = new Calculator();

        System.out.println("Sum of integers: " + calc.add(10, 20));
        System.out.println("Sum of doubles : " + calc.add(10.5, 20.3));
        System.out.println("Concatenation  : " + calc.add("Manish ", "Giri"));
    }
}
