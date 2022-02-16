package com.cinamidea.natour_2022.auth.signin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.CustomAuthFragment;
import com.cinamidea.natour_2022.auth.resetpassword.views.ResetCRActivity;
import com.cinamidea.natour_2022.navigation.main.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

public class SigninFragment extends CustomAuthFragment implements SignInContract.View {

    public static String current_username;
    private Button button_signin;
    private TextView text_forgotpwd;
    private EditText edit_user;
    private EditText edit_password;
    private Button button_googlesignin;
    private GoogleSignInClient googlesignin_client;
    private ActivityResultLauncher<Intent> start_activity_for_result;
    private GoogleSignInOptions gso;

    private SignInContract.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter = new SignInPresenter(this);

        initGoogleAuthVars();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewComponents(view);
        setListeners();

    }


    @Override
    protected void setupViewComponents(View view) {

        button_signin = view.findViewById(R.id.fragmentSignin_signin);
        text_forgotpwd = view.findViewById(R.id.fragmentSignin_forgotpassword);
        edit_user = view.findViewById(R.id.fragmentSignin_username);
        edit_password = view.findViewById(R.id.fragmentSignin_password);
        button_googlesignin = view.findViewById(R.id.fragmentSignin_signinwithgoogle);

        setupAnimation();

    }

    private void setListeners() {

        button_signin.setOnClickListener(v -> {

            runAnimation(button_signin);

            String username = edit_user.getText().toString();
            String password = edit_password.getText().toString();

            presenter.cognitoSignInButtonClicked(username, password, getActivity().getSharedPreferences("Cognito", Context.MODE_PRIVATE));

            //Set username per l'utente della chat
            current_username = username;
        });

        text_forgotpwd.setOnClickListener(view -> runHandledIntent(new Intent(getActivity(), ResetCRActivity.class)));

        button_googlesignin.setOnClickListener(view -> {

            presenter.googleSignInButtonClicked(googlesignin_client, getActivity().getSharedPreferences("Google", Context.MODE_PRIVATE));

        });

    }

    @Override
    public void signInSuccess() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }

    @Override
    public void googleSignUp() {
        startGoogleSignUp();
    }

    @Override
    public void displayError(String message) {
        googlesignin_client.signOut();
        //TODO TOAST
    }



    //Google sign up stuff

    public void startGoogleSignUp() {
        Intent signin_intent = googlesignin_client.getSignInIntent();
        start_activity_for_result.launch(signin_intent);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completed_task) {


        GoogleSignInAccount google_account = completed_task.getResult();

        String username = google_account.getDisplayName().replace(" ", "");
        String email = google_account.getEmail();
        String id_token = google_account.getIdToken();
        presenter.googleSignUpButtonClicked(username, email, id_token, getActivity().getSharedPreferences("Google", Context.MODE_PRIVATE));
    }

    private void initGoogleAuthVars() {

        start_activity_for_result = registerForActivityResult(new ActivityResultContracts
                .StartActivityForResult(), result -> {

            if (result.getResultCode() == Activity.RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                handleSignInResult(task);
            }
        });
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(("556927589955-pahgt8na4l8de0985mvlc9gugfltbkef.apps.googleusercontent.com"))
                .build();

        googlesignin_client = GoogleSignIn.getClient(getActivity(), gso);

    }


}