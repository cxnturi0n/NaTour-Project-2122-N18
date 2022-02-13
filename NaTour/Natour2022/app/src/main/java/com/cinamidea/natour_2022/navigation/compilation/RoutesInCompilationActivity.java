package com.cinamidea.natour_2022.navigation.compilation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.SigninFragment;
import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.navigation.main.RecyclerViewAdapter;
import com.cinamidea.natour_2022.utilities.auth.UserType;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.routes.GetRoutesInCompilationCallback;
import com.cinamidea.natour_2022.utilities.http.callbacks.routes.GetUserCompilationsCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RoutesInCompilationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private static List<Route> routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_in_compilation);

        loadRecyclerView();
    }

    private void loadRecyclerView() {

        recyclerView = findViewById(R.id.activityRoutesCompilation_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProgressBar progressBar = findViewById(R.id.activityRoutesCompilation_progress);

        UserType user_type = new UserType(this);
        String id_token = user_type.getUser_type() + user_type.getId_token();
        new RoutesHTTP().getUserRoutesCompilation(SigninFragment.current_username, getIntent().getStringExtra("id"), id_token, new GetRoutesInCompilationCallback(this, progressBar, recyclerView, recyclerViewAdapter));

    }

}