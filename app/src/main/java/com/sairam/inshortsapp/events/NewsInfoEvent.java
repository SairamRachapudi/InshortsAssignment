package com.sairam.inshortsapp.events;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 * Event class - Project Info
 */

public class NewsInfoEvent {
    private final String projectUrl;

    public NewsInfoEvent(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getProjectUrl() {
        return projectUrl;
    }
}
