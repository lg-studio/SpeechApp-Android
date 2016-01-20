package com.tas.speech.sections.game.prompts.input.rating;

import com.tas.speech.sections.game.models.Metric;

public interface OnRatingChangedListener {
    void onRatingChanged(Metric metric, float rating);

}
