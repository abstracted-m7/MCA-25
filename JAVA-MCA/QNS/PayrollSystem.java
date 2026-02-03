
// Q2. Design a small class hierarchy for a "payroll system" that utilizas super to initializa parent class member.

class Employee{
    String ID, name;//glob var
    Employee(String ID, String name){
        this.ID = ID;
        this.name = name;
    }
}
class payment extends Employee{
    int salary;
    payment(String ID, String name, int salary){
        super(ID, name); //pass value to parent class
        this.salary = salary;
    }

    void display(){
        System.out.println("===EMP Details=====");
        System.out.println("Name: "+super.name);
        System.out.println("ID: "+super.ID);
        System.out.println("Salary: "+salary);
    }
}

public class PayrollSystem {
    public static void main(String[] args) {
        payment emp = new payment("A1","Manish",2000 );
        emp.display();
    }
}
