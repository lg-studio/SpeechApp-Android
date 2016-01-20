package com.tas.speech.sections.game.prompts.input.multiplechoice.viewholders;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tas.speech.R;
import com.tas.speech.sections.game.GameActivity;
import com.tas.speech.sections.game.models.PromptMultipleChoiceOption;

import java.util.ArrayList;

/**
 * Created by drago_000 on 07/08/2015.
 */
public class MultipleChoicePictureInputViewHolder {

    private static final int OPTION_NO = 4;

    private ArrayList<ImageView> ivOptions;
    private ImageView ivSubmit;

    private int currentSelectedOption;

    private Context mCtx;

    public MultipleChoicePictureInputViewHolder(Context ctx, View v) {
        ivOptions = new ArrayList<>();

        mCtx = ctx;

        ivOptions.add((ImageView)v.findViewById(R.id.iv_option1));
        ivOptions.add((ImageView)v.findViewById(R.id.iv_option2));
        ivOptions.add((ImageView)v.findViewById(R.id.iv_option3));
        ivOptions.add((ImageView)v.findViewById(R.id.iv_option4));

        ivSubmit = (ImageView)v.findViewById(R.id.iv_submit);

        currentSelectedOption = -1;
    }

    public void bind(final ArrayList<PromptMultipleChoiceOption> options) {

        for(int i = 0; i < OPTION_NO; ++i) {

            Picasso.with(mCtx).load(options.get(i).getPictureUrl()).into(ivOptions.get(i));

            ivOptions.get(i).setTag(String.valueOf(i));
            ivOptions.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = Integer.parseInt((String) v.getTag());
                    selectOption(index);


                    BigPictureDialogFragment bigPictureDialog = BigPictureDialogFragment.newInstance(options.get(index).getPictureUrl());
                    bigPictureDialog.show(((GameActivity)mCtx).getSupportFragmentManager(), null);
                }
            });
        }

    }

    private void selectOption(int optionIndex) {
        if(currentSelectedOption != optionIndex) {
            if(currentSelectedOption != -1) {
                ivOptions.get(currentSelectedOption).setBackgroundDrawable(mCtx.getResources()
                        .getDrawable(R.drawable.round_rectangle_multiple_text));
            }
            else {
                ivSubmit.setAlpha(250);
            }
            ivOptions.get(optionIndex).setBackgroundDrawable(mCtx.getResources()
                    .getDrawable(R.drawable.round_rectangle_multiple_text_selected));
            currentSelectedOption = optionIndex;
        }
    }

    public static class BigPictureDialogFragment extends DialogFragment {

        public static final String PICTURE_URL_ARG = "PICTURE_URL";

        public static BigPictureDialogFragment newInstance(String pictureUrl) {
            BigPictureDialogFragment frag = new BigPictureDialogFragment();
            Bundle args = new Bundle();
            args.putString(PICTURE_URL_ARG, pictureUrl);
            frag.setArguments(args);
            return frag;
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            String pictureUrl = getArguments().getString(PICTURE_URL_ARG);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.layout_dialog_bigger_image, null);
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            ImageView ivBigImage = (ImageView) dialogView.findViewById(R.id.iv_big_image);
            Picasso.with(getActivity()).load(pictureUrl).into(ivBigImage);

            builder.setView(dialogView);
            return builder.create();
        }

        public void onDismiss(DialogInterface arg) {
            super.onDismiss(arg);
        }
    }


}
