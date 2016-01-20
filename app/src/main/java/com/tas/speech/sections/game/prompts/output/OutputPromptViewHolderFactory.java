package com.tas.speech.sections.game.prompts.output;

import android.view.View;

import com.tas.speech.sections.game.OutputPromptsView;
import com.tas.speech.sections.game.prompts.PromptTypes;
import com.tas.speech.sections.game.prompts.output.viewholders.AudioOutputPromptViewHolder;
import com.tas.speech.sections.game.prompts.output.viewholders.TextOutputPromptViewHolder;

/**
 * Factory class which depending on the prompt type will produce the associated view holder
 */
public class OutputPromptViewHolderFactory {

    public static OutputPromptViewHolder getPromptViewHolder(OutputPromptsView outputPromptsView,
                                                             String promptType, View promptView) {

        if(promptType.equals(PromptTypes.TEXT)) {
            return new TextOutputPromptViewHolder(promptView);
        }
        else if(promptType.equals(PromptTypes.AUDIO)) {
            return new AudioOutputPromptViewHolder(promptView, outputPromptsView);
        }
        return null;
    }
}