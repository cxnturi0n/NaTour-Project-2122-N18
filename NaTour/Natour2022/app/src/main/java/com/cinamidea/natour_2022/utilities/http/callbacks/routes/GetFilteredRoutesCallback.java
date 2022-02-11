package com.cinamidea.natour_2022.utilities.http.callbacks.routes;

import android.util.Log;

import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

public class GetFilteredRoutesCallback implements HTTPCallback {
    @Override
    public void handleStatus200(String response) {
        Log.e("Filtered routes",response);
        List<Route> routes = jsonToRoutesParsing(response);
        for (int i =0;i<routes.size();i++){
            System.out.println(routes.get(i).toString());

        }

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
