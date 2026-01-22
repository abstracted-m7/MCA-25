

class Employee {

    final String companyName = "XYZ";

    // Method to be overridden
    void calculateSalary() {
        System.out.println("Salary Structure..!!");
    }

    // final method (cannot be overridden)
    final void showCompany() {
        System.out.println("Company: " + companyName);
    }
}


class Manager extends Employee {

    // Overriding method (dynamic dispatch)
    @Override
    void calculateSalary() {
        System.out.println("Manager Salary: Basic + Bonus");
    }
}

class Developer extends Employee {

    @Override
    void calculateSalary() {
        System.out.println("Developer Salary: Basic + Incentives");
    }
}

// Main class
public class DynamicDispatchDemo {

    public static void main(String[] args) {

        Employee emp;

        emp = new Employee(); //parent
        emp.calculateSalary();

        System.out.println();


        emp = new Manager(); //child
        emp.calculateSalary(); //Manager class called
        emp.showCompany();

        System.out.println();

        emp = new Developer();//child
        emp.calculateSalary(); //Developer class called
        emp.showCompany();
    }
}
