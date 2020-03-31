
package sg.edu.nus.studentmanagementsystem.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Admin;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Course;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Department;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Enrollment;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Faculty;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Module;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Semester;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Student;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.User;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.ContactDetail;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.CourseQuery;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.DepartmentQuery;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.EnrollmentQuery;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.ModuleQuery;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.PasswordDetails;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.UserQuery;
import sg.edu.nus.studentmanagementsystem.repositories.AdminRepository;
import sg.edu.nus.studentmanagementsystem.repositories.FacultyRepository;
import sg.edu.nus.studentmanagementsystem.repositories.StudentRepository;
import sg.edu.nus.studentmanagementsystem.services.AdminService;
import sg.edu.nus.studentmanagementsystem.services.PagingServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private AdminRepository adminRepo;
	private FacultyRepository facultyRepo;
	private StudentRepository studentRepo;
	private AdminService adminService;
	private PagingServiceImpl pagingService;
	
	@Autowired
	public void setPagingService(PagingServiceImpl pagingService) {
		this.pagingService = pagingService;
	}

	@Autowired
	public void setAdminRepository(AdminRepository repo) {
		this.adminRepo = repo;
	}

	@Autowired
	public void setFacultyRepository(FacultyRepository repo) {
		this.facultyRepo = repo;
	}

	@Autowired
	public void setStudentRepository(StudentRepository repo) {
		this.studentRepo = repo;
	}

	@Autowired
	public void setStudentService(AdminService aService) {
		this.adminService = aService;
	}

	@GetMapping("/")
	public String Index() {
		return "redirect:/admin/profile";
	}

	@GetMapping("/main")
	public String Main(HttpSession session, Model model) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		model.addAttribute("admin", session.getAttribute("sessionUser"));

		return "AdminMain";
	}

	@GetMapping("/profile")
	public String Profile(HttpSession session, Model model) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		String username = ((Admin) session.getAttribute("sessionUser")).getUsername();
		Admin admin = adminService.findByUsername(username);
		model.addAttribute("admin", admin);
		model.addAttribute("contactDetail",
				new ContactDetail(admin.getAddress(), admin.getContact(), admin.getEmail()));

		model.addAttribute("passwordDetail", new PasswordDetails());
		
		return "AdminProfile";
	}

	@GetMapping("/process_applications")
	public String ProcessApplications(HttpSession session, Model model,@RequestParam(name="val",required=false,defaultValue="0") Integer currentPage) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		List<Enrollment> paEnrollmentList = adminService.retrieveEnrollmentsByStatus("Pending Approval");
		
		HashMap<String,Object> p = pagingService.Pagination(currentPage,5,paEnrollmentList);
		model.addAttribute("pages", p.get("pages"));
		model.addAttribute("paEnrollmentList", p.get("list"));
		model.addAttribute("totalPage",p.get("totalPage"));
		
		//model.addAttribute("paEnrollmentList", paEnrollmentList);

		return "AdminProcessApplications";
	}

	@PostMapping("/process_application/{id}")
	public String processApplication(@RequestParam("action") String action, @PathVariable("id") Integer id,
			Model model) {

		adminService.updateEnrollmentStatus(action, id);

		return "redirect:/admin/process_applications";
	}

	@GetMapping("/manage_users")
	public String ManageUsers(HttpSession session, Model model) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		if (!model.containsAttribute("query"))
			model.addAttribute("query", new UserQuery());

		return "AdminManageUsers";
	}

	@PostMapping("/manage_users/search")
	public String ManageUsersSearch(@ModelAttribute("query") UserQuery userQuery, Model model,
			RedirectAttributes redirectAttr) {
		String user = userQuery.getUser();
		String username = userQuery.getUsername();
		String firstlastname = userQuery.getFirstlastname();

		if (!Strings.isBlank(username) && !Strings.isBlank(firstlastname))
			return "redirect:/admin/manage_users";
		else if (!Strings.isBlank(username)) {
			User searchResult;
			if (user.equals("Student"))
				searchResult = studentRepo.findByUsername(username);
			else if (user.equals("Faculty"))
				searchResult = facultyRepo.findByUsername(username);
			else
				searchResult = adminRepo.findByUsername(username);
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} else if (!Strings.isBlank(firstlastname) && firstlastname.split("\\s+").length == 1) {
			if (user.equals("Student")) {
				List<Student> searchResult = studentRepo.findByOneName(firstlastname);
				redirectAttr.addFlashAttribute("searchResult", searchResult);
			} else if (user.equals("Faculty")) {
				List<Faculty> searchResult = facultyRepo.findByOneName(firstlastname);
				redirectAttr.addFlashAttribute("searchResult", searchResult);
			} else {
				List<Admin> searchResult = adminRepo.findByOneName(firstlastname);
				redirectAttr.addFlashAttribute("searchResult", searchResult);
			}
		} else if (!Strings.isBlank(firstlastname)) {
			String[] str = splitWord(firstlastname);
			if (user.equals("Student")) {
				List<Student> searchResult = studentRepo.findByTwoNames(str[0], str[1]);
				redirectAttr.addFlashAttribute("searchResult", searchResult);
			} else if (user.equals("Faculty")) {
				List<Faculty> searchResult = facultyRepo.findByTwoNames(str[0], str[1]);
				redirectAttr.addFlashAttribute("searchResult", searchResult);
			} else {
				List<Admin> searchResult = adminRepo.findByTwoNames(str[0], str[1]);
				redirectAttr.addFlashAttribute("searchResult", searchResult);
			}
		} else {
			if (user.equals("Student")) {
				List<Student> searchResult = studentRepo.findByTwoNames("", "");
				redirectAttr.addFlashAttribute("searchResult", searchResult);
			} else if (user.equals("Faculty")) {
				List<Faculty> searchResult = facultyRepo.findByTwoNames("", "");
				redirectAttr.addFlashAttribute("searchResult", searchResult);
			} else {
				List<Admin> searchResult = adminRepo.findByTwoNames("", "");
				redirectAttr.addFlashAttribute("searchResult", searchResult);
			}
		}

		redirectAttr.addFlashAttribute("query", userQuery);

		return "redirect:/admin/manage_users";
	}

	@RequestMapping(value = { "/manage_users/add/{user}",
			"/manage_users/edit/{user}/{id}" }, method = RequestMethod.GET)
	public String ManageUsersEdit(@PathVariable(name = "user") String user,
			@PathVariable(name = "id", required = false) Integer id, HttpSession session, Model model) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		if (user.equals("Student")) {
			if (!model.containsAttribute("student")) {
				Student student = new Student();
				String newUsername = adminService.generateUsername(student);
				student.setUsername(newUsername);
				student.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
				if (id != null)
					student = studentRepo.findById(id).get();
				model.addAttribute("student", student);
			}

			return "AdminStudentForm";
		} else if (user.equals("Faculty")) {
			if (!model.containsAttribute("faculty")) {
				Faculty faculty = new Faculty();
				String newUsername = adminService.generateUsername(faculty);
				faculty.setUsername(newUsername);
				faculty.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
				if (id != null)
					faculty = facultyRepo.findById(id).get();
				model.addAttribute("faculty", faculty);
			}

			List<Department> departmentlist = adminService.retrieveAllDepartments();
			model.addAttribute("departmentlist", departmentlist);

			return "AdminFacultyForm";
		} else if (user.equals("Admin")) {
			if (!model.containsAttribute("admin")) {
				Admin admin = new Admin();
				String newUsername = adminService.generateUsername(admin);
				admin.setUsername(newUsername);
				admin.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
				if (id != null)
					admin = adminRepo.findById(id).get();
				model.addAttribute("admin", admin);
			}

			return "AdminAdminForm";
		}

		return "redirect:/admin/manage_users";
	}

	@PostMapping("/manage_users/edit/student/save")
	public String UpdateStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
			RedirectAttributes redirectAttr) {

		if (bindingResult.hasErrors()) {
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.student", bindingResult);
			redirectAttr.addFlashAttribute("student", student);

			return "redirect:/admin/manage_users/edit/Student/" + student.getId();
		}

		if (student.getId() == 0) {
			Semester s = adminService.retrieveSemesterById(192001);
			s.addStudent(student);
		}
		Student savedStudent = studentRepo.save(student);
		redirectAttr.addFlashAttribute("student", savedStudent);
		redirectAttr.addFlashAttribute("success", "Student Saved Successfully!");

		return "redirect:/admin/manage_users/edit/Student/" + student.getId();
	}

	@PostMapping("/manage_users/edit/faculty/save")
	public String UpdateFaculty(@Valid @ModelAttribute("faculty") Faculty faculty, BindingResult bindingResult,
			RedirectAttributes redirectAttr) {
		if (bindingResult.hasErrors()) {
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.faculty", bindingResult);
			redirectAttr.addFlashAttribute("faculty", faculty);

			return "redirect:/admin/manage_users/edit/Faculty/" + faculty.getId();
		}

		if (faculty.getId() == 0) {
			Department d = adminService.retrieveDepartmentById(faculty.getDepartment().getId());
			d.addFaculty(faculty);
		}
		Faculty savedFaculty = facultyRepo.save(faculty);
		redirectAttr.addFlashAttribute("faculty", savedFaculty);
		redirectAttr.addFlashAttribute("success", "Faculty Saved Successfully!");

		return "redirect:/admin/manage_users/edit/Faculty/" + faculty.getId();
	}

	@PostMapping("/manage_users/edit/admin/save")
	public String UpdateAdmin(@Valid @ModelAttribute("admin") Admin admin, BindingResult bindingResult,
			RedirectAttributes redirectAttr) {
		if (bindingResult.hasErrors()) {
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.admin", bindingResult);
			redirectAttr.addFlashAttribute("admin", admin);

			return "redirect:/admin/manage_users/edit/Admin/" + admin.getId();
		}

		Admin savedAdmin = adminRepo.save(admin);
		redirectAttr.addFlashAttribute("admin", savedAdmin);
		redirectAttr.addFlashAttribute("success", "Admin Saved Successfully!");

		return "redirect:/admin/manage_users/edit/Admin/" + admin.getId();
	}

	@RequestMapping("/manage_users/delete/{user}/{id}")
	public String DeleteUser(Model model, @PathVariable("user") String user, @PathVariable("id") Integer id,
			HttpSession session) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		if (user.equals("Admin")) {
			Admin a = adminRepo.findById(id).get();
			adminRepo.delete(a.getId());
		} else if (user.equals("Faculty")) {
			Faculty f = facultyRepo.findById(id).get();
			facultyRepo.delete(f.getId());
		} else if (user.equals("Student")) {
			Student s = studentRepo.findById(id).get();
			studentRepo.delete(s.getId());
		}

		return "redirect:/admin/manage_users";
	}

	@GetMapping("/manage_courses")
	public String ManageCourses(HttpSession session, Model model) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		List<Semester> semesters = adminService.retrieveAllSemesters();
		List<Department> departments = adminService.retrieveAllDepartments();
		List<Faculty> faculties = adminService.retrieveAllFaculties();
		List<Module> modules = adminService.retrieveAllModules();

		model.addAttribute("semesters", semesters);
		model.addAttribute("departments", departments);
		model.addAttribute("faculties", faculties);
		model.addAttribute("modules", modules);
		// List<String> courses = adminService.retrieveAllCourses();
		if (!model.containsAttribute("query"))
			model.addAttribute("query", new CourseQuery());
