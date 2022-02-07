package org.natour.admin;

import org.natour.exceptions.CognitoException;
import org.natour.idps.Cognito;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UserType;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class AdminUtils {

    public void sendMailsToCognitoUsers(String subject, String promo_message) throws CognitoException, MessagingException {


        Cognito cognito = new Cognito();

        List<UserType> users = (List<UserType>) cognito.getCognitoUsers(null).get("users");

        for (UserType u : users) {
            // Sender's email ID needs to be mentioned
            String from = "natour.social@gmail.com";

            // Assuming you are sending email from through gmails smtp
            String host = "smtp.gmail.com";

            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            // Get the Session object.// and pass username and password
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication(from, "cinamidea2022");

                }

            });

            // Used to debug SMTP issues
            session.setDebug(true);

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.

            String email = u.attributes().get(0).value();
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(promo_message);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }

    }
}
