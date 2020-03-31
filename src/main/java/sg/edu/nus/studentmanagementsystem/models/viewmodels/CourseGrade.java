package sg.edu.nus.studentmanagementsystem.models.viewmodels;

public class CourseGrade {
    private String courseCode;
    private String courseName;
    private int courseCredits;
    private String courseGrade;

    public CourseGrade() {
    }

    public CourseGrade(String courseCode, String courseName, int courseCredits, String courseGrade) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseCredits = courseCredits;
        this.courseGrade = courseGrade;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseCredits() {
        return courseCredits;
    }

    public void setCourseCredits(int courseCredits) {
        this.courseCredits = courseCredits;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }
}