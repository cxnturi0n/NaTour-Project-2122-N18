package com.cinamidea.natour_2022.navigation.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.signin.SigninFragment;
import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.entities.RouteFilters;
import com.cinamidea.natour_2022.navigation.main.recyclerview.RecyclerViewAdapter;
import com.cinamidea.natour_2022.utilities.UserType;
import com.cinamidea.natour_2022.utilities.auth.UserSharedPreferences;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.routes.GetFilteredRoutesCallback;
import com.paulrybitskyi.persistentsearchview.PersistentSearchView;
import com.paulrybitskyi.persistentsearchview.listeners.OnSearchConfirmedListener;
import com.paulrybitskyi.persistentsearchview.listeners.OnSearchQueryChangeListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;
    public static RecyclerViewAdapter recyclerViewAdapter;
    private PersistentSearchView persistentSearchView;
    private ImageButton button_back;

    private boolean is_searchview_empty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        button_back = findViewById(R.id.activitySearch_backbutton);
        recyclerView = findViewById(R.id.activitySearch_recyclerview);
        persistentSearchView = findViewById(R.id.activitySearch_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        button_back.setOnClickListener(v -> {

            persistentSearchView.setVisibility(View.VISIBLE);
            button_back.setVisibility(View.GONE);

        });

        persistentSearchView.setOnSearchConfirmedListener(new OnSearchConfirmedListener() {
            @Override
            public void onSearchConfirmed(PersistentSearchView searchView, String query) {

                hideSoftKeyboard(SearchActivity.this);
                persistentSearchView.setVisibility(View.GONE);
                button_back.setVisibility(View.VISIBLE);
                loadRecyclerView(query);

            }
        });

        persistentSearchView.setOnSearchQueryChangeListener(new OnSearchQueryChangeListener() {
            @Override
            public void onSearchQueryChanged(PersistentSearchView searchView, String oldQuery, String newQuery) {
                if(persistentSearchView.isInputQueryEmpty()) {

                    persistentSearchView.setLeftButtonDrawable(R.drawable.ic_back);
                    is_searchview_empty = true;

                }
                else {

                    persistentSearchView.setLeftButtonDrawable(R.drawable.stream_ui_ic_search);
                    is_searchview_empty = false;

                }

            }
        });

        persistentSearchView.setOnLeftBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(is_searchview_empty)
                    finish();
                else
                    persistentSearchView.confirmSearchAction();

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

        UserSharedPreferences user_type = new UserSharedPreferences(this);
        Log.e("Tag", user_type.getId_token());
        String id_token = user_type.getUser_type() + user_type.getId_token();
        new RoutesHTTP().getFilteredRoutes(routeFilters, id_token,
                new GetFilteredRoutesCallback(new UserType(getApplicationContext()).getUsername(), id_token, routes, fav_routes, this, progressBar, recyclerView, recyclerViewAdapter));


    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

}