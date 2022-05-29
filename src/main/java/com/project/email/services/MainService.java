package com.project.email.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Properties;

@Service
public class MainService {

    @Value("${emailCreds.mail.smtp.auth.val}")
    private String smtpAuth;
    @Value("${emailCreds.mail.smtp.starttls.enable.val}")
    private String smtpStarttls;
    @Value("${emailCreds.mail.smtp.host.val}")
    private String smtpHost;
    @Value("${emailCreds.mail.smtp.port.val}")
    private String smtpPort;

    @Value("${emailCreds.mail.from.id.val}")
    private String userId;
    @Value("${emailCreds.mail.from.pass.val}")
    private String userPass;
    @Value("${emailCreds.mail.from.email.val}")
    private String userEmail;


    @Value("${emailCreds.mail.to.email.val}")
    private String toEmail;
    @Value("${emailCreds.mail.to.subject.val}")
    private String toSubject;

    @Value("${emailCreds.mail.content.val}")
    private String content;

    public void sendEmail() throws AddressException, MessagingException, IOException {

        URL url = new URL(content);

        // Copy certificate in local
        try (InputStream in = url.openStream()) {
            Path target = Paths.get("/Users", "sumitjadiya", "Desktop", "fileIn.pdf");
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", smtpStarttls);
        props.put("mail.smtp.host", smtpHost); // gmail
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userId, userPass);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userEmail, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        msg.setSubject(toSubject);
        msg.setContent("email content", "text/html");
        msg.setSentDate(new Date());

//        code for URL
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(content, "text/html");
//        messageBodyPart.setDataHandler(new DataHandler(uds));
//        messageBodyPart.setDisposition(Part.ATTACHMENT);
//        messageBodyPart.setFileName(url.getFile());

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile("/Users/sumitjadiya/Desktop/fileIn.pdf");
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}
