package com.cinamidea.natour_2022.auth.signup.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.signin.SigninFragment;
import com.cinamidea.natour_2022.auth.signup.contracts.ConfirmSignUpContract;
import com.cinamidea.natour_2022.auth.signup.presenters.ConfirmSignUpPresenter;
import com.cinamidea.natour_2022.utilities.UserType;

public class ConfirmSignupActivity extends AppCompatActivity implements ConfirmSignUpContract.View{

    private ImageButton button_back;
    private com.chaos.view.PinView pin_view;
    private Button button_verify;

    private ConfirmSignUpContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmsignup);

        presenter = new ConfirmSignUpPresenter(this);

        setupComponents();
        setListeners();

    }

    private void setupComponents() {

        button_back = findViewById(R.id.activityPin_backbutton);
        button_verify = findViewById(R.id.activityPin_button);
        pin_view = findViewById(R.id.activityPin_pin);
    }


    private void setListeners() {

        button_back.setOnClickListener(v -> finish());

        button_verify.setOnClickListener(view -> {
            String confirmation_code = pin_view.getText().toString();
            presenter.confirmSignUpButtonPressed(SigninFragment.current_username, confirmation_code);
        });
    }

    @Override
    public void confirmSignUpSuccess() {

        new UserType(this).clear();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public void displayError(String message) {
        //TODO Toast
    }
}