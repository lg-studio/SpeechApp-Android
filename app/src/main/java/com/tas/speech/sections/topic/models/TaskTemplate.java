package com.tas.speech.sections.topic.models;

import java.io.Serializable;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class TaskTemplate implements Serializable {

    private String Id, Type, Name, IconUrl;
    private TaskProperties Properties;

    public TaskTemplate() {

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIconUrl() {
        return IconUrl;
    }

    public void setIconUrl(String iconUrl) {
        IconUrl = iconUrl;
    }

    public TaskProperties getProperties() {
        return Properties;
    }

    public void setProperties(TaskProperties properties) {
        Properties = properties;
    }
}
