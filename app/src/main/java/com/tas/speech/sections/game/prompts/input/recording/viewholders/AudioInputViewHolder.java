package com.tas.speech.sections.game.prompts.input.recording.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tas.speech.sections.game.GameActivity;
import com.tas.speech.R;
import com.tas.speech.sections.game.GameEventListener;
import com.tas.speech.sections.game.prompts.input.recording.AudioInputPrompt;
import com.tas.speech.sections.game.models.Prompt;
import com.tas.speech.utils.DateTimeFormatters;

import java.util.ArrayList;

/**
 * AudioInputViewHolder
 */
public class AudioInputViewHolder {
    private static final String TAG = "AudioInputViewHolder";

    ArrayList<String> recordedFiles;

    private ImageView ivRecord, ivRecordCheck, ivStop, ivStopCheck;
    private SeekBar sbRecordingProgress;
    private TextView tvRecordingProgress, tvMinLength, tvMaxLength;

    private View.OnClickListener ivRecordOnClickListener, ivRecordCheckOnClickListener, ivStopOnClickListener,
            ivStopCheckOnClickListener;

    private Prompt mRecodingPrompt;

    private int recordingProgress;
    private int minRecordingLength, maxRecordingLength, maxRecordings, maxRetriesPerRecording,
            retriesMade, recordingsMade;

    private boolean isRecording;

    private Runnable recordingProgressTask;

    private Context mCtx;
    private Animation fadeOutanimation;

    private GameEventListener mGameEventListener;
    private String mNodeId;

