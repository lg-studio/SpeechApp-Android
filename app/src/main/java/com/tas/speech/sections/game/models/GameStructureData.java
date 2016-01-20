package com.tas.speech.sections.game.models;

import java.util.ArrayList;

/**
 * Created by drago_000 on 17/07/2015.
 */
public class GameStructureData {
    String TaskId;
    ArrayList<Prompt> Items;

    public GameStructureData() {

    }

    public ArrayList<Prompt> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Prompt> items) {
        Items = items;
    }

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }
}
