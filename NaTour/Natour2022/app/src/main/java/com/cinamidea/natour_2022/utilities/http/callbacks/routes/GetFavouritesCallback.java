package com.cinamidea.natour_2022.utilities.http.callbacks.routes;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.navigation.main.RecyclerViewAdapter;
import com.cinamidea.natour_2022.navigation.search.geosearch.CompletedGeoSearchActivity;
import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetFavouritesCallback implements HTTPCallback {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private final Activity activity;
    private final ProgressBar progressBar;
    private final ArrayList<Route> routes;
    private ArrayList<Route> fav_routes;
    private boolean is_to_visit_fragment;
    private boolean is_search = false;

    public GetFavouritesCallback(Activity activity, ProgressBar progressBar, ArrayList<Route> routes, ArrayList<Route> fav_routes) {
        this.activity = activity;
        this.progressBar = progressBar;
        this.fav_routes = fav_routes;
        this.routes = routes;
        is_search = true;
    }

    public GetFavouritesCallback(RecyclerView recyclerView, RecyclerViewAdapter recyclerViewAdapter, Activity activity, ProgressBar progressBar, boolean is_to_visit_fragment, ArrayList<Route> routes) {
        this.recyclerView = recyclerView;
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.activity = activity;
        this.progressBar = progressBar;
        this.is_to_visit_fragment = is_to_visit_fragment;
        this.routes = routes;
    }

    @Override
    public void handleStatus200(String response) {
        activity.runOnUiThread(() -> {

            progressBar.setVisibility(View.GONE);

            if(!is_search) {

                recyclerViewAdapter = new RecyclerViewAdapter(activity, routes, jsonToRoutesParsing(response), is_to_visit_fragment);
                recyclerView.setAdapter(recyclerViewAdapter);

            }else {

                fav_routes = jsonToRoutesParsing(response);
                CompletedGeoSearchActivity.setFav_routes(fav_routes);
                CompletedGeoSearchActivity.setRoutes(routes);
                activity.startActivity(new Intent(activity, CompletedGeoSearchActivity.class));

            }
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
