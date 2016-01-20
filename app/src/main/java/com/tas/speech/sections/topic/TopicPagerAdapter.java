package com.tas.speech.sections.topic;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tas.speech.sections.topic.models.Topic;

import java.util.ArrayList;

// The topic details pager adapter
public class TopicPagerAdapter extends FragmentPagerAdapter {

    private Context mCtx;
    private ArrayList<Topic> mTopicList;

    public TopicPagerAdapter(Context ctx, FragmentManager mgr, ArrayList<Topic> topicList) {
        super(mgr);

        mCtx = ctx;
        mTopicList = topicList;
    }

    @Override
    public Fragment getItem(int position) {
        return (TopicDetailsFragment.newInstance(mTopicList.get(position)));
    }

    @Override
    public int getCount() {
        return mTopicList.size();
    }
}
