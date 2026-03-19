import java.util.Scanner;

public class DataConversionUtility {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // 1. String to Integer
        System.out.print("Enter a numeric string: ");
        String strNumber = scanner.nextLine();

        int intValue = Integer.parseInt(strNumber);  // parsing
        System.out.println("String to Integer: " + intValue);

        // 2. Integer to Double and Float
        double doubleValue = Double.valueOf(intValue); // boxing + widening
        float floatValue = Float.valueOf(intValue);

        System.out.println("Integer to Double: " + doubleValue);
        System.out.println("Integer to Float: " + floatValue);

        // 3. Integer to String
        String intToString = Integer.toString(intValue);
        System.out.println("Integer to String: " + intToString);

        // 4. Double to String
        String doubleToString = Double.toString(doubleValue);
        System.out.println("Double to String: " + doubleToString);

        // 5. String to Double
        System.out.print("Enter a decimal number: ");
        String strDecimal = scanner.nextLine();

        double parsedDouble = Double.parseDouble(strDecimal);
        System.out.println("String to Double: " + parsedDouble);

        // 6. Wrapper to Primitive
        Integer wrappedInt = Integer.valueOf(intValue); // boxing
        int primitiveInt = wrappedInt.intValue();       // unboxing

        System.out.println("Wrapper Integer: " + wrappedInt);
        System.out.println("Unboxed primitive int: " + primitiveInt);

        scanner.close();
    }
}