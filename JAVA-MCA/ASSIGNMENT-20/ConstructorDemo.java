class Student {

    int rollNo;
    String name;
    double marks;

    Student(int r, String n, double m) {
        rollNo = r;
        name = n;
        marks = m;
    }

    String calculateGrade() {
        if (marks >= 80)
            return "A";
        else if (marks >= 60)
            return "B";
        else if (marks >= 40)
            return "C";
        else
            return "Fail";
    }

    void displayDetails() {
        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
        System.out.println("Marks   : " + marks);
        System.out.println("Grade   : " + calculateGrade());
    }
}

public class ConstructorDemo {

    public static void main(String[] args) {

        Student s1 = new Student(40, "Manish", 92);

        s1.displayDetails();
    }
}
