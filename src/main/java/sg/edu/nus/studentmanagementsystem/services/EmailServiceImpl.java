package sg.edu.nus.studentmanagementsystem.services;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import sg.edu.nus.studentmanagementsystem.models.viewmodels.Email;

@Service
public class EmailServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void manageSendEmail(Email email) {
        try {
            sendEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(Email mail) throws Exception {

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            if (mail.getEmailFrom() != null) {
                messageHelper.setFrom(mail.getEmailFrom());
            }

            if (mail.getToEmails() != null) {
                String[] toEmailArray = mail.getToEmails().split(";");
                List<String> toEmailList = new ArrayList<String>();
                if (null == toEmailArray || toEmailArray.length <= 0) {
                    throw new Exception("Recipients can't be null!");
                } else {
                    for (String s : toEmailArray) {
                        if (s != null && !s.equals("")) {
                            toEmailList.add(s);
                        }
                    }
                    if (null == toEmailList || toEmailList.size() <= 0) {
                        throw new Exception("Recipients can't be null!");
                    } else {
                        toEmailArray = new String[toEmailList.size()];
                        for (int i = 0; i < toEmailList.size(); i++) {
                            toEmailArray[i] = toEmailList.get(i);
                        }
                    }
                }
                messageHelper.setTo(toEmailArray);
            }

            if (mail.getSubject() != null) {
                messageHelper.setSubject(mail.getSubject());
            }

            messageHelper.setText(mail.getContent(), true);

            javaMailSender.send(message);
            log.info("Email Sent Successfully!");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}