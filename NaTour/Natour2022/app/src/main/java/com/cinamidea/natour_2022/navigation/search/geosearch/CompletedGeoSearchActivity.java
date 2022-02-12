package com.cinamidea.natour_2022.navigation.search.geosearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.navigation.main.RecyclerViewAdapter;
import com.cinamidea.natour_2022.utilities.auth.UserType;

import java.util.ArrayList;

public class CompletedGeoSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private static ArrayList<Route> routes;
    private static ArrayList<Route> fav_routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_search);

        loadRecyclerView();

    }

    private void loadRecyclerView() {

        recyclerView = findViewById(R.id.activityCompletedGeoSearch_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(this, routes, fav_routes, false);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    public static void setRoutes(ArrayList<Route> routes) {
        CompletedGeoSearchActivity.routes = routes;
    }

    public static void setFav_routes(ArrayList<Route> fav_routes) {
        CompletedGeoSearchActivity.fav_routes = fav_routes;
    }

}