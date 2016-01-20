package com.tas.speech.sections.game.prompts.output;

import android.view.View;

import com.tas.speech.sections.game.models.PromptProperties;

/**
 * Created by drago_000 on 02/07/2015.
 */
public interface OutputPromptViewHolder {

    void bind(PromptProperties promptProperties, int promptPosition, View v);
}
