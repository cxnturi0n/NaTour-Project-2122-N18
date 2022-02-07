package com.cinamidea.natour_2022.navigation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.SigninFragment;
import com.cinamidea.natour_2022.utilities.auth.UserType;
import com.cinamidea.natour_2022.utilities.http.AuthenticationHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.auth.ChangePasswordCallback;

public class ChangePasswordActivity extends AppCompatActivity {

    private ImageButton button_back;
    private EditText current_pwd, new_pwd, confirm_pwd;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setupComponents();
        setListeners();

    }

    private void setupComponents() {

        button_back = findViewById(R.id.activityChangePassw_back);
        current_pwd = findViewById(R.id.activityChangePassw_current);
        new_pwd = findViewById(R.id.activityChangePassw_new);
        confirm_pwd = findViewById(R.id.activityChangePassw_confirm);
        submit = findViewById(R.id.activityChangePassw_submit);

    }

    private void setListeners() {

        button_back.setOnClickListener(view -> finish());

        submit.setOnClickListener(view -> {

            UserType user_type = new UserType(this);
            if (new_pwd.getText().toString().equals(confirm_pwd.getText().toString())) {

                new AuthenticationHTTP().changePassword(SigninFragment.current_username, current_pwd.getText().toString(),
                        new_pwd.getText().toString(), user_type.getId_token(), new ChangePasswordCallback(this));

            }

        });

    }

}