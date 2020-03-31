package sg.edu.nus.studentmanagementsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
	@Query("SELECT c FROM Course c WHERE c.module.modulecode = :modulecode")
	List<Course> findByModuleCode(@Param("modulecode") String modulecode);
	
	@Query("SELECT c FROM Course c WHERE UPPER(c.module.modulename) LIKE UPPER(CONCAT('%', :modulename, '%'))")
	List<Course> findByPartialModulename(@Param("modulename") String modulename);
	
    @Query("SELECT c FROM Course c WHERE c.semester.id = :semesterId")	
    List<Course> findBySemesterId(@Param("semesterId") int semesterId);
	
	@Query("SELECT c FROM Course c WHERE c.id = :courseId")
	Course findByCourseId(@Param("courseId") int courseId);

	@Query("SELECT c FROM Course c WHERE c.semester.id =:semesterId AND c.id = :courseId")
	Course findBySemesterIdAndCourseId(@Param("semesterId") int semesterId, @Param("courseId") int courseid);

	@Query("SELECT c1 FROM Course c1 WHERE c1 NOT IN (SELECT c FROM Course c LEFT JOIN c.enrollments e WHERE e.student.username = :username) and c1.semester.id = :semesterid")
	List<Course> findAvailableCoursesByUsername(@Param("username") String username, @Param("semesterid") int semesterid);

	@Query("SELECT c1 FROM Course c1 WHERE c1 IN (SELECT c FROM Course c LEFT JOIN c.enrollments e WHERE e.student.username = :username)")
	List<Course> findEnrolledCoursesByUsername(@Param("username") String username);

	@Query("SELECT DISTINCT c FROM Course c WHERE c.faculty.username = :facultyUsername")
	List<Course> findByFacultyUsername(@Param("facultyUsername") String facultyUsername);
	
	@Query("SELECT c1 FROM Course c1 ")
	List<String> findDistinctModules();
	
	@Query("SELECT c FROM Course c WHERE c.department.id = :departmentId")
	List<Course> findByDepartmentId(@Param("departmentId") int departmentId);
	
	@Query("SELECT c FROM Course c WHERE c.faculty.id = :facultyId")
	List<Course> findByFacultyId(@Param("facultyId") int facultyId);
	
	
}

/* Changes to Method Names */
// findByCourseByName --> findByPartialCoursename
// findCoursesAvailableForStudent --> findBySemesterId
// findCourseBySemesterCourseCode --> findBySemesterIdAndCoursecode
// findCoursesAvailable --> findAvailableCoursesByUsername
// findallcourses --> findEnrolledCoursesByUsername
// findCoursesByFaculty --> findByFacultyUsername
// findCourseByCoursecode --> findByCoursecode