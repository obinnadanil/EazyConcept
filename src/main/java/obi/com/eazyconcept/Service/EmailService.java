package obi.com.eazyconcept.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import obi.com.eazyconcept.Entity.ContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    TemplateEngine templateEngine;

    public void  sentHtmlEmail(String to, String subject,  ContactRequest contactRequest) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8" );
        helper.setTo(to);
        helper.setSubject(subject);
        Context context = new Context();
        context.setVariable("requestDetails", contactRequest);

        String htmlContent = templateEngine.process("email-template", context);

        helper.setText(htmlContent, true);
        helper.setFrom("izzykoncept042@gmail.com");

        mailSender.send(message);

    }
}
