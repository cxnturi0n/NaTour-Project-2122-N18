package com.cinamidea.natour_2022.navigation.compilation.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.entities.RoutesCompilation;
import com.cinamidea.natour_2022.navigation.compilation.contracts.CreateCompilationContract;
import com.cinamidea.natour_2022.navigation.compilation.models.CreateCompilationModel;
import com.cinamidea.natour_2022.navigation.compilation.presenters.CreateCompilationPresenter;
import com.cinamidea.natour_2022.utilities.UserType;

public class CreateCompilationActivity extends AppCompatActivity implements CreateCompilationContract.View {

    private Button button_create;
    private ImageButton button_back;
    private ProgressBar progressBar;
    private CreateCompilationContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_compilation);

        button_back = findViewById(R.id.activityCreateCompilation_backbutton);
        button_create = findViewById(R.id.activityCreateCompilation_create);
        progressBar = findViewById(R.id.activityCreateCompilation_progress);
        presenter = new CreateCompilationPresenter(this, new CreateCompilationModel());

        listeners();

    }


    private void listeners() {

        button_back.setOnClickListener(view -> finish());

        button_create.setOnClickListener(view -> {

            String title = ((EditText)findViewById(R.id.activityCreateCompilation_title)).getText().toString();
            String description = ((EditText)findViewById(R.id.activityCreateCompilation_description)).getText().toString();

            if(!title.isEmpty()&&!description.isEmpty()) {
                UserType user_type = new UserType(this);
                RoutesCompilation routesCompilation = new RoutesCompilation("", user_type.getUsername(), title, description, null);


                String id_token = user_type.getUserType() + user_type.getIdToken();

                progressBar.setVisibility(View.VISIBLE);
                presenter.createCompilationButtonClicked(user_type.getUsername(), routesCompilation, id_token);
            }else
            {
                //TODO CIAO
            }

        });

    }

    @Override
    public void compilationCreated() {
        this.runOnUiThread(() -> {

            progressBar.setVisibility(View.GONE);
            finish();

        });
    }

    @Override
    public void displayError(String message) {
        //TODO ERROR
        Log.e("errorcreatecompilation",message);
    }

    @Override
    public void logOutUnauthorizedUser() {
        //TODO ERROR
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}