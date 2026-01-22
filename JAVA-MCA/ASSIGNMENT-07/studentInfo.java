
import java.util.Scanner;

class studentData {
    //name, age, course, and institution    
    private String name, course, institution;
    private int age;

    studentData(String name, String course, String institution, int age){
        this.name = name;
        this.course = course;
        this.institution = institution;
        this.age = age;
    }

    //ShowData
    void ShowData(){
        System.out.println("==============================");
        System.out.println("        STUDENT DETAILS       ");
        System.out.println("==============================");
        System.out.println("Name        :"+name);
        System.out.println("Course      :"+course);
        System.out.println("Institution :"+institution);
        System.out.println("Age:        :"+age);
        System.out.println("==============================");

    }
}


public class studentInfo{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String name, course, institution;
        int age;

        System.out.println("Enter Your Info: ");
        System.out.print("Name: ");
        name = sc.nextLine();

        System.out.print("Age: ");
        age = sc.nextInt();
        sc.nextLine();

        System.out.print("Course: ");
        course = sc.nextLine();

        System.out.print("Institution: ");
        institution = sc.nextLine();

        studentData stud = new studentData(name, course, institution, age);

        stud.ShowData();
    }
}