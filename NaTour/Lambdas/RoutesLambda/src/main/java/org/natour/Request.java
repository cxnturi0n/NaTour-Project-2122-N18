package org.natour;

import org.natour.entities.Report;
import org.natour.entities.Route;

public class Request {

    private String action;
    private String id_token;
    private Route route;
    private Report report;
    private String user_type;
    private String username;
    private String profile_image_base64;
    private int start;
    private int end;
    private int radius;
    private String locality;

    public Request(){

    }

    public Request(String action, String id_token, Route route, String user_type, int start, int end, String username, String profile_image_base64, Report report, int radius, String locality) {
        this.action = action;
        this.id_token = id_token;
        this.route = route;
        this.user_type = user_type;
        this.start = start;
        this.end = end;
        this.username = username;
        this.profile_image_base64 = profile_image_base64;
        this.report = report;
        this.radius = radius;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile_image_base64() {
        return profile_image_base64;
    }

    public void setProfile_image_base64(String profile_image_base64) {
        this.profile_image_base64 = profile_image_base64;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}
