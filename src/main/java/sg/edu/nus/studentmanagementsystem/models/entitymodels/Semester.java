package sg.edu.nus.studentmanagementsystem.models.entitymodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Semester {
    @Id
    private int id;
    private String name;

    @OneToMany(targetEntity = Course.class, mappedBy = "semester", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<Course>();

    @OneToMany(targetEntity = Enrollment.class, mappedBy = "semester", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<Enrollment>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "Student_Semester", joinColumns = @JoinColumn(name = "semester_id"), // owning side
            inverseJoinColumns = @JoinColumn(name = "student_id")) // inverse side
    private List<Student> students = new ArrayList<Student>();

    public Semester() {
    }

    public Semester(int id, String name, List<Student> students, List<Enrollment> enrollments, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.enrollments = enrollments;
        this.courses = courses;
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

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    @Override
	public int hashCode() {
		return Objects.hash(id, name, students);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Semester other = (Semester) obj;
		return id == other.id && Objects.equals(name, other.name) && Objects.equals(students, other.students);
    }
    
    public void addCourse(Course course)
	{
		courses.add(course);
		course.setSemester(this);
	}
	public void removeCourse(Course course)
	{
		courses.remove(course);
		course.setSemester(null);
    }
    
    public void addEnrollment(Enrollment enrollment)
	{
		enrollments.add(enrollment);
		enrollment.setSemester(this);
	}
	public void removeEnrollment(Enrollment enrollment)
	{
		enrollments.remove(enrollment);
		enrollment.setSemester(null);
    }
    
    public void addStudent(Student student) {
		students.add(student);
		student.getSemesters().add(this);
	}
	
	public void removeStudent(Student student) {
		students.remove(student);
		student.getSemesters().remove(this);
    }
}