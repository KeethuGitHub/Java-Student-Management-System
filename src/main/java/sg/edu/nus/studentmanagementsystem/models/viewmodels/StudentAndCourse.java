package sg.edu.nus.studentmanagementsystem.models.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class StudentAndCourse {
    private String username;
    private String firstName;
    private String lastName;
	private String major;
	
    private List<String> courseInfo = new ArrayList<String>(); //coursecode and course name

    public StudentAndCourse() {
    }

    public StudentAndCourse(String username, String firstName, String lastName, String major, List<String> courseInfo) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.courseInfo = courseInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public List<String> getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(List<String> courseInfo) {
        this.courseInfo = courseInfo;
    }

    public void addCourseInfo(String courseInfo) {
    	this.courseInfo.add(courseInfo);
    }
}