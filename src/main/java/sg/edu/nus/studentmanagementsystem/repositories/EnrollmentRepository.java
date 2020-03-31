package sg.edu.nus.studentmanagementsystem.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Course;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Enrollment;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Semester;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Student;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    @Query("SELECT e FROM Enrollment e WHERE e.student = :student AND e.semester = :semester AND e.course =:course")
    public Enrollment findByStudentAndSemesterAndCourse(@Param("student") Student student,
            @Param("semester") Semester semester, @Param("course") Course course);

    @Query("SELECT e FROM Enrollment e WHERE LOWER(e.student.firstName) LIKE :searchStr "
								 + "OR LOWER(e.student.lastName) LIKE :searchStr "
								 + "OR LOWER(CONCAT(e.student.firstName,' ',e.student.lastName)) LIKE :searchStr "
								 + "OR LOWER(e.course.module.modulename) LIKE :searchStr "
								 + "OR LOWER(e.course.module.modulecode) LIKE :searchStr "
								 + "OR LOWER(e.enrollmentStatus) LIKE :searchStr "
								 + "OR LOWER(e.semester.name) LIKE :searchStr "
								 + "OR LOWER(e.grade) LIKE :searchStr")
    List<Enrollment> findBySearchStr(@Param("searchStr") String searchStr);

    @Query("select e from Enrollment e WHERE e.semester.id = :semesterId")
    List<Enrollment> findBySemester(@Param("semesterId") int semesterId);
    
    @Query("select e from Enrollment e WHERE e.course.module.id = :moduleId")
    List<Enrollment> findByModule(@Param("moduleId") int moduleId);
    

    @Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus =:enrollmentStatus")
    List<Enrollment> findByEnrollmentStatus(@Param("enrollmentStatus") String enrollmentStatus);

    @Query("select e from Enrollment e WHERE e.student = :student")
    List<Enrollment> findByStudent(@Param("student") Student student);
    
    @Query("select e from Enrollment e WHERE e.student.id = :studentId")
    List<Enrollment> findByStudentId(@Param("studentId") int studentId);

    @Query("SELECT e.student FROM Enrollment e WHERE e.enrollmentStatus IN ('Enrolled', 'Completed') AND e.course.id = :courseId")
    List<Student> findAllStudentsInOneCourse(@Param("courseId") int courseId);

    @Query("SELECT DISTINCT e.student FROM Enrollment e WHERE e.enrollmentStatus IN ('Enrolled', 'Completed') AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Student> findStudentsByFaculty(@Param("facultyUsername") String facultyUsername);

    @Query("SELECT DISTINCT e.student FROM Enrollment e WHERE e.enrollmentStatus IN ('Enrolled', 'Completed') AND e.semester.name = :semesterName AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
	List<Student> findStudentsByFacultyAndSemester(@Param("facultyUsername") String facultyUsername, @Param("semesterName") String semesterName);
    
    @Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus IN ('Enrolled', 'Completed') AND e.semester.name= :semesterName AND e.course.id = :courseId AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Enrollment> findBySemesterAndCourseAndFaculty(@Param("semesterName") String semesterName, @Param("courseId") int courseId, @Param("facultyUsername") String facultyUsername);
    
    @Query("SELECT DISTINCT e.student FROM Enrollment e WHERE e.enrollmentStatus IN ('Enrolled', 'Completed') AND e.semester.name = :semesterName AND e.course.module.modulecode = :modulecode AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Student> findStudentsByFacultyAndSemesterAndCourse(@Param("facultyUsername") String facultyUsername,
            @Param("semesterName") String semesterName, @Param("modulecode") String modulecode);
    
    @Query("SELECT e.course FROM Enrollment e WHERE e.enrollmentStatus IN ('Enrolled','Completed') AND e.student.username = :studentUsername AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Course> findCoursesByStudentAndFaculty(@Param("studentUsername") String studentUsername,
            @Param("facultyUsername") String facultyUsername);

    @Query("SELECT e.course FROM Enrollment e WHERE e.enrollmentStatus IN ('Enrolled','Completed') AND e.student.username = :studentUsername AND e.semester.name = :semesterName AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Course> findCoursesByStudentAndFacultyAndSemester(@Param("studentUsername") String studentUsername,
            @Param("facultyUsername") String facultyUsername, @Param("semesterName") String semesterName);

    @Query("SELECT DISTINCT e.course FROM Enrollment e WHERE e.enrollmentStatus IN ('Enrolled','Completed') AND e.semester.name= :currentSemester AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Course> findCoursesByFacultyAndSemester(@Param("facultyUsername") String facultyUsername,
            @Param("currentSemester") String currentSemester);

    @Modifying
    @Query("DELETE FROM Enrollment e WHERE e.id = :id")
    @Transactional
    void delete(@Param("id") int id);
    
    @Query("SELECT e FROM Enrollment e WHERE e.semester.id = :semesterId AND e.student.id = :studentId")
    public List<Enrollment> findBySemesterByStudentId(@Param("semesterId") int semesterId, @Param("studentId") int studentId);
    
    @Query("SELECT e FROM Enrollment e WHERE e.course.module.id = :moduleId AND e.student.id = :studentId")
    public List<Enrollment> findByModuleByStudentId(@Param("moduleId") int moduleId, @Param("studentId") int studentId);
    
    @Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus IN ('Completed') AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Enrollment> findByFaculty(@Param("facultyUsername") String facultyUsername);
    
    @Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus IN ('Completed') AND e.grade=:grade AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Enrollment> findByFacultyAndGrade(@Param("facultyUsername") String facultyUsername, @Param("grade") String grade);
    
    @Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus IN ('Completed') AND e.course.id = :courseId AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Enrollment> findByFacultyAndCourse(@Param("facultyUsername") String facultyUsername, @Param("courseId") int courseId);
    
    @Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus IN ('Completed') AND e.grade = :grade AND e.course.id = :courseId AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Enrollment> findByFacultyAndCourseAndGrade(@Param("facultyUsername") String facultyUsername, @Param("courseId")int courseId, @Param("grade") String grade);
    
    @Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus IN ('Completed') AND e.semester.name = :semesterName AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Enrollment> findByFacultyAndSem(@Param("facultyUsername") String facultyUsername, @Param("semesterName") String semesterName);
    
    @Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus IN ('Completed') AND e.grade=:grade AND e.semester.name = :semesterName AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Enrollment> findByFacultyAndGradeAndSem(@Param("facultyUsername") String facultyUsername, @Param("grade") String grade, @Param("semesterName") String semesterName);
    
    @Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus IN ('Completed') AND e.course.id = :courseId AND e.semester.name = :semesterName AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Enrollment> findByFacultyAndCourseAndSem(@Param("facultyUsername") String facultyUsername, @Param("courseId") int courseId, @Param("semesterName") String semesterName);
    
    @Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus IN ('Completed') AND e.semester.name= :semesterName AND e.course.id = :courseId AND e.grade = :grade AND e.course IN (SELECT c FROM Course c WHERE c.faculty.username = :facultyUsername)")
    List<Enrollment> findByFacultyAndCourseAndGradeAndSem(@Param("facultyUsername") String facultyUsername, @Param("courseId")int courseId, @Param("grade") String grade, @Param("semesterName") String semesterName);

}

/* Changes to Method Names */
// findEnrollmentByStudentSemesterCourse --> findByStudentAndSemesterAndCourse
// findEnrollmentsByCourseAndSemester --> findBySemesterAndCourseAndFaculty
// findEnrollmentByEnrollmentStatus --> findByEnrollmentStatus
// findEnrollmentByStudent --> findByStudent
// findStudentBySemester --> findStudentsByFacultyAndSemester
// findStudentByCourseAndSemester --> findStudentsByFacultyAndSemesterAndCourse
// findCourseByStudentFaculty --> findCoursesByStudentAndFaculty
// findCourseByStudentSemesterFaculty -->
// findCoursesByStudentAndFacultyAndSemester