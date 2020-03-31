package sg.edu.nus.studentmanagementsystem.models.viewmodels;

public class SemesterAndCourse {
    private String semesterName;
    private int courseId;

    public SemesterAndCourse() {
    }

    public SemesterAndCourse(String semesterName, int courseId) {
        this.semesterName = semesterName;
        this.courseId = courseId;
    }

	public String getSemesterName() {
		return semesterName;
	}

	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
}