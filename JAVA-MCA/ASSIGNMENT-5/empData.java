
// employee id, employee name, designation, company name, and salary


import java.util.Scanner;
import java.util.InputMismatchException;

class empDetails {
    private int employeeId;
    private String employeeName;
    private String designation;
    private String companyName;
    private double salary;

    public void getData() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("--- Employee Data Entry ---");
            
            System.out.print("Enter Employee ID (Integer): ");
            employeeId = sc.nextInt();
            sc.nextLine(); 

            System.out.print("Enter Employee Name: ");
            employeeName = sc.nextLine();

            System.out.print("Enter Designation: ");
            designation = sc.nextLine();

            System.out.print("Enter Company Name: ");
            companyName = sc.nextLine();

            System.out.print("Enter Salary (Decimal/Number): ");
            salary = sc.nextDouble();

        } catch (InputMismatchException e) {
            System.out.println("\n[ERROR]: Invalid input. Please enter numeric values for ID and Salary.");
            System.exit(0); 
        }
    }

    public void showData() {
        System.out.println("\n==========================================");
        System.out.println("            EMPLOYEE RECORD               ");
        System.out.println("==========================================");
        System.out.println("ID            :"+ employeeId);
        System.out.println("Name          :"+ employeeName);
        System.out.println("Designation   :"+ designation);
        System.out.println("Company       :"+ companyName);
        System.out.println("Salary        :"+ salary);
        System.out.println("==========================================\n");
    }
}

public class empData {
    public static void main(String[] args) {

        empDetails emp = new empDetails();
        
        emp.getData();
        emp.showData();
    }
}