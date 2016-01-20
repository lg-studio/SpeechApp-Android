package com.tas.speech.sections.game.prompts.input.multiplechoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tas.speech.R;
import com.tas.speech.sections.game.models.PromptMultipleChoiceOption;
import com.tas.speech.sections.game.prompts.input.InputPrompt;
import com.tas.speech.sections.game.prompts.input.multiplechoice.viewholders.MultipleChoicePictureInputViewHolder;

import java.util.ArrayList;

/**
 * Created by drago_000 on 07/08/2015.
 */
public class MultipleChoicePictureInputPrompt implements InputPrompt {

    private Context mCtx;
    private LinearLayout mLlFooter;
    private ArrayList<PromptMultipleChoiceOption> mOptions;

    public MultipleChoicePictureInputPrompt(Context ctx, LinearLayout llFooter, ArrayList<PromptMultipleChoiceOption> options) {
        mCtx = ctx;
        mLlFooter = llFooter;
        mOptions = options;
    }

    @Override
    public void render() {
        LayoutInflater layoutInflater = (LayoutInflater)
                mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View multipleChoicePictureView = layoutInflater.inflate(R.layout.layout_prompt_multiple_image_input, mLlFooter, false);

        ImageView ivSubmit = (ImageView)multipleChoicePictureView.findViewById(R.id.iv_submit);
        ivSubmit.setAlpha(90);

        mLlFooter.removeAllViews();
        mLlFooter.addView(multipleChoicePictureView);

        // init the view holder
        MultipleChoicePictureInputViewHolder submitViewHolder = new MultipleChoicePictureInputViewHolder(mCtx, multipleChoicePictureView);
        submitViewHolder.bind(mOptions);

        Animation animation = AnimationUtils.loadAnimation(mCtx, R.anim.slide_up_in);
        mLlFooter.startAnimation(animation);
        mLlFooter.setVisibility(View.VISIBLE);
    }
}
