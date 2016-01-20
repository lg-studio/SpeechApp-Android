package com.tas.speech.sections.topic.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class Topic implements Serializable{

    private String Id, Name, IconUrl;
    private TopicProperties Properties;
    private ArrayList<TaskTemplate> TaskTemplates;

    public Topic() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public TopicProperties getProperties() {
        return Properties;
    }

    public void setProperties(TopicProperties properties) {
        Properties = properties;
    }

    public ArrayList<TaskTemplate> getTaskTemplates() {
        return TaskTemplates;
    }

    public void setTaskTemplates(ArrayList<TaskTemplate> taskTemplates) {
        TaskTemplates = taskTemplates;
    }
}
