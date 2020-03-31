package sg.edu.nus.studentmanagementsystem.models.entitymodels;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Faculty extends User {
    private static int numOfFaculty = 0;

    @OneToMany(targetEntity = Course.class, mappedBy = "faculty", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Course> courses = new ArrayList<Course>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Faculty() {
    }

    public Faculty(String username, String password, String firstName, String lastName, String address, String contact,
            String dateOfBirth, String email, char gender, Department department, List<Course> courses) {
        super(username, password, firstName, lastName, address, contact, dateOfBirth, email, gender);
        this.department = department;
        this.courses = courses;
    }

    public static int getNumOfFaculty() {
        return numOfFaculty;
    }

    public static void setNumOfFaculty(int numOfFaculty) {
        Faculty.numOfFaculty = numOfFaculty;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void addCourse(Course course) {
        courses.add(course);
        course.setFaculty(this);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        course.setFaculty(null);
    }
}