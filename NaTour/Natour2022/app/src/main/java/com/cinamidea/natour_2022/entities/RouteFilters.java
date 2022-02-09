package com.cinamidea.natour_2022.entities;

import com.google.android.gms.maps.model.LatLng;

public class RouteFilters {

    private String route_name;
    private String level;
    private float duration;
    private boolean is_disability_access;
    private LatLng centre;
    private double radius;

    public RouteFilters() {
    }

    public RouteFilters(String route_name, String level, float duration, boolean is_disability_access, LatLng centre, double radius) {
        this.route_name = route_name;
        this.level = level;
        this.duration = duration;
        this.is_disability_access = is_disability_access;
        this.centre = centre;
        this.radius = radius;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
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

    public void setRadius(double radius) {
        this.radius = radius;
    }
}