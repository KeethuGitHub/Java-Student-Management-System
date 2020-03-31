package sg.edu.nus.studentmanagementsystem.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Admin;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Course;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Department;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Enrollment;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Faculty;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Semester;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Student;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.User;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Module;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.ContactDetail;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.PasswordDetails;
import sg.edu.nus.studentmanagementsystem.repositories.AdminRepository;
import sg.edu.nus.studentmanagementsystem.repositories.CourseRepository;
import sg.edu.nus.studentmanagementsystem.repositories.DepartmentRepository;
import sg.edu.nus.studentmanagementsystem.repositories.EnrollmentRepository;
import sg.edu.nus.studentmanagementsystem.repositories.FacultyRepository;
import sg.edu.nus.studentmanagementsystem.repositories.ModuleRepository;
import sg.edu.nus.studentmanagementsystem.repositories.SemesterRepository;
import sg.edu.nus.studentmanagementsystem.repositories.StudentRepository;

@Service
public class AdminService {
	private AdminRepository adminRepo;
	private CourseRepository courseRepo;
	private DepartmentRepository departmentRepo;
	private EnrollmentRepository enrollmentRepo;
	private FacultyRepository facultyRepo;
	private SemesterRepository semesterRepo;
	private ModuleRepository moduleRepo;
	private StudentRepository studentRepo; 

	
//	@Autowired
//	public void setFacultyService(FacultyService fs) {
//		this.facultyService = fs;
//	}
	
	@Autowired
	public void setAdminRepository(AdminRepository repo) {
		this.adminRepo = repo;
	}
	
	@Autowired
    public void setStudentRepository(StudentRepository repo) {
        this.studentRepo = repo;
    }

	@Autowired
	public void setCourseRepository(CourseRepository repo) {
		this.courseRepo = repo;
	}

