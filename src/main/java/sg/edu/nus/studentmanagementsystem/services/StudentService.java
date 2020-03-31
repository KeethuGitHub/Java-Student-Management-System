package sg.edu.nus.studentmanagementsystem.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Course;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Enrollment;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Semester;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Student;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.ContactDetail;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.CourseGrade;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.EnrollmentRecord;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.GradeBook;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.PasswordDetails;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.SemesterGrade;
import sg.edu.nus.studentmanagementsystem.repositories.CourseRepository;
import sg.edu.nus.studentmanagementsystem.repositories.EnrollmentRepository;
import sg.edu.nus.studentmanagementsystem.repositories.SemesterRepository;
import sg.edu.nus.studentmanagementsystem.repositories.StudentRepository;

@Service
public class StudentService {
    private CourseRepository courseRepo;
    private EnrollmentRepository enrollmentRepo;
    private SemesterRepository semesterRepo;
    private StudentRepository studentRepo;

    @Autowired
    public void setCourseRepository(CourseRepository repo) {
        this.courseRepo = repo;
    }

    @Autowired
    public void setEnrollmentRepository(EnrollmentRepository repo) {
        this.enrollmentRepo = repo;
    }

    @Autowired
    public void setSemesterRepository(SemesterRepository repo) {
        this.semesterRepo = repo;
    }

    @Autowired
    public void setStudentRepository(StudentRepository repo) {
        this.studentRepo = repo;
    }

    public Student findById(int id) {
        return studentRepo.findById(id);
    }

    public Student findByUsername(String username) {
        return studentRepo.findByUsername(username);
    }

    public GradeBook retrieveGradeBook(String username) {
        Student student = studentRepo.findByUsername(username);
        List<Semester> semesters = student.getSemesters();
        List<Enrollment> enrollments = student.getEnrollments();

        @SuppressWarnings("serial")
        HashMap<String, Double> gradeScores = new HashMap<String, Double>() {
            {
                put("A+", 5.0);
                put("A", 5.0);
                put("A-", 4.5);
                put("B+", 4.0);
                put("B", 3.5);
                put("B-", 3.0);
                put("C+", 2.5);
                put("C", 2.0);
                put("D+", 1.5);
                put("D", 1.0);
                put("F", 0.0);
            }
        };

        GradeBook gradeBook = new GradeBook();
        gradeBook.setMajor(student.getMajor());
        List<SemesterGrade> semesterGrades = new ArrayList<SemesterGrade>();
        int totalMC = 0;
        int totalMCGP = 0;
        for (Semester semester : semesters) {
            SemesterGrade semesterGrade = new SemesterGrade();
            semesterGrade.setSemesterName(semester.getName());
            List<CourseGrade> courseGrades = new ArrayList<CourseGrade>();
            int semMC = 0;
            int semMCGP = 0;
            for (Enrollment enrollment : enrollments) {
                CourseGrade courseGrade = new CourseGrade();
                double gradeScore = 0;
                if (enrollment.getSemester().getId() == semester.getId()
                        && enrollment.getEnrollmentStatus().equals("Completed")) {
                    Course course = enrollment.getCourse();
                    courseGrade.setCourseCode(course.getModule().getModulecode());
                    courseGrade.setCourseName(course.getModule().getModulename());
                    courseGrade.setCourseCredits(course.getModule().getCredit());
                    courseGrade.setCourseGrade(enrollment.getGrade());
                    gradeScore = gradeScores.get(enrollment.getGrade());
                    semMC += course.getModule().getCredit();
                    semMCGP += course.getModule().getCredit() * gradeScore;
                    courseGrades.add(courseGrade);
                }
            }
            totalMC += semMC;
            totalMCGP += semMCGP;
            double semGPA = (double) semMCGP / (double) semMC;
            semesterGrade.setSemesterGPA(semGPA);
            semesterGrade.setCourseGrades(courseGrades);
            semesterGrades.add(semesterGrade);
        }
        gradeBook.setGradedCredits(totalMC);
        gradeBook.setSemesterGrades(semesterGrades);
        double studentCGPA = (double) totalMCGP / (double) totalMC;
        if (studentCGPA != student.getCgpa())
            System.out.println("ERROR!!! DISCREPANCIES IN CGPA!!!");
        gradeBook.setCgpa(student.getCgpa());
        if (studentCGPA >= 4.5)
            gradeBook.setDegreeClass("Honours (Highest Distinction)");
        else if (studentCGPA >= 4.0 && studentCGPA < 4.5)
            gradeBook.setDegreeClass("Honours (Distinction)");
        else if (studentCGPA >= 3.5 && studentCGPA < 4.0)
            gradeBook.setDegreeClass("Honours (Merit)");
        else if (studentCGPA >= 3.0 && studentCGPA < 3.5)
            gradeBook.setDegreeClass("Honours");
        else if (studentCGPA >= 2.0 && studentCGPA < 3.0)
            gradeBook.setDegreeClass("Pass");
        else
            gradeBook.setDegreeClass("Fail");

        return gradeBook;
    }

