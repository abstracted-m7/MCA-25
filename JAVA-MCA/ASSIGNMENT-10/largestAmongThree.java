import java.util.Scanner;

public class largestAmongThree {
    int n1, n2, n3;

    Scanner sc = new Scanner(System.in);

    void numInput(){ //take input
        System.out.print("Enter First number (n1): ");
        n1 = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter second number (n2): ");
        n2 = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter third number (n3): ");
        n3 = sc.nextInt();
        sc.nextLine();
    }

    void maxFind(){ // find max among 3
        if (n1 > n2 && n1 > n3) {
            System.out.printf("The number(n1) = %d is maximum.",n1);
        }else if (n2 > n1 && n2 > n3) {
            System.out.printf("The number(n2) = %d is maximum.",n2);
        }else if (n3 > n1 && n3 > n2) {
            System.out.printf("The number(n3) = %d is maximum.",n3);
        }else{
            System.out.println("Enter valid integers..!!");
        }
    }

    public static void main(String[] args) {
        largestAmongThree finder = new largestAmongThree();
        finder.numInput();
        finder.maxFind();
        finder.sc.close();
    }
}