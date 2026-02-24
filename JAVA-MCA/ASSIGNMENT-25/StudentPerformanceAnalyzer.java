import java.util.Scanner;

public class StudentPerformanceAnalyzer {

    private static final int PASSING_MARK = 40;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nStudent's pass marks is 40.\n");
        System.out.print("Enter number of students: ");
        int numStudents = sc.nextInt();
        System.out.print("Enter number of subjects: ");
        int numSubjects = sc.nextInt();

        // 2D Array to store marks
        double[][] marksTable = new double[numStudents][numSubjects];

        // Modular Method Calls
        inputMarks(marksTable, sc);
        displayAnalysis(marksTable);
        
        sc.close();
    }

    /**
     * Handles data input for all students and subjects.
     */
    public static void inputMarks(double[][] marks, Scanner sc) {
        for (int i = 0; i < marks.length; i++) {
            System.out.println("Entering marks for Student " + (i + 1) + ":");
            for (int j = 0; j < marks[i].length; j++) {
                System.out.print("  Subject " + (j + 1) + ": ");
                marks[i][j] = sc.nextDouble();
            }
        }
    }

    /**
     * Computes and displays averages, toppers, and pass/fail counts.
     */
    public static void displayAnalysis(double[][] marks) {
        int numStudents = marks.length;
        int numSubjects = marks[0].length;

        System.out.println("\n--- Performance Report ---");

        // 1. Subject-wise Averages
        for (int j = 0; j < numSubjects; j++) {
            Double sum = 0.0; // Using Wrapper Class
            for (int i = 0; i < numStudents; i++) {
                sum += marks[i][j];
            }
            System.out.printf("Average for Subject %d: %.2f\n", (j + 1), (sum / numStudents));
        }

        // 2. Highest Scorer & Pass/Fail Count
        double highestTotal = -1;
        int topperIndex = 0;
        int passCount = 0;

        for (int i = 0; i < numStudents; i++) {
            double studentTotal = 0;
            boolean failedAny = false;

            for (int j = 0; j < numSubjects; j++) {
                studentTotal += marks[i][j];
                if (marks[i][j] < PASSING_MARK) failedAny = true;
            }

            if (studentTotal > highestTotal) {
                highestTotal = studentTotal;
                topperIndex = i;
            }

            if (!failedAny) passCount++;
        }

        System.out.println("--------------------------");
        System.out.println("Highest Scorer: Student " + (topperIndex + 1) + " with " + highestTotal + " marks.");
        System.out.println("Students Passed (All subjects): " + passCount);
        System.out.println("Students Failed (One or more subjects): " + (numStudents - passCount));
    }
}