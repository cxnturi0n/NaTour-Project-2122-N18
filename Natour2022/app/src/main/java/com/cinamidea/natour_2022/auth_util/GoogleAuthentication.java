package com.cinamidea.natour_2022.auth_util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.navigation.HomeActivity;
import com.cinamidea.natour_2022.auth.SigninFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.Task;

public class GoogleAuthentication {

    private AppCompatActivity activity;
    private ActivityResultLauncher<Intent> start_activity_for_result;
    private GoogleSignInOptions gso;
    GoogleSignInClient googlesignin_client;

    public GoogleAuthentication(AppCompatActivity activity) {

        this.activity = activity;

        start_activity_for_result = activity.registerForActivityResult(new ActivityResultContracts
                .StartActivityForResult(), result -> {

            if (result.getResultCode() == Activity.RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                handleSignInResult(task);
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(("556927589955-6560abd2gt8mm470tn1v4jlpmag213lt.apps.googleusercontent.com"))
                .build();


        googlesignin_client = GoogleSignIn.getClient(activity, gso);


    }

    public void signIn() {
        Intent signin_intent = googlesignin_client.getSignInIntent();
        start_activity_for_result.launch(signin_intent);

    }

    public void silentSignIn() {
        Task<GoogleSignInAccount> task = googlesignin_client.silentSignIn();
        if (task.isSuccessful()) {

            //Se l id token non è scaduto
            GoogleSignInAccount signInAccount = task.getResult();
            SigninFragment.chat_username = signInAccount.getGivenName();
            activity.startActivity(new Intent(activity, HomeActivity.class));

        } else {
            //Se l id token è scaduto allora automaticamente lo refresha, e salvalo nelle shared preferences
            task.addOnCompleteListener(task1 -> {
                try {

                    GoogleSignInAccount signInAccount = task1.getResult(ApiException.class);
                    SigninFragment.chat_username = signInAccount.getGivenName();


                    //Salviamo il token di google nelle shared preferences
                    SharedPreferences sharedPreferences = activity.getSharedPreferences("google_token", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("id_token",signInAccount.getIdToken()).commit();

                    activity.startActivity(new Intent(activity, HomeActivity.class));



                } catch (ApiException apiException) {
                    int status_code = apiException.getStatusCode();

                    switch (status_code) {
                        case CommonStatusCodes.SIGN_IN_REQUIRED:
                            signIn();
                            break;
                        case CommonStatusCodes.NETWORK_ERROR:
                            Log.e("Google SignIn", "Network error");
                            break;
                        case CommonStatusCodes.INVALID_ACCOUNT:
                            Log.e("Google SignIn", "Invalid account");
                            break;
                        case CommonStatusCodes.INTERNAL_ERROR:
                            Log.e("Google SignIn", "Internal error");
                            break;
                        case 12501: //Sign in cancelled
                            Log.e("Google SignIn", "Sign in cancelled");
                            break;
                        case 12502:
                            Log.e("Google SignIn", "Sign in already processing");
                            break;
                        default:
                            Log.e("Google SignIn", "Please, try again");
                            break;
                    }
                }
            });
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completed_task) {


        GoogleSignInAccount google_account = completed_task.getResult();

        String username = google_account.getDisplayName().replace(" ","");
        String email = google_account.getEmail();
        String id_token = google_account.getIdToken();
        Authentication.googleSignUp(username,email, id_token, new GoogleSignUpCallback(activity, username, id_token, this));

    }

    public void signOut() {

        googlesignin_client.signOut().addOnCompleteListener(activity, task -> Log.e("Google SignOut", "OK"));

    }

}
