package sg.edu.nus.studentmanagementsystem.models.viewmodels;

public class DepartmentQuery {
    private String departmentname;

    public DepartmentQuery() {
    }

    public DepartmentQuery(String departmentname) {
        this.departmentname = departmentname;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }
}