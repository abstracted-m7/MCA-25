import java.util.*;

// Student Class
class Student {
    int id;
    String name;
    String course;

    public Student(int id, String name, String course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }

    public void display() {
        System.out.println("ID: " + id +
                ", Name: " + name +
                ", Course: " + course);
    }
}

// Main Class
public class CourseEnrollmentSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Polymorphism: List reference, ArrayList object
        List<Student> students = new ArrayList<>();

        int choice;

        do {
            System.out.println("\n===== Course Enrollment System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Student at Position");
            System.out.println("3. Update Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    // Add normally
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Course: ");
                    String course = sc.nextLine();

                    students.add(new Student(id, name, course));
                    System.out.println("Student added!");
                    break;

                case 2:
                    // Add at specific position
                    System.out.print("Enter index: ");
                    int index = sc.nextInt();

                    if (index >= 0 && index <= students.size()) {
                        System.out.print("Enter ID: ");
                        int id2 = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Name: ");
                        String name2 = sc.nextLine();

                        System.out.print("Enter Course: ");
                        String course2 = sc.nextLine();

                        students.add(index, new Student(id2, name2, course2));
                        System.out.println("Student added at position " + index);
                    } else {
                        System.out.println("Invalid index!");
                    }
                    break;

                case 3:
                    // Update student
                    System.out.print("Enter index to update: ");
                    int updateIndex = sc.nextInt();

                    if (updateIndex >= 0 && updateIndex < students.size()) {
                        System.out.print("Enter new name: ");
                        sc.nextLine();
                        String newName = sc.nextLine();

                        System.out.print("Enter new course: ");
                        String newCourse = sc.nextLine();

                        Student s = students.get(updateIndex);
                        s.name = newName;
                        s.course = newCourse;

                        System.out.println("Student updated!");
                    } else {
                        System.out.println("Invalid index!");
                    }
                    break;

                case 4:
                    // Display using iteration
                    if (students.isEmpty()) {
                        System.out.println("No students enrolled!");
                    } else {
                        System.out.println("\n--- Enrollment Order ---");

                        // Using Iterator
                        Iterator<Student> it = students.iterator();
                        while (it.hasNext()) {
                            it.next().display();
                        }
                    }
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        sc.close();
    }
}