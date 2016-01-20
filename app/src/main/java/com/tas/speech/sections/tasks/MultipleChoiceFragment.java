package com.tas.speech.sections.tasks;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tas.speech.R;
import com.tas.speech.sections.game.GameActivity;
import com.tas.speech.sections.tasks.models.MultipleChoiceOption;
import com.tas.speech.sections.tasks.models.MultipleChoiceOptions;

import java.util.ArrayList;

public class MultipleChoiceFragment extends Fragment {
    private static final int ROW_SIZE = 3;

    private static final String ARG_MULTIPLE_CHOICE_OPTIONS = "MULTIPLE_CHOICE_OPTIONS";
    private static final String ARG_TASK_ID = "TASK_ID";

    private TextView tvChoice;
    private TableLayout tableChoices;

    private MultipleChoiceOptions options;
    private String taskId;

    public static MultipleChoiceFragment newInstance(String taskId, MultipleChoiceOptions options) {
        MultipleChoiceFragment fragment = new MultipleChoiceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MULTIPLE_CHOICE_OPTIONS, options);
        args.putString(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    public MultipleChoiceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            options = (MultipleChoiceOptions) getArguments().getSerializable(ARG_MULTIPLE_CHOICE_OPTIONS);
            taskId = getArguments().getString(ARG_TASK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_multiple_choice, container, false);

        tvChoice = (TextView)fragmentView.findViewById(R.id.tv_choice);
        tvChoice.setText("");

        tableChoices = (TableLayout)fragmentView.findViewById(R.id.table_choices);

        tvChoice.setText(options.getDisplayText());

        final ArrayList<MultipleChoiceOption> optionsList = options.getOptions();

        int index = 0;
        int columnIndex;

        TableRow row = (TableRow)inflater.inflate(R.layout.layout_multiple_row, null);
        while(index < optionsList.size()) {
            RelativeLayout cell = null;
            if(optionsList.get(index).getType().equals("Text")) {
                cell = (RelativeLayout)inflater.inflate(R.layout.layout_multiple_cell_topic, null);
                TextView tvTopic = (TextView) cell.findViewById(R.id.tv_topic);
                tvTopic.setText(optionsList.get(index).getProperties().getText());
                cell.setTag(optionsList.get(index).getInnerOptions());
            }
            else if(optionsList.get(index).getType().equals("ImageProcessor")) {
                cell = (RelativeLayout)inflater.inflate(R.layout.layout_multiple_cell_image, null);
                ImageView ivPicture = (ImageView) cell.findViewById(R.id.iv_image);
                Picasso.with(getActivity()).load(optionsList.get(index).getProperties().getImageUrl()).into(ivPicture);
            }

            cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<MultipleChoiceOption> tagOptions = (ArrayList<MultipleChoiceOption>) v.getTag();

                    if(tagOptions != null && !tagOptions.isEmpty()) {
                        MultipleChoiceOptions innerOptions = new MultipleChoiceOptions();
                        innerOptions.setDisplayText(options.getDisplayText());
                        innerOptions.setOptions(tagOptions);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        MultipleChoiceFragment fragment = MultipleChoiceFragment.newInstance(taskId, innerOptions);
                        fragmentTransaction.replace(R.id.ll_fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else {
                        Intent gameIntent = new Intent(getActivity(), GameActivity.class);

                        gameIntent.putExtra(GameActivity.ARG_TASK_ID, taskId);

                        startActivity(gameIntent);
                    }
                }
            });

            row.addView(cell);

            columnIndex = index % ROW_SIZE;
            //New row -> add to table
            if(columnIndex == ROW_SIZE - 1) {
                tableChoices.addView(row);
                row = (TableRow)inflater.inflate(R.layout.layout_multiple_row, null);
            }
            index++;
        }

        // Fill the last row with empty cells if it's not complete
        int childrenNo = row.getChildCount();
        if(childrenNo < ROW_SIZE) {
            for(int i = 0; i < ROW_SIZE - childrenNo; ++i) {
                RelativeLayout cell = (RelativeLayout)inflater.inflate(R.layout.layout_multiple_cell_topic, null);
                cell.setVisibility(View.INVISIBLE);
                row.addView(cell);
            }
        }

        tableChoices.addView(row);

        return fragmentView;
    }

}
