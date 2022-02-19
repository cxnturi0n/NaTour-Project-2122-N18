package com.cinamidea.natour_2022.navigation.compilation.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.entities.RoutesCompilation;
import com.cinamidea.natour_2022.navigation.compilation.CompilationRecyclerAdapter;
import com.cinamidea.natour_2022.navigation.compilation.contracts.CompilationContract;
import com.cinamidea.natour_2022.navigation.compilation.presenters.CompilationPresenter;
import com.cinamidea.natour_2022.utilities.UserType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CompilationActivity extends AppCompatActivity implements CompilationContract.View{

    private RecyclerView recyclerView;
    private CompilationRecyclerAdapter compilationRecyclerViewAdapter;
    private FloatingActionButton button_add;
    private CompilationContract.Presenter presenter;
    private ProgressBar progressBar;
    private String extra = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compilation);

        button_add = findViewById(R.id.activityCompilation_add);
        progressBar = findViewById(R.id.activityCompilation_progress);
        presenter = new CompilationPresenter(this);

        extra = getIntent().getStringExtra("route_name");
        if(extra!=null) {

            button_add.setVisibility(View.GONE);

        }


        button_add.setOnClickListener(v -> {

            startActivity(new Intent(this, CreateCompilationActivity.class));

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadCompilationRecyclerView();

    }

    private void loadCompilationRecyclerView() {

        recyclerView = findViewById(R.id.activityCompilation_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);

        UserType user_type = new UserType(this);
        String id_token = user_type.getUserType() + user_type.getIdToken();
        presenter.getUserCompilationsButtonClicked(user_type.getUsername(), id_token);

    }

    @Override
    public void loadCompilations(ArrayList<RoutesCompilation> compilations) {
        this.runOnUiThread(() -> {

            progressBar.setVisibility(View.GONE);

            compilationRecyclerViewAdapter = new CompilationRecyclerAdapter(this, compilations, extra);
            recyclerView.setAdapter(compilationRecyclerViewAdapter);

        });
    }

    @Override
    public void displayError(String message) {
        //TODO ADD TOAST
        Log.e("tag1",message);
    }

    @Override
    public void logOutUnauthorizedUser() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}