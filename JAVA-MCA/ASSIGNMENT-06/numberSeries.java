import java.util.Scanner;
import java.util.Arrays;

class SeriesGenerator {

    //generate a series
    public int[] generateNumber(int n) {
        int[] seriesArray = new int[n];

        for (int i = 0; i < n; i++) {
            seriesArray[i] = i*3;
        }

        return seriesArray;
    }

    //sum & average of the array
    void calSumAvg(int[] arr){
        int sum =0 ;
        double avg = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        avg = sum / arr.length;

        System.out.println("The array is : "+Arrays.toString(arr));
        System.out.println("Sum of the array: "+sum);
        System.out.println("Average of the array: "+avg);
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
    obj.calSumAvg(arr);
    System.out.println(Arrays.toString(arr));
  }  
}
