package org.natour.idps;

import org.natour.exceptions.PersistenceException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Cognito {

    private static final String CLIENT_ID = "157v4iepuh9gof03tp1u4hh9fb";
    private static final String CLIENT_SECRET = "llqe93m3ekag6akhcmikopbtid99e9u677an6sstvov8q8p0psh";

    public Cognito() {

    }

    public static void signUpUser(String email, String password, String username) throws PersistenceException {

        //Creating cognito provider client
        CognitoIdentityProviderClient cognito_client = CognitoIdentityProviderClient.builder()
                .region(Region.EU_CENTRAL_1)
                .build();

        try {
            //Setting up user attributes(in our case, only email is needed)
            AttributeType user_attributes = AttributeType.builder()
                    .name("email")
                    .value(email)
                    .build();

            //Safe way to encode client id and secret id
            String secret_hash = getSecretHash(username);
            //Setting up user request, specifying user pool id, username, password and attributes
            SignUpRequest signup_request = SignUpRequest.builder().username(username).password(password).userAttributes(user_attributes).clientId(CLIENT_ID).secretHash(secret_hash).build();

            //Creating user
            cognito_client.signUp(signup_request);


        } catch (CognitoIdentityProviderException e) {
            throw new PersistenceException(e.awsErrorDetails().errorMessage());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public static void confirmUser(String username, String confirmation_code) throws PersistenceException {


        CognitoIdentityProviderClient cognito_client = CognitoIdentityProviderClient.builder()
                .region(Region.EU_CENTRAL_1)
                .build();
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


    private static String getSecretHash(String username) throws NoSuchAlgorithmException, InvalidKeyException {

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