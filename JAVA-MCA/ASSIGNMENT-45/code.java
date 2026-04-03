import java.util.Scanner;

// Custom Exception
class ExamTimeoutException extends Exception {
    public ExamTimeoutException(String message) {
        super(message);
    }
}

// Shared Exam Class
class Exam {
    private boolean isSubmitted = false;

    // Submit method (only once)
    public synchronized void submit() {
        if (!isSubmitted) {
            isSubmitted = true;
            System.out.println("\n Exam Submitted Successfully!");
        }
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }
}

// Timer Thread (No continuous printing → clean input)
class TimerThread extends Thread {
    private int time; // seconds
    private Exam exam;

    public TimerThread(int time, Exam exam) {
        this.time = time;
        this.exam = exam;
    }

    public void run() {
        try {
            // Wait for given time
            Thread.sleep(time * 1000);

            // If not submitted → timeout
            if (!exam.isSubmitted()) {
                throw new ExamTimeoutException(" Time is up! Auto-submitting...");
            }

        } catch (ExamTimeoutException e) {
            System.out.println("\n" + e.getMessage());
            exam.submit();

        } catch (InterruptedException e) {
            System.out.println("Timer interrupted.");
        }
    }
}

// Student Thread
class StudentThread extends Thread {
    private Exam exam;
    private Scanner sc;

    public StudentThread(Exam exam, Scanner sc) {
        this.exam = exam;
        this.sc = sc;
    }

    public void run() {
        try {
            System.out.println("\n Answer the questions:");

            System.out.print("Q1: 2 + 2 = ");
            String ans1 = sc.nextLine();

            if (exam.isSubmitted()) return;

            System.out.print("Q2: Capital of India = ");
            String ans2 = sc.nextLine();

            if (exam.isSubmitted()) return;

            // Manual submit
            exam.submit();

        } catch (Exception e) {
            System.out.println("Error while answering.");
        }
    }
}

// Main Class
public class code {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Exam exam = new Exam();

        // Create threads
        TimerThread timer = new TimerThread(5, exam); // 20 seconds
        StudentThread student = new StudentThread(exam, sc);

        // Start threads
        timer.start();
        student.start();

        try {
            student.join(); // wait for student
            timer.join();   // wait for timer
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        sc.close();
        System.out.println("\n Exam Ended.");
    }
}