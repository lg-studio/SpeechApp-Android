package com.tas.speech.sections.topic;

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
import com.tas.speech.sections.tasks.TasksFragment;
import com.tas.speech.R;
import com.tas.speech.sections.topic.models.Topic;

public class TopicDetailsFragment extends Fragment {

    private static final String TAG = "TopicDetailsFragment";

    private static final String ARG_TOPIC = "TOPIC";

    private Topic topic;

    public static TopicDetailsFragment newInstance(Topic topic) {
        TopicDetailsFragment fragment = new TopicDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TOPIC, topic);
        fragment.setArguments(args);
        return fragment;
    }

    public TopicDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topic = (Topic)getArguments().getSerializable(ARG_TOPIC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_topic_details, container, false);

        // Setting the topic type label
        TextView tvTopicType = (TextView)fragmentView.findViewById(R.id.tv_topic_type);
        tvTopicType.setText(topic.getName());

        // Setting the average score
        TextView tvAverageScore = (TextView)fragmentView.findViewById(R.id.tv_average_score);
        tvAverageScore.setText(getResources().getString(R.string.topics_prefix_avg_score)
                + " " + topic.getProperties().getAverageScore());

        // Setting the minutes spent
        TextView tvMinutesSpent = (TextView)fragmentView.findViewById(R.id.tv_minutes_spent);
        tvMinutesSpent.setText(getResources().getString(R.string.topics_prefix_min_spent)
                + " " + topic.getProperties().getMinutesSpent());

        // Setting the number of exercises for the current topci
        TextView tvExercises = (TextView)fragmentView.findViewById(R.id.tv_exercises);
        String suffix;
        if(topic.getTaskTemplates().size() == 1) {
            suffix = getResources().getString(R.string.topics_suffix_ex_singular);
        }
        else {
            suffix = getResources().getString(R.string.topics_suffix_ex_plural);
        }
        tvExercises.setText(topic.getTaskTemplates().size() + " " + suffix);

        // Setting the topic's associated image
        ImageView ivTaskIcon = (ImageView)fragmentView.findViewById(R.id.iv_topic_icon);
        Picasso.with(getActivity()).load(topic.getIconUrl()).into(ivTaskIcon);

        // Setting a click listener for the entire topic details layout
        RelativeLayout rlTopicDetails = (RelativeLayout)fragmentView.findViewById(R.id.rl_topic_details);
        rlTopicDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Log.d(TAG, "TASK TEMPLATE ID: " + topic.getTaskTemplates().get(0).getId());

                // Instantiating a TaskFragment
                TasksFragment fragment = TasksFragment.newInstance(topic.getName(), topic.getTaskTemplates());
                fragmentTransaction.replace(R.id.ll_fragment_container, fragment);

                // Adding the fragment to back stack
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // Returning the current fragment view
        return fragmentView;
    }

}
