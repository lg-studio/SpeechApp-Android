package com.tas.speech.sections.game.prompts.output.states;

import com.tas.speech.sections.game.prompts.output.AudioOutputPromptController;

/**
 * AudioPromptState class will store the audio playback state for an audio output prompt
 */
public class AudioPromptState {
    // mMode > IDLE, PLAYING, PAUSED, COMPLETED
    private AudioOutputPromptController.AudioPromptMode mMode;
    private long mProgress;
    private long mDuration;

    public AudioPromptState() {
        mProgress = 0;
        mDuration = 0;

        mMode = AudioOutputPromptController.AudioPromptMode.IDLE;
    }

    public long getDuration() {
        return mDuration;
    }

    public long getProgress() {
        return mProgress;
    }

    public AudioOutputPromptController.AudioPromptMode getMode() {
        return mMode;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public void setProgress(long progress) {
        mProgress = progress;
    }

    public void setMode(AudioOutputPromptController.AudioPromptMode mode) {
        mMode = mode;
    }

    public String toString() {
        return "[" + "progress: " + mProgress + ", duration: " + mDuration + ", mode: " + mMode + "]";
    }
}