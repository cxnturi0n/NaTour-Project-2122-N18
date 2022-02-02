package com.cinamidea.natour_2022.routes_callbacks;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.navigation.RecyclerViewAdapter;
import com.cinamidea.natour_2022.routes_util.Route;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

public class GetFavouritesCallback implements RoutesCallback{

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Activity activity;
    private ProgressBar progressBar;
    private ArrayList<Route> routes;
    private boolean is_to_visit_fragment;

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
            recyclerViewAdapter = new RecyclerViewAdapter(activity, routes, jsonToRoutesParsing(response), is_to_visit_fragment);
            recyclerView.setAdapter(recyclerViewAdapter);
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
        for(int i = 0; i < routes_array.length;i++) {

            routes.add(routes_array[i]);
        }

        return routes;

    }

    private String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }

}
