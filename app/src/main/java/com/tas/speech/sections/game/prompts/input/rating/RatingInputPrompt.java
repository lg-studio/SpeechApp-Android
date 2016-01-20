package com.tas.speech.sections.game.prompts.input.rating;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.tas.speech.R;
import com.tas.speech.sections.game.Game;
import com.tas.speech.sections.game.GameEventListener;
import com.tas.speech.sections.game.OutputPromptsView;
import com.tas.speech.sections.game.models.Prompt;
import com.tas.speech.sections.game.models.PromptProperties;
import com.tas.speech.sections.game.models.TaskInputRatingMetric;
import com.tas.speech.sections.game.prompts.PromptTypes;
import com.tas.speech.sections.game.prompts.input.InputPrompt;
import com.tas.speech.sections.game.models.Metric;
import com.tas.speech.sections.main.MainActivity;

import java.util.List;

/**
 * RatingInputPrompt controls the ratings for all the metrics
 */
public class RatingInputPrompt implements OnRatingChangedListener, InputPrompt {
    private static final String TAG = "RatingInputPrompt";

    private static final int ALPHA_ACTIVE_VALUE = 255;
    private static final int ALPHA_INACTIVE_VALUE = 90;

    private Context mCtx;

    private LinearLayout mLlFooter;
    private ImageView ivCheck;

    private OutputPromptsView mOutputPromptsView;
    private GameEventListener mGameEventListener;
    private View.OnClickListener checkClickListener;

    private List<Metric> mMetrics;
    private SparseArray<TaskInputRatingMetric> taskInputRatingMetrics;
    private String mNodeId;

    public RatingInputPrompt(Context ctx, LinearLayout llFooter, OutputPromptsView outputPromptsView,
                             Prompt prompt, GameEventListener gameEventListener) {
        mLlFooter = llFooter;
        mCtx = ctx;
        mMetrics = prompt.getProperties().getMetrics();
        mNodeId = prompt.getNodeId();
        mOutputPromptsView = outputPromptsView;
        mGameEventListener = gameEventListener;

        taskInputRatingMetrics = new SparseArray<>();
    }

    public void render() {
        LayoutInflater layoutInflater = (LayoutInflater)
                mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Iterate through metrics and display the corresponding view in the footer
        for(Metric metric : mMetrics) {
            RatingViewHolder ratingViewHolder = RatingViewHolderFactory.getRatingViewHolder(metric,
                    mLlFooter, layoutInflater);
            ratingViewHolder.bind(metric, this);

            mLlFooter.addView(ratingViewHolder.getParentView());
            mLlFooter.addView(layoutInflater.inflate(R.layout.layout_footer_divider, mLlFooter,false));
        }

        // Add the rating confirmation button
        View check = layoutInflater.inflate(R.layout.layout_check, mLlFooter, false);
        ivCheck = (ImageView)check.findViewById(R.id.iv_check);
        ivCheck.setAlpha(ALPHA_INACTIVE_VALUE);
        mLlFooter.addView(check);

        checkClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the all the rating views and hide the footer
                Animation animation = AnimationUtils.loadAnimation(mCtx, R.anim.slide_down_out);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        mLlFooter.setVisibility(View.GONE);
                        mLlFooter.removeAllViews();
                    }
                });

                Gson gson = new Gson();
                String taskInputMetricJson = gson.toJson(taskInputRatingMetrics);
                Log.d(TAG, "taskInputRatingMetrics: " + taskInputMetricJson);

                // If submit action has been completed successfully
                if(mGameEventListener.onRatingSubmit(mNodeId, taskInputRatingMetrics)) {
                    mLlFooter.startAnimation(animation);

                    // Add a text output prompt to the game configuration with the ratings the user gave
                    mOutputPromptsView.addOutputPrompt(generateRatingPrompt());
                }
            }
        };

        // Disable the click listener on the rating confirmation button
        ivCheck.setOnClickListener(null);

        // Hide the footer
        Animation animation = AnimationUtils.loadAnimation(mCtx, R.anim.slide_up_in);
        mLlFooter.startAnimation(animation);
        mLlFooter.setVisibility(View.VISIBLE);
    }

    private String buildRatingString() {
        String rating = "";

        for(Metric metric : mMetrics) {
            TaskInputRatingMetric taskInputRatingMetric =
                    taskInputRatingMetrics.get(Integer.parseInt(metric.getId()));
            if(!metric.getType().equals("Thumbs")) {
                rating += (int)(taskInputRatingMetric.getRating() * 100) + "%";
            }
            else {
                if(taskInputRatingMetric.getRating() == 1) {
                    rating += "100%";
                }
                else {
                    rating += "0%";
                }
            }
            rating += " in " + metric.getProperties().getCategory() + ", ";
        }

        rating = rating.substring(0, rating.length() - 2);

        return rating;
    }

    private Prompt generateRatingPrompt() {
        Prompt prompt = new Prompt();
        prompt.setId(Game.getNextCustomPromptSeq());
        prompt.setType(PromptTypes.TEXT);
        PromptProperties properties = new PromptProperties();
        properties.setPictureUrl(MainActivity.myPictureUrl);
        properties.setText(String.format(mCtx.getString(R.string.rating_message),
                Game.getRatedUser(), buildRatingString()));
        properties.setName(mCtx.getString(R.string.my_message_author));
        properties.setPersonalMessage("1");
        prompt.setProperties(properties);

        return prompt;
    }

    // If one of the ratings changed, this method is called
    @Override
    public void onRatingChanged(Metric metric, float rating) {
        int key = Integer.parseInt(metric.getId());
        if(taskInputRatingMetrics.get(key) != null) {
            taskInputRatingMetrics.remove(key);
        }
        TaskInputRatingMetric taskInputMetric = new TaskInputRatingMetric();
        taskInputMetric.setId(metric.getId());
        taskInputMetric.setRating(rating);
        taskInputMetric.setType(metric.getType());
        taskInputRatingMetrics.put(key, taskInputMetric);

        // If the user rated all the metrics enable the rating confirmation button
        if(taskInputRatingMetrics.size() == mMetrics.size()) {
            ivCheck.setAlpha(ALPHA_ACTIVE_VALUE);
            ivCheck.setOnClickListener(checkClickListener);
        }
    }
}
