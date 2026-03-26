package university.app;

import java.util.Scanner;
import university.model.Student;
import university.model.Course;
import university.service.UniversityService;
import university.service.UniversityServiceImpl;

public class UniversityManager {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UniversityService service = new UniversityServiceImpl();

        while (true) {

            System.out.println("\n====================================");
            System.out.println("University Resource Manager");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. View Students");
            System.out.println("4. View Courses");
            System.out.println("5. Search Student");
            System.out.println("6. Search Course");
            System.out.println("7. Exit");
            System.out.println("====================================\n");


            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();

                    service.addStudent(new Student(id, name, dept));
                    break;

                case 2:

                    System.out.print("Enter Course ID: ");
                    int cid = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Course Name: ");
                    String cname = sc.nextLine();

                    service.addCourse(new Course(cid, cname));
                    break;

                case 3:
                    service.viewStudents();
                    break;

                case 4:
                    service.viewCourses();
                    break;

                case 5:

                    System.out.print("Enter student name to search: ");
                    String sname = sc.nextLine();

                    service.searchStudentByName(sname);
                    break;

                case 6:

                    System.out.print("Enter course name to search: ");
                    String cname2 = sc.nextLine();

                    service.searchCourseByName(cname2);
                    break;

                case 7:
                    System.exit(0);

            }

        }

    }

}