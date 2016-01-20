package com.tas.speech.sections.game.prompts.input.rating;

import android.view.View;

import com.tas.speech.sections.game.models.Metric;

/**
 * Created by drago_000 on 16/07/2015.
 */
public interface RatingViewHolder {

    void bind(Metric m, OnRatingChangedListener ratingChangedListener);

    View getParentView();
}
