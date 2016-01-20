package com.tas.speech.sections.game.models;

import java.util.ArrayList;

/**
 * Created by drago on 12/08/2015.
 */
public class TaskInputRating {

    private int NodeId;
    private String UserId;
    private ArrayList<TaskInputRatingMetric> Metrics;

    public TaskInputRating() {

    }

    public int getNodeId() {
        return NodeId;
    }

    public void setNodeId(int nodeId) {
        NodeId = nodeId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public ArrayList<TaskInputRatingMetric> getMetrics() {
        return Metrics;
    }

    public void setMetrics(ArrayList<TaskInputRatingMetric> metrics) {
        Metrics = metrics;
    }
}

