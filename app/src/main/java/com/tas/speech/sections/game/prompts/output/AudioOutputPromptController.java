package com.tas.speech.sections.game.prompts.output;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.SparseArray;

import com.tas.speech.sections.game.prompts.output.states.AudioPromptState;

/**
 * AudioOutputPromptController stores an array of audio playback states; a state for each audio
 * output prompt element
 *
 * This class also stores the single MediaPlayer instance used in the app
 */
public class AudioOutputPromptController {

    private static final String TAG = "AudioOutputPromptCtrl";

    public enum AudioPromptMode {
        IDLE, PLAYING, PAUSED, COMPLETED
    }

    //the current focused audio output prompt (its index)
    private static int activePrompt;

    private static SparseArray<AudioPromptState> audioPromptsStates;

    private static MediaPlayer mediaPlayer;
    public static AudioManager audioManager;

    public static void init(Context ctx) {
        audioPromptsStates = new SparseArray<>();
        activePrompt = -1;

        mediaPlayer = new MediaPlayer();
        audioManager = (AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE);
    }

    public static AudioPromptState getAudioPromptState(int key) {
        AudioPromptState state = audioPromptsStates.get(key);

        if(state == null) {
            state = new AudioPromptState();

            if(key != -1) {
                audioPromptsStates.put(key, state);
            }
        }

        return state;
    }

    public static void setAudioPromptMode(int k, AudioPromptMode mode) {
        audioPromptsStates.get(k).setMode(mode);
    }

    public static void setAudioPromptProgress(int k, long progress) {
        audioPromptsStates.get(k).setProgress(progress);
    }

    public static void setAudioPromptDuration(int k, long duration) {
        audioPromptsStates.get(k).setDuration(duration);
    }

    public static int getActivePrompt() {
        return activePrompt;
    }

    public static void setActiveAudioPrompt(int prompt) {
        activePrompt = prompt;
    }

    public static MediaPlayer getMediaPlayer() {
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }

    public static void releaseMediaPlayer() {
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public static boolean allTheAudiosPlayed() {
        for(int i = 0; i < audioPromptsStates.size(); ++i) {
            Log.d(TAG, "State for " + audioPromptsStates.keyAt(i) + " -> " + audioPromptsStates.get(audioPromptsStates.keyAt(i)).getMode().toString());
            if(audioPromptsStates.get(audioPromptsStates.keyAt(i)).getMode() == AudioPromptMode.IDLE) {
                return false;
            }
        }
        return true;
    }
}
