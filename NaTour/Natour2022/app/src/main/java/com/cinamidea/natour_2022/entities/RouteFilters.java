package com.cinamidea.natour_2022.entities;

import com.google.android.gms.maps.model.LatLng;

public class RouteFilters {

    private String route_name = "";
    private String levels = "";
    private int duration = 0;
    private boolean is_disability_access = false;
    private LatLng centre = new LatLng(0,0);
    private int radius = 0;
    private String tags = "";

    public RouteFilters() {
    }

    public RouteFilters(String route_name, String levels, int duration, boolean is_disability_access, LatLng centre, int radius,String tags) {
        this.route_name = route_name;
        this.levels = levels;
        this.duration = duration;
        this.is_disability_access = is_disability_access;
        this.centre = centre;
        this.radius = radius;
        this.tags = tags;
    }


    public String getTags() {
        return tags;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public String getLevel() {
        return levels;
    }

    public void setLevel(String level) {
        this.levels = level;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isIs_disability_access() {
        return is_disability_access;
    }

    public void setIs_disability_access(boolean is_disability_access) {
        this.is_disability_access = is_disability_access;
    }

    public LatLng getCentre() {
        return centre;
    }

    public void setCentre(LatLng centre) {
        this.centre = centre;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}