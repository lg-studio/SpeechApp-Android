package com.tas.speech.sections.game;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.tas.speech.Const;
import com.tas.speech.R;
import com.tas.speech.client.ApiTypes;
import com.tas.speech.SpeechApp;
import com.tas.speech.sections.game.client.GameApi;
import com.tas.speech.sections.game.models.Prompt;
import com.tas.speech.sections.game.models.GameStructureResponse;
import com.tas.speech.sections.game.models.TaskInput;
import com.tas.speech.sections.game.models.TaskInputAnswer;
import com.tas.speech.sections.game.models.TaskInputRating;
import com.tas.speech.sections.game.models.TaskInputRatingMetric;
import com.tas.speech.sections.game.models.TaskInputResponse;
import com.tas.speech.sections.game.prompts.PromptTypes;
import com.tas.speech.sections.game.prompts.output.AudioOutputPromptController;

import java.io.File;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class Game implements GameEventListener {

    private static final String TAG = "Game";

    private Context mCtx;

    private InputPromptsView mInputPromptsView;
    private OutputPromptsView mOutputPromptsView;

    // the game configuration array (contains both inputs and outputs)
    private ArrayList<Prompt> configuration;
    private ArrayList<Prompt> currentOutputs;

    // the next step from the configuration array
    private int nextStepIndex;

    private String mGameId, mTaskId;

    private Callback<GameStructureResponse> nodeStructureCallback, taskStructureCallback;

    private static String ratedUser;

    private Handler handler;
    private Runnable displayItemTask;
    private static int i;

    private boolean gameFinished;

    private static int customPromptSeq;

    // Task info
    // All the ratings offered during a task
    public static ArrayList<TaskInputRating> ratings;
    // All the answers offered during a task
    public static ArrayList<TaskInputAnswer> answers;

    public Game(Context ctx, String gameId, InputPromptsView inputPromptsView, final OutputPromptsView outputPromptsView) {
        ratings = new ArrayList<>();
        answers = new ArrayList<>();

        nextStepIndex = 0;
        customPromptSeq = 0;

        mCtx = ctx;
        mGameId = gameId;
        gameFinished = false;

        handler = new Handler();

        displayItemTask = new Runnable() {
            @Override
            public void run() {
                if (configuration.size() > i && configuration.get(i).getPromptCategory() == Prompt.Category.OUTPUT) {
                    currentOutputs.add(configuration.get(i));
                    mOutputPromptsView.refreshAdapter();
                    i++;
                    outputPromptsView.scrollChatToBottom();
                    handler.postDelayed(this, Const.PROMPT_DELAY);
                }
                else {
                    nextStepIndex = i;
                    continueCurrentGame();
                }
            }
        };

        configuration = new ArrayList<>();

        mInputPromptsView = inputPromptsView;
        mOutputPromptsView = outputPromptsView;

        // Init the list view adapter with an empty array
        currentOutputs = new ArrayList<>();
        mOutputPromptsView.initAdapter(currentOutputs);

        taskStructureCallback = new Callback<GameStructureResponse>() {
            @Override
            public void success(GameStructureResponse gameStructureResponse, retrofit.client.Response response2) {
                ((GameActivity)mCtx).hideProgress();
                mTaskId = gameStructureResponse.getData().getTaskId();

                configuration.addAll(gameStructureResponse.getData().getItems());
                continueCurrentGame();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };

        nodeStructureCallback = new Callback<GameStructureResponse>() {
            @Override
            public void success(GameStructureResponse gameStructureResponse, retrofit.client.Response response2) {
                ((GameActivity)mCtx).hideProgress();
                configuration.remove(nextStepIndex);
                configuration.addAll(nextStepIndex, gameStructureResponse.getData().getItems());
                if(!gameStructureResponse.getData().getItems().isEmpty()) {
                    ratedUser = gameStructureResponse.getData().getItems().get(0).getProperties().getName();
                }
                continueCurrentGame();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };

        ((GameActivity)mCtx).showProgress();
        ((GameApi) SpeechApp.getApiOfType(ApiTypes.GAME_API)).taskStructure(mGameId, taskStructureCallback);
    }

    public void continueCurrentGame() {
        // output -> continue
        if(!gameFinished && nextStepIndex == configuration.size() - 1) {
            // Add the submit prompt if the game is finished
            Prompt submitPrompt = new Prompt();
            submitPrompt.setId(Game.getNextCustomPromptSeq());
            submitPrompt.setType(PromptTypes.SUBMIT);
            configuration.add(submitPrompt);
            gameFinished = true;
            nextStepIndex++;
            continueCurrentGame();
        }
        for(i = nextStepIndex; i < configuration.size(); ++i)
            if (configuration.get(i).getPromptCategory() == Prompt.Category.OUTPUT) {
                handler.postDelayed(displayItemTask, Const.PROMPT_DELAY);
                break;
            }
            // input -> break and wait for input
            else if (configuration.get(i).getPromptCategory() == Prompt.Category.INPUT) {
                mInputPromptsView.displayInputPrompt(mCtx, configuration.get(i));
                mOutputPromptsView.refreshAdapter();

                nextStepIndex = i + 1;
                break;
            }
            // feedback required -> request feedback json
            else {
                nextStepIndex = i;
                requestFeedback(Integer.parseInt(configuration.get(i).getId()));
                break;
            }
    }

    public void addPromptToCurrentConfiguration(Prompt prompt) {
        configuration.add(nextStepIndex, prompt);
        continueCurrentGame();
    }

    private void requestFeedback(int id) {
        ((GameActivity)mCtx).showProgress();
        ((GameApi) SpeechApp.getApiOfType(ApiTypes.GAME_API)).nodeStructure(mTaskId,
                String.valueOf(id), nodeStructureCallback);
    }

    public static String getRatedUser() {
        return ratedUser;
    }

    public static String getNextCustomPromptSeq() {
        return "c" + customPromptSeq++;
    }

    @Override
    public void onTaskSubmit() { // Method called when the user submits a task

        // A POST request with multipart/form-data content will be sent to the server:
        //              - a text/plain json formatted string containing a list of the recorded files
        //                  and the ratings
        //              - a list of multipart/form-data containing each recorded file

        // TaksInput - POJO class that will be converted into the forementioned json
        TaskInput taskInput = new TaskInput();
        taskInput.setTaskId(mTaskId);
        taskInput.setRatings(ratings);

        Callback<TaskInputResponse> taskInputCallback = new Callback<TaskInputResponse>() {
            @Override
            public void success(TaskInputResponse taskInputResponse, Response response) {
                ((GameActivity)mCtx).hideProgress();
                ((GameActivity) mCtx).finish();
            }

            @Override
            public void failure(RetrofitError error) {
                ((GameActivity)mCtx).hideProgress();
                ((GameActivity) mCtx).finish();
            }
        };

        // The multipart content that will be sent to server when the task is submitted
        MultipartTypedOutput attachments = new MultipartTypedOutput();

        // Populate a TypedFile list with all the recordings made during the current task
        ArrayList<TypedFile> typedFiles = new ArrayList<>();
        for(TaskInputAnswer answer : answers) {
            ArrayList<String> fileNames = answer.getFileNames();
            for(String fileName : fileNames) {
                typedFiles.add(new TypedFile("multipart/form-data", new File(fileName)));
            }
        }

        // Get rid of the absolute path and keep only the file name for the recorded files list that
        //will be stored in the json
        for(int i = 0; i < answers.size(); ++i) {
            ArrayList<String> newFileNames = new ArrayList<>();
            for(int j = 0; j < answers.get(i).getFileNames().size(); ++j) {
                File file = new File(answers.get(i).getFileNames().get(j));
                newFileNames.add(file.getName());
            }
            answers.get(i).setFileNames(newFileNames);
        }
        taskInput.setAnswers(answers);

        // Convert TaskInput into JSON using Gson library
        Gson gson = new Gson();
        String taskInputJson = gson.toJson(taskInput);
        TypedString inputs = new TypedString(taskInputJson);

        // Add the json to the multipart objects as text/plain using the key "Inputs"
        attachments.addPart("Inputs", inputs);

        // Add each file to the multipart object using the key "file"
        for(TypedFile typedFile : typedFiles) {
            attachments.addPart("file", typedFile);
        }

        ((GameActivity)mCtx).showProgress();
        // Execute the POST request
        ((GameApi) SpeechApp.getApiOfType(ApiTypes.GAME_API)).taskInput(attachments, taskInputCallback);
    }

    @Override
    public boolean onRatingSubmit(String nodeId, SparseArray<TaskInputRatingMetric> ratedMetrics) { // add every submitted rating to the ratings list
        TaskInputRating rating = new TaskInputRating();

        if(nodeId != null) {
            if(!submitPossbile(nodeId, PromptTypes.RATING)) {
                return false;
            }
            rating.setNodeId(Integer.parseInt(nodeId));
        }

        ArrayList<TaskInputRatingMetric> ratedMetricsList = new ArrayList<>();
        for(int i = 0; i < ratedMetrics.size(); ++i) {
            ratedMetricsList.add(ratedMetrics.get(ratedMetrics.keyAt(i)));
        }
        rating.setMetrics(ratedMetricsList);

        ratings.add(rating);

        return true;
    }

    @Override
    public boolean onRecordingSubmit(String nodeId, ArrayList<String> fileNames) { // on every submitted answer save all the recorded files' absolute paths
        if(submitPossbile(nodeId, PromptTypes.RECORDING)) {
            TaskInputAnswer answer = new TaskInputAnswer();
            answer.setNodeId(Integer.parseInt(nodeId));
            answer.setFileNames(fileNames);
            answers.add(answer);
            return true;
        }

        return false;
    }

    private boolean submitPossbile(String nodeId, String promptType) {
        //Determine the rated/answered prompt type
        Prompt ratedPrompt = null;
        for (int i = 0; i < currentOutputs.size(); ++i) {
            if (currentOutputs.get(i).getNodeId() != null && currentOutputs.get(i).getNodeId().equals(nodeId)) {
                ratedPrompt = currentOutputs.get(i);
                break;
            }
        }

        // If rated/answered prompt is of type Audio check before submitting rating if all the audios
        // have been played
        if (ratedPrompt != null && ratedPrompt.getType().equals(PromptTypes.AUDIO)) {
            if (!AudioOutputPromptController.allTheAudiosPlayed()) {
                displaySubmitAlertDialog(promptType);
                return false;
            }
        }
        return true;
    }

    private void displaySubmitAlertDialog(String promptType) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mCtx);
        // set title
        alertDialogBuilder.setTitle(mCtx.getString(R.string.title_listen_all_audios));

        String message = "";
        if(promptType.equals(PromptTypes.RATING)) {
            message = mCtx.getString(R.string.rating_listen_all_audios);
        }
        else if(promptType.equals(PromptTypes.RECORDING)) {
            //You must listen too all the audios to submit your answer
            message = mCtx.getString(R.string.answer_listen_all_audios);
        }

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(mCtx.getString(R.string.positive_listen_all_audios),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
