/*
    1. Input student name, roll, course, samester, marks
    2. Use gatter & setter method
        2.1. set student info ultile user wants to stop
    3. display all info about student
    4. STOP
*/

import java.util.Scanner;

class studentInfo{
    String name, roll, course, samester;
    int marks;
    void set(String name, String roll, String course, String samester, int marks ){
        this.name = name;
        this.roll = roll;
        this.course = course;
        this.samester = samester;
        this.marks = marks;
    }

    //get methods
    void get(){
        System.out.println("======== Student Info ========");
        System.out.println("Name: "+name);
        System.out.println("Roll: "+roll);
        System.out.println("Course: "+course);
        System.out.println("Samester: "+samester);
        System.out.println("Marks: "+marks);
        
    }
}

public class student {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.println("Fill student details: ");

        //Name
        System.out.print("Enetr student name: ");
        String name = sc.nextLine();

        //
        System.out.print("Roll: ");
        String roll = sc.nextLine();

        System.out.print("Course: ");
        String course = sc.nextLine();

        System.out.print("Samester: ");
        String samester = sc.nextLine();

        System.out.print("Marks: ");
        int marks = sc.nextInt();

        //obj through pass value
        studentInfo stud = new studentInfo();
        stud.set(name, roll, course, samester, marks);
        stud.get();
        sc.close();
    }
}
