package sg.edu.nus.studentmanagementsystem.models.viewmodels;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ContactDetail {
    @NotBlank(message = "Address is required!")   
    private String address;
    @NotBlank(message = "Contact number is required!")
    @Pattern(regexp = "^[6|8|9]\\d{7}$", message = "Invalid contact number format!")
    private String contact;
    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid email format!")
    private String email;

    public ContactDetail() {
    }

    public ContactDetail(String address, String contact, String email) {
        this.address = address;
        this.contact = contact;
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}