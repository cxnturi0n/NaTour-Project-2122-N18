package com.cinamidea.natour_2022.user.changepassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.utilities.UserType;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordContract.View{

    private ImageButton button_back;
    private EditText current_pwd, new_pwd, confirm_pwd;
    private Button submit;
    private ChangePasswordContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setupComponents();
        setListeners();
        presenter = new ChangePasswordPresenter(this);
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
            if (new_pwd.getText().toString().equals(confirm_pwd.getText().toString()))
               presenter.changePasswordButtonPressed(new UserType(this), current_pwd.getText().toString(), new_pwd.getText().toString());

        });

    }


    @Override
    public void onPasswordChanged() {
        new UserType(this).clear();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void displayError(String message) {
        Log.e("password", message);
        //TODO LOG
    }

    @Override
    public void logOutUnauthorizedUser() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}