package com.tas.speech.sections.certificates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tas.speech.R;
import com.tas.speech.sections.certificates.models.Certificate;

import java.util.ArrayList;

/**
 * Created by drago_000 on 02/08/2015.
 */
public class CertificatesAdapter extends BaseExpandableListAdapter{

    private Context mCtx;
    private ArrayList<Certificate> mCertificates;

    public CertificatesAdapter(Context ctx, ArrayList<Certificate> certificates) {
        mCtx = ctx;
        mCertificates = certificates;
    }

    @Override
    public int getGroupCount() {
        return mCertificates.size();
    }

    @Override
    public int getChildrenCount(int parent) {
        return mCertificates.get(parent).getSubcategories().size();
    }

    @Override
    public Object getGroup(int parent) {
        return mCertificates.get(parent).getName();
    }

    @Override
    public Object getChild(int parent, int child) {
        return mCertificates.get(parent).getSubcategories().get(child).getName();
    }

    @Override
    public long getGroupId(int parent) {
        return parent;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {

        String parentTitle = (String) getGroup(parent);

        if(convertView == null) {
            // Inflating the parent view
            LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_certificate_parent, parentView, false);
        }

        // Setting the parent label
        TextView tvParentTitle = (TextView)convertView.findViewById(R.id.tv_certificate_parent_title);
        tvParentTitle.setText(parentTitle);

        // Managing the arrow indicator behavior: show the right arrow when the group has children and
        // it isn't expanded; show the down arrow when the group is expanded; hide the indicator when
        // the group has no children
        ImageView ivCustomIndicator = (ImageView)convertView.findViewById(R.id.iv_custom_indicator);
        if(getChildrenCount(parent) == 0) {
            ivCustomIndicator.setVisibility(View.GONE);
        }
        else {
            ivCustomIndicator.setVisibility(View.VISIBLE);
            if(isExpanded) {
                ivCustomIndicator.setImageResource(R.drawable.ic_arrow_down);
            }
            else {
                ivCustomIndicator.setImageResource(R.drawable.ic_arrow_right);
            }
        }

        return convertView;
    }

    @Override
    public View getChildView(int parent, int child, boolean isLastChild, View convertView, ViewGroup parentView) {
        String childTitle = (String) getChild(parent, child);

        if(convertView == null) {
            // Inflating the child view
            LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_certificate_child, parentView, false);
        }

        // Setting the child label
        TextView tvChildTitle = (TextView)convertView.findViewById(R.id.tv_certificate_child_title);
        tvChildTitle.setText(childTitle);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
