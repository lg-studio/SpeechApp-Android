package com.tas.speech.sections.game.prompts.input.rating.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tas.speech.R;
import com.tas.speech.sections.game.prompts.input.rating.OnRatingChangedListener;
import com.tas.speech.sections.game.models.Metric;
import com.tas.speech.sections.game.prompts.input.rating.RatingViewHolder;

/**
 * ThumbsRatingViewHolder
 */
public class ThumbsRatingViewHolder implements RatingViewHolder {
    private static final int ALPHA_ACTIVE_VALUE = 255;
    private static final int ALPHA_INACTIVE_VALUE = 90;

    private static final int THUMBS_UP_RATING = 1;
    private static final int THUMBS_DOWN_RATING = 0;

    private View mParentView;
    private TextView tvFeedBackQuestion;
    private ImageView ivThumbsUp, ivThumbsDown;

    private boolean thumbsUp;
    private Metric metric;
    private OnRatingChangedListener mRatingChangedListener;

    public ThumbsRatingViewHolder(View v) {
        tvFeedBackQuestion = (TextView)v.findViewById(R.id.tv_feedback_question);

        ivThumbsDown = (ImageView)v.findViewById(R.id.iv_thumbs_down);
        ivThumbsUp = (ImageView)v.findViewById(R.id.iv_thumbs_up);

        mParentView = v;

        thumbsUp = false;
    }

    public void bind(Metric m, OnRatingChangedListener ratingChangedListener) {
        mRatingChangedListener = ratingChangedListener;
        metric = m;

        tvFeedBackQuestion.setText(m.getProperties().getText());

        ivThumbsDown.setAlpha(ALPHA_INACTIVE_VALUE);
        ivThumbsUp.setAlpha(ALPHA_INACTIVE_VALUE);

        ivThumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thumbsUp = false;

                ivThumbsDown.setAlpha(ALPHA_ACTIVE_VALUE);
                ivThumbsUp.setAlpha(ALPHA_INACTIVE_VALUE);

                mRatingChangedListener.onRatingChanged(metric, getRating());
            }
        });

        ivThumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thumbsUp = true;

                ivThumbsUp.setAlpha(ALPHA_ACTIVE_VALUE);
                ivThumbsDown.setAlpha(ALPHA_INACTIVE_VALUE);

                mRatingChangedListener.onRatingChanged(metric, getRating());
            }
        });
    }

    public View getParentView() {
        return mParentView;
    }

    private int getRating() {
        if(thumbsUp) {
            return THUMBS_UP_RATING;
        }
        else {
            return THUMBS_DOWN_RATING;
        }
    }
}
