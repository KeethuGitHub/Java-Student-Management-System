package sg.edu.nus.studentmanagementsystem.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Course;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Enrollment;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Faculty;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Semester;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Student;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.ContactDetail;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.PasswordDetails;
import sg.edu.nus.studentmanagementsystem.repositories.CourseRepository;
import sg.edu.nus.studentmanagementsystem.repositories.EnrollmentRepository;
import sg.edu.nus.studentmanagementsystem.repositories.FacultyRepository;
import sg.edu.nus.studentmanagementsystem.repositories.SemesterRepository;
import sg.edu.nus.studentmanagementsystem.repositories.StudentRepository;

@Service
public class FacultyService {
    private CourseRepository courseRepo;
    private EnrollmentRepository enrollmentRepo;
    private FacultyRepository facultyRepo;
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
    public void setFacultyRepository(FacultyRepository repo) {
        this.facultyRepo = repo;
    }

    @Autowired
    public void setStudentRepository(SemesterRepository repo) {
        this.semesterRepo = repo;
    }

    @Autowired
    public void setStudentRepository(StudentRepository repo) {
        this.studentRepo = repo;
    }

    public Faculty findByUsername(String username) {
        return facultyRepo.findByUsername(username);
    }

    public List<Enrollment> retrieveMasterList() {
        return enrollmentRepo.findAll();
    }

    public List<Enrollment> retrieveSearchResults(String searchStr) {
        return enrollmentRepo.findBySearchStr(searchStr);
    }

    public List<Course> retrieveFacultyCourses(String username) {
        return courseRepo.findByFacultyUsername(username);
    }

    public Course retrieveCourse(int courseId) {
        return courseRepo.findByCourseId(courseId);
    }

    public List<Student> retrieveCourseStudents(int courseId) {
        return enrollmentRepo.findAllStudentsInOneCourse(courseId);
    }

    public Student retrieveStudentParticulars(String username) {
        return studentRepo.findByUsername(username);
    }

    public List<Semester> retrieveAllSemesters() {
        return semesterRepo.findAll();
    }

    public List<Student> retrieveAllStudentsUnderFaculty(String username) {
        return enrollmentRepo.findStudentsByFaculty(username);
    }

    public List<Student> retrieveAllStudentsUnderFacultyInSemester(String username, String semesterName) {
        return enrollmentRepo.findStudentsByFacultyAndSemester(username, semesterName);
    }

    public List<Student> retrieveAllStudentsUnderFacultyInSemesterInCourse(String username, String semesterName, String modulecode) {
        return enrollmentRepo.findStudentsByFacultyAndSemesterAndCourse(username, semesterName, modulecode);
    }

    public List<Course> retrieveCoursesByStudentAndFaculty(String studentUsername, String facultyUsername) {
        return enrollmentRepo.findCoursesByStudentAndFaculty(studentUsername, facultyUsername);
    }

    public List<Course> retrieveCoursesByStudentAndFacultyAndSemester(String studentUsername, String facultyUsername, String semesterName) {
        return enrollmentRepo.findCoursesByStudentAndFacultyAndSemester(studentUsername, facultyUsername, semesterName);
    }

    public List<Course> retrieveCoursesByFacultyAndSemester(String facultyUsername, String semesterName) {
        return enrollmentRepo.findCoursesByFacultyAndSemester(facultyUsername, semesterName);
    }

    public Enrollment retrieveEnrollment(Integer id) {
        return enrollmentRepo.findById(id).get();
    }

    public List<Enrollment> retrieveEnrollments(String semesterName, int courseId, String facultyUsername) {
        return enrollmentRepo.findBySemesterAndCourseAndFaculty(semesterName, courseId, facultyUsername);
    }

    public Enrollment updateEnrollment(Enrollment enrollment) {
        Student student = studentRepo.findByUsername(enrollment.getStudent().getUsername());
        Semester semester = semesterRepo.findById(enrollment.getSemester().getId());
        Course course = courseRepo.findByCourseId(enrollment.getCourse().getId());
        enrollment.setEnrollmentStatus("Completed");
        enrollment.setCourse(course);
        enrollment.setSemester(semester);
        enrollment.setStudent(student);
        Enrollment savedEnrollment = enrollmentRepo.save(enrollment);

        double cgpa = calculateCGPA(student.getId());
        student.setCgpa(cgpa);
        studentRepo.save(student);

        return savedEnrollment;
    }

