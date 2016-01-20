package com.tas.speech.sections.game;

import android.util.SparseArray;

import com.tas.speech.sections.game.models.TaskInputRatingMetric;

import java.util.ArrayList;

/**
 * Created by drago on 12/08/2015.
 */
public interface GameEventListener {
    void onTaskSubmit();
    boolean onRatingSubmit(String nodeId, SparseArray<TaskInputRatingMetric> ratedMetrics);
    boolean onRecordingSubmit(String nodeId, ArrayList<String> fileNames);
}
