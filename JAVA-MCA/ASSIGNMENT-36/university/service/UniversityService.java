package university.service;

import university.model.Student;
import university.model.Course;

public interface UniversityService {

    void addStudent(Student student);

    void addCourse(Course course);

    void viewStudents();

    void viewCourses();

    void searchStudentByName(String name);

    void searchCourseByName(String name);
}