    public AudioInputViewHolder(Context ctx, View v, String nodeId, GameEventListener gameEventListener) {
        tvRecordingProgress = (TextView)v.findViewById(R.id.tv_recording_progress);
        tvMinLength = (TextView)v.findViewById(R.id.tv_min_length);
        tvMaxLength = (TextView)v.findViewById(R.id.tv_max_length);

        ivRecord = (ImageView)v.findViewById(R.id.iv_record);
        ivRecordCheck = (ImageView)v.findViewById(R.id.iv_record_check);
        ivStop = (ImageView)v.findViewById(R.id.iv_stop);
        ivStopCheck = (ImageView)v.findViewById(R.id.iv_stop_check);

        sbRecordingProgress = (SeekBar)v.findViewById(R.id.sb_recording_progress);

        // disable the recording progress seekbar
        sbRecordingProgress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        mCtx = ctx;

        mGameEventListener = gameEventListener;
        mNodeId = nodeId;
        recordedFiles = new ArrayList<>();

        fadeOutanimation = AnimationUtils.loadAnimation(mCtx, R.anim.slide_down_out);
        fadeOutanimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                AudioInputPrompt.mLlFooter.setVisibility(View.GONE);
                AudioInputPrompt.mLlFooter.removeAllViews();
            }
        });
    }

    public void bind(Prompt recordingPrompt) {
        isRecording = false;

        mRecodingPrompt = recordingPrompt;

        recordingProgress = 0;
        recordingsMade = 0;
        retriesMade = 0;
        // Read the recording prompt properties
        maxRecordings = Integer.parseInt(mRecodingPrompt.getProperties().getMaxRecordings());
        maxRetriesPerRecording = Integer.parseInt(mRecodingPrompt.getProperties().getMaxRetriesPerRecording());
        minRecordingLength = Integer.parseInt(mRecodingPrompt.getProperties().getMinLength()) * 1000;
        maxRecordingLength = Integer.parseInt(mRecodingPrompt.getProperties().getMaxLength()) * 1000;

        // Init the progress view elements
        sbRecordingProgress.setMax(maxRecordingLength);
        sbRecordingProgress.setProgress(0);
        tvRecordingProgress.setText("00:00");
        tvMaxLength.setText(DateTimeFormatters.convertMillisecondsToMinAndSec(maxRecordingLength));
        tvMinLength.setText(DateTimeFormatters.convertMillisecondsToMinAndSec(minRecordingLength));

        // Init the listeners for all the foru action buttons: record, confirm recording, retry and
        // confirm recordings
        initBtnListeners();
        ivRecord.setOnClickListener(ivRecordOnClickListener);
        ivRecordCheck.setOnClickListener(ivRecordCheckOnClickListener);
        ivStop.setOnClickListener(ivStopOnClickListener);
        ivStopCheck.setOnClickListener(ivStopCheckOnClickListener);

        // init the job that will update the recording progress
        recordingProgressTask = new Runnable() {
            @Override
            public void run() {

                updateRecordingProgressUI();

                recordingProgress += 500;

                if(recordingProgress < maxRecordingLength) {
                    sbRecordingProgress.getHandler().postDelayed(this, 500);
                }
                else {
                    recordingProgress = maxRecordingLength;
                    tvRecordingProgress.setText(DateTimeFormatters.convertMillisecondsToMinAndSec(recordingProgress));
                    sbRecordingProgress.setProgress(recordingProgress);
                    AudioInputPrompt.stopRecording();
                    isRecording = false;
                    recordingProgress = 0;
                }
            }
        };
    }

    private void startUIRecordingProgressTask() {
        sbRecordingProgress.getHandler().post(recordingProgressTask);
    }

    private void stopUIRecordingProgressTask() {
        sbRecordingProgress.getHandler().removeCallbacks(recordingProgressTask);
    }

    private void disableButton(ImageView button) {
        button.setAlpha(90);
        button.setOnClickListener(null);
    }

    private void enableButton(ImageView button, View.OnClickListener listener) {
        button.setAlpha(250);
        button.setOnClickListener(listener);
    }

    private void submitRecording() {
        AudioInputPrompt.stopRecording();

        //Add the recorded audio to the array
        recordedFiles.add(AudioInputPrompt.mFileName);

        Log.d(TAG, "Recorded Files: " +recordedFiles);

        isRecording = false;
        stopUIRecordingProgressTask();

        recordingsMade++;
        retriesMade = 0;

        if(recordingsMade == maxRecordings) {

            AudioInputPrompt.mLlFooter.startAnimation(fadeOutanimation);
            ((GameActivity)mCtx).continueCurrentGame();
        }
        else {
            recordingProgress = 0;

            ivRecordCheck.setVisibility(View.INVISIBLE);
            ivRecord.setVisibility(View.VISIBLE);

            updateRecordingProgressUI();
        }
    }

    private void updateRecordingProgressUI() {
        tvRecordingProgress.setText(DateTimeFormatters.convertMillisecondsToMinAndSec(recordingProgress));
        sbRecordingProgress.setProgress(recordingProgress);

        if(recordingProgress < minRecordingLength) {
            disableButton(ivRecordCheck);
        }

        if(recordingProgress < minRecordingLength || recordingProgress >= maxRecordingLength - 10000) {
            sbRecordingProgress.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }
        else {
            sbRecordingProgress.getProgressDrawable().setColorFilter(null);
            enableButton(ivRecordCheck, ivRecordCheckOnClickListener);
        }

        if(!isRecording && recordingsMade != 0) {
            ivStop.setVisibility(View.INVISIBLE);
            ivStopCheck.setVisibility(View.VISIBLE);
        }
        else if(isRecording) {
            ivStopCheck.setVisibility(View.INVISIBLE);
            if(retriesMade < maxRetriesPerRecording) {
                ivStop.setVisibility(View.VISIBLE);
            }
            else {
                ivStop.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initBtnListeners() {
        ivRecordOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ivRecord.setVisibility(View.INVISIBLE);

                disableButton(ivRecordCheck);
                ivRecordCheck.setVisibility(View.VISIBLE);

                if(retriesMade < maxRetriesPerRecording) {
                    ivStop.setVisibility(View.VISIBLE);
                }

                AudioInputPrompt.startRecording();
                isRecording = true;
                startUIRecordingProgressTask();
            }
        };

        ivRecordCheckOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "ivRecordCheck onClick");
                submitRecording();
            }
        };

        ivStopOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioInputPrompt.stopRecording();



                isRecording = false;
                stopUIRecordingProgressTask();

                retriesMade++;

                ivStop.setVisibility(View.GONE);

                ivRecordCheck.setVisibility(View.GONE);

                enableButton(ivRecord, ivRecordOnClickListener);
                ivRecord.setVisibility(View.VISIBLE);

                recordingProgress = 0;

                updateRecordingProgressUI();
            }
        };

        ivStopCheckOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If submit action has benn completed successfully
                if(mGameEventListener.onRecordingSubmit(mNodeId, recordedFiles)) {

                    AudioInputPrompt.stopRecording();
                    isRecording = false;

                    stopUIRecordingProgressTask();

                    sbRecordingProgress.getProgressDrawable().setColorFilter(null);

                    AudioInputPrompt.mLlFooter.startAnimation(fadeOutanimation);

                    ((GameActivity) mCtx).continueCurrentGame();
                }
            }
        };
    }
}
