package com.cinamidea.natour_2022.routes_callbacks;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.navigation.RecyclerViewAdapter;
import com.cinamidea.natour_2022.routes_util.Route;
import com.cinamidea.natour_2022.routes_util.RoutesHTTP;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

public class GetNRoutesCallback implements RoutesCallback{

    private String user_type;
    private String username;
    private String id_token;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Activity activity;
    private ProgressBar progressBar;

    public GetNRoutesCallback(String user_type, String username, String id_token, RecyclerView recyclerView,
                              RecyclerViewAdapter recyclerViewAdapter, Activity activity, ProgressBar progressBar) {
        this.user_type = user_type;
        this.username = username;
        this.id_token = id_token;
        this.recyclerView = recyclerView;
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.activity = activity;
        this.progressBar = progressBar;
    }

    @Override
    public void handleStatus200(String response) {
        RoutesHTTP.getFavouriteRoutes(user_type, username, id_token, new GetFavouritesCallback(recyclerView, recyclerViewAdapter, activity, progressBar, false, jsonToRoutesParsing(response)));
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
