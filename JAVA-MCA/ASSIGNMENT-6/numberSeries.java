import java.util.Scanner;
import java.util.Arrays;

class SeriesGenerator {

    public int[] generateNumber(int n) {
        int[] seriesArray = new int[n];

        for (int i = 0; i < n; i++) {
            seriesArray[i] = i*3;
        }

        return seriesArray;
    }
}

public class numberSeries {
  public static void main(String[] args) {

    SeriesGenerator obj = new SeriesGenerator();//object

    Scanner sc = new Scanner(System.in);

    System.out.print("Enter how many element you ant to generate: ");
    int num = sc.nextInt();
    sc.nextLine();

    int [] arr = obj.generateNumber(num);

    System.out.println(Arrays.toString(arr));
  }  
}