	@Autowired
	public void setDepartmentRepository(DepartmentRepository repo) {
		this.departmentRepo = repo;
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
	public void setSemesterRepository(SemesterRepository repo) {
		this.semesterRepo = repo;
	}

	@Autowired
	public void setModuleRepository(ModuleRepository repo) {
		this.moduleRepo = repo;
	}

	public Admin updateAdmin(Admin admin) {
		return adminRepo.save(admin);
	}

	public Admin findByUsername(String username) {
		return adminRepo.findByUsername(username);
	}

	public Enrollment retrieveEnrollmentById(int id) {
		return enrollmentRepo.findById(id).get();
	}
	
	public List<Enrollment> retrieveAllEnrollmentsByStudentId(int id)
	{
		return enrollmentRepo.findByStudentId(id);
	}
	
	public List<Enrollment> retrieveAllEnrollmentsBySemesterByStudentId(int semesterid, int studentid)
	{
		return enrollmentRepo.findBySemesterByStudentId(semesterid, studentid);
	}
	
	public List<Enrollment> retrieveAllEnrollmentsByModuleByStudentId(int moduleid,int studentid)
	{
		return enrollmentRepo.findByModuleByStudentId(moduleid, studentid);
	}
	
	
	public List<Enrollment> retrieveEnrollmentsBySemester(int id)
	{
		return enrollmentRepo.findBySemester(id);
	}
	
	public List<Enrollment> retrieveEnrollmentsByModule(int id)
	{
		return enrollmentRepo.findByModule(id);
	}
	
	public List<Enrollment> retrieveEnrollmentsByStatus(String enrollmentStatus) {
		return enrollmentRepo.findByEnrollmentStatus(enrollmentStatus);
	}
	
	public List<Enrollment> retrieveAllEnrollments(){
		return enrollmentRepo.findAll();
	}
	
	public void deleteEnrollment(Enrollment enrollment) {
		enrollmentRepo.delete(enrollment.getId());
	}
	
	public List<Enrollment> retrieveEnrollmentsByStudentId(int studentId){
		return enrollmentRepo.findByStudentId(studentId);
	}

	public void updateEnrollmentStatus(String action, int id) {
		Enrollment e = retrieveEnrollmentById(id);
		if (action.equals("approve"))
			e.setEnrollmentStatus("Enrolled");
		else if (action.equals("reject"))
			e.setEnrollmentStatus("Rejected");
		enrollmentRepo.save(e);
	}

	public Course retrieveCourseById(int id) {
		return courseRepo.findById(id).get();
	}

	public List<Course> retrieveCourseBySemester(int id) {
		return courseRepo.findBySemesterId(id);
	}

	public List<Course> retrieveCourseByDepartment(int id) {
		return courseRepo.findByDepartmentId(id);
	}

	public List<Course> retrieveCourseByFaculty(int id) {
		return courseRepo.findByFacultyId(id);
	}

	public List<Course> retrieveCourseByModuleCode(String modulecode) {
		return courseRepo.findByModuleCode(modulecode);
	}

	public List<Module> retrieveAllModules() {
		return moduleRepo.findAll();
	}

	public List<Course> retrieveAllCourses() {
		return courseRepo.findAll();
	}

	public List<Course> retrieveCoursesByPartialModulename(String modulename) {
		return courseRepo.findByPartialModulename(modulename);
	}

	public Department retrieveDepartmentById(int id) {
		return departmentRepo.findById(id).get();
	}

	public Department retrieveDepartmentByName(String departmentName) {
		return departmentRepo.findByName(departmentName);
	}

	public List<Department> retrieveAllDepartments() {
		return departmentRepo.findAll();
	}

	public Faculty retrieveFacultyById(int id) {
		return facultyRepo.findById(id);
	}

	public List<Faculty> retrieveAllFaculties() {
		return facultyRepo.findAll();
	}

	public Semester retrieveSemesterById(int id) {
		return semesterRepo.findById(id);
	}

	public List<Semester> retrieveAllSemesters() {
		return semesterRepo.findAll();
	}

	public Module retrieveModuleById(int id) {
		return moduleRepo.findById(id).get();
	}
	
	public Module retrieveModuleByModulecode(String modulecode) {
		return moduleRepo.findByModulecode(modulecode);
	}
	
	public Module retrieveModuleByModulename(String modulename) {
		return moduleRepo.findByModulename(modulename);
	}


	public Course updateCourse(Course course) {
		Department d = retrieveDepartmentById(course.getDepartment().getId()); // new department
		Faculty f = retrieveFacultyById(course.getFaculty().getId());// new faculty
		Semester s = retrieveSemesterById(course.getSemester().getId()); // new semester
		Module m = retrieveModuleById(course.getModule().getId()); // new module

		if (course.getId() == 0) {
			d.addCourse(course);
			f.addCourse(course);
			s.addCourse(course);
			m.addCourse(course);
			return courseRepo.save(course);
		} else
		{
			Course course_old = retrieveCourseById(course.getId());
			List<Enrollment> enrollments = course_old.getEnrollments();
			for (Enrollment e: enrollments)
			{
				if(!(e.getSemester().equals(course.getSemester())))
				{
					return null;
				}
			}
			
			course_old.setDepartment(course.getDepartment());
			course_old.setEnrollments(course.getEnrollments());
			course_old.setFaculty(course.getFaculty());
			course_old.setModule(course.getModule());
			course_old.setSemester(course.getSemester());
			course_old.setSize(course.getSize());
			return courseRepo.save(course_old);
		}
	}
	
	public Student retrieveStudentById(int id)
	{
		return studentRepo.findById(id);
	}
	
	public Enrollment updateEnrollment(Enrollment enrollment)
	{
		
		Semester sem = retrieveSemesterById(enrollment.getSemester().getId()); // new semester
		Course c = retrieveCourseById(enrollment.getCourse().getId()); //new course
		Student s = retrieveStudentById(enrollment.getStudent().getId()); //new student
		
		if(courseRepo.findBySemesterIdAndCourseId(enrollment.getSemester().getId(), enrollment.getCourse().getId()) == null)
		{
			return null;
		}
		
		if(enrollment.getEnrollmentStatus().equals("Pending Approval") && !enrollment.getGrade().equals(""))
		{
			return null;
		}
		
		if(enrollment.getEnrollmentStatus().equals("Completed") && enrollment.getGrade().equals(""))
		{
			return null;
		}
		if(enrollment.getEnrollmentStatus().equals("Enrolled") && !enrollment.getGrade().equals(""))
		{
			return null;
		}
		if(enrollment.getEnrollmentStatus().equals("Rejected") && !enrollment.getGrade().equals(""))
		{
			return null;
		}
		
		if (enrollment.getId() == 0) {
			s.addEnrollment(enrollment);
			sem.addEnrollment(enrollment);
			c.addEnrollment(enrollment);
			return enrollmentRepo.save(enrollment);
			}
		else
		{
			Enrollment enrollment_old = enrollmentRepo.findById(enrollment.getId()).get();
			enrollment_old.setCourse(c);
			enrollment_old.setSemester(sem);
			enrollment_old.setStudent(s);
			enrollment_old.setEnrollmentStatus(enrollment.getEnrollmentStatus());
			if(enrollment.getGrade().equals(""))
			{
				enrollment_old.setGrade(null);
			}
			else {
				enrollment_old.setGrade(enrollment.getGrade());
			}
			Enrollment savedEnrollment = enrollmentRepo.save(enrollment_old);
			s.setCgpa(calculateCGPA(enrollment));
			studentRepo.save(s);
			return savedEnrollment;
		}
	}
	
	public Module updateModule(Module module) {
		return moduleRepo.save(module);
	}
	

    public Department updateDepartment(Department department) {
        return departmentRepo.save(department);
    }

    public Admin updateAdmin(String username, ContactDetail contactDetail) {
        Admin admin = adminRepo.findByUsername(username);
        admin.setAddress(contactDetail.getAddress());
        admin.setContact(contactDetail.getContact());
        admin.setEmail(contactDetail.getEmail());

        return adminRepo.save(admin);
    }
    
    public Admin updateAdminPassword(String username, PasswordDetails passwordDetails) {
        Admin admin = adminRepo.findByUsername(username);   
        admin.setPassword(BCrypt.hashpw(passwordDetails.getNewpassword(), BCrypt.gensalt(10)));

        return adminRepo.save(admin);
    }
    
    
    public String generateUsername(User user) {
    	int usernameLength = 6; // S 0000 1
    	int currentNumOfUser;
    	String newUsername = "";
    	
    	if (user instanceof Student) {
    		newUsername += "S";
    		currentNumOfUser = studentRepo.findMaxId();
    		String nextIndex = String.valueOf(currentNumOfUser+1);
    		for (int i = 0; i < (usernameLength-1) - nextIndex.length(); i++) {
    			newUsername = newUsername + "0";
    		}
    		newUsername += nextIndex;
    	}
    	else if (user instanceof Faculty) {
    		newUsername += "F";
    		currentNumOfUser = facultyRepo.findMaxId();
    		String nextIndex = String.valueOf(currentNumOfUser+1);
    		for (int i = 0; i < (usernameLength-1) - nextIndex.length(); i++) {
    			newUsername = newUsername + "0";
    		}
    		newUsername += nextIndex;
    	}
    	else if (user instanceof Admin) {
    		newUsername += "A";
    		currentNumOfUser = adminRepo.findMaxId();
    		String nextIndex = String.valueOf(currentNumOfUser+1);
    		for (int i = 0; i < (usernameLength-1) - nextIndex.length(); i++) {
    			newUsername = newUsername + "0";
    		}
    		newUsername += nextIndex;
    	}
    	return newUsername;
    }
    
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
    
    public double calculateCGPA(Enrollment enrollment) {
        HashMap<String, Double> map = getGradeScoreMap();
        double cgpa = 0.0;
        int totalCourseCredit = 0;
        double totalGradePoints = 0;
        List<Enrollment> enrollments = enrollment.getStudent().getEnrollments();
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
    
    
}