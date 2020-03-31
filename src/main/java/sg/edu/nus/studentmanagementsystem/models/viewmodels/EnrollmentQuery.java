package sg.edu.nus.studentmanagementsystem.models.viewmodels;

public class EnrollmentQuery {

	public EnrollmentQuery() {
	
	}
	private int semesterId;
	private int moduleId;
	public int getSemesterId() {
		return semesterId;
	}
	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public EnrollmentQuery(int semesterId, int moduleId) {
		super();
		this.semesterId = semesterId;
		this.moduleId = moduleId;
	}
}
