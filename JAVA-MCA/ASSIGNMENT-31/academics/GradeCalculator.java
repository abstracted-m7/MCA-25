
package academics;

public class GradeCalculator {

    public String calculateGrade(int marks) {
        if (marks >= 90)
            return "A+";
        else if (marks >= 75)
            return "A";
        else if (marks >= 60)
            return "B";
        else if (marks >= 50)
            return "C";
        else
            return "Fail";
    }
}