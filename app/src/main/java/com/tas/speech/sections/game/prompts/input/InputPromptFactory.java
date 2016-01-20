package com.tas.speech.sections.game.prompts.input;

import android.content.Context;
import android.widget.LinearLayout;

import com.tas.speech.sections.game.GameEventListener;
import com.tas.speech.sections.game.OutputPromptsView;
import com.tas.speech.sections.game.models.Prompt;
import com.tas.speech.sections.game.prompts.PromptTypes;
import com.tas.speech.sections.game.prompts.input.multiplechoice.MultipleChoicePictureInputPrompt;
import com.tas.speech.sections.game.prompts.input.multiplechoice.MultipleChoiceTextInputPrompt;
import com.tas.speech.sections.game.prompts.input.rating.RatingInputPrompt;
import com.tas.speech.sections.game.prompts.input.recording.AudioInputPrompt;
import com.tas.speech.sections.game.prompts.input.submit.SubmitInputPrompt;

/**
 * Created by drago_000 on 15/07/2015.
 */
public class InputPromptFactory {
    public static InputPrompt getPrompt(Context ctx, LinearLayout llInputFooter, OutputPromptsView outputPromptsView,
                                        Prompt prompt, GameEventListener gameEventListener) {

        if(prompt.getType().equals(PromptTypes.RATING)) {
            return new RatingInputPrompt(ctx, llInputFooter, outputPromptsView, prompt, gameEventListener);
        }
        else if(prompt.getType().equals(PromptTypes.RECORDING)) {
            return new AudioInputPrompt(ctx, llInputFooter, prompt, gameEventListener);
        }
        else if(prompt.getType().equals(PromptTypes.SUBMIT)) {
            return new SubmitInputPrompt(ctx, llInputFooter, gameEventListener);
        }
        else if(prompt.getType().equals(PromptTypes.MULTIPLE_CHOICE_TEXT)) {
            return new MultipleChoiceTextInputPrompt(ctx, llInputFooter, prompt.getProperties().getOptions());
        }
        else if(prompt.getType().equals(PromptTypes.MULTIPLE_CHOICE_PICTURE)) {
            return new MultipleChoicePictureInputPrompt(ctx, llInputFooter, prompt.getProperties().getOptions());
        }
        return null;
    }
}
