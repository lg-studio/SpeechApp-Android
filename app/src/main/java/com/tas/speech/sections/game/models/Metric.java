package com.tas.speech.sections.game.models;

/**
 * Created by drago_000 on 10/07/2015.
 */
public class Metric {

    private String Id;
    private String Type;
    private MetricProperties Properties;

    public Metric() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public MetricProperties getProperties() {
        return Properties;
    }

    public void setProperties(MetricProperties properties) {
        Properties = properties;
    }
}
