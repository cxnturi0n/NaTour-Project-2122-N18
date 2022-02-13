package com.cinamidea.natour_2022.navigation.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.SigninFragment;
import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.entities.RouteFilters;
import com.cinamidea.natour_2022.entities.RoutesCompilation;
import com.cinamidea.natour_2022.navigation.main.RecyclerViewAdapter;
import com.cinamidea.natour_2022.utilities.auth.UserType;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.routes.GetFilteredRoutesCallback;
import com.paulrybitskyi.persistentsearchview.PersistentSearchView;
import com.paulrybitskyi.persistentsearchview.listeners.OnSearchConfirmedListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;
    public static RecyclerViewAdapter recyclerViewAdapter;
    private PersistentSearchView persistentSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.activitySearch_recyclerview);
        persistentSearchView = findViewById(R.id.activitySearch_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        persistentSearchView.setOnSearchConfirmedListener(new OnSearchConfirmedListener() {
            @Override
            public void onSearchConfirmed(PersistentSearchView searchView, String query) {

                loadRecyclerView(query);

            }
        });

    }

    private void loadRecyclerView(String query) {

        RouteFilters routeFilters = new RouteFilters();
        routeFilters.setRoute_name(query);

        ProgressBar progressBar = findViewById(R.id.activitySearch_progress);
        progressBar.setVisibility(View.VISIBLE);

        ArrayList<Route> routes = new ArrayList<>();
        ArrayList<Route> fav_routes = new ArrayList<>();

        UserType user_type = new UserType(this);
        Log.e("Tag", user_type.getId_token());
        String id_token = user_type.getUser_type() + user_type.getId_token();
        new RoutesHTTP().getFilteredRoutes(routeFilters, id_token,
                new GetFilteredRoutesCallback(SigninFragment.current_username, id_token, routes, fav_routes, this, progressBar, recyclerView, recyclerViewAdapter));

    }

}