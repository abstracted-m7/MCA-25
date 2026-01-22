
import java.util.Scanner;

class NumVerify {
    private int num;

    //constructor
    NumVerify(int num) {
        this.num = num;
    }

    // number check positive, negative, or zero
    void numCheck() {
        if (num == 0) {
            System.out.println("Number is Zero");
        } else if (num > 0) {
            System.out.println("Number is Positive");
        } else {
            System.out.println("Number is Negative");
        }
    }

    //check odd even
    void checkOddEven(){
        if (num % 2 == 0) {
            System.out.printf("The %d is EVEN",num);
        }else{
            System.out.printf("The %d is ODD",num);
        }
    }

    //check prime or not
    void primeCheck(){
        if (num <= 1) {
            System.out.printf("\nThe number %d is not prime.",num);
            return;
        }
        boolean isPrime = true;
        for (int i = 2; i < (num/2); i++) {
            if (num % i == 0) {
                isPrime = false;
                break;
            }
        }
        if (isPrime) {
            System.out.printf("\nThe number %d is prime number.",num);
        }else{
            System.out.printf("\nThe number %d is not prime.",num);

        }
    }
    //check palindrom
    void checkPalindrom(){
        int temp = num, reverse = 0;

        if (num < 0) {
            System.out.printf("\nThe number %d is not palindrome.",num);
            return;
        }

        while (temp > 0) {
            int rem = temp % 10;
            reverse = reverse*10 + rem;
            temp /= 10;
        }

        if (num == reverse) {
            System.out.printf("\nThe number %d is palindrome.",num);
        }else{
            System.out.printf("\nThe number %d is not palindrome.",num);
        }
    }


    //check factorial
    void checkFact(){
        long fact = 1;
        if (num == 0 && num == 1) {
            System.out.printf("\nFactorial of %d is 1.",num);
        }
        for (int i = 2; i <= num; i++) {
            fact *= i;
        }
        System.out.printf("\nFactorial of %d is : %d",num,fact);
    }
}

public class numberCheck {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter an integer number: ");
            int num = sc.nextInt();

            NumVerify obj = new NumVerify(num);//object create

            obj.numCheck(); //p_n_z check
            obj.checkOddEven(); //odd_even check
            obj.primeCheck(); //prime check
            obj.checkPalindrom(); //palindrome check
            obj.checkFact(); //factorial check


        } catch (Exception e) {
            System.out.println("Error: Enter a valid integer number.");
        } finally {
            sc.close();
        }
    }
}
