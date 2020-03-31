package sg.edu.nus.studentmanagementsystem.models.viewmodels;

import javax.validation.constraints.NotBlank;

public class PasswordDetails {
	@NotBlank
	private String newpassword;
	@NotBlank
	private String oldpassword;
	@NotBlank
	private String cfmpassword;
	
	public PasswordDetails(){}
	
	public PasswordDetails(String newpassword, String oldpassword, String cfmpassword) {
		super();
		this.newpassword = newpassword;
		this.oldpassword = oldpassword;
		this.cfmpassword = cfmpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getCfmpassword() {
		return cfmpassword;
	}
	public void setCfmpassword(String cfmpassword) {
		this.cfmpassword = cfmpassword;
	} 
	
	
}
