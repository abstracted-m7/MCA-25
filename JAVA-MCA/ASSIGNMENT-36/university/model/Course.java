package university.model;

public class Course {

    private int courseId;
    private String courseName;

    public Course(int courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void display() {
        System.out.println("Course ID: " + courseId + "\n Course Name: " + courseName);
    }
}