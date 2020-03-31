package sg.edu.nus.studentmanagementsystem.models.viewmodels;

import java.util.Map;

public class Email {
	private String emailHost;

	private String emailFrom;

	private String emailUserName;

	private String emailPassword;
	
	private String toEmails;

	private String subject;

	private String content;

	private Map<String, String> pictures;

    private Map<String, String> attachments;

    public Email() {
    }

    public Email(String emailHost, String emailFrom, String emailUserName, String emailPassword, String toEmails,
            String subject, String content, Map<String, String> pictures, Map<String, String> attachments) {
        this.emailHost = emailHost;
        this.emailFrom = emailFrom;
        this.emailUserName = emailUserName;
        this.emailPassword = emailPassword;
        this.toEmails = toEmails;
        this.subject = subject;
        this.content = content;
        this.pictures = pictures;
        this.attachments = attachments;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailUserName() {
        return emailUserName;
    }

    public void setEmailUserName(String emailUserName) {
        this.emailUserName = emailUserName;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getToEmails() {
        return toEmails;
    }

    public void setToEmails(String toEmails) {
        this.toEmails = toEmails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getPictures() {
        return pictures;
    }

    public void setPictures(Map<String, String> pictures) {
        this.pictures = pictures;
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }
}