class Student {

    int rollNo;
    String name;

    void displayDetails() {
        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
    }
}

// Main class
public class AccessMembersDemo {

    public static void main(String[] args) {

        Student s1 = new Student();

        //student manish
        s1.rollNo = 40;
        s1.name = "Manish";
        s1.displayDetails();

        //student suman
        s1.rollNo = 39;
        s1.name = "Suman";
        s1.displayDetails();
    }
}
