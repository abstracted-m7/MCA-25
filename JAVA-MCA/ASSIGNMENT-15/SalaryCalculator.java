import java.util.Scanner;

// Base class
class Employee {
    int empId;
    String name;
    double basicSalary;

    void getDetails(Scanner sc) {
        System.out.print("Enter Employee ID: ");
        empId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Employee Name: ");
        name = sc.nextLine();

        System.out.print("Enter Basic Salary: ");
        basicSalary = sc.nextDouble();
    }

    double calculateSalary() {
        return basicSalary;
    }

    void display() {
        System.out.println("Employee ID   : " + empId);
        System.out.println("Employee Name : " + name);
    }
}

// Child class for Permanent Employee
class PermanentEmployee extends Employee {

    double bonus;

    void getBonus(Scanner sc) {
        System.out.print("Enter Bonus Amount: ");
        bonus = sc.nextDouble();
    }

    @Override
    double calculateSalary() {
        return basicSalary + bonus;
    }
}

// Child class for Contract Employee
class ContractEmployee extends Employee {

    int hoursWorked;
    double ratePerHour;

    void getContractDetails(Scanner sc) {
        System.out.print("Enter Hours Worked: ");
        hoursWorked = sc.nextInt();

        System.out.print("Enter Rate Per Hour: ");
        ratePerHour = sc.nextDouble();
    }

    @Override
    double calculateSalary() {
        return hoursWorked * ratePerHour;
    }
}

// Main class
public class SalaryCalculator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Select Employee Type:");
        System.out.println("1. Permanent Employee");
        System.out.println("2. Contract Employee");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        Employee emp;

        if (choice == 1) {
            PermanentEmployee pe = new PermanentEmployee();
            pe.getDetails(sc);
            pe.getBonus(sc);
            emp = pe;

        } else if (choice == 2) {
            ContractEmployee ce = new ContractEmployee();
            ce.getDetails(sc);
            ce.getContractDetails(sc);
            emp = ce;

        } else {
            System.out.println("Invalid choice!");
            sc.close();
            return;
        }

        System.out.println("\nEmployee Salary Details:");
        emp.display();
        System.out.println("Total Salary : " + emp.calculateSalary());

        sc.close();
    }
}
