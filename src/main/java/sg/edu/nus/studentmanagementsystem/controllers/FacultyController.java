package sg.edu.nus.studentmanagementsystem.controllers;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import sg.edu.nus.studentmanagementsystem.SmsApplication;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Course;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Enrollment;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Faculty;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Semester;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Student;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.ContactDetail;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.CourseAnnouncement;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.Email;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.PasswordDetails;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.SemesterAndCourse;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.SemesterAndCoursesAndGrade;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.StudentAndCourse;
import sg.edu.nus.studentmanagementsystem.services.EmailServiceImpl;
import sg.edu.nus.studentmanagementsystem.services.FacultyService;
import sg.edu.nus.studentmanagementsystem.services.PagingServiceImpl;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
    private static final Logger log = LoggerFactory.getLogger(SmsApplication.class);

    private FacultyService facultyService;

    private EmailServiceImpl emailService;

    private PagingServiceImpl pagingService;

    @Autowired
    public void setStudentService(FacultyService fService) {
        this.facultyService = fService;
    }

    @Autowired
    public void setEmailServiceImpl(EmailServiceImpl emailServiceImpl) {
        this.emailService = emailServiceImpl;
    }

    @Autowired
    public void setPagingServiceImpl(PagingServiceImpl pagingServiceImpl) {
        this.pagingService = pagingServiceImpl;
    }

    @GetMapping("/")
    public String Index() {
        return "redirect:/faculty/profile";
    }

    @GetMapping("/main")
    public String Main(HttpSession session, Model model) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        model.addAttribute("faculty", session.getAttribute("sessionUser"));

        return "FacultyMain";
    }

    @GetMapping("/profile")
    public String Profile(HttpSession session, Model model) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        Faculty faculty = facultyService.findByUsername(username);
        model.addAttribute("faculty", faculty);
        model.addAttribute("contactDetail",
                new ContactDetail(faculty.getAddress(), faculty.getContact(), faculty.getEmail()));
        model.addAttribute("passwordDetail", new PasswordDetails());
        return "FacultyProfile";
    }

    @GetMapping("/master_list")
    public String MasterList(HttpSession session, Model model,
            @RequestParam(name = "val", required = false, defaultValue = "0") Integer currentPage,
            @ModelAttribute("searchStr") String searchStr) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        List<Enrollment> enrollments = new ArrayList<Enrollment>();
        if (!model.containsAttribute("export")) {
            enrollments = facultyService.retrieveMasterList();
            model.addAttribute("searchStr", "");
            model.addAttribute("export", null);
        } else if (model.containsAttribute("export")) {
            enrollments = facultyService.retrieveSearchResults("%" + searchStr.toLowerCase() + "%");
            EnrollmentCsv(enrollments);
        }

        // model.addAttribute("enrollments", enrollments);
        HashMap<String, Object> p = pagingService.Pagination(currentPage, 8, enrollments);
        model.addAttribute("pages", p.get("pages"));
        log.info(p.get("pages").toString());
        model.addAttribute("enrollments", p.get("list"));
        model.addAttribute("totalPage", p.get("totalPage"));

        return "FacultyMasterList";
    }

    @RequestMapping(value = "/master_list_export", method = { RequestMethod.GET, RequestMethod.POST })
    public String MasterListExport(@RequestParam(name = "val", required = false, defaultValue = "") String searchStr,
            HttpSession session, Model model, RedirectAttributes ra) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        ra.addFlashAttribute("export", "Export Successful!");
        ra.addFlashAttribute("searchStr", searchStr);

        return "redirect:/faculty/master_list";
    }

    @PostMapping("/master_list/search_results")
    public String SearchResults(@RequestParam("searchStr") String searchStr, HttpSession session, Model model) {
        if (searchStr == null || searchStr.trim().isEmpty())
            return "redirect:/faculty/master_list";

        List<Enrollment> enrollments = facultyService.retrieveSearchResults("%" + searchStr.toLowerCase() + "%");
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("searchStr", searchStr);
        return "FacultyMasterList";
    }

    @GetMapping("/course_list")
    public String CourseList(HttpSession session, Model model,
            @RequestParam(name = "val", required = false, defaultValue = "0") Integer currentPage) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        List<Course> courses = facultyService.retrieveFacultyCourses(username);

        HashMap<String, Object> p = pagingService.Pagination(currentPage, 2, courses);
        model.addAttribute("pages", p.get("pages"));
        model.addAttribute("courses", p.get("list"));
        model.addAttribute("totalPage", p.get("totalPage"));

        // model.addAttribute("courses", courses);

        return "FacultyCourseList";
    }

    @GetMapping("/course_list/student_list/{courseId}")
    public String CourseStudentList(@PathVariable("courseId") int courseId, HttpSession session, Model model) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        Course course = facultyService.retrieveCourse(courseId);
        List<Student> students = facultyService.retrieveCourseStudents(course.getId());
        model.addAttribute("selectedCourse", course);
        model.addAttribute("students", students);

        return "FacultyCourseStudentList";
    }

    @GetMapping("/view_student_particulars/{username}")
    public String ViewStudentParticulars(@PathVariable("username") String username, HttpSession session, Model model) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        Student student = facultyService.retrieveStudentParticulars(username);
        model.addAttribute("student", student);

        return "FacultyViewStudent";
    }

    @GetMapping("/student_list")
    public String StudentList(HttpSession session, Model model,
            @RequestParam(name = "val", required = false, defaultValue = "0") Integer currentPage) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        List<Semester> semesters = facultyService.retrieveAllSemesters();
        List<Student> students = facultyService.retrieveAllStudentsUnderFaculty(username);
        List<StudentAndCourse> studentAndCourses = new ArrayList<StudentAndCourse>();
        for (Student s : students) {
            StudentAndCourse studentAndCourse = new StudentAndCourse();
            studentAndCourse.setUsername(s.getUsername());
            studentAndCourse.setFirstName(s.getFirstName());
            studentAndCourse.setLastName(s.getLastName());
            studentAndCourse.setMajor(s.getMajor());

            List<Course> courses = facultyService.retrieveCoursesByStudentAndFaculty(s.getUsername(), username);
            if (courses != null) {
                for (Course c : courses) {
                    String courseInfo = String
                            .format(c.getModule().getModulecode() + " " + c.getModule().getModulename());
                    studentAndCourse.addCourseInfo(courseInfo);
                }
            }
            studentAndCourses.add(studentAndCourse);
        }
        HashMap<String, Object> p = pagingService.Pagination(currentPage, 3, studentAndCourses);
        model.addAttribute("pages", p.get("pages"));
        log.info(p.get("pages").toString());
        model.addAttribute("studentAndCourses", p.get("list"));
        model.addAttribute("totalPage", p.get("totalPage"));

        model.addAttribute("semester", new Semester());
        model.addAttribute("semesters", semesters);
        // model.addAttribute("studentAndCourses", studentAndCourses);

        return "FacultyStudentList";
    }

    @PostMapping("/student_list")
    public String SemesterStudentList(@ModelAttribute("semester") Semester semester, HttpSession session, Model model,
            @RequestParam(name = "val", required = false, defaultValue = "0") Integer currentPage) {
        if (semester == null || Strings.isBlank(semester.getName()))
            return "redirect:/faculty/student_list";

        String semesterName = semester.getName();
        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        List<Semester> semesters = facultyService.retrieveAllSemesters();
        List<Student> students = facultyService.retrieveAllStudentsUnderFacultyInSemester(username, semesterName);
        List<StudentAndCourse> studentAndCourses = new ArrayList<StudentAndCourse>();
        for (Student s : students) {
            StudentAndCourse studentAndCourse = new StudentAndCourse();
            studentAndCourse.setUsername(s.getUsername());
            studentAndCourse.setFirstName(s.getFirstName());
            studentAndCourse.setLastName(s.getLastName());
            studentAndCourse.setMajor(s.getMajor());

            List<Course> courses = facultyService.retrieveCoursesByStudentAndFacultyAndSemester(s.getUsername(),
                    username, semesterName);
            if (courses != null) {
                for (Course c : courses) {
                    String courseInfo = String
                            .format(c.getModule().getModulecode() + " " + c.getModule().getModulecode());
                    studentAndCourse.addCourseInfo(courseInfo);
                }
            }
            studentAndCourses.add(studentAndCourse);
        }

        HashMap<String, Object> p = pagingService.Pagination(currentPage, 3, studentAndCourses);
        model.addAttribute("pages", p.get("pages"));
        log.info(p.get("pages").toString());
        model.addAttribute("studentAndCourses", p.get("list"));
        model.addAttribute("totalPage", p.get("totalPage"));

        model.addAttribute("semesters", semesters);
        // model.addAttribute("studentAndCourses", studentAndCourses);

        return "FacultyStudentList";
    }

    @GetMapping("/score_cards")
    public String ScoreCards(HttpSession session, Model model) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        SemesterAndCourse semesterAndCourse = new SemesterAndCourse();
        List<Semester> semesters = facultyService.retrieveAllSemesters();
        List<Course> courses = facultyService.retrieveFacultyCourses(username);
        model.addAttribute("semesterAndCourse", semesterAndCourse);
        model.addAttribute("semesters", semesters);
        model.addAttribute("courses", courses);

        return "FacultyScoreCards";
    }

    @RequestMapping(value = "/score_cards/display_scores", method = { RequestMethod.GET, RequestMethod.POST })
    public String DisplayStudentsScores(@ModelAttribute("semesterAndCourse") SemesterAndCourse semesterAndCourse,
            HttpSession session, Model model) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        String semesterName = semesterAndCourse.getSemesterName();
        int courseId = semesterAndCourse.getCourseId();
        List<Enrollment> enrollments = facultyService.retrieveEnrollments(semesterName, courseId, username);
        List<Semester> semesters = facultyService.retrieveAllSemesters();
        List<Course> courses = facultyService.retrieveFacultyCourses(username);
        model.addAttribute("studentEnrollments", enrollments);
        model.addAttribute("semesters", semesters);
        model.addAttribute("courses", courses);

        return "FacultyScoreCards";
    }

    @GetMapping("/score_cards/edit/{enrollmentId}")
    public String EditScoreCard(@PathVariable("enrollmentId") Integer id, HttpSession session, Model model) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        Enrollment enrollment = facultyService.retrieveEnrollment(id);
        model.addAttribute("enrollmentToScore", enrollment);

        return "FacultyStudentScoreCard";
    }

    @RequestMapping(params = "save", value = "/score_cards/save/{enrollmentId}", method = RequestMethod.POST)
    public String SaveScoreCard(@ModelAttribute("enrollmentToScore") Enrollment enrollment, Model model,
            RedirectAttributes redirectAttr) {
        Enrollment savedEnrollment = facultyService.updateEnrollment(enrollment);
        SemesterAndCourse semesterAndCourse = new SemesterAndCourse(savedEnrollment.getSemester().getName(),
                savedEnrollment.getCourse().getId());
        redirectAttr.addFlashAttribute("semesterAndCourse", semesterAndCourse);

        return "redirect:/faculty/score_cards/display_scores";
    }

    @RequestMapping(params = "cancel", value = "/score_cards/save/{enrollmentId}", method = RequestMethod.POST)
    public String cancelEditScore(Model model, @ModelAttribute("enrollmentToScore") Enrollment enrollment,
            RedirectAttributes redirectAttr) {
        SemesterAndCourse semesterAndCourse = new SemesterAndCourse(enrollment.getSemester().getName(),
                enrollment.getCourse().getId());
        redirectAttr.addFlashAttribute("semesterAndCourse", semesterAndCourse);

        return "redirect:/faculty/score_cards/display_scores";
    }

    @GetMapping("/cgpa_report")
    public String CgpaReport(HttpSession session, Model model) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        List<Semester> semesters = facultyService.retrieveAllSemesters();
        List<Student> students = facultyService.retrieveAllStudentsUnderFaculty(username);
        model.addAttribute("semester", new Semester());
        model.addAttribute("semesters", semesters);
        model.addAttribute("students", students);

        return "FacultyCgpaReport";
    }

    @PostMapping("/cgpa_report")
    public String SemesterCgpaReport(@ModelAttribute("semester") Semester semester, HttpSession session, Model model) {
        if (semester == null || Strings.isBlank(semester.getName()))
            return "redirect:/faculty/cgpa_report";

        String semesterName = semester.getName();
        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        List<Semester> semesters = facultyService.retrieveAllSemesters();
        List<Student> students = facultyService.retrieveAllStudentsUnderFacultyInSemester(username, semesterName);
        model.addAttribute("semesters", semesters);
        model.addAttribute("students", students);

        return "FacultyCgpaReport";
    }
    
    @GetMapping("/grade_report")
    public String gradeReport(HttpSession session, Model model) {
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";

        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        
        List<Semester> semesters = facultyService.retrieveAllSemesters();
        List<Course> courses = facultyService.retrieveFacultyCourses(username);
        List<String> grades = new ArrayList<>(Arrays.asList("A+","A","A-","B+","B","B-","C+","C","D+","D","F"));

        model.addAttribute("semesters", semesters);
        model.addAttribute("courses", courses);
        model.addAttribute("grades", grades);
        
        
        SemesterAndCoursesAndGrade gradeQuery = new SemesterAndCoursesAndGrade();
        model.addAttribute("gradeQuery", gradeQuery);
        
        return "FacultyGradeReport";
    }
    
    @PostMapping(value = "/grade_report")
    public String displayGradeReport(@ModelAttribute("gradeQuery") SemesterAndCoursesAndGrade semesterAndCoursesAndGrade,
    		@RequestParam(value = "coursesChosen" , required = false) int[] coursesChosen,
            HttpSession session, Model model, RedirectAttributes ra) {
    	
        if (!(session.getAttribute("sessionUser") instanceof Faculty))
            return "redirect:/home";
        
        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        String semesterName = semesterAndCoursesAndGrade.getSemesterName();
        String grade = semesterAndCoursesAndGrade.getGrade();
        
        
        List<Enrollment> allEnrollments = new ArrayList<Enrollment>();
        
        // 8 cases:
        if (semesterName.equals("") && coursesChosen == null && grade.equals("")) {
        	allEnrollments = facultyService.retrieveEnrollmentsForGradeReport(username);
        }
        
        else if (semesterName.equals("") && coursesChosen == null && (!grade.equals(""))) {
        	allEnrollments = facultyService.retrieveEnrollmentsForGradeReport(username, grade);
        }
        
        else if (semesterName.equals("") && coursesChosen != null && grade.equals("")) {
        	for (Integer courseId:coursesChosen) {
        		List<Enrollment> enrols = facultyService.retrieveEnrollmentsForGradeReport(username, courseId);
        		allEnrollments.addAll(enrols);
        	}
        }
        
        else if (semesterName.equals("") && coursesChosen != null && (!grade.equals(""))) {
        	for (Integer courseId:coursesChosen) {
        		List<Enrollment> enrols = facultyService.retrieveEnrollmentsForGradeReport(username, courseId, grade);
        		allEnrollments.addAll(enrols);
        	}
        }
        
        else if ( (!semesterName.equals("")) && coursesChosen == null && grade.equals("")) {
        	allEnrollments = facultyService.retrieveEnrollmentsForGradeReportWithSemester(username, semesterName);
        }
        
        else if ( (!semesterName.equals("")) && coursesChosen == null && (!grade.equals(""))) {
        	allEnrollments = facultyService.retrieveEnrollmentsForGradeReportWithSemester(username, grade, semesterName);
        }
        
        else if ( (!semesterName.equals("")) && coursesChosen != null && grade.equals("")) {
        	for (Integer courseId:coursesChosen) {
        		List<Enrollment> enrols = facultyService.retrieveEnrollmentsForGradeReportWithSemester(username, courseId, semesterName);
        		allEnrollments.addAll(enrols);
        	}
        }
        
        else if ( (!semesterName.equals("")) && coursesChosen != null && (!grade.equals(""))) {
        	for (Integer courseId:coursesChosen) {
        		List<Enrollment> enrols = facultyService.retrieveEnrollmentsForGradeReportWithSemester(username, courseId, grade, semesterName);
        		allEnrollments.addAll(enrols);
        	}
        }
        
        ra.addFlashAttribute("studentEnrollments", allEnrollments);
        
        return "redirect:/faculty/grade_report";
    }
    
    
    
    
    
    
    
    

    @RequestMapping(value = "/course_announcements", method = { RequestMethod.GET, RequestMethod.POST })
    public String CreateEmailAnnouncement(
            @RequestParam(name = "error", required = false, defaultValue = "") String errorMsg,
            @ModelAttribute(name = "courseAnnouncement") CourseAnnouncement courseAnnouncementEntered,
            HttpSession session, Model model) {

        String currentSemester = "AY1920 Semester 1";

        if (courseAnnouncementEntered != null) {
            courseAnnouncementEntered.setSemesterName(currentSemester);
            model.addAttribute("courseAnnouncement", courseAnnouncementEntered);
        } else {
            CourseAnnouncement courseAnnouncement = new CourseAnnouncement();
            courseAnnouncement.setSemesterName(currentSemester);
            model.addAttribute("courseAnnouncement", courseAnnouncement);
        }

        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        List<Course> courses = facultyService.retrieveCoursesByFacultyAndSemester(username, currentSemester);
        model.addAttribute("courses", courses);

        if (errorMsg != null)
            model.addAttribute("error", errorMsg);
        else
            model.addAttribute("error", "");

        return "FacultyEmailAnnouncement";
    }

    @RequestMapping(params = "submit", value = "/course_announcements/submit", method = RequestMethod.POST)
    public String submitAnnouncement(@ModelAttribute("courseAnnouncement") CourseAnnouncement courseAnnouncement,
            RedirectAttributes ra, HttpSession session, Model model) {

        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        Faculty faculty = facultyService.findByUsername(username);
        String semesterName = courseAnnouncement.getSemesterName();
        //
        log.info(semesterName);
        String modulecode = courseAnnouncement.getCoursecode();
        //
        log.info(modulecode);
        String announcement = courseAnnouncement.getAnnouncement();
        //
        log.info(announcement);

        List<Student> studentRecipients = facultyService.retrieveAllStudentsUnderFacultyInSemesterInCourse(username,
                semesterName, modulecode);

        String recipientEmailList = FacultyService.generateRecipientEmailList(studentRecipients);

        if (Strings.isEmpty(recipientEmailList)) {
            ra.addFlashAttribute("courseAnnouncement", courseAnnouncement);
            ra.addAttribute("error", "Failed to send announcement because no course/student is selected!");
            log.info("No recipients selected");
            return "redirect:/faculty/course_announcements";
        } else if (Strings.isEmpty(announcement)) {
            ra.addFlashAttribute("courseAnnouncement", courseAnnouncement);
            ra.addAttribute("error", "Empty announcement cannot be sent!");
            log.info("Empty Announcement");
            return "redirect:/faculty/course_announcements";
        }

        Email email = new Email();
        email.setSubject(String.format("[%s]Course Announcement", modulecode));
        email.setEmailFrom(faculty.getEmail());
        email.setToEmails(recipientEmailList);
        email.setContent(announcement);
        model.addAttribute(email);

        log.info("Subject: " + email.getSubject());
        log.info("From: " + email.getEmailFrom());
        log.info("To: " + email.getToEmails());
        log.info("Content: " + email.getContent());
        emailService.manageSendEmail(email);

        return "FacultyEmailStatusSuccess";
    }

    @PostMapping("/update_faculty")
    public String UpdateFaculty(@Valid @ModelAttribute("contactDetail") ContactDetail contactDetail,
            BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttr) {
        if (bindingResult.hasErrors()) {
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.contactDetail", bindingResult);
            redirectAttr.addFlashAttribute("contactDetail", contactDetail);

            return "redirect:/faculty/profile";
        }

        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        Faculty savedFaculty = facultyService.updateFaculty(username, contactDetail);
        session.setAttribute("sessionUser", savedFaculty);

        return "redirect:/faculty/profile";
    }
    
    @PostMapping("/update_faculty_password")
    public String UpdateFaculty(@Valid @ModelAttribute("passwordDetail") PasswordDetails passwordDetail,
            BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttr) {
        if (bindingResult.hasErrors()) {
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.contactDetail", bindingResult);
            redirectAttr.addFlashAttribute("passwordError", "Failed to save password!");
            redirectAttr.addFlashAttribute("passwordDetail", passwordDetail);
            return "redirect:/faculty/profile";
        }
        
        String facultyPassword = ((Faculty) session.getAttribute("sessionUser")).getPassword();
        
        if (!passwordDetail.getNewpassword().equals(passwordDetail.getCfmpassword()))
        {
        	redirectAttr.addFlashAttribute("passwordError", "Failed to save password!");
        	return "redirect:/faculty/profile";
        }
        
        if(!BCrypt.checkpw(passwordDetail.getOldpassword(), facultyPassword))
        {
        	redirectAttr.addFlashAttribute("passwordError", "Failed to save password!");
        	return "redirect:/faculty/profile";
        }

        String username = ((Faculty) session.getAttribute("sessionUser")).getUsername();
        Faculty savedFaculty = facultyService.updateFacultyPassword(username, passwordDetail);
        session.setAttribute("sessionUser", savedFaculty);

        return "redirect:/faculty/profile";
    }

    public void EnrollmentCsv(List<Enrollment> enrollments) {
        FileWriter fileWriter = null;
        String filePath = "enrollment.csv";
        try {
            fileWriter = new FileWriter(filePath);
            fileWriter.write("Semester" + "," + "Student Name" + "," + "Module Code" + "," + "Module Name" + ","
                    + "Enrollment Status" + "," + "Grade\n");
            for (Enrollment enrollment : enrollments) {
                fileWriter.write(enrollment.toCsv());
                fileWriter.append("\n");
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