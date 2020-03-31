package sg.edu.nus.studentmanagementsystem.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Admin;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Faculty;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Guest;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Student;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.User;
import sg.edu.nus.studentmanagementsystem.models.viewmodels.Login;
import sg.edu.nus.studentmanagementsystem.services.AdminService;
import sg.edu.nus.studentmanagementsystem.services.FacultyService;
import sg.edu.nus.studentmanagementsystem.services.StudentService;

@Controller
public class HomeController {
    private AdminService adminService;
    private FacultyService facultyService;
    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService sService) {
        this.studentService = sService;
    }

    @Autowired
    public void setFacultyService(FacultyService fService) {
        this.facultyService = fService;
    }

    @Autowired
    public void setAdminService(AdminService aService) {
        this.adminService = aService;
    }

    @GetMapping("/")
    public String Index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String Home(HttpSession session, Model model) {
        if (session.getAttribute("sessionUser") == null)
            session.setAttribute("sessionUser", new Guest());

        model.addAttribute("user", session.getAttribute("sessionUser"));

        return "Home";
    }

    @GetMapping("/login")
    public String Login(HttpSession session, Model model) {
        if (session.getAttribute("sessionUser") == null)
            session.setAttribute("sessionUser", new Guest());
        if (!(session.getAttribute("sessionUser") instanceof Guest))
            return "redirect:/home";

        ArrayList<String> domains = new ArrayList<String>();
        domains.add("Student");
        domains.add("Faculty");
        domains.add("Admin");
        if (!model.containsAttribute("login"))
            model.addAttribute("login", new Login());
        model.addAttribute("domains", domains);

        return "Login";
    }

    @GetMapping("/logout")
    public String Logout(HttpSession session) {
        session.invalidate();

        return "redirect:/home";
    }

    @GetMapping("/authenticate")
    public String Authenticate() {
        return "redirect:/home";
    }

    @PostMapping("/authenticate")
    public String Authenticate(@Valid @ModelAttribute("login") Login Login, BindingResult bindingResult,
            HttpSession session, RedirectAttributes redirectAttr) {
        if (bindingResult.hasErrors()) {
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.login", bindingResult);
            redirectAttr.addFlashAttribute("login", Login);

            return "redirect:/login";
        }

        boolean isValidUser = false;
        User user = null;
        if (Login.getDomain().equals("Student"))
            user = studentService.findByUsername(Login.getUsername().toUpperCase());
        else if (Login.getDomain().equals("Faculty"))
            user = facultyService.findByUsername(Login.getUsername().toUpperCase());
        else if (Login.getDomain().equals("Admin"))
            user = adminService.findByUsername(Login.getUsername().toUpperCase());

        if (user != null) {
            isValidUser = BCrypt.checkpw(Login.getPassword(), user.getPassword());
            if (isValidUser) {
                session.setAttribute("sessionUser", user);

                return "redirect:/home";
            }
        }

        redirectAttr.addFlashAttribute("userNotFound", true);
        Login.setPassword("");
        redirectAttr.addFlashAttribute("login", Login);

        return "redirect:/login";
    }

    @GetMapping("/portal")
    public String Portal(HttpSession session) {
        if (session.getAttribute("sessionUser") == null || session.getAttribute("sessionUser") instanceof Guest)
            return "redirect:/home";

        if (session.getAttribute("sessionUser") instanceof Student)
            return "redirect:/student/";
        else if (session.getAttribute("sessionUser") instanceof Faculty)
            return "redirect:/faculty/";
        else if (session.getAttribute("sessionUser") instanceof Admin)
            return "redirect:/admin/";

        return "redirect:/home";
    }
}