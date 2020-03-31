package sg.edu.nus.studentmanagementsystem.models.viewmodels;

public class CourseQuery {
	
	private String modulecode;
	private String modulename;
	private int semesterId;  
	private int departmentId;
	private int facultyId;

	public CourseQuery() {
	}

	public String getModulecode() {
		return modulecode;
	}

	public void setModulecode(String modulecode) {
		this.modulecode = modulecode;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public int getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public CourseQuery(String modulecode, String modulename, int semesterId, int departmentId, int facultyId) {
		super();
		this.modulecode = modulecode;
		this.modulename = modulename;
		this.semesterId = semesterId;
		this.departmentId = departmentId;
		this.facultyId = facultyId;
	}

	

}
