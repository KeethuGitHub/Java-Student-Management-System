package sg.edu.nus.studentmanagementsystem.models.entitymodels;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String enrollmentStatus;
    private String grade = "";

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Enrollment() {
    }

    public Enrollment(int id, String enrollmentStatus, String grade, Student student, Course course) {
        this.id = id;
        this.enrollmentStatus = enrollmentStatus;
        this.grade = grade;
        this.student = student;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return semester.getName() + "," + student.getFirstName() + " " + student.getLastName() + ","
                + course.getModule().getModulecode() + "," + course.getModule().getModulename() + "," + enrollmentStatus
                + "," + grade;
    }

    public static String toStringHeading() {
        return "Semester" + "," + "Student Name" + "," + "Module Code" + "," + "Module Name" + "," + "Enrollment Status"
                + "," + "Grade";
    }

    public String toCsv() {
        return semester.getName() + "," + student.getFirstName() + " " + student.getLastName() + ","
                + course.getModule().getModulecode() + "," + course.getModule().getModulename() + "," + enrollmentStatus
                + "," + grade;
    }
}