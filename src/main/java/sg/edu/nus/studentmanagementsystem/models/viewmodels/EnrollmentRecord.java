package sg.edu.nus.studentmanagementsystem.models.viewmodels;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Department;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Faculty;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Semester;

public class EnrollmentRecord {
    private int moduleid;
	private String modulecode;
    private String modulename;
    private int credit;
    private int size;
    private String enrollmentStatus;

    private Department department;
    private Faculty faculty;
    private Semester semester;

    
    
    public int getModuleid() {
		return moduleid;
	}

	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
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

	public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }	
}