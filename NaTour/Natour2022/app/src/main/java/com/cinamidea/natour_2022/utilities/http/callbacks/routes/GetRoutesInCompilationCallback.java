package com.cinamidea.natour_2022.utilities.http.callbacks.routes;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.auth.signin.SigninFragment;
import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.navigation.main.RecyclerViewAdapter;
import com.cinamidea.natour_2022.utilities.auth.UserSharedPreferences;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

public class GetRoutesInCompilationCallback implements HTTPCallback {

    private Activity activity;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    public GetRoutesInCompilationCallback(Activity activity, ProgressBar progressBar, RecyclerView recyclerView, RecyclerViewAdapter recyclerViewAdapter) {
        this.activity = activity;
        this.progressBar = progressBar;
        this.recyclerView = recyclerView;
        this.recyclerViewAdapter = recyclerViewAdapter;
    }

    @Override
    public void handleStatus200(String response) {

        activity.runOnUiThread(() -> {

            progressBar.setVisibility(View.GONE);
            UserSharedPreferences userSharedPreferences = new UserSharedPreferences(activity);

            new RoutesHTTP().getFavouriteRoutes(SigninFragment.current_username, userSharedPreferences.getUser_type()+ userSharedPreferences.getId_token(), new GetFavouritesCallback(recyclerView, recyclerViewAdapter, activity, progressBar, false, jsonToRoutesParsing(response)));

        });

    }

    @Override
    public void handleStatus400(String response) {

    }

    @Override
    public void handleStatus401(String response) {

    }

    @Override
    public void handleStatus500(String response) {

    }

    @Override
    public void handleRequestException(String message) {

    }

    private ArrayList<Route> jsonToRoutesParsing(String response) {
        Gson gson = new Gson();
        ArrayList<Route> routes = new ArrayList<>();
        Route[] routes_array = gson.fromJson(removeQuotesAndUnescape(response), Route[].class);
        for (int i = 0; i < routes_array.length; i++) {

            routes.add(routes_array[i]);
        }

        return routes;

    }

    private String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }

}
