package SeAH.savg.controller;

import SeAH.savg.Email.EmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
public class EmailController {
    private final JavaMailSender mailSender;

    @Autowired
    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @PostMapping("/api/send-email")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailData emailData) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailData.getRecipients().toArray(new String[0]));
            helper.setSubject(emailData.getSubject());
            helper.setText(emailData.getContent(),true);

            mailSender.send(message);
            return ResponseEntity.ok().body(new ResponseMessage("Email sent successfully."));
        } catch (MessagingException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email.");
        }
    }


}
