package com.tas.speech.sections.game.prompts.input.submit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.tas.speech.R;
import com.tas.speech.sections.game.GameEventListener;
import com.tas.speech.sections.game.prompts.input.InputPrompt;
import com.tas.speech.sections.game.prompts.input.submit.viewholders.SubmitInputViewHolder;

public class SubmitInputPrompt implements InputPrompt {

    private Context mCtx;
    private LinearLayout mLlFooter;
    private GameEventListener mGameEventListener;

    public SubmitInputPrompt(Context ctx, LinearLayout llFooter, GameEventListener gameEventListener) {
        mCtx = ctx;
        mLlFooter = llFooter;
        mGameEventListener = gameEventListener;
    }

    @Override
    public void render() {
        LayoutInflater layoutInflater = (LayoutInflater)
                mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View submitView = layoutInflater.inflate(R.layout.layout_prompt_submit_input, mLlFooter, false);
        mLlFooter.removeAllViews();
        mLlFooter.addView(submitView);

        // init the view holder
        SubmitInputViewHolder submitViewHolder = new SubmitInputViewHolder(mCtx, submitView, mGameEventListener);
        submitViewHolder.bind();
        Animation animation = AnimationUtils.loadAnimation(mCtx, R.anim.slide_up_in);
        mLlFooter.startAnimation(animation);
        mLlFooter.setVisibility(View.VISIBLE);
    }
}
