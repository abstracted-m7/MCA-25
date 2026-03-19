import java.util.Scanner;
import java.util.Vector;

// Student class
class Student {
    Integer rollNo;   // Wrapper class
    String name;

    Student(Integer rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }

    void display() {
        System.out.println("Roll No: " + rollNo + ", Name: " + name);
    }
}

// Main class
public class StudentRegistry {

    public static void main(String[] args) {

        Vector<Student> students = new Vector<>();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Student Registry Menu ---");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {

                case 1: // Add student
                    System.out.print("Enter Roll Number: ");
                    Integer roll = scanner.nextInt(); // autoboxing
                    scanner.nextLine(); // consume newline

                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();

                    students.add(new Student(roll, name));
                    System.out.println("Student added successfully!");
                    break;

                case 2: // Remove student
                    System.out.print("Enter Roll Number to remove: ");
                    int removeRoll = scanner.nextInt();

                    boolean removed = false;
                    for (Student s : students) {
                        if (s.rollNo.equals(removeRoll)) {
                            students.remove(s);
                            removed = true;
                            System.out.println("Student removed successfully!");
                            break;
                        }
                    }

                    if (!removed) {
                        System.out.println("Student not found!");
                    }
                    break;

                case 3: // Search student
                    System.out.print("Enter Roll Number to search: ");
                    int searchRoll = scanner.nextInt();

                    boolean found = false;
                    for (Student s : students) {
                        if (s.rollNo.equals(searchRoll)) {
                            System.out.println("Student found:");
                            s.display();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Student not found!");
                    }
                    break;

                case 4: // Display all
                    if (students.isEmpty()) {
                        System.out.println("No records available.");
                    } else {
                        System.out.println("\n--- Student List ---");
                        for (Student s : students) {
                            s.display();
                        }
                    }
                    break;

                case 5:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 5);

        scanner.close();
    }
}