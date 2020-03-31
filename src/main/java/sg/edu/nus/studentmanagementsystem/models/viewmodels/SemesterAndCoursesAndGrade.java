package sg.edu.nus.studentmanagementsystem.models.viewmodels;

public class SemesterAndCoursesAndGrade {
    private String semesterName;
    private int[] courseIds;
    private String grade;
    
    
	public SemesterAndCoursesAndGrade() {}
	public SemesterAndCoursesAndGrade(String semesterName, int[] courseIds, String grade) {
		super();
		this.semesterName = semesterName;
		this.courseIds = courseIds;
		this.grade = grade;
	}
	public String getSemesterName() {
		return semesterName;
	}
	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}
	public int[] getCourseIds() {
		return courseIds;
	}
	public void setCourseIds(int[] courseIds) {
		this.courseIds = courseIds;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
    
    
}