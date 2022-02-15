package com.cinamidea.natour_2022.navigation.compilation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.signin.SigninFragment;
import com.cinamidea.natour_2022.navigation.main.RecyclerViewAdapter;
import com.cinamidea.natour_2022.utilities.auth.UserSharedPreferences;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.routes.GetUserCompilationsCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CompilationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CompilationRecyclerViewAdapter compilationRecyclerViewAdapter;
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatingActionButton button_add;
    private String extra = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compilation);

        button_add = findViewById(R.id.activityCompilation_add);

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
        ProgressBar progressBar = findViewById(R.id.activityCompilation_progress);

        UserSharedPreferences user_type = new UserSharedPreferences(this);
        String id_token = user_type.getUser_type() + user_type.getId_token();
        new RoutesHTTP().getUserRoutesCompilations(SigninFragment.current_username, id_token, new GetUserCompilationsCallback(this, progressBar, recyclerView, compilationRecyclerViewAdapter, extra));

    }


}