package com.tas.speech.sections.tasks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tas.speech.R;
import com.tas.speech.sections.main.MainActivity;
import com.tas.speech.sections.topic.models.TaskTemplate;

import java.util.ArrayList;

public class TasksFragment extends Fragment {

    private static final String ARG_TITLE = "TITLE";
    private static final String ARG_TASK_TEMPLATES = "TASK_TEMPLATES";

    private ViewPager pagerGames;
    private ProgressBar pbLoading;

    private String title;
    private ArrayList<TaskTemplate> taskTemplates;

    public static TasksFragment newInstance(String title, ArrayList<TaskTemplate> taskTemplates) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();

        args.putString(ARG_TITLE, title);
        args.putSerializable(ARG_TASK_TEMPLATES, taskTemplates);

        fragment.setArguments(args);
        return fragment;
    }

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Saving the received title and task templates
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            taskTemplates = (ArrayList<TaskTemplate>) getArguments().getSerializable(ARG_TASK_TEMPLATES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_tasks, container, false);

        pbLoading = (ProgressBar)fragmentView.findViewById(R.id.pb_loading);

        TextView tvTopictype = (TextView)fragmentView.findViewById(R.id.tv_topic_type);
        tvTopictype.setText(title);

        // Changing the section title
        ((MainActivity)getActivity()).changeTitle(getResources().getString(R.string.tasks_title));

        // Initializing the task details pager adapter, using taskTemplates array received on fragment
        // instantiation
        pagerGames = (ViewPager)fragmentView.findViewById(R.id.pager_games);
        pagerGames.setAdapter(new TasksPagerAdapter(getActivity(), getChildFragmentManager(), taskTemplates));

        return fragmentView;
    }

    public void hideLoadingProgress() {
        pbLoading.setVisibility(View.GONE);
    }

    public void showLoadingProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }
}
