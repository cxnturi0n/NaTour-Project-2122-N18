package com.cinamidea.natour_2022.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth_util.Authentication;
import com.cinamidea.natour_2022.auth_util.SignupCallback;

public class SignupFragment extends CustomAuthFragment {

    private Button signup_button;
    private EditText edit_user;
    private EditText edit_email;
    private EditText edit_password;
    private EditText edit_retypepassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewComponents(view);
        setListeners();

    }

    @Override
    protected void setupViewComponents(View view) {

        signup_button = view.findViewById(R.id.fragmentSignup_signup);
        edit_user = view.findViewById(R.id.fragmentSignup_username);
        edit_email = view.findViewById(R.id.fragmentSignup_email);
        edit_password = view.findViewById(R.id.fragmentSignup_password);
        edit_retypepassword = view.findViewById(R.id.fragmentSignup_retypepassw);

        setupAnimation();

    }

    private void setListeners() {

        signup_button.setOnClickListener(v -> {

//            Dialog dialog = new Dialog(getActivity());
//            dialog.setContentView(R.layout.error_message_layout);
//            dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.message_notification_background));
//            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            dialog.show();

            runAnimation(signup_button);
            String password = edit_password.getText().toString();
            String retype_password = edit_retypepassword.getText().toString();

            if(!password.equals(retype_password)) {

                Toast.makeText(getActivity(), "Errore password test", Toast.LENGTH_LONG).show();
                return;

            }

            String username = edit_user.getText().toString();
            String email = edit_email.getText().toString();

            Intent intent = new Intent(getActivity(), PinActivity.class);

            intent.putExtra("username", username);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            Authentication auth = new Authentication();
            auth.signUp(username, email, password, new SignupCallback(username, getActivity()));

            SigninFragment.chat_username=username;

        });

    }
    
}