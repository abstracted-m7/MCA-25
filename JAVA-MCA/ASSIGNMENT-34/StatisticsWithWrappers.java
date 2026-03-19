import java.util.ArrayList;
import java.util.Scanner;

public class StatisticsWithWrappers {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.print("Enter how many numbers you want to input: ");
        int n = scanner.nextInt();

        // Input numbers (Autoboxing: int → Integer)
        for (int i = 0; i < n; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            int value = scanner.nextInt();
            numbers.add(value); // autoboxing happens here
        }

        // Initialize min and max
        Integer min = numbers.get(0);
        Integer max = numbers.get(0);

        int sum = 0;

        // Process values (Unboxing happens automatically)
        for (Integer num : numbers) {
            if (num < min) {   // unboxing Integer → int
                min = num;
            }
            if (num > max) {
                max = num;
            }
            sum += num; // unboxing for arithmetic
        }

        // Calculate average
        double average = (double) sum / numbers.size();

        // Display results
        System.out.println("\n--- Statistical Results ---");
        System.out.println("Minimum value: " + min);
        System.out.println("Maximum value: " + max);
        System.out.println("Average value: " + average);

        scanner.close();
    }
}