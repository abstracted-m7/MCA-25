
// In class this code we write...hehehee


import java.util.Scanner;

class Employee {
    int empId;
    String name;

    Employee(int empId, String name) {
        this.empId = empId;
        this.name = name;
    }

    double calculateSalary() {
        return 0;
    }

    void display(double salary) {
        System.out.println("\n--- Employee Details ---");
        System.out.println("Employee Name: " + name);
        System.out.println("Employee Id: " + empId);
        System.out.println("Total Salary: " + salary);
    }
}

class PermanentEmployee extends Employee {
    double basicPay, hra, da;

    PermanentEmployee(int empId, String name, double basicPay) {
        super(empId, name);
        this.basicPay = basicPay;
        this.hra = 0.20 * basicPay;
        this.da = 0.10 * basicPay;
    }

    @Override
    double calculateSalary() {
        return (basicPay + hra + da);
    }
}

class ContractorEmployee extends Employee { 
    int hr;
    double hrRate;

    ContractorEmployee(int empId, String name, int hr, double hrRate) {
        super(empId, name);
        this.hr = hr;
        this.hrRate = hrRate;
    }

    @Override
    double calculateSalary() {
        return (hr * hrRate);
    }
}

public class EmployeInhertance {
    public static void main(String[] args) {
        int id, choice;
        String name;

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter employee details: ");
        System.out.print("Enter employee id: ");
        id = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.print("Enter employee name: ");
        name = sc.nextLine();

        // Reference of parent class for Dynamic Method Dispatch
        Employee emp = null; 

        System.out.println("\n=== Menu ===");
        System.out.println("1. Permanent Employee");
        System.out.println("2. Contractor Employee");
        System.out.println("============");
        System.out.print("Enter employee type: ");
        choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter your basic pay: ");
                double basicPay = sc.nextDouble();
                emp = new PermanentEmployee(id, name, basicPay);
                break;

            case 2:
                System.out.print("Enter hours worked: ");
                int hours = sc.nextInt();
                System.out.print("Enter hourly rate: ");
                double rate = sc.nextDouble();
                emp = new ContractorEmployee(id, name, hours, rate);
                break;

            default:
                System.out.println("Invalid choice!");
                break;
        }

        if (emp != null) {
            double salary = emp.calculateSalary(); 
            emp.display(salary);
        }

        sc.close();
    }
}