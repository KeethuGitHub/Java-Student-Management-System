package sg.edu.nus.studentmanagementsystem.models.entitymodels;

public class Guest {
    private String firstName;
    private String lastName;

    public Guest() {
        this.firstName = "Guest";
        this.lastName = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}