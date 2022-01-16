package org.natour.entities;

import java.util.List;

public class Route {

    private String name;

    private String description;

    private String creator_username;

    private String level;

    private float duration;

    private int report_count;

    private boolean disability_access;

    private List<LatLng> coordinates;

    public Route(){

    }


    public Route(String name, String description, String creator_username, String level, float duration, int report_count, boolean disability_access, List <LatLng> coordinates) {

        this.name = name;
        this.description = description;
        this.creator_username = creator_username;
        this.level = level;
        this.duration = duration;
        this.report_count = report_count;
        this.disability_access = disability_access;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreator_username() {
        return creator_username;
    }

    public String getLevel() {
        return level;
    }

    public float getDuration() {
        return duration;
    }

    public int getReport_count() {
        return report_count;
    }

    public boolean isDisability_access() {
        return disability_access;
    }

    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<LatLng> coordinates) {
        this.coordinates = coordinates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreator_username(String creator_username) {
        this.creator_username = creator_username;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setReport_count(int report_count) {
        this.report_count = report_count;
    }

    public void setDisability_access(boolean disability_access) {
        this.disability_access = disability_access;
    }

}