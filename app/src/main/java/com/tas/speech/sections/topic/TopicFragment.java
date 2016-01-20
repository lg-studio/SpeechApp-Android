package com.tas.speech.sections.topic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tas.speech.R;
import com.tas.speech.client.ApiTypes;
import com.tas.speech.SpeechApp;
import com.tas.speech.sections.main.MainActivity;
import com.tas.speech.sections.topic.client.TopicsApi;
import com.tas.speech.sections.topic.models.TasksNavTreeResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TopicFragment extends Fragment {

    private static final String TAG = "TopicFragment";

    private static final String ARG_CERTIFICATE_NAME = "CERTIFICATE_NAME";
    private static final String ARG_CERTIFICATE_ID = "CERTIFICATE_ID";
    private static final String ARG_CERTIFICATE_SUBCATEGORY_ID = "CERTIFICATE_SUBCATEGORY_ID";

    private TextView tvCertificateName;
    private ProgressBar pbLoading;

    private ViewPager pagerTopic;

    private Callback<TasksNavTreeResponse> tasksNavTreeCallback;

    private String certificateName, certificateId, certificateSubcategoryId;


    public static TopicFragment newInstance(String certificateName, String certificateId,
                                            String certificateSubcategoryId) {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CERTIFICATE_NAME, certificateName);
        args.putString(ARG_CERTIFICATE_ID, certificateId);
        args.putString(ARG_CERTIFICATE_SUBCATEGORY_ID, certificateSubcategoryId);
        fragment.setArguments(args);
        return fragment;
    }

    public TopicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            certificateName = getArguments().getString(ARG_CERTIFICATE_NAME);
            certificateId = getArguments().getString(ARG_CERTIFICATE_ID);
            certificateSubcategoryId = getArguments().getString(ARG_CERTIFICATE_SUBCATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_topic, container, false);

        // Obtain a reference to the certificate name text view
        tvCertificateName = (TextView)fragmentView.findViewById(R.id.tv_certificate_name);
        tvCertificateName.setText(certificateName);

        // Setting the section title
        ((MainActivity)getActivity()).changeTitle(getResources().getString(R.string.topics_title));

        pagerTopic = (ViewPager)fragmentView.findViewById(R.id.pager_topic);

        pbLoading = (ProgressBar) fragmentView.findViewById(R.id.pb_loading);

        // Initializing the task navigation tree service callback method
        tasksNavTreeCallback = new Callback<TasksNavTreeResponse>() {

            @Override
            public void success(TasksNavTreeResponse tasksNavTreeResponse, Response response) {

                Log.d(TAG, "tasksNavTreeResponse.getData().getTopicList().size(): " + tasksNavTreeResponse.getData().getTopicList().size());

                // Initializing the pager adapter using the received response
                pagerTopic.setAdapter(new TopicPagerAdapter(getActivity(), getChildFragmentManager(),
                        tasksNavTreeResponse.getData().getTopicList()));

                // Hiding the loading indicator
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };

        // Showing the loading indicator
        pbLoading.setVisibility(View.VISIBLE);

        // Calling the task navigation tree service, passing the previously defined callback method

        Log.d(TAG, "Calling topicsService: " + certificateId + ", " + certificateSubcategoryId);
        ((TopicsApi)SpeechApp.getApiOfType(ApiTypes.TOPICS_API)).taskNavigationTree(certificateId,
                certificateSubcategoryId, tasksNavTreeCallback);

        // Returning the current fragment view instance
        return fragmentView;
    }

}
