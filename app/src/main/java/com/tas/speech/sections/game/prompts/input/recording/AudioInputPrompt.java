package com.tas.speech.sections.game.prompts.input.recording;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.tas.speech.R;
import com.tas.speech.sections.game.GameEventListener;
import com.tas.speech.sections.game.prompts.input.InputPrompt;
import com.tas.speech.sections.game.prompts.input.recording.viewholders.AudioInputViewHolder;
import com.tas.speech.sections.game.models.Prompt;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * AudioInputPrompt
 */
public class AudioInputPrompt implements InputPrompt {
    private static final String TAG = "AudioInputPrompt";

    public static String mFileName = null;
    private static MediaRecorder mRecorder = null;

    public static LinearLayout mLlFooter;

    private Context mCtx;
    private Prompt mRecorderPrompt;

    private GameEventListener mGameEventListener;

    public AudioInputPrompt(Context ctx, LinearLayout llFooter, Prompt recorderPrompt,
                            GameEventListener gameEventListener) {
        mCtx = ctx;
        mRecorderPrompt = recorderPrompt;

        mLlFooter = llFooter;

        mGameEventListener = gameEventListener;
    }

    public void render() {
        LayoutInflater layoutInflater = (LayoutInflater)
                mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate the audio input prompt
        View audioRecorderView = layoutInflater.inflate(R.layout.layout_audio_recorder, mLlFooter, false);

        // init the view holder
        AudioInputViewHolder audioRecorderViewHolder = new AudioInputViewHolder(mCtx, audioRecorderView, mRecorderPrompt.getNodeId(), mGameEventListener);
        audioRecorderViewHolder.bind(mRecorderPrompt);

        // add the previously inflated view to the footer and display the footer
        mLlFooter.addView(audioRecorderView);
        Animation animation = AnimationUtils.loadAnimation(mCtx, R.anim.slide_up_in);
        mLlFooter.startAnimation(animation);
        mLlFooter.setVisibility(View.VISIBLE);
    }

    public static void startRecording() {
        mFileName = generateFileName();

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioSamplingRate(8000);
        mRecorder.setAudioEncodingBitRate(16000);
        mRecorder.setAudioChannels(1);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    public static void stopRecording() {
        if(mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    private static String generateFileName() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
        String timestampStr = sdf.format(date);

        String fileName;
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/audiorecord" + timestampStr + ".m4a";

        return fileName;
    }
}
