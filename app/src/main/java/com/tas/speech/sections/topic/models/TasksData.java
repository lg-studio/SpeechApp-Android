package com.tas.speech.sections.topic.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class TasksData implements Serializable {

    private ArrayList<Topic> TopicList;

    public TasksData() {

    }

    public ArrayList<Topic> getTopicList() {
        return TopicList;
    }

    public void setTopicList(ArrayList<Topic> topicList) {
        TopicList = topicList;
    }
}
