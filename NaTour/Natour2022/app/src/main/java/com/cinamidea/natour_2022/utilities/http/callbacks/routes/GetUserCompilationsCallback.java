package com.cinamidea.natour_2022.utilities.http.callbacks.routes;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.entities.RoutesCompilation;
import com.cinamidea.natour_2022.navigation.compilation.CompilationRecyclerViewAdapter;
import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

public class GetUserCompilationsCallback implements HTTPCallback {

    private Activity activity;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private CompilationRecyclerViewAdapter recyclerViewAdapter;
    private String extra;

    public GetUserCompilationsCallback(Activity activity, ProgressBar progressBar, RecyclerView recyclerView, CompilationRecyclerViewAdapter recyclerViewAdapter, String extra) {
        this.activity = activity;
        this.progressBar = progressBar;
        this.recyclerView = recyclerView;
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.extra = extra;
    }

    @Override
    public void handleStatus200(String response) {

        activity.runOnUiThread(() -> {

            progressBar.setVisibility(View.GONE);

            recyclerViewAdapter = new CompilationRecyclerViewAdapter(activity, jsonToRoutesParsing(response), extra);
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

    private ArrayList<RoutesCompilation> jsonToRoutesParsing(String response) {
        Gson gson = new Gson();
        ArrayList<RoutesCompilation> routes = new ArrayList<>();
        RoutesCompilation[] routes_array = gson.fromJson(removeQuotesAndUnescape(response), RoutesCompilation[].class);
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
