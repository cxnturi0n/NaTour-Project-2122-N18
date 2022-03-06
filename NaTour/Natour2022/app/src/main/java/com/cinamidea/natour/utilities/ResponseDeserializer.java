package com.cinamidea.natour.utilities;

import com.cinamidea.natour.entities.Route;
import com.cinamidea.natour.entities.RoutesCompilation;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

public class ResponseDeserializer {

    public static ArrayList<Route> jsonToRoutesList(String response) {
        Gson gson = new Gson();
        ArrayList<Route> routes = new ArrayList<>();
        Route[] routes_array = gson.fromJson(removeQuotesAndUnescape(response), Route[].class);
        for (int i = 0; i < routes_array.length; i++) {

            routes.add(routes_array[i]);
        }
        return routes;
    }

    public static ArrayList<RoutesCompilation> jsonToRoutesCompilationsList(String response) {
        Gson gson = new Gson();
        ArrayList<RoutesCompilation> routes = new ArrayList<>();
        RoutesCompilation[] routes_array = gson.fromJson(removeQuotesAndUnescape(response), RoutesCompilation[].class);
        for (int i = 0; i < routes_array.length; i++) {

            routes.add(routes_array[i]);
        }
        return routes;
    }

    public static Route [] jsonToRoutesArray(String response) {
        return new Gson().fromJson(removeQuotesAndUnescape(response), Route[].class);
    }

    public static String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }

    public static String jsonToMessage(String response){
        return new Gson().fromJson(removeQuotesAndUnescape(response), Message.class).message;
    }

    public class Message {

        public String message;

    }





}
