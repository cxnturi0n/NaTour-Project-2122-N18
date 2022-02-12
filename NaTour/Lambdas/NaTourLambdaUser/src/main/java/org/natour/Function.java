package org.natour;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.natour.admin.AdminUtils;
import org.natour.entities.AdminMailMessage;
import org.natour.exceptions.CognitoException;
import org.natour.idps.Cognito;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;


public class Function implements RequestHandler<Event, String> {

    Cognito cognito = new Cognito();

    @Override
    public String handleRequest(Event event, Context context) {

        String action = event.getAction();

        switch (action) {

            //Pure Cognito User Registration
            case "REGISTER":
                try {

                    cognito.signUpUser(event.getUser());

                    return "User signed in successfully";

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());

                }
                //Confirm signup with confirmation code sent via email
            case "CONFIRM":
                try {
                    cognito.confirmUser(event.getUser().getUsername(), event.getConfirmation_code());

                    return "User confirmed";

                } catch (CognitoException e) {
                    throw new RuntimeException(e.getMessage());
                }

                //Google User Registration
            case "GOOGLE_REGISTER":
                try {

                    boolean is_google_user_registered = cognito.createGoogleUser(event.getUser(), event.getId_token());

                    return is_google_user_registered ? "Google User successfully signed up" : "Google User successfully signed in";

                } catch (CognitoException | GeneralSecurityException | IOException e) {

                    throw new RuntimeException(e.getMessage());
                }

                //Cognito Token Validation
            case "TOKEN_LOGIN":

                cognito.verifyIdToken(event.getId_token());

                return "Successfully signed in";

            //Get cognito id and refresh tokens by username and password
            case "PASSWORD":
                try {
                    String json_tokens = cognito.signInUserAndGetTokens(event.getUser().getUsername(), event.getUser().getPassword());

                    return json_tokens;

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());

                }

                //Get cognito id token by refresh token
            case "REFRESH_TOKEN":
                try {
                    String new_id_token = cognito.getNewIdToken(event.getRefresh_token(), event.getUser().getUsername());

                    return new_id_token;

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());
                }
                //Initiate forgot password flow, an email will eventually be sent to the user
            case "FORGOT_PWD":
                try {
                    cognito.initiateForgotPassword(event.getUser().getUsername());

                    return "A code has been sent to your mail";

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());

                }
                //Password reset via confirmation code sent previously via email
            case "RESET_PWD":
                try {
                    if (event.getConfirmation_code() != null) {
                        cognito.resetPassword(event.getUser().getUsername(), event.getUser().getPassword(), event.getConfirmation_code());
                    } else {
                        cognito.changePassword(event.getOld_password(), event.getUser().getPassword(), event.getAccess_token());
                    }
                    return "Password changed successfully";
                } catch (CognitoException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "CHANGE_PASSWORD":
                try {
                    cognito.changePassword(event.getOld_password(), event.getUser().getPassword(), event.getAccess_token());
                    return "Password changed successully";

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());

                }

            case "ADMIN_SEND_EMAIL":

                if(event.getUser().getUsername().equals("admin_natour_cinamidea2022"))
                    cognito.verifyIdToken(event.getId_token()); //Throws runtime exception if token is invalid or expired

                AdminMailMessage m = event.getAdmin_mail_message();
                try {
                    //Throws run time exception if token expires or is invalid
                    cognito.verifyIdToken(event.getId_token());

                    new AdminUtils().sendMailsToCognitoUsers(m.getSubject(), m.getBody());
                    return "Mail sent successfully";
                } catch (CognitoException | MessagingException e) {
                    throw new RuntimeException(e.getMessage());
                }
            default:
                throw new RuntimeException("Wrong action");
        }
    }
}



