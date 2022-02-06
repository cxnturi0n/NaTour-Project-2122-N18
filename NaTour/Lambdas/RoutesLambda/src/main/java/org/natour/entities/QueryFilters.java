package org.natour.entities;

public class QueryFilters {

    private String route_name;
    private String level;
    private float duration;
    private boolean is_disability_access;
    private LatLng centre;
    private double radius;
    private int start;
    private int end;
    private String creator_username;
    private String username;

    public QueryFilters(){

    }

    public QueryFilters(String route_name, String level, float duration, boolean is_disability_access, LatLng centre, double radius, int start, int end, String creator_username, String username) {
        this.route_name = route_name;
        this.level = level;
        this.duration = duration;
        this.is_disability_access = is_disability_access;
        this.centre = centre;
        this.radius = radius;
        this.start = start;
        this.end = end;
        this.creator_username = creator_username;
        this.username = username;
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

    public String getCreator_username() {
        return creator_username;
    }

    public void setCreator_username(String creator_username) {
        this.creator_username = creator_username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

