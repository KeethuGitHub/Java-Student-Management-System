package sg.edu.nus.studentmanagementsystem.controllers;

import java.util.HashMap;
import java.io.FileWriter;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Course;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Student;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.ContactDetail;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.CourseGrade;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.EnrollmentRecord;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.GradeBook;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.PasswordDetails;
import sg.edu.nus.studentmanagementsystem.services.PagingServiceImpl;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.SemesterGrade;
import sg.edu.nus.studentmanagementsystem.services.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;
    private PagingServiceImpl pagingService;
	
	@Autowired
	public void setPagingService(PagingServiceImpl pagingService) {
		this.pagingService = pagingService;
	}
	
    @Autowired
    public void setStudentService(StudentService sService) {
        this.studentService = sService;
    }

    @GetMapping("/")
    public String Index() {
        return "redirect:/student/profile";
    }

    @GetMapping("/main")
    public String Main(HttpSession session, Model model) {
        if (!(session.getAttribute("sessionUser") instanceof Student))
            return "redirect:/home";

        model.addAttribute("student", session.getAttribute("sessionUser"));

        return "StudentMain";
    }

    @GetMapping("/profile")
    public String Profile(HttpSession session, Model model) {
        if (!(session.getAttribute("sessionUser") instanceof Student))
            return "redirect:/home";

        String username = ((Student) session.getAttribute("sessionUser")).getUsername();
        Student student = studentService.findByUsername(username);
        model.addAttribute("student", student);
        model.addAttribute("contactDetail",
                new ContactDetail(student.getAddress(), student.getContact(), student.getEmail()));
        model.addAttribute("passwordDetail", new PasswordDetails());

        return "StudentProfile";
    }

    @GetMapping("/grade_book")
    public String DegreeAudit(HttpSession session, Model model) {
        if (!(session.getAttribute("sessionUser") instanceof Student))
            return "redirect:/home";

        String username = ((Student) session.getAttribute("sessionUser")).getUsername();
        GradeBook gradeBook = studentService.retrieveGradeBook(username);
        model.addAttribute("gradeBook", gradeBook);
        if (model.containsAttribute("export")) {
            GradeBookCsv(gradeBook);
        }
        return "StudentGradeBook";
    }

    @RequestMapping(value = "/gradebook_export", method = { RequestMethod.GET, RequestMethod.POST })
    public String GradebookExport(HttpSession session, RedirectAttributes ra) {
        if (!(session.getAttribute("sessionUser") instanceof Student))
            return "redirect:/home";

        ra.addFlashAttribute("export", "Success");

        return "redirect:/student/grade_book";
    }

    @GetMapping("/available_courses")
    public String AvailableCourses(HttpSession session, Model model, @RequestParam(name="val",required=false,defaultValue="0") Integer currentPage) {
        if (!(session.getAttribute("sessionUser") instanceof Student))
            return "redirect:/home";

        String username = ((Student) session.getAttribute("sessionUser")).getUsername();
        List<Course> availableCourses = studentService.retrieveAvailableCourses(username,192002); //updated to only retrieve the courses for the current sem
        HashMap<String,Object> p = pagingService.Pagination(currentPage,5,availableCourses);
		model.addAttribute("pages", p.get("pages"));
		model.addAttribute("availableCourses", p.get("list"));
		model.addAttribute("totalPage",p.get("totalPage"));
        return "StudentAvailableCourses";
    }

    @GetMapping("/add/{semesterId}/{courseId}")
    public String AddCourse(@PathVariable("semesterId") String semesterId, @PathVariable("courseId") int courseId,
            HttpSession session) {
        String username = ((Student) session.getAttribute("sessionUser")).getUsername();
        studentService.enrollCourse(username, Integer.parseInt(semesterId), courseId);

        return "redirect:/student/available_courses";
    }

    @GetMapping("/enrolled_courses")
    public String EnrolledCourses(HttpSession session, Model model, @RequestParam(name="val",required=false,defaultValue="0") Integer currentPage) {
        if (!(session.getAttribute("sessionUser") instanceof Student))
            return "redirect:/home";

        String username = ((Student) session.getAttribute("sessionUser")).getUsername();
        List<EnrollmentRecord> enrollmentRecords = studentService.retrieveEnrolledCourses(username);
        model.addAttribute("semesters", ((Student) session.getAttribute("sessionUser")).getSemesters());
        HashMap<String,Object> p = pagingService.Pagination(currentPage,4,enrollmentRecords);
		model.addAttribute("pages", p.get("pages"));
		model.addAttribute("enrollmentRecords", p.get("list"));
		model.addAttribute("totalPage",p.get("totalPage"));
        //model.addAttribute("enrollmentRecords", enrollmentRecords);
        return "StudentEnrolledCourses";
    }

    @GetMapping("/remove/{semesterId}/{courseId}")
    public String RemoveCourse(@PathVariable("semesterId") String semesterId, @PathVariable("courseId") int courseId,
            HttpSession session) {
        String username = ((Student) session.getAttribute("sessionUser")).getUsername();
        studentService.unenrollCourse(username, Integer.parseInt(semesterId), courseId);

        return "redirect:/student/enrolled_courses";
    }

    @PostMapping("/update_student")
    public String UpdateStudent(@Valid @ModelAttribute("contactDetail") ContactDetail contactDetail,
            BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttr) {
        if (bindingResult.hasErrors()) {
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.contactDetail", bindingResult);
            redirectAttr.addFlashAttribute("contactDetail", contactDetail);

            return "redirect:/student/profile";
        }

        String username = ((Student) session.getAttribute("sessionUser")).getUsername();
        Student savedStudent = studentService.updateStudent(username, contactDetail);
        session.setAttribute("sessionUser", savedStudent);

        return "redirect:/student/profile";
    }
    
    @PostMapping("/update_student_password")
    public String UpdateStudentPassword(@Valid @ModelAttribute("passwordDetail") PasswordDetails passwordDetail,
            BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttr) {
        if (bindingResult.hasErrors()) {
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.passwordDetail", bindingResult);
            redirectAttr.addFlashAttribute("passwordError", "Failed to save password!");
            redirectAttr.addFlashAttribute("passwordDetail", passwordDetail);

            return "redirect:/student/profile";
        }
        
        String studentPassword = ((Student) session.getAttribute("sessionUser")).getPassword();
        
        if (!passwordDetail.getNewpassword().equals(passwordDetail.getCfmpassword()))
        {
        	redirectAttr.addFlashAttribute("passwordError", "Failed to save password!");
        	return "redirect:/student/profile";
        }
        
        if(!BCrypt.checkpw(passwordDetail.getOldpassword(), studentPassword))
        {
        	redirectAttr.addFlashAttribute("passwordError", "Failed to save password!");
        	return "redirect:/student/profile";
        }

        String username = ((Student) session.getAttribute("sessionUser")).getUsername();
        Student savedStudent = studentService.updateStudentPassword(username, passwordDetail);
        session.setAttribute("sessionUser", savedStudent);

        return "redirect:/student/profile";
    }

    public void GradeBookCsv(GradeBook gb) {
        FileWriter fileWriter = null;
        String filePath = "gradebook.csv";
        try {
            fileWriter = new FileWriter(filePath);
            fileWriter.write("Major: " + gb.getMajor() + "\n");
            fileWriter.write("Total Graded MCs: " + gb.getGradedCredits() + "\n");
            fileWriter.write("Major: " + gb.getCgpa() + "\n");
            fileWriter.write("Degree Classification" + gb.getDegreeClass() + "\n");
            for (SemesterGrade sg : gb.getSemesterGrades()) {
                fileWriter.write(sg.getSemesterName());
                fileWriter.append("\n");
                fileWriter.write("Course Code,Course Name,Modular Credits,Grade\n");
                for (CourseGrade cg : sg.getCourseGrades()) {
                    fileWriter.write(cg.getCourseCode() + ",");
                    fileWriter.write(cg.getCourseName() + ",");
                    fileWriter.write(cg.getCourseCredits() + ",");
                    fileWriter.write(cg.getCourseGrade() + "\n");
                }
                fileWriter.write(",,,Semester GPA: " + String.format("%.2f", sg.getSemesterGPA()) + "\n");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}