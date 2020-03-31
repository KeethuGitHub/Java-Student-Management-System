package sg.edu.nus.studentmanagementsystem.models.entitymodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Student extends User {
    private static int numOfStudent = 0;

    @NotBlank(message = "Major is required!")
    private String major;
    private double cgpa = 0.0;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity = Enrollment.class, mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<Enrollment>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = Semester.class, mappedBy = "students", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Semester> semesters = new ArrayList<Semester>();

    public Student() {
    }

    public Student(String username, String password, String firstName, String lastName, String address, String contact,
            String dateOfBirth, String email, char gender, String major, double cgpa) {
        super(username, password, firstName, lastName, address, contact, dateOfBirth, email, gender);
        this.major = major;
        this.cgpa = cgpa;
    }

    public static int getNumOfStudent() {
        return numOfStudent;
    }

    public static void setNumOfStudent(int numOfStudent) {
        Student.numOfStudent = numOfStudent;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cgpa, major, semesters);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Double.doubleToLongBits(cgpa) == Double.doubleToLongBits(other.cgpa)
				&& Objects.equals(major, other.major) && Objects.equals(semesters, other.semesters);
	}

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        enrollment.setStudent(this);
    }

    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
        enrollment.setStudent(null);
    }
}