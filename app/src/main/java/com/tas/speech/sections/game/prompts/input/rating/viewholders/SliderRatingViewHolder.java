package com.tas.speech.sections.game.prompts.input.rating.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tas.speech.R;
import com.tas.speech.sections.game.prompts.input.rating.OnRatingChangedListener;
import com.tas.speech.sections.game.models.Metric;
import com.tas.speech.sections.game.prompts.input.rating.RatingViewHolder;

/**
 * SliderRatingViewHolder
 */
public class SliderRatingViewHolder implements SeekBar.OnSeekBarChangeListener, RatingViewHolder {
    private static final String TAG = "SliderRatingViewHolder";

    private static final int SLIDER_MAX_PROGRESS = 100;
    private static final int SLIDER_HALF_PROGRESS = 50;

    private TextView tvFeedBackQuestion, tvFeedbackPercentage;
    private SeekBar sbFeedBack;
    private View mParentView;
    private Metric metric;
    private OnRatingChangedListener mRatingChangedListener;

    public SliderRatingViewHolder(View v) {
        tvFeedBackQuestion = (TextView)v.findViewById(R.id.tv_feedback_question);
        tvFeedbackPercentage = (TextView)v.findViewById(R.id.tv_feedback_percentage);
        sbFeedBack = (SeekBar)v.findViewById(R.id.sb_feedback);

        mParentView = v;
    }

    public void bind(Metric m, OnRatingChangedListener ratingChangedListener) {
        metric = m;
        mRatingChangedListener = ratingChangedListener;

        sbFeedBack.setMax(SLIDER_MAX_PROGRESS);
        sbFeedBack.setProgress(SLIDER_HALF_PROGRESS);

        sbFeedBack.setOnSeekBarChangeListener(this);

        tvFeedbackPercentage.setText(SLIDER_HALF_PROGRESS + "%");
        tvFeedBackQuestion.setText(m.getProperties().getText());
    }

    @Override
    public View getParentView() {
        return mParentView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser) {
            tvFeedbackPercentage.setText(progress + "%");
            mRatingChangedListener.onRatingChanged(metric, (float)progress / SLIDER_MAX_PROGRESS);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