//        model.addAttribute("courses", courses);

		return "AdminManageCourses";
	}

	@PostMapping("/manage_courses/search")
	public String ManageCoursesSearch(@ModelAttribute("query") CourseQuery courseQuery, Model model,
			RedirectAttributes redirectAttr) {
		String modulecode = courseQuery.getModulecode();
		String modulename = courseQuery.getModulename();
		int semesterid = courseQuery.getSemesterId();
		int departmentid = courseQuery.getDepartmentId();
		int facultyid = courseQuery.getFacultyId();

		if (semesterid == -1 && departmentid == -1 && facultyid == -1 && modulecode.equals("")
				&& Strings.isBlank(modulename)) {
			List<Course> searchResult = adminService.retrieveAllCourses();
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} else if (semesterid != -1 && departmentid == -1 && facultyid == -1 && modulecode.equals("")
				&& Strings.isBlank(modulename)) {
			List<Course> searchResult = adminService.retrieveCourseBySemester(semesterid);
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} else if (semesterid == -1 && departmentid != -1 && facultyid == -1 && modulecode.equals("")
				&& Strings.isBlank(modulename)) {
			List<Course> searchResult = adminService.retrieveCourseByDepartment(departmentid);
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} else if (semesterid == -1 && departmentid == -1 && facultyid != -1 && modulecode.equals("")
				&& Strings.isBlank(modulename)) {
			List<Course> searchResult = adminService.retrieveCourseByFaculty(facultyid);
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} else if (semesterid == -1 && departmentid == -1 && facultyid == -1 && !modulecode.equals("")
				&& Strings.isBlank(modulename)) {
			List<Course> searchResult = adminService.retrieveCourseByModuleCode(modulecode);
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} else if (semesterid == -1 && departmentid == -1 && facultyid == -1 && modulecode.equals("")
				&& !Strings.isBlank(modulename)) {
			List<Course> searchResult = adminService.retrieveCoursesByPartialModulename(modulename);
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} 
		
		redirectAttr.addFlashAttribute("query", courseQuery);

		return "redirect:/admin/manage_courses";
	}

	@RequestMapping(value = { "/manage_courses/add", "/manage_courses/edit/{id}" }, method = RequestMethod.GET)
	public String ManageCoursesEdit(@PathVariable(name = "id", required = false) Integer id, HttpSession session,
			Model model) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		if (!model.containsAttribute("course")) {
			Course course = new Course();
			if (id != null)
				course = adminService.retrieveCourseById(id);
			model.addAttribute("course", course);
		}

		List<Department> departments = adminService.retrieveAllDepartments();
		List<Faculty> faculties = adminService.retrieveAllFaculties();
		List<Semester> semesters = adminService.retrieveAllSemesters();
		List<Module> modules = adminService.retrieveAllModules();
		model.addAttribute("departments", departments);
		model.addAttribute("faculties", faculties);
		model.addAttribute("semesters", semesters);
		model.addAttribute("modules", modules);
		return "AdminCourseForm";
	}

	@PostMapping("/manage_courses/edit/save")
	public String UpdateCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult,
			RedirectAttributes redirectAttr) {
		if (bindingResult.hasErrors()) {
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.course", bindingResult);
			redirectAttr.addFlashAttribute("course", course);

			return "redirect:/admin/manage_courses/edit/" + course.getId();
		}

		Course savedCourse = adminService.updateCourse(course);
		if (savedCourse == null) {
			redirectAttr.addFlashAttribute("Fail", "Failed to save. Try amending the Semester field.");
			redirectAttr.addFlashAttribute("course", course);

			return "redirect:/admin/manage_courses/edit/" + course.getId();
		}

		redirectAttr.addFlashAttribute("course", savedCourse);
		redirectAttr.addFlashAttribute("success", "Course Saved Successfully!");

		return "redirect:/admin/manage_courses/edit/" + savedCourse.getId();

	}

	@GetMapping("/manage_departments")
	public String ManageDepartment(HttpSession session, Model model) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		List<Department> departments = adminService.retrieveAllDepartments();
		if (!model.containsAttribute("query"))
			model.addAttribute("query", new DepartmentQuery());
		model.addAttribute("departments", departments);

		return "AdminManageDepartments";
	}

	@PostMapping("/manage_departments/search")
	public String ManageDepartmentsSearch(@ModelAttribute("query") DepartmentQuery departmentQuery, Model model,
			RedirectAttributes redirectAttr) {
		String departmentname = departmentQuery.getDepartmentname();

		if (departmentname.equals("")) {
			List<Department> searchResult = adminService.retrieveAllDepartments();
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} else {
			Department searchResult = adminService.retrieveDepartmentByName(departmentname);
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		}
		redirectAttr.addFlashAttribute("query", departmentQuery);

		return "redirect:/admin/manage_departments";
	}

	@RequestMapping(value = { "/manage_departments/add", "/manage_departments/edit/{id}" }, method = RequestMethod.GET)
	public String ManageDepartmentsEdit(@PathVariable(name = "id", required = false) Integer id, HttpSession session,
			Model model) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		if (!model.containsAttribute("department")) {
			Department department = new Department();
			if (id != null)
				department = adminService.retrieveDepartmentById(id);
			model.addAttribute("department", department);
		}

		return "AdminDepartmentForm";
	}

	@PostMapping("/manage_departments/edit/save")
	public String UpdateDepartment(@Valid @ModelAttribute("department") Department department,
			BindingResult bindingResult, RedirectAttributes redirectAttr) {
		if (bindingResult.hasErrors()) {
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.department", bindingResult);
			redirectAttr.addFlashAttribute("department", department);

			return "redirect:/admin/manage_departments/edit/" + department.getId();
		}

		Department savedDepartment = adminService.updateDepartment(department);
		redirectAttr.addFlashAttribute("department", savedDepartment);
		redirectAttr.addFlashAttribute("success", "Department Saved Successfully!");

		return "redirect:/admin/manage_departments/edit/" + savedDepartment.getId();
	}

	@GetMapping("/manage_modules")
	public String ManageModule(HttpSession session, Model model) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		List<Module> modules = adminService.retrieveAllModules();
		if (!model.containsAttribute("query"))
			model.addAttribute("query", new ModuleQuery());
		model.addAttribute("modules", modules);

		return "AdminManageModules";
	}

	@PostMapping("/manage_modules/search")
	public String ManageModulesSearch(@ModelAttribute("query") ModuleQuery moduleQuery, Model model,
			RedirectAttributes redirectAttr) {
		String modulecode = moduleQuery.getModulecode();
		String modulename = moduleQuery.getModulename();

		if (modulecode.equals("") && modulename.equals("")) {
			List<Module> searchResult = adminService.retrieveAllModules();
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} else if (!modulecode.equals("") && modulename.equals("")) {
			Module searchResult = adminService.retrieveModuleByModulecode(modulecode);
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} else if (modulecode.equals("") && !modulename.equals("")) {
			Module searchResult = adminService.retrieveModuleByModulename(modulename);
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} else {
		}
		redirectAttr.addFlashAttribute("query", moduleQuery);

		return "redirect:/admin/manage_modules";
	}

	@RequestMapping(value = { "/manage_modules/add", "/manage_modules/edit/{id}" }, method = RequestMethod.GET)
	public String ManageModuleEdit(@PathVariable(name = "id", required = false) Integer id, HttpSession session,
			Model model) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		if (!model.containsAttribute("module")) {
			Module module = new Module();
			if (id != null)
				module = adminService.retrieveModuleById(id);
			model.addAttribute("module", module);
		}

		return "AdminModuleForm";
	}

	@PostMapping("/manage_modules/edit/save")
	public String UpdateModule(@Valid @ModelAttribute("module") Module module, BindingResult bindingResult,
			RedirectAttributes redirectAttr) {
		if (bindingResult.hasErrors()) {
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.module", bindingResult);
			redirectAttr.addFlashAttribute("module", module);

			return "redirect:/admin/manage_modules/edit/" + module.getId();
		}

		Module savedModule = adminService.updateModule(module);
		redirectAttr.addFlashAttribute("module", savedModule);
		redirectAttr.addFlashAttribute("success", "Module Saved Successfully!");

		return "redirect:/admin/manage_modules/edit/" + savedModule.getId();
	}

	@RequestMapping("/manage_users/Student/{id}/view_enrollments")
	public String ManageEnrollments(@PathVariable("id") Integer id, HttpSession session, Model model) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		List<Semester> semesters = adminService.retrieveAllSemesters();
		List<Module> modules = adminService.retrieveAllModules();
		model.addAttribute("semesters", semesters);
		model.addAttribute("modules", modules);
		model.addAttribute("id", id);

		List<Enrollment> enrollments = adminService.retrieveEnrollmentsByStudentId(id);
		if (!model.containsAttribute("query"))
			model.addAttribute("query", new EnrollmentQuery());
		model.addAttribute("enrollments", enrollments);

		return "AdminManageEnrollments";
	}

	@PostMapping("/manage_enrollments/search/{id}")
	public String ManageModulesSearch(@PathVariable("id") Integer id,
			@ModelAttribute("query") EnrollmentQuery enrollmentQuery, Model model, RedirectAttributes redirectAttr) {
		int semesterid = enrollmentQuery.getSemesterId();
		int moduleid = enrollmentQuery.getModuleId();
		if (semesterid == -1 && moduleid == -1) {
			List<Enrollment> searchResult = adminService.retrieveAllEnrollmentsByStudentId(id);
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} else if (semesterid != -1 && moduleid == -1) {
			List<Enrollment> searchResult = adminService.retrieveAllEnrollmentsBySemesterByStudentId(semesterid,id);
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		} else if (semesterid == -1 && moduleid != -1) {
			List<Enrollment> searchResult = adminService.retrieveAllEnrollmentsByModuleByStudentId(moduleid,id);
			redirectAttr.addFlashAttribute("searchResult", searchResult);
		}

		redirectAttr.addFlashAttribute("query", enrollmentQuery);

		return "redirect:/admin/manage_users/Student/" + id + "/view_enrollments";
	}

	@RequestMapping(value = { "/manage_enrollments/add/{userid}", "/manage_enrollments/edit/{id}" }, method = RequestMethod.GET)
	public String ManageEnrollmentEdit(@PathVariable(name = "userid", required = false) Integer userid, @PathVariable(name = "id", required = false) Integer id, HttpSession session,
			Model model) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		List<Semester> semesters = adminService.retrieveAllSemesters();
		List<Course> courses = adminService.retrieveAllCourses();
		model.addAttribute("semesters", semesters);
		model.addAttribute("courses", courses);

		if (userid != null) {
			Enrollment enrollment = new Enrollment();
			Student s = adminService.retrieveStudentById(userid);
			enrollment.setStudent(s);
			model.addAttribute("enrollment", enrollment);
		}
		else 
		{
			if (!model.containsAttribute("enrollment")) {
				Enrollment enrollment = adminService.retrieveEnrollmentById(id);
				model.addAttribute("enrollment", enrollment);
			}
		}

		return "AdminEnrollmentForm";
	}

	@PostMapping("/manage_enrollments/edit/save")
	public String UpdateEnrollment(@Valid @ModelAttribute("enrollment") Enrollment enrollment,
			BindingResult bindingResult, RedirectAttributes redirectAttr) {
		if (bindingResult.hasErrors()) {
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.enrollment", bindingResult);
			redirectAttr.addFlashAttribute("enrollment", enrollment);

			return "redirect:/admin/manage_enrollments/edit/" + enrollment.getId();
		}
		
		Enrollment savedEnrollment = adminService.updateEnrollment(enrollment);
		if (savedEnrollment != null)
		{
			redirectAttr.addFlashAttribute("enrollment", savedEnrollment);
			redirectAttr.addFlashAttribute("success", "Enrollment Saved Successfully!");
			return "redirect:/admin/manage_enrollments/edit/" + savedEnrollment.getId();
		}
		else 
		{
			redirectAttr.addFlashAttribute("enrollment", enrollment);
			redirectAttr.addFlashAttribute("Fail", "Please check your entry.");
			return "redirect:/admin/manage_enrollments/edit/" + enrollment.getId();
		}
		

	}

	@RequestMapping("/manage_enrollments/delete/{id}")
	public String DeleteEnrollment(@PathVariable("id") Integer id, HttpSession session) {
		if (!(session.getAttribute("sessionUser") instanceof Admin))
			return "redirect:/home";

		Enrollment deleteEnrollment = adminService.retrieveEnrollmentById(id);
		adminService.deleteEnrollment(deleteEnrollment);
		return "redirect:/admin/manage_users/Student/" + deleteEnrollment.getStudent().getId() + "/view_enrollments";
	}

	@PostMapping("/update_admin")
	public String UpdateAdmin(@Valid @ModelAttribute("contactDetail") ContactDetail contactDetail,
			BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttr) {
		if (bindingResult.hasErrors()) {
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.contactDetail", bindingResult);
			redirectAttr.addFlashAttribute("contactDetail", contactDetail);

			return "redirect:/admin/profile";
		}

		String username = ((Admin) session.getAttribute("sessionUser")).getUsername();
		Admin savedFaculty = adminService.updateAdmin(username, contactDetail);
		session.setAttribute("sessionUser", savedFaculty);

		return "redirect:/admin/profile";
	}
	
	@PostMapping("/update_admin_password")
    public String UpdateAdminPassword(@Valid @ModelAttribute("passwordDetail") PasswordDetails passwordDetail,
            BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttr) {
        if (bindingResult.hasErrors()) {
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.passwordDetail", bindingResult);
            redirectAttr.addFlashAttribute("passwordError", "Failed to save password!");
            redirectAttr.addFlashAttribute("passwordDetail", passwordDetail);

            return "redirect:/admin/profile";
        }
        
        String adminPassword = ((Admin) session.getAttribute("sessionUser")).getPassword();
        
        if (!passwordDetail.getNewpassword().equals(passwordDetail.getCfmpassword()))
        {
        	redirectAttr.addFlashAttribute("passwordError", "Failed to save password!");
        	return "redirect:/admin/profile";
        }
        
        if(!BCrypt.checkpw(passwordDetail.getOldpassword(), adminPassword))
        {
        	redirectAttr.addFlashAttribute("passwordError", "Failed to save password!");
        	return "redirect:/admin/profile";
        }

        String username = ((Admin) session.getAttribute("sessionUser")).getUsername();
        Admin savedAdmin = adminService.updateAdminPassword(username, passwordDetail);
        session.setAttribute("sessionUser", savedAdmin);

        return "redirect:/admin/profile";
    }
	

	public static String[] splitWord(String s) {
		String[] split = s.split("\\s+");
		String firstword = split[0];
		String secondword = "";

		if (s.length() == 0) {
			firstword = "";
			secondword = "";
		} else if (s.length() == 1) {
			firstword = s;
			secondword = "";
		} else {
			for (int i = 1; i < split.length; i++) {
				if (i == 1) {
					secondword = split[i];
				} else {
					secondword = secondword + " " + split[i];
				}
			}
		}
		String[] str = new String[] { firstword, secondword };

		return str;
	}
}