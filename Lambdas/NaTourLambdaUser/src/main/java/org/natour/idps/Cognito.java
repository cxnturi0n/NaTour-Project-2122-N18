package org.natour.idps;

import org.natour.entities.User;
import org.natour.exceptions.PersistenceException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentity.model.CognitoIdentityProvider;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Cognito {

    private final String CLIENT_ID = "157v4iepuh9gof03tp1u4hh9fb";
    private final String CLIENT_SECRET = "llqe93m3ekag6akhcmikopbtid99e9u677an6sstvov8q8p0psh";
    private CognitoIdentityProviderClient cognito_client;

    public Cognito() {
        //Creating cognito provider client
        cognito_client = CognitoIdentityProviderClient.builder()
                .region(Region.EU_CENTRAL_1)
                .build();
    }

    public void signUpUser(User user) throws PersistenceException {

        try {
            //Setting up user attributes(in our case, only email is needed)
            AttributeType user_attributes = AttributeType.builder()
                    .name("email")
                    .value(user.getEmail())
                    .build();

            //Safe way to encode client id and secret id
            String secret_hash = getSecretHash(user.getUsername());
            //Setting up user request, specifying user pool id, username, password and attributes
            SignUpRequest signup_request = SignUpRequest.builder().username(user.getUsername()).password(user.getPassword()).userAttributes(user_attributes).clientId(CLIENT_ID).secretHash(secret_hash).build();

            //Creating user
            cognito_client.signUp(signup_request);


        } catch (CognitoIdentityProviderException e) {
            throw new PersistenceException(e.awsErrorDetails().errorMessage());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public void confirmUser(String username, String confirmation_code) throws PersistenceException {

        try {
            //Safe way to encode client id and secret id
            String secret_hash = getSecretHash(username);
            //Setting up confirm request
            ConfirmSignUpRequest c = ConfirmSignUpRequest.builder().secretHash(secret_hash).confirmationCode(confirmation_code).username(username).clientId(CLIENT_ID).build();
            //Confirming user by confirmation_code(Sent via email after signup)
            cognito_client.confirmSignUp(c);
        } catch (CognitoIdentityProviderException e) {
            throw new PersistenceException(e.awsErrorDetails().errorMessage());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public void initiateForgotPassword(String username) throws PersistenceException {
        try {
            String secret_hash = getSecretHash(username);

            ForgotPasswordRequest forgotpwd_request = ForgotPasswordRequest.builder().clientId(CLIENT_ID).secretHash(secret_hash).username(username).build();

            cognito_client.forgotPassword(forgotpwd_request);

        } catch (CognitoIdentityProviderException e) {
            throw new PersistenceException(e.awsErrorDetails().errorMessage());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public void resetPassword(String username, String new_password, String confirmation_code) throws PersistenceException {

        try {
            String secret_hash = getSecretHash(username);

            ConfirmForgotPasswordRequest confirm_forgotpwd_request = ConfirmForgotPasswordRequest.builder().username(username).clientId(CLIENT_ID).password(new_password).secretHash(secret_hash).confirmationCode(confirmation_code).build();

            cognito_client.confirmForgotPassword(confirm_forgotpwd_request);

        } catch (CognitoIdentityProviderException e) {
            throw new PersistenceException(e.awsErrorDetails().errorMessage());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public void signInUser(String username, String password) throws PersistenceException {


        try {
            Map<String, String> auth_params = new HashMap<>();
            auth_params.put("USERNAME", username);
            auth_params.put("PASSWORD", password);
            auth_params.put("SECRET_HASH", getSecretHash(username));

            InitiateAuthRequest signin_request = InitiateAuthRequest.builder().authParameters(auth_params).
                    clientId(CLIENT_ID).authFlow(AuthFlowType.USER_PASSWORD_AUTH).build();

            cognito_client.initiateAuth(signin_request);

        } catch (CognitoIdentityProviderException e) {
            throw new PersistenceException(e.awsErrorDetails().errorMessage());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

    }


    private String getSecretHash(String username) throws NoSuchAlgorithmException, InvalidKeyException {

        final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

        SecretKeySpec signingKey = new SecretKeySpec(
                CLIENT_SECRET.getBytes(StandardCharsets.UTF_8),
                HMAC_SHA256_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        mac.init(signingKey);
        mac.update(username.getBytes(StandardCharsets.UTF_8));
        byte[] rawHmac = mac.doFinal(CLIENT_ID.getBytes(StandardCharsets.UTF_8));
        return java.util.Base64.getEncoder().encodeToString(rawHmac);
    }

}