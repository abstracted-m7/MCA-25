/*
    1. create emp class with name, id, designation, salary
    2. take input empName, basic, hra, da, pf, tax
    3. calculate gross and net salary
*/

import java.util.Scanner;
class employee{
    //gross salary
    double grossSalary(double basic, double hra, double da){
        double gross = basic + hra + da;
        return gross;
    }
    //salary diduction
    double salaryDeduction(double pf, double tax){
        double deduction = pf + tax;
        return deduction;
    }
    //net salary
    double netSalary(double gross, double deduction){
        double net = gross - deduction;
        return net;
    }
}


public class empSalaryCal {
    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Employee Name: ");
        String empName = sc.nextLine();

        System.out.print("Enter Basic Salary: ");
        double basic = sc.nextDouble();

        System.out.print("Enter HRA: ");
        double hra = sc.nextDouble();

        System.out.print("Enter DA: ");
        double da = sc.nextDouble();

        System.out.print("Enter PF: ");
        double pf = sc.nextDouble();

        System.out.print("Enter Tax: ");
        double tax = sc.nextDouble();

        //create obj
        employee emp = new employee();

        double gross = emp.grossSalary(basic, hra, da);
        double deduction = emp.salaryDeduction(pf, tax);
        double net = emp.netSalary(gross, deduction);

        //print the info
        System.out.println("======Salary Info======");
        System.out.println("Employe name   : "+empName);
        System.out.println("Gross Salary   : "+gross);
        System.out.println("Diduction      : "+deduction);
        System.out.println("In hand salary : "+net);
    
        sc.close();
    }

}