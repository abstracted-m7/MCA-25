import java.util.*;

// Employee Class (Model)
class Employee {
    int id;
    String name;
    double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public void display() {
        System.out.println("ID: " + id +
                ", Name: " + name +
                ", Salary: " + salary);
    }
}

// Main Application
public class EmployeeDirectory {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Vector (Thread-safe)
        Vector<Employee> employees = new Vector<>();

        int choice;

        do {
            System.out.println("\n===== Employee Directory =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. Display All Employees");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    // Add employee
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();

                    employees.add(new Employee(id, name, salary));
                    System.out.println("Employee added successfully!");
                    break;

                case 2:
                    // Remove employee by ID
                    System.out.print("Enter Employee ID to remove: ");
                    int removeId = sc.nextInt();

                    boolean found = false;
                    for (int i = 0; i < employees.size(); i++) {
                        if (employees.get(i).id == removeId) {
                            employees.remove(i);
                            System.out.println("Employee removed!");
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Employee not found!");
                    }
                    break;

                case 3:
                    // Display all employees
                    if (employees.isEmpty()) {
                        System.out.println("No employees found!");
                    } else {
                        System.out.println("\n--- Employee List ---");
                        for (Employee e : employees) {
                            e.display();
                        }
                    }
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);

        sc.close();
    }
}