package sg.edu.nus.studentmanagementsystem.models.entitymodels;

import javax.persistence.Entity;

@Entity
public class Admin extends User {
    private static int numOfAdmin = 0;

    public Admin() {
    }

    public Admin(String username, String password, String firstName, String lastName, String address, String contact,
            String dateOfBirth, String email, char gender) {
        super(username, password, firstName, lastName, address, contact, dateOfBirth, email, gender);
    }

    public static int getNumOfAdmin() {
        return numOfAdmin;
    }

    public static void setNumOfAdmin(int numOfAdmin) {
        Admin.numOfAdmin = numOfAdmin;
    }
}