    public Faculty updateFaculty(String username, ContactDetail contactDetail) {
        Faculty faculty = facultyRepo.findByUsername(username);
        faculty.setAddress(contactDetail.getAddress());
        faculty.setContact(contactDetail.getContact());
        faculty.setEmail(contactDetail.getEmail());

        return facultyRepo.save(faculty);
    }
    
    public Faculty updateFacultyPassword(String username, PasswordDetails passwordDetails) {
        Faculty faculty = facultyRepo.findByUsername(username);   
        faculty.setPassword(BCrypt.hashpw(passwordDetails.getNewpassword(), BCrypt.gensalt(10)));

        return facultyRepo.save(faculty);
    }
    
    
    public List<Enrollment> retrieveEnrollmentsForGradeReport(String facultyUsername) {
        return enrollmentRepo.findByFaculty(facultyUsername);
    }
    
    public List<Enrollment> retrieveEnrollmentsForGradeReport(String facultyUsername, String grade) {
        return enrollmentRepo.findByFacultyAndGrade(facultyUsername, grade);
    }
    
    public List<Enrollment> retrieveEnrollmentsForGradeReport(String facultyUsername, int courseId) {
        return enrollmentRepo.findByFacultyAndCourse(facultyUsername, courseId);
    }
    
    public List<Enrollment> retrieveEnrollmentsForGradeReport(String facultyUsername, int courseId, String grade) {
        return enrollmentRepo.findByFacultyAndCourseAndGrade(facultyUsername, courseId, grade);
    }
    
    public List<Enrollment> retrieveEnrollmentsForGradeReportWithSemester(String facultyUsername, String semesterName) {
        return enrollmentRepo.findByFacultyAndSem(facultyUsername, semesterName);
    }
    
    public List<Enrollment> retrieveEnrollmentsForGradeReportWithSemester(String facultyUsername, String grade, String semesterName) {
        return enrollmentRepo.findByFacultyAndGradeAndSem(facultyUsername, grade, semesterName);
    }
    
    public List<Enrollment> retrieveEnrollmentsForGradeReportWithSemester(String facultyUsername, int courseId, String semesterName) {
        return enrollmentRepo.findByFacultyAndCourseAndSem(facultyUsername, courseId, semesterName);
    }
    
    public List<Enrollment> retrieveEnrollmentsForGradeReportWithSemester(String facultyUsername, int courseId, String grade, String semesterName) {
        return enrollmentRepo.findByFacultyAndCourseAndGradeAndSem(facultyUsername, courseId, grade, semesterName);
    }
    

//    // -------------------- CGPA calculation ----------------------------//
    public static HashMap<String, Double> getGradeScoreMap() {
        HashMap<String, Double> gradeScoreMap = new HashMap<String, Double>();
        gradeScoreMap.put("A+", 5.0);
        gradeScoreMap.put("A", 5.0);
        gradeScoreMap.put("A-", 4.5);
        gradeScoreMap.put("B+", 4.0);
        gradeScoreMap.put("B", 3.5);
        gradeScoreMap.put("B-", 3.0);
        gradeScoreMap.put("C+", 2.5);
        gradeScoreMap.put("C", 2.0);
        gradeScoreMap.put("D+", 1.5);
        gradeScoreMap.put("D", 1.0);
        gradeScoreMap.put("F", 0.0);

        return gradeScoreMap;
    }

    public double calculateCGPA(int id) {
        HashMap<String, Double> map = getGradeScoreMap();
        Student student = studentRepo.findById(id);
        double cgpa = 0.0;
        int totalCourseCredit = 0;
        double totalGradePoints = 0;
        List<Enrollment> enrollments = student.getEnrollments();
        if (enrollments != null) {
            for (Enrollment e : enrollments) {            
                if (e.getEnrollmentStatus().equals("Completed")) {
                    totalCourseCredit += e.getCourse().getModule().getCredit();
                    totalGradePoints += map.get(e.getGrade()) * e.getCourse().getModule().getCredit();
                }
            }
            cgpa = totalGradePoints / totalCourseCredit;

            return cgpa;
        }

        return 0.0;
    }

    public static String generateRecipientEmailList(List<Student> students) {
		if (students == null)
            return "";
            
		String recipientList = "";
		for (Iterator<Student> i = students.iterator(); i.hasNext();) {
			recipientList += (i.next().getEmail()+";");
        }
        
		return recipientList;
	}

}