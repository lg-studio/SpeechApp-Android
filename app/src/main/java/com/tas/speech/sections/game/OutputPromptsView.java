package com.tas.speech.sections.game;

import com.tas.speech.sections.game.models.Prompt;

import java.util.ArrayList;

/**
 * Created by drago_000 on 15/07/2015.
 */
public interface OutputPromptsView {

    void redrawPrompt(int promptIndex);

    boolean promptIsVisible(int promptIndex);

    void addOutputPrompt(Prompt prompt);

    void initAdapter(ArrayList<Prompt> outputPrompts);

    void refreshAdapter();

    void addToAdapter(Prompt prompt);

    void scrollChatToBottom();
}
