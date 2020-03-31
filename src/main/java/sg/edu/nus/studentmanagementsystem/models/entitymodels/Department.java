package sg.edu.nus.studentmanagementsystem.models.entitymodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Department Name is required!")
    private String name;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity=Course.class, mappedBy="department", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<Course>();
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity=Faculty.class, mappedBy="department", cascade = CascadeType.ALL)
    private List<Faculty> faculties = new ArrayList<Faculty>();

    public Department() {
    }

    public Department(int id, String name, List<Course> courses, List<Faculty> faculties) {
        this.id = id;
        this.name = name;
        this.courses = courses;
        this.faculties = faculties;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    @Override
	public int hashCode() {
		return Objects.hash(courses, faculties, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(courses, other.courses) && Objects.equals(faculties, other.faculties) && id == other.id
				&& Objects.equals(name, other.name);
	}

    public void addCourse(Course course)
	{
		courses.add(course);
		course.setDepartment(this);
	}
	public void removeCourse(Course course)
	{
		courses.remove(course);
		course.setDepartment(null);
	}

	public void addFaculty(Faculty faculty)
	{
		faculties.add(faculty);
		faculty.setDepartment(this);
	}
	
	public void removeFaculty(Faculty faculty)
	{
		faculties.remove(faculty);
		faculty.setDepartment(null);
    }
}