package university.service;

import java.util.ArrayList;
import university.model.Student;
import university.model.Course;

public class UniversityServiceImpl implements UniversityService {

    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Course> courses = new ArrayList<>();

    @Override
    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Student Added Successfully");
    }

    @Override
    public void addCourse(Course course) {
        courses.add(course);
        System.out.println("Course Added Successfully");
    }

    @Override
    public void viewStudents() {
        for (Student s : students) {
            s.display();
        }
    }

    @Override
    public void viewCourses() {
        for (Course c : courses) {
            c.display();
        }
    }

    @Override
    public void searchStudentByName(String name) {

        for (Student s : students) {

            if (s.getName().toLowerCase().contains(name.toLowerCase())) {
                s.display();
            }

        }
    }

    @Override
    public void searchCourseByName(String name) {

        for (Course c : courses) {

            if (c.getCourseName().toLowerCase().contains(name.toLowerCase())) {
                c.display();
            }

        }
    }
}