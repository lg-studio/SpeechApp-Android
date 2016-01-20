package com.tas.speech.sections.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tas.speech.SpeechApp;
import com.tas.speech.sections.certificates.CertificatesFragment;
import com.tas.speech.client.ApiTypes;
import com.tas.speech.sections.login.LoginActivity;
import com.tas.speech.NotificationsFragment;
import com.tas.speech.sections.profile.ProfileFragment;
import com.tas.speech.R;
import com.tas.speech.sections.login.client.LoginApi;
import com.tas.speech.sections.login.models.LoginResponse;
import com.tas.speech.sections.login.models.LogoutResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String TITLES[] = {"Home","Profile","Notifications","Log Out"};
    private int ICONS[] = {R.drawable.ic_home, R.drawable.ic_profile, R.drawable.ic_notifications,
            R.drawable.ic_logout};

    public static int selectedSection = -1;

    private Toolbar toolbar;                              // Declaring the Toolbar Object
    private TextView tvSectionTitle;

    private RecyclerView mRecyclerView;                           // Declaring RecyclerView
    private RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    private RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    private DrawerLayout Drawer;                                  // Declaring DrawerLayout

    private ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle
    private FragmentManager fragmentManager;

    public static String myPictureUrl;

    public static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;

        // Getting the user info from the login activity
        LoginResponse loginResponse =
                (LoginResponse)getIntent().getSerializableExtra(LoginActivity.ARG_USER_INFO);

        myPictureUrl = loginResponse.getData().getUserDetails().getProfileImageUrl();

        toolbar = (Toolbar) findViewById(R.id.toolbar_certificates); // Attaching the layout to the toolbar object

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvSectionTitle = (TextView)findViewById(R.id.toolbar_title);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new DrawerAdapter(this, TITLES, ICONS, loginResponse);       // Creating the Adapter
        // And passing the titles, icons and the login response

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager

        fragmentManager = getSupportFragmentManager();

        // Creating a gesture detector that will be used to handle click events on the drawer's items
        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this,
                new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        // Attaching a on touch listener to drawer's items
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

                    // If the user info section is touched do nothing
                    if(recyclerView.getChildPosition(child) == 0) {
                        return false;
                    }

                    // Switch section
                    goToSection(recyclerView, child);

                    // If another item than login was selected close the drawer
                    if(selectedSection != -1) {
                        Drawer.closeDrawers();
                    }

                    return true;
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

        init();
    }

    private void goToSection(RecyclerView recyclerView, View child) {
        int nextSection = recyclerView.getChildPosition(child);
        if(selectedSection != nextSection) {
            // If a previous choice was made set a the selected color for the selected row
            if(selectedSection != -1) {
                View prevSelectedSection = recyclerView.getChildAt(selectedSection);
                LinearLayout llDrawerRow = (LinearLayout)prevSelectedSection.findViewById(R.id.ll_drawer_row);
                llDrawerRow.setBackgroundColor(getResources().getColor(R.color.green_dark));
            }
            // Else, set the drawer's first item as the default selected item
            else {
                View home = recyclerView.getChildAt(1);
                LinearLayout llDrawerRow = (LinearLayout)home.findViewById(R.id.ll_drawer_row);
                llDrawerRow.setBackgroundColor(getResources().getColor(R.color.green_dark));
            }

            selectedSection = nextSection;

            // Clearing the fragment back stack
            int count = fragmentManager.getBackStackEntryCount();
            for(int i = 0; i < count; ++i) {
                fragmentManager.popBackStack();
            }

            // Starting a new fragment transaction
            FragmentTransaction fragmentTransaction;
            fragmentTransaction = fragmentManager.beginTransaction();

            // Attach the corresponding fragment to the fragment container
            switch (selectedSection) {
                case 1:
                    CertificatesFragment fragment = new CertificatesFragment();
                    fragmentTransaction.replace(R.id.ll_fragment_container, fragment);
                    break;
                case 2:
                    ProfileFragment profileFragment = new ProfileFragment();
                    fragmentTransaction.replace(R.id.ll_fragment_container, profileFragment);
                    break;
                case 3:
                    NotificationsFragment notificationsFragment = new NotificationsFragment();
                    fragmentTransaction.replace(R.id.ll_fragment_container, notificationsFragment);
                    break;
                case 4:
                    selectedSection = -1;
                    logout();
                    break;
            }

            fragmentTransaction.commit();

            // Color the selected item in the drawer
            LinearLayout llDrawerRow = (LinearLayout) child.findViewById(R.id.ll_drawer_row);
            llDrawerRow.setBackgroundColor(getResources().getColor(R.color.green_dark2));
        }
    }

    private void init() {
        // Setting the title for the certificates section
        tvSectionTitle.setText(getResources().getText(R.string.certificates_title));

        // Attaching the certificates fragment to the Main Activity's fragment container
        CertificatesFragment fragment = new CertificatesFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ll_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void logout() {
        ((LoginApi) SpeechApp.getApiOfType(ApiTypes.LOGIN_API)).logout(new Callback<LogoutResponse>() {
            @Override
            public void success(LogoutResponse logoutResponse, Response response) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                preferences.edit().remove(ctx.getString(R.string.auth_cookie)).commit();

                ((MainActivity) ctx).finish();
            }

            @Override
            public void failure(RetrofitError error) {
                ((MainActivity) ctx).finish();
            }
        });
    }

    public int getFragmentContainerId() {
        return R.id.ll_fragment_container;
    }

    public void changeTitle(String newTitle) {
        tvSectionTitle.setText(newTitle);
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() == 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
            // set title
            alertDialogBuilder.setTitle("Info");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Are you sure you want to quit the application?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            logout();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else {
            super.onBackPressed();
        }

    }
}
