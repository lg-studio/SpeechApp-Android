package com.tas.speech.sections.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tas.speech.R;
import com.tas.speech.sections.login.models.LoginResponse;
import com.tas.speech.sections.login.models.UserDetails;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private int mNavIcons[];

    private String name;        //String Resource for header View Name
    private String email;       //String Resource for header view email

    private Context mCtx;
    private UserDetails userDetails;

    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them
    public static class ViewHolder extends RecyclerView.ViewHolder {
        int holderId;

        TextView tvItemLabel;
        ImageView ivItemIcon;
        LinearLayout llRow;

        ImageView ivProfile;
        TextView tvName, tvEmail;

        public ViewHolder(View itemView, int ViewType) {    // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);

            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created
            if(ViewType == TYPE_ITEM) {
                tvItemLabel = (TextView) itemView.findViewById(R.id.tv_item_label);
                ivItemIcon = (ImageView) itemView.findViewById(R.id.iv_item_icon);
                llRow = (LinearLayout) itemView.findViewById(R.id.ll_drawer_row);
                holderId = 1;
            }
            else{
                tvName = (TextView) itemView.findViewById(R.id.tv_name);
                tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
                ivProfile = (ImageView) itemView.findViewById(R.id.iv_circle_view);
                holderId = 0;
            }
        }
    }

    public DrawerAdapter(Context ctx, String Titles[], int icons[], LoginResponse loginResponse){

        mNavTitles = Titles;
        mNavIcons = icons;

        userDetails = loginResponse.getData().getUserDetails();
        name = userDetails.getFirstName() + userDetails.getLastName();
        email = userDetails.getEmail();

        mCtx = ctx;
    }

    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drawer_row,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drawer_header,parent,false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created

        }
        return null;

    }

    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(DrawerAdapter.ViewHolder holder, int position) {
        if(holder.holderId ==1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.tvItemLabel.setText(mNavTitles[position - 1]); // Setting the Text with the array of our Titles
            holder.ivItemIcon.setImageResource(mNavIcons[position - 1]);// Settimg the image with array of our icons

            if(MainActivity.selectedSection == -1 && position == 1) {
                holder.llRow.setBackgroundColor(MainActivity.ctx.getResources().getColor(R.color.green_dark2));
            }
            else if(MainActivity.selectedSection != -1 && position == MainActivity.selectedSection) {
                holder.llRow.setBackgroundColor(MainActivity.ctx.getResources().getColor(R.color.green_dark2));
            }
            else {
                holder.llRow.setBackgroundColor(MainActivity.ctx.getResources().getColor(R.color.green_dark));
            }
        }
        else{
            Picasso.with(mCtx).load(userDetails.getProfileImageUrl()).into(holder.ivProfile);
            holder.tvName.setText(name);
            holder.tvEmail.setText(email);
        }
    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return mNavTitles.length+1; // the number of items in the list will be +1 the titles including the header view.
    }

    // With the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}
