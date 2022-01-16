package org.natour.entities;

import java.util.Date;

public class Route {

    private int route_id;

    private String name;

    private String description;

    private String creator_username;

    private String level;

    private float duration;

    private String last_modified;

    private int report_count;

    private boolean disability_access;

    public Route(int route_id, String name, String description, String creator_username, String level, float duration, String last_modified, int report_count, boolean disability_access) {

        this.route_id = route_id;
        this.name = name;
        this.description = description;
        this.creator_username = creator_username;
        this.level = level;
        this.duration = duration;
        this.last_modified = last_modified;
        this.report_count = report_count;
        this.disability_access = disability_access;

    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator_username() {
        return creator_username;
    }

    public void setCreator_username(String creator_username) {
        this.creator_username = creator_username;
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

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public int getReport_count() {
        return report_count;
    }

    public void setReport_count(int report_count) {
        this.report_count = report_count;
    }

    public boolean isDisability_access() {
        return disability_access;
    }

    public void setDisability_access(boolean disability_access) {
        this.disability_access = disability_access;
    }
}