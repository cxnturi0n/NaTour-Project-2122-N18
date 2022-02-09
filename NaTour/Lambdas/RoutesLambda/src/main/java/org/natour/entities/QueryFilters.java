package org.natour.entities;

public class QueryFilters {

    private String route_name;
    private String level;
    private float duration;
    private boolean is_disability_access;
    private float centre_latitude;
    private float centre_longitude;
    private float start_point_latitude;
    private float start_point_longitude;
    private float end_point_latitude;
    private float end_point_longitude;
    private double radius;
    private int start;
    private int end;
    private String creator_username;
    private String username;
    private String tags;

    public QueryFilters(){

    }

    public QueryFilters(String route_name, String level, float duration, boolean is_disability_access, float centre_latitude, float centre_longitude, float start_point_latitude, float start_point_longitude, float end_point_latitude, float end_point_longitude, double radius, int start, int end, String creator_username, String username, String tags) {
        this.route_name = route_name;
        this.level = level;
        this.duration = duration;
        this.is_disability_access = is_disability_access;
        this.centre_latitude = centre_latitude;
        this.centre_longitude = centre_longitude;
        this.start_point_latitude = start_point_latitude;
        this.start_point_longitude = start_point_longitude;
        this.end_point_latitude = end_point_latitude;
        this.end_point_longitude = end_point_longitude;
        this.radius = radius;
        this.start = start;
        this.end = end;
        this.creator_username = creator_username;
        this.username = username;
        this.tags = tags;
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

    public float getCentre_latitude() {
        return centre_latitude;
    }

    public void setCentre_latitude(float centre_latitude) {
        this.centre_latitude = centre_latitude;
    }

    public float getCentre_longitude() {
        return centre_longitude;
    }

    public void setCentre_longitude(float centre_longitude) {
        this.centre_longitude = centre_longitude;
    }

    public float getStart_point_latitude() {
        return start_point_latitude;
    }

    public void setStart_point_latitude(float start_point_latitude) {
        this.start_point_latitude = start_point_latitude;
    }

    public float getStart_point_longitude() {
        return start_point_longitude;
    }

    public void setStart_point_longitude(float start_point_longitude) {
        this.start_point_longitude = start_point_longitude;
    }

    public float getEnd_point_latitude() {
        return end_point_latitude;
    }

    public void setEnd_point_latitude(float end_point_latitude) {
        this.end_point_latitude = end_point_latitude;
    }

    public float getEnd_point_longitude() {
        return end_point_longitude;
    }

    public void setEnd_point_longitude(float end_point_longitude) {
        this.end_point_longitude = end_point_longitude;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "QueryFilters{" +
                "route_name='" + route_name + '\'' +
                ", level='" + level + '\'' +
                ", duration=" + duration +
                ", is_disability_access=" + is_disability_access +
                ", centre_latitude=" + centre_latitude +
                ", centre_longitude=" + centre_longitude +
                ", start_point_latitude=" + start_point_latitude +
                ", start_point_longitude=" + start_point_longitude +
                ", end_point_latitude=" + end_point_latitude +
                ", end_point_longitude=" + end_point_longitude +
                ", radius=" + radius +
                ", start=" + start +
                ", end=" + end +
                ", creator_username='" + creator_username + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

