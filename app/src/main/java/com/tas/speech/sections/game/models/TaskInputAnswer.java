package com.tas.speech.sections.game.models;

import java.util.ArrayList;

/**
 * Created by drago on 12/08/2015.
 */
public class TaskInputAnswer {
    int NodeId;
    ArrayList<String> FileNames;

    public TaskInputAnswer() {

    }

    public int getNodeId() {
        return NodeId;
    }

    public void setNodeId(int nodeId) {
        NodeId = nodeId;
    }

    public ArrayList<String> getFileNames() {
        return FileNames;
    }

    public void setFileNames(ArrayList<String> fileNames) {
        FileNames = fileNames;
    }
}
