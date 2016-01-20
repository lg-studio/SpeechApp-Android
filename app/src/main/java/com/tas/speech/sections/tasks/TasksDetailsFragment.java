package com.tas.speech.sections.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tas.speech.R;
import com.tas.speech.client.ApiTypes;
import com.tas.speech.SpeechApp;
import com.tas.speech.sections.game.GameActivity;
import com.tas.speech.sections.tasks.client.MultipleChoiceApi;
import com.tas.speech.sections.tasks.models.MultipleChoiceOptionsResponse;
import com.tas.speech.sections.topic.models.TaskTemplate;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TasksDetailsFragment extends Fragment {

    private static final String TAG = "TasksDetailsFragment";

    private static final String ARG_TASK_TEMPLATE = "TASK_TEMPLATE";

    private TaskTemplate taskTemplate;

    public static TasksDetailsFragment newInstance(TaskTemplate taskTemplate) {
        TasksDetailsFragment fragment = new TasksDetailsFragment();

        Bundle args = new Bundle();

        args.putSerializable(ARG_TASK_TEMPLATE, taskTemplate);

        fragment.setArguments(args);
        return fragment;
    }

    public TasksDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskTemplate = (TaskTemplate) getArguments().getSerializable(ARG_TASK_TEMPLATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_tasks_details, container, false);

        TextView tvGameTitle = (TextView)fragmentView.findViewById(R.id.tv_game_title);
        tvGameTitle.setText(taskTemplate.getName());
        TextView tvGameInfo = (TextView)fragmentView.findViewById(R.id.tv_game_info);
        tvGameInfo.setText("Retries: " + taskTemplate.getProperties().getRetries());
        TextView tvAverageScore = (TextView)fragmentView.findViewById(R.id.tv_average_score);
        tvAverageScore.setText("Average Rating: " + taskTemplate.getProperties().getAverageRating());

        ImageView ivTaskIcon = (ImageView)fragmentView.findViewById(R.id.iv_task_icon);
        Picasso.with(getActivity()).load(taskTemplate.getIconUrl()).into(ivTaskIcon);

        RelativeLayout rlGameDetails = (RelativeLayout)fragmentView.findViewById(R.id.rl_game_details);
        rlGameDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "taskTemplate.getType(): " + taskTemplate.getType());
                Log.d(TAG, "taskTemplate.getId(): " + taskTemplate.getId());
                Log.d(TAG, "taskTemplate.getIconUrl(): " + taskTemplate.getIconUrl());

                if(taskTemplate.getType() == null || taskTemplate.getType().equals("RegularTask")) {
                    Log.d(TAG, "Regular Game");
                    Intent gameIntent = new Intent(getActivity(), GameActivity.class);
                    Log.d(TAG, "Regular Game id: " + taskTemplate.getId());
                    gameIntent.putExtra(GameActivity.ARG_TASK_ID, taskTemplate.getId());
                    startActivity(gameIntent);
                }
                else if(taskTemplate.getType() != null &&
                        taskTemplate.getType().equals("MultipleChoiceTask")) {


                    Callback<MultipleChoiceOptionsResponse> multipleChoiceOptionsCallback  = new Callback<MultipleChoiceOptionsResponse>() {
                        @Override
                        public void success(MultipleChoiceOptionsResponse multipleChoiceOptionsResponse, Response response) {
                            ((TasksFragment)getParentFragment()).hideLoadingProgress();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            MultipleChoiceFragment fragment = MultipleChoiceFragment.newInstance(taskTemplate.getId(),
                                    multipleChoiceOptionsResponse.getData().getMultipleChoiceOptions());
                            fragmentTransaction.replace(R.id.ll_fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                        }
                    };

                    ((TasksFragment)getParentFragment()).showLoadingProgress();
                    ((MultipleChoiceApi)SpeechApp.getApiOfType(ApiTypes.MULTIPLE_CHOICE_API))
                            .multipleChoiceOptions(taskTemplate.getId(), multipleChoiceOptionsCallback);

                }
            }
        });

        return fragmentView;
    }

}
