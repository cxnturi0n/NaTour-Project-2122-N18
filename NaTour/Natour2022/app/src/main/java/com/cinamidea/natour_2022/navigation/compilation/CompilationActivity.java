package com.cinamidea.natour_2022.navigation.compilation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.navigation.main.RecyclerViewAdapter;
import com.cinamidea.natour_2022.utilities.auth.UserType;

public class CompilationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compilation);

    }

    private void loadRecyclerView() {

        recyclerView = findViewById(R.id.activityCompilation_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProgressBar progressBar = findViewById(R.id.activityCompilation_progress);

        UserType user_type = new UserType(this);
        String id_token = user_type.getUser_type() + user_type.getId_token();

    }


}