package com.example.librarymangmentsystem.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {

    public static void sendVerificationCode(String email, String verificationCode) {
        String host = "smtp.gmail.com";
        String from = "obaidrazan43@gmail.com";
        String password = "jtqy ctlw ifdl nkya";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Verification Code");
            message.setText("Your verification code is: " + verificationCode);

            Transport.send(message);
            System.out.println("Verification code sent to: " + email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}