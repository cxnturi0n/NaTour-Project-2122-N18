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

            case "REGISTER":

                String password = request.getUser().getPassword();
                if (password != null) {
                    try {

                        cognito.signUpUser(request.getUser());

                        return "User signed in successfully";

                    } catch (CognitoException e) {

                        throw new RuntimeException(e.getMessage());

                    }
                }

                try {
                    //Used for registering user signed in with google
                    cognito.createUserWithRandomPassword(request.getUser());

                    return "User created successfully";

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());

                }

            case "CONFIRM":
                try {
                    cognito.confirmUser(request.getUser().getUsername(), request.getConfirmation_code());

                    return "User confirmed";

                } catch (CognitoException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "TOKEN_LOGIN":

                cognito.verifyIdToken(request.getId_token());

                return "Successfully signed in";

            case "PASSWORD":
                try {
                    String json_tokens = cognito.signInUserAndGetTokens(request.getUser().getUsername(), request.getUser().getPassword());

                    return json_tokens;

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());

                }

            case "REFRESH_TOKEN":
                try {
                    String new_id_token = cognito.getNewIdToken(request.getRefresh_token(), request.getUser().getUsername());

                    return new_id_token;

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());
                }
            case "FORGOT_PWD":
                try {
                    cognito.initiateForgotPassword(request.getUser().getUsername());

                    return "A code has been sent to your mail";

                } catch (CognitoException e) {

                    throw new RuntimeException(e.getMessage());

                }
            case "RESET_PWD":
                try {
                    cognito.resetPassword(request.getUser().getUsername(), request.getUser().getPassword(), request.getConfirmation_code());

                    return "Password changed successfully";

                } catch (CognitoException e) {
                    throw new RuntimeException(e.getMessage());
                }
            case "GOOGLE_TOKEN":

                try {
                    return GoogleAuth.getAccountParams(request.getId_token()).get("username");
                } catch (GeneralSecurityException | IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            default:
                throw new RuntimeException("Wrong action");
        }
    }
}