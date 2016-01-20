package com.tas.speech.form.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tas.speech.R;

public class LovAdapter extends ArrayAdapter<String>{

    private String[] mObjects;
    private Context mCtx;

    public String mSelectedValue;

    public LovAdapter(Context context, int resource, String[] objects, String selectedValue) {
        super(context, resource, objects);

        mObjects = objects;
        mCtx = context;
        mSelectedValue = selectedValue;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder; // to reference the child views for later actions

        if (v == null) {
            LayoutInflater vi =
                    (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.layout_dialog_options_row, null);
            // cache view fields into the holder
            holder = new ViewHolder();
            holder.tvOption = (TextView) v.findViewById(R.id.tv_option);
            // associate the holder with the view for later lookup
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }
        // no local variables with findViewById here

        // use holder.nameText where you were
        // using the local variable nameText before

        holder.tvOption.setText(mObjects[position]);
        if(mObjects[position].equals(mSelectedValue)) {
//            holder.tvOption.setBackgroundDrawable(mCtx.getResources().getDrawable(R.drawable.selector_option));
            holder.tvOption.setBackgroundColor(mCtx.getResources().getColor(R.color.silver));
        }
        else {
            holder.tvOption.setBackgroundDrawable(mCtx.getResources().getDrawable(R.drawable.selector_option));
        }
        return v;
    }

    public String[] getObjects() {
        return mObjects;
    }

    // somewhere else in your class definition
    static class ViewHolder {
        TextView tvOption;
    }
}
