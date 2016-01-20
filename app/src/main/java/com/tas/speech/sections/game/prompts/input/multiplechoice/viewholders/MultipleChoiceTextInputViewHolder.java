package com.tas.speech.sections.game.prompts.input.multiplechoice.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tas.speech.R;
import com.tas.speech.sections.game.models.PromptMultipleChoiceOption;

import java.util.ArrayList;

/**
 * Created by drago_000 on 07/08/2015.
 */
public class MultipleChoiceTextInputViewHolder {

    private static final int OPTION_NO = 4;

    private ArrayList<TextView> tvOptions;
    private ImageView ivSubmit;

    private int currentSelectedOption;

    private Context mCtx;

    public MultipleChoiceTextInputViewHolder(Context ctx, View v) {
        tvOptions = new ArrayList<>();

        mCtx = ctx;

        tvOptions.add((TextView)v.findViewById(R.id.tv_option1));
        tvOptions.add((TextView)v.findViewById(R.id.tv_option2));
        tvOptions.add((TextView)v.findViewById(R.id.tv_option3));
        tvOptions.add((TextView)v.findViewById(R.id.tv_option4));

        ivSubmit = (ImageView)v.findViewById(R.id.iv_submit);

        currentSelectedOption = -1;
    }

    public void bind(ArrayList<PromptMultipleChoiceOption> options) {

        for(int i = 0; i < OPTION_NO; ++i) {
            tvOptions.get(i).setText(options.get(i).getText());
            tvOptions.get(i).setTag(String.valueOf(i));
            tvOptions.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(Integer.parseInt((String) v.getTag()));
                }
            });
        }

    }

    private void selectOption(int optionIndex) {
        if(currentSelectedOption != optionIndex) {
            if(currentSelectedOption != -1) {
                tvOptions.get(currentSelectedOption).setBackgroundDrawable(mCtx.getResources()
                        .getDrawable(R.drawable.round_rectangle_multiple_text));
            }
            else {
                ivSubmit.setAlpha(250);
            }
            tvOptions.get(optionIndex).setBackgroundDrawable(mCtx.getResources()
                    .getDrawable(R.drawable.round_rectangle_multiple_text_selected));
            currentSelectedOption = optionIndex;
        }
    }

}
