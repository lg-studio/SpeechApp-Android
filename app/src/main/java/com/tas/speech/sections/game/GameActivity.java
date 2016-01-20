package com.tas.speech.sections.game;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.tas.speech.R;
import com.tas.speech.sections.game.prompts.input.InputPrompt;
import com.tas.speech.sections.game.prompts.input.InputPromptFactory;
import com.tas.speech.sections.game.prompts.input.recording.AudioInputPrompt;
import com.tas.speech.sections.game.prompts.output.AudioOutputPromptController;
import com.tas.speech.sections.game.models.Prompt;
import com.tas.speech.sections.game.prompts.output.OutputPromptAdapter;

import java.util.ArrayList;

/**
 * This activity controls the way input and output elements are displayed by implementing two
 * interfaces:
 *            - InputPromptsView
 *            - OutputPromptsView
 *
 * An instance of the Game class is stored by this activity; in order to trigger the game's next state
 * all the other components will have to call the activity's continueCurrentGame method.
 */
public class GameActivity extends AppCompatActivity implements InputPromptsView, OutputPromptsView {

    private static final String TAG = "GameActivity";

    public static final String ARG_TASK_ID = "TASK_ID";

    private ListView lvOutputPrompt;
    private ProgressBar pbLoading;
    private OutputPromptAdapter outputPromptAdapter;
    private LinearLayout llInputFooter;

    public Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String taskId = getIntent().getStringExtra(ARG_TASK_ID);
        Log.d(TAG, "taskId: " + taskId);

        pbLoading = (ProgressBar)findViewById(R.id.pb_loading);

        // Get the list view which will display all the output prompts
        lvOutputPrompt = (ListView)findViewById(R.id.lv_output_prompt);

        // Hide the input footer
        llInputFooter = (LinearLayout)findViewById(R.id.ll_input_footer);
        llInputFooter.setVisibility(View.INVISIBLE);

        game = new Game(this, taskId, this, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        AudioOutputPromptController.releaseMediaPlayer();
        AudioInputPrompt.stopRecording();
    }

    // Method which redraws the output prompt with the index promptIndex in the list view
    @Override
    public void redrawPrompt(int promptIndex) {
        int start = lvOutputPrompt.getFirstVisiblePosition();
        for(int i=start, j=lvOutputPrompt.getLastVisiblePosition();i<=j;i++) {
            if(promptIndex == i){
                View view = lvOutputPrompt.getChildAt(i-start);
                lvOutputPrompt.getAdapter().getView(i, view, lvOutputPrompt);
                break;
            }
        }
    }

    // Method which checks if the output prompt with the index promptIndex is displayed in the list
    // view
    @Override
    public boolean promptIsVisible(int promptIndex) {
        int start = lvOutputPrompt.getFirstVisiblePosition();
        for(int i=start, j=lvOutputPrompt.getLastVisiblePosition();i<=j;i++) {
            if(promptIndex == i){
                return true;
            }
        }
        return false;
    }

    // Method which adds an output prompt to the current game configuration
    @Override
    public void addOutputPrompt(Prompt prompt) {
        game.addPromptToCurrentConfiguration(prompt);
    }

    // Method which initializes the adapter and associates it with the output prompt list view
    @Override
    public void initAdapter(ArrayList<Prompt> outputPrompts) {
        outputPromptAdapter = new OutputPromptAdapter(this, this, outputPrompts);
        lvOutputPrompt.setAdapter(outputPromptAdapter);
    }

    // Method which refreshes displayed data in the output prompt list view
    @Override
    public void refreshAdapter() {
        outputPromptAdapter.notifyDataSetChanged();
//        lvOutputPrompt.startLayoutAnimation();
    }

    @Override
    public void addToAdapter(Prompt prompt) {

    }

    // Method which adds an input prompt to the footer and displays it; the method also scrolls the
    // output prompt list view to the last element
    @Override
    public void displayInputPrompt(Context ctx, Prompt inputPromptModel) {
        InputPrompt inputPrompt = InputPromptFactory.getPrompt(ctx, llInputFooter, this, inputPromptModel, game);
        inputPrompt.render();

        scrollChatToBottom();
    }

    @Override
    public void scrollChatToBottom() {
        lvOutputPrompt.post(new Runnable() {
            @Override
            public void run() {
                lvOutputPrompt.setSelection(outputPromptAdapter.getCount());
            }
        });
    }

    // Method that continues the current game to the next step
    public void continueCurrentGame() {
        game.continueCurrentGame();
    }

    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }
}
