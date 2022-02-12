package com.cinamidea.natour_2022.utilities.http;

import com.cinamidea.natour_2022.entities.Report;
import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.entities.RouteFilters;
import com.cinamidea.natour_2022.entities.RoutesCompilation;
import com.cinamidea.natour_2022.utilities.http.callbacks.HTTPCallback;
import com.google.gson.Gson;

import okhttp3.Headers;

public class RoutesHTTP extends OkHTTPRequest {


    public void insertRoute(String user_type, Route route, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";
        String tags = (route.getTags().length() > 0) ? route.getTags() : null;

        Gson gson = new Gson();

        String json_coords = gson.toJson(route.getCoordinates());

        String request_body = "{\"name\":" + route.getName() + ",\"user_type\":" + user_type + ",\"action\":" + "INSERT"
                + ",\"description\":" + route.getDescription() + ",\"level\":" + route.getLevel() +
                ",\"duration\":" + route.getDuration() + ",\"report_count\":" + route.getReport_count() + ",\"disability_access\":"
                + route.isDisability_access() + ",\"creator_username\":" + route.getCreator_username() +
                ",\"coordinates\":" + json_coords + ",\"tags\":" + tags + ",\"image_base64\":" + route.getImage_base64() + ",\"length\":" + route.getLength() + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        startHttpRequest(callback);

    }

    public void getAllRoutes(String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        startHttpRequest(callback);

    }

    public void getNRoutes(String id_token, int start, int end, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes?start=" + start + "&end=" + end;

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        startHttpRequest(callback);

    }


    public void insertFavouriteRoute(String route_name, String username, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/routes/favourites";

        String request_body = "{\"route_name\":" + route_name + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        startHttpRequest(callback);
    }

    public void insertToVisitRoute(String route_name, String username, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/routes/to-visit";

        String request_body = "{\"route_name\":" + route_name + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        startHttpRequest(callback);
    }

    public void insertReport(Report report, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes/report";

        String request_body = "{\"issuer\":" + report.getIssuer() + ",\"route_name\":" + report.getRoute_name() + ",\"description\":"
                + report.getDescription() + ",\"title\":" + report.getTitle() + ",\"type\":" + report.getType() + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        startHttpRequest(callback);
    }

    public void createRoutesCompilation(RoutesCompilation routes_compilation, String id_token, HTTPCallback callback){

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes-compilations";

        String request_body = "{\"creator_username\":" + routes_compilation.getCreator_username() + ",\"title\":" + routes_compilation.getTitle() + ",\"description\":"
                + routes_compilation.getDescription() +"}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        startHttpRequest(callback);
    }

    public void insertRouteIntoCompilation(String username, String route_name, String compilation_id, String id_token, HTTPCallback callback){

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/"+username+"/routes/compilations/"+compilation_id;

        String request_body = "{\"route_name\":" + route_name+"}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getPostRequest(url, request_body, header);

        startHttpRequest(callback);
    }

    public void getFavouriteRoutes(String username, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/routes/favourites";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        startHttpRequest(callback);
    }

    public void deleteFavouriteRoute(String username, String id_token, String route_name, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/routes/favourites?route-name=" + route_name;

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getDeleteRequest(url, header);

        startHttpRequest(callback);

    }

    public void deleteToVisitRoute(String username, String id_token, String route_name, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/routes/to-visit?route-name=" + route_name;

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getDeleteRequest(url, header);

        startHttpRequest(callback);

    }

    public void getToVisitRoutes(String username, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/" + username + "/routes/to-visit";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        startHttpRequest(callback);

    }

    public void getUserRoutes(String creator_username, String id_token, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes?creator-username=" + creator_username;

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        startHttpRequest(callback);
    }

    public void getRoutesByLevel(String id_token, String level, HTTPCallback callback) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes?level=" + level;

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        startHttpRequest(callback);

    }

    public void getFilteredRoutes(RouteFilters route_filters, String id_token, HTTPCallback callback)
    {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes?"+"route-name="+route_filters.getRoute_name()+"&level="+route_filters.getLevel()+"&duration="+route_filters.getDuration()+
                "&disability-access"+route_filters.isIs_disability_access()+"&centre-latitude="+route_filters.getCentre().latitude+"&centre-longitude="+route_filters.getCentre().longitude+"&radius="+route_filters.getRadius()+"&tags="+route_filters.getTags();

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        startHttpRequest(callback);
    }

    public void getUserRoutesCompilation(String username, String compilation_id, String id_token, HTTPCallback callback)
    {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/"+username+"/routes/compilations/"+compilation_id;

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        startHttpRequest(callback);
    }

    public void getUserRoutesCompilations(String username, String id_token, HTTPCallback callback)
    {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/users/"+username+"/routes/compilations";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        request = getGetRequest(url, header);

        startHttpRequest(callback);

    }




    //TODO:Put immagine nel bucket
    /*public static void putImageInBucket(String user_type, String id_token, Base64 image,RoutesCallback callback){
        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        String request_body = "{\"user_type\":" + user_type + ",\"action\":" + "GET_BY_LEVEL" +
                ",\"level\":" + level + "}";

    }

     */


}