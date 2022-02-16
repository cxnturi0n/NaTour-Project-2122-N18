package com.cinamidea.natour_2022.utilities.http.callbacks.routes;

import android.app.Activity;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.navigation.main.RecyclerViewAdapter;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

public class GetAllRoutesCallback implements HTTPCallback {

    private final String username;
    private final String id_token;

    private final RecyclerView recyclerView;
    private final RecyclerViewAdapter recyclerViewAdapter;
    private final Activity activity;
    private final ProgressBar progressBar;

    public GetAllRoutesCallback(String username, String id_token, RecyclerView recyclerView,
                                RecyclerViewAdapter recyclerViewAdapter, Activity activity, ProgressBar progressBar) {
        this.username = username;
        this.id_token = id_token;
        this.recyclerView = recyclerView;
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.activity = activity;
        this.progressBar = progressBar;
    }

    @Override
    public void handleStatus200(String response) {
        //new RoutesHTTP().getFavouriteRoutes(username, id_token, new GetFavouritesCallback(recyclerView, recyclerViewAdapter, activity, progressBar, false, jsonToRoutesParsing(response)));
    }

    @Override
    public void handleStatus400(String response) {

    }

    @Override
    public void handleStatus401(String response) {
        Log.e("ciao","SI SONO IO");
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
