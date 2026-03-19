
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class DateTimeManager {

    public static void main(String[] args) {

        // 1. Display current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println("Current Date and Time: " + currentDateTime.format(formatter));

        // 2. Display current day of the week
        DayOfWeek day = currentDateTime.getDayOfWeek();
        System.out.println("Today is: " + day);

        // 3. Calculate days between two dates
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first date (yyyy-mm-dd): ");
        String date1Input = scanner.nextLine();

        System.out.print("Enter second date (yyyy-mm-dd): ");
        String date2Input = scanner.nextLine();

        // Convert string to LocalDate
        LocalDate date1 = LocalDate.parse(date1Input);
        LocalDate date2 = LocalDate.parse(date2Input);

        // Calculate difference
        long daysBetween = ChronoUnit.DAYS.between(date1, date2);

        System.out.println("Number of days between the two dates: " + Math.abs(daysBetween));

        scanner.close();
    }
}
