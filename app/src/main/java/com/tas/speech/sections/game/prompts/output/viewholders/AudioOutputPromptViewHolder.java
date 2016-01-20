package com.tas.speech.sections.game.prompts.output.viewholders;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tas.speech.R;
import com.tas.speech.sections.game.OutputPromptsView;
import com.tas.speech.sections.game.prompts.output.AudioOutputPromptController;
import com.tas.speech.sections.game.prompts.output.OutputPromptViewHolder;
import com.tas.speech.sections.game.prompts.output.states.AudioPromptState;
import com.tas.speech.sections.game.models.PromptProperties;
import com.tas.speech.utils.DateTimeFormatters;

import java.io.IOException;

/**
 * AudioOutputPromptViewHolder
 */
public class AudioOutputPromptViewHolder implements OutputPromptViewHolder,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnPreparedListener, AudioManager.OnAudioFocusChangeListener,
        SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "AudioOutputPrompt";

    private int mPromptPosition;
    private String audioUrl;
    private View mPromptView;

    private TextView tvMessage, tvUsername, tvAudioProgress;
    private SeekBar sbAudioProgress;
    private ImageView ivPlay, ivAvatar;

    private Runnable audioProgressTask;

    private OutputPromptsView mOutputPromptsView;

    public AudioOutputPromptViewHolder(View promptView, OutputPromptsView outputPromptsView) {
        tvMessage = (TextView)promptView.findViewById(R.id.tv_message);
        tvUsername = (TextView)promptView.findViewById(R.id.tv_username);
        tvAudioProgress = (TextView)promptView.findViewById(R.id.tv_audio_progress);
        sbAudioProgress = (SeekBar)promptView.findViewById(R.id.sb_audio_progress);
        ivPlay = (ImageView)promptView.findViewById(R.id.iv_play);
        ivAvatar = (ImageView)promptView.findViewById(R.id.iv_avatar);

        mOutputPromptsView = outputPromptsView;
    }

    public void bind(PromptProperties promptProperties, int promptPosition, View promptView) {
        /**
         * Initializing view
         */

        Picasso.with(promptView.getContext()).load(promptProperties.getPictureUrl()).into(ivAvatar);

        sbAudioProgress.setOnSeekBarChangeListener(this);

        if(promptProperties.getHeaderText() == null || promptProperties.getHeaderText().equals("")) {
            tvMessage.setVisibility(View.GONE);
        }
        else {
            tvMessage.setText(promptProperties.getHeaderText());
        }
        tvUsername.setText(promptProperties.getName());

        mPromptPosition = promptPosition;
        mPromptView = promptView;

        audioUrl = promptProperties.getAudioUrl();

        AudioPromptState promptState = AudioOutputPromptController.getAudioPromptState(mPromptPosition);
        updateAudioProgressView((int)promptState.getProgress(), (int)promptState.getDuration());

        if(promptState.getMode() == AudioOutputPromptController.AudioPromptMode.PLAYING) {
            ivPlay.setSelected(true);
            ivPlay.setImageResource(R.drawable.icon_pause);
        }
        else {
            ivPlay.setSelected(false);
            ivPlay.setImageResource(R.drawable.icon_play);
        }

        /**
         * Setting the onclick listener for the play/pause button
         */
        ivPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View button) {
                button.setSelected(!button.isSelected());

                if (!button.isSelected()) {
                    ((ImageView) button).setImageResource(R.drawable.icon_play);
                    /**
                     * Pause
                     */
                    AudioOutputPromptController.getMediaPlayer().pause();
                    AudioOutputPromptController.getAudioPromptState(AudioOutputPromptController.getActivePrompt())
                            .setMode(AudioOutputPromptController.AudioPromptMode.PAUSED);
                } else {
                    ((ImageView) button).setImageResource(R.drawable.icon_pause);
                    /**
                     * Play
                     */

                    /**
                     * Check if another audio prompt gets focus; if yes, reset the media player
                     */
                    if (AudioOutputPromptController.getActivePrompt() != mPromptPosition) {
                        initMediaPlayer();

                        /**
                         * If the current active audio prompt was PLAYING change its mode to PAUSED
                         */
                        if (AudioOutputPromptController.getAudioPromptState(AudioOutputPromptController.getActivePrompt()).getMode()
                                == AudioOutputPromptController.AudioPromptMode.PLAYING) {

                            AudioOutputPromptController.setAudioPromptMode(AudioOutputPromptController.getActivePrompt(),
                                    AudioOutputPromptController.AudioPromptMode.PAUSED);

                            mOutputPromptsView.redrawPrompt(AudioOutputPromptController.getActivePrompt());
                        }

                        AudioOutputPromptController.setActiveAudioPrompt(mPromptPosition);
                    }
                    /**
                     * If I press the play button on an already focused audio prompt
                     */
                    else {
                        if (AudioOutputPromptController.getAudioPromptState(mPromptPosition).getMode() ==
                                AudioOutputPromptController.AudioPromptMode.PAUSED) {
                            AudioOutputPromptController.getMediaPlayer().start();
                            startUIAudioProgressTask();
                        }
                        if (AudioOutputPromptController.getAudioPromptState(mPromptPosition).getMode() ==
                                AudioOutputPromptController.AudioPromptMode.COMPLETED) {
                            AudioOutputPromptController.getMediaPlayer().seekTo(0);
                            AudioOutputPromptController.getMediaPlayer().start();
                            startUIAudioProgressTask();
                        }
                    }

                    AudioOutputPromptController.getAudioPromptState(AudioOutputPromptController.getActivePrompt())
                            .setMode(AudioOutputPromptController.AudioPromptMode.PLAYING);
                }
            }
        });

        /**
         * If the audio prompt is active and it's on PLAYING mode try to start a thread that will
         * update the seekbar and the progress text
         */
        if(AudioOutputPromptController.getActivePrompt() == mPromptPosition &&
                AudioOutputPromptController.getAudioPromptState(mPromptPosition).getMode() ==
                        AudioOutputPromptController.AudioPromptMode.PLAYING) {
            startUIAudioProgressTask();
        }

    }

    private void updateAudioProgressView(int progress, int duration) {
        tvAudioProgress.setText(DateTimeFormatters.convertMillisecondsToMinAndSec(progress) +
                "/" + DateTimeFormatters.convertMillisecondsToMinAndSec(duration));
        sbAudioProgress.setMax(duration);
        sbAudioProgress.setProgress(progress);
    }

    private void updateAudioProgress(boolean audioFinished) {
        //Reading current state from the controller
        AudioPromptState currentState = AudioOutputPromptController.getAudioPromptState(mPromptPosition);

        if (!audioFinished) {
            //Reading current position from the media player
            int currentPosition = AudioOutputPromptController.getMediaPlayer().getCurrentPosition();

            //Update the view in accordance with the progress made
            updateAudioProgressView(currentPosition, (int) currentState.getDuration());

            //Update the progress made for the current prompt in the controller
            AudioOutputPromptController.setAudioPromptProgress(mPromptPosition, currentPosition);
        }
        else {
            updateAudioProgressView((int)currentState.getDuration(), (int) currentState.getDuration());

            AudioOutputPromptController.setAudioPromptProgress(mPromptPosition,
                    (int)currentState.getDuration());
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser) {
            /**
             * If I have focus player.seek(selectedPosition)
             */
            if(mPromptPosition == AudioOutputPromptController.getActivePrompt()) {
                AudioOutputPromptController.getMediaPlayer().seekTo(progress);
                updateAudioProgressView(progress, AudioOutputPromptController.getMediaPlayer().getDuration());
            }
            /**
             * Else save the selected progress in the controller
             */
            else {
                if(AudioOutputPromptController.getAudioPromptState(mPromptPosition).getMode() !=
                        AudioOutputPromptController.AudioPromptMode.IDLE) {
                    updateAudioProgressView(progress,
                            (int) AudioOutputPromptController.getAudioPromptState(mPromptPosition).getDuration());
                    AudioOutputPromptController.setAudioPromptProgress(mPromptPosition, progress);
                }
            }

            if(AudioOutputPromptController.getAudioPromptState(mPromptPosition).getMode() ==
                    AudioOutputPromptController.AudioPromptMode.COMPLETED) {
                AudioOutputPromptController.setAudioPromptMode(mPromptPosition,
                        AudioOutputPromptController.AudioPromptMode.PAUSED);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // try to get audio focus
        int result = AudioOutputPromptController.audioManager.requestAudioFocus(this,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        // if focus granted
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // read the current state from the controller
            AudioPromptState state = AudioOutputPromptController.getAudioPromptState(mPromptPosition);
            // if the duration saved in the controller is 0, update it with the value obtained from
            // the media player
            if(state.getDuration() == 0) {
                state.setDuration(AudioOutputPromptController.getMediaPlayer().getDuration());
            }

            //update the view using the progress read from the controller
            updateAudioProgressView((int) state.getProgress(), (int) state.getDuration());

            if(state.getProgress() != 0) {
                mp.seekTo((int)state.getProgress());
            }

            mp.start();
            startUIAudioProgressTask();
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        Log.d(TAG, "focusChange: " + focusChange);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//        double progress = ((double)percent / 100) * mp.getDuration();
//        sbAudioProgress.setSecondaryProgress((int) progress);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        AudioOutputPromptController.setAudioPromptMode(mPromptPosition,
                AudioOutputPromptController.AudioPromptMode.COMPLETED);
        ivPlay.setSelected(false);
        ivPlay.setImageResource(R.drawable.icon_play);
    }

    private void initMediaPlayer() {
        AudioOutputPromptController.getMediaPlayer().reset();
        AudioOutputPromptController.getMediaPlayer().setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            AudioOutputPromptController.getMediaPlayer().setDataSource(mPromptView.getContext(),
                    Uri.parse(audioUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AudioOutputPromptController.getMediaPlayer().setOnBufferingUpdateListener(this);
        AudioOutputPromptController.getMediaPlayer().setOnCompletionListener(this);
        AudioOutputPromptController.getMediaPlayer().setOnPreparedListener(this);

        AudioOutputPromptController.getMediaPlayer().prepareAsync();
    }

    private void startUIAudioProgressTask() {
        audioProgressTask = new Runnable() {
            @Override
            public void run() {
                if(mPromptPosition == AudioOutputPromptController.getActivePrompt() &&
                        mOutputPromptsView.promptIsVisible(mPromptPosition) &&
                        AudioOutputPromptController.getAudioPromptState(mPromptPosition).getMode() ==
                                AudioOutputPromptController.AudioPromptMode.PLAYING) {
                    if (AudioOutputPromptController.getMediaPlayer() != null) {
                        updateAudioProgress(false);
                    }
                    mPromptView.getHandler().postDelayed(this, 1000);
                }
                else if(AudioOutputPromptController.getAudioPromptState(mPromptPosition).getMode() !=
                        AudioOutputPromptController.AudioPromptMode.PAUSED) {
                    updateAudioProgress(true);
                }
            }
        };

        mPromptView.getHandler().post(audioProgressTask);
    }

}
