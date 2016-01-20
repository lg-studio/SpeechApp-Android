package com.tas.speech.sections.game.prompts.output.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tas.speech.R;
import com.tas.speech.sections.game.models.PromptProperties;
import com.tas.speech.sections.game.prompts.output.OutputPromptViewHolder;

/**
 * TextOutputPromptViewHolder
 */
public class TextOutputPromptViewHolder implements OutputPromptViewHolder {

    private TextView tvUsername, tvMessage;
    private ImageView ivAvatar;

    public TextOutputPromptViewHolder(View promptView) {
        tvUsername = (TextView)promptView.findViewById(R.id.tv_username);
        tvMessage = (TextView)promptView.findViewById(R.id.tv_message);
        ivAvatar = (ImageView)promptView.findViewById(R.id.iv_avatar);
    }

    public void bind(PromptProperties promptProperties, int promptPosition, View promptView) {
        tvMessage.setText(promptProperties.getText());
        tvUsername.setText(promptProperties.getName());

        Picasso.with(promptView.getContext()).load(promptProperties.getPictureUrl()).into(ivAvatar);
    }
}
