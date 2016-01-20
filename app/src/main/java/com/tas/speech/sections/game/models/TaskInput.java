package com.tas.speech.sections.game.models;

import java.util.ArrayList;

/**
 * Created by drago on 12/08/2015.
 */
public class TaskInput {
    String TaskId;
    ArrayList<TaskInputRating> Ratings;
    ArrayList<TaskInputAnswer> Answers;

    public TaskInput() {

    }

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    public ArrayList<TaskInputRating> getRatings() {
        return Ratings;
    }

    public void setRatings(ArrayList<TaskInputRating> ratings) {
        Ratings = ratings;
    }

    public ArrayList<TaskInputAnswer> getAnswers() {
        return Answers;
    }

    public void setAnswers(ArrayList<TaskInputAnswer> answers) {
        Answers = answers;
    }
}
