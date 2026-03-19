import academics.Student;
import academics.GradeCalculator;

public class MainApp {
    public static void main(String[] args) {

        // Create Student object
        Student s1 = new Student("XYZ", 101, 82);

        // Display student info
        s1.displayInfo();

        // Calculate grade
        GradeCalculator gc = new GradeCalculator();
        String grade = gc.calculateGrade(s1.getMarks());

        System.out.println("Grade: " + grade);
    }
}
