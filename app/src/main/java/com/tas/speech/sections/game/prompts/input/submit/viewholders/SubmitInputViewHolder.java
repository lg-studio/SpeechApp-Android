package com.tas.speech.sections.game.prompts.input.submit.viewholders;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tas.speech.R;
import com.tas.speech.sections.game.Game;
import com.tas.speech.sections.game.GameEventListener;

/**
 * Created by drago_000 on 31/07/2015.
 */
public class SubmitInputViewHolder {

    private static final String TAG = "SubmitInputViewHolder";

    private Context mCtx;
    private ImageView ivSubmit;
    private GameEventListener mGameEventListener;

    public SubmitInputViewHolder(Context ctx, View v, GameEventListener gameEventListener) {
        mCtx = ctx;

        ivSubmit = (ImageView)v.findViewById(R.id.iv_submit);

        mGameEventListener = gameEventListener;
    }

    public void bind() {
        ivSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "Game.ratings.size(): " + Game.ratings.size());
                Log.d(TAG, "Game.answers.size(): " + Game.answers.size());

                mGameEventListener.onTaskSubmit();


            }
        });
    }
}
