
import java.util.Scanner;

class sumCalculator{

    int claculate(int limit){
        int sum = 0;
        for (int i = 1; i <= limit; i++) {
            sum += i;
        }
        return sum;
    }
}


public class SumOfNNatural {
    public static void main(String[] args) {
        int limit;
        Scanner sc = new Scanner(System.in);

        System.out.println("N natural number sum calculator");

        System.out.print("Enter the number (limit): ");
        limit = sc.nextInt();

        sumCalculator cal = new sumCalculator();
        int result = cal.claculate(limit);
        System.out.printf("Sum of %d natural number is : %d.",limit, result );
        sc.close();
    }
}

