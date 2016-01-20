package com.tas.speech.sections.tasks;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tas.speech.sections.topic.models.TaskTemplate;

import java.util.ArrayList;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class TasksPagerAdapter extends FragmentPagerAdapter {
    private Context mCtx;
    private ArrayList<TaskTemplate> mTaskTemplates;

    public TasksPagerAdapter(Context ctx, FragmentManager mgr, ArrayList<TaskTemplate> taskTemplates) {
        super(mgr);

        mCtx = ctx;
        mTaskTemplates = taskTemplates;
    }

    @Override
    public Fragment getItem(int position) {
        return (TasksDetailsFragment.newInstance(mTaskTemplates.get(position)));
    }

    @Override
    public int getCount() {
        return mTaskTemplates.size();
    }
}
