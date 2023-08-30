package SeAH.savg.controller;
import SeAH.savg.Email.EmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
public class EmailController {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @CrossOrigin(origins = "http://localhost:3000")
//    @CrossOrigin(origins = "http://172.20.20.252:3000")   // 세아

    @PostMapping("/api/send-email")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailData emailData) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setTo(emailData.getRecipient());
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
