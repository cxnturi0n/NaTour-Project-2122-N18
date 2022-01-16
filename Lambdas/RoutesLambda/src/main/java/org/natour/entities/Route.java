package org.natour.entities;

import java.util.Date;
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
}