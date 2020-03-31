package sg.edu.nus.studentmanagementsystem.models.viewmodels;

import java.util.List;

public class GradeBook {
    private String major;
    private int gradedCredits;
    private double cGPA;
    private String degreeClass;

    List<SemesterGrade> semesterGrades;

    public GradeBook() {
    }

    public GradeBook(String major, int gradedCredits, double cGPA, String degreeClass,
            List<SemesterGrade> semesterGrades) {
        this.major = major;
        this.gradedCredits = gradedCredits;
        this.cGPA = cGPA;
        this.degreeClass = degreeClass;
        this.semesterGrades = semesterGrades;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getGradedCredits() {
        return gradedCredits;
    }

    public void setGradedCredits(int gradedCredits) {
        this.gradedCredits = gradedCredits;
    }

    public double getCgpa() {
        return cGPA;
    }

    public void setCgpa(double cGPA) {
        this.cGPA = cGPA;
    }

    public String getDegreeClass() {
        return degreeClass;
    }

    public void setDegreeClass(String degreeClass) {
        this.degreeClass = degreeClass;
    }

    public List<SemesterGrade> getSemesterGrades() {
        return semesterGrades;
    }

    public void setSemesterGrades(List<SemesterGrade> semesterGrades) {
        this.semesterGrades = semesterGrades;
    }
}