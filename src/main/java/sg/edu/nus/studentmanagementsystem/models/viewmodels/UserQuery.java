package sg.edu.nus.studentmanagementsystem.models.viewmodels;

public class UserQuery {
    private String user;
    private String username;
	private String firstlastname;

    public UserQuery() {
    }

    public UserQuery(String user, String username, String firstlastname) {
        this.user = user;
        this.username = username;
        this.firstlastname = firstlastname;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstlastname() {
        return firstlastname;
    }

    public void setFirstlastname(String firstlastname) {
        this.firstlastname = firstlastname;
    }
}