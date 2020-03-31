package sg.edu.nus.studentmanagementsystem.models.viewmodels;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Login {
    @NotBlank(message = "Username is required!")
    @Pattern(regexp = "^(?i)[a|f|s]\\d{5}$", message = "Invalid username format!")
    private String username;
    @NotBlank(message = "Password is required!")
    private String password;
    @NotBlank(message = "Choose a login domain!")
    private String domain;

    public Login() {
    }

    public Login(String username, String password, String domain) {
        this.username = username;
        this.password = password;
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}