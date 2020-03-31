package sg.edu.nus.studentmanagementsystem.models.viewmodels;

public class CourseAnnouncement {
	private String coursecode;
	private String semesterName;
    private String announcement;

    public CourseAnnouncement() {
    }

    public CourseAnnouncement(String coursecode, String semesterName, String announcement) {
        this.coursecode = coursecode;
        this.semesterName = semesterName;
        this.announcement = announcement;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }
}