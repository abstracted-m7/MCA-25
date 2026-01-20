
import java.util.Scanner;

class TemperatureConverter {

    public double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }
}


public class temparatureConvertion {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter temperature in Celsius: ");
        double celsius = sc.nextDouble();

        TemperatureConverter converter = new TemperatureConverter();

        double fahrenheit = converter.celsiusToFahrenheit(celsius);

        System.out.println("Temperature in Fahrenheit: " + fahrenheit);

        sc.close();
    }
}