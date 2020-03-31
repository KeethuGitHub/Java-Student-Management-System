package sg.edu.nus.studentmanagementsystem.models.viewmodels;

import java.util.List;

public class SemesterGrade {
    private String semesterName;
    private double semesterGPA;

    List<CourseGrade> courseGrades;

    public SemesterGrade() {
    }

    public SemesterGrade(String semesterName, double semesterGPA, List<CourseGrade> courseGrades) {
        this.semesterName = semesterName;
        this.semesterGPA = semesterGPA;
        this.courseGrades = courseGrades;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public double getSemesterGPA() {
        return semesterGPA;
    }

    public void setSemesterGPA(double semesterGPA) {
        this.semesterGPA = semesterGPA;
    }

    public List<CourseGrade> getCourseGrades() {
        return courseGrades;
    }

    public void setCourseGrades(List<CourseGrade> courseGrades) {
        this.courseGrades = courseGrades;
    }
}