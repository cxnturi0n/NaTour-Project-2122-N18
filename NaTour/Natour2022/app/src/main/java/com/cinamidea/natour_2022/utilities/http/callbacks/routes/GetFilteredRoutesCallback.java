package com.cinamidea.natour_2022.utilities.http.callbacks.routes;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.navigation.main.RecyclerViewAdapter;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

public class GetFilteredRoutesCallback implements HTTPCallback {

    private String username;
    private String id_token;

    private ArrayList<Route> routes;
    private ArrayList<Route> fav_routes;
    private Activity activity;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    public GetFilteredRoutesCallback(String username, String id_token, ArrayList<Route> routes,  ArrayList<Route> fav_routes, Activity activity, ProgressBar progressBar, RecyclerView recyclerView, RecyclerViewAdapter recyclerViewAdapter) {
        this.username = username;
        this.id_token = id_token;
        this.activity = activity;
        this.fav_routes = fav_routes;
        this.routes = routes;
        this.progressBar = progressBar;
        this.recyclerView = recyclerView;
        this.recyclerViewAdapter = recyclerViewAdapter;
    }

    public GetFilteredRoutesCallback(String username, String id_token, ArrayList<Route> routes,  ArrayList<Route> fav_routes, Activity activity, ProgressBar progressBar) {
        this.username = username;
        this.id_token = id_token;
        this.activity = activity;
        this.fav_routes = fav_routes;
        this.routes = routes;
        this.progressBar = progressBar;
    }

    @Override
    public void handleStatus200(String response) {

        activity.runOnUiThread(() -> {

            routes = jsonToRoutesParsing(response);
            //if(recyclerView == null)
               // new RoutesHTTP().getFavouriteRoutes(username, id_token, new GetFavouritesCallback(activity,progressBar, routes, fav_routes));
            //else
                //ew RoutesHTTP().getFavouriteRoutes(username, id_token, new GetFavouritesCallback(activity, progressBar, routes, fav_routes, recyclerView, recyclerViewAdapter));

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
