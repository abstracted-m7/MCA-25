

class Student {

    int rollNo;
    String name,course;
    double marks;

    void setDetails(int r, String n, String c, double m) {
        rollNo = r;
        name = n;
        course = c;
        marks = m;
    }

    void displayDetails() {
        System.out.println("=========================");
        System.out.println("Student Achademic Details");
        System.out.println("=========================");
        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
        System.out.println("Course  : " + course);
        System.out.println("Marks   : " + marks);
        System.out.println("=========================");

    }
}



public class StudentInfo {

    public static void main(String[] args) {

        java.util.Scanner sc = new java.util.Scanner(System.in);

        Student s1 = new Student();

        System.out.print("Enter Roll Number: ");
        int roll = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        // Store data
        s1.setDetails(roll, name, course, marks);

        // Display data
        s1.displayDetails();

        sc.close();
    }
}
