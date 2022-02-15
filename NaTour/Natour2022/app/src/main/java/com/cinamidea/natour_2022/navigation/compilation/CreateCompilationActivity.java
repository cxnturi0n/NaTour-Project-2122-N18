package com.cinamidea.natour_2022.navigation.compilation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.signin.SigninFragment;
import com.cinamidea.natour_2022.entities.RoutesCompilation;
import com.cinamidea.natour_2022.utilities.auth.UserSharedPreferences;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.routes.CreateCompilationCallback;

public class CreateCompilationActivity extends AppCompatActivity {

    Button button_create;
    ImageButton button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_compilation);

        setupComponents();
        listeners();

    }

    private void setupComponents() {

        button_back = findViewById(R.id.activityCreateCompilation_backbutton);
        button_create = findViewById(R.id.activityCreateCompilation_create);

    }

    private void listeners() {

        button_back.setOnClickListener(view -> finish());

        button_create.setOnClickListener(view -> {

            String title = ((EditText)findViewById(R.id.activityCreateCompilation_title)).getText().toString();
            String description = ((EditText)findViewById(R.id.activityCreateCompilation_description)).getText().toString();

            RoutesCompilation routesCompilation = new RoutesCompilation("", SigninFragment.current_username, title, description, null);
            UserSharedPreferences userSharedPreferences = new UserSharedPreferences(this);
            ProgressBar progressBar = findViewById(R.id.activityCreateCompilation_progress);
            progressBar.setVisibility(View.VISIBLE);
            new RoutesHTTP().createRoutesCompilation(routesCompilation, userSharedPreferences.getUser_type()+ userSharedPreferences.getId_token(), new CreateCompilationCallback(this, progressBar));
            //TODO: Creazione compilation

        });

    }

}