// Custom Exception: Invalid Marks
class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

// Shared Resource
class Student {
    int marks;
    char grade;
    boolean isMarksEntered = false;
    boolean isGradeCalculated = false;
}

// Thread 1: Input Marks
class InputThread extends Thread {
    Student student;

    InputThread(Student student) {
        this.student = student;
    }

    public void run() {
        try {
            System.out.println("Entering marks...");

            int inputMarks = 85; // simulate input (change to -10 for exception)

            if (inputMarks < 0 || inputMarks > 100) {
                throw new InvalidMarksException("Marks should be between 0 and 100!");
            }

            student.marks = inputMarks;
            student.isMarksEntered = true;

            System.out.println("Marks entered: " + student.marks);

        } catch (InvalidMarksException e) {
            System.out.println("Input Error: " + e.getMessage());
        }
    }
}

// Thread 2: Calculate Grade
class GradeThread extends Thread {
    Student student;

    GradeThread(Student student) {
        this.student = student;
    }

    public void run() {
        try {
            Thread.sleep(1000);

            if (!student.isMarksEntered) {
                throw new InvalidMarksException("Marks not entered yet!");
            }

            System.out.println("Calculating grade...");

            int marks = student.marks;

            if (marks >= 90) student.grade = 'A';
            else if (marks >= 75) student.grade = 'B';
            else if (marks >= 50) student.grade = 'C';
            else student.grade = 'F';

            student.isGradeCalculated = true;

            System.out.println("Grade calculated: " + student.grade);

        } catch (InvalidMarksException e) {
            System.out.println("Grade Error: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Thread 3: Generate Result Summary
class ResultThread extends Thread {
    Student student;

    ResultThread(Student student) {
        this.student = student;
    }

    public void run() {
        try {
            Thread.sleep(2000);

            if (!student.isGradeCalculated) {
                throw new InvalidMarksException("Grade not calculated yet!");
            }

            System.out.println("Generating result summary...");
            System.out.println("Marks: " + student.marks);
            System.out.println("Grade: " + student.grade);

        } catch (InvalidMarksException e) {
            System.out.println("Result Error: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Main Class
public class StudentResultProcessing {
    public static void main(String[] args) {

        Student student = new Student();

        InputThread t1 = new InputThread(student);
        GradeThread t2 = new GradeThread(student);
        ResultThread t3 = new ResultThread(student);

        t1.start();
        t2.start();
        t3.start();
    }
}