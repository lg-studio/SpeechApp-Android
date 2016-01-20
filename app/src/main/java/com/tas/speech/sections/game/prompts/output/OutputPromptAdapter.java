package com.tas.speech.sections.game.prompts.output;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.tas.speech.R;
import com.tas.speech.sections.game.OutputPromptsView;
import com.tas.speech.sections.game.models.Prompt;
import com.tas.speech.sections.game.prompts.PromptTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * OutputPromptAdapter
 */
public class OutputPromptAdapter extends BaseAdapter {

    private static final int PROMPT_TYPE_COUNT = 4;
    private static final int AUDIO_PROMPT_LEFT = 0;
    private static final int AUDIO_PROMPT_RIGHT = 1;
    private static final int TEXT_PROMPT_LEFT = 2;
    private static final int TEXT_PROMPT_RIGHT = 3;

    private LayoutInflater mInflater;
    private List<Prompt> mPrompts;

    private OutputPromptsView mOutputPromptsView;

    private Context mCtx;

    private ArrayList<String> animatedPrompts;

    public OutputPromptAdapter(Context ctx, OutputPromptsView outputPromptsView, List<Prompt> prompts) {
        super();

        mInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPrompts = prompts;
        mOutputPromptsView = outputPromptsView;

        mCtx = ctx;
        animatedPrompts = new ArrayList<>();

        AudioOutputPromptController.init(ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Prompt prompt = mPrompts.get(position);
        OutputPromptViewHolder viewHolder;

        int rowType = getItemViewType(position);

        /**
         * If the row is null, inflate the view
         */
        if (row == null) {
            if(rowType == AUDIO_PROMPT_LEFT) {
                row = mInflater.inflate(R.layout.layout_prompt_audio_output_left, parent, false);
            }
            else if(rowType == AUDIO_PROMPT_RIGHT) {
                row = mInflater.inflate(R.layout.layout_prompt_audio_output_right, parent, false);
            }
            else if(rowType == TEXT_PROMPT_LEFT) {
                row = mInflater.inflate(R.layout.layout_prompt_text_output_left, parent, false);
            }
            else if(rowType == TEXT_PROMPT_RIGHT) {
                row = mInflater.inflate(R.layout.layout_prompt_text_output_right, parent, false);
            }
        }

        /**
         * Get the view holder for the current row
         */
        if (row.getTag() == null) {
            viewHolder = OutputPromptViewHolderFactory.getPromptViewHolder(mOutputPromptsView, prompt.getType(), row);
            row.setTag(viewHolder);
        } else {
            viewHolder = (OutputPromptViewHolder) row.getTag();
        }

        /**
         * Init the view holder with the corresponding data
         */
        viewHolder.bind(prompt.getProperties(), position, row);

        if(!animatedPrompts.contains(prompt.getId())) {
            animatedPrompts.add(prompt.getId());
            Animation animation = AnimationUtils.loadAnimation(mCtx, R.anim.slide_right_in);
            row.startAnimation(animation);
        }
        return row;
    }

    @Override
    public int getCount() {
        return mPrompts.size();
    }

    @Override
    public Object getItem(int position) {
        return mPrompts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        Prompt prompt = mPrompts.get(position);
        if(prompt.getType().equals(PromptTypes.AUDIO) &&
                prompt.getProperties().getPersonalMessage() != null &&
                prompt.getProperties().getPersonalMessage().equals("1")){
            return AUDIO_PROMPT_RIGHT;
        }
        else if(prompt.getType().equals(PromptTypes.AUDIO)) {
            return AUDIO_PROMPT_LEFT;
        }
        else if(prompt.getType().equals(PromptTypes.TEXT) &&
                prompt.getProperties().getPersonalMessage() != null &&
                prompt.getProperties().getPersonalMessage().equals("1")) {
            return TEXT_PROMPT_RIGHT;
        }
        return TEXT_PROMPT_LEFT;
    }

    @Override
    public int getViewTypeCount() {
        return PROMPT_TYPE_COUNT;
    }

}

