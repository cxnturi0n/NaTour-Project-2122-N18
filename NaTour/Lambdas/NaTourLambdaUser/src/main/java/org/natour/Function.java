package org.natour;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.natour.exceptions.CognitoException;
import org.natour.idps.Cognito;
import org.natour.idps.GoogleAuth;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Function implements RequestHandler<Request, String> {
    @Override
    public String handleRequest(Request request, Context context) {

        Cognito cognito = new Cognito();
        String action = request.getAction();

        switch (action) {

            //Pure Cognito User Registration
            case "REGISTER":
                try {

                    cognito.signUpUser(request.getUser());

                    return "User signed in successfully";

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());

                }
                //Confirm signup with confirmation code sent via email
            case "CONFIRM":
                try {
                    cognito.confirmUser(request.getUser().getUsername(), request.getConfirmation_code());

                    return "User confirmed";

                } catch (CognitoException e) {
                    throw new RuntimeException(e.getMessage());
                }

                //Google User Registration
            case "GOOGLE_REGISTER":
                try {

                    boolean isGoogleUserRegistered = cognito.createGoogleUser(request.getUser(), request.getId_token());

                    return isGoogleUserRegistered ? "User successfully signed up" : "User successfully signed in";

                } catch (CognitoException | GeneralSecurityException | IOException e) {

                    throw new RuntimeException(e.getMessage());
                }

                //Cognito Token Validation
            case "TOKEN_LOGIN":

                cognito.verifyIdToken(request.getId_token());

                return "Successfully signed in";

            //Get cognito id and refresh tokens by username and password
            case "PASSWORD":
                try {
                    String json_tokens = cognito.signInUserAndGetTokens(request.getUser().getUsername(), request.getUser().getPassword());

                    return json_tokens;

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());

                }

                //Get cognito id token by refresh token
            case "REFRESH_TOKEN":
                try {
                    String new_id_token = cognito.getNewIdToken(request.getRefresh_token(), request.getUser().getUsername());

                    return new_id_token;

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());
                }
                //Initiate forgot password flow, an email will eventually be sent to the user
            case "FORGOT_PWD":
                try {
                    cognito.initiateForgotPassword(request.getUser().getUsername());

                    return "A code has been sent to your mail";

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());

                }
                //Password reset via confirmation code sent previously via email
            case "RESET_PWD":
                try {
                    if (request.getConfirmation_code() != null) {
                        cognito.resetPassword(request.getUser().getUsername(), request.getUser().getPassword(), request.getConfirmation_code());
                    } else {
                        cognito.changePassword(request.getOld_password(), request.getUser().getPassword(), request.getAccess_token());
                    }
                    return "Password changed successfully";
                } catch (CognitoException e) {
                    throw new RuntimeException(e.getMessage());
                }
                //Google id token validation
         /*   case "GOOGLE_TOKEN":

                try {
                    return GoogleAuth.getAccountParams(request.getId_token()).get("username");
                } catch (GeneralSecurityException | IOException e) {
                    throw new RuntimeException(e.getMessage());
                }*/
            default:
                throw new RuntimeException("Wrong action");
        }
    }
}