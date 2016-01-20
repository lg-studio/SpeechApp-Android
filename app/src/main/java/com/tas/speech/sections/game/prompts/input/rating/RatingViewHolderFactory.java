package com.tas.speech.sections.game.prompts.input.rating;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.tas.speech.R;
import com.tas.speech.sections.game.models.Metric;
import com.tas.speech.sections.game.prompts.input.rating.viewholders.SliderRatingViewHolder;
import com.tas.speech.sections.game.prompts.input.rating.viewholders.ThumbsRatingViewHolder;

/**
 * RatingViewHolderFactory class inflates rating views and produces the associated view holders
 * depending on metric type
 */
public class RatingViewHolderFactory {

    public static RatingViewHolder getRatingViewHolder(Metric metric, LinearLayout llfooter,
                                             LayoutInflater layoutInflater) {
        View feedbackView;
        RatingViewHolder ratingViewHolder = null;
        if(metric.getType().equals("Slider")) {
            feedbackView = layoutInflater.inflate(R.layout.layout_rating_slider, llfooter, false);
            ratingViewHolder = new SliderRatingViewHolder(feedbackView);

        }
        else if(metric.getType().equals("Thumbs")) {
            feedbackView = layoutInflater.inflate(R.layout.layout_rating_thumbs, llfooter, false);
            ratingViewHolder = new ThumbsRatingViewHolder(feedbackView);
        }
        return ratingViewHolder;
    }
}