    public List<Course> retrieveAvailableCourses(String username, int semesterid) {
        return courseRepo.findAvailableCoursesByUsername(username, semesterid);
    }

    public void enrollCourse(String username, int semesterId, int courseId) {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentStatus("Pending Approval");
        Student student = studentRepo.findByUsername(username);
        Semester semester = semesterRepo.findById(semesterId);
        Course course = courseRepo.findBySemesterIdAndCourseId(semesterId, courseId);
        enrollment.setStudent(student);
        enrollment.setSemester(semester);
        enrollment.setCourse(course);
        enrollmentRepo.save(enrollment);
    }

    public List<EnrollmentRecord> retrieveEnrolledCourses(String username) {
        Student student = studentRepo.findByUsername(username);
        List<EnrollmentRecord> enrollmentRecords = new ArrayList<EnrollmentRecord>();

        List<Course> allCourses = courseRepo.findEnrolledCoursesByUsername(username);
        for (Course course : allCourses) {
            Semester semester = semesterRepo.findById(course.getSemester().getId());
            Enrollment enrollment = enrollmentRepo.findByStudentAndSemesterAndCourse(student, semester, course);
            EnrollmentRecord enrollmentRecord = new EnrollmentRecord();
            enrollmentRecord.setModulecode(course.getModule().getModulecode());
            enrollmentRecord.setCredit(course.getModule().getCredit());
            enrollmentRecord.setDepartment(course.getDepartment());
            enrollmentRecord.setEnrollmentStatus(enrollment.getEnrollmentStatus());
            enrollmentRecord.setFaculty(course.getFaculty());
            enrollmentRecord.setModulename(course.getModule().getModulename());
            enrollmentRecord.setSemester(course.getSemester());
            enrollmentRecord.setSize(course.getSize());
            enrollmentRecord.setModuleid(course.getId());
            enrollmentRecords.add(enrollmentRecord);
        }

        return enrollmentRecords;
    }

    public void unenrollCourse(String username, int semesterId, int courseId) {
        Student student = studentRepo.findByUsername(username);
        Semester semester = semesterRepo.findById(semesterId);
        Course course = courseRepo.findBySemesterIdAndCourseId(semesterId, courseId);
        Enrollment enrollment = enrollmentRepo.findByStudentAndSemesterAndCourse(student, semester, course);
        enrollmentRepo.delete(enrollment.getId());
    }

    public Student updateStudent(String username, ContactDetail contactDetail) {
        Student student = studentRepo.findByUsername(username);   
        student.setAddress(contactDetail.getAddress());
        student.setContact(contactDetail.getContact());
        student.setEmail(contactDetail.getEmail());

        return studentRepo.save(student);
    }
    
    public Student updateStudentPassword(String username, PasswordDetails passwordDetails) {
        Student student = studentRepo.findByUsername(username);   
        student.setPassword(BCrypt.hashpw(passwordDetails.getNewpassword(), BCrypt.gensalt(10)));

        return studentRepo.save(student);
    }
    
    
}