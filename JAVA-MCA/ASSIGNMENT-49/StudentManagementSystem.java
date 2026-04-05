import java.util.*;

// Student class (Model)
class Student {
    int rollNo;
    String name;
    double marks;

    public Student(int rollNo, String name, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    public void display() {
        System.out.println("Roll No: " + rollNo +
                ", Name: " + name +
                ", Marks: " + marks);
    }
}

// Main Application
public class StudentManagementSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        int choice;

        do {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Display All Students");
            System.out.println("4. Access Student by Index");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    // Add student
                    System.out.print("Enter Roll No: ");
                    int roll = sc.nextInt();
                    sc.nextLine(); // consume newline

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();

                    students.add(new Student(roll, name, marks));
                    System.out.println("Student added successfully!");
                    break;

                case 2:
                    // Delete student by roll number
                    System.out.print("Enter Roll No to delete: ");
                    int deleteRoll = sc.nextInt();

                    boolean found = false;
                    for (int i = 0; i < students.size(); i++) {
                        if (students.get(i).rollNo == deleteRoll) {
                            students.remove(i);
                            System.out.println("Student deleted!");
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Student not found!");
                    }
                    break;

                case 3:
                    // Display all students
                    if (students.isEmpty()) {
                        System.out.println("No records found!");
                    } else {
                        System.out.println("\n--- Student List ---");
                        for (Student s : students) {
                            s.display();
                        }
                    }
                    break;

                case 4:
                    // Access by index
                    System.out.print("Enter index: ");
                    int index = sc.nextInt();

                    if (index >= 0 && index < students.size()) {
                        students.get(index).display();
                    } else {
                        System.out.println("Invalid index!");
                    }
                    break;

                case 5:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        sc.close();
    }
}