package sg.edu.nus.studentmanagementsystem.models.entitymodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int size;

    @ManyToOne 
    @JoinColumn(name = "module_id")
    private Module module; 
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @OneToMany(targetEntity = Enrollment.class, mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<Enrollment>();

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

	public Course() {
	}

    public Course(int id, int size, Module module, Department department, Faculty faculty, List<Enrollment> enrollments,
			Semester semester) {
		super();
		this.id = id;
		this.size = size;
		this.module = module;
		this.department = department;
		this.faculty = faculty;
		this.enrollments = enrollments;
		this.semester = semester;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
    
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

    @Override
	public int hashCode() {
		return Objects.hash(department, enrollments, faculty, id, module, semester, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(department, other.department) && Objects.equals(enrollments, other.enrollments)
				&& Objects.equals(faculty, other.faculty) && id == other.id && Objects.equals(module, other.module)
				&& Objects.equals(semester, other.semester) && size == other.size;
	}

	public void addEnrollment(Enrollment enrollment)
	{
		enrollments.add(enrollment);
		enrollment.setCourse(this);
	}
	
	public void removeEnrollment(Enrollment enrollment)
	{
		enrollments.remove(enrollment);
		enrollment.setCourse(null);
    }